import java.util.LinkedList;

/**
 * Created by FMF 7 on 6/26/2017.
 */
public class TableEntry {
    private LinkedList<String> values;
    private int loader;

    public TableEntry(LinkedList<String> v){
        values = v;
        loader=v.size();
    }

    public String toString(){
        return values.toString();
    }

    public String getValues(){
        loader++;
        if(loader >= values.size()){
            loader=0;
            return values.get(0);
        }else{
            return values.get(loader);
        }
    }
}
