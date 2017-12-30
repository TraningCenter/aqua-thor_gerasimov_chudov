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

/**
 *
 * @author alegerd
 */

public class Shark extends Element implements IFish{

    Integer liveTime;
    Integer liveTimeWithoutFood;
    Integer speed;
    Integer senseRadius;
    List<IObserver> observers = new ArrayList<>();

    public Shark() {
        
    }

    public Shark(Integer liveTime, Integer liveTimeWithoutFood, Integer speed, Integer senseRadius) {
        this.liveTime = liveTime;
        this.liveTimeWithoutFood = liveTimeWithoutFood;
        this.speed = speed;
        this.senseRadius = senseRadius;
    }
    
    public Tuple move(List<Cell> fieldOfView){
        Tuple nextCell = coords;

        if(fieldOfView != null && fieldOfView.size() > 0){
            Cell selfCell = fieldOfView.get(0);
            
            for(Cell cell : fieldOfView){
                if(cell.getTempCoords().x == senseRadius && cell.getTempCoords().y == senseRadius){
                    selfCell = cell;          // нашли клетку в которой стоим
                }
            }
            
            Cell food = findFish(selfCell, fieldOfView);
            List<Cell> possibleCells = getNearbyCells(selfCell.getTempCoords(), fieldOfView);
            
            if(food != null){
                System.out.println("[food is not empty]");
                Cell next = moveToFromObject(selfCell, food, possibleCells, true);
                checkCell(next);
                nextCell = next.getCoords();
            }
            else{
                System.out.println("[food is empty]");
            }
        }
        return nextCell;
    }
    
    private List<Cell> getNearbyCells(Tuple self, List<Cell> fieldOfView){
        List<Cell> result = new ArrayList<>();
        for(Cell cell : fieldOfView){
            Integer x = cell.getTempCoords().x;
            Integer y = cell.getTempCoords().y;
            Integer selfx = self.x;
            Integer selfy = self.y;
            
            if((Math.abs(x - selfx) <= 1) 
                    && (Math.abs(y - selfy) <= 1) 
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
        for(IObserver obs : observers){
            obs.fishEatten(fishCell);
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
