import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

/**
 * Created by FMF 7 on 6/22/2017.
 */
public class EditDataScreenController {
    public ChoiceBox tableBox;
    public TextField fileField;
    public Button addFileButton;

    public void initialize(){
        Directory dir = Directory.getInstance();

        tableBox.setItems(FXCollections.observableArrayList(dir.getAllTables()));

    }

    public void onAddFile(ActionEvent actionEvent) {
    }

    public void onLogout(ActionEvent actionEvent) {
        SceneMaster sceneMaster = SceneMaster.getSceneMaster();
        sceneMaster.setStage("Scenes/LoginScreen.fxml");
    }

    public void onBack(ActionEvent actionEvent) {
        SceneMaster sceneMaster = SceneMaster.getSceneMaster();
        sceneMaster.setStage("Scenes/EditDBScreen.fxml");
    }
}
