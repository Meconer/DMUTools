/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dmutools;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTextArea;

/**
 *
 * @author mats
 */
public class DmuProgram {
    ArrayList<String> programLines;

    public DmuProgram() {
        programLines = new ArrayList<>();
    }
    
    public void readFile(File fileToRead){
        programLines.clear();
        try {
            try (BufferedReader reader = Files.newBufferedReader( fileToRead.toPath() , Charset.defaultCharset())) {
                String line;
                while (( line = reader.readLine()) != null ) {
                    String comment = extractComment(line);
                    if (comment != null ) programLines.add( comment );
                    String tool = extractTool(line);
                    if (tool != null ) programLines.add( tool );
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public void sendToTextArea(JTextArea jTAProgram) {
        for ( String line : programLines ) {
            jTAProgram.append(line + "\n" );
        }
    }
    
    private String extractComment(String line) {
        final String commentMatch = ".*\\((.*)\\)";
        Pattern p = Pattern.compile(commentMatch);
        Matcher m = p.matcher(line);
        if (m.find()) return m.group(1).trim();
        return null;
    }

    private String extractTool(String line) {
        final String toolMatch = ".*(T\\d*)";
        line = line.replaceAll("\\(.*?\\)", "");
        Pattern p = Pattern.compile(toolMatch);
        Matcher m = p.matcher(line);
        if (m.find()) return m.group(1).trim();
        return null;
    }

    void sendToList(DefaultListModel<String> dmuListModel) {
        for ( String line : programLines ) {
            dmuListModel.addElement(line+"\n");
        }
    }

}
