/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vectory;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.Pane;

/**
 *
 * @author Berube
 */
public class LayerGroup extends Pane {
    private static int newGroupIndex = 1;

    private String name;
    
    public LayerGroup() {
        this("Group " + getNextIndex());
    }
    
    public LayerGroup(String name) {
        this.name = name;
    }
    
    public void addGroup(LayerGroup group) {
        getChildren().add(group);
    }
    
    public void addLayer(Layer layer) {
        getChildren().add(layer);
    }
    
    public static int getNextIndex() {
        return newGroupIndex++;
    }
    
    public void setName(String value) {
        name = value;
    }
    
    public String getName() {
        return name;
    }
    
}
