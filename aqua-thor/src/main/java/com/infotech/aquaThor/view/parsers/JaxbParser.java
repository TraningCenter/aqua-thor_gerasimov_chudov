/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infotech.aquaThor.view.parsers;

import com.infotech.aquaThor.interfaces.IParser;
import com.infotech.aquaThor.model.Model;
import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;


public class JaxbParser implements IParser{

    public Model parse(String input) throws Exception {
        File file = new File(getClass().getClassLoader().getResource(input).getFile());
        JAXBContext jContext = JAXBContext.newInstance(Model.class);
        Unmarshaller unmarshaller = jContext.createUnmarshaller();
        Model model = (Model) unmarshaller.unmarshal(file);
        return model;
    }
    
}
