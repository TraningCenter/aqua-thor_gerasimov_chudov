/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infotech.aquaThor.view;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.DefaultWindowManager;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.RadioBoxList;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.TerminalEmulatorDeviceConfiguration;
import com.infotech.aquaThor.interfaces.IParser;
import com.infotech.aquaThor.view.parsers.JaxbParser;
import com.infotech.aquaThor.view.parsers.ParserProp;
import com.infotech.aquaThor.view.parsers.SaxParser;
import com.infotech.aquaThor.view.parsers.StaxParser;
import com.infotech.aquaThor.view.parsers.domParser;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InputMenu {
    
    private final RadioBoxList<TextColor> colorSelector;
    private final RadioBoxList<String> parserSelector;
    
    private DefaultTerminalFactory terminalFactory;
    private Terminal terminal;
    private TerminalSize size;
    private Screen screen;
    private Panel panel;
    private BasicWindow window;
    private MultiWindowTextGUI gui;
    private ParserProp parserProp;
    private GraphicTerminal gTerminal;
    
    {
        try{
            size = new TerminalSize(35, 10);
            
            terminalFactory = new DefaultTerminalFactory();
            terminalFactory.setTerminalEmulatorTitle("Aqua-Thor");
            terminalFactory.setInitialTerminalSize(size);
            terminal = terminalFactory.createTerminal();
            screen = new TerminalScreen(terminal);
        } catch (IOException ex){
            ex.printStackTrace();
        }
        colorSelector = new RadioBoxList<>();
        parserSelector = new RadioBoxList<>();
        
        colorSelector.addItem(TextColor.ANSI.WHITE);
        colorSelector.addItem(TextColor.ANSI.GREEN);
        colorSelector.addItem(TextColor.ANSI.BLUE);
        colorSelector.addItem(TextColor.ANSI.RED);
        
        colorSelector.setCheckedItemIndex(0);
        
        parserSelector.addItem("DOM");
        parserSelector.addItem("SAX");
        parserSelector.addItem("StAX");
        parserSelector.addItem("JAXB");
        
        parserProp = new ParserProp();

        String currParser = parserProp.getParser().trim();
        for (int i = 0; i < parserSelector.getItems().size(); i++){
            if (currParser.equalsIgnoreCase(parserSelector.getItemAt(i))){
                parserSelector.setCheckedItemIndex(i);
            } else if (currParser.equalsIgnoreCase("")){
                parserSelector.setCheckedItemIndex(0);
            }
        }
    }
    
    public void start() throws IOException {
        screen.startScreen();
        
        panel = new Panel();
        panel.setLayoutManager(new GridLayout(3));
        EmptySpace emptySpace = new EmptySpace();
        emptySpace.setColor(TextColor.ANSI.BLUE);
        
        window = new BasicWindow();
        window.setTitle("Menu");
        gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), emptySpace);
        
        Button colorButton = new Button("Color", new Runnable() {
            @Override
            public void run() {
                BasicWindow colorWindow = colorSelector();
                gui.addWindow(colorWindow);
                gui.setActiveWindow(colorWindow);
            }
        });
        
        Button parserButton = new Button("Parser", new Runnable() {
            @Override
            public void run() {
                BasicWindow parserWindow = parserSelector();
                gui.addWindow(parserWindow);
                gui.setActiveWindow(parserWindow);
            }
        });
        
        Button start = new Button("Start", new Runnable() {
            @Override
            public void run() {
                try {
                    GraphicTerminal gTerminal = new GraphicTerminal(colorSelector.getCheckedItem(), selectParser());
                    gTerminal.start();
                } catch (Exception ex) {
                    Logger.getLogger(InputMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        panel.addComponent(colorButton);
        panel.addComponent(parserButton);
        panel.addComponent(start);
        
        window.setComponent(panel);
        gui.addWindowAndWait(window);
        
        KeyStroke key;
        while (true){
            key = screen.pollInput();
            if (key != null){
                key = screen.readInput();
                switch (key.getKeyType()){
                    case EOF:
                        System.exit(0);
                        break;
                }
            }
        }
    }
    
    private BasicWindow colorSelector(){
        Panel colorPanel = new Panel();
        BasicWindow colorWindow = new BasicWindow();
        colorPanel.addComponent(colorSelector);
        
        Button submit = new Button("OK", new Runnable() {
            @Override
            public void run() {
                colorWindow.close();
                gui.setActiveWindow(window);
            }
        });
        
        colorPanel.addComponent(submit);
        colorWindow.setComponent(colorPanel);
        return colorWindow;
    }
    
    private BasicWindow parserSelector(){
        Panel parserPanel = new Panel();
        BasicWindow parserWindow = new BasicWindow();
        parserPanel.addComponent(parserSelector);
        
        Button submit = new Button("OK", new Runnable() {
            @Override
            public void run() {
                parserWindow.close();
                gui.setActiveWindow(window);
                parserProp.setParser(parserSelector.getCheckedItem());
            }
        });
        
        parserPanel.addComponent(submit);
        parserWindow.setComponent(parserPanel);
        return parserWindow;
    }
    
    private IParser selectParser(){
        IParser parser = null;
        if ("DOM".equalsIgnoreCase(parserSelector.getCheckedItem())){
            parser = new domParser();
        }
        if ("SAX".equalsIgnoreCase(parserSelector.getCheckedItem())){
            parser = new SaxParser();
        }
        if ("StAX".equalsIgnoreCase(parserSelector.getCheckedItem())){
            parser = new StaxParser();
        }
        if ("JAXB".equalsIgnoreCase(parserSelector.getCheckedItem())){
            parser = new JaxbParser();
        }
        return parser;
    }
}
