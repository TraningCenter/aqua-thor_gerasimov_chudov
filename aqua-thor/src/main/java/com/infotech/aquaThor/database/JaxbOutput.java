/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infotech.aquaThor.database;

import java.io.File;
import java.io.FileOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class JaxbOutput {
    
    private String path;
    
    public JaxbOutput(String path){
        this.path = path;
    }
    
    public void writeDatabase(Database database){
        try{
            File file = new File(path);
            JAXBContext jContext = JAXBContext.newInstance(Database.class);
            Marshaller marshaller = jContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            Database db = database;
            marshaller.marshal(db, new FileOutputStream(file));
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    public Database readDatabase() throws Exception{
        File file = new File(path);
        JAXBContext jContext = JAXBContext.newInstance(Database.class);
        Unmarshaller unmarshaller = jContext.createUnmarshaller();
        Database db = (Database) unmarshaller.unmarshal(file);
        return db;
    }
}
