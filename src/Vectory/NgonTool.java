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
public class NgonTool implements Tool {
    private static NgonTool instance = null;
    
    private NgonTool() {
    
    }
    
    public static NgonTool getInstance() {
        if (instance == null)
            instance = new NgonTool();
        
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
