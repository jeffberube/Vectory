/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vectory;

import java.util.ArrayList;
import java.util.Iterator;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;

/**
 *
 * @author Berube
 */
public class Context {
    private static Tool tool = null;
    
    private final static SimpleObjectProperty<VecDocument> activeDocument = new SimpleObjectProperty();
    private final static SimpleObjectProperty<DocumentPane> activeDocumentPane = new SimpleObjectProperty();
    private final static SimpleObjectProperty<Layer> selectedLayer = new SimpleObjectProperty();
    
    private final static SimpleObjectProperty<Color> fillColor = new SimpleObjectProperty(Color.WHITE);
    private final static SimpleObjectProperty<Color> strokeColor = new SimpleObjectProperty(Color.BLACK);
    
    private static ArrayList<VecObject> selectedObjects = new ArrayList();
    
    public VecDocument getActiveDocument() {
        return activeDocument.get();
    }
    
    public static void setActiveDocument(VecDocument document) {
        activeDocument.set(document);
        activeDocumentPane.set(activeDocument.get().getDocumentPane());
    }
    
    public static SimpleObjectProperty<VecDocument> activeDocumentProperty() {
        return activeDocument;
    }
    
    public static DocumentPane getActiveDocumentPane() {
        return activeDocumentPane.get();
    }
        
    public static SimpleObjectProperty<DocumentPane> activeDocumentPaneProperty() {
        return activeDocumentPane;
    }
    
    public static Layer getSelectedLayer() {
        return activeDocumentPane.get().getSelectedLayer();
    }
    
    public static ArrayList<VecObject> getSelectedObjects() {
        return selectedObjects;
    }
    
    public static void addSelectedObject(VecObject object) {
        selectedObjects.add(object);
        if (selectedObjects.size() == 1) {
            fillColor.bindBidirectional(object.fillColorProperty());
            strokeColor.bindBidirectional(object.strokeColorProperty());
            //object.fillColorProperty().bindBidirectional(fillColor);
            //object.strokeColorProperty().bindBidirectional(strokeColor);
        }   
    }
    
    public static void deselectAllObjects() {
        Iterator iterator = selectedObjects.iterator();
        VecObject object;
        
        while(iterator.hasNext()) {
            object = (VecObject)iterator.next();
            object.setSelected(false);
            object.fillColorProperty().unbindBidirectional(fillColor);
            object.strokeColorProperty().unbindBidirectional(strokeColor);
        }
        
        selectedObjects.clear();
    }
    
    public static Color getFillColor() {
        return fillColor.getValue();
    }
    
    public static void setFillColor(Color color) {
        fillColor.set(color);
    }
    
    public static SimpleObjectProperty<Color> fillColorProperty() {
        return fillColor;
    }
    
    public static Color getStrokeColor() {
        return strokeColor.getValue();
    }
    
    public static void setStrokeColor(Color color) {
        strokeColor.set(color);
    }
    
    public static SimpleObjectProperty<Color> strokeColorProperty() {
        return strokeColor;
    }
    
    public static Tool getTool() {
        if (tool == null)
            tool = SelectTool.getInstance();
        
        return tool;
    }
    
    public static void setTool(Tool selectedTool) {
        tool = selectedTool;
    }
    
}
