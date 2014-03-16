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
    String line;
    int toolNo;
    String lValue;
    String rValue;

    TmLine(String line) {
        this.line = line;
        readToolNo();
        readLValue();
        readRValue();
    }

    int readToolNo() {
        if (line == null ) return -1;
        final String toolMatch = "T(\\d+)";
        Pattern p = Pattern.compile(toolMatch);
        Matcher m = p.matcher(line);
        if (m.find()) {
            String toolString = m.group(1).trim();
            int tNo = Integer.parseInt(toolString);
            return tNo;
        }
        return -2;
        
    }

    String readLValue() {
        if (line == null ) return null;
        final String lValMatch = "L(\\d+\\.*\\d*)";
        Pattern p = Pattern.compile(lValMatch);
        Matcher m = p.matcher(line);
        if (m.find()) {
            lValue = m.group(1).trim();
            return lValue;
        }
        return null;

    }

    String readRValue() {
        if (line == null ) return null;
        final String rValMatch = "R(\\d+\\.*\\d*)";
        Pattern p = Pattern.compile(rValMatch);
        Matcher m = p.matcher(line);
        if (m.find()) {
            rValue = m.group(1).trim();
            return rValue;
        }
        return null;

    }

    void setLValue(String lValue) {
        this.lValue = lValue;
    }

    void setRValue(String rValue) {
        this.rValue = rValue;
    }

    String getLine() {
        line = line.replaceAll("L\\d*\\.?\\d*" , "L"+lValue);
        line = line.replaceAll("R\\d*\\.?\\d*" , "R"+rValue);
        return line;
    }
    
}
