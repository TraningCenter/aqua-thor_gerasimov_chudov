/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infotech.aquaThor.model;
import com.infotech.aquaThor.interfaces.*;
import com.infotech.aquaThor.model.utils.Cell;
import com.infotech.aquaThor.model.utils.CellContent;
import com.infotech.aquaThor.model.utils.Tuple;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alegerd
 */

@XmlRootElement(name="folder")
@XmlAccessorType(XmlAccessType.FIELD)
public class Field implements IField{
    
    @XmlAttribute(name="width")
    private Integer width;
    
    @XmlAttribute(name="height")
    private Integer height;
    
    @XmlElement(name="closed")
    private boolean closed;
    
    private Cell[][] ocean;
    
    public Field(){}
    
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
    
    public void setCell(Tuple cell, CellContent value){
        ocean[(int)cell.x][(int)cell.y] = new Cell(cell, value);
    }
  
    private void makeOcean(){
        this.ocean = new Cell[width][height];
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                ocean[i][j] = new Cell(new Tuple(i,j), CellContent.EMPTY);
            }
        }
    }
    
    public List<Cell> getAllCells(){
        List<Cell> allCells = new ArrayList<>();
        for(Cell[] row : ocean){
            for(Cell cell : row){
                allCells.add(cell);
            }
        }
        return allCells;
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

    public Cell[][] getOcean() {
        return ocean;
    }

    public void setOcean(Cell[][] ocean) {
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
