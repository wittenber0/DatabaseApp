import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.LinkedList;

/**
 * Created by FMF 7 on 6/19/2017.
 */
public class ViewDataScreenController {
    public ChoiceBox<MyView> tableBox;
    public TableView dataView;
    public Label errorLabel;
    private MyView currentMyView;

    public void initialize(){
        errorLabel.setVisible(false);
        Directory dir = Directory.getInstance();
        tableBox.setItems(FXCollections.observableArrayList(dir.getAllMyViews()));


    }

    public void onLogout(ActionEvent actionEvent) {
        SceneMaster sceneMaster = SceneMaster.getSceneMaster();
        sceneMaster.setStage("Scenes/MainMenuScreen.fxml");
    }

    public void onBack(ActionEvent actionEvent) {
        SceneMaster sceneMaster = SceneMaster.getSceneMaster();
        sceneMaster.setStage("Scenes/MainMenuScreen.fxml");
    }

    public void onSubmit(ActionEvent actionEvent) {
        Directory dir = Directory.getInstance();
        currentMyView = tableBox.getValue();
        LinkedList<TableEntry> rows = dir.doJoin(currentMyView);
        System.out.println(rows.size());
        if(rows.size() ==0){
            errorLabel.setText("There is no data for this view");
            errorLabel.setVisible(true);
        }
        dataView.getColumns().removeAll(FXCollections.observableArrayList(dataView.getColumns()));


        LinkedList<TableColumn> columnList = new LinkedList<TableColumn>();
        for(int i=0; i<currentMyView.columns.size(); i++){
            TableColumn t = new TableColumn(currentMyView.columns.get(i));

            t.setCellValueFactory(new PropertyValueFactory<TableEntry, String>("pointer"));
            columnList.add(t);

        }

        dataView.getColumns().addAll(columnList);
        System.out.println(rows);
        dataView.setItems(FXCollections.observableArrayList(rows));

        dir.exportToCSV(currentMyView);
    }
}
