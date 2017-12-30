/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infotech.aquaThor.model;

import com.infotech.aquaThor.interfaces.IField;
import com.infotech.aquaThor.interfaces.IRenderer;
import com.infotech.aquaThor.view.render.SimpleConsoleRenderer;

/**
 *
 * @author alegerd
 */
public class Application extends Thread{

    Model model;
    IRenderer render;
    IField field;
    FoodCreator fc;
    
    public Application(Model model){
        this.model = model;
        field = model.getField();
        render = new SimpleConsoleRenderer(field);
        fc = new FoodCreator(field);
    }
    
    @Override
    public void run() {
       
        Thread ren = (Thread)render;
        Thread foodCreator = (Thread)fc;
        
        foodCreator.start();
        ren.start();
        
        while(true){
            if(!Thread.interrupted()){
                try{
                    model.bypassAllElements();
                    synchronized(this.field){
                        field = model.getField();
                    }
                    Thread.sleep(1000);
                }catch(Exception e){
                    
                }
            }
            else
            {
                ren.interrupt();
            };
        }
    }  
}
