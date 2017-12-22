/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infotech.aquaThor.model.entities;

import com.infotech.aquaThor.interfaces.*;
import com.infotech.aquaThor.model.utils.Element;

/**
 *
 * @author alegerd
 */
public class Shark extends Element implements IFish{
    Integer liveTime;
    Integer liveTimeWithoutFood;
    Integer speed;
    Integer senseRadius;

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
