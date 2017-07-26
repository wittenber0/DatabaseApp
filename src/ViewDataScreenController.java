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
        System.out.println(currentMyView.rows.size());
        if(currentMyView.rows.size() ==0){
            errorLabel.setText("There is no data stored in this view");
            errorLabel.setVisible(true);
        }
        dataView.getColumns().removeAll(FXCollections.observableArrayList(dataView.getColumns()));


        LinkedList<TableColumn> columnList = new LinkedList<TableColumn>();
        for(int i=0; i<5; i++){
            TableColumn t = new TableColumn();

            t.setCellValueFactory(new PropertyValueFactory<TableEntry, String>("pointer"));
            columnList.add(t);

        }

        dataView.getColumns().addAll(columnList);
        System.out.println(currentMyView.rows);
        dataView.setItems(FXCollections.observableArrayList(currentMyView.rows));

        dir.exportToCSV(currentMyView);
    }
}
