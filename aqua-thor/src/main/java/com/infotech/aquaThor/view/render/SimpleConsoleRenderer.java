/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infotech.aquaThor.view.render;

import com.infotech.aquaThor.interfaces.IField;
import com.infotech.aquaThor.interfaces.IRenderer;
import com.infotech.aquaThor.model.Field;
import com.infotech.aquaThor.model.utils.Cell;
import com.infotech.aquaThor.model.utils.CellContent;

/**
 *
 * @author alegerd
 */
public class SimpleConsoleRenderer extends Thread implements IRenderer{

    private IField field = null;

    public SimpleConsoleRenderer(IField ocean) {
        this.field = ocean;
    }
        
    @Override
    public void run(){
        while(true){
            if(!Thread.interrupted()){
                draw();
            }
        }
    }
    
    @Override
    public void draw() {
        synchronized(this.field){
            sleep();
            System.out.printf("\033[H\033[2J");
            for(Cell[] row : field.getOcean()){
                for(Cell cell : row){
                    if(cell.getContent() == CellContent.EMPTY)
                        System.out.print("-");
                    else if(cell.getContent() == CellContent.FOOD)
                        System.out.print(1);
                    else if(cell.getContent() == CellContent.FISH)
                        System.out.print(2);
                    else if(cell.getContent() == CellContent.SHARK)
                        System.out.print(3);
                }
                System.out.println();
            }
        }
    }

    @Override
    public void writeMessage(String message) {
        
    }
    
    private void sleep(){
        try{
            Thread.sleep(100);
        }catch(InterruptedException e){
                
        }
    }
}
