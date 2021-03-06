import java.util.LinkedList;

/**
 * Created by FMF 7 on 6/13/2017.
 */
public class Table implements IDirItem{
    private String name;
    private LinkedList<String> columns = new LinkedList<String>();
    private LinkedList<TableEntry> rows = new LinkedList<TableEntry>();

    public Table(String n, LinkedList<String> c){
        name= n;
        columns= c;
    }

    public Table(String n, LinkedList<String> c, LinkedList<TableEntry> r){
        name = n.toUpperCase();
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

    public boolean saveEntry(TableEntry entry){
        Directory dir = Directory.getInstance();
        rows.add(entry);
        return dir.saveEntry(this.name, entry);
    }

    public String getName(){
        return name;
    }

}
