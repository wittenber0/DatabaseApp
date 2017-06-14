import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

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

    public void initialize(){
        newColumnField.clear();
        tableNameField.clear();
    }

    public void onBack(ActionEvent actionEvent) {
        SceneMaster sceneMaster = SceneMaster.getSceneMaster();
        sceneMaster.setStage("Scenes/EditDBScreen.fxml");
    }

    public void onLogout(ActionEvent actionEvent) {
        SceneMaster sceneMaster = SceneMaster.getSceneMaster();
        sceneMaster.setStage("Scenes/LoginScreen.fxml");
    }

    public void onNewColumn(ActionEvent actionEvent) {
        try {
            DBHandler dbHandler = new DBHandler();
            dbHandler.saveColumnName(newColumnField.getText());
            newColumnField.clear();
            newColumnButton.setDisable(true);
            newColumnButton.setOpacity(.5);
        }catch(Exception e) {

        }
    }

    public void onAddColumn(ActionEvent actionEvent) {


    }

    public void onRemoveColumn(ActionEvent actionEvent) {
    }

    public void onSaveTable(ActionEvent actionEvent) {
        TableHandler tableHandler = new TableHandler();
        
    }

    public void newColumnKeyReleased(KeyEvent keyEvent) {
        if(!newColumnField.getText().equals("")){
            newColumnButton.setOpacity(1);
            newColumnButton.setDisable(false);
        }
    }
}
