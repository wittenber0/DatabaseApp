import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        SceneMaster sceneMaster = SceneMaster.getSceneMaster();
        sceneMaster.loadSceneMasterStage(primaryStage);
        sceneMaster.setStage("Scenes/LoginScreen.fxml");
    }


    public static void main(String[] args) {
        try{
            DBHandler myDB = new DBHandler();
        }catch(Exception e){
            System.out.println(e);
        }
        launch(args);
    }
}
