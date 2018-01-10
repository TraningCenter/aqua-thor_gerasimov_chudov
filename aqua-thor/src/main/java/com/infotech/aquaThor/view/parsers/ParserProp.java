/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infotech.aquaThor.view.parsers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ParserProp {
    
    private final static String PATH = "src/main/resources/parser.properties";
    private final static String COMMENT = " Select parser type form list:\n"
                                                + "# DOM;\n"
                                                + "# SAX;\n"
                                                + "# StAX;\n"
                                                + "# JAXB.";    
    
    private Properties props;
    private File file;
    private FileInputStream in;
    private FileOutputStream out;
    private String parser;
    
    public ParserProp(){
        try{
            props = new Properties();
            file = new File(PATH);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    public String getParser(){
        try{
            in = new FileInputStream(file);
            props.load(in);
            parser = props.getProperty("Parser");
            in.close();
        } catch (IOException ex){
            ex.printStackTrace();
        }
        return parser;
    }
    
    public void setParser(String currentParser){
        try{
            out = new FileOutputStream(file);
            props.setProperty("Parser", currentParser);
            props.store(out, COMMENT);
            out.close();
            parser = currentParser;
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
