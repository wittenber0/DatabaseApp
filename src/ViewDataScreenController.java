import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.LinkedList;

/**
 * Created by FMF 7 on 6/19/2017.
 */
public class ViewDataScreenController {
    public ChoiceBox<Table> tableBox;
    public TableView dataView;
    private Table currentTable;

    public void initialize(){
        Directory dir = Directory.getInstance();
        tableBox.setItems(FXCollections.observableArrayList(dir.getAllTables()));


    }

    public void onLogout(ActionEvent actionEvent) {
        SceneMaster sceneMaster = SceneMaster.getSceneMaster();
        sceneMaster.setStage("Scenes/LoginScreen.fxml");
    }

    public void onBack(ActionEvent actionEvent) {
        SceneMaster sceneMaster = SceneMaster.getSceneMaster();
        sceneMaster.setStage("Scenes/MainMenuScreen.fxml");
    }

    public void onSubmit(ActionEvent actionEvent) {
        currentTable = tableBox.getValue();
        dataView.getColumns().removeAll(FXCollections.observableArrayList(dataView.getColumns()));


        LinkedList<TableColumn> columnList = new LinkedList<TableColumn>();
        for(int i=0; i<5; i++){
            TableColumn t = new TableColumn();

            t.setCellValueFactory(new PropertyValueFactory<TableEntry, String>("pointer"));
            columnList.add(t);

        }

        dataView.getColumns().addAll(columnList);
        System.out.println(currentTable.getRows());
        dataView.setItems(FXCollections.observableArrayList(currentTable.getRows()));
    }
}
