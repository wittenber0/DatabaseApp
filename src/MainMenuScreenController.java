import javafx.event.ActionEvent;
import javafx.scene.control.Button;

/**
 * Created by FMF 7 on 6/9/2017.
 */
public class MainMenuScreenController {
    public Button viewDataButton;
    public Button editDataButton;
    public Button logoutButton;


    public void onEditData(ActionEvent actionEvent) {
        SceneMaster sceneMaster = SceneMaster.getSceneMaster();
        sceneMaster.setStage("Scenes/EditDBScreen.fxml");
    }

    public void onViewData(ActionEvent actionEvent) {
        System.out.println(StringChecker.split("This is, a test, lets see if this works"));
        SceneMaster sceneMaster = SceneMaster.getSceneMaster();
        sceneMaster.setStage("Scenes/ViewDataScreen.fxml");

    }
}
