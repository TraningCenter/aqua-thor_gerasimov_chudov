/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infotech.aquaThor.database;

import com.infotech.aquaThor.interfaces.IFish;
import com.infotech.aquaThor.model.Model;
import com.infotech.aquaThor.model.entities.Fish;
import com.infotech.aquaThor.model.entities.Shark;
import java.util.ArrayList;

public class Statistics {
    
    private Model model;
    private Database database;
    private Step step;
    private ArrayList<IStep> steps;
    
    public Statistics(Model model){
        this.model = model;
        this.database = new Database();
        this.steps = database.getSteps().getStepsList();
    }
    
    public Database getDatabase(){
        return database;
    }
    
    public void generate(int stepNumber){
        ArrayList<Fish> fishes = new ArrayList<>();
        ArrayList<Shark> sharks = new ArrayList<>();
        
        for (IFish fish : model.getFishes()){
            if (fish.getClass().equals(Fish.class)){
                fishes.add((Fish)fish);
            }
            if (fish.getClass().equals(Shark.class)){
                sharks.add((Shark)fish);
            }
        }
        
        this.step = new Step(stepNumber, fishes.size(), sharks.size());
        database.getSteps().getStepsList().add(step);
    }
}
