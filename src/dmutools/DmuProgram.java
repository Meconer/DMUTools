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
                    programLines.add(line);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    void sendToTextArea(JTextArea jTAProgram) {
        for ( String line : programLines ) {
            jTAProgram.append(line + "\n" );
        }
        
    }
}
