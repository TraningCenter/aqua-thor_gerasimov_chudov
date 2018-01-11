/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infotech.aquaThor.database;

import com.infotech.aquaThor.model.Model;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="database")
@XmlAccessorType(XmlAccessType.FIELD)
public class Database {
    
    @XmlElement(name="steps")
    private Steps steps;
    
    public Database(){
        steps = new Steps();
    }

    public Steps getSteps() {
        return steps;
    }
}
