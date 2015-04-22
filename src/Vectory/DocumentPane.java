/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vectory;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Berube
 */
public class DocumentPane extends Pane {
    private final Pane canvas = new Pane();

    private LayerGroup rootGroup = new LayerGroup("root");
    private final LayerManager layerManager = new LayerManager(rootGroup);

    public DocumentPane() {
        super();
        init();
    }
    
    private void init() {
        setupMouseEventHandlers();
        initStyle();
        initCanvas();
        initRootGroup();

        getChildren().addAll(canvas, rootGroup);
    }
    
    private void initStyle() {
        getStylesheets().add(DocumentPane.class.getResource("assets/css/DocumentPane.css").toExternalForm());
        getStyleClass().add("documentPane");
    }
    
    private void initCanvas() {
        canvas.getStyleClass().add("canvas");
        canvas.setPrefWidth(Constants.DEFAULT_CANVAS_WIDTH);
        canvas.setPrefHeight(Constants.DEFAULT_CANVAS_HEIGHT);
        canvas.translateXProperty().bind(widthProperty().subtract(canvas.widthProperty()).divide(2));
        canvas.translateYProperty().bind(heightProperty().subtract(canvas.heightProperty()).divide(2));
    }
    
    private void initRootGroup() {
        rootGroup.addLayer(layerManager.newLayer());
    }
    
    private void setupMouseEventHandlers() {
        setOnMouseEntered(e->Context.getTool().mouseEnteredHandler(e));
        setOnMousePressed(e->Context.getTool().mousePressedHandler(e));
        setOnMouseDragged(e->Context.getTool().mouseDraggedHandler(e));
        setOnMouseReleased(e->Context.getTool().mouseReleasedHandler(e));
    }
    
    public void setRoot(LayerGroup group) {
        rootGroup = group;
    }
    
    public LayerGroup getRoot() {
        return rootGroup;
    }
    
    public LayerManager getLayerManager() {
        return layerManager;
    }
    
    public Layer getSelectedLayer() {
        return layerManager.getSelectedLayer();
    }
    
    /*
    private void setShowRulers(boolean value) {
        showRulers = value;
    }
    
    private boolean getShowRulers() {
        return showRulers;
    }
    */
}
