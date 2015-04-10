/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vectory;

import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author Berube
 */
public class Toolbar extends FloatingPanel {
    private static Toolbar instance = null;
    
    private final Image toolsSprites = new Image(getClass().getResourceAsStream("assets/images/tools.png"));
    private final ImageView selectIcon = new ImageView(new WritableImage(toolsSprites.getPixelReader(), 0, 0, 32, 32));
    private final ImageView typeIcon = new ImageView(new WritableImage(toolsSprites.getPixelReader(), 0, 32, 32, 32));
    private final ImageView lineIcon = new ImageView(new WritableImage(toolsSprites.getPixelReader(), 0, 64, 32, 32));
    private final ImageView squiggleIcon = new ImageView(new WritableImage(toolsSprites.getPixelReader(), 0, 96, 32, 32));
    private final ImageView rectangleIcon = new ImageView(new WritableImage(toolsSprites.getPixelReader(), 0, 128, 32, 32));
    private final ImageView ellipseIcon = new ImageView(new WritableImage(toolsSprites.getPixelReader(), 0, 160, 32, 32));
    private final ImageView ngonIcon = new ImageView(new WritableImage(toolsSprites.getPixelReader(), 0, 192, 32, 32));
    private final ImageView starIcon = new ImageView(new WritableImage(toolsSprites.getPixelReader(), 0, 224, 32, 32));
    private final ImageView paintIcon = new ImageView(new WritableImage(toolsSprites.getPixelReader(), 0, 256, 32, 32));
    private final ImageView imageIcon = new ImageView(new WritableImage(toolsSprites.getPixelReader(), 0, 288, 32, 32));
    
    private final ImageView[] icons = new ImageView[]{selectIcon, typeIcon,
                    lineIcon, squiggleIcon, rectangleIcon, ellipseIcon,
                    ngonIcon, starIcon, paintIcon, imageIcon};
    
    private final ToolbarButton selectTool = new ToolbarButton(selectIcon);
    private final ToolbarButton typeTool = new ToolbarButton(typeIcon);
    private final ToolbarButton lineTool = new ToolbarButton(lineIcon);
    private final ToolbarButton squiggleTool = new ToolbarButton(squiggleIcon);
    private final ToolbarButton rectangleTool = new ToolbarButton(rectangleIcon);
    private final ToolbarButton ellipseTool = new ToolbarButton(ellipseIcon);
    private final ToolbarButton ngonTool = new ToolbarButton(ngonIcon);
    private final ToolbarButton starTool = new ToolbarButton(starIcon);
    private final ToolbarButton paintTool = new ToolbarButton(paintIcon);
    private final ToolbarButton imageTool = new ToolbarButton(imageIcon);
    
    private final ToolbarButton[] tools = new ToolbarButton[]{selectTool, 
                typeTool, lineTool, squiggleTool, rectangleTool, ellipseTool, 
                ngonTool, starTool, paintTool, imageTool};

    private final ToggleGroup toolsGroup = new ToggleGroup();
    
    private final VBox toolsPane = new VBox();
    
    private final Pane buttonsPane = new Pane();
    private Separator separator;
    private final ToolbarColorPicker colorController = ToolbarColorPicker.getInstance();
    
    public static Toolbar getInstance() {
        if (instance == null)
            instance = new Toolbar();
        
        return instance;
    }
    
    private Toolbar() {
        super();
        init();
    }
    
    private void init() {
        getStylesheets().add(ToolbarButton.class.getResource("assets/css/Toolbar.css").toExternalForm());
        getStyleClass().add("toolbar");
        
        visibleProperty().bindBidirectional(MenuBar.toolbarMenuItemCheckedProperty());
        setVisible(true);
        
        initButtonsPane();
        initSeparator();
        initColorController();
        initToolsPane();
        
        setContent(toolsPane);
        toolsGroup.selectToggle(selectTool);
    }
    
    private void initButtonsPane() {
        createToolbarButtons();
        setupToolbarButtonsEventHandlers();
        buttonsPane.getChildren().addAll(tools);
    }
    
    private void initSeparator() {
        separator = new Separator(Constants.TOOLBAR_BUTTON_WIDTH * 2, 
                Separator.LineOrientation.HORIZONTAL, 
                Color.web("#000000"), Color.web("#444444"));
        
        separator.setTranslateX(Constants.BORDER_WIDTH * 2);
        separator.translateYProperty().bind(tools[tools.length - 1].translateYProperty().add(Constants.BORDER_WIDTH * 2));
    }
    
    private void initColorController() {
        colorController.setTranslateX(Constants.BORDER_WIDTH);
        colorController.translateYProperty().bind(separator.translateYProperty().add(8));
    }
    
    private void initToolsPane() {
        toolsPane.setPrefWidth(Constants.TOOLBAR_WIDTH);
        toolsPane.setPrefHeight(Constants.TOOLBAR_HEIGHT);
        toolsPane.getStyleClass().add("toolbar");
        
        toolsPane.getChildren().addAll(buttonsPane, separator, colorController);
    }
    
    private void createToolbarButtons() {
        for (int i = 0; i < tools.length; i++) {
            tools[i].translateXProperty().bind(tools[i].widthProperty().multiply(i % 2).add(Constants.BORDER_WIDTH));
            tools[i].translateYProperty().bind(tools[i].heightProperty().multiply(i / 2).add(Constants.BORDER_WIDTH));
            tools[i].setToggleGroup(toolsGroup);
        }
    }
    
    private void setupToolbarButtonsEventHandlers() {
        selectTool.setUserData(SelectTool.getInstance());
        typeTool.setUserData(TypeTool.getInstance());
        lineTool.setUserData(LineTool.getInstance());
        squiggleTool.setUserData(SquiggleTool.getInstance());
        rectangleTool.setUserData(RectangleTool.getInstance());
        ellipseTool.setUserData(EllipseTool.getInstance());
        ngonTool.setUserData(NgonTool.getInstance());
        starTool.setUserData(StarTool.getInstance());
        paintTool.setUserData(PaintTool.getInstance());
        imageTool.setUserData(ImageTool.getInstance());
        
        toolsGroup.selectedToggleProperty().addListener(e->Context.setTool((Tool)toolsGroup.getSelectedToggle().getUserData()));
    }
    
}
