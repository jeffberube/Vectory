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
    
    private final String tooltipString = "UP/DOWN: Increase / decrease corner radius\n"
                + "SHIFT: Square bounding box\n"
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
        double newX = Snapper.fix(e.isAltDown() ? originLayoutX - newWidth : (deltaX < 0 ? originLayoutX - Math.abs(deltaX) : originLayoutX));
        double newY = Snapper.fix(e.isAltDown() ? originLayoutY - newHeight : (deltaY < 0 ? originLayoutY - Math.abs(deltaY) : originLayoutY));
        
        if (e.isAltDown()) {
            newWidth *= 2;
            newHeight *= 2;
        }

        newObject.setWidth(newWidth);
        newObject.setHeight(newHeight);
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
                newObject.setWidth(Math.max(newObject.getWidth(), newObject.getHeight()));
                newObject.setHeight(newObject.getWidth());
                if (originIsCenter) {
                    newObject.setTranslateX(originLayoutX - (newObject.getWidth() / 2));
                    newObject.setTranslateY(originLayoutY - (newObject.getHeight() / 2));
                }
                break;
            case ALT:
                newObject.setTranslateX(newObject.getTranslateX() - newObject.getWidth());
                newObject.setTranslateY(newObject.getTranslateY() - newObject.getHeight());
                newObject.setWidth(newObject.getWidth() * 2);
                newObject.setHeight(newObject.getHeight() * 2);
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
        newObject = new Rectangle();
        newObject.setFill(Color.TRANSPARENT);
        newObject.setStroke(Context.getActiveDocumentPane().getSelectedLayer().getLayerColor());
        newObject.setStrokeWidth(1);
        newObject.setTranslateX((int)originLayoutX + 0.5);
        newObject.setTranslateY((int)originLayoutY + 0.5);
        Context.getActiveDocumentPane().getChildren().add(newObject);
    }
    
    private void addObject() {
        VecRectangle vr = new VecRectangle(newObject, Context.getSelectedLayer());

        vr.setTranslateX(newObject.getTranslateX());
        vr.setTranslateY(newObject.getTranslateY());
        
        newObject.setTranslateX(0);
        newObject.setTranslateY(0);

        applyContextToObject();
        Context.getSelectedLayer().getChildren().add(vr);
        //vr.setSelected(true);
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
