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
public class SquiggleTool implements Tool {
    private static SquiggleTool instance = null;
    
    private SquiggleTool() {
    
    }
    
    public static SquiggleTool getInstance() {
        if (instance == null)
            instance = new SquiggleTool();
        
        return instance;
    }
    
    public Cursor getCursor() {
        return Cursor.DEFAULT;
    }
    
    public void mousePressedHandler(MouseEvent e) {
    
    }
    
    public void mouseDraggedHandler(MouseEvent e){
    
    }
    
    public void mouseReleasedHandler(MouseEvent e){
    
    }
    
}
