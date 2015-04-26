/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vectory;

import java.util.Random;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 *
 * @author Berube
 */
public class Layer extends Pane implements Toggle {
    private static Color[] layerColors = new Color[] {Color.AQUA, Color.DEEPPINK, 
        Color.FUCHSIA, Color.LAWNGREEN, Color.RED, Color.MAGENTA,
        Color.MEDIUMBLUE, Color.ROYALBLUE, Color.SKYBLUE, Color.SILVER};

    private boolean locked = true;
    
    private final SimpleStringProperty name = new SimpleStringProperty();
    private final SimpleObjectProperty<Color> layerColor = new SimpleObjectProperty();
    private final SimpleBooleanProperty selected = new SimpleBooleanProperty();
    private final SimpleObjectProperty<Image> thumbnail = new SimpleObjectProperty();
    private final SimpleObjectProperty<ToggleGroup> toggleGroup = new SimpleObjectProperty();
    
    public Layer(String name, ToggleGroup toggleGroup) {
        super();
        this.name.set(name);
        this.toggleGroup.set(toggleGroup);
        init();
    }
    
    private void init() {
        layerColor.set(layerColors[new Random().nextInt(layerColors.length)]);
    }
    
    public Color getLayerColor() {
        return layerColor.getValue();
    }
    
    public void setLayerColor(Color color) {
        layerColor.set(color);
    }
    
    public SimpleObjectProperty<Color> layerColorProperty() {
        return layerColor;
    }
    
    public String getName() {
        return name.get();
    }
    
    public void setName(String value) {
        name.set(value);
    }
    
    public SimpleStringProperty nameProperty() {
        return name;
    }
    
    public boolean isLocked() {
        return locked;
    }
    
    public void setLocked(boolean value) {
        locked = value;
    }
    
    @Override
    public void setSelected(boolean value) {
        selected.set(value);
    }
    
    @Override
    public boolean isSelected() {
        return selected.get();
    }
    
    @Override
    public SimpleBooleanProperty selectedProperty() {
        return selected;
    }
    
    @Override
    public void setToggleGroup(ToggleGroup group) {
        toggleGroup.set(group);
    }
    
    @Override
    public ToggleGroup getToggleGroup() {
        return toggleGroup.get();
    }

    @Override
    public SimpleObjectProperty<ToggleGroup> toggleGroupProperty() {
        return toggleGroup;
    }
}
