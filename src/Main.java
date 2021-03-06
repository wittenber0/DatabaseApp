import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        SceneMaster sceneMaster = SceneMaster.getSceneMaster();
        sceneMaster.loadSceneMasterStage(primaryStage);
        sceneMaster.setStage("Scenes/MainMenuScreen.fxml");
    }


    public static void main(String[] args) {
        Directory dir = Directory.getInstance();
        launch(args);
    }
}
