/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vectory;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Berube
 */
public class MainWindowPane extends Pane {
    private static MainWindowPane instance = null;
    
    private final HeaderPane header = HeaderPane.getInstance();
    private final WorkAreaPane documentArea = WorkAreaPane.getInstance();
    private final StatusBarPane statusBar = StatusBarPane.getInstance();
 
    public static MainWindowPane getInstance(Stage stage) {
        if (instance == null)
            instance = new MainWindowPane(stage);
        
        return instance;
    }
    
    private MainWindowPane(Stage stage) {
        super();
        init(stage);
    }
    
    private void init(Stage stage) {
        getStylesheets().add(MainWindowPane.class.getResource("assets/css/MainWindow.css").toExternalForm());
        getStyleClass().add("mainWindow");
        setId("mainWindow");
        
        getChildren().addAll(header, documentArea, statusBar);
        
        initHeader();
        initDocumentArea();
        initStatusBar();
    }
    
    private void initHeader() {
        header.prefWidthProperty().bind(widthProperty().subtract(Constants.BORDER_WIDTH * 2));
        header.setTranslateX(Constants.BORDER_WIDTH);
        header.setTranslateY(Constants.BORDER_WIDTH);
    }
    
    private void initDocumentArea() {
        documentArea.setTranslateX(Constants.BORDER_WIDTH);
        documentArea.translateYProperty().bind(header.heightProperty().add(1));
        documentArea.prefHeightProperty().bind(heightProperty().subtract(header.heightProperty()).subtract(statusBar.heightProperty()).subtract(Constants.BORDER_WIDTH * 2));
        documentArea.prefWidthProperty().bind(widthProperty().subtract(Constants.BORDER_WIDTH * 2));
    }
    
    private void initStatusBar() {
        statusBar.translateYProperty().bind(heightProperty().subtract(statusBar.heightProperty()).subtract(Constants.BORDER_WIDTH));
        statusBar.prefWidthProperty().bind(widthProperty());
    }
    
}
