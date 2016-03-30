/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmutools;

/**
 *
 * @author Mcx8
 */
public class DMUTool {
    private int toolNo;
    private double toolLength;
    private double toolRadius;
    private String toolName;

    public DMUTool(int toolNo, double toolLength, double toolRadius, String toolName) {
        this.toolNo = toolNo;
        this.toolLength = toolLength;
        this.toolRadius = toolRadius;
        this.toolName = toolName;
    }

    public DMUTool(int toolNo) {
        this.toolNo = toolNo;
    }

    public int getToolNo() {
        return toolNo;
    }

    public void setToolNo(int toolNo) {
        this.toolNo = toolNo;
    }

    public double getToolLength() {
        return toolLength;
    }

    public void setToolLength(double toolLength) {
        this.toolLength = toolLength;
    }
    
    public double getToolRadius() {
        return toolRadius;
    }

    public void setToolRadius(double toolRadius) {
        this.toolRadius = toolRadius;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }
    
    
}
