import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginScreenController {
    public Label usernameLabel;
    public TextField usernameField;
    public Label passwordLabel;
    public TextField passwordField;
    public Button loginButtin;
    public Label errorLabel;

    public void initialize(){
        errorLabel.setVisible(false);
    }


    public void onSubmit(ActionEvent actionEvent) {
        SceneMaster sceneMaster = SceneMaster.getSceneMaster();
        sceneMaster.setStage("Scenes/MainMenuScreen.fxml");
    }
}
