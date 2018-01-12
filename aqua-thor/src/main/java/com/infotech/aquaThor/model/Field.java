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
/**
 *
 * @author alegerd
 */

public class Field implements IField{
    
    private Integer width;
    private Integer height;
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
    
    public void setCell(Tuple coord, CellContent value){
        ocean[(int)coord.y][(int)coord.x] = new Cell(coord, value);
    }
  
    private void makeOcean(){
        this.ocean = new Cell[height][width];
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                ocean[j][i] = new Cell(new Tuple(i,j), CellContent.EMPTY);
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
