/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infotech.aquaThor.view.parsers;

import com.infotech.aquaThor.interfaces.*;
import com.infotech.aquaThor.model.Model;
import com.infotech.aquaThor.model.entities.Fish;
import com.infotech.aquaThor.model.entities.Shark;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
/**
 *
 * @author alegerd
 */
public class domParser implements IParser{
    
    public Model parse() throws Exception{
        Model result = new Model();
        List<IFish> fishes = new ArrayList<>();
        List<IStream> streams = new ArrayList<>();
        
        InputStream input = ClassLoader.getSystemResourceAsStream("input.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	Document doc = dBuilder.parse(input);
        
        doc.getDocumentElement().normalize();
        
        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
        
        NodeList fishNodes = doc.getElementsByTagName("fish");
        
        
        for(int i = 0; i < fishNodes.getLength(); i++){
            Node fNode =  fishNodes.item(i);
            
            Element elem = (Element)fNode;
            
            if(fNode.getNodeType() == Node.ELEMENT_NODE){
                String fishType = elem.getAttribute("type");
                
                if(fishType.equals("victim")){
                    Fish fish = new Fish();
                    fish.setXCoord(Integer.parseInt(elem.getAttribute("x")));
                    fish.setYCoord(Integer.parseInt(elem.getAttribute("y")));
                    fish.setLiveTime(Integer.parseInt(elem.getElementsByTagName("live_time").item(0).getTextContent()));
                    fish.setLiveTimeWithoutFood(Integer.parseInt(elem.getElementsByTagName("live_time_without_food").item(0).getTextContent()));
                    fish.setSpeed(Integer.parseInt(elem.getElementsByTagName("speed").item(0).getTextContent()));
                    fish.setSenseRadius(Integer.parseInt(elem.getElementsByTagName("sense_radius").item(0).getTextContent()));
                    fishes.add(fish);
                }
                else if(fishType.equals("predator")){
                    Shark fish = new Shark();
                    fish.setXCoord(Integer.parseInt(elem.getAttribute("x")));
                    fish.setYCoord(Integer.parseInt(elem.getAttribute("y")));
                    fish.setLiveTime(Integer.parseInt(elem.getElementsByTagName("live_time").item(0).getTextContent()));
                    fish.setLiveTimeWithoutFood(Integer.parseInt(elem.getElementsByTagName("live_time_without_food").item(0).getTextContent()));
                    fish.setSpeed(Integer.parseInt(elem.getElementsByTagName("speed").item(0).getTextContent()));
                    fish.setSenseRadius(Integer.parseInt(elem.getElementsByTagName("sense_radius").item(0).getTextContent()));
                    fishes.add(fish);
                }
            }
        }
        
        result.setFishes(fishes);
        return result;
    }
}
