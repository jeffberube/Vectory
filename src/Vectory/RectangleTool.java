/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vectory;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Berube
 */
public class RectangleTool implements Tool {    
    private static RectangleTool instance = null;
    
    private double originMouseX = 0;
    private double originMouseY = 0;
    private double originLayoutX = 0;
    private double originLayoutY = 0;
    
    private boolean creatingObject = false;
    private boolean squareFootprint = false;
    private boolean originIsCenter = false;
    
    EventHandler keyPressedFilter = new EventHandler<KeyEvent>() {
                            public void handle(KeyEvent e) {
                                keyPressedHandler(e);
                            }
                        };
    
    EventHandler keyReleasedFilter = new EventHandler<KeyEvent>() {
                            public void handle(KeyEvent e) {
                                keyReleasedHandler(e);
                            }
                        };
    
    
    private Rectangle newObject;
    
    private RectangleTool() {
    
    }
    
    public static RectangleTool getInstance() {
        if (instance == null)
            instance = new RectangleTool();
        
        return instance;
    }
    
    public Cursor getCursor() {
        return Cursor.CROSSHAIR;
    }
    
    public void mousePressedHandler(MouseEvent e) {
        storeOriginMouseCoordinates(e);
        addKeyboardModifiers();
    }
    
    public void mouseDraggedHandler(MouseEvent e){
        if (newObject == null) 
            createObject();
        
        if (!creatingObject)
            creatingObject = true;
        
        double deltaX = e.getScreenX() - originMouseX;
        double deltaY = e.getScreenY() - originMouseY;
        double newWidth = (int)(squareFootprint ? Math.abs(Math.max(deltaX, deltaY)) : Math.abs(deltaX));
        double newHeight = (int)(squareFootprint ? Math.abs(Math.max(deltaX, deltaY)) : Math.abs(deltaY));
        double newX = Snapper.fix(originIsCenter ? originLayoutX - newWidth : (deltaX < 0 ? originLayoutX - Math.abs(deltaX) : originLayoutX));
        double newY = Snapper.fix(originIsCenter ? originLayoutY - newHeight : (deltaY < 0 ? originLayoutY - Math.abs(deltaY) : originLayoutY));
        
        if (originIsCenter) {
            newWidth *= 2;
            newHeight *= 2;
        }

        newObject.setWidth(newWidth);
        newObject.setHeight(newHeight);
        newObject.setTranslateX(newX);
        newObject.setTranslateY(newY);
        
    }
    
    public void mouseReleasedHandler(MouseEvent e){
        addObject();
        resetState();
        removeKeyboardModifiers();
    }
    
    public void keyPressedHandler(KeyEvent e) {
        switch(e.getCode()) {
            case DOWN:
                double arc = newObject.getArcHeight();
                newObject.setArcHeight(arc <= 0 ? 0 : arc - (Math.max(newObject.getWidth(), newObject.getHeight()) * (0.02)));
                newObject.setArcWidth(newObject.getArcHeight());
                break;
            case UP:
                newObject.setArcHeight(newObject.getArcHeight() + (Math.max(newObject.getWidth(), newObject.getHeight()) * (0.02)));
                newObject.setArcWidth(newObject.getArcHeight());
                break;
            case SHIFT:
                squareFootprint = true;
                newObject.setWidth(Math.max(newObject.getWidth(), newObject.getHeight()));
                newObject.setHeight(newObject.getWidth());
                if (originIsCenter) {
                    newObject.setTranslateX(originLayoutX - (newObject.getWidth() / 2));
                    newObject.setTranslateY(originLayoutY - (newObject.getHeight() / 2));
                }
                break;
            case ALT:
                originIsCenter = true;
                newObject.setTranslateX(newObject.getTranslateX() - newObject.getWidth());
                newObject.setTranslateY(newObject.getTranslateY() - newObject.getHeight());
                newObject.setWidth(newObject.getWidth() * 2);
                newObject.setHeight(newObject.getHeight() * 2);
                break;
        }
    }
    
    public void keyReleasedHandler(KeyEvent e) {
        switch(e.getCode()) {
            case SHIFT:
                squareFootprint = false;
                break;
            case ALT:
                originIsCenter = false;
                break;
        }
    }
    
    private void storeOriginMouseCoordinates(MouseEvent e) {
        originMouseX = e.getScreenX();
        originMouseY = e.getScreenY();
        originLayoutX = e.getX();
        originLayoutY = e.getY();
    }
    
    private void addKeyboardModifiers() {
        Context.getActiveDocumentPane().getScene().addEventFilter(KeyEvent.KEY_PRESSED, keyPressedFilter); 
        Context.getActiveDocumentPane().getScene().addEventFilter(KeyEvent.KEY_RELEASED, keyReleasedFilter);
    }
    
    private void removeKeyboardModifiers() {
        Context.getActiveDocumentPane().getScene().removeEventFilter(KeyEvent.KEY_PRESSED, keyPressedFilter);
        Context.getActiveDocumentPane().getScene().removeEventFilter(KeyEvent.KEY_RELEASED, keyReleasedFilter);
    }
    
    private void createObject() {
        newObject = new Rectangle();
        newObject.setFill(Color.TRANSPARENT);
        newObject.setStroke(Context.getActiveDocumentPane().getSelectedLayer().getLayerColor());
        newObject.setStrokeWidth(1);
        newObject.setTranslateX((int)originLayoutX + 0.5);
        newObject.setTranslateY((int)originLayoutY + 0.5);
        Context.getActiveDocumentPane().getChildren().add(newObject);
    }
    
    private void addObject() {
        applyContextToObject();
        Context.getSelectedLayer().getChildren().add(new VecRectangle(newObject, Context.getSelectedLayer()));
    }
    
    private void applyContextToObject() {
        newObject.setFill(Context.getFillColor());
        newObject.setStroke(Context.getStrokeColor());
    }
    
    private void resetState() {
        creatingObject = false;
        newObject = null;
    }
}
