import java.util.LinkedList;

/**
 * Created by FMF 7 on 6/30/2017.
 */
public class MyView {
    private String name;
    private LinkedList<Table> tables;
    private LinkedList<String> columns;
    private String keyColumn;
    private LinkedList<TableEntry> rows;

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
}
