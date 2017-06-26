import java.util.LinkedList;

/**
 * Created by FMF 7 on 6/13/2017.
 */
public class Table {
    private String name;
    private LinkedList<String> columns = new LinkedList<String>();
    private LinkedList<TableEntry> rows = new LinkedList<TableEntry>();

    public Table(String n, LinkedList<String> c){
        name= n;
        columns= c;
    }

    public Table(String n, LinkedList<String> c, LinkedList<TableEntry> r){
        name = n;
        columns= c;
        rows= r;
    }

    public String toString(){
        return name;
    }

    public LinkedList<TableEntry> getRows() {
        return rows;
    }

    public LinkedList<String> getColumns() {
        return columns;
    }

    public boolean saveEntry(String[] entry){
        Directory dir = Directory.getInstance();
        return dir.saveEntry(this.name, entry);
    }

}
