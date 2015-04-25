/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vectory;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 *
 * @author Berube
 */
public class UniversalShape extends Pane {
    private final SimpleDoubleProperty width = new SimpleDoubleProperty();
    private final SimpleDoubleProperty height = new SimpleDoubleProperty();

    private Shape shape;
    
    public UniversalShape(Shape shape) {
        this.shape = shape;
        init();
    }
    
    private void init() {
        if (shape instanceof Rectangle) {
            Rectangle rect = (Rectangle)shape;
            rect.widthProperty().bind(widthProperty());
            rect.heightProperty().bind(heightProperty());

        } else if (shape instanceof Ellipse) {
            ((Ellipse)shape).centerXProperty().bind(widthProperty().divide(2));
            ((Ellipse)shape).centerYProperty().bind(heightProperty().divide(2));
            ((Ellipse)shape).radiusXProperty().bind(widthProperty().divide(2));
            ((Ellipse)shape).radiusYProperty().bind(heightProperty().divide(2));
        }
        
        getChildren().add(shape);
    }
    
    public Shape getShapeNode() {
        return shape;
    }
    
    public void setShapeNode(Shape shape) {
        this.shape = shape;
    }

}
