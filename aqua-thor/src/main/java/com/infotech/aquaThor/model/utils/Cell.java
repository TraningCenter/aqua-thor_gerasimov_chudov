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
public class Cell {
    private Tuple coords;
    private Tuple tempCoords;
    private CellContent content;

    public Cell(Tuple coords, CellContent content) {
        this.coords = coords;
        this.content = content;
    }

    public Tuple getCoords() {
        return coords;
    }

    public void setCoords(Tuple coords) {
        this.coords = coords;
    }

    public CellContent getContent() {
        return content;
    }

    public void setContent(CellContent content) {
        this.content = content;
    }

    public Tuple getTempCoords() {
        return tempCoords;
    }

    public void setTempCoords(Tuple tempCoords) {
        this.tempCoords = tempCoords;
    }
     
}
