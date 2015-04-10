/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vectory;

import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author Berube
 */
public class WorkAreaPane extends Pane {
    private static WorkAreaPane instance = null;
    
    private final ScrollPane documentView = new ScrollPane();
    private final ScrollBar verticalScroll = new ScrollBar();
    private final ScrollBar horizontalScroll = new ScrollBar();
    
    public static WorkAreaPane getInstance() {
        if (instance == null)
            instance = new WorkAreaPane();
        
        return instance;
    }
    
    private WorkAreaPane() {
        super();
        init();  
    }
    
    private void init() {
        getStylesheets().add(WorkAreaPane.class.getResource("assets/css/WorkAreaPane.css").toExternalForm());
        getStyleClass().add("workAreaPane");
        
        initDocumentView();
        getChildren().addAll(documentView);
    }
    
    private void initDocumentView() {
        documentView.getStyleClass().add("documentView");
        documentView.setTranslateX(Constants.BORDER_WIDTH);
        documentView.setTranslateY(Constants.WORKAREA_TOPBAR_HEIGHT);
        documentView.prefWidthProperty().bind(widthProperty().subtract(Constants.BORDER_WIDTH * 2));
        documentView.prefHeightProperty().bind(heightProperty().subtract(Constants.WORKAREA_TOPBAR_HEIGHT + Constants.WORKAREA_BOTTOMBAR_HEIGHT));
        
        documentView.setFitToHeight(true);
        documentView.setFitToWidth(true);
        
        documentView.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        documentView.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        documentView.contentProperty().bind(Context.activeDocumentPaneProperty());
    }
    
}
