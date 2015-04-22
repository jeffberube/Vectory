/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vectory;

import javafx.scene.shape.Rectangle;

/**
 *
 * @author Berube
 */
public class ResizeHandle extends Rectangle {
    public static final int TOP_LEFT = 0;
    public static final int TOP = 1;
    public static final int TOP_RIGHT = 2;
    public static final int RIGHT = 3;
    public static final int BOTTOM_RIGHT = 4;
    public static final int BOTTOM = 5;
    public static final int BOTTOM_LEFT = 6;
    public static final int LEFT = 7;
    
    private int handlePosition  = 0;
 
    public ResizeHandle(int position) {
        super(0, 0, Constants.TRANSFORMATION_BOX_HANDLE_WIDTH, Constants.TRANSFORMATION_BOX_HANDLE_HEIGHT);
        handlePosition = position;
    }
    
    public void setHandlePosition(int value) {
        handlePosition = value;
    }
    
    public int getHandlePosition() {
        return handlePosition;
    }
}
