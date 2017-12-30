/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infotech.aquaThor.model;

import com.infotech.aquaThor.interfaces.IField;
import com.infotech.aquaThor.model.utils.Cell;
import com.infotech.aquaThor.model.utils.CellContent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alegerd
 */
public class FoodCreator extends Thread{
    
    private IField field;
    
    public FoodCreator(IField field){
        this.field = field;
    }
    
    @Override
    public void run(){
        while(true){
            if(!Thread.interrupted()){
                synchronized(this.field){
                    createFood();
                }
                try{
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    
                }
            }
        }
    }
    
    private void createFood(){
        List<Cell> emptyCells = new ArrayList();
        for(Cell cell : field.getAllCells()){
            if(cell.getContent() == CellContent.EMPTY){
                emptyCells.add(cell);
            }
        }
        Integer randomIndex = null;
        Random rnd = new Random();
        randomIndex = rnd.nextInt(emptyCells.size());
        if(randomIndex != null){
            Cell cell = emptyCells.get(randomIndex);
            cell.setContent(CellContent.FOOD);
        }
    }
}
