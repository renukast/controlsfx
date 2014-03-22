/**
 * Copyright (c) 2013, 2014, ControlsFX
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *     * Neither the name of ControlsFX, any associated website, nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL CONTROLSFX BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.controlsfx.samples;

import static org.controlsfx.control.decoration.Decorator.addDecoration;
import static org.controlsfx.control.decoration.Decorator.removeAllDecorations;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import org.controlsfx.ControlsFXSample;
import org.controlsfx.control.decoration.GraphicDecoration;
import org.controlsfx.control.decoration.StyleClassDecoration;

public class HelloDecorationPane extends ControlsFXSample {
    
    private final TextField field = new TextField();
    
    @Override public String getSampleName() {
        return "Decorations";
    }
    
    @Override public String getJavaDocURL() {
        return Utils.JAVADOC_BASE + "org/controlsfx/control/DecorationPane.html";
    }
    
    @Override public Node getPanel(final Stage stage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10, 10, 10, 10));
        root.setMaxHeight(Double.MAX_VALUE);
        
        root.getChildren().add(field);
        
        // for the sake of this sample we have to install a custom css file to
        // style the sample - but we can't do this until the scene is set on the
        // pane
        root.sceneProperty().addListener(new InvalidationListener() {
            @Override public void invalidated(Observable o) {
                if (root.getScene() != null) {
                    root.getScene().getStylesheets().add(HelloDecorationPane.class.getResource("decorations.css").toExternalForm());
                }
            }
        });
        
        return root;
    }
    
    @Override
    public Node getControlPanel() {
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(30, 30, 0, 30));
        
        int row = 0;
        
        // --- show decorations
        Label showDecorationsLabel = new Label("Show decorations: ");
        showDecorationsLabel.getStyleClass().add("property");
        grid.add(showDecorationsLabel, 0, row);
        ChoiceBox<String> decorationTypeBox = new ChoiceBox<>(FXCollections.observableArrayList("None", "Node", "CSS", "Node + CSS"));
        decorationTypeBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue<? extends String> o, String old, String newItem) {
                removeAllDecorations(field);
                switch (newItem) {
                    case "None": break;
                    case "Node": {
                        addDecoration(field, new GraphicDecoration(createDecoratorNode(Color.RED),Pos.TOP_LEFT));
                        addDecoration(field, new GraphicDecoration(createDecoratorNode(Color.RED),Pos.TOP_CENTER));
                        addDecoration(field, new GraphicDecoration(createDecoratorNode(Color.RED),Pos.TOP_RIGHT));
                        addDecoration(field, new GraphicDecoration(createDecoratorNode(Color.GREEN),Pos.CENTER_LEFT));
                        addDecoration(field, new GraphicDecoration(createDecoratorNode(Color.GREEN),Pos.CENTER));
                        addDecoration(field, new GraphicDecoration(createDecoratorNode(Color.GREEN),Pos.CENTER_RIGHT));
                        addDecoration(field, new GraphicDecoration(createDecoratorNode(Color.BLUE),Pos.BOTTOM_LEFT));
                        addDecoration(field, new GraphicDecoration(createDecoratorNode(Color.BLUE),Pos.BOTTOM_CENTER));
                        addDecoration(field, new GraphicDecoration(createDecoratorNode(Color.BLUE),Pos.BOTTOM_RIGHT));
                        break;
                    }
                    case "CSS": {
                        addDecoration(field, new StyleClassDecoration("warning"));
                        break;
                    }
                    case "Node + CSS": {
                        addDecoration(field, new GraphicDecoration(createDecoratorNode(Color.GREEN),Pos.CENTER_RIGHT));
                        addDecoration(field, new StyleClassDecoration("success"));
                        break;
                    }
                }
            }
        });
        grid.add(decorationTypeBox, 1, row++);
        
        // --- Toggle text field visibility
        Label showTextFieldLabel = new Label("TextField visible: ");
        showTextFieldLabel.getStyleClass().add("property");
        grid.add(showTextFieldLabel, 0, row);
        ToggleButton fieldVisibleBtn = new ToggleButton("Press");
        fieldVisibleBtn.setSelected(true);
        field.visibleProperty().bindBidirectional(fieldVisibleBtn.selectedProperty());
        grid.add(fieldVisibleBtn, 1, row++);
        
        return grid;
    }
    
    private Node createDecoratorNode(Color color) {
    	Circle d = new Circle(5);
        d.setFill(color);
        return d;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}