import java.util.LinkedList;

/**
 * Created by FMF 7 on 6/30/2017.
 */
public class MyView implements IDirItem{
    public String name;
    public LinkedList<Table> tables;
    public LinkedList<String> columns;
    public String keyColumn;
    public LinkedList<TableEntry> rows;

    public MyView(String name, LinkedList<Table> tables, LinkedList<String> columns, String keyColumn){
        this.name = name;
        this.tables = tables;
        this.columns = columns;
        this.keyColumn = keyColumn;
        this.rows = populateRows();
    }

    private LinkedList<TableEntry> populateRows(){
        Directory dir = Directory.getInstance();
        return dir.doJoin(this);

    }

    public boolean saveMyView(){
        Directory dir = Directory.getInstance();
        return dir.saveMyView(this);
    }

    public String toString(){
        return name;
    }
}
