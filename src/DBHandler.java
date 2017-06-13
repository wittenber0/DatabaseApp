import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

/**
 * Created by FMF 7 on 6/13/2017.
 */
public class DBHandler {

    private Connection connection;

    public DBHandler(Connection c) {connection = c;}

    public DBHandler() throws Exception{
        open();
    }

    public void open() throws Exception{
        try{
            DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
            connection = DriverManager.getConnection("jdbc:derby:myDB;create=true");
        }catch(SQLException e){
            System.out.print("Connection Failed");
            throw new Exception();
        }
        System.out.println("myDB Registered and Connected");
    }

    public boolean createTable(String name, LinkedList<String> columnList){
        try {
            Statement statement = connection.createStatement();
            String tableSQL = "CREATE TABLE "+ name + "(\n ";
            String columnType = "varchar(255)";
            for(String c : columnList){
                String s = c + " " + columnType +",\n ";
                tableSQL += s;
            }

            System.out.println(tableSQL);
        }catch(Exception e){
            System.out.println(e);
        }


        return false;
    }
}
