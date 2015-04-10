/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vectory;

import java.util.List;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 *
 * @author Berube
 */
public abstract class VecObject extends Pane {
    private final SimpleObjectProperty<Color> strokeColor = new SimpleObjectProperty();
    private final SimpleObjectProperty<Color> fillColor = new SimpleObjectProperty();
    private final SimpleDoubleProperty strokeWidth = new SimpleDoubleProperty();
    
    private final SimpleObjectProperty<Image> thumbnail = new SimpleObjectProperty();
    
    private final SimpleBooleanProperty selected = new SimpleBooleanProperty(false);
    
    private final TransformationBox transformationBox = new TransformationBox(this);
    
    private Layer parentLayer;
    
    protected VecObject(Layer layer) {
        parentLayer = layer;
        init();
    }
    
    private void init() {
    
    }
    
    public Layer getParentLayer() {
        return parentLayer;
    }
    
    public void setParentLayer(Layer layer) {
        parentLayer.getChildren().remove(this);
        layer.getChildren().add(this);
        parentLayer = layer;
    }
    
    public void setSelected(boolean value) {
        selected.set(value);
    }
    
}
