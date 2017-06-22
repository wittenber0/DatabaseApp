import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;

/**
 * Created by FMF 7 on 6/19/2017.
 */
public class ViewDataScreenController {
    public ChoiceBox tableBox;
    public TableView dataView;

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
}
