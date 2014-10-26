/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dmutools;

/**
 *
 * @author mats
 */
class OdsTool {
    private int toolNo = 0;
    private int placeNo = 0;
    private String length = "";
    private String radius = "";
    private String comment = "";

    public int getToolNo() {
        return toolNo;
    }

    public void setToolNo(int toolNo) {
        this.toolNo = toolNo;
    }

    public int getPlaceNo() {
        return placeNo;
    }

    public void setPlaceNo(int placeNo) {
        this.placeNo = placeNo;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
