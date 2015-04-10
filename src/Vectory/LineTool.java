/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vectory;

import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Berube
 */
public class LineTool implements Tool {
    private static LineTool instance = null;
    
    private LineTool() {
    
    }
    
    public static LineTool getInstance() {
        if (instance == null)
            instance = new LineTool();
        
        return instance;
    }
    
    public Cursor getCursor() {
        return Cursor.CROSSHAIR;
    }
    
    public void mousePressedHandler(MouseEvent e) {
    
    }
    
    public void mouseDraggedHandler(MouseEvent e){
    
    }
    
    public void mouseReleasedHandler(MouseEvent e){
    
    }
    
}
