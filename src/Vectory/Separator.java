/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vectory;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 *
 * @author Berube
 */
public class Separator extends Pane {
    public static enum LineOrientation {VERTICAL, HORIZONTAL};
    
    private static final int LINE_OFFSET = 1;
    
    private final SimpleDoubleProperty startX = new SimpleDoubleProperty();
    private final SimpleDoubleProperty startY = new SimpleDoubleProperty();
    private final SimpleDoubleProperty endX = new SimpleDoubleProperty();
    private final SimpleDoubleProperty endY = new SimpleDoubleProperty();
    
    private final Line darkLine = new Line();
    private final Line lightLine = new Line();
    private final Line[] lines = new Line[]{darkLine, lightLine};
    
    private double length;
    
    private Color darkColor;
    private Color lightColor;
    private Color[] colors;
    
    private LineOrientation orientation;
    
    public Separator(double length, LineOrientation orientation, 
            Color darkColor, Color lightColor) {
        super();
        
        setLength(length);
        setOrientation(orientation);
        setColors(darkColor, lightColor);
        
        init();
    }
    
    private void init() {
        initProperties();
        initLineBindings();
        getChildren().addAll(lines);
    }
    
    private void initProperties() {
        setStartX(0.5);
        setStartY(0.5);
        setEndX(orientation == LineOrientation.HORIZONTAL ? length : 0.5);
        setEndY(orientation == LineOrientation.VERTICAL ? length : 0.5);
        
    }
    
    private void initLineBindings() {
        for (int i = 0; i < lines.length; i++) {
            lines[i].setStroke(colors[i]);
            lines[i].startXProperty().bind(this.startXProperty().add(orientation == LineOrientation.VERTICAL ? i * LINE_OFFSET : 0));
            lines[i].startYProperty().bind(this.startYProperty().add(orientation == LineOrientation.HORIZONTAL ? i * LINE_OFFSET : 0));
            lines[i].endXProperty().bind(this.endXProperty().add(orientation == LineOrientation.VERTICAL ? i * LINE_OFFSET : 0));
            lines[i].endYProperty().bind(this.endYProperty().add(orientation == LineOrientation.HORIZONTAL ? i * LINE_OFFSET : 0));
        }
    }
    
    private void setLength(double value) {
        length = value;
    }
    
    private void setColors(Color darkColor, Color lightColor) {
        this.darkColor = darkColor;
        this.lightColor = lightColor;
        colors = new Color[]{darkColor, lightColor};
        lines[0].setStroke(colors[0]);
        lines[1].setStroke(colors[1]);
    }
    
    private void setOrientation(LineOrientation orientation) {
        this.orientation = orientation;
    }

    public void setStartX(double value) {
        startX.set(value);
    }
    
    public void setStartY(double value) {
        startY.set(value);
    }
    
    public void setEndX(double value) {
        endX.set(value);
    }
    
    public void setEndY(double value) {
        endY.set(value);
    }
    
    public double getStartX() {
        return startX.get();
    }
    
    public double getStartY() {
        return startY.get();
    }
    
    public double getEndX() {
        return endX.get();
    }
    
    public double getEndY() {
        return endY.get();
    }
    
    public SimpleDoubleProperty startXProperty() {
        return startX;
    }
    
    public SimpleDoubleProperty startYProperty() {
        return startY;
    }
    
    public SimpleDoubleProperty endXProperty() {
        return endX;
    }
    
    public SimpleDoubleProperty endYProperty() {
        return endY;
    }
    
    
    
}
