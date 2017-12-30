/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infotech.aquaThor;

import com.infotech.aquaThor.interfaces.*;
import com.infotech.aquaThor.model.Application;
import com.infotech.aquaThor.model.Model;
import com.infotech.aquaThor.view.parsers.JaxbParser;
import com.infotech.aquaThor.view.parsers.SaxParser;
import com.infotech.aquaThor.view.parsers.StaxParser;
import com.infotech.aquaThor.view.parsers.domParser;
import com.infotech.aquaThor.view.render.SimpleConsoleRenderer;

/**
 *
 * @author alegerd
 */
public class Main {
    public static void main(String[] args){
        Model model;
        IParser dom = new domParser();
        Application life;
        
        try{
            model = dom.parse("input.xml");
            life = new Application(model);
            life.start();                 // красивая строчка :)
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
