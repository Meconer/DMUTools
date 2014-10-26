/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dmutools;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                String comment = mC.group(2);
                
                System.out.println( comment );
                odsTool.setComment( comment );
                line = mC.group(1).trim();
            }
            
            final String TOOL_LIST_REGEX = "P(\\d+) T(\\d+) L([0-9]*\\.?[0-9]+)( R([-+]?[0-9]*\\.?[0-9]+))?" ; //( R([0-9]*\\.?]0-9]+))?";
            Pattern toolListPattern = Pattern.compile(TOOL_LIST_REGEX);
            Matcher mT = toolListPattern.matcher(line);
            if ( mT.matches() ) {
                for ( int i = 0 ; i <= mT.groupCount() ; i++ ) {
                    System.out.println(i + " : " + mT.group(i) );
                }
            } else System.out.println( line + ":Matchar ej");
            
        }
    }
    
}
