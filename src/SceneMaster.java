import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by FMF 7 on 6/9/2017.
 */
public class SceneMaster {
    private static SceneMaster sceneMaster;
    private Stage stage;

    private SceneMaster(){

    }

    public static SceneMaster getSceneMaster(){
        if(sceneMaster == null){
            sceneMaster = new SceneMaster();
        }
        return sceneMaster;
    }


    public void loadSceneMasterStage(Stage s){
        if(stage == null){
            stage=s;
            stage.setWidth(800);
            stage.setHeight(800);
        }
    }

    public boolean setStage(String location){
        if(stage != null) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource(location));
                stage.setTitle(location);
                stage.setScene(new Scene(root, stage.getWidth(), stage.getHeight()));
                stage.show();
                return true;
            } catch (Exception e) {
                System.out.println("Problem Setting Scene");
                return false;
            }
        }else{
            System.out.println("No Stage Loaded");
            return false;
        }
    }
}
