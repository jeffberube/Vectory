/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vectory;

import javafx.application.Platform;
import javafx.geometry.BoundingBox;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author Berube
 */
public class MainWindowControls extends HBox {
    private final Image windowControlsSprites = new Image(getClass().getResourceAsStream("assets/images/window_controls.png"));
    private final ImageView minimizeIcon = new ImageView(new WritableImage(windowControlsSprites.getPixelReader(), 0, 0, 12, 10));
    private final ImageView maximizeIcon = new ImageView(new WritableImage(windowControlsSprites.getPixelReader(), 16, 0, 12, 10));
    private final ImageView restoreIcon = new ImageView(new WritableImage(windowControlsSprites.getPixelReader(), 32, 0, 12, 10));
    private final ImageView closeIcon = new ImageView(new WritableImage(windowControlsSprites.getPixelReader(), 48, 0, 12, 10));
    
    private final PopupButton minimizeButton = new PopupButton(minimizeIcon);
    private final PopupButton maximizeRestoreButton = new PopupButton(maximizeIcon);
    private final PopupButton closeButton = new PopupButton(closeIcon);
    
    private BoundingBox savedBounds;
    private MainWindowPane mainWindow;
    
    private static boolean maximized = false;

    public MainWindowControls() {
        super();
        init();
    }
    
    private void init() {
        initMinimizeButton();
        initMaximizeRestoreButton();
        initCloseButton();
        
        getChildren().addAll(minimizeButton, maximizeRestoreButton, closeButton);
        getStylesheets().add(ToolbarButton.class.getResource("assets/css/WindowControls.css").toExternalForm());
    }
    
    private void initMinimizeButton() {
        minimizeButton.getStyleClass().add("windowControls");
        Tooltip.install(minimizeButton, new Tooltip("Minimize Window"));
        minimizeButton.setOnAction(e->((Stage)getScene().getWindow()).setIconified(true));
    }
    
    private void initMaximizeRestoreButton() { 
        maximizeRestoreButton.getStyleClass().add("windowControls");
        Tooltip.install(maximizeRestoreButton, new Tooltip("Maximize Window"));
        maximizeRestoreButton.setOnAction(e->toggleMaximized());
    }
    
    private void initCloseButton() {
        closeButton.getStyleClass().add("windowControls");
        closeButton.getStyleClass().add("closeButton");
        closeButton.setId("closeButton");
        Tooltip.install(closeButton, new Tooltip("Exit"));
        closeButton.setOnAction(e->Platform.exit());
    }
    
    private void saveBounds() {
        savedBounds = new BoundingBox(mainWindow.getTranslateX(), mainWindow.getTranslateY(), mainWindow.getPrefWidth(), mainWindow.getPrefHeight());
    }
    
    private void restoreBounds() {
        mainWindow.setTranslateX(savedBounds.getMinX());
        mainWindow.setTranslateY(savedBounds.getMinY());
        mainWindow.setPrefWidth(savedBounds.getWidth());
        mainWindow.setPrefHeight(savedBounds.getHeight());
        savedBounds = null;
        
        
        maximizeRestoreButton.setGraphic(maximizeIcon);
        Tooltip.uninstall(maximizeRestoreButton, maximizeRestoreButton.getTooltip());
        Tooltip.install(maximizeRestoreButton, new Tooltip("Maximize Window"));
    }
    
    private void maximizeWindow() {
        saveBounds();
        
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        mainWindow.setTranslateX(0);
        mainWindow.setTranslateY(0);
        mainWindow.setPrefWidth(visualBounds.getWidth());
        mainWindow.setPrefHeight(visualBounds.getHeight());
        
        maximizeRestoreButton.setGraphic(restoreIcon);
        Tooltip.uninstall(maximizeRestoreButton, maximizeRestoreButton.getTooltip());
        Tooltip.install(maximizeRestoreButton, new Tooltip("Restore Window"));
    }
    
    public void toggleMaximized() {
        if (mainWindow == null) 
                    mainWindow = (MainWindowPane)getScene().getRoot().lookup("#mainWindow");
            
        if (maximized)
            restoreBounds();        
        else
            maximizeWindow();
            
        maximized = !maximized;
    }
    
    public static boolean isMaximized() {
        return maximized;
    }
    
}
