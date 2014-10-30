/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dmutools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
        File odsTemplateFile = new File("E:\\Mats\\VLISTDMU.ods");
        Sheet sheet = SpreadSheet.createFromFile(odsTemplateFile).getFirstSheet();
        int currentOdsLine = 14;
        Iterator<OdsTool> iterator = toolList.iterator();
        while ( iterator.hasNext() ) {
            OdsTool tool = iterator.next();
            sheet.getCellAt( 0, currentOdsLine ).setValue(tool.getToolNo());
            sheet.getCellAt( 1, currentOdsLine ).setValue(tool.getPlaceNo());
            sheet.getCellAt( 2, currentOdsLine ).setValue(tool.getComment());
            sheet.getCellAt( 5, currentOdsLine ).setValue((String)tool.getRadius());
            sheet.getCellAt( 7, currentOdsLine ).setValue(tool.getHolderName());
            sheet.getCellAt( 3, currentOdsLine + 1 ).setValue( "Ställängd: " + tool.getLength());
            currentOdsLine += 2;
        }
        sheet.getSpreadSheet().saveAs(odsToolListFile);
        OOUtils.open(odsToolListFile);
    }
    
}
