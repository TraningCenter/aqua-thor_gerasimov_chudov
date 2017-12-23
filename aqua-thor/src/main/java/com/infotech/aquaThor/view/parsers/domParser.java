/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infotech.aquaThor.view.parsers;

import com.infotech.aquaThor.interfaces.*;
import com.infotech.aquaThor.model.Field;
import com.infotech.aquaThor.model.Model;
import com.infotech.aquaThor.model.entities.Fish;
import com.infotech.aquaThor.model.entities.Shark;
import com.infotech.aquaThor.model.entities.Stream;
import com.infotech.aquaThor.model.utils.Orientation;
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
        Model result;
        List<IFish> fishes = new ArrayList<>();
        List<IStream> streams = new ArrayList<>();
        IField field;
        
        InputStream input = ClassLoader.getSystemResourceAsStream("input.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	Document doc = dBuilder.parse(input);
        
        doc.getDocumentElement().normalize();
        
        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
        
        Node fieldNode = doc.getElementsByTagName("field").item(0);
        NodeList fishNodes = doc.getElementsByTagName("fish");
        NodeList streamNodes = doc.getElementsByTagName("stream");
       
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
        
        for(int i = 0; i < streamNodes.getLength(); i++){
            Node sNode = streamNodes.item(i);
            
            Element elem = (Element)sNode;
            
            if(sNode.getNodeType() == Node.ELEMENT_NODE){
                Stream stream = new Stream();
                stream.setSpeed(Integer.parseInt(elem.getElementsByTagName("stream_speed").item(0).getTextContent()));
                stream.setOrientation(elem.getAttribute("orientation").equals("horizontal")? 
                                                                        Orientation.HORIZONTAL : 
                                                                        Orientation.VERTICAL);
                stream.setStartPos(Integer.parseInt(elem.getElementsByTagName("start_coord").item(0).getTextContent()));
                stream.setFinishPos(Integer.parseInt(elem.getElementsByTagName("finish_coord").item(0).getTextContent()));
                streams.add(stream);
            }
        }
        
        Element fieldElem = (Element)fieldNode;
        Integer width = Integer.parseInt(fieldElem.getAttribute("width"));
        Integer height = Integer.parseInt(fieldElem.getAttribute("height"));
        boolean closed = Boolean.parseBoolean(fieldElem.getElementsByTagName("closed").item(0).getTextContent());
        field = new Field(width, height, closed);
        
        result = new Model(field, fishes, streams);
        return result;
    }
}
