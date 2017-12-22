/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infotech.aquaThor.model.utils;

/**
 *
 * @author alegerd
 */
public abstract class Element {
    protected Tuple<Integer,Integer> coords = new Tuple<>();
    
    public void setXCoord(Integer x){
        coords.x = x;
    }
    
    public void setYCoord(Integer y){
        coords.y = y;
    }
    
    public Integer getXCoord(){
        return coords.x;
    }
    
    public Integer getYCoord(){
        return coords.y;
    }
}
