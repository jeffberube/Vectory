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
public class HeaderPane extends Pane {
    private static HeaderPane instance = null;
    
    private MainWindowPane mainWindow;
    private WritableImage snapshot;
    private ImageView mainWindowSnapshot;
    private boolean dragging = false;
    
    private double originMouseX = 0;
    private double originMouseY = 0;
    private double originWindowX = 0;
    private double originWindowY = 0;

    private final Rectangle backgroundGradient = new Rectangle(0, 0, 0, 0);
    private final MenuBar menuBar = MenuBar.getInstance();
    private final DocumentBar documentBar = new DocumentBar();
    private final MainWindowControls windowControls = new MainWindowControls(); 
    private final ImageView logo = new ImageView(new Image(getClass().getResourceAsStream("assets/images/icon.png")));
    
    public static HeaderPane getInstance() {
        if (instance == null)
            instance = new HeaderPane();
        
        return instance;
    }
    
    private HeaderPane() {
        super();
        init();
        getChildren().addAll(backgroundGradient, logo, createSeparator(), menuBar, documentBar, windowControls);
    }
    
    private void init() { 
        prefHeightProperty().bind(heightProperty().multiply(0).add(70));

        setupWindowDrag();

        initBackgroundGradient();
        initLogo();
        initMenuBar();
        initDocumentBar();
        initWindowControls();
    }
    
    private void setupWindowDrag() {
        setOnMousePressed(e->{
            if (!MainWindowControls.isMaximized()) {
                if (mainWindow == null) 
                    mainWindow = (MainWindowPane)getScene().getRoot().lookup("#mainWindow");
                
                originMouseX = e.getScreenX();
                originMouseY = e.getScreenY();
                originWindowX = mainWindow.getTranslateX();
                originWindowY = mainWindow.getTranslateY(); 
            }
        });
        
        setOnMouseDragged(e->{
            if (!MainWindowControls.isMaximized()) {
                dragging = true;
                
                if (mainWindow == null) 
                    mainWindow = (MainWindowPane)getScene().getRoot().lookup("#mainWindow");
                
                if (mainWindowSnapshot == null) {
                    snapshot = mainWindow.snapshot(null, null);
                    mainWindowSnapshot = new ImageView(snapshot);
                    mainWindowSnapshot.setCache(true);

                    mainWindowSnapshot.setTranslateX((int)mainWindow.getTranslateX() + 0.5);
                    mainWindowSnapshot.setTranslateY((int)mainWindow.getTranslateY() + 0.5);

                    ((RootPane)getScene().getRoot()).getChildren().add(mainWindowSnapshot);
                    
                    mainWindow.setVisible(false);
                }
                
                mainWindowSnapshot.toBack();
                
                mainWindowSnapshot.setTranslateX((int)(originWindowX - (originMouseX - e.getScreenX())));
                mainWindowSnapshot.setTranslateY((int)(originWindowY - (originMouseY - e.getScreenY())));
            } 
        });
        
        setOnMouseReleased(e->{
            if (dragging && !MainWindowControls.isMaximized()) {
                if (mainWindow == null) 
                    mainWindow = (MainWindowPane)getScene().getRoot().lookup("#mainWindow");
                
                mainWindow.setTranslateX(originWindowX - (originMouseX - e.getScreenX()));
                mainWindow.setTranslateY(originWindowY - (originMouseY - e.getScreenY()));
                
                mainWindow.setVisible(true);
                
                ((RootPane)getScene().getRoot()).getChildren().remove(mainWindowSnapshot);
                
                mainWindowSnapshot = null;
                
                dragging = false;
            }  
        });
        
        setOnMouseClicked(e->{
            if (e.getClickCount() == 2) windowControls.toggleMaximized();
        });
    }
    
    private void initBackgroundGradient() {
        //Stop[] stops = new Stop[] {new Stop(0, Color.web("#444444")), new Stop(1, Color.web("#222222"))};
        Stop[] stops = new Stop[] {new Stop(0, Color.web("#222222")), 
            new Stop(0.5, Color.web("#444444")), 
            new Stop(0.5, Color.web("#222222")),
            new Stop(1, Color.web("#222222"))};
        LinearGradient lg = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
        backgroundGradient.setFill(lg);
        backgroundGradient.widthProperty().bind(widthProperty());
        backgroundGradient.heightProperty().bind(heightProperty());
    }
    
    private void initMenuBar() {
        menuBar.setTranslateX(72);
        menuBar.setTranslateY(6);
    }
    
    private void initDocumentBar() {
        documentBar.setTranslateX(72);
        documentBar.setTranslateY(44);
        documentBar.addDocument(new VecDocument("Untitled.vec*"));
        documentBar.addDocument(new VecDocument("Untitled-2.vec*"));
    }
    
    private void initWindowControls() {
        windowControls.translateXProperty().bind(widthProperty().subtract(windowControls.widthProperty()).subtract(4));
        windowControls.setTranslateY(4);
    }
    
    private void initLogo() {
        logo.setPreserveRatio(true);
        logo.setFitHeight(32);
        logo.setFitWidth(32);
        logo.setTranslateX(16.5);
        logo.setTranslateY(19.5);
    }
    
    private Separator createSeparator() {
        Separator separator = new Separator(54, Separator.LineOrientation.VERTICAL, 
                Color.web("#222222"), Color.web("#444444"));
        
        separator.setTranslateX(62);
        separator.setTranslateY(8);
        return separator;
    }
    
}
