/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vectory;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Berube
 */
public class SelectTool implements Tool {
    private static SelectTool instance = null;
    
    private boolean selectMultiple = false;
    
    private SelectTool() {
    
    }
    
    public static SelectTool getInstance() {
        if (instance == null)
            instance = new SelectTool();
        
        return instance;
    }
    
    public Cursor getCursor() {
        return Cursor.DEFAULT;
    }
    
    public void mouseEnteredHandler(MouseEvent e) {
        if (!(e.getTarget() instanceof ResizeHandle) && !(e.getTarget() instanceof RotationHotspot))
            Context.getActiveDocumentPane().setCursor(getCursor());
    }
    
    public void mousePressedHandler(MouseEvent e) {
        Node target = (Node)e.getTarget();
        Node parent = (Node)target.getParent();
        
        //System.out.println(target + " " + parent + " " + parent.getParent() + " " + e.isShiftDown());
        
        if ((!(parent instanceof VecObject) && !(parent instanceof TransformationBox))
                || (parent instanceof VecObject && !e.isShiftDown()))         
            Context.deselectAllObjects();
        
        else if (target instanceof VecObject)
            ((VecObject)parent).setSelected(true);
    }
    
    public void mouseDraggedHandler(MouseEvent e){
        
    }
    
    public void mouseReleasedHandler(MouseEvent e){
        
    }
    
}
