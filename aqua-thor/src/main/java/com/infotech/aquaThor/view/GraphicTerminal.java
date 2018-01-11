/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infotech.aquaThor.view;

import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalFactory;
import com.infotech.aquaThor.database.Database;
import com.infotech.aquaThor.database.JaxbOutput;
import com.infotech.aquaThor.database.Statistics;
import com.infotech.aquaThor.interfaces.IField;
import com.infotech.aquaThor.interfaces.IFish;
import com.infotech.aquaThor.interfaces.IParser;
import com.infotech.aquaThor.interfaces.IStream;
import com.infotech.aquaThor.model.FoodCreator;
import com.infotech.aquaThor.model.Model;
import com.infotech.aquaThor.model.entities.Fish;
import com.infotech.aquaThor.model.entities.Shark;
import com.infotech.aquaThor.model.entities.Stream;
import com.infotech.aquaThor.model.utils.Cell;
import java.io.IOException;
import javafx.geometry.Orientation;

public class GraphicTerminal{
    
    private final static String PATH = "configurations/input.xml";
    private final int DEF_COLUMN = 20;
    private final int DEF_ROW = 18;
    private final char FISH = '∝';
    private final char SHARK = '⋉';
    private final char FOOD = '◆';
    
    private IParser parser;
    private Model model;
    private IField field;
    private FoodCreator foodCreator;
    private TextColor textColor;
    private DefaultTerminalFactory terminalFactory;
    private TerminalSize size;
    private TerminalSize controlPanelSize;
    private TerminalSize infoPanelSize;
    private Terminal terminal;
    private TerminalScreen screen;
    private TextGraphics tGraphics;
    private KeyStroke key;
    private TextCharacter fishIcon;
    private TextCharacter sharkIcon;
    private TextCharacter foodIcon;
    private Thread foodThread;
    private Statistics stats;
    private JaxbOutput xmlWriter;
    
    private boolean simulation;
    private boolean pause;
    private int marginTop;
    private int marginLeft;
    private int fishCount;
    private int sharkCount;
    private int steps;
    
    public GraphicTerminal(TextColor txtColor, IParser parser) throws Exception{
        this.parser = parser;
        this.textColor = txtColor;
        init();
        size = new TerminalSize(field.getWidth() + DEF_COLUMN, field.getHeight() + DEF_ROW);
        terminalFactory = new DefaultTerminalFactory();
        terminalFactory.setTerminalEmulatorTitle("Aqua-Thor");
        terminalFactory.setInitialTerminalSize(size);
        fishIcon = new TextCharacter(FISH, TextColor.ANSI.GREEN, TextColor.ANSI.WHITE);
        sharkIcon = new TextCharacter(SHARK, TextColor.ANSI.RED, TextColor.ANSI.WHITE);
        foodIcon = new TextCharacter(FOOD, TextColor.ANSI.MAGENTA, TextColor.ANSI.WHITE);
        marginTop = (Integer)(DEF_ROW / 2);
        marginLeft = (Integer)(DEF_COLUMN / 2);
        terminal = terminalFactory.createTerminal();
        screen = new TerminalScreen(terminal);
        screen.setCursorPosition(null);
        tGraphics = screen.newTextGraphics();
        screen.startScreen();
    }
    
    private void init() throws Exception{
        model = parser.parse(PATH);
        field = model.getField();
        xmlWriter = new JaxbOutput();
        stats = new Statistics(model);
        simulation = true;
        pause = false;
    }
    
    public void start() throws IOException{
        
        controlPanel();
        drawFrame(new TerminalPosition(marginLeft - 1, marginTop - 1), new TerminalSize(field.getWidth() + 2, field.getHeight() + 2));
        icons();
        
        screen.refresh();
        
        steps = 0;
        
        foodCreator = new FoodCreator(field);
        foodThread = (Thread) foodCreator;
        foodThread.start();
        
        while (simulation){              
            stats.generate(steps);
            
            if (!Thread.interrupted()){
                
                fishCount = 0;
                sharkCount = 0;
                
                for (IFish fish : model.getFishes()){
                    if (fish.getClass().equals(Fish.class)){
                        fishCount++;
                    }
                    if (fish.getClass().equals(Shark.class)){
                        sharkCount++;
                    }
                }
                
                infoPanel();
                
                try{
                    drawOcean();
                } catch(Exception ex){
                    ex.printStackTrace();
                }
            } else {
                foodThread.interrupt();
                break;
            }
            readKey();
            
            if (fishCount == 0 && sharkCount == 0){
                endSimulationPanel();
            }
        }
        
        xmlWriter.writeDatabase(stats.getDatabase());
        
        screen.stopScreen();
        screen.close();
        terminal.close();
    }
    
