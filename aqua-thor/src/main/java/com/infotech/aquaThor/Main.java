/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infotech.aquaThor;

import com.infotech.aquaThor.model.utils.Config;
import com.infotech.aquaThor.view.InputMenu;

import java.io.IOException;

/**
 *
 * @author alegerd
 */
public class Main {
    public static void main(String[] args) throws IOException {
        /*Model model;
        IParser dom = new StaxParser();
        Application life;
        
        try{
            model = dom.parse("input.xml");
            life = new Application(model);
            life.start();                 // красивая строчка :)
        }catch(Exception e){
            e.printStackTrace();
        }*/
        Config.setDelay(1500);
        InputMenu inputMenu = new InputMenu();
        inputMenu.start();
    }
}
