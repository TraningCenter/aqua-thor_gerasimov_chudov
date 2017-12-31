/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infotech.aquaThor.model.entities;

import com.infotech.aquaThor.interfaces.*;
import com.infotech.aquaThor.model.utils.Cell;
import com.infotech.aquaThor.model.utils.CellContent;
import com.infotech.aquaThor.model.utils.Element;
import com.infotech.aquaThor.model.utils.Tuple;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author alegerd
 */

public class Shark extends Element implements IFish{

    Integer liveTime;
    Integer liveTimeWithoutFood;
    Integer speed;
    Integer age;
    Integer starvingTime;
    Integer senseRadius;
    Integer reprTime;
    Integer reprCount;
    
    List<IObserver> observers = new ArrayList<>();
    Random rnd;

    public Shark() {
        this.age = 0;
        this.starvingTime = 0;
        this.reprTime = 20;
        this.reprCount = 0;
        rnd = new Random();
    }

    public Shark(Integer liveTime, Integer liveTimeWithoutFood, Integer speed, Integer senseRadius) {
        this.liveTime = liveTime;
        this.liveTimeWithoutFood = liveTimeWithoutFood;
        this.speed = speed;
        this.senseRadius = senseRadius;
        this.age = 0;
        this.starvingTime = 0;
        this.reprTime = 7;
        this.reprCount = 0;
        rnd = new Random();
    }
    
    public Tuple move(List<Cell> fieldOfView, boolean closedField){
        Tuple nextCell = coords;
        Cell selfCell = null;

        if(fieldOfView != null && fieldOfView.size() > 0){
            selfCell = fieldOfView.get(0);
           
            
            if(!closedField)
                for(Cell cell : fieldOfView){
                    if(cell.getTempCoords().x == senseRadius && cell.getTempCoords().y == senseRadius){
                        selfCell = cell;          // нашли клетку в которой стоим
                    }
                }
            else{
                for(Cell cell : fieldOfView){
                    if(cell.getTempCoords().x == 0 && cell.getTempCoords().y == 0){
                        selfCell = cell;          // нашли клетку в которой стоим
                    }
                }
            }
            
            Cell food = findFish(selfCell, fieldOfView);
            List<Cell> possibleCells = getNearbyCells(selfCell.getTempCoords(), fieldOfView, speed);
            
            if(food != null){
                Cell next = moveToFromObject(selfCell, food, possibleCells, true);
                checkCell(next);
                nextCell = next.getCoords();
            }
            else{
                if(possibleCells.size() > 1){
                    Integer randomIndex = rnd.nextInt(possibleCells.size() - 1);
                    checkCell(possibleCells.get(randomIndex));
                    nextCell = possibleCells.get(randomIndex).getCoords();
                }
            }
            
            makeChildren(selfCell, fieldOfView);
            selfCell.setContent(CellContent.EMPTY);
        }
        age++;
        starvingTime++;
        CheckLiveTime(selfCell);
        
        return nextCell;
    }
    
    private void CheckLiveTime(Cell selfCell){
        if(age > liveTime 
                && starvingTime > liveTimeWithoutFood && selfCell != null){
            selfCell.setContent(CellContent.EMPTY);
        }
    }
    
    private List<Cell> getNearbyCells(Tuple self, List<Cell> fieldOfView, Integer radius){
        List<Cell> result = new ArrayList<>();
        for(Cell cell : fieldOfView){
            Integer x = cell.getTempCoords().x;
            Integer y = cell.getTempCoords().y;
            Integer selfx = self.x;
            Integer selfy = self.y;
            
            if((Math.abs(x - selfx) <= radius) 
                    && (Math.abs(y - selfy) <= radius) 
                    && cell.getContent() != CellContent.SHARK){
                result.add(cell);
            }
        }
        return result;
    }
    
    private Cell findFish(Cell self, List<Cell> fieldOfView){
        Cell foodCell = null;
        Double distance;
        Double currentDistance;
        
            distance = 1000.;
            for(Cell cell : fieldOfView){
                if(cell.getContent() == CellContent.FISH){
                    Integer x = cell.getTempCoords().x;
                    Integer y = cell.getTempCoords().y;
                    Integer selfx = self.getTempCoords().x;
                    Integer selfy = self.getTempCoords().y;
                    
                    currentDistance = Math.sqrt(Math.abs(x-selfx)*Math.abs(x-selfx) + Math.abs(y-selfy)*Math.abs(y-selfy));
                    if(currentDistance < distance){
                        distance = currentDistance;
                        foodCell = cell;
                    }
                }
            }
            
        return foodCell;
    }
    
