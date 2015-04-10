/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vectory;

import javafx.scene.Node;
import javafx.scene.control.Button;

/**
 *
 * @author Berube
 */
public class PopupButton extends Button {
    
    public PopupButton(String value) {
        super();
        init();
        setText(value);
    }
    
    public PopupButton(Node image) {
        super();
        init();
        setGraphic(image);
    }
    
    private void init() {
        getStylesheets().add(PopupButton.class.getResource("assets/css/PopupButton.css").toExternalForm());
        getStyleClass().add("popupButton");
    }
    
}
