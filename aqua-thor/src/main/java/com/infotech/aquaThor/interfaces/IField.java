/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infotech.aquaThor.interfaces;

/**
 *
 * @author alegerd
 */
public interface IField {
     Integer getWidth();

     void setWidth(Integer width);
    
     Integer getHeight();

     void setHeight(Integer height);

     boolean isClosed();

     void setClosed(boolean closed);

     Integer[][] getOcean();

     void setOcean(Integer[][] ocean);
    
    String toString();
}
