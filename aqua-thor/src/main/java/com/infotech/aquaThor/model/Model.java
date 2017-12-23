/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infotech.aquaThor.model;

import com.infotech.aquaThor.interfaces.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alegerd
 */
public class Model {
    private IField field;
    private List<IFish> fishes = new ArrayList<>();
    private List<IStream> streams = new ArrayList<>();

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
