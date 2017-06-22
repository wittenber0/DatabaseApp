import java.util.LinkedList;

/**
 * Created by FMF 7 on 6/13/2017.
 */
public class Table {
    private String name;
    private LinkedList<String> columns = new LinkedList<String>();
    private LinkedList<LinkedList<String>> rows = new LinkedList<LinkedList<String>>();

    public Table(String n, LinkedList<String> c){
        name= n;
        columns= c;
    }

    public Table(String n, LinkedList<String> c, LinkedList<LinkedList<String>> r){
        name = n;
        columns= c;
        rows= r;
    }

    public String toString(){
        return name;
    }

    public LinkedList<LinkedList<String>> getRows() {
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
