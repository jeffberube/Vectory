/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vectory;

import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 *
 * @author Berube
 */
public class TransformationBox extends Pane {
    private final Rectangle box = new Rectangle();

    private final ResizeHandle topLeftHandle = new ResizeHandle(ResizeHandle.TOP_LEFT);
    private final ResizeHandle topHandle = new ResizeHandle(ResizeHandle.TOP);
    private final ResizeHandle topRightHandle = new ResizeHandle(ResizeHandle.TOP_RIGHT);
    private final ResizeHandle leftHandle = new ResizeHandle(ResizeHandle.RIGHT);
    private final ResizeHandle rightHandle = new ResizeHandle(ResizeHandle.BOTTOM_RIGHT);
    private final ResizeHandle bottomLeftHandle = new ResizeHandle(ResizeHandle.BOTTOM);
    private final ResizeHandle bottomHandle = new ResizeHandle(ResizeHandle.BOTTOM_LEFT);
    private final ResizeHandle bottomRightHandle = new ResizeHandle(ResizeHandle.LEFT);

    private final RotationHotspot topLeftHotspot = new RotationHotspot();
    private final RotationHotspot topHotspot = new RotationHotspot();
    private final RotationHotspot topRightHotspot = new RotationHotspot();
    private final RotationHotspot leftHotspot = new RotationHotspot();
    private final RotationHotspot rightHotspot = new RotationHotspot();
    private final RotationHotspot bottomLeftHotspot = new RotationHotspot();
    private final RotationHotspot bottomHotspot = new RotationHotspot();
    private final RotationHotspot bottomRightHotspot = new RotationHotspot();
    
    private final Image rotateCursorsSprites = new Image(getClass().getResourceAsStream("assets/images/rotate_cursors.png"));
    private final ImageCursor rotateTLCursor = new ImageCursor(new WritableImage(rotateCursorsSprites.getPixelReader(), 0, 0, 32, 32), 16, 16);
    private final ImageCursor rotateTCursor = new ImageCursor(new WritableImage(rotateCursorsSprites.getPixelReader(), 32, 0, 32, 32), 16, 16);
    private final ImageCursor rotateTRCursor = new ImageCursor(new WritableImage(rotateCursorsSprites.getPixelReader(), 64, 0, 32, 32), 16, 16);
    private final ImageCursor rotateLCursor = new ImageCursor(new WritableImage(rotateCursorsSprites.getPixelReader(), 0, 32, 32, 32), 16, 16);
    private final ImageCursor rotateRCursor = new ImageCursor(new WritableImage(rotateCursorsSprites.getPixelReader(), 64, 32, 32, 32), 16, 16);
    private final ImageCursor rotateBLCursor = new ImageCursor(new WritableImage(rotateCursorsSprites.getPixelReader(), 0, 64, 32, 32), 16, 16);
    private final ImageCursor rotateBCursor = new ImageCursor(new WritableImage(rotateCursorsSprites.getPixelReader(), 32, 64, 32, 32), 16, 16);
    private final ImageCursor rotateBRCursor = new ImageCursor(new WritableImage(rotateCursorsSprites.getPixelReader(), 64, 64, 32, 32), 16, 16);
    
    private final ResizeHandle[] handles = new ResizeHandle[] {topLeftHandle, 
                topHandle, topRightHandle, leftHandle, rightHandle,
                bottomLeftHandle, bottomHandle, bottomRightHandle};
    
    private final RotationHotspot[] hotspots = new RotationHotspot[] {topLeftHotspot,
                topHotspot, topRightHotspot, leftHotspot, rightHotspot, 
                bottomLeftHotspot, bottomHotspot, bottomRightHotspot};
    
    private final Cursor[] cursorsResize = new Cursor[] {Cursor.NW_RESIZE,
                Cursor.V_RESIZE, Cursor.NE_RESIZE, Cursor.H_RESIZE,
                Cursor.NW_RESIZE, Cursor.V_RESIZE, Cursor.NE_RESIZE,
                Cursor.H_RESIZE};
    
    private final ImageCursor[] cursorsRotate = new ImageCursor[] {rotateTLCursor, 
                rotateTCursor, rotateTRCursor, rotateRCursor, rotateBRCursor, 
                rotateBCursor, rotateBLCursor, rotateLCursor};
    
    EventHandler keyPressedFilter = new EventHandler<KeyEvent>() {
                            public void handle(KeyEvent e) {
                                keyPressedHandler(e);
                            }
                        };
    
    EventHandler keyReleasedFilter = new EventHandler<KeyEvent>() {
                            public void handle(KeyEvent e) {
                                keyReleasedHandler(e);
                            }
                        };
    
