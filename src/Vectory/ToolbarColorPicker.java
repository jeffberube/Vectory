/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vectory;

import Vectory.ColorDialog.ColorPickerDialog;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Window;

/**
 *
 * @author Berube
 */
public class ToolbarColorPicker extends Pane {
    private static ToolbarColorPicker instance = null;
    
    private final Image strokeFillSprites = new Image(getClass().getResourceAsStream("assets/images/strokefill.png"));
    private final ImageView strokeIcon = new ImageView(new WritableImage(strokeFillSprites.getPixelReader(), 0, 0, 16, 16));
    private final ImageView fillIcon = new ImageView(new WritableImage(strokeFillSprites.getPixelReader(), 0, 16, 16, 16));
    
    private final StackPane colorViewStack = new StackPane();
    
    private final Pane strokeColorButtonPane = new Pane();
    private final Pane fillColorButtonPane = new Pane();
    
    private final PopupButton strokeColorButton = new PopupButton(strokeColorButtonPane);
    private final PopupButton fillColorButton = new PopupButton(fillColorButtonPane);

    private final Rectangle strokeColorView = new Rectangle();
    private final Rectangle fillColorView = new Rectangle();
    
    
    private final SimpleObjectProperty<Color> fillColor = new SimpleObjectProperty(Color.WHITE);
    private final SimpleObjectProperty<Color> strokeColor = new SimpleObjectProperty(Color.BLACK);
    
    private boolean strokeColorViewFocused = false;
    private boolean fillColorViewFocused = false;
    
    private ColorPickerDialog colorPicker;
    
    private Color savedFillColor;
    private Color savedStrokeColor;
    
    private ToolbarColorPicker() {
        super();
        init();
    }
    
    private void init() {
        initStyle();
        initStrokeColorButton();
        initFillColorButton();
        
        fillColor.bindBidirectional(Context.fillColorProperty());
        strokeColor.bindBidirectional(Context.strokeColorProperty());
        
        getChildren().addAll(strokeColorButton, fillColorButton);
    }
    
    private void initStyle() {
        getStylesheets().add(ToolbarButton.class.getResource("assets/css/ToolbarColorPicker.css").toExternalForm());
        getStyleClass().add("toolbarColorPicker");
    }
    

    private void initStrokeColorButton() {
        initStrokeColorButtonPane();
        strokeColorButton.setOnAction(e->showCustomColorDialog(strokeColor));
        strokeColorButton.prefWidthProperty().bind(widthProperty().subtract(Constants.BORDER_WIDTH * 2));
    }
    
    private void initStrokeColorButtonPane() {
        initStrokeColorView();
        strokeColorButtonPane.getChildren().addAll(strokeIcon, strokeColorView);
    }
    
    private void initStrokeColorView() {
        strokeColorView.setWidth(28);
        strokeColorView.setHeight(16);
        strokeColorView.setTranslateX(24.5);
        strokeColorView.setTranslateY(0.5);
        strokeColorView.getStyleClass().add("strokeColorView");
        strokeColorView.fillProperty().bind(strokeColor);
        strokeColorView.setStroke(Color.BLACK);
        strokeColorView.setStrokeWidth(1);
    }
    
    private void initFillColorButton() {
        initFillColorButtonPane();
        fillColorButton.translateYProperty().bind(strokeColorButton.translateYProperty().add(strokeColorButton.heightProperty()).add(Constants.BORDER_WIDTH));
        fillColorButton.setOnAction(e->showCustomColorDialog(fillColor));
        fillColorButton.prefWidthProperty().bind(widthProperty().subtract(Constants.BORDER_WIDTH * 2));
    }
    
    private void initFillColorButtonPane() {
        initFillColorView();
        fillColorButtonPane.getChildren().addAll(fillIcon, fillColorView);
    }
    
    private void initFillColorView() {
        fillColorView.setWidth(28);
        fillColorView.setHeight(16);
        fillColorView.setTranslateX(24.5);
        fillColorView.setTranslateY(0.5);
        fillColorView.getStyleClass().add("fillColorView");
        fillColorView.fillProperty().bind(fillColor);
        fillColorView.setStroke(Color.BLACK);
        fillColorView.setStrokeWidth(1);
    }
    
    
    private void showCustomColorDialog(SimpleObjectProperty<Color> colorProperty) {
        colorPicker = new ColorPickerDialog(Context.getActiveDocumentPane().getScene().getWindow());
        colorPicker.setCurrentColor(colorProperty.get());
        colorPicker.setOnOK(applyCustomColor(colorProperty));
        colorPicker.setOnCancel(restoreCurrentColor(colorProperty));
        colorPicker.show();
    }
    
    private Runnable restoreCurrentColor(SimpleObjectProperty<Color> colorProperty) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                colorProperty.set(colorPicker.getCurrentColor());
            }
        };
        
        return runnable;
    }
    
    private Runnable applyCustomColor(SimpleObjectProperty<Color> colorProperty) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                colorProperty.set(colorPicker.getCustomColor());
            }
        };
        
        return runnable;
    }
    
    public static ToolbarColorPicker getInstance() {
        if (instance == null)
            instance = new ToolbarColorPicker();
        
        return instance;
    }
    
    public Color getFillColor() {
        return fillColor.get();
    }
    
    public void setFillColor(Color value) {
        fillColor.set(value);
    }
    
    public SimpleObjectProperty<Color> fillColorProperty() {
        return fillColor;
    }
    
    public Color getStrokeColor() {
        return strokeColor.get();
    }
    
    public void setStrokeColor(Color value) {
        strokeColor.set(value);
    }
    
    public SimpleObjectProperty<Color> strokeColorProperty() {
        return strokeColor;
    }
    
    
    
    

}
