/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vectory;

import java.io.File;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.ToggleGroup;

/**
 *
 * @author Berube
 */
public class VecDocument {
    private final SimpleObjectProperty<DocumentPane> documentPane = new SimpleObjectProperty(new DocumentPane());
    private final SimpleStringProperty documentName = new SimpleStringProperty();
    private File documentFile;
    
    
    public VecDocument() {
        this("Untitled"); 
    }
    
    public VecDocument(String documentName) {
        this.documentName.set(documentName);
        init();
    }
    
    public VecDocument(File documentFile) {

    }
    
    private void init() {
        Context.setActiveDocument(this);
    }
    
    public String getDocumentName() {
        return documentName.get();
    }
    
    public void setDocumentName(String name) {
        documentName.set(name);
    }
    
    public LayerGroup getDocumentRoot() {
        return getDocumentPane().getRoot();
    }
    
    public DocumentPane getDocumentPane() {
        return documentPane.get();
    }
    
    public SimpleObjectProperty<DocumentPane> documentPaneProperty() {
        return documentPane;
    }
    
}
