package org.controlsfx.samples.checked;

import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import org.controlsfx.Sample;
import org.controlsfx.control.CheckListView;
import org.controlsfx.samples.Utils;

public class HelloCheckListView extends Application implements Sample {
    
    @Override public String getSampleName() {
        return "CheckListView";
    }
    
    @Override public String getJavaDocURL() {
        return Utils.JAVADOC_BASE + "org/controlsfx/control/CheckListView.html";
    }
    
    @Override public boolean includeInSamples() {
        return true;
    }
    
    @Override public Node getPanel(Stage stage) {
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(30, 30, 0, 30));
        
        final ObservableList<String> strings = FXCollections.observableArrayList();
        for (int i = 0; i <= 100; i++) {
            strings.add("Item " + i);
        }
        
        final Label checkedItemsLabel = new Label();
        final Label selectedItemsLabel = new Label();

        // CheckListView
        final CheckListView<String> checkListView = new CheckListView<>(strings);
        checkListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        checkListView.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<String>() {
            @Override public void onChanged(ListChangeListener.Change<? extends String> c) {
                updateText(selectedItemsLabel, c.getList());
            }
        });
        checkListView.getCheckModel().getSelectedItems().addListener(new ListChangeListener<String>() {
            @Override public void onChanged(ListChangeListener.Change<? extends String> c) {
                updateText(checkedItemsLabel, c.getList());
            }
        });
        grid.add(checkListView, 0, 0, 1, 3);
        
        // labels displaying state
        grid.add(new Label("Checked items: "), 1, 0);
        grid.add(checkedItemsLabel, 2, 0);
        
        grid.add(new Label("Selected items: "), 1, 1);
        grid.add(selectedItemsLabel, 2, 1);
        
        return grid;
    }
    
    protected void updateText(Label label, ObservableList<? extends String> list) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0, max = list.size(); i < max; i++) {
            sb.append(list.get(i));
            if (i < max - 1) {
                sb.append(", ");
            }
        }
        label.setText(sb.toString());
    }

    @Override public void start(Stage stage) throws Exception {
        stage.setTitle("CheckListView Demo");
        
        
        Scene scene = new Scene((Parent) getPanel(stage), 550, 550);
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}