/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infotech.aquaThor;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.infotech.aquaThor.interfaces.*;
import com.infotech.aquaThor.model.Application;
import com.infotech.aquaThor.model.Model;
import com.infotech.aquaThor.view.InputMenu;
import com.infotech.aquaThor.view.parsers.JaxbParser;
import com.infotech.aquaThor.view.parsers.ParserProp;
import com.infotech.aquaThor.view.parsers.SaxParser;
import com.infotech.aquaThor.view.parsers.StaxParser;
import com.infotech.aquaThor.view.parsers.domParser;
import com.infotech.aquaThor.view.render.SimpleConsoleRenderer;
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
        
        InputMenu inputMenu = new InputMenu();
        inputMenu.start();
    }
}
