/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infotech.aquaThor.database;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="step")
@XmlAccessorType(XmlAccessType.FIELD)
public class Step implements IStep{
    
    @XmlAttribute(name="number")
    private int step;
    
    @XmlElement(name="fish-count")
    private int fishCount;
    
    @XmlElement(name="shark-count")
    private int sharkCount;
    
    public Step(){}
    
    public Step(int step, int fishCount, int sharkCount){
        this.step = step;
        this.fishCount = fishCount;
        this.sharkCount = sharkCount;
    }

    @Override
    public int getStep() {
        return step;
    }

    @Override
    public int getFishCount() {
        return fishCount;
    }

    @Override
    public int getSharkCount() {
        return sharkCount;
    }
}
