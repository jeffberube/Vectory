/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vectory;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Tooltip;

/**
 *
 * @author Berube
 */
public class ViewOptions {
    
    private static final SimpleBooleanProperty showModKeysTooltip = new SimpleBooleanProperty(true);
    private static final SimpleBooleanProperty showRulers = new SimpleBooleanProperty(true);
    
    private static Tooltip modKeysTooltip = null;
    private static String modKeysTooltipString = "";
    
    public static void setShowModKeysTooltip(boolean value) {
        showModKeysTooltip.set(value);
    }
    
    public static SimpleBooleanProperty getShowModKeysTooltip() {
        return showModKeysTooltip;
    }
    
    public static boolean modKeysTooltipEnabled() {
        return showModKeysTooltip.get();
    }
    
    public static void setModKeysTooltipString(String value) {
        modKeysTooltipString = value;
    }
    
    public static void updateTooltipLocation(double x, double y) {
        if (!modKeysTooltipEnabled())
            return;
        
        if (modKeysTooltip == null) {
                modKeysTooltip = new Tooltip(modKeysTooltipString);
                modKeysTooltip.show(Context.getActiveDocumentPane(), x, y);
            }
        modKeysTooltip.setAnchorX(x);
        modKeysTooltip.setAnchorY(y);
    }
    
    public static void hideTooltip() {
        if (modKeysTooltip != null) {
            modKeysTooltip.hide();
            modKeysTooltip = null;
        }
    }
    
}
