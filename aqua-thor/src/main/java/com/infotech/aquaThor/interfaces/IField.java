/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infotech.aquaThor.interfaces;

import com.infotech.aquaThor.model.utils.Cell;
import com.infotech.aquaThor.model.utils.CellContent;
import com.infotech.aquaThor.model.utils.Tuple;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;


/**
 *
 * @author alegerd
 */

@XmlAccessorType(XmlAccessType.FIELD)
public interface IField {
    
     public void setCell(Tuple cell, CellContent value);
     
     List<Cell> getAllCells();
     
     Integer getWidth();

     void setWidth(Integer width);
    
     Integer getHeight();

     void setHeight(Integer height);

     boolean isClosed();

     void setClosed(boolean closed);

     Cell[][] getOcean();

     void setOcean(Cell[][] ocean);
    
    String toString();
}
