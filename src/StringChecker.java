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
                || s.contains(" ")
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
            return s;
        }
    }

}
