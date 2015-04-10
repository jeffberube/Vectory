/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vectory;

import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author Berube
 */
public class RootPane extends Pane {
    private MainWindowPane mainWindow;
    private final Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
 
    public RootPane(Stage stage) {
        super();
        init(stage);
    }
    
    private void init(Stage stage) {
        initMainWindow(stage);
        
        getStylesheets().add(RootPane.class.getResource("assets/css/RootPane.css").toExternalForm());
        getStyleClass().add("rootPane");
        
        getChildren().addAll(mainWindow);
        
        prefWidthProperty().bind(stage.widthProperty());
        prefHeightProperty().bind(stage.heightProperty());
    }
    
    private void initMainWindow(Stage stage) {
        mainWindow = MainWindowPane.getInstance(stage);
        
        mainWindow.setMinWidth(800);
        mainWindow.setMinHeight(600);
        
        mainWindow.setPrefWidth(1080);
        mainWindow.setPrefHeight(800);
        
        mainWindow.setMaxWidth(visualBounds.getWidth());
        mainWindow.setMaxHeight(visualBounds.getHeight());
        
        mainWindow.setTranslateX((visualBounds.getWidth() - mainWindow.getPrefWidth()) / 2);
        mainWindow.setTranslateY((visualBounds.getHeight() - mainWindow.getPrefHeight()) / 2);
    }
 
}