    private VecObject vecObject = null;
    private Shape shadowShape = null;
    
    private double originMouseX = 0;
    private double originMouseY = 0;
    private double originLayoutX = 0;
    private double originLayoutY = 0;
    
    private double mouseOriginTheta = 0;
    private double shapeOriginTheta = 0;
    private double deltaTheta = 0;
    private double deltaInDegrees = 0;
    
    private String rotationTooltipString = "SHIFT: 45\u00B0 steps";
    private String resizeTooltipString = "SHIFT: Preserve Aspect Ratio"
                                        + "ALT: Mirror Transform About Center";
    
    private boolean stepRotate = false;
    
    
    /*
        @param  object  Object this transformation box is bounded to
    */
    public TransformationBox(VecObject object) {
        super();
        
        vecObject = object;
        this.visibleProperty().bind(vecObject.selectedProperty());
        
        init();
    }
    
    private void init() {
        initBox();
        initHandles();
        initHotspots();
        
        getChildren().add(box);
        getChildren().addAll(handles);
        getChildren().addAll(hotspots);
    }
    
    private void initBox() {
        box.setTranslateX(-1);
        box.setTranslateY(-1);
        box.widthProperty().bind(vecObject.nodeWidthProperty().add(2));
        box.heightProperty().bind(vecObject.nodeHeightProperty().add(2));
        box.strokeProperty().bind(vecObject.getParentLayer().layerColorProperty());
        box.setFill(Color.TRANSPARENT);
        
        setupBoxMouseEvents();
    }
    
    private void setupBoxMouseEvents() {
        box.setOnMouseEntered(e->setCursor(Cursor.MOVE));
        box.setOnMouseExited(e->setCursor(Context.getTool().getCursor()));
    }
    
    private void initHandles() {
        for (int i = 0; i < handles.length; i++) {
            final int index = i;
            setupHandleEvents(handles[i], index);
        }
        
        initTopLeftHandle();
        initTopHandle();
        initTopRightHandle();
        initLeftHandle();
        initRightHandle();
        initBottomLeftHandle();
        initBottomHandle();
        initBottomRightHandle();
        
        setHandlesVisible(true);
    }
    
    private void setupHandleEvents(Rectangle handle, int index) {
        handle.setOnMouseEntered(e->setCursor(getResizeCursor(e)));
        //handle.setOnMousePressed(e->resizePressedHandler(e));
        //handle.setOnMouseDragged(e->resizeDraggedHandler(e));
        //handle.setOnMouseReleased(e->resizeReleasedHandler(e));
        handle.setOnMouseExited(e->setCursor(Context.getTool().getCursor()));
    }
    
    private void initTopLeftHandle() {
        topLeftHandle.setTranslateX(-Constants.TRANSFORMATION_BOX_HANDLE_WIDTH / 2 - 1);
        topLeftHandle.setTranslateY(-Constants.TRANSFORMATION_BOX_HANDLE_HEIGHT / 2 - 1);
    }
    
    private void initTopHandle() {
        topHandle.translateXProperty().bind(vecObject.nodeWidthProperty().divide(2).subtract(Constants.TRANSFORMATION_BOX_HANDLE_WIDTH / 2));
        topHandle.translateYProperty().bind(topLeftHandle.translateYProperty());
    }
    
    private void initTopRightHandle() {
        topRightHandle.translateXProperty().bind(vecObject.nodeWidthProperty().subtract(Constants.TRANSFORMATION_BOX_HANDLE_WIDTH / 2));
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
        bottomLeftHandle.translateYProperty().bind(vecObject.nodeHeightProperty().subtract(Constants.TRANSFORMATION_BOX_HANDLE_HEIGHT / 2));
    }
    
    private void initBottomHandle() {
        bottomHandle.translateXProperty().bind(topHandle.translateXProperty());
        bottomHandle.translateYProperty().bind(bottomLeftHandle.translateYProperty());
    }
    
    private void initBottomRightHandle() {
        bottomRightHandle.translateXProperty().bind(topRightHandle.translateXProperty());
        bottomRightHandle.translateYProperty().bind(bottomLeftHandle.translateYProperty());
    }

    private void initHotspots() {
        for (Rectangle hotspot: hotspots) {
            hotspot.setFill(Color.TRANSPARENT);
            hotspot.setStroke(Color.TRANSPARENT);
            hotspot.setOnMouseEntered(e->setCursor(getRotationCursor(e)));
            hotspot.setOnMousePressed(e->rotatePressedHandler(e));
            hotspot.setOnMouseDragged(e->rotateDraggedHandler(e));
            hotspot.setOnMouseReleased(e->rotateReleasedHandler(e));
            hotspot.setOnMouseExited(e->setCursor(Context.getTool().getCursor()));
        }
        
        initTopLeftHotspot();
        initTopHotspot();
        initTopRightHotspot();
        initLeftHotspot();
        initRightHotspot();
        initBottomLeftHotspot();
        initBottomHotspot();
        initBottomRightHotspot();
    }
    
