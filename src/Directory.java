import javafx.scene.control.Tab;

import java.util.LinkedList;

/**
 * Created by FMF 7 on 6/9/2017.
 */
public class Directory {
    private static Directory dir;
    private DBHandler handler;
    private LinkedList<String> columnNames = new LinkedList<String>();
    private LinkedList<Table> allTables = new LinkedList<Table>();




    private Directory(){
        try {
            handler = new DBHandler();
            load();


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
            handler.createTable(name.toUpperCase(), columns);
            Table t = new Table(name.toUpperCase(), columns);
            allTables.add(t);
            return true;
        }catch(Exception e){
            System.out.println("ya" + e);
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

    private void load(){
        columnNames = handler.getColumnNames();
        allTables = handler.setTables();
    }

    public LinkedList<Table> getAllTables(){
        System.out.println(allTables);
        return allTables;
    }

    public boolean saveEntry(String name, TableEntry entry){
        return handler.saveDataEntry(name, entry);
    }

}
