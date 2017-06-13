/**
 * Created by FMF 7 on 6/9/2017.
 */
public class Directory {
    private static Directory dir;




    private Directory(){};

    public static Directory getInstance(){
        if(dir==null){
            dir = new Directory();
        }
        return dir;
    }
}
