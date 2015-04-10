/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vectory;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Berube
 */
public class Vectory extends Application {
    private RootPane rootPane;
    private Scene scene;
    private PanelsController panelsController;
    
    Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
    
    @Override
    public void start(Stage stage) { 
        init(stage);
        
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("assets/images/icon.png")));
        stage.setTitle("Untitled.vec");
        stage.setScene(scene);
        stage.show();
    }
    
    private void init(Stage stage) {
        rootPane = new RootPane(stage);
        scene = new Scene(rootPane, visualBounds.getWidth(), visualBounds.getHeight(), Color.TRANSPARENT);
        panelsController = new PanelsController(rootPane);
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
