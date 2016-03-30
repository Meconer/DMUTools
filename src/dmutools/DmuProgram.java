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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultListModel;
import javax.swing.JTextArea;

/**
 *
 * @author mats
 */
public class DmuProgram {
    DefaultListModel<String> programLines;
    Set<DMUTool> toolSet = new LinkedHashSet<>();
    private Path currentDir = null;


    public DmuProgram() {
        programLines = new DefaultListModel<>();
    }
    
    public Path getCurrentDir() {
        return currentDir;
    }
    

    public void readFile(File fileToRead){
        setCurrentDir( fileToRead );
        programLines.clear();
        try {
            try (BufferedReader reader = Files.newBufferedReader( fileToRead.toPath() , Charset.forName("ISO_8859_1"))) {
                String line;
                
                Pattern toolCommentPattern = Pattern.compile("\\(\\* T(\\d+) (.*)\\)");
                while (( line = reader.readLine()) != null ) {
                    String comment = extractComment(line);
                    if (comment != null ) { 
                        programLines.add( programLines.size(), comment );
                        if ( comment.startsWith("(* T")) {
                            // This is a tool comment
                            Matcher m = toolCommentPattern.matcher(comment);
                            if ( m.find() ) {
                                int toolNo = Integer.parseInt( m.group(1) );
                                String toolComment = m.group(2);
                                DMUTool dmuTool = new DMUTool(toolNo, 0, 0, toolComment);
                                if ( !toolNoExist(toolNo) ) {
                                    toolSet.add(dmuTool);
                                } else {
                                    changeTool( dmuTool );
                                }
                            }
                            
                        }
                    }
                    String tool = extractTool(line);
                    if (tool != null ) {
                        programLines.add( programLines.size(), tool );
                        int toolNo = Integer.parseInt(tool.substring(1));
                        if ( !toolNoExist( toolNo ) ) {
                            DMUTool dmuTool = new DMUTool(Integer.parseInt(tool.substring(1)));
                            toolSet.add(dmuTool);
                        }
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(toolSet);
    }

    private String extractComment(String line) {
        final String commentMatch = ".*(\\(.*\\))";
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

    void appendToolListFromProgramEvents(JTextArea textArea) {
        for ( DMUTool dmuTool : toolSet ) {
            int toolNo = dmuTool.getToolNo();
            String s = "P" + toolNumberToPlaceNumber(toolNo) + " T" + toolNo + " L0 R0 " +
                    "(" + dmuTool.getToolName() + ")\n";
            textArea.append(s) ;
        }
    }

    // The dmu 50 uses two magazine chains. The tools are placed in this order:
    // Tool Place
    // ==========
    //   1    2
    //   2    17
    //   3    3
    //   4    18
    //   5    4
    //   6    19
    //   7    5
    //   8    20
    // and so on.
    private int toolNumberToPlaceNumber(int toolNumber) {
        if ( isOdd(toolNumber) ) {
            return 2 + toolNumber / 2;
        } else {
            return 16 + toolNumber / 2;
        }
    }

    // Returns true if number is odd.
    private boolean isOdd(int n) {
        return   n % 2 != 0 ;
    }

    private void setCurrentDir(File fileToRead) {
        currentDir = Paths.get( fileToRead.getAbsolutePath() ).getParent();
    }

    boolean hasFile() {
        return currentDir != null;
    }

    private boolean toolNoExist(int toolNo) {
        for ( DMUTool dt : toolSet ) {
            if ( dt.getToolNo() == toolNo ) return true;
        }
        return false;
    }

    private void changeTool(DMUTool dmuTool) {
        
        int toolNo = dmuTool.getToolNo();
        for ( DMUTool dt : toolSet ) {
            if ( dt.getToolNo() == toolNo ) {
                dt.setToolLength(dmuTool.getToolLength());
                dt.setToolRadius(dmuTool.getToolRadius());
                dt.setToolName(dmuTool.getToolName());
            }
        }
    }
    

}
