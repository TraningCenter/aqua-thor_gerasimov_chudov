/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infotech.aquaThor.model.entities;

import com.infotech.aquaThor.interfaces.IStream;
import com.infotech.aquaThor.model.utils.Orientation;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author alegerd
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class Stream implements IStream{
    
    @XmlElement(name="start_coord")
    private Integer startPos;
    
    @XmlElement(name="finish_coord")
    private Integer finishPos;
    
    @XmlElement(name="stream_speed")
    private Integer speed;
    
    @XmlAttribute(name="orientation")
    private Orientation orientation;

    public Stream() {
    }

    public Stream(Integer startPos, Integer finishPos, Integer speed) {
        this.startPos = startPos;
        this.finishPos = finishPos;
        this.speed = speed;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    } 
 
    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }
   
    public Integer getStartPos() {
        return startPos;
    }

    public void setStartPos(Integer startPos) {
        this.startPos = startPos;
    }

    public Integer getFinishPos() {
        return finishPos;
    }

    public void setFinishPos(Integer finishPos) {
        this.finishPos = finishPos;
    }
    
    @Override
    public String toString(){
        String result = new String();
        result += "Stream";
        result += "\nOrientation - ";
        if(this.orientation == Orientation.HORIZONTAL){
            result += "horizontal";
        }
        else{
            result += "vertical";
        }
        result += "\nStart - " + this.startPos;
        result += "\nFinish - " + this.finishPos;
        return result;
    }
}
