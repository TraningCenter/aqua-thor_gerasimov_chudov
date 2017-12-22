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
}
