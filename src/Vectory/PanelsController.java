/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vectory;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

/**
 *
 * @author Berube
 */
public class PanelsController {
    private RootPane rootPane;
    
    private final Toolbar toolbar = Toolbar.getInstance();
    private final LayersPanel layersPanel = LayersPanel.getInstance();
    
    Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
    
    public PanelsController(RootPane rootPane) {
        this.rootPane = rootPane;
        init();
    }
    
    private void init() {
        initToolbar();
        rootPane.getChildren().add(toolbar);
    }
    
    private void initToolbar() {
        toolbar.setTranslateX(((visualBounds.getWidth() - Constants.MAIN_WINDOW_DEFAULT_WIDTH) / 2) - (Constants.TOOLBAR_WIDTH / 3));
        toolbar.setTranslateY((visualBounds.getHeight() - Constants.TOOLBAR_HEIGHT) / 2);
    }
    
    public void showToolbar() {
        toolbar.setVisible(true);
        toolbar.toFront();
    }
    
    public void hideToolbar() {
        toolbar.setVisible(false);
    }
    
    private void createLayersPanel() {
    
    }
    
    private void createFontPanel() {
    
    }
    
}