    private void initTopLeftHotspot() {
        topLeftHotspot.translateXProperty().bind(topLeftHandle.translateXProperty().subtract(Constants.TRANSFORMATION_BOX_ROTATION_HOTSPOT_WIDTH));
        topLeftHotspot.translateYProperty().bind(topLeftHandle.translateYProperty().subtract(Constants.TRANSFORMATION_BOX_ROTATION_HOTSPOT_HEIGHT));
    }
    
    private void initTopHotspot() {
        topHotspot.translateXProperty().bind(vecObject.widthProperty().divide(2).subtract(Constants.TRANSFORMATION_BOX_ROTATION_HOTSPOT_WIDTH / 2));
        topHotspot.translateYProperty().bind(topLeftHotspot.translateYProperty());
    }
    
    private void initTopRightHotspot() {
        topRightHotspot.translateXProperty().bind(topRightHandle.translateXProperty().add(Constants.TRANSFORMATION_BOX_HANDLE_WIDTH));
        topRightHotspot.translateYProperty().bind(topLeftHotspot.translateYProperty());
    }
    
    private void initLeftHotspot() {
        leftHotspot.translateXProperty().bind(topLeftHotspot.translateXProperty());
        leftHotspot.translateYProperty().bind(vecObject.heightProperty().divide(2).subtract(Constants.TRANSFORMATION_BOX_ROTATION_HOTSPOT_HEIGHT / 2));
    }
    
    private void initRightHotspot() {
        rightHotspot.translateXProperty().bind(topRightHotspot.translateXProperty());
        rightHotspot.translateYProperty().bind(leftHotspot.translateYProperty());
    }
    
    private void initBottomLeftHotspot() {
        bottomLeftHotspot.translateXProperty().bind(topLeftHotspot.translateXProperty());
        bottomLeftHotspot.translateYProperty().bind(bottomLeftHandle.translateYProperty().add(Constants.TRANSFORMATION_BOX_HANDLE_HEIGHT));
    }
    
    private void initBottomHotspot() {
        bottomHotspot.translateXProperty().bind(topHotspot.translateXProperty());
        bottomHotspot.translateYProperty().bind(bottomLeftHotspot.translateYProperty());
    }
    
    private void initBottomRightHotspot() {
        bottomRightHotspot.translateXProperty().bind(topRightHotspot.translateXProperty());
        bottomRightHotspot.translateYProperty().bind(bottomLeftHotspot.translateYProperty());
    }
    
    private double computeTheta(MouseEvent e) {
        Bounds sceneBounds = vecObject.localToScene(vecObject.getBoundsInLocal());
        double deltaX = e.getSceneX() - (sceneBounds.getMinX() + (sceneBounds.getWidth() / 2));
        double deltaY = e.getSceneY() - (sceneBounds.getMinY() + (sceneBounds.getHeight() / 2));
        return Math.atan2(deltaY, deltaX);
    }
    
    private double getRotationDelta(MouseEvent e) {
        return computeTheta(e) - mouseOriginTheta;
    }
    
    private int getCursorIndex(MouseEvent e) {
        double theta = computeTheta(e) + Math.PI - (Math.PI / 8);

        if (theta < 0)
            theta = (Math.PI * 2) + theta;

        return (int)Math.floor((theta * 8) / (2 * Math.PI)) % 8;
    }
    
    private Cursor getResizeCursor(MouseEvent e) {
        return cursorsResize[getCursorIndex(e)];
    }
    
    private ImageCursor getRotationCursor(MouseEvent e) {
        return cursorsRotate[getCursorIndex(e)];
    }
    
    private void storeOriginMouseCoordinates(MouseEvent e) {
        originMouseX = e.getScreenX();
        originMouseY = e.getScreenY();
        originLayoutX = e.getX();
        originLayoutY = e.getY();
    }
    
    private void resizePressedHandler(MouseEvent e) {
        shapeOriginTheta = Math.toRadians(vecObject.getRotate());
        ViewOptions.setModKeysTooltipString(resizeTooltipString);
    }
    
