/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dmutools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author mats
 */
class TmLine {

    public int getPlaceNo() {
        return placeNo;
    }

    public int getToolNo() {
        return toolNo;
    }

    public String getLValue() {
        return lValue;
    }

    public String getRValue() {
        return rValue;
    }

    public Boolean getHasR() {
        return hasR;
    }

    public String getComment() {
        return comment;
    }
    String line;
    int placeNo;
    int toolNo;
    String lValue;
    String rValue;
    Boolean hasR;
    String comment;

    TmLine(String line) {
        this.line = line;
        readPlaceNo();
        readToolNo();
        readLValue();
        readRValue();
        readComment();
    }

    private int readToolNo() {
        if (line == null ) return -1;
        final String toolMatch = "T(\\d+)";
        Pattern p = Pattern.compile(toolMatch);
        Matcher m = p.matcher(lineWithoutComment());
        if (m.find()) {
            String toolString = m.group(1).trim();
            toolNo = Integer.parseInt(toolString);
            return toolNo;
        }
        return -2;
        
    }

    private int readPlaceNo() {
        if (line == null ) return -1;
        final String placeMatch = "P(\\d+)";
        Pattern p = Pattern.compile(placeMatch);
        Matcher m = p.matcher(lineWithoutComment());
        if (m.find()) {
            String placeString = m.group(1).trim();
            placeNo = Integer.parseInt(placeString);
            return placeNo;
        }
        return -2;
        
    }

    private String readLValue() {
        if (line == null ) return null;
        final String lValMatch = "L(\\d+\\.*\\d*)";
        Pattern p = Pattern.compile(lValMatch);
        Matcher m = p.matcher(lineWithoutComment());
        if (m.find()) {
            lValue = m.group(1).trim();
            return lValue;
        }
        return null;

    }

    private String readRValue() {
        if (line == null ) return null;
        final String rValMatch = "R([+-]?\\d+\\.?\\d*)";
        Pattern p = Pattern.compile(rValMatch);
        String lineWoutComment = lineWithoutComment();
        Matcher m = p.matcher(lineWoutComment);
        if (m.find()) {
            rValue = m.group(1).trim();
            hasR = true;
            return rValue;
        }
        hasR = false;
        return null;

    }

    private String lineWithoutComment() {
        String lineWithoutComment = line.replaceAll("\\(.*\\)", "");
        return lineWithoutComment;
    }

    void setLValue(String lValue) {
        this.lValue = lValue;
    }

    void setRValue(String rValue) {
        this.rValue = rValue;
    }

    String getLine() {
        line = "P" + placeNo +
               " T" + toolNo + 
               " L" + lValue;
        if (rValue == null ) {
            line += " " + comment;
        } else {
            line += " R" + rValue + " " + comment;

        }
               
        return line;
    }

    private String readComment() {
        comment = "";
        if (line == null ) return "";
        final String commentMatch = "(\\(.*\\))";
        Pattern p = Pattern.compile(commentMatch);
        Matcher m = p.matcher(line);
        if (m.find()) {
            comment = m.group(1).trim();
            return comment;
        }
        return "";
    }

    boolean hasRValue() {
        return rValue != null;
    }
    
}
