/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infotech.aquaThor.model;

import com.infotech.aquaThor.interfaces.IField;
import com.infotech.aquaThor.interfaces.IRenderer;
import com.infotech.aquaThor.model.utils.Cell;
import com.infotech.aquaThor.model.utils.Config;

/**
 *
 * @author alegerd
 */
public class Application extends Thread{

    Model model;
    IField field;
    FoodCreator fc;
    
    public Application(Model model){
        this.model = model;
        field = model.getField();
        fc = new FoodCreator(field);
    }
    
    @Override
    public void run() {

        Thread foodCreator = (Thread)fc;
        
        foodCreator.start();
        
        while(true){
            if(!Thread.interrupted()){
                try{
                    synchronized(this.field){
                        System.out.printf("\033[H\033[2J");
                        Cell[][] ocean = this.field.getOcean();
                        for(int i = 0; i < field.getHeight(); i++){
                            for(int j = 0; j < field.getWidth(); j++){
                                Cell cell = ocean[i][j];
                                
                                if(null != cell.getContent())
                                    switch (cell.getContent()) {
                                    case EMPTY:
                                        System.out.print("-");
                                        break;
                                    case FOOD:
                                        System.out.print("*");
                                        break;
                                    case FISH:
                                        System.out.print("f");
                                        break;
                                    case SHARK:
                                        System.out.print("s");
                                        break;
                                    default:
                                        break;
                                }
                            }
                            System.out.print("\n");
                        }
                        Thread.sleep(Config.delay);
                        model.bypassAllElements();
                    }
                    Thread.sleep(Config.delay/2);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            else
            {
                foodCreator.interrupt();
            };
        }
    }  
}
