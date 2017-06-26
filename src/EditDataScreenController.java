import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by FMF 7 on 6/22/2017.
 */
public class EditDataScreenController {
    public ChoiceBox<Table> tableBox;
    public TextField fileField;
    public Button addFileButton;

    public void initialize(){
        Directory dir = Directory.getInstance();
        addFileButton.setDisable(true);
        addFileButton.setOpacity(.5);

        tableBox.setItems(FXCollections.observableArrayList(dir.getAllTables()));

    }


    public void onLogout(ActionEvent actionEvent) {
        SceneMaster sceneMaster = SceneMaster.getSceneMaster();
        sceneMaster.setStage("Scenes/LoginScreen.fxml");
    }

    public void onBack(ActionEvent actionEvent) {
        SceneMaster sceneMaster = SceneMaster.getSceneMaster();
        sceneMaster.setStage("Scenes/EditDBScreen.fxml");
    }

    public void onAddFile(ActionEvent actionEvent) {

        try {
            Table table = tableBox.getValue();
            String pathDirectory = fileField.getText();
            System.out.println(pathDirectory);
            Scanner scanner = new Scanner(new File(pathDirectory));
            LinkedList<Table> rows = new LinkedList<Table>();
            while(scanner.hasNext()) {
                String str = scanner.next();

                String[] props = str.split(",");
                TableEntry entry = new TableEntry(props);
                table.saveEntry(entry);
            }


        }catch(Exception e){
            System.out.println(e);
        }

        fileField.clear();
        addFileButton.setDisable(true);
        addFileButton.setOpacity(.5);


    }

    public void onFileKeyReleased(KeyEvent keyEvent) {
        if(fileField.getText().equals("")){
            addFileButton.setDisable(true);
            addFileButton.setOpacity(.5);
        }else{
            addFileButton.setDisable(false);
            addFileButton.setOpacity(1);
        }
    }
}
