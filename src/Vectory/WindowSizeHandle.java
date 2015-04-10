/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vectory;

import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author Berube
 */
public class WindowSizeHandle extends Pane {
    private MainWindowPane mainWindow;
    
    private double originMouseX = 0;
    private double originMouseY = 0;
    private double originWindowWidth = 0;
    private double originWindowHeight = 0;
    
    public WindowSizeHandle() {
        super();
        init();
    }
    
    private void init() {
        generateLines();
        setupWindowResize();
    }
    
    private void generateLines() {
        Dimple[] dimples = new Dimple[10];
        
        int count = 0;
        while (count < dimples.length)
            dimples[count++] = new Dimple();
        
        count = 0;
        
        for (int i = 4; i > 0; i--)
            for (int j = i; j < 5; j++) {
                dimples[count].setTranslateX((j * 4) + 0.5);
                dimples[count].setTranslateY(((4 - i) * 4) + 0.5);
                count++;
            }
    
        getChildren().addAll(dimples);
    }
    
    private void setupWindowResize() {
        
        setOnMouseEntered(e->{
            if (!MainWindowControls.isMaximized())
                setCursor(Cursor.NW_RESIZE);
        });
        
        setOnMouseExited(e->{
            if (!MainWindowControls.isMaximized())
                setCursor(Cursor.DEFAULT);
        });
        
        setOnMousePressed(e->{
            if (!MainWindowControls.isMaximized()) {
                if (mainWindow == null) 
                    mainWindow = (MainWindowPane)getScene().getRoot().lookup("#mainWindow");
                
                originMouseX = e.getScreenX();
                originMouseY = e.getScreenY();
                originWindowWidth = mainWindow.getWidth();
                originWindowHeight = mainWindow.getHeight();
            }
        });
        
        setOnMouseDragged(e->{
            if (!MainWindowControls.isMaximized()) {
                if (mainWindow == null) 
                    mainWindow = (MainWindowPane)getScene().getRoot().lookup("#mainWindow");
                
                mainWindow.setPrefWidth(originWindowWidth - (originMouseX - e.getScreenX()));
                mainWindow.setPrefHeight(originWindowHeight - (originMouseY - e.getScreenY()));
            }
        });
    }
    
    private class Dimple extends Pane {
        
        private Rectangle darkDimple = new Rectangle(0, 0, 1, 1);
        private Rectangle lightDimple = new Rectangle(1, 1, 1, 1);
        
        public Dimple() {
            darkDimple.setFill(Color.web("#222222"));
            lightDimple.setFill(Color.web("#CCCCCC"));
            getChildren().addAll(darkDimple, lightDimple);
        }
        
    }
    
}
