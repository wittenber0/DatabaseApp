import java.sql.*;
import java.util.ArrayList;
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

        LinkedList<String> TableNames = new LinkedList<String>();
        TableNames.add("TableName");


        try{
            connection.createStatement().execute("SELECT * FROM ColumnTable");
            System.out.println("ColumnTable Already Exists");
        }catch(Exception e) {
            createTable("ColumnTable", columns);
            System.out.println("ColumnTable Created");
        }

        try{
            connection.createStatement().execute("SELECT * FROM TableTable");
            System.out.println("TableTable Already Exists");
        }catch(Exception e) {
            createTable("TableTable", TableNames);
            System.out.println("TableTable Created");
        }
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
            statement.execute(tableSQL);

            System.out.println(tableSQL);

            statement.execute("INSERT INTO TableTable (TableName) VALUES ('"+name+"'");
            return true;
        }catch(Exception e){
            System.out.println(e);
        }


        return false;
    }

    public boolean addColumnName(String c){
        String addNameSQL = "INSERT INTO ColumnTable (ColumnName) VALUES ('"+c+"')";

        try {
            Statement statement = connection.createStatement();
            statement.execute(addNameSQL);

            return true;
        }catch(Exception e){
            System.out.println(e);
        }



        return false;
    }

    public LinkedList<String> getColumnNames(){
        try{
            LinkedList<String> columnNames = new LinkedList<String>();
            ResultSet r = connection.createStatement().executeQuery("SELECT * FROM ColumnTable");
            while(r.next()){
                columnNames.add(r.getString(1));
            }
            return columnNames;
        }catch(Exception e){
            return null;
        }
    }

    public LinkedList<Table> fetchTables(){

        return null;

    }

}
