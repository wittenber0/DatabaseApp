import java.util.LinkedList;

/**
 * Created by FMF 7 on 6/9/2017.
 */
public class Directory {
    private static Directory dir;
    private DBHandler handler;
    private LinkedList<String> columnNames;




    private Directory(){
        try {
            handler = new DBHandler();
            columnNames = handler.getColumnNames();

        }catch(Exception e){

        }

    };

    public static Directory getInstance(){
        if(dir==null){
            dir = new Directory();
        }
        return dir;
    }

    public boolean makeTable(String name, LinkedList<String> columns){
        try {
            handler.createTable(name, columns);
        }catch(Exception e){
            System.out.println(e);
        }
        return false;

    }

    public boolean saveColumnName(String c){
        columnNames.add(c);
        System.out.println(columnNames);
        return handler.addColumnName(c);
    }

    public LinkedList<String> getColumnNames(){
        return columnNames;
    }
}
