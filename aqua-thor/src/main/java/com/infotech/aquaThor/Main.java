/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infotech.aquaThor;

import com.infotech.aquaThor.interfaces.*;
import com.infotech.aquaThor.model.Model;
import com.infotech.aquaThor.view.parsers.JaxbParser;
import com.infotech.aquaThor.view.parsers.SaxParser;
import com.infotech.aquaThor.view.parsers.StaxParser;
import com.infotech.aquaThor.view.parsers.domParser;

/**
 *
 * @author alegerd
 */
public class Main {
    public static void main(String[] args){
        Model model;
        Model model2;
        Model model3;
        Model model4;
        IParser dom = new domParser();
        IParser sax = new SaxParser();
        IParser stax = new StaxParser();
        IParser jaxb = new JaxbParser();
        try{
            model = dom.parse();
            model2 = sax.parse();
            model3 = stax.parse();
            model4 = jaxb.parse();
            System.out.println(model.toString());
            System.out.println(model2.toString());
            System.out.println(model3.toString());
            System.out.println(model4.toString());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
