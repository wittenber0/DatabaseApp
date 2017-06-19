import java.util.LinkedList;

/**
 * Created by FMF 7 on 6/13/2017.
 */
public class Table {
    private String name;
    private LinkedList<String> columns = new LinkedList<String>();
    private LinkedList<String> rows = new LinkedList<String>();

    public Table(String n, LinkedList<String> c){
        name= n;
        columns= c;
    }

    public Table(String n, LinkedList<String> c, LinkedList<String> r){
        name = n;
        columns= c;
        rows= r;
    }

    public String toString(){
        return name;
    }
}
