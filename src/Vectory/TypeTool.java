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
public class TypeTool implements Tool {
    private static TypeTool instance = null;
    
    private TypeTool() {
    
    }
    
    public static TypeTool getInstance() {
        if (instance == null)
            instance = new TypeTool();
        
        return instance;
    }
    
    public Cursor getCursor() {
        return Cursor.TEXT;
    }
    
    public void mousePressedHandler(MouseEvent e) {
    
    }
    
    public void mouseDraggedHandler(MouseEvent e){
    
    }
    
    public void mouseReleasedHandler(MouseEvent e){
    
    }
    
}
