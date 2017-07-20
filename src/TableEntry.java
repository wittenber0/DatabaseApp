import java.util.LinkedList;

/**
 * Created by FMF 7 on 6/26/2017.
 */
public class TableEntry {


    private LinkedList<String> values = new LinkedList<String>();
    private int loader;

    public TableEntry(LinkedList<String> v){
        values = v;
        loader=values.size();
    }

    public TableEntry(String[] v){
        for (int i=0; i<v.length; i++){
            values.add(v[i]);
        }
        loader=values.size();
    }

    public String toString(){
        return values.toString();
    }

    public String getPointer(){
        loader++;
        if(loader >= values.size()){
            loader=0;
            return values.get(0);
        }else{
            return values.get(loader);
        }

    }

    public LinkedList<String> getValues() {
        return values;
    }
}
