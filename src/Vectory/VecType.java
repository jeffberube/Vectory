/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vectory;

import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 *
 * @author Berube
 */
public class VecType extends VecObject{
    private TextField text;
    private Rectangle bounds;
    
    /* Hack to deal with multiplication */
    private boolean key = false;
    /*
        @param  layer   Parent layer for this object
    */
    public VecType(TextField text, Layer layer) {
        super(layer, text);
        this.text = text;
        init();
    }
    
    private void init() {
        initNodeProperties();
        getChildren().add(0, text);
    }
    
    protected void initNodeProperties() {
        text.translateXProperty().bind(widthProperty().divide(2));
        text.translateYProperty().bind(heightProperty().divide(2));
        //text.colorProperty().bind(super.fillColorProperty());
        //text.strokeProperty().bind(super.strokeColorProperty());
    }
    
    protected Shape getShapeDuplicate() {
        return bounds;
    }
    
}
