/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vectory;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

/**
 *
 * @author Berube
 */
public class LayerManager {
    private final SimpleObjectProperty<ToggleGroup> toggleGroup = new SimpleObjectProperty(new ToggleGroup());
    private final SimpleObjectProperty<Toggle> selectedLayer = new SimpleObjectProperty();
    private LayerGroup documentRootGroup;
    private int newLayerIndex = 1;
    
    public LayerManager(LayerGroup documentRootGroup) {
        this.documentRootGroup = documentRootGroup;
        selectedLayer.bind(toggleGroup.get().selectedToggleProperty());
    }
    
    public Layer newLayer() {
        return newLayer("Layer" + getNextIndex());
    }
    
    public Layer newLayer(String name) {
        Layer layer = new Layer(name, toggleGroup.get());
        getToggleGroup().getToggles().add(layer);
        getToggleGroup().selectToggle(layer);
        layer.setToggleGroup(getToggleGroup());
        return layer;
    }
    
    private int getNextIndex() {
        return newLayerIndex++;
    }
    
    public Layer getSelectedLayer() {
        return (Layer)getToggleGroup().getSelectedToggle();
    }
    
    public SimpleObjectProperty<Toggle> selectedLayerProperty() {
        return selectedLayer;
    }
    
    public ToggleGroup getToggleGroup() {
        return toggleGroup.get();
    }
    
    public SimpleObjectProperty<ToggleGroup> toggleGroupProperty() {
        return toggleGroup;
    }
    
    
}
