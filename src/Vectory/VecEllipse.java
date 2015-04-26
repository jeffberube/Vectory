/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vectory;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;

/**
 *
 * @author Berube
 */
public class VecEllipse extends VecObject{
    private Ellipse ellipse;
    
    /* Hack to deal with multiplication */
    private boolean key = false;
    /*
        @param  layer   Parent layer for this object
    */
    public VecEllipse(Ellipse ellipse, Layer layer) {
        super(layer, ellipse);
        this.ellipse = ellipse;
        init();
    }
    
    private void init() {
        initNodeProperties();
        getChildren().add(0, ellipse);
    }
    
    protected void initNodeProperties() {
        //ellipse.radiusXProperty().bind(nodeWidthProperty().divide(2));
        //ellipse.radiusYProperty().bind(nodeHeightProperty().divide(2));
        
        nodeWidthProperty().addListener(e->{
            if (key)
                key = false;
            else {
                key = true;
                ellipse.setRadiusX(nodeWidthProperty().get() / 2);
            }
        });
        
        nodeHeightProperty().addListener(e->{
            if (key)
                key = false;
            else {
                key = true;
                ellipse.setRadiusY(nodeHeightProperty().get() / 2);
            }
        });
        
        ellipse.radiusXProperty().addListener(e->{
            if (key)
                key = false;
            else {
                key = true;
                nodeWidthProperty().set(ellipse.getRadiusX() * 2);
            }
        });
        
        ellipse.radiusYProperty().addListener(e->{
            if (key)
                key = false;
            else {
                key = true;
                nodeHeightProperty().set(ellipse.getRadiusY() * 2);
            }
        });
        
        nodeWidthProperty().set(ellipse.getRadiusX() * 2);
        nodeHeightProperty().set(ellipse.getRadiusY() * 2);
        ellipse.centerXProperty().bind(widthProperty().divide(2));
        ellipse.centerYProperty().bind(heightProperty().divide(2));
        ellipse.fillProperty().bind(super.fillColorProperty());
        ellipse.strokeProperty().bind(super.strokeColorProperty());
    }
    
    protected Shape getShapeDuplicate() {
        Ellipse clone = new Ellipse(ellipse.getRadiusX(), ellipse.getRadiusY());
        return clone;
    }
}
