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
public class ToolbarButton extends ToggleButton {
    
    public ToolbarButton(ImageView image) {
        super();
        getStylesheets().add(ToolbarButton.class.getResource("assets/css/ToolbarButton.css").toExternalForm());
        getStyleClass().add("toolbarButton");
        setGraphic(image);
    }
    
}
