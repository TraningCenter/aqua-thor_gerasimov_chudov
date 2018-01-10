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
import com.infotech.aquaThor.model.utils.Orientation;
import com.infotech.aquaThor.model.utils.Tuple;
import com.infotech.aquaThor.view.parsers.FieldAdapter;
import com.infotech.aquaThor.view.parsers.FishAdapter;
//import com.sun.java.swing.plaf.gtk.GTKConstants;
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
    
    @XmlElement(name="field")
    @XmlJavaTypeAdapter(FieldAdapter.class)
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
    
    @XmlElementWrapper(name="newFish")
    @XmlElement(name="newFish", type=Food.class)
    private List<IFish> newFish = new ArrayList<>();
    
    @XmlElementWrapper(name="deadFish")
    @XmlElement(name="deadFish", type=Food.class)
    private List<IFish> deadFish = new ArrayList<>();

    public Model(){}
    
    public Model(IField field, List<IFish> fishes, List<IStream> streams) {
        this.fishes = fishes;
        this.streams = streams;
        this.field = field;
        createOcean();
    }

    public void bypassAllElements() throws Exception{
        Cell[][] allCells = field.getOcean();
        for(IFish fish : fishes){
            if(!deadFish.contains(fish)){
                Tuple nextCellForFish = fish.move(generateFieldOfView(fish), field.isClosed());
                if(CheckLiveTime(fish)){
                    if(fish instanceof Fish)
                        allCells[nextCellForFish.y][nextCellForFish.x].setContent(CellContent.FISH);
                    else if(fish instanceof Shark)
                        allCells[nextCellForFish.y][nextCellForFish.x].setContent(CellContent.SHARK);
                    fish.setCoordinates(nextCellForFish);
                }
                if(field.isClosed()){
                    if(nextCellForFish.x < 0){
                        nextCellForFish.x = field.getWidth() - 1;
                    }
                    if(nextCellForFish.y < 0){
                        nextCellForFish.y = field.getHeight() - 1;
                    }
                    if(nextCellForFish.x > field.getWidth()){
                        nextCellForFish.x = 0;
                    }
                    if(nextCellForFish.y > field.getHeight()){
                        nextCellForFish.y = 0;
                    }
                }
            }
        }
        makeStreams();
        addChildrenToFishList();
        removeDead();
    }
    
    public void fishCreated(Cell coord, CellContent type, IFish child){

        newFish.add(child);
        child.addObserver(this);
        coord.setContent(type);
        field.setCell(coord.getCoords(), type);            
    }
    
    public void fishEatten(Cell cell){
        List<IFish> removingFishes = new ArrayList();
        for(IFish fish: fishes){
            if(fish.getCoordinates().equals(cell.getCoords())){
                removingFishes.add(fish);
            }
        }
        deadFish.addAll(removingFishes);
    }  
    
    public void foodEatten(Cell cell){
        List<IFood> removingFood = new ArrayList();
        for(IFood f: food){
            if(f.getCoordinates().equals(cell.getCoords())){
                removingFood.add(f);
            }
        }
        food.removeAll(removingFood);
    }
    
    private void addChildrenToFishList(){
        fishes.addAll(newFish);
        newFish.clear();
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
    
    private boolean CheckLiveTime(IFish fish){
        if(fish.getAge() <= fish.getLiveTime() 
                && fish.getStarvingTime() <= fish.getLiveTimeWithoutFood()){
            return true;
        }
        else{
            deadFish.add(fish);  //смерть
            return false;
        }
    }
    
    private void removeDead(){
        fishes.removeAll(deadFish);
        deadFish.clear();
    }
    
    private void makeStreams(){
        for(IStream stream : streams){
            Integer start = stream.getStartPos();
            Integer finish = stream.getFinishPos();
            Float dest = Math.signum(finish - start);
            Integer xStart;
            Integer xFin;

            if(stream.getOrientation() == Orientation.HORIZONTAL){
                for(int x=0; x < field.getWidth(); x+=dest){
                    for(int y=start; y < finish; y+=dest){
                        moveObject(new Tuple(x,y), new Tuple(x-stream.getSpeed(),y));
                    }
                }
            }
            else if(stream.getOrientation() == Orientation.VERTICAL){
                for(int y=0; y < field.getHeight(); y+=dest){
                    for(int x=start; x < finish; x+=dest){
                        moveObject(new Tuple(x,y), new Tuple(x,y-stream.getSpeed()));
                    }
                }
            }
        }
    }
    
    private void moveObject(Tuple from, Tuple to){
        Cell[][] allCells = field.getOcean();

        
        if(field.isClosed()){
            if(to.y < 0) to.y = 0;
            if(to.x < 0) to.x = 0;
            if(to.x >= field.getWidth()) to.x = field.getWidth()-1;
            if(to.y >= field.getHeight()) to.y = field.getHeight()-1;
            
            Cell fromCell = allCells[from.y][from.x];
            Cell toCell = allCells[to.y][to.x];
            if((fromCell.getContent() == CellContent.EMPTY) || (fromCell.getContent() == CellContent.FOOD)){

            }
            else{
                for(IFish fish : fishes){
                        if(fish.getCoordinates().equals(from)){
                            CellContent cont = fromCell.getContent();
                            fromCell.setContent(CellContent.EMPTY);
                            toCell.setContent(cont);
                            
                            fish.setCoordinates(to);
                            fish.checkCell(allCells[to.y][to.x]);
                            break;
                        }
                    }
                }
            }
        else{
           if(to.x < 0) to.x = field.getWidth()-1;
           if(to.y < 0) to.y = field.getHeight()-1;
           if(to.x >= field.getWidth()) to.x = 0;
           if(to.y >= field.getHeight()) to.y = 0;
           
            Cell fromCell = allCells[from.y][from.x];
            Cell toCell = allCells[to.y][to.x];
            if((fromCell.getContent() == CellContent.EMPTY) || (fromCell.getContent() == CellContent.FOOD)){

            }
            else{
                for(IFish fish : fishes){
                        if(fish.getCoordinates().equals(from)){
                            CellContent cont = fromCell.getContent();
                            fromCell.setContent(CellContent.EMPTY);
                            toCell.setContent(cont);
                            
                            fish.setCoordinates(to);
                            fish.checkCell(allCells[to.y][to.x]);
                            break;
                        }
                    }
                }
            }    
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
