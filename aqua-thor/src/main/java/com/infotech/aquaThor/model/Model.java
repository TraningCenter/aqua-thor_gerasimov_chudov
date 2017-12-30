/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infotech.aquaThor.model;

import com.infotech.aquaThor.interfaces.*;
import com.infotech.aquaThor.model.entities.Fish;
import com.infotech.aquaThor.model.entities.Food;
import com.infotech.aquaThor.model.entities.Shark;
import com.infotech.aquaThor.model.entities.Stream;
import com.infotech.aquaThor.model.utils.Cell;
import com.infotech.aquaThor.model.utils.CellContent;
import com.infotech.aquaThor.model.utils.Tuple;
import com.infotech.aquaThor.view.parsers.FishAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author alegerd
 */

@XmlRootElement(name="input")
@XmlAccessorType(XmlAccessType.FIELD)
public class Model implements IObserver{
    
    @XmlElement(name="field", type=Field.class)
    private IField field;
    
    @XmlElementWrapper(name="fishes")
    @XmlElement(name="fish")
    @XmlJavaTypeAdapter(FishAdapter.class)
    private List<IFish> fishes = new ArrayList<>();
    
    @XmlElementWrapper(name="streams")
    @XmlElement(name="stream", type=Stream.class)
    private List<IStream> streams = new ArrayList<>();
    
    @XmlElementWrapper(name="foods")
    @XmlElement(name="food", type=Food.class)
    private List<IFood> food = new ArrayList<>();

    public Model(){}
    
    public Model(IField field, List<IFish> fishes, List<IStream> streams) {
        this.fishes = fishes;
        this.streams = streams;
        this.field = field;
        createOcean();
    }

    public void bypassAllElements() throws Exception{
        List<Cell> allCells = field.getAllCells();
        for(IFish fish : fishes){
            Tuple nextCellForFish = fish.move(generateFieldOfView(fish));
            for(Cell cell : allCells){
                if(cell.getCoords().equals(fish.getCoordinates()))
                {
                    cell.setContent(CellContent.EMPTY);
                }
            }
            for(Cell cell : allCells){
                if(cell.getCoords().equals(nextCellForFish))
                {
                    if(fish instanceof Fish)
                        cell.setContent(CellContent.FISH);
                    else if(fish instanceof Shark)
                        cell.setContent(CellContent.SHARK);
                    fish.setCoordinates(nextCellForFish);
                }
            }
        }
    }
    
    public void fishEatten(Cell cell){
        List<IFish> removingFishes = new ArrayList();
        for(IFish fish: fishes){
            if(fish.getCoordinates().equals(cell.getCoords())){
                removingFishes.add(fish);
                cell.setContent(CellContent.EMPTY);
            }
        }
        System.out.println("[SHARK ATE FISH]");
        fishes.removeAll(removingFishes);
    }  
    
    public void foodEatten(Cell cell){
        List<IFood> removingFood = new ArrayList();
        for(IFood f: food){
            if(f.getCoordinates().equals(cell.getCoords())){
                removingFood.add(f);
                cell.setContent(CellContent.EMPTY);
            }
        }
        System.out.println("[FISH ATE FOOD]");
        food.removeAll(removingFood);
    }
    
    private List<Cell> generateFieldOfView(IFish fish){
        List<Cell> fieldOfView = new ArrayList<>();
        Integer radius = fish.getSenseRadius();
        
        Integer x = fish.getCoordinates().x;
        Integer y = fish.getCoordinates().y;
        if(field.isClosed()){
            List<Cell> allCells = field.getAllCells();
            
            for(Cell cell : allCells){
                Integer cellx = cell.getCoords().x; 
                Integer celly = cell.getCoords().y; 
                if(Math.abs(cellx - x) <= radius 
                        && Math.abs(celly - y) <= radius){
                    cell.setTempCoords(new Tuple(x - cellx, y - celly));
                    fieldOfView.add(cell);
                }
            }
        }
        else{
            Cell[][] allCells = field.getOcean();
            
            Integer width = field.getWidth();
            Integer height = field.getHeight();
            Integer startX = (x - radius < 0)? (x - radius) + width : (x - radius); // 9
            Integer startY = (y - radius < 0)? (y - radius) + height : (y - radius); // 7
            
            for(int i = 0; i < radius*2 + 1; i++){
                for(int j = 0; j < radius*2 + 1; j++){
                    Integer curX = (startX + j) < width ? (startX + j) :  (startX + j) % width;
                    Integer curY = (startY + i) < height ? (startY + i) : (startY + i) % height; 
                    
                    Cell newCell = allCells[curY][curX];
                    newCell.setTempCoords(new Tuple(i,j));
                    fieldOfView.add(newCell);
                }
            }
            
        }
        return fieldOfView;
    }
    
    private void createOcean(){
        for(IFish fish : fishes){
            CellContent cellValue = CellContent.EMPTY;
            if(fish instanceof Fish){
                cellValue = CellContent.FISH;
            }
            else if(fish instanceof Shark){
                cellValue = CellContent.SHARK;
            }
            field.setCell(fish.getCoordinates(), cellValue);
        }
        setObserver();
    }
    
    private void setObserver(){
        for(IFish fish : fishes){
            fish.addObserver(this);
        }
    }
    
    public List<IFish> getFishes() {
        return fishes;
    }

    public void setFishes(List<IFish> fishes) {
        this.fishes = fishes;
    }

    public List<IStream> getStreams() {
        return streams;
    }

    public void setStreams(List<IStream> streams) {
        this.streams = streams;
    }
    
    public IField getField(){
        return this.field;
    }
    
    public void setField(IField field){
        this.field = field;
    }
    
    public String toString(){
        String result = new String();
        result += "Model";
        result += "\n------------------";
        result += "\n" + field.toString() + "\n";
        for(IFish fish : fishes){
            result += "\n" + fish.toString() +"\n";
        }
        for(IStream stream : streams){
            result += "\n" + stream.toString() + "\n";
        }
        result += "\n------------------";
        return result;
    }
}
