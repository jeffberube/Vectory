/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vectory;

import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;

/**
 *
 * @author Berube
 */
public class VecEllipse extends VecObject{
    private Ellipse ellipse;
    
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
        ellipse.radiusXProperty().bind(nodeWidthProperty().divide(2));
        ellipse.radiusYProperty().bind(nodeHeightProperty().divide(2));
        ellipse.fillProperty().bind(super.fillColorProperty());
        ellipse.strokeProperty().bind(super.strokeColorProperty());
    }
    
    protected Shape getShapeDuplicate() {
        Ellipse clone = new Ellipse(ellipse.getRadiusX(), ellipse.getRadiusY());
        return clone;
    }
}
