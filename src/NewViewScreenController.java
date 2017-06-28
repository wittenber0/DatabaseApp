import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

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

    private Table currentTable;

    public void initialize(){
        Directory dir = Directory.getInstance();
        allTablesView.setItems(FXCollections.observableArrayList(dir.getAllTables()));
        allTablesView.refresh();


        allTablesView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Table>(){
            public void changed(ObservableValue<? extends Table> observable, Table oldValue, Table newValue) {
                currentTable = newValue;
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
    }

    public void onRemoveTable(ActionEvent actionEvent) {
    }

    public void onSetKey(ActionEvent actionEvent) {
    }

    public void onSaveView(ActionEvent actionEvent) {
        System.out.println(currentTable.getColumns());
    }
}
