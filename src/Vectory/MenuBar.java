/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vectory;

import javafx.beans.property.BooleanProperty;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;

/**
 *
 * @author Berube
 */
public class MenuBar extends HBox {
    private static MenuBar instance = null;
    
    private static final Image emptyIcon = new Image(MenuBar.class.getResourceAsStream("assets/images/empty.png"));

    private static final ToggleGroup menuGroup = new ToggleGroup();
    private static final MenuButton fileMenuButton = new MenuButton("File");
    private static final MenuButton editMenuButton = new MenuButton("Edit");
    private static final MenuButton viewMenuButton = new MenuButton("View");
    private static final MenuButton windowMenuButton = new MenuButton("Window");
    private static final MenuButton helpMenuButton = new MenuButton("Help");
    
    private static final MenuButton[] menuButtons = new MenuButton[]{fileMenuButton, 
        editMenuButton, viewMenuButton, windowMenuButton, helpMenuButton};

    private static final MenuItem newItem = new MenuItem("New...", new ImageView(emptyIcon));
    private static final MenuItem openItem = new MenuItem("Open...", new ImageView(emptyIcon));
    private static final Menu openRecentItem = new Menu("Open Recent File                ", new ImageView(emptyIcon));
    private static final MenuItem closeItem = new MenuItem("Close", new ImageView(emptyIcon));
    private static final MenuItem closeAllItem = new MenuItem("Close All", new ImageView(emptyIcon));
    private static final MenuItem saveItem = new MenuItem("Save", new ImageView(emptyIcon));
    private static final MenuItem saveAsItem = new MenuItem("Save As...", new ImageView(emptyIcon));
    private static final MenuItem printItem = new MenuItem("Print...", new ImageView(emptyIcon));
    private static final MenuItem exitItem = new MenuItem("Exit", new ImageView(emptyIcon));
    
    private static final MenuItem[] fileMenuItems = new MenuItem[]{newItem, openItem, 
        openRecentItem, closeItem, closeAllItem, saveItem, saveAsItem, 
        printItem, exitItem};
    
