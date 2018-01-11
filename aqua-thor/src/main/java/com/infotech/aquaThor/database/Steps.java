/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infotech.aquaThor.database;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="steps")
@XmlAccessorType(XmlAccessType.FIELD)
public class Steps {
    
    @XmlElementWrapper(name="steps")
    @XmlElement(name="steps", type=Step.class)
    private ArrayList<IStep> steps = new ArrayList<>();
    
    public ArrayList<IStep> getStepsList(){
        return steps;
    }
    
    public void setStepsList(ArrayList<IStep> steps){
        this.steps = steps;
    }
}
