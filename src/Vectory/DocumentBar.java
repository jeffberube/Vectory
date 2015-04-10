/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vectory;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author Berube
 */
public class DocumentBar extends HBox {
    private ToggleGroup tabGroup = new ToggleGroup();
    private ArrayList<VecDocument> docs = new ArrayList();
    private ArrayList<DocumentTab> tabs = new ArrayList();
    
    public DocumentBar() {
        super();
        
        tabGroup.selectedToggleProperty().addListener(e-> {
            try {
                Stage stage = (Stage)(getScene().getWindow());
                DocumentTab tab = ((DocumentTab)(tabGroup.getSelectedToggle()));
                VecDocument document = tab.getDocument();
                stage.setTitle(document.getDocumentName());
                Context.setActiveDocument(((DocumentTab)tabGroup.getSelectedToggle()).getDocument());
            } catch (Exception err) {}
        });
    }
    
    public void addDocument(VecDocument document) {
        DocumentTab tab = new DocumentTab(document);
        tab.setToggleGroup(tabGroup);
        tab.setSelected(true);
        
        docs.add(document);
        tabs.add(tab);
        
        HBox.setMargin(tab, new Insets(0, -1, 0, 0));
        
        getChildren().add(tab);
    }
    
}
