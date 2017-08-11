import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * Created by FMF 7 on 8/11/2017.
 */
public class DeleteScreenController {
    public Button backButton;
    public Button logoutButton;
    public ListView tableView;
    public Button deleteTableButton;
    public ListView myViewView;
    public Button deleteMyViewButton;
    public Label errorLabel;
    public Table currentTable;
    public MyView currentMyView;

    public void initialize(){
        Directory dir = Directory.getInstance();
        tableView.setItems(FXCollections.observableArrayList(dir.getAllTables()));
        myViewView.setItems(FXCollections.observableArrayList(dir.getAllMyViews()));

        deleteTableButton.setDisable(true);
        deleteTableButton.setOpacity(.5);
        deleteMyViewButton.setDisable(true);
        deleteMyViewButton.setOpacity(.5);

        errorLabel.setVisible(false);


        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Table>(){
            public void changed(ObservableValue<? extends Table> observable, Table oldValue, Table newValue) {
                errorLabel.setVisible(false);
                deleteTableButton.setDisable(false);
                deleteTableButton.setOpacity(1);

                currentTable = newValue;
            }
        });

        myViewView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MyView>(){
            public void changed(ObservableValue<? extends MyView> observable, MyView oldValue, MyView newValue) {
                deleteMyViewButton.setDisable(false);
                deleteMyViewButton.setOpacity(1);

                currentMyView = newValue;
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

    public void onDeleteTable(ActionEvent actionEvent) {
        boolean ready = true;
        for(int i=0; i< Directory.getInstance().getAllMyViews().size(); i++){
            MyView v = Directory.getInstance().getAllMyViews().get(i);
            if(v.tables.contains(currentTable)){
                ready = false;
            }

        }
        if(ready) {
            Directory.getInstance().deleteTable(currentTable);
            initialize();
        }else{
            errorLabel.setText("This table is used in views that still exist. Delete the views first");
            errorLabel.setVisible(true);
        }
    }

    public void onDeleteMyView(ActionEvent actionEvent) {
        Directory.getInstance().deleteMyView(currentMyView);
        initialize();
    }
}