    private Cell moveToFromObject(Cell self, Cell object, List<Cell> fieldOfView, boolean direction){
        Cell nextCell = self;
        Double distance;
        Double currentDistance;
        
        if(direction){           
            distance = 1000.;
            for(Cell cell : fieldOfView){
                Integer x = cell.getTempCoords().x;
                Integer y = cell.getTempCoords().y;
                Integer objx = object.getTempCoords().x;
                Integer objy = object.getTempCoords().y;
                
                currentDistance = Math.sqrt(Math.abs(x-objx)*Math.abs(x-objx) + Math.abs(y-objy)*Math.abs(y-objy));
                if(currentDistance < distance){
                    distance = currentDistance;
                    nextCell = cell;
                }
            }
        }
        else{
            distance = 0.;
            for(Cell cell : fieldOfView){
                Integer x = cell.getTempCoords().x;
                Integer y = cell.getTempCoords().y;
                Integer objx = object.getTempCoords().x;
                Integer objy = object.getTempCoords().y;
                
                currentDistance = Math.sqrt(Math.abs(x-objx)*Math.abs(x-objx) + Math.abs(y-objy)*Math.abs(y-objy));
                if(currentDistance > distance){
                    distance = currentDistance;
                    nextCell = cell;
                }
            }
        }
        
        return nextCell;
    }
    
    public void setCoordinates(Tuple coords){
        setCoords(coords);
    }
    
    private void checkCell(Cell cell){
        if(cell.getContent() == CellContent.FISH){
            eatFish(cell);
        }
    }
    
    private void eatFish(Cell fishCell){
        starvingTime = 0;
        for(IObserver obs : observers){
            obs.fishEatten(fishCell);
        }
    }
    
    private void makeChildren(Cell self, List<Cell> fieldOfView){
        List<Cell> emptyCells = getNearbyCells(self.getTempCoords(), fieldOfView, 1);
        reprCount++;
        
        if(reprCount == reprTime && emptyCells.size() > 1){
            reprCount = 0;
            IFish child = new Shark();
            child.setLiveTime(this.liveTime);
            child.setLiveTimeWithoutFood(this.liveTimeWithoutFood);
            child.setSenseRadius(this.senseRadius);
            child.setSpeed(this.speed);
            Integer next = rnd.nextInt(emptyCells.size() - 1);
            child.setCoordinates(emptyCells.get(next).getCoords());
            self.setContent(CellContent.EMPTY);
            
            for(IObserver obs : observers){
                obs.fishCreated(emptyCells.get(next), CellContent.SHARK, child);
            }
        }
    }
        
    public void addObserver(IObserver observer){
        observers.add(observer);
    }
    
    public Tuple getCoordinates(){
        return new Tuple(getXCoord(), getYCoord());
    }
    public int getLiveTime() {
        return liveTime;
    }

    public void setLiveTime(int liveTime) {
        this.liveTime = liveTime;
    }

    public int getLiveTimeWithoutFood() {
        return liveTimeWithoutFood;
    }

    public void setLiveTimeWithoutFood(int liveTimeWithoutFood) {
        this.liveTimeWithoutFood = liveTimeWithoutFood;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSenseRadius() {
        return senseRadius;
    }

    public void setSenseRadius(int senseRadius) {
        this.senseRadius = senseRadius;
    }
    
    public Integer getAge(){
        return this.age;
    }
    
    public Integer getStarvingTime(){
        return this.starvingTime;
    }
   
    @Override
    public String toString(){
        String result = new String();
        result += "Fish, predator";
        result += "\nLive Time : " + liveTime;
        result += "\nLive time without food : " + liveTimeWithoutFood;
        result += "\nSpeed : " + speed;
        result += "\nSense radius : " + senseRadius; 
        result += "\n X coord : " + coords.x;
        result += "\n Y coord : " + coords.y;
        return result;
    }
}
