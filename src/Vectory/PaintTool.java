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
public class PaintTool implements Tool {
    private static PaintTool instance = null;
    
    private PaintTool() {
    
    }
    
    public static PaintTool getInstance() {
        if (instance == null)
            instance = new PaintTool();
        
        return instance;
    }
    
    public Cursor getCursor() {
        return Cursor.CROSSHAIR;
    }
    
    public void mouseEnteredHandler(MouseEvent e) {
        Context.getActiveDocumentPane().setCursor(getCursor());
    }
    
    public void mousePressedHandler(MouseEvent e) {
    
    }
    
    public void mouseDraggedHandler(MouseEvent e){
    
    }
    
    public void mouseReleasedHandler(MouseEvent e){
    
    }
    
}