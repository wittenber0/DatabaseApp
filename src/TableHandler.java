import java.util.LinkedList;

/**
 * Created by FMF 7 on 6/13/2017.
 */
public class TableHandler {

    public TableHandler(){

    }

    public boolean makeTable(String name, LinkedList<String> columns){
        try {
            DBHandler dbHandler = new DBHandler();
            dbHandler.createTable(name, columns);
        }catch(Exception e){
            System.out.println(e);
        }
        return false;

    }
}
