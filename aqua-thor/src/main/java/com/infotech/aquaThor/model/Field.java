/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infotech.aquaThor.model;
import com.infotech.aquaThor.interfaces.*;

/**
 *
 * @author alegerd
 */
public class Field implements IField{
    
    private Integer width;
    private Integer height;
    private boolean closed;
    private Integer[][] ocean;
    
    public Field(Integer width, Integer height, boolean closed) {
        this.width = width;
        this.height = height;
        this.closed = closed;
        makeOcean();
    }
    
    public Field(Integer width, Integer height) {
        this.width = width;
        this.height = height;
        makeOcean();
    }
        
    private void makeOcean(){
        this.ocean = new Integer[width][height];
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public Integer[][] getOcean() {
        return ocean;
    }

    public void setOcean(Integer[][] ocean) {
        this.ocean = ocean;
    }
    
    public String toString(){
        String result = new String();
        result += "Field";
        result += "\nWidth - " + this.width;
        result += "\nHeight - " + this.height; 
        return result;
    }
}
