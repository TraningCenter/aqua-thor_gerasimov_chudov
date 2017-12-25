/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infotech.aquaThor.view.parsers;

import com.infotech.aquaThor.interfaces.IFish;
import com.infotech.aquaThor.model.entities.Fish;
import com.infotech.aquaThor.model.entities.Shark;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
public class FishAdapter extends XmlAdapter<FishAdapter.AdapterFish, IFish>{
    
    public static class AdapterFish{
        
        @XmlAttribute
        public String type;
        
        @XmlAttribute
        public int x;
        
        @XmlAttribute
        public int y;
        
        @XmlElement(name="live_time")
        public int liveTime;
    
        @XmlElement(name="live_time_without_food")
        public int liveTimeWithoutFood;
        
        @XmlElement(name="sense_radius")
        public int senseRadius;
        
        @XmlElement(name="speed")
        public int speed;
    }
    
    @Override
    public IFish unmarshal(AdapterFish v) throws Exception {
        
        if (v.type.equals("victim")){
            Fish fish = new Fish();
            fish.setXCoord(v.x);
            fish.setYCoord(v.y);
            fish.setLiveTime(v.liveTime);
            fish.setLiveTimeWithoutFood(v.liveTimeWithoutFood);
            fish.setSpeed(v.speed);
            fish.setSenseRadius(v.senseRadius);
            return fish;
        }
        else if (v.type.equals("predator")){
            Shark shark = new Shark();
            shark.setXCoord(v.x);
            shark.setYCoord(v.y);
            shark.setLiveTime(v.liveTime);
            shark.setLiveTimeWithoutFood(v.liveTimeWithoutFood);
            shark.setSpeed(v.speed);
            shark.setSenseRadius(v.senseRadius);
            return shark;
        }
        return null;
    }

    @Override
    public AdapterFish marshal(IFish v) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
