import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.util.LinkedList;

/**
 * Created by FMF 7 on 6/12/2017.
 */
public class EditTableScreenController {
    public ListView allColumnsView;
    public ListView currentColumnsView;
    public TextField newColumnField;
    public TextField tableNameField;
    public Button backButton;
    public Button logoutButton;
    public Button newColumnButton;
    public Button addColumnButton;
    public Button removeColumnButton;
    public Button saveTableButton;

    public String allColumnSelected;
    public String currentColumnSelected;
    public LinkedList<String> currentColumns;

    public void initialize(){
        Directory dir = Directory.getInstance();
        allColumnsView.setItems(FXCollections.observableArrayList(dir.getColumnNames()));
        allColumnsView.refresh();

        allColumnSelected = null;
        currentColumnSelected = null;
        currentColumns = new LinkedList<String>();

        setBoundaries();


        allColumnsView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                addColumnButton.setDisable(false);
                addColumnButton.setOpacity(1);

                allColumnSelected = newValue;
            }
        });

        currentColumnsView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                addColumnButton.setDisable(false);
                addColumnButton.setOpacity(1);
                removeColumnButton.setDisable(false);
                removeColumnButton.setOpacity(1);

                currentColumnSelected = newValue;
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

    public void onNewColumn(ActionEvent actionEvent) {
        Directory dir = Directory.getInstance();
        dir.saveColumnName(newColumnField.getText().toUpperCase());
        newColumnField.clear();
        newColumnButton.setDisable(true);
        newColumnButton.setOpacity(.5);
        allColumnsView.setItems(FXCollections.observableArrayList(dir.getColumnNames()));
        allColumnsView.refresh();
    }

    public void onAddColumn(ActionEvent actionEvent) {
        currentColumns.add(allColumnSelected);

        currentColumnsView.setItems(FXCollections.observableArrayList(currentColumns));
        currentColumnsView.refresh();

        setSaveButton();


    }

    public void onRemoveColumn(ActionEvent actionEvent) {
        currentColumns.remove(currentColumnSelected);

        currentColumnsView.setItems(FXCollections.observableArrayList(currentColumns));
        currentColumnsView.refresh();

        if(currentColumns.size() ==0){
            removeColumnButton.setDisable(true);
            removeColumnButton.setOpacity(.5);
        }

        setSaveButton();
    }

    public void onSaveTable(ActionEvent actionEvent) {
        Directory dir = Directory.getInstance();
        dir.makeTable(tableNameField.getText().toUpperCase(), currentColumns);
        initialize();
    }

    public void newColumnKeyReleased(KeyEvent keyEvent) {
        if(!newColumnField.getText().equals("")){
            newColumnButton.setOpacity(1);
            newColumnButton.setDisable(false);
        }
    }

    public void onNameKeyReleased(KeyEvent keyEvent) {
        setSaveButton();
    }

    public void setBoundaries(){
        newColumnField.clear();
        tableNameField.clear();
        newColumnButton.setDisable(true);
        newColumnButton.setOpacity(.5);
        currentColumnsView.setItems(FXCollections.observableArrayList());
        currentColumnsView.refresh();

        saveTableButton.setOpacity(.5);
        saveTableButton.setDisable(true);
        addColumnButton.setDisable(true);
        addColumnButton.setOpacity(.5);
        removeColumnButton.setDisable(true);
        removeColumnButton.setOpacity(.5);
    }

    private void setSaveButton(){
        if(tableNameField.getText().equals("") || currentColumns.size()==0){
            saveTableButton.setOpacity(.5);
            saveTableButton.setDisable(true);
        }else{
            saveTableButton.setOpacity(1);
            saveTableButton.setDisable(false);
        }
    }
}