    private void stopSimulation(){
        simulation = false;
    }
    
    private void drawOcean() throws Exception{
        synchronized(this.field){
            Cell[][] ocean = this.field.getOcean();
            for (int i = 0; i < field.getHeight(); i++){
                for (int j = 0; j < field.getWidth(); j++){
                    Cell cell = ocean[i][j];
                
                    if (cell.getContent() != null){
                        switch (cell.getContent()){
                            case EMPTY:
                                screen.setCharacter(new TerminalPosition(j + marginLeft, i + marginTop), 
                                        new TextCharacter('~', textColor, TextColor.ANSI.WHITE));
                                break;
                            case FOOD:
                                screen.setCharacter(new TerminalPosition(j + marginLeft, i + marginTop), 
                                        foodIcon);
                                break;
                            case FISH:
                                screen.setCharacter(new TerminalPosition(j + marginLeft, i + marginTop), 
                                       fishIcon);
                                break;
                            case SHARK:
                                screen.setCharacter(new TerminalPosition(j + marginLeft, i + marginTop), 
                                        sharkIcon);
                                break;
                            default:
                                break;
                        }
                    }
                    verticalStream(j);
                }
                horizontalStream(i);
                screen.refresh();
            }
            model.bypassAllElements();
            Thread.sleep(800);
            steps++;
            screen.refresh();
        }
    }
    
