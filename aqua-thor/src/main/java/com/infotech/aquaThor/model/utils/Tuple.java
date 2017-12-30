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
public class Tuple{
    public Integer x;
    public Integer y;
    
    public Tuple(Integer x, Integer y){
        this.x = x;
        this.y = y;
    }
    
    public Tuple(){
        
    }
    
    public boolean equals(Tuple other){
        return (this.x.equals(other.x) && this.y.equals(other.y));
    }
}
