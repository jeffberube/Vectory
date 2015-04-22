/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vectory;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;


/**
 *
 * @author Berube
 */
public abstract class VecObject extends Pane {
    private final SimpleDoubleProperty nodeWidth = new SimpleDoubleProperty();
    private final SimpleDoubleProperty nodeHeight = new SimpleDoubleProperty();
    private final SimpleObjectProperty<Color> strokeColor = new SimpleObjectProperty();
    private final SimpleObjectProperty<Color> fillColor = new SimpleObjectProperty();
    private final SimpleDoubleProperty strokeWidth = new SimpleDoubleProperty();
    
    private final SimpleObjectProperty<Image> thumbnail = new SimpleObjectProperty();
    
    private final SimpleBooleanProperty selected = new SimpleBooleanProperty(false);
    
    private final TransformationBox transformationBox;
    
    private Layer parentLayer;
    protected Node node;
    
    protected VecObject(Layer layer, Node node) {
        parentLayer = layer;
        this.node = node;
        transformationBox = new TransformationBox(this);
        init();
    }
    
    private void init() {
        selected.addListener(e->{
            if (selected.get())
                Context.addSelectedObject(this);
        });
        
        setOnMouseClicked(e->{
            if (Context.getTool() == SelectTool.getInstance())
                setSelected(true);
        });
        
        getChildren().add(transformationBox);
    }
    
    protected abstract void initNodeProperties();
    protected abstract Shape getShapeDuplicate();
    
    public Layer getParentLayer() {
        return parentLayer;
    }
    
    public void setParentLayer(Layer layer) {
        parentLayer.getChildren().remove(this);
        layer.getChildren().add(this);
        parentLayer = layer;
    }
    
    public Node getNode() {
        return node;
    }
    
    public void setShapeWidth(double value) {
        nodeWidth.set(value);
    }
    
    public double getShapeWidth() {
        return nodeWidth.get();
    }
    
    public SimpleDoubleProperty nodeWidthProperty() {
        return nodeWidth;
    }
    
    public void setShapeHeight(double value) {
        nodeHeight.set(value);
    }
    
    public double getShapeHeight() {
        return nodeHeight.get();
    }
    
    public SimpleDoubleProperty nodeHeightProperty() {
        return nodeHeight;
    }
    
    public void setSelected(boolean value) {
        selected.set(value);
    }
    
    public boolean isSelected() {
        return selected.get();
    }
    
    public BooleanProperty selectedProperty() {
        return selected;
    }
    
}
