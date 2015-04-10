/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vectory;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Berube
 */
public class FloatingPanel extends Pane {
    protected final Pane titleBar = new Pane();
    private Pane content;
    
    private WritableImage snapshot;
    private ImageView panelSnapshot;
    private boolean dragging = false;
    
    private double originMouseX = 0;
    private double originMouseY = 0;
    private double originWindowX = 0;
    private double originWindowY = 0;
    
    private final Image windowControlsSprites = new Image(getClass().getResourceAsStream("assets/images/window_controls.png"));
    private final ImageView closeIcon = new ImageView(new WritableImage(windowControlsSprites.getPixelReader(), 48, 0, 12, 10));
    private final PopupButton closeButton = new PopupButton(closeIcon);
    
    private final Rectangle backgroundGradient = new Rectangle(0, 0, 0, 0);
    
    private boolean resizable = false;
    
    public FloatingPanel() {
        super();
        init();
    }
    
    private void init() {
        initTitleBar();
        setupPanelDrag();
        
        getStylesheets().add(FloatingPanel.class.getResource("assets/css/FloatingPanel.css").toExternalForm());
        getStyleClass().add("floatingPanel");
        setId("floatingPanel");
        
        setStyle("-fx-background-color: #222222");
        
        getChildren().addAll(titleBar);
        toFront();
    }
    
    private void initTitleBar() {
        initBackgroundGradient();
        initCloseButton();
        
        titleBar.prefHeightProperty().bind(closeButton.heightProperty());
        titleBar.getChildren().addAll(backgroundGradient, closeButton);
    }
    
    private void initBackgroundGradient() {
        Stop[] stops = new Stop[] {new Stop(0, Color.web("#222222")), new Stop(1, Color.web("#444444"))};
        LinearGradient lg = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
        backgroundGradient.setFill(lg);
        backgroundGradient.widthProperty().bind(widthProperty().subtract(Constants.BORDER_WIDTH * 2));
        backgroundGradient.setHeight(24);
        backgroundGradient.relocate(Constants.BORDER_WIDTH, Constants.BORDER_WIDTH);
    }
    
    private void initCloseButton() {
        closeButton.getStyleClass().add("closeButton");
        closeButton.setId("closeButton");
        closeButton.translateXProperty().bind(widthProperty().subtract(closeButton.widthProperty()).subtract(Constants.BORDER_WIDTH * 2));
        closeButton.setTranslateY(4);
        closeButton.setOnAction(e->this.setVisible(false));
    }
    
    private void setupPanelDrag() {
        setOnMousePressed(e->{
            originMouseX = e.getScreenX();
            originMouseY = e.getScreenY();
            originWindowX = getTranslateX();
            originWindowY = getTranslateY();
        });
        
        setOnMouseDragged(e->{
            dragging = true;

            if (panelSnapshot == null) {
                snapshot = snapshot(null, null);
                panelSnapshot = new ImageView(snapshot);
                panelSnapshot.setCache(true);

                panelSnapshot.setTranslateX((int) getTranslateX() + 0.5);
                panelSnapshot.setTranslateY((int) getTranslateY() + 0.5);

                ((RootPane) getScene().getRoot()).getChildren().add(panelSnapshot);

                setVisible(false);
            }

            panelSnapshot.toFront();

            panelSnapshot.setTranslateX((int) (originWindowX - (originMouseX - e.getScreenX())));
            panelSnapshot.setTranslateY((int) (originWindowY - (originMouseY - e.getScreenY())));
        });
        
        setOnMouseReleased(e->{
            if (dragging) {
                setTranslateX(originWindowX - (originMouseX - e.getScreenX()));
                setTranslateY(originWindowY - (originMouseY - e.getScreenY()));
                
                setVisible(true);
                
                ((RootPane)getScene().getRoot()).getChildren().remove(panelSnapshot);
                
                panelSnapshot = null;
                
                dragging = false;
            }  
        });
    
    }
    
    protected void setContent(Pane content) {
        this.content = content;
        
        getChildren().add(content);
        
        content.translateYProperty().bind(backgroundGradient.heightProperty());
        titleBar.prefWidthProperty().bind(content.widthProperty());

        prefHeightProperty().bind(titleBar.heightProperty().add(content.heightProperty()));
        prefWidthProperty().bind(content.widthProperty());
    }
    
    protected void setResizable(boolean value) {
        resizable = value;
    }
    
}
