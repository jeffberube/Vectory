/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vectory;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Berube
 */
public class TransformationBox extends Pane {
    private final Rectangle box = new Rectangle();
    
    private final Rectangle topLeftHandle = new Rectangle(0, 0, Constants.TRANSFORMATION_BOX_HANDLE_WIDTH, Constants.TRANSFORMATION_BOX_HANDLE_HEIGHT);
    private final Rectangle topHandle = new Rectangle(0, 0, Constants.TRANSFORMATION_BOX_HANDLE_WIDTH, Constants.TRANSFORMATION_BOX_HANDLE_HEIGHT);
    private final Rectangle topRightHandle = new Rectangle(0, 0, Constants.TRANSFORMATION_BOX_HANDLE_WIDTH, Constants.TRANSFORMATION_BOX_HANDLE_HEIGHT);
    private final Rectangle leftHandle = new Rectangle(0, 0, Constants.TRANSFORMATION_BOX_HANDLE_WIDTH, Constants.TRANSFORMATION_BOX_HANDLE_HEIGHT);
    private final Rectangle rightHandle = new Rectangle(0, 0, Constants.TRANSFORMATION_BOX_HANDLE_WIDTH, Constants.TRANSFORMATION_BOX_HANDLE_HEIGHT);
    private final Rectangle bottomLeftHandle = new Rectangle(0, 0, Constants.TRANSFORMATION_BOX_HANDLE_WIDTH, Constants.TRANSFORMATION_BOX_HANDLE_HEIGHT);
    private final Rectangle bottomHandle = new Rectangle(0, 0, Constants.TRANSFORMATION_BOX_HANDLE_WIDTH, Constants.TRANSFORMATION_BOX_HANDLE_HEIGHT);
    private final Rectangle bottomRightHandle = new Rectangle(0, 0, Constants.TRANSFORMATION_BOX_HANDLE_WIDTH, Constants.TRANSFORMATION_BOX_HANDLE_HEIGHT);
    
    private final Rectangle[] handles = new Rectangle[] {topLeftHandle, 
                topHandle, topRightHandle, leftHandle, rightHandle,
                bottomLeftHandle, bottomHandle, bottomRightHandle};
    
    private final SimpleObjectProperty<Color> color = new SimpleObjectProperty();
    
    private VecObject object;
    
    /*
        @param  object  Object this transformation box is bounded to
    */
    public TransformationBox(VecObject object) {
        super();
        
        this.object = object;
        
        init();
    }
    
    private void init() {
        initBox();
        initHandles();
    }
    
    private void initBox() {
        box.widthProperty().bind(object.widthProperty());
        box.heightProperty().bind(object.heightProperty());
        box.strokeProperty().bind(color);
        box.setFill(Color.TRANSPARENT);
    }
    
    private void initHandles() {
        for(Rectangle handle: handles) {
            handle.setFill(Color.WHITE);
            handle.strokeProperty().bind(color);
        }
        
        initTopLeftHandle();
        initTopHandle();
        initTopRightHandle();
        initLeftHandle();
        initRightHandle();
        initBottomLeftHandle();
        initBottomHandle();
        initBottomRightHandle();
    }
    
    private void initTopLeftHandle() {
        topLeftHandle.setTranslateX(-Constants.TRANSFORMATION_BOX_HANDLE_WIDTH / 2);
        topLeftHandle.setTranslateY(-Constants.TRANSFORMATION_BOX_HANDLE_HEIGHT / 2);
    }
    
    private void initTopHandle() {
        topHandle.translateXProperty().bind(widthProperty().divide(2).subtract(Constants.TRANSFORMATION_BOX_HANDLE_WIDTH / 2));
        topHandle.translateYProperty().bind(topLeftHandle.translateYProperty());
    }
    
    private void initTopRightHandle() {
        topRightHandle.translateXProperty().bind(widthProperty().subtract(Constants.TRANSFORMATION_BOX_HANDLE_WIDTH / 2));
        topRightHandle.translateYProperty().bind(topLeftHandle.translateYProperty());
    }
    
    private void initLeftHandle() {
        leftHandle.translateXProperty().bind(topLeftHandle.translateXProperty());
        leftHandle.translateYProperty().bind(heightProperty().divide(2).subtract(Constants.TRANSFORMATION_BOX_HANDLE_HEIGHT / 2));
    }
    
    private void initRightHandle() {
        rightHandle.translateXProperty().bind(topRightHandle.translateXProperty());
        rightHandle.translateYProperty().bind(heightProperty().divide(2).subtract(Constants.TRANSFORMATION_BOX_HANDLE_HEIGHT / 2));
    }
    
    private void initBottomLeftHandle() {
        bottomLeftHandle.translateXProperty().bind(topLeftHandle.translateXProperty());
        bottomLeftHandle.translateYProperty().bind(heightProperty().subtract(Constants.TRANSFORMATION_BOX_HANDLE_HEIGHT / 2));
    }
    
    private void initBottomHandle() {
        bottomHandle.translateXProperty().bind(topHandle.translateXProperty());
        bottomHandle.translateYProperty().bind(bottomLeftHandle.translateYProperty());
    }
    
    private void initBottomRightHandle() {
        bottomRightHandle.translateXProperty().bind(topRightHandle.translateXProperty());
        bottomRightHandle.translateYProperty().bind(bottomLeftHandle.translateYProperty());
    }
}
