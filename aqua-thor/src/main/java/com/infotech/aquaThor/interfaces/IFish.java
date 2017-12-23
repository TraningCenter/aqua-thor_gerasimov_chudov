/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infotech.aquaThor.interfaces;

/**
 *
 * @author alegerd
 */
public interface IFish {
    
    public int getLiveTime();

    public void setLiveTime(int liveTime);

    public int getLiveTimeWithoutFood();

    public void setLiveTimeWithoutFood(int liveTimeWithoutFood);

    public int getSpeed();

    public void setSpeed(int speed);

    public int getSenseRadius();

    public void setSenseRadius(int senseRadius);
    
    String toString();
}
