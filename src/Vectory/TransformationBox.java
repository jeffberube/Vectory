/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vectory;

import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

/**
 *
 * @author Berube
 */
public class TransformationBox extends Pane {
    private static TransformationBox instance = null;
    
    private final Rectangle box = new Rectangle();

    private final ResizeHandle topLeftHandle = new ResizeHandle(ResizeHandle.TOP_LEFT);
    private final ResizeHandle topHandle = new ResizeHandle(ResizeHandle.TOP);
    private final ResizeHandle topRightHandle = new ResizeHandle(ResizeHandle.TOP_RIGHT);
    private final ResizeHandle rightHandle = new ResizeHandle(ResizeHandle.RIGHT);
    private final ResizeHandle bottomRightHandle = new ResizeHandle(ResizeHandle.BOTTOM_RIGHT);
    private final ResizeHandle bottomHandle = new ResizeHandle(ResizeHandle.BOTTOM);
    private final ResizeHandle bottomLeftHandle = new ResizeHandle(ResizeHandle.BOTTOM_LEFT);
    private final ResizeHandle leftHandle = new ResizeHandle(ResizeHandle.LEFT);

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
    private UniversalShape shadowShape = null;
    
    private double originMouseTheta = 0;
    private double originMouseX = 0;
    private double originMouseY = 0;

    private double originShapeX = 0;
    private double originShapeY = 0;
    private double originShapeWidth = 0;
    private double originShapeHeight = 0;
    private double originShapeRatio = 0;        // Width / Height
    private double originShapeTheta = 0;
    
    private Rotate originRotate = null;
    private Rotate shadowRotate = null;
    
    private double deltaTheta = 0;
    private double deltaInDegrees = 0;
    
    private Bounds originSceneBounds = null;
    private Bounds sceneBounds = null;
    
    private final String moveTooltipString = "SHIFT: Lock Direction";
    private final String rotationTooltipString = "SHIFT: 45\u00B0 steps";
    private final String resizeTooltipString = "SHIFT: Preserve Aspect Ratio\n"
                                        + "ALT: Mirror Transform About Center";
    
    private boolean stepRotate = false;
    
    
    /*
        @param  object  Object this transformation box is bounded to
    */
    private TransformationBox(VecObject object) {
        super();
        
        vecObject = object;
        this.visibleProperty().bind(vecObject.selectedProperty());
        
        init();
    }
    
    private void init() {
        initOriginRotate();
        initShadowRotate();
        
        initBox();
        initHandles();
        initHotspots();
        
        setTranslateX(vecObject.getTranslateX());
        setTranslateY(vecObject.getTranslateY());
        
        getTransforms().add(shadowRotate);

        getChildren().add(box);
        getChildren().addAll(handles);
        getChildren().addAll(hotspots);
        
        updateSceneBounds();
    }
    
    private void initOriginRotate() {
        originRotate = getVecObjectRotate();
        if (originRotate == null) {
            originRotate = new Rotate(0, vecObject.getShapeWidth() / 2, vecObject.getShapeHeight() / 2);
            vecObject.getTransforms().add(originRotate);
        }
    }
    
    private void initShadowRotate() {
        if (shadowRotate == null)
            shadowRotate = new Rotate(originRotate.getAngle(), originRotate.getPivotX(), originRotate.getPivotY());
        else 
            updateShadowRotate();
    }
    
    private void updateShadowRotate() {
        shadowRotate.setAngle(originRotate.getAngle());
        shadowRotate.setPivotX(originRotate.getPivotX());
        shadowRotate.setPivotY(originRotate.getPivotY());
    }
    
    private void initBox() {
        box.setTranslateX(-1);
        box.setTranslateY(-1);
        box.setWidth(vecObject.nodeWidthProperty().get() + 2);
        box.setHeight(vecObject.nodeHeightProperty().get() + 2);
        box.strokeProperty().bind(vecObject.getParentLayer().layerColorProperty());
        box.setFill(Color.TRANSPARENT);
        
        setupBoxMouseEvents();
    }
    
