/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vectory;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 *
 * @author Berube
 */
public class VecRectangle extends VecObject {
    private Rectangle rectangle;
    
    /*
        @param  layer   Parent layer for this object
    */
    public VecRectangle(Rectangle rectangle, Layer layer) {
        super(layer, rectangle);
        this.rectangle = rectangle;
        init();
    }
    
    private void init() {
        initNodeProperties();
        getChildren().add(0, rectangle);
    }
    
    protected void initNodeProperties() {
        nodeWidthProperty().bindBidirectional(rectangle.widthProperty());
        nodeHeightProperty().bindBidirectional(rectangle.heightProperty());
        rectangle.fillProperty().bind(super.fillColorProperty());
        rectangle.strokeProperty().bind(super.strokeColorProperty());
    }
    
    protected Shape getShapeDuplicate() {
        Rectangle clone = new Rectangle(rectangle.getWidth(), rectangle.getHeight());
        clone.setArcHeight(rectangle.getArcHeight());
        clone.setArcWidth(rectangle.getArcWidth());
        return clone;
    }
    
}
