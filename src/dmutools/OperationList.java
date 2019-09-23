/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmutools;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author mats.andersson
 */
public class OperationList {

    String programText;
    
    OperationList(String programText) {
        this.programText = programText;
    }

    public String createHtmlOpList() {
        String opCommentMatch = ".*\\(\\*\\* (.*) \\*\\*\\)";
        Pattern opCommentPattern = Pattern.compile(opCommentMatch);

        String toolCommentMatch = ".*\\(\\* (.*) \\)";
        Pattern toolCommentPattern = Pattern.compile(toolCommentMatch);
        
        String coolantMatch = "M[7,8,9,07,08,09]";
        Pattern coolantPattern = Pattern.compile(coolantMatch);
        
        String resultString ="";
        int opNo = 1;
        
        Scanner scanner = new Scanner(programText);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            
            Matcher m = opCommentPattern.matcher(line);
            if ( m.find()) {
                resultString += "   OP" + opNo + " : " + m.group(1) + "\n";
                opNo++;
            }
            
            m = toolCommentPattern.matcher(line);
            if ( m.find()) {
                resultString += m.group(1) + "\n";
            }
            
            line = removeComment(line);
            
            m = coolantPattern.matcher(line);
            if ( m.find() ) {
                resultString += m.group() + "\n";
            }
            
        }
        return resultString;
    }

    private String removeComment(String line) {
        String result = "";
        boolean isInComment = false;
        for ( int i = 0 ; i< line.length(); i++ ) {
            char c = line.charAt(i);
            if ( !isInComment ) {
                if ( c == '(') {
                    isInComment = true;
                } else 
                    result += c;
            } else {
                if ( c == ')')  isInComment = false;
            }
        }
        return result;
    }
    
}
