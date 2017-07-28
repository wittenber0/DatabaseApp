import java.util.LinkedList;

/**
 * Created by FMF 7 on 7/25/2017.
 */
public class StringChecker {

    public StringChecker(){}

    public static String check(String s) throws InvalidSymbolException{
        s = s.toUpperCase();
        if(s.contains("'")
                || s.contains(".")
                || s.contains("\n")
                || s.contains("/")
                || s.contains(",")
                || s.contains("*")
                || s.contains("&")
                || s.contains("%")
                || s.contains("?")
                || s.contains("TABLE")
                || s.contains("INSERT")
                || s.contains("JOIN")
                || s.contains("UNION")
                || s.contains("FOR")){
            throw new InvalidSymbolException();
        }else{
            return s.replace(" ", "_");
        }
    }

    public static LinkedList<String> split(String s){
        LinkedList<String> loStrings = new LinkedList<String>();

        while(s.indexOf(",")>0){
            int end = s.indexOf(",");
            loStrings.add(s.substring(0,end));
            s=s.substring(end+1,s.length());
        }
        loStrings.add(s);
        return loStrings;
    }

    public static String clean(String s){
        s = s.replace("\r", "");
        return s;
    }
}
