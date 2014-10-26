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
        Scanner scanner = new Scanner(tmText);
        while ( scanner.hasNext() ) {
            OdsTool odsTool = new OdsTool();
            toolList.add(odsTool);
            String line = scanner.nextLine();
            Pattern commentPattern = Pattern.compile( COMMENT_REGEX);
            Matcher m = commentPattern.matcher(line);
            if ( m.matches() ) {
                String comment = m.group(2);
                
                System.out.println( comment );
                odsTool.setComment( comment );
                line = m.group(1);
            }
            
            System.out.println(line);
                   
            
        }
    }
    
}
