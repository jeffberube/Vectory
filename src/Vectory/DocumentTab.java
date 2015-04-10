/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vectory;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;

/**
 *
 * @author Berube
 */
public class DocumentTab extends ToggleButton {
    
    private VecDocument document;
    
    private final Image windowControlsSprites = new Image(getClass().getResourceAsStream("assets/images/window_controls.png"));
    private final ImageView closeIcon = new ImageView(new WritableImage(windowControlsSprites.getPixelReader(), 48, 0, 12, 10));
    private final PopupButton closeButton = new PopupButton(closeIcon);
    
    public DocumentTab(VecDocument document) {
        super();
        init(document);  
    }
    
    private void init(VecDocument document) {
        this.document = document;
        setText(document.getDocumentName());
        
        getStylesheets().add(DocumentTab.class.getResource("assets/css/DocumentTab.css").toExternalForm());
        getStyleClass().add("documentTab");

        closeButton.setTranslateY(1);
        closeButton.getStyleClass().add("noPadding");
        closeButton.setId("closeButton");
        
        setGraphic(closeButton);
        setContentDisplay(ContentDisplay.RIGHT);
    }
    
    public VecDocument getDocument() {
        return document;
    }
    
}
