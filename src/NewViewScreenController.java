import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.io.FileWriter;
import java.util.LinkedList;

/**
 * Created by FMF 7 on 6/27/2017.
 */
public class NewViewScreenController {
    public ListView allTablesView;
    public ListView myViewTablesView;
    public ListView allColumnsView;
    public ListView uniqueKeyView;
    public ListView sharedColumnsView;
    public Button setKeyButton;
    public Button saveViewButton;
    public Button addTableButton;
    public Button removeTableButton;
    public Button addColumnButton;
    public Button removeColumnButton;
    public TextField viewNameField;
    public ListView myViewColumnsView;
    public Label errorLabel;

    private Table currentAllTable;
    private Table currentMyViewTable;
    private String currentSharedColumn;
    private String currentAllColumn;
    private String currentMyViewColumn;
    private String currentKey;


    private LinkedList<Table> currentMyViewTables = new LinkedList<Table>();
    private LinkedList<String> currentMyViewColumns = new LinkedList<String>();

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

        myViewTablesView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Table>(){
            public void changed(ObservableValue<? extends Table> observable, Table oldValue, Table newValue) {
                currentMyViewTable = newValue;

                removeTableButton.setOpacity(1);
                removeTableButton.setDisable(false);
            }
        });

        allColumnsView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                currentAllColumn = newValue;

                addColumnButton.setOpacity(1);
                addColumnButton.setDisable(false);

            }
        });

        myViewColumnsView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                currentMyViewColumn = newValue;

                removeColumnButton.setOpacity(1);
                removeColumnButton.setDisable(false);
            }
        });

        sharedColumnsView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                currentSharedColumn = newValue;

                setKeyButton.setOpacity(1);
                setKeyButton.setDisable(false);
            }
        });


    }

    public void onBack(ActionEvent actionEvent) {
        SceneMaster sceneMaster = SceneMaster.getSceneMaster();
        sceneMaster.setStage("Scenes/EditDBScreen.fxml");
    }

    public void onLogout(ActionEvent actionEvent) {
        SceneMaster sceneMaster = SceneMaster.getSceneMaster();
        sceneMaster.setStage("Scenes/MainMenuScreen.fxml");
    }

    public void onAddTable(ActionEvent actionEvent) {
        if(!currentMyViewTables.contains(currentAllTable)) {
            currentMyViewTables.add(currentAllTable);
        }
        myViewTablesView.setItems(FXCollections.observableArrayList(currentMyViewTables));

        setSharedColumnsView();
        setAllColumnsView();

        if((currentKey != null) && (!currentAllTable.getColumns().contains(currentKey))){
            uniqueKeyView.setItems(FXCollections.observableArrayList());
            uniqueKeyView.refresh();
        }

        setSaveable();

        if(currentMyViewTables.size()==2){
            addTableButton.setDisable(true);
            addTableButton.setOpacity(.5);
        }
    }

    public void onRemoveTable(ActionEvent actionEvent) {
        currentMyViewTables.remove(currentMyViewTable);


        LinkedList<String> r = new LinkedList<String>();

        System.out.println("Check");
        for (String c : currentMyViewColumns) {
            if (currentMyViewTable.getColumns().contains(c)) {
                r.add(c);
            }
        }

        currentMyViewColumns.removeAll(r);

        myViewTablesView.setItems(FXCollections.observableArrayList(currentMyViewTables));
        myViewTablesView.refresh();
        myViewColumnsView.setItems(FXCollections.observableArrayList(currentMyViewColumns));
        myViewColumnsView.refresh();

        setSharedColumnsView();
        setAllColumnsView();

        if(currentMyViewTables.size() ==0){
            setKeyButton.setOpacity(.5);
            setKeyButton.setDisable(true);
            removeTableButton.setOpacity(.5);
            removeTableButton.setDisable(true);
        }

        uniqueKeyView.setItems(FXCollections.observableArrayList());
        uniqueKeyView.refresh();
        setSaveable();

        if(currentMyViewTables.size()< 2){
            addTableButton.setDisable(false);
            addTableButton.setOpacity(1);
        }
    }

    public void onSetKey(ActionEvent actionEvent) {
        currentKey = currentSharedColumn;
        uniqueKeyView.setItems(FXCollections.observableArrayList(currentKey));
        uniqueKeyView.refresh();
        setSaveable();
    }

    public void onSaveView(ActionEvent actionEvent) {
        try {
            StringChecker.check(viewNameField.getText());
            MyView v = new MyView(viewNameField.getText(), currentMyViewTables, currentMyViewColumns, currentKey);
            v.saveMyView();
            setBoundaries();
            setSaveable();
        }catch(Exception e){
            errorLabel.setVisible(true);
            setSaveable();
        }
    }

    public void setBoundaries(){
        addTableButton.setOpacity(.5);
        addTableButton.setDisable(true);
        removeTableButton.setOpacity(.5);
        removeTableButton.setDisable(true);
        setKeyButton.setOpacity(.5);
        setKeyButton.setDisable(true);
        removeColumnButton.setOpacity(.5);
        removeColumnButton.setDisable(true);
        addColumnButton.setOpacity(.5);
        addColumnButton.setDisable(true);
        setSaveable();
        viewNameField.clear();
        myViewTablesView.setItems(FXCollections.observableArrayList());
        myViewTablesView.refresh();
        sharedColumnsView.setItems(FXCollections.observableArrayList());
        sharedColumnsView.refresh();
        uniqueKeyView.setItems(FXCollections.observableArrayList());
        uniqueKeyView.refresh();
        errorLabel.setVisible(false);


    }

    public void onViewNameKeyReleased(KeyEvent keyEvent) {
        errorLabel.setVisible(false);
        setSaveable();
    }

    private void setSharedColumnsView(){
        LinkedList<String> cl = new LinkedList<String>();
        for (Table t : currentMyViewTables){
            for (String c : t.getColumns()){
                if(!cl.contains(c)){
                    cl.add(c);
                }
            }
        }
        LinkedList<String> r = new LinkedList<String>();
        for (String c : cl){
            for (Table t : currentMyViewTables){
                if(!t.getColumns().contains(c) && !r.contains(c)){
                    r.add(c);
                }
            }

        }
        cl.removeAll(r);

        sharedColumnsView.setItems(FXCollections.observableArrayList(cl));
        sharedColumnsView.refresh();
    }

    private void setAllColumnsView(){
        LinkedList<String> cl = new LinkedList<String>();
        for (Table t : currentMyViewTables){
            for (String c : t.getColumns()){
                if(!cl.contains(c)){
                    cl.add(c);
                }
            }
        }

        allColumnsView.setItems(FXCollections.observableArrayList(cl));
        allColumnsView.refresh();
    }

    public void onAddColumn(ActionEvent actionEvent) {
        if(!currentMyViewColumns.contains(currentAllColumn)) {
            currentMyViewColumns.add(currentAllColumn);
        }
        myViewColumnsView.setItems(FXCollections.observableArrayList(currentMyViewColumns));

        setSaveable();

    }

    public void onRemoveColumn(ActionEvent actionEvent) {
        currentMyViewColumns.remove(currentMyViewColumn);
        myViewColumnsView.setItems(FXCollections.observableArrayList(currentMyViewColumns));
        myViewColumnsView.refresh();

        if(currentMyViewColumns.size() ==0){
            removeColumnButton.setOpacity(.5);
            removeColumnButton.setDisable(true);
        }
        setSaveable();

    }

    private void setSaveable(){
        if(viewNameField.getText().equals("") || currentMyViewColumns.size()==0 || currentMyViewTables.size() ==0 || uniqueKeyView.getItems().size()==0 || errorLabel.isVisible()){
            saveViewButton.setOpacity(.5);
            saveViewButton.setDisable(true);
        }else{
            saveViewButton.setOpacity(1);
            saveViewButton.setDisable(false);
        }

    }
}