    private void resizeDraggedHandler(MouseEvent e) {
        switch (((ResizeHandle)e.getTarget()).getHandlePosition()) {
            case ResizeHandle.TOP_LEFT:
            case ResizeHandle.TOP_RIGHT:
            case ResizeHandle.BOTTOM_LEFT:
            case ResizeHandle.BOTTOM_RIGHT:
                
                break;
                
            case ResizeHandle.LEFT:
            case ResizeHandle.RIGHT:
                
                break;
                
            case ResizeHandle.BOTTOM:
            case ResizeHandle.TOP:
                
                break;
        }
    }
    
    private void resizeReleasedHandler(MouseEvent e) {
        
    }
    
    private void rotatePressedHandler(MouseEvent e) {
        mouseOriginTheta = computeTheta(e);
        shapeOriginTheta = Math.toRadians(vecObject.getRotate());
        addKeyboardModifiers();
        ViewOptions.setModKeysTooltipString(rotationTooltipString);
    }
    
    private void rotateDraggedHandler(MouseEvent e) {
        double rotationDelta = getRotationDelta(e);

        setHandlesVisible(false);
        ViewOptions.updateTooltipLocation(e.getScreenX() + 16, e.getScreenY() + 16);
        
        if (shadowShape == null) 
            createShadowShape();
        
        if (getCursor() != getRotationCursor(e)) 
            setCursor(getRotationCursor(e));
        
        if (e.isShiftDown())    // Step rotation
            rotationDelta = Math.toDegrees(rotationDelta) % 45 > 22.5 ? 
                    Math.toRadians((((int)Math.toDegrees(rotationDelta) / 45) * 45) + 45)
                    : Math.toRadians(((int)Math.toDegrees(rotationDelta) / 45) * 45);
        
        deltaInDegrees = Math.toDegrees(rotationDelta);
        
        box.setRotate(deltaInDegrees);
        shadowShape.setRotate(deltaInDegrees);
    }
    
    private void rotateReleasedHandler(MouseEvent e) {
        ViewOptions.hideTooltip();
        destroyShadowShape();
        applyRotation(e);
        removeKeyboardModifiers();
        setHandlesVisible(true);
    }
    
    private void applyRotation(MouseEvent e) {
        box.setRotate(0);
        vecObject.setRotate(Math.toDegrees(shapeOriginTheta) + deltaInDegrees);
    }
    
    private void setHandlesVisible(boolean value) {
        for (Rectangle handle: handles) {
            handle.setStroke(value ? Context.getSelectedLayer().getLayerColor() : Color.TRANSPARENT);
            handle.setFill(value ? Color.WHITE : Color.TRANSPARENT);
        }
    }
    
    private void addKeyboardModifiers() {
        Context.getActiveDocumentPane().getScene().addEventFilter(KeyEvent.KEY_PRESSED, keyPressedFilter); 
        Context.getActiveDocumentPane().getScene().addEventFilter(KeyEvent.KEY_RELEASED, keyReleasedFilter);
    }
    
    private void removeKeyboardModifiers() {
        Context.getActiveDocumentPane().getScene().removeEventFilter(KeyEvent.KEY_PRESSED, keyPressedFilter);
        Context.getActiveDocumentPane().getScene().removeEventFilter(KeyEvent.KEY_RELEASED, keyReleasedFilter);
    }

    
    public void keyPressedHandler(KeyEvent e) {
        switch(e.getCode()) {
            case SHIFT:
                stepRotate = true;
                break;
        }
    }
    
    public void keyReleasedHandler(KeyEvent e) {
        switch(e.getCode()) {
            case SHIFT:
                stepRotate = false;
                break;
        }
    }
    
    private void createShadowShape() {
        shadowShape = vecObject.getShapeDuplicate();
        shadowShape.setFill(Color.TRANSPARENT);
        shadowShape.setStroke(Context.getSelectedLayer().getLayerColor());
        
        if (shadowShape instanceof Rectangle) {
            Rectangle rect = (Rectangle)shadowShape;
            rect.widthProperty().bind(box.widthProperty());
            rect.heightProperty().bind(box.heightProperty());
            rect.translateXProperty().bind(box.translateXProperty());
            rect.translateYProperty().bind(box.translateYProperty());
        } else if (shadowShape instanceof Ellipse) {
            ((Ellipse)shadowShape).centerXProperty().bind(box.widthProperty().divide(2));
            ((Ellipse)shadowShape).centerYProperty().bind(box.heightProperty().divide(2));
            ((Ellipse)shadowShape).radiusXProperty().bind(box.widthProperty().divide(2));
            ((Ellipse)shadowShape).radiusYProperty().bind(box.heightProperty().divide(2));
        }
        
        getChildren().add(shadowShape);
    }
    
    private void destroyShadowShape() {
        getChildren().remove(shadowShape);
        shadowShape = null;
    }
}
