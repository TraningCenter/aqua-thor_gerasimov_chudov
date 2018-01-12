/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infotech.aquaThor.view.parsers;

import com.infotech.aquaThor.interfaces.*;
import com.infotech.aquaThor.model.*;
import com.infotech.aquaThor.model.entities.Fish;
import com.infotech.aquaThor.model.entities.Shark;
import com.infotech.aquaThor.model.entities.Stream;
import com.infotech.aquaThor.model.utils.Orientation;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author alegerd
 */
class MyHandler extends DefaultHandler{
                                
            List<IFish> fishes = new ArrayList<>();
            List<IStream> streams = new ArrayList<>();
            List<IField> fields = new ArrayList<>();
            Integer fieldWidth;
            Integer fieldHeight;
            IFish fish;
            IField field;
            IStream stream;
            
            boolean bClosed = false;
            boolean bStreamSpeed = false;
            boolean bStartCoord = false;
            boolean bFinishCoord = false;
            boolean bLiveTime = false;
            boolean bLiveTimeWithoutFood = false;
            boolean bFishSpeed = false;
            boolean bRepr = false;
            boolean bSenseRadius = false;
                    
            @Override
            public void startElement(String uri, String localName,String qName,
                Attributes attributes) throws SAXException {
                
                if(qName.equalsIgnoreCase("field")){
                  
                    fieldWidth = Integer.parseInt(attributes.getValue("width"));
                    fieldHeight = Integer.parseInt(attributes.getValue("height"));
                    System.out.println(fieldWidth);
                    this.field = new Field(fieldWidth, fieldHeight);
                }
                else if(qName.equalsIgnoreCase("stream")){
                    String type = attributes.getValue("orientation");
                    if(type.equals("horizontal")){
                        Stream stream = new Stream();
                        stream.setOrientation(Orientation.HORIZONTAL);
                        this.stream = stream;
                    }
                    else if(type.equals("vertical")){
                        Stream stream = new Stream();
                        stream.setOrientation(Orientation.VERTICAL);
                        this.stream = stream;
                    }
                }
                else if(qName.equalsIgnoreCase("fish")){
                    String type = attributes.getValue("type");
                    if(type.equals("victim")){
                        Fish fish = new Fish();
                        fish.setXCoord(Integer.parseInt(attributes.getValue("x")));
                        fish.setYCoord(Integer.parseInt(attributes.getValue("y")));
                        this.fish = fish;
                    }
                    else if(type.equals("predator")){
                        Shark fish = new Shark();
                        fish.setXCoord(Integer.parseInt(attributes.getValue("x")));
                        fish.setYCoord(Integer.parseInt(attributes.getValue("y")));
                        this.fish = fish;
                    }
                }
                else if(qName.equalsIgnoreCase("closed")){
                    bClosed = true;
                }
                else if(qName.equalsIgnoreCase("stream_speed")){
                    bStreamSpeed = true;
                }
                else if(qName.equalsIgnoreCase("start_coord")){
                    bStartCoord = true;
                }
                else if(qName.equalsIgnoreCase("finish_coord")){
                    bFinishCoord = true;
                }
                else if(qName.equalsIgnoreCase("live_time")){
                    bLiveTime = true;
                }
                else if(qName.equalsIgnoreCase("live_time_without_food")){
                    bLiveTimeWithoutFood = true;
                }
                else if(qName.equalsIgnoreCase("speed")){
                    bFishSpeed = true;
                }
                else if(qName.equalsIgnoreCase("reproduction")){
                    bRepr = true;
                }
                else if(qName.equalsIgnoreCase("sense_radius")){
                    bSenseRadius = true;
                }
            }
            
            @Override
            public void endElement(String uri, String localName,
		String qName) throws SAXException {
                
                if(qName.equalsIgnoreCase("fish")){
                    fishes.add(fish);
                }
                if(qName.equalsIgnoreCase("stream")){
                    streams.add(stream);
                }
                if(qName.equalsIgnoreCase("field")){
                    fields.add(field);
                }

            }

            @Override
            public void characters(char ch[], int start, int length) throws SAXException {
                if(bClosed){
                    field.setClosed(Boolean.parseBoolean(new String(ch,start,length))); 
                    bClosed = false;
                }
                if(bStreamSpeed){
                    stream.setSpeed(Integer.parseInt(new String(ch,start,length)));
                    bStreamSpeed = false;
                }
                if(bStartCoord){
                    stream.setStartPos(Integer.parseInt(new String(ch,start,length)));
                    bStartCoord = false;
                }
                if(bFinishCoord){
                    stream.setFinishPos(Integer.parseInt(new String(ch,start,length)));
                    bFinishCoord = false;
                }
                if(bLiveTime){
                    fish.setLiveTime(Integer.parseInt(new String(ch,start,length)));
                    bLiveTime = false;
                }
               if(bRepr){
                    fish.setReproduction(Integer.parseInt(new String(ch,start,length)));
                    bRepr = false;
                }
                if(bLiveTimeWithoutFood){
                    fish.setLiveTimeWithoutFood(Integer.parseInt(new String(ch,start,length)));
                    bLiveTimeWithoutFood = false;
                }
                if(bFishSpeed){
                    fish.setSpeed(Integer.parseInt(new String(ch,start,length)));
                    bFishSpeed = false;
                }
                if(bSenseRadius){
                    fish.setSenseRadius(Integer.parseInt(new String(ch,start,length)));
                    bSenseRadius = false;
                }
            }
            
            public List<IFish> getFishList(){
                return fishes;
            }
            
            public List<IStream> getStreamList(){
                return streams;
            }
            
            public List<IField> getFieldList(){
                return fields;
            }
}

public class SaxParser implements IParser{
    public Model parse(String file) throws Exception{
        Model result;
        
        SAXParserFactory factory = SAXParserFactory.newInstance();
	SAXParser saxParser = factory.newSAXParser();
        
        MyHandler handler = new MyHandler();
        InputStream stream = new FileInputStream(file);
        saxParser.parse(stream, handler);
        IField field = handler.getFieldList().get(0);
        result = new Model(field, handler.getFishList(), handler.getStreamList());
        return result;
    }
}
