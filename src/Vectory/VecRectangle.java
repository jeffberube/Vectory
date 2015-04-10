/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vectory;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

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
        super(layer);
        this.rectangle = rectangle;
        init();
    }
    
    private void init() {
        getChildren().add(rectangle);
    }
    
}
