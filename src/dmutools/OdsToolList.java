/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dmutools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jopendocument.dom.OOUtils;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

/**
 *
 * @author mats
 */
public class OdsToolList {

    ArrayList<OdsTool> toolList = null;
    File odsToolListFile;
    private DMUPreferences dMUPreferences = DMUPreferences.getInstance();
    
    OdsToolList(File selectedFile) {
        odsToolListFile = selectedFile;
        
    }

    void createOdsToolList(String tmText) {
        toolList = new ArrayList<>();
        final String COMMENT_REGEX = "(.*)\\((.*)\\).*";
        Scanner lineScanner = new Scanner(tmText);
        while ( lineScanner.hasNext() ) {
            OdsTool odsTool = new OdsTool();
            toolList.add(odsTool);
            String line = lineScanner.nextLine().trim();
            Pattern commentPattern = Pattern.compile( COMMENT_REGEX);
            Matcher mC = commentPattern.matcher(line);
            if ( mC.matches() ) {
                String comment = mC.group(2).trim();
                String holder = "";
                int lastSpacePos = comment.lastIndexOf(" ");
                if ( lastSpacePos >0 ) {
                    // There is a space. Take the last word as name of holder.
                    holder = comment.substring(lastSpacePos + 1);
                    comment = comment.substring(0, lastSpacePos ).trim();
                }
                
                odsTool.setComment( comment );
                odsTool.setHolderName( holder );
                line = mC.group(1).trim();
            }
            
            final String TOOL_LIST_REGEX = "P(\\d+) T(\\d+) L([0-9]*\\.?[0-9]+)( R([-+]?[0-9]*\\.?[0-9]+))?" ; //( R([0-9]*\\.?]0-9]+))?";
            Pattern toolListPattern = Pattern.compile(TOOL_LIST_REGEX);
            Matcher mT = toolListPattern.matcher(line);
            if ( mT.matches() ) {
                odsTool.setPlaceNo( Integer.parseInt(mT.group(1)));
                odsTool.setToolNo( Integer.parseInt(mT.group(2)));
                odsTool.setLength( mT.group(3) );
                odsTool.setRadius(mT.group(4) );
            } else System.out.println( line + ":Matchar ej");
        }
        try {
            saveToOds();
        } catch (IOException ex) {
            Logger.getLogger(OdsToolList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void saveToOds() throws IOException {
        String templateFileName = dMUPreferences.getTemplateFileName();
        File odsTemplateFile = new File(templateFileName);
        Sheet sheet = SpreadSheet.createFromFile(odsTemplateFile).getFirstSheet();
        
        // Lägg in datum högst upp till höger
        sheet.getCellAt( 7, 0 ).setValue(new Date() );
        
        // Lägg in kund, benämning och artikelnummer i kolumn 0 och raderna 3,4 och 5
        // Först kund
        Path path = odsToolListFile.getAbsoluteFile().toPath();
        int level = path.getNameCount();
        String customerName = path.getName(level - 5 ).toString();
        sheet.getCellAt(0,3).setValue(customerName);

        // och så artikelnummer och benämning. Artikelnummer är det först "ordet" i pathnivå level - 4
        String pathLevelm4 = path.getName(level -4 ).toString();
        int posOfFirstSpace = pathLevelm4.indexOf(" ");
        String artNo = "";
        String name = "";
        if ( posOfFirstSpace > 0 ) {
            artNo = pathLevelm4.substring( 0, posOfFirstSpace );
            name = pathLevelm4.substring(posOfFirstSpace + 1 , pathLevelm4.length() );
        } else {
            name = pathLevelm4;
        }
        sheet.getCellAt( 0, 4 ).setValue(name);
        sheet.getCellAt( 0, 5 ).setValue(artNo);

        // Fyll i verktygslistan men sortera den först.
        Collections.sort(toolList );
        int currentOdsLine = 15;
        Iterator<OdsTool> iterator = toolList.iterator();
        while ( iterator.hasNext() ) {
            OdsTool tool = iterator.next();
            sheet.getCellAt( 0, currentOdsLine ).setValue(tool.getToolNo());
            sheet.getCellAt( 1, currentOdsLine ).setValue(tool.getPlaceNo());
            sheet.getCellAt( 2, currentOdsLine ).setValue(tool.getComment());
            sheet.getCellAt( 5, currentOdsLine ).setValue((String)tool.getRadius());
            sheet.getCellAt( 7, currentOdsLine ).setValue(tool.getHolderName());
            sheet.getCellAt( 2, currentOdsLine + 1 ).setValue( tool.getLength());
            currentOdsLine += 2;
        }
        sheet.getSpreadSheet().saveAs(odsToolListFile);
        OOUtils.open(odsToolListFile);
    }
    
}
