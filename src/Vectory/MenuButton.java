/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vectory;

import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;

/**
 *
 * @author Berube
 */
public class MenuButton extends ToggleButton {
    
    public MenuButton(String value) {
        super();
        init();
        setText(value);
    }
    
    public MenuButton(ImageView image) {
        super();
        init();
        setGraphic(image);
    }
    
    private void init() {
        getStylesheets().add(PopupButton.class.getResource("assets/css/MenuButton.css").toExternalForm());
        getStyleClass().add("menuButton");
    }
    
}
