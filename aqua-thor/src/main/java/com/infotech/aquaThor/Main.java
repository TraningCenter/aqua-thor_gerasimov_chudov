/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infotech.aquaThor;

import com.infotech.aquaThor.interfaces.*;
import com.infotech.aquaThor.model.Model;
import com.infotech.aquaThor.view.parsers.SaxParser;
import com.infotech.aquaThor.view.parsers.domParser;

/**
 *
 * @author alegerd
 */
public class Main {
    public static void main(String[] args){
        Model model;
        Model model2;
        IParser dom = new domParser();
        IParser sax = new SaxParser();
        try{
            model = dom.parse();
            model2 = sax.parse();
            System.out.println(model.toString());
            System.out.println(model2.toString());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
