import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.util.LinkedList;

/**
 * Created by FMF 7 on 6/27/2017.
 */
public class NewViewScreenController {
    public ListView allTablesView;
    public ListView viewTablesView;
    public ListView allColumnsView;
    public ListView uniqueKeyView;
    public Button setKeyButton;
    public Button saveViewButton;
    public Button addTableButton;
    public Button removeTableButton;
    public TextField viewNameField;

    private Table currentAllTable;
    private Table currentViewTable;
    private String currentAllColumn;
    private LinkedList<String> currentAllColumns = new LinkedList<String>();
    private LinkedList<Table> currentViewTables = new LinkedList<Table>();

    public void initialize(){
        Directory dir = Directory.getInstance();
        allTablesView.setItems(FXCollections.observableArrayList(dir.getAllTables()));
        allTablesView.refresh();

        setBoundaries();

        allTablesView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Table>(){
            public void changed(ObservableValue<? extends Table> observable, Table oldValue, Table newValue) {
                currentAllTable = newValue;

                addTableButton.setOpacity(1);
                addTableButton.setDisable(false);
            }
        });

        allColumnsView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                currentAllColumn = newValue;

                setKeyButton.setOpacity(1);
                setKeyButton.setDisable(false);
            }
        });

        viewTablesView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Table>(){
            public void changed(ObservableValue<? extends Table> observable, Table oldValue, Table newValue) {
                currentViewTable = newValue;

                removeTableButton.setOpacity(1);
                removeTableButton.setDisable(false);
            }
        });
    }

    public void onBack(ActionEvent actionEvent) {
        SceneMaster sceneMaster = SceneMaster.getSceneMaster();
        sceneMaster.setStage("Scenes/EditDBScreen.fxml");
    }

    public void onLogout(ActionEvent actionEvent) {
        SceneMaster sceneMaster = SceneMaster.getSceneMaster();
        sceneMaster.setStage("Scenes/LoginScreen.fxml");
    }

    public void onAddTable(ActionEvent actionEvent) {
        if(!currentViewTables.contains(currentAllTable)) {
            currentViewTables.add(currentAllTable);
        }
        viewTablesView.setItems(FXCollections.observableArrayList(currentViewTables));
        currentAllColumns.addAll(currentAllTable.getColumns());

        setAllColumnsView();

        if(!viewNameField.getText().equals("")){
            saveViewButton.setOpacity(1);
            saveViewButton.setDisable(false);
        }
    }

    public void onRemoveTable(ActionEvent actionEvent) {
        currentViewTables.remove(currentViewTable);
        viewTablesView.setItems(FXCollections.observableArrayList(currentViewTables));
        viewTablesView.refresh();
        currentAllColumns.clear();

        setAllColumnsView();

        if(currentViewTables.size() ==0){
            setKeyButton.setOpacity(.5);
            setKeyButton.setDisable(true);
            saveViewButton.setOpacity(.5);
            saveViewButton.setDisable(true);
            removeTableButton.setOpacity(.5);
            removeTableButton.setDisable(true);
        }

        uniqueKeyView.setItems(FXCollections.observableArrayList());
        uniqueKeyView.refresh();
    }

    public void onSetKey(ActionEvent actionEvent) {
        uniqueKeyView.setItems(FXCollections.observableArrayList(currentAllColumn));
        uniqueKeyView.refresh();
    }

    public void onSaveView(ActionEvent actionEvent) {
        System.out.println(currentAllTable.getColumns());
        setBoundaries();
    }

    public void setBoundaries(){
        addTableButton.setOpacity(.5);
        addTableButton.setDisable(true);
        removeTableButton.setOpacity(.5);
        removeTableButton.setDisable(true);
        setKeyButton.setOpacity(.5);
        setKeyButton.setDisable(true);
        saveViewButton.setOpacity(.5);
        saveViewButton.setDisable(true);
        viewNameField.clear();
        viewTablesView.setItems(FXCollections.observableArrayList());
        viewTablesView.refresh();
        allColumnsView.setItems(FXCollections.observableArrayList());
        allColumnsView.refresh();
        uniqueKeyView.setItems(FXCollections.observableArrayList());
        uniqueKeyView.refresh();
    }

    public void onViewNameKeyReleased(KeyEvent keyEvent) {
        if(viewNameField.getText().equals("") || currentViewTables.size()==0){
            saveViewButton.setOpacity(.5);
            saveViewButton.setDisable(true);
        }else{
            saveViewButton.setOpacity(1);
            saveViewButton.setDisable(false);
        }
    }

    private void setAllColumnsView(){
        LinkedList<String> cl = new LinkedList<String>();
        for (Table t : currentViewTables){
            for (String c : t.getColumns()){
                if(!cl.contains(c)){
                    cl.add(c);
                }
            }
        }
        LinkedList<String> r = new LinkedList<String>();
        for (String c : cl){
            for (Table t : currentViewTables){
                if(!t.getColumns().contains(c) && !r.contains(c)){
                    r.add(c);
                }
            }

        }
        cl.removeAll(r);

        allColumnsView.setItems(FXCollections.observableArrayList(cl));
        allColumnsView.refresh();
    }

}
