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
public interface Tool {
    public abstract Cursor getCursor();
    
    public abstract void mouseEnteredHandler(MouseEvent e);
    
    public abstract void mousePressedHandler(MouseEvent e);
    
    public abstract void mouseDraggedHandler(MouseEvent e);
    
    public abstract void mouseReleasedHandler(MouseEvent e);
}
