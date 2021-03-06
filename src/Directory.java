import javafx.scene.control.Tab;

import javax.jnlp.FileContents;
import javax.jnlp.FileOpenService;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by FMF 7 on 6/9/2017.
 */
public class Directory {
    private static Directory dir;
    private DBHandler handler;
    private LinkedList<String> columnNames = new LinkedList<String>();
    private LinkedList<Table> allTables = new LinkedList<Table>();
    private LinkedList<MyView> allMyViews = new LinkedList<MyView>();




    private Directory(){
        try {
            handler = new DBHandler();
            load();
        }catch(Exception e){
            System.out.println("Could not load:  "+ e);
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
            Table t = new Table(name.toUpperCase(), columns);
            allTables.add(t);
            handler.createTable(t.getName(), t.getColumns(), true);
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
        allMyViews = handler.setMyViews(allTables);

    }

    public LinkedList<Table> getAllTables(){
        System.out.println(allTables);
        return allTables;
    }

    public boolean saveEntry(String name, TableEntry entry){
        return handler.saveDataEntry(name, entry);
    }

    public LinkedList<TableEntry> doJoin(MyView v){
        return handler.saveMyViewData(v);
    }

    public boolean saveMyView(MyView v){
        allMyViews.add(v);
        return handler.saveMyView(v);
    }

    public LinkedList<MyView> getAllMyViews(){
        System.out.println(allMyViews);
        return allMyViews;
    }


    public void exportToCSV(MyView v){
        LinkedList<TableEntry> myViewData = doJoin(v);

        try {
            FileWriter f = new FileWriter("Output.csv");
            for(TableEntry te : myViewData){
                String row = "";
                for(int i=0; i<te.getValues().size(); i++){
                    String s = te.getValues().get(i);

                    if(i!=te.getValues().size()-1) {
                        row += s + ",";
                    }else{
                        row += s;
                    }
                }
                row = StringChecker.clean(row);
                row+="\n";
                f.write(row);

            }
            f.flush();


        }catch(Exception e){

            System.out.println("Could Not Export:  " + e);
        }
    }
}
