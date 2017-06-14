import java.sql.*;
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

        LinkedList<String> columns = new LinkedList<String>();
        columns.add("ColumnName");

        createTable("ColumnTable", columns);
    }

    public boolean createTable(String name, LinkedList<String> columnList){
        try {
            Statement statement = connection.createStatement();
            String tableSQL = "CREATE TABLE "+ name + " (\n";
            String columnType = "varchar(255)";
            for(int i=0; i< columnList.size(); i++){
                String c = columnList.get(i);
                if(i==0){
                    String s =  c + " " + columnType;
                    tableSQL += s;
                }else {
                    String s = ", \n" + c + " " + columnType;
                    tableSQL += s;
                }
            }
            tableSQL += "\n)";

            System.out.println(tableSQL);

            return true;
        }catch(Exception e){
            System.out.println(e);
        }


        return false;
    }

    public boolean saveColumnName(String c){
        String addNameSQL = "";
        addNameSQL += "INSERT INTO ColumnTable VALUES "+c;

        try {
            Statement statement = connection.createStatement();
            statement.execute(addNameSQL);
            return true;
        }catch(Exception e){
            System.out.println(e);
        }

        System.out.println(addNameSQL);

        return false;
    }

}
