/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vectory;

/**
 *
 * @author Berube
 */
public class LayersPanel extends FloatingPanel {
    private static LayersPanel instance = null;
    
    public static LayersPanel getInstance() {
        if (instance == null)
            instance = new LayersPanel();
        
        return instance;
    }
    
    private LayersPanel() {
        super();
        init();
    }
    
    private void init() {
    
    }
    
}
