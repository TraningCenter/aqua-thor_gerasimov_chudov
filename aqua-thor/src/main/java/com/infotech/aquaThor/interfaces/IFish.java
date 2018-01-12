/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infotech.aquaThor.interfaces;

import com.infotech.aquaThor.model.utils.Cell;
import com.infotech.aquaThor.model.utils.Tuple;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;


/**
 *
 * @author alegerd
 */

@XmlAccessorType(XmlAccessType.FIELD)
public interface IFish {
   
    public Integer getAge();
    
    public Integer getStarvingTime();
    
    public void addObserver(IObserver observer);
    
    public Tuple move(List<Cell> fieldOfview, boolean closedField);
    
    public Tuple getCoordinates();
    
    public void setCoordinates(Tuple coord);
    
    public int getLiveTime();

    public void setLiveTime(int liveTime);

    public int getLiveTimeWithoutFood();

    public void setLiveTimeWithoutFood(int liveTimeWithoutFood);

    public int getSpeed();

    public void setReproduction(int time);
    
    public int getReproduction();
    
    public void setSpeed(int speed);

    public int getSenseRadius();
    
    public void checkCell(Cell cell);

    public void setSenseRadius(int senseRadius);
    
    String toString();
}