    private static final KeyCodeCombination[] fileMenuAccelerators = new KeyCodeCombination[] {
        new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN),                             // New
        new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN),                             // Open
        null,                                                                                       // Open Recent
        null,                                                                                       // Close
        null,                                                                                       // Close All
        new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN),                             // Save
        new KeyCodeCombination(KeyCode.S, KeyCombination.SHIFT_DOWN, KeyCombination.CONTROL_DOWN),  // Save As
        new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN),                             // Print
        new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN)                              // Exit
    };
    
    private static final MenuItem undoItem = new MenuItem("Undo", new ImageView(emptyIcon));
    private static final MenuItem redoItem = new MenuItem("Redo", new ImageView(emptyIcon));
    private static final MenuItem cutItem = new MenuItem("Cut", new ImageView(emptyIcon));
    private static final MenuItem copyItem = new MenuItem("Copy", new ImageView(emptyIcon));
    private static final MenuItem pasteItem = new MenuItem("Paste", new ImageView(emptyIcon));
    private static final MenuItem preferencesItem = new MenuItem("Preferences                ", new ImageView(emptyIcon));
    
    private static final MenuItem[] editMenuItems = new MenuItem[]{undoItem, redoItem,
        cutItem, copyItem, pasteItem, preferencesItem};
    
    private static final KeyCodeCombination[] editMenuAccelerators = new KeyCodeCombination[]{
        new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN),                             // Undo
        new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN),  // Redo
        new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN),                             // Cut
        new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN),                             // Copy
        new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN),                             // Paste
        null
    };
    
    private static final CheckMenuItem rulersItem = new CheckMenuItem("Show Rulers                ");
    
    private static final MenuItem[] viewMenuItems = new MenuItem[]{rulersItem};
    
    private static final KeyCodeCombination[] viewMenuAccelerators = new KeyCodeCombination[]{
        new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN)                              // Show Rulers                        
    };
    
    private static final CheckMenuItem colorsItem = new CheckMenuItem("Colors");
    private static final CheckMenuItem fontsItem = new CheckMenuItem("Fonts");
    private static final CheckMenuItem historyItem = new CheckMenuItem("History");
    private static final CheckMenuItem layersItem = new CheckMenuItem("Layers");
    private static final CheckMenuItem objectPropertiesItem = new CheckMenuItem("Object Properties                ");
    private static final CheckMenuItem toolbarItem = new CheckMenuItem("Toolbar");
    private static final CheckMenuItem toolSettingsItem = new CheckMenuItem("Tool Settings");
    private static final MenuItem mockDocItem = new MenuItem("Untitled.vec");
    
    private static final MenuItem[] windowMenuItems = new MenuItem[]{colorsItem,
         fontsItem, historyItem, layersItem, objectPropertiesItem, toolbarItem,
         toolSettingsItem, mockDocItem};
    
    private static final KeyCodeCombination[] windowMenuAccelerators = new KeyCodeCombination[]{
        new KeyCodeCombination(KeyCode.F2),                                                         // Colors
        new KeyCodeCombination(KeyCode.F3),                                                         // Fonts
        new KeyCodeCombination(KeyCode.F4),                                                         // History
        new KeyCodeCombination(KeyCode.F5),                                                         // Layers
        new KeyCodeCombination(KeyCode.F6),                                                         // Object Properties
        new KeyCodeCombination(KeyCode.F7),                                                         // Toolbar
        new KeyCodeCombination(KeyCode.F8),                                                         // Tool Settings
        null
    };
    
    private static final MenuItem helpItem = new MenuItem("Help...", new ImageView(emptyIcon));
    private static final MenuItem aboutItem = new MenuItem("About Vectory...                ", new ImageView(emptyIcon));
    
    private static final MenuItem[] helpMenuItems = new MenuItem[]{helpItem, aboutItem};
    
    private static final KeyCodeCombination[] helpMenuAccelerators = new KeyCodeCombination[] {
        new KeyCodeCombination(KeyCode.F1),                                                         // Help
        null                                                                                        // About
    };
    
    private static final MenuItem[][] menuItems = new MenuItem[][]{fileMenuItems,
        editMenuItems, viewMenuItems, windowMenuItems, helpMenuItems};
    
    private static final KeyCodeCombination[][] menuAccelerators = new KeyCodeCombination[][]{
        fileMenuAccelerators, editMenuAccelerators, viewMenuAccelerators,
        windowMenuAccelerators, helpMenuAccelerators
    };

    private static final ContextMenu fileMenu = new ContextMenu(newItem, 
        new SeparatorMenuItem(), openItem, openRecentItem, 
        new SeparatorMenuItem(), closeItem, closeAllItem, saveItem, saveAsItem, 
        new SeparatorMenuItem(), printItem, new SeparatorMenuItem(), exitItem);
    
    private static final ContextMenu editMenu = new ContextMenu(undoItem, redoItem, 
        new SeparatorMenuItem(), cutItem, copyItem, pasteItem,
        new SeparatorMenuItem(), preferencesItem);
    
    private static final ContextMenu viewMenu = new ContextMenu(rulersItem);
    
    private static final ContextMenu windowMenu = new ContextMenu(colorsItem,
        fontsItem, historyItem, layersItem, objectPropertiesItem, toolbarItem, 
        toolSettingsItem,  new SeparatorMenuItem(), mockDocItem);
    
    private static final ContextMenu helpMenu = new ContextMenu(helpItem, 
        new SeparatorMenuItem(), aboutItem);
    
    private static final ContextMenu[] contextMenus = new ContextMenu[]{fileMenu, 
        editMenu, viewMenu, windowMenu, helpMenu};

    private MenuBar() {
        super();
        init();
    }
    
    private void init() {
        initMenus();
        
        getStylesheets().add(MenuBar.class.getResource("assets/css/ContextMenu.css").toExternalForm());
        getChildren().addAll(menuButtons);
    }
    
    private void initMenus() {
        int i = 0;
        while (i < menuButtons.length) {
            
            setupMenuButtonHandlers(contextMenus[i], menuButtons[i]);
            setupMenuItemsAccelerators(menuItems[i], menuAccelerators[i]);
            contextMenus[i].setOnAction(e->menuGroup.selectToggle(null));
            contextMenus[i].setOnAutoHide(e->menuGroup.selectToggle(null));
            menuButtons[i++].setToggleGroup(menuGroup);
        }        
    }
    
    private void setupMenuItemsAccelerators(MenuItem[] menuItems, KeyCodeCombination[] accelerators) {
        int i = 0;
        while (i < menuItems.length) {
            menuItems[i].setAccelerator(accelerators[i]);
            i++;
        }    
    }
    
    private void setupMenuButtonHandlers(ContextMenu menu, MenuButton anchor) {
        anchor.setOnMouseReleased(e->menuButtonMouseReleasedHandler(menu, anchor));
        anchor.setOnMouseEntered(e->menuButtonMouseEnteredHandler(menu, anchor));
    }
    
    private void menuButtonMouseReleasedHandler(ContextMenu menu, MenuButton anchor) {
        if (menuGroup.getSelectedToggle() != anchor)
            menu.hide();
        else
            showMenu(menu, anchor);
    }
    
    private void menuButtonMouseEnteredHandler(ContextMenu menu, MenuButton anchor) {
        if (menuGroup.getSelectedToggle() != null && menuGroup.getSelectedToggle() != anchor) {
            menuGroup.selectToggle(anchor);
            showMenu(menu, anchor);
        }
    }
    
    private void showMenu(ContextMenu menu, Node anchor) {
        int i = 0;
        while (i < contextMenus.length)
            contextMenus[i++].hide();
                    
        menu.show(anchor, Side.BOTTOM, 0, 0);
    }
    
    public static MenuBar getInstance() {
        if (instance == null)
            instance = new MenuBar();
        
        return instance;
    }
    
    public static BooleanProperty colorsMenuItemCheckedProperty() {
        return colorsItem.selectedProperty();
    }
    
    public static BooleanProperty fontsMenuItemCheckedProperty() {
        return fontsItem.selectedProperty();
    }
    
    public static BooleanProperty historyMenuItemCheckedProperty() {
        return historyItem.selectedProperty();
    }
    
    public static BooleanProperty layersMenuItemCheckedProperty() {
        return layersItem.selectedProperty();
    }
    
    public static BooleanProperty objectPropertiesMenuItemCheckedProperty() {
        return objectPropertiesItem.selectedProperty();
    }
    
    public static BooleanProperty toolbarMenuItemCheckedProperty() {
        return toolbarItem.selectedProperty();
    }
    
    public static BooleanProperty toolSettingsMenuItemCheckedProperty() {
        return toolSettingsItem.selectedProperty();
    }
    
}