    private void setupBoxMouseEvents() {
        box.setOnMouseEntered(e->setCursor(Cursor.MOVE));
        box.setOnMousePressed(e->movePressedHandler(e));
        box.setOnMouseDragged(e->moveDraggedHandler(e));
        box.setOnMouseReleased(e->moveReleasedHandler(e));
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
    
    private void setupHandleEvents(ResizeHandle handle, int index) {
        handle.setOnMouseEntered(e->setCursor(getResizeCursor(e)));
        handle.setOnMousePressed(e->resizePressedHandler(e));
        handle.setOnMouseDragged(e->resizeDraggedHandler(e));
        handle.setOnMouseReleased(e->resizeReleasedHandler(e));
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
    
    private void updateSceneBounds() {
        sceneBounds = vecObject.localToScene(vecObject.getBoundsInLocal());
    }
    
    private double computeTopLeftToMouse(MouseEvent e) {
        Point2D topLeft = vecObject.localToScene(0, 0);
        double x1x2 = Math.pow(e.getSceneX() - topLeft.getX(), 2);
        double y1y2 = Math.pow(e.getSceneY() - topLeft.getY(), 2);
        return Math.sqrt(x1x2 + y1y2);
    }
    
    private double computeCenterToMouse(MouseEvent e) {
        //updateSceneBounds();
        Point2D center = vecObject.localToScene(originShapeWidth / 2, originShapeHeight / 2);
        double x1x2 = Math.pow(e.getSceneX() - (sceneBounds.getMinX() + (sceneBounds.getWidth() / 2)), 2);
        double y1y2 = Math.pow(e.getSceneY() - (sceneBounds.getMinY() + (sceneBounds.getHeight() / 2)), 2);
        return Math.sqrt(x1x2 + y1y2);
    }
    
    private double computeMouseTheta(MouseEvent e) {
        Point2D center = vecObject.localToScene(originRotate.getPivotX(), originRotate.getPivotY());
        double deltaX = e.getSceneX() - center.getX();
        double deltaY = e.getSceneY() - center.getY();
        return Math.atan2(deltaY, deltaX);
    }
    
    private Rotate getVecObjectRotate() {
        List<Transform> transforms = vecObject.getTransforms();
        
        for (Transform t: transforms)
            if (t instanceof Rotate)
                return (Rotate)t;
        
        return null;
    }
    
    private double getRotationDelta(MouseEvent e) {
        return computeMouseTheta(e) - originMouseTheta;
    }
    
    private int getCursorIndex(MouseEvent e) {
        updateSceneBounds();
        double theta = computeMouseTheta(e) + Math.PI - (Math.PI / 8);

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
    
    private void movePressedHandler(MouseEvent e) {
        recordInitialState(e);
        ViewOptions.setModKeysTooltipString(moveTooltipString);
    }
    
    private void moveDraggedHandler(MouseEvent e) {
        if (shadowShape == null)
            createShadowShape();
        
        setHandlesVisible(false);
        
        setTranslateX(originShapeX + (e.getScreenX() - originMouseX));
        setTranslateY(originShapeY + (e.getScreenY() - originMouseY));
    }
    
    private void moveReleasedHandler(MouseEvent e) {
        ViewOptions.hideTooltip();
        applyMove(e);
        destroyShadowShape();
        setHandlesVisible(true);
    }
    
    private void applyMove(MouseEvent e) {
        vecObject.setTranslateX(getTranslateX());
        vecObject.setTranslateY(getTranslateY());
    }

    private void resizePressedHandler(MouseEvent e) {
        recordInitialState(e);
        ViewOptions.setModKeysTooltipString(resizeTooltipString);
    }
    
    private void recordInitialState(MouseEvent e) {
        storeOriginMouseState(e);
        storeOriginShapeMetrics(e);
    }
    
    private void storeOriginMouseState(MouseEvent e) {
        originMouseX = e.getScreenX();
        originMouseY = e.getScreenY();
        originMouseTheta = computeMouseTheta(e);
    }
    
    private void storeOriginShapeMetrics(MouseEvent e) {
        originShapeX = vecObject.getTranslateX();
        originShapeY = vecObject.getTranslateY();
        originShapeWidth = vecObject.getShapeWidth();
        originShapeHeight = vecObject.getShapeHeight();
        originShapeRatio = originShapeWidth / originShapeHeight;
        originShapeTheta = Math.toRadians(originRotate.getAngle());
        
        updateSceneBounds();
    }
    
    private void resizeDraggedHandler(MouseEvent e) {
        boolean altDown = e.isAltDown();
        boolean shiftDown = e.isShiftDown();
        
        double newX = 0, newY = 0, newWidth = 0, newHeight = 0, newPivotX = 0, newPivotY = 0, diffWidth = 0, diffHeight = 0;
        
        double topLeftToMouse = computeTopLeftToMouse(e);
        double centerToMouse = computeCenterToMouse(e);
        double verticalDistance = Math.sin(computeMouseTheta(e) - Math.toRadians(originRotate.getAngle())) * centerToMouse;
        double horizontalDistance = Math.cos(computeMouseTheta(e) - Math.toRadians(originRotate.getAngle())) * centerToMouse;
        double tempAspectRatio = ((originShapeWidth / 2) + horizontalDistance) / ((originShapeHeight / 2) + verticalDistance);
        
        double deltaX = horizontalDistance - (originShapeWidth / 2);
        double deltaY = verticalDistance - (originShapeHeight / 2);
        double width = originShapeWidth + deltaX;
        double height = originShapeHeight + deltaY;
        double altWidth = horizontalDistance * 2;
        double altHeight = verticalDistance * 2;
        
        double distanceRatio = horizontalDistance / verticalDistance;
        
        if (shadowShape == null) 
            createShadowShape();
        
        setHandlesVisible(false);
        ViewOptions.updateTooltipLocation(e.getScreenX() + 16, e.getScreenY() + 16);
        
        switch (((ResizeHandle)e.getTarget()).getHandlePosition()) {  
            case ResizeHandle.LEFT:
                newWidth = (int)(altDown ? Math.abs(altWidth) : (deltaX > 0 ? deltaX : originShapeWidth - width));
                newHeight = (int)(shiftDown? newWidth / originShapeRatio : originShapeHeight);
                newX = altDown ? (deltaX < -originShapeWidth / 2 ? width : -deltaX) : (deltaX > 0 ? originShapeWidth : width);
                newY = shiftDown? (originShapeHeight - newHeight) / 2 : 0;
                diffWidth = newWidth - originShapeWidth;
                shadowRotate.setPivotX(altDown ? newWidth / 2 : (deltaX > 0 ? -originRotate.getPivotX() : originRotate.getPivotX() + diffWidth));
                shadowRotate.setPivotY(shiftDown ? newHeight / 2 : originRotate.getPivotY());
                break;
                
            case ResizeHandle.RIGHT:
                newWidth = (int)Math.abs(altDown ? altWidth : width);
                newHeight = (int)(shiftDown? newWidth / originShapeRatio : originShapeHeight);
                newX = altDown ? (deltaX < -originShapeWidth / 2 ? width : -deltaX) : (deltaX < -originShapeWidth ? -newWidth : 0);
                newY = shiftDown? (originShapeHeight - newHeight) / 2 : 0;
                shadowRotate.setPivotX(altDown ? newWidth / 2 : (deltaX < -originShapeWidth ? originRotate.getPivotX() + newWidth : originRotate.getPivotX()));
                shadowRotate.setPivotY(shiftDown ? newHeight / 2 : originRotate.getPivotY());
                break;
                
            case ResizeHandle.TOP:
                newHeight = (int)(altDown ? Math.abs(altHeight) : (deltaY > 0 ? deltaY : originShapeHeight - height));
                newWidth = (int)(shiftDown? newHeight * originShapeRatio : originShapeWidth);
                newY = altDown ? (deltaY < -originShapeHeight / 2 ? height : -deltaY) : (deltaY > 0 ? originShapeHeight : height);
                newX = shiftDown? (originShapeWidth - newWidth) / 2 : 0;
                diffHeight = newHeight - originShapeHeight;
                shadowRotate.setPivotY(altDown ? newHeight / 2 : (deltaY > 0 ? -originRotate.getPivotY() : originRotate.getPivotY() + diffHeight));
                shadowRotate.setPivotX(shiftDown ? newWidth / 2 : originRotate.getPivotX());
                break;
                
            case ResizeHandle.BOTTOM:
                newHeight = (int)Math.abs(altDown ? altHeight : height);
                newWidth = (int)(shiftDown? newHeight * originShapeRatio : originShapeWidth);
                newY = altDown ? (deltaY < -originShapeHeight / 2 ? height : -deltaY) : (deltaY < -originShapeHeight ? -newHeight : 0);
                newX = shiftDown? (originShapeWidth - newWidth) / 2 : 0;
                shadowRotate.setPivotY(altDown ? newHeight / 2 : (deltaY < -originShapeHeight ? originRotate.getPivotY() + newHeight : originRotate.getPivotY()));
                shadowRotate.setPivotX(shiftDown ? newWidth / 2 : originRotate.getPivotX());
                break;
                
                
                
            case ResizeHandle.BOTTOM_LEFT:
                if (shiftDown) {
                    if (Math.abs(tempAspectRatio) > originShapeRatio) {
                        newHeight = (int)(altDown ? Math.abs(altHeight) : (deltaY < -originShapeHeight ? deltaY : originShapeHeight - height));
                        newWidth = (int)(newHeight * originShapeRatio);
                        System.out.println("> dX: " + deltaX + "\tdY: " + deltaY + "\ttAS: " + tempAspectRatio);
                        newX = altDown ? (originShapeWidth - newWidth) / 2 : (deltaX > 0 ? originShapeWidth : originShapeWidth - newWidth);
                        newY = altDown ? (originShapeHeight - newHeight) / 2 : (newX > originShapeWidth ? -newHeight : 0);
                    } else {
                        newWidth = (int)(altDown ? Math.abs(altWidth) : (deltaX > 0 ? deltaX : originShapeWidth - width));
                        newHeight = (int)(newWidth / originShapeRatio);
                        System.out.println("< dX: " + deltaX + "\tdY: " + deltaY + "\ttAS: " + tempAspectRatio);
                        newY = altDown ? (originShapeHeight - newHeight) / 2 : (deltaY < -originShapeHeight ? -newHeight : 0);
                        newX = altDown ? (originShapeWidth - newWidth) / 2 : (newY < 0 ? -newWidth : originShapeWidth - newWidth);
                    }
                    shadowRotate.setPivotY(altDown ? newHeight / 2 : (deltaY < -originShapeHeight ? originRotate.getPivotY() + newHeight : originRotate.getPivotY()));
                    shadowRotate.setPivotX(altDown ? newWidth / 2 : (deltaX < -originShapeWidth ? originRotate.getPivotX() + newWidth : originRotate.getPivotX()));
                } else {
                    newWidth = (int)(altDown ? Math.abs(altWidth) : (deltaX > 0 ? deltaX : originShapeWidth - width));
                    newHeight = (int)Math.abs(altDown ? altHeight : height);
                    newX = altDown ? (originShapeWidth - newWidth) / 2 : (deltaX > 0 ? originShapeWidth : width);
                    newY = altDown ? (originShapeHeight - newHeight) / 2 : (deltaY < -originShapeHeight ? -newHeight : 0);
                    shadowRotate.setPivotX(altDown ? newWidth / 2 : (deltaX > 0 ? -originRotate.getPivotX() : originRotate.getPivotX() + diffWidth));
                    shadowRotate.setPivotY(altDown ? newHeight / 2 : (deltaY < -originShapeHeight ? originRotate.getPivotY() + newHeight : originRotate.getPivotY()));
                }
                break;
            
                
                
                
                
                
                
            case ResizeHandle.BOTTOM_RIGHT:
                if (shiftDown) {
                    if (Math.abs(tempAspectRatio) > originShapeRatio) {
                        newHeight = (int)Math.abs(altDown ? altHeight : height);
                        newWidth = (int)(newHeight * originShapeRatio);
                        newX = altDown? (originShapeWidth - newWidth) / 2 : (deltaX < -originShapeWidth ? -newWidth : 0);
                        newY = altDown ? (originShapeHeight - newHeight) / 2 : (newX < 0 ? -newHeight : 0);
                    } else {
                        newWidth = (int)Math.abs(altDown ? altWidth : width);
                        newHeight = (int)(newWidth / originShapeRatio);
                        newY = altDown ? (originShapeHeight - newHeight) / 2 : (deltaY < -originShapeHeight ? -newHeight : 0);
                        newX = altDown ? (originShapeWidth - newWidth) / 2 : (newY < 0 ? -newWidth : 0);
                    }
                    shadowRotate.setPivotY(altDown ? newHeight / 2 : (deltaY < -originShapeHeight ? originRotate.getPivotY() + newHeight : originRotate.getPivotY()));
                    shadowRotate.setPivotX(altDown ? newWidth / 2 : (deltaX < -originShapeWidth ? originRotate.getPivotX() + newWidth : originRotate.getPivotX()));
                } else {
                    newWidth = (int)Math.abs(altDown ? altWidth : width);
                    newHeight = (int)Math.abs(altDown ? altHeight : height);
                    newX = altDown ? (originShapeWidth - newWidth) / 2 : (deltaX < -originShapeWidth ? -newWidth : 0);
                    newY = altDown ? (originShapeHeight - newHeight) / 2 : (deltaY < -originShapeHeight ? -newHeight : 0);
                    shadowRotate.setPivotX(altDown ? newWidth / 2 : (deltaX < -originShapeWidth ? originRotate.getPivotX() + newWidth : originRotate.getPivotX()));
                    shadowRotate.setPivotY(altDown ? newHeight / 2 : (deltaY < -originShapeHeight ? originRotate.getPivotY() + newHeight : originRotate.getPivotY()));
                }
                break;
                
                
                
                
                
                
                
                
            
                
            case ResizeHandle.TOP_LEFT:
            case ResizeHandle.TOP_RIGHT:
            
            
                break;
        }

        box.setWidth(newWidth + 2);
        box.setHeight(newHeight + 2);
        
        setTranslateX(originShapeX + newX);
        setTranslateY(originShapeY + newY);
    }
    
    private void resizeReleasedHandler(MouseEvent e) {
        ViewOptions.hideTooltip();
        applyResize(e);
        destroyShadowShape();
        setHandlesVisible(true);
    }
    
    private void applyResize(MouseEvent e) {
        Bounds newBounds = localToParent(box.getBoundsInParent());
        double newCenterX = ((newBounds.getMaxX() - newBounds.getMinX()) / 2) + newBounds.getMinX();
        double newCenterY = ((newBounds.getMaxY() - newBounds.getMinY()) / 2) + newBounds.getMinY();
        double newWidth = shadowShape.getWidth() - 2;
        double newHeight = shadowShape.getHeight() - 2;
        
        originRotate.setPivotX(newWidth / 2);
        originRotate.setPivotY(newHeight / 2);
        vecObject.setShapeWidth(newWidth);
        vecObject.setShapeHeight(newHeight);
        vecObject.setTranslateX(newCenterX - (newWidth / 2));
        vecObject.setTranslateY(newCenterY - (newHeight / 2));
    }
    
    private void rotatePressedHandler(MouseEvent e) {
        originMouseTheta = computeMouseTheta(e);
        originShapeTheta = Math.toRadians(originRotate.getAngle());

        setTranslateX(vecObject.getTranslateX());
        setTranslateY(vecObject.getTranslateY());
        
        updateShadowRotate();

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
        
        shadowRotate.setAngle(deltaInDegrees + Math.toDegrees(originShapeTheta));
    }
    
    private void rotateReleasedHandler(MouseEvent e) {
        ViewOptions.hideTooltip();
        destroyShadowShape();
        applyRotation(e);
        setHandlesVisible(true);
    }
    
    private void applyRotation(MouseEvent e) {
        originRotate.setAngle(shadowRotate.getAngle());
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
        shadowShape = new UniversalShape(vecObject.getShapeDuplicate());
        shadowShape.getShapeNode().setFill(Color.TRANSPARENT);
        shadowShape.getShapeNode().setStroke(Context.getSelectedLayer().getLayerColor());
        shadowShape.prefWidthProperty().bind(box.widthProperty());
        shadowShape.prefHeightProperty().bind(box.heightProperty());
        shadowShape.translateXProperty().bind(box.translateXProperty());
        shadowShape.translateYProperty().bind(box.translateYProperty());
        
        getChildren().add(shadowShape);
    }
    
    private void destroyShadowShape() {
        getChildren().remove(shadowShape);
        shadowShape = null;
    }
    
    public static TransformationBox getInstance(VecObject object) {
        if (instance == null || instance.vecObject != object)
            instance = new TransformationBox(object);
            
        return instance;
    }
    
    private void dumpValues() {
        System.out.println("vecobj \t\tX: " + vecObject.getTranslateX() + "\t\tY: " + vecObject.getTranslateY() + "\t\tpX: " + originRotate.getPivotX() + "\t\tpY: " + originRotate.getPivotY() + "\t\tW: " + vecObject.getWidth() + "\t\tH:" + vecObject.getHeight());
        System.out.println("this \t\tX: " + getTranslateX() + "\t\tY: " + getTranslateY() + "\t\tpX: " + shadowRotate.getPivotX() + "\t\tpY: " + shadowRotate.getPivotY() + "\t\tW: " + getWidth() + "\t\tH:" + getHeight());
    }
}