    private void readKey(){
        try{
            if (screen.pollInput() != null){
                key = screen.readInput();
                switch (key.getKeyType()){
                    case Escape:
                        foodThread.interrupt();
                        stopSimulation();
                        terminal.close();
                        pause = false;
                        break;
                    case EOF:
                        foodThread.interrupt();
                        stopSimulation();
                        pause = false;
                        break;
                    case Enter:
                        pause = !pause;
                        synchronized(this.foodThread){
                            if (!pause){
                                this.field.notify();
                            }
                        }
                        break;
                }
            }
            synchronized(this.foodThread){
                if (pause){
                    this.field.wait();
                }
            }
            while (pause){
                readKey();
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void controlPanel(){
        
        String[] control = new String[3];
        control[0] = "Control:";
        control[1] = "[Enter] - pause;";
        control[2] = "[Escape] - exit.";
        
        int panelWidth = 0;
        
        for (int i = 0; i < control.length; i++){
            if (control[i].length() > panelWidth){
                panelWidth = control[i].length();
            }
        }
        
        TerminalPosition controlPanelPosition = new TerminalPosition(0, 0);
        controlPanelSize = new TerminalSize(panelWidth + 3, control.length + 2);
        
        drawFrame(controlPanelPosition, controlPanelSize);
        
        for (int i = 0; i < control.length; i++){
            try{
                stringDrawer(control[i], controlPanelPosition.getColumn() + 1, controlPanelPosition.getRow() + i + 1);
            } catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }
    
    private void infoPanel(){
        
        String[] info = new String[3];
        info[0] = "Fish count: " + String.valueOf(fishCount);
        info[1] = "Shark count: " + String.valueOf(sharkCount);
        info[2] = "Step count: " + String.valueOf(steps);
        
        int panelWidth = 0;
        
        for (int i = 0; i < info.length; i++){
            if (info[i].length() > panelWidth){
                panelWidth = info[i].length();
            }
        }
        
        infoPanelSize = new TerminalSize(panelWidth + 3, info.length + 2);
        TerminalPosition infoPanelPosition = new TerminalPosition(controlPanelSize.getColumns() + 4, 0);
        
        drawFrame(infoPanelPosition, infoPanelSize);
        
        for (int i = 0; i < info.length; i++){
            try{
                stringDrawer(info[i], infoPanelPosition.getColumn() + 1, infoPanelPosition.getRow() + i + 1);
            } catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }
    
    private void endSimulationPanel(){
        String[] panel = new String[3];
        panel[0] = "Simulation is ended!";
        panel[1] = "<restart>";
        panel[2] = "<exit>";
        
        TerminalSize panelSize = new TerminalSize(panel[0].length() + 2, panel.length + 2);
        TerminalPosition panelPos = new TerminalPosition(controlPanelSize.getColumns() + infoPanelSize.getColumns() + 8, 0);
        
        drawFrame(panelPos, panelSize);
        
        for (int i = 0; i < panel.length; i++){
            try{
                stringDrawer(panel[i], panelPos.getColumn() + 1, panelPos.getRow() + i + 1);
            } catch (IOException ex){
                ex.printStackTrace();
            }
        }
        
        TerminalPosition restartPos = new TerminalPosition(panelPos.getColumn() + 2, panelPos.getRow() + 2);
        TerminalPosition exitPos = new TerminalPosition(panelPos.getColumn() + 2, panelPos.getRow() + 3);
        
        try{
            screen.setCursorPosition(restartPos);
            screen.refresh();
        } catch(IOException ex){
            ex.printStackTrace();
        }
        
        while (simulation){
            if (screen.getCursorPosition().equals(restartPos)){
                try{
                    restartSim(exitPos);
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
            if (screen.getCursorPosition().equals(exitPos)){
                try{
                    exitSim(restartPos);
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    }
    
    private void horizontalStream(int coord){
        for (IStream stream : model.getStreams()){
            switch (stream.getOrientation()){
                case HORIZONTAL:
                    if (stream.getStartPos() <= coord && stream.getFinishPos() >= coord){
                        screen.setCharacter(new TerminalPosition(marginLeft - 2, marginTop + coord),
                                new TextCharacter('◀'));
                    } else{
                        screen.setCharacter(new TerminalPosition(marginLeft - 2, marginTop + coord),
                                new TextCharacter(Symbols.SINGLE_LINE_VERTICAL));
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void verticalStream(int coord){
        for (IStream stream : model.getStreams()){
            switch (stream.getOrientation()){
                case VERTICAL:
                    if (stream.getStartPos() <= coord && stream.getFinishPos() >= coord){
                        screen.setCharacter(new TerminalPosition(marginLeft + coord, marginTop - 2), 
                                new TextCharacter('▲'));
                    } else{
                        screen.setCharacter(new TerminalPosition(marginLeft + coord, marginTop - 2), 
                                new TextCharacter(Symbols.SINGLE_LINE_HORIZONTAL));
                    }
                    break;
                default:
                    break;
            }
        }
    }
    
    private void icons(){
        String[] iconsPanel = new String[3];
        iconsPanel[0] = "-shark; ";
        iconsPanel[1] = "-fish; ";
        iconsPanel[2] = "-food.";
        
        int panelWidth = 0;
        for (int i = 0; i < iconsPanel.length; i++){
            panelWidth += iconsPanel[i].length() + 1;
        }
        
        TerminalPosition position = new TerminalPosition(marginLeft + 1, size.getRows() - marginTop + 2);
        
        drawFrame(new TerminalPosition(position.getColumn() - 2, position.getRow() - 1), new TerminalSize(panelWidth + 1, iconsPanel.length));
        
        int col = 0;
        for (int i = 0; i < iconsPanel.length; i++){
            try{
                stringDrawer(iconsPanel[i], position.getColumn() + col + 1, position.getRow());
                col += iconsPanel[i].length();
            } catch (IOException ex){
                ex.printStackTrace();
            }
        }
        screen.setCharacter(position, sharkIcon);
        screen.setCharacter(new TerminalPosition(position.getColumn() + iconsPanel[0].length(), 
                position.getRow()), fishIcon);
        screen.setCharacter(new TerminalPosition(position.getColumn() + iconsPanel[0].length() + iconsPanel[1].length(), 
                position.getRow()), foodIcon);
    }
    
    private void restartSim(TerminalPosition exitPos) throws Exception{
        boolean flag = true;
        while (flag){
            key = screen.readInput();
            switch (key.getKeyType()){
                case ArrowDown:
                    screen.setCursorPosition(exitPos);
                    flag = false;
                    break;
                case Enter:
                    foodThread.interrupt();
                    screen.clear();
                    screen.setCursorPosition(null);
                    init();
                    start();
                    break;
                case EOF:
                    foodThread.interrupt();
                    stopSimulation();
                    flag = false;
                    break;
            }
            screen.refresh();
        }
    }
    
    private void exitSim(TerminalPosition restartPos) throws Exception{
        boolean flag = true;
        while (flag){
            key = screen.readInput();
            switch (key.getKeyType()){
                case ArrowUp:
                    screen.setCursorPosition(restartPos);
                    flag = false;
                    break;
                case Enter:
                    foodThread.interrupt();
                    stopSimulation();
                    flag = false;
                    break;
                case EOF:
                    foodThread.interrupt();
                    stopSimulation();
                    flag = false;
                    break;
            }
            screen.refresh();
        }
    }
    
    private void drawFrame(TerminalPosition start, TerminalSize end){
        tGraphics.drawRectangle(start, end, new TextCharacter(Symbols.DIAMOND, TextColor.ANSI.RED, TextColor.ANSI.BLACK));
    }
    
    private void stringDrawer(String string, int column, int row) throws IOException{
        char[] charArray = string.toCharArray();
        for (int i = 0; i < string.length(); i++){
            screen.setCharacter(new TerminalPosition(column + i, row), new TextCharacter(charArray[i]));
        }
    }
}
