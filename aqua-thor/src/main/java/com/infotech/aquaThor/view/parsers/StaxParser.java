/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infotech.aquaThor.view.parsers;

import com.infotech.aquaThor.interfaces.IField;
import com.infotech.aquaThor.interfaces.IFish;
import com.infotech.aquaThor.interfaces.IParser;
import com.infotech.aquaThor.interfaces.IStream;
import com.infotech.aquaThor.model.Field;
import com.infotech.aquaThor.model.Model;
import com.infotech.aquaThor.model.entities.Fish;
import com.infotech.aquaThor.model.entities.Shark;
import com.infotech.aquaThor.model.entities.Stream;
import com.infotech.aquaThor.model.utils.Orientation;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;


public class StaxParser implements IParser{
    
    IField field;
    IStream stream;
    IFish someFish;
    
    public Model parse() throws Exception {
        Model model;
        List<IFish> fishes = new ArrayList<>();
        List<IStream> streams = new ArrayList<>();
        
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLEventReader eventReader = factory.createXMLEventReader(ClassLoader.getSystemResourceAsStream("input.xml"));
        
        while (eventReader.hasNext()){
            XMLEvent xmlEvent = eventReader.nextEvent();
            
            switch (xmlEvent.getEventType()){
                case XMLStreamConstants.START_ELEMENT:
                    StartElement startElement = xmlEvent.asStartElement();
                    
                    switch (startElement.getName().getLocalPart()){
                        case "field":
                            int width = Integer.parseInt(startElement.getAttributeByName(new QName("width")).getValue());
                            int height = Integer.parseInt(startElement.getAttributeByName(new QName("height")).getValue());
                            field = new Field(width, height);
                            break;
                            
                        case "closed":
                            xmlEvent = eventReader.nextEvent();
                            field.setClosed(Boolean.valueOf(xmlEvent.asCharacters().getData()));
                            break;
                            
                        case "stream":
                            Stream stream = new Stream();
                            String orientation = startElement.getAttributeByName(new QName("orientation")).getValue();
                            if (orientation.equals("horizontal")){
                                stream.setOrientation(Orientation.HORIZONTAL);
                                this.stream = stream;
                            }
                            else if (orientation.equals("vertical")){
                                stream.setOrientation(Orientation.VERTICAL);
                                this.stream = stream;
                            }
                            break;
                            
                        case "stream_speed":
                            xmlEvent = eventReader.nextEvent();
                            this.stream.setSpeed(Integer.parseInt(xmlEvent.asCharacters().getData()));
                            break;
                            
                        case "start_coord":
                            xmlEvent = eventReader.nextEvent();
                            this.stream.setStartPos(Integer.parseInt(xmlEvent.asCharacters().getData()));
                            break;
                         
                        case "finish_coord":
                            xmlEvent = eventReader.nextEvent();
                            this.stream.setFinishPos(Integer.parseInt(xmlEvent.asCharacters().getData()));
                            break;
                            
                        case "fish":
                            Attribute attribute = startElement.getAttributeByName(new QName("type"));
                            if (attribute.getValue().equals("victim")){
                                Fish fish = new Fish();  
                                fish.setXCoord(Integer.parseInt(startElement.getAttributeByName(new QName("x")).getValue()));
                                fish.setYCoord(Integer.parseInt(startElement.getAttributeByName(new QName("y")).getValue()));
                                someFish = fish;
                            } 
                            else if (attribute.getValue().equals("predator")){
                                Shark shark = new Shark();
                                shark.setXCoord(Integer.parseInt(startElement.getAttributeByName(new QName("x")).getValue()));
                                shark.setYCoord(Integer.parseInt(startElement.getAttributeByName(new QName("y")).getValue()));
                                someFish = shark;
                            }
                            break;
                        
                        case "live_time":
                            xmlEvent = eventReader.nextEvent();
                            someFish.setLiveTime(Integer.parseInt(xmlEvent.asCharacters().getData()));
                            break;
                        
                        case "live_time_without_food":
                            xmlEvent = eventReader.nextEvent();
                            someFish.setLiveTimeWithoutFood(Integer.parseInt(xmlEvent.asCharacters().getData()));
                            break;
                        
                        case "speed":
                            xmlEvent = eventReader.nextEvent();
                            someFish.setSpeed(Integer.parseInt(xmlEvent.asCharacters().getData()));
                            break;
                        
                        case "sense_radius":
                            xmlEvent = eventReader.nextEvent();
                            someFish.setSenseRadius(Integer.parseInt(xmlEvent.asCharacters().getData()));
                            break;
                            
                    }
                    break;
                
                case XMLStreamConstants.END_ELEMENT:
                    EndElement endElement = xmlEvent.asEndElement();
                    
                    switch (endElement.getName().getLocalPart()){
                        case "stream":
                            streams.add(this.stream);
                            break;
                            
                        case "fish":
                            fishes.add(someFish);
                            break;
                    }
                    break;
            }
            
        }
        
        model = new Model(field, fishes, streams);
        return model;
    }
}
