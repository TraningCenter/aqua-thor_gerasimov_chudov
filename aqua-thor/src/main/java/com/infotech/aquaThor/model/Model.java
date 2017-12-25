/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infotech.aquaThor.model;

import com.infotech.aquaThor.interfaces.*;
import com.infotech.aquaThor.model.entities.Fish;
import com.infotech.aquaThor.model.entities.Shark;
import com.infotech.aquaThor.model.entities.Stream;
import com.infotech.aquaThor.view.parsers.FishAdapter;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author alegerd
 */

@XmlRootElement(name="input")
@XmlAccessorType(XmlAccessType.FIELD)
public class Model {
    
    @XmlElement(name="field", type=Field.class)
    private IField field;
    
    @XmlElementWrapper(name="fishes")
    @XmlElement(name="fish")
    @XmlJavaTypeAdapter(FishAdapter.class)
    private List<IFish> fishes = new ArrayList<>();
    
    @XmlElementWrapper(name="streams")
    @XmlElement(name="stream", type=Stream.class)
    private List<IStream> streams = new ArrayList<>();

    public Model(){}
    
    public Model(IField field, List<IFish> fishes, List<IStream> streams) {
        this.fishes = fishes;
        this.streams = streams;
        this.field = field;
    }

    public List<IFish> getFishes() {
        return fishes;
    }

    public void setFishes(List<IFish> fishes) {
        this.fishes = fishes;
    }

    public List<IStream> getStreams() {
        return streams;
    }

    public void setStreams(List<IStream> streams) {
        this.streams = streams;
    }
    
    public IField getField(){
        return this.field;
    }
    
    public void setField(IField field){
        this.field = field;
    }
    
    public String toString(){
        String result = new String();
        result += "Model";
        result += "\n------------------";
        result += "\n" + field.toString() + "\n";
        for(IFish fish : fishes){
            result += "\n" + fish.toString() +"\n";
        }
        for(IStream stream : streams){
            result += "\n" + stream.toString() + "\n";
        }
        result += "\n------------------";
        return result;
    }
}
