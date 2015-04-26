/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vectory;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

/**
 *
 * @author Berube
 */
public class EllipseTool implements Tool {
    private static EllipseTool instance = null;
    
    private double originMouseX = 0;
    private double originMouseY = 0;
    private double originLayoutX = 0;
    private double originLayoutY = 0;
    
    private final String tooltipString = "UP/DOWN: Increase / decrease corner radius\n"
                + "SHIFT: Circle\n"
                + "ALT: Origin is center";
    
    private Tooltip modKeysTooltip = null;

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
    
    private Ellipse newObject;
    
    private EllipseTool() {
    
    }
    
    public static EllipseTool getInstance() {
        if (instance == null)
            instance = new EllipseTool();
        
        return instance;
    }
    
    public Cursor getCursor() {
        return Cursor.CROSSHAIR;
    }
    
    public void mouseEnteredHandler(MouseEvent e) {
        Context.getActiveDocumentPane().setCursor(this.getCursor());
        e.consume();
    }
    
    public void mousePressedHandler(MouseEvent e) {
        Context.deselectAllObjects();
        storeOriginMouseCoordinates(e);
        addKeyboardModifiers();
        ViewOptions.setModKeysTooltipString(tooltipString);
        e.consume();
    }
    
    public void mouseDraggedHandler(MouseEvent e){
        if (newObject == null) 
            createObject();
        
        if (!creatingObject)
            creatingObject = true;
        
        ViewOptions.updateTooltipLocation(e.getScreenX() + 16, e.getScreenY() + 16);
        
        double deltaX = e.getScreenX() - originMouseX;
        double deltaY = e.getScreenY() - originMouseY;
        double newWidth = (int)(e.isShiftDown() ? Math.abs(Math.max(deltaX, deltaY)) : Math.abs(deltaX));
        double newHeight = (int)(e.isShiftDown() ? Math.abs(Math.max(deltaX, deltaY)) : Math.abs(deltaY));
        double newX = Snapper.fix(e.isAltDown() ? originLayoutX : originLayoutX + (newWidth / 2));
        double newY = Snapper.fix(e.isAltDown() ? originLayoutY : originLayoutY + (newHeight /2));
        
        if (e.isAltDown()) {
            newWidth *= 2;
            newHeight *= 2;
        }

        newObject.setRadiusX(newWidth / 2);
        newObject.setRadiusY(newHeight  / 2);
        newObject.setTranslateX(newX);
        newObject.setTranslateY(newY);
    }
    
    public void mouseReleasedHandler(MouseEvent e){
        ViewOptions.hideTooltip();   
        addObject();
        resetState();
        removeKeyboardModifiers();
    }
    
    public void keyPressedHandler(KeyEvent e) {
        double objectWidth = newObject.getRadiusX() * 2;
        double objectHeight = newObject.getRadiusY() * 2;
        
        switch(e.getCode()) {
            case SHIFT:
                newObject.setRadiusX(Math.max(objectWidth, objectHeight));
                newObject.setRadiusY(objectWidth);
                if (originIsCenter) {
                    newObject.setTranslateX(originLayoutX - (objectWidth / 2));
                    newObject.setTranslateY(originLayoutY - (objectHeight / 2));
                }
                break;
            case ALT:
                newObject.setTranslateX(newObject.getTranslateX() - objectWidth);
                newObject.setTranslateY(newObject.getTranslateY() - objectHeight);
                newObject.setRadiusX(objectWidth);
                newObject.setRadiusY(objectHeight);
                break;
        }
        
        e.consume();
    }
    
     public void keyReleasedHandler(KeyEvent e) {

    }
    
    private void storeOriginMouseCoordinates(MouseEvent e) {
        originMouseX = e.getScreenX();
        originMouseY = e.getScreenY();
        originLayoutX = e.getX();
        originLayoutY = e.getY();
    }
    
    private void addKeyboardModifiers() {
        Context.getActiveDocumentPane().getScene().addEventFilter(KeyEvent.KEY_PRESSED, keyPressedFilter); 
        Context.getActiveDocumentPane().getScene().addEventFilter(KeyEvent.KEY_PRESSED, keyReleasedFilter); 
    }
    
    private void removeKeyboardModifiers() {
        Context.getActiveDocumentPane().getScene().removeEventFilter(KeyEvent.KEY_PRESSED, keyPressedFilter);
        Context.getActiveDocumentPane().getScene().removeEventFilter(KeyEvent.KEY_PRESSED, keyReleasedFilter); 
    }
    
    private void createObject() {
        newObject = new Ellipse();
        newObject.setFill(Color.TRANSPARENT);
        newObject.setStroke(Context.getActiveDocumentPane().getSelectedLayer().getLayerColor());
        newObject.setStrokeWidth(1);
        newObject.setTranslateX((int)originLayoutX + 0.5);
        newObject.setTranslateY((int)originLayoutY + 0.5);
        Context.getActiveDocumentPane().getChildren().add(newObject);
    }
    
    private void addObject() {
        VecEllipse ve = new VecEllipse(newObject, Context.getSelectedLayer());

        ve.setTranslateX(newObject.getTranslateX() - newObject.getRadiusX());
        ve.setTranslateY(newObject.getTranslateY() - newObject.getRadiusY());
        
        newObject.setTranslateX(0);
        newObject.setTranslateY(0);

        applyContextToObject(ve);
        Context.getSelectedLayer().getChildren().add(ve);
        //vr.setSelected(true);
    }
    
    private void applyContextToObject(VecEllipse ve) {
        ve.setFillColor(Context.getFillColor());
        ve.setStrokeColor(Context.getStrokeColor());
    }
    
    private void resetState() {
        creatingObject = false;
        newObject = null;
    }
    
}
