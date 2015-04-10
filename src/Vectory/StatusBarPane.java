/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vectory;

import javafx.scene.layout.Pane;

/**
 *
 * @author Berube
 */
public class StatusBarPane extends Pane {
    private static StatusBarPane instance = null;
    
    private final WindowSizeHandle wsh = new WindowSizeHandle();
    
    public static StatusBarPane getInstance() {
        if (instance == null)
            instance = new StatusBarPane();
        
        return instance;
    }
    
    private StatusBarPane() {
        super();
        init();
        getChildren().addAll(wsh);
    }
    
    private void init() {
        setPrefHeight(Constants.STATUSBAR_HEIGHT);
        setStyle("-fx-background-color: #222222;");
    
        initWSH();
    }
    
    private void initWSH() {
        wsh.translateXProperty().bind(widthProperty().subtract(wsh.widthProperty()).subtract(22));
        wsh.setTranslateY(4);
    }
    
    
    
}
