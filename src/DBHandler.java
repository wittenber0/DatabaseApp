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

        startTables();

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

            try {
                statement.execute(tableSQL);
                System.out.println(name + " is created");
                if(name=="TableTable"){
                    return true;
                }
            }catch(Exception e){
                System.out.println("Failure to create "+name);
            }

            try {
                statement.execute("INSERT INTO TableTable (TableName) VALUES ('" + name + "')");
            }catch(Exception e){
                System.out.println(name + " is not added to TableTable");
            }
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

    public LinkedList<Table> setTables(){
        LinkedList<Table> allTables = new LinkedList<Table>();
        try {
            ResultSet r1 = connection.createStatement().executeQuery("SELECT * FROM TableTable");
            while (r1.next()){
                LinkedList<String> rowHeaders = new LinkedList<String>();
                LinkedList<TableEntry> rows = new LinkedList<TableEntry>();
                String name = r1.getString(1);
                ResultSet r2 = connection.createStatement().executeQuery("SELECT * FROM "+name);
                while (r2.next()){
                    LinkedList<String> row = new LinkedList<String>();
                    for (int i=1; i <= r2.getMetaData().getColumnCount(); i++){
                        row.add(r2.getString(i));
                    }
                    if(row.size()!=0){
                        rows.add(new TableEntry(row));
                    }
                }

                System.out.println("Name:        " + name);
                String headerSQL = "select sys.syscolumns.columnname, sys.systables.tablename from sys.systables join sys.syscolumns on sys.systables.tableid = sys.syscolumns.referenceid where sys.systables.tablename = '"+name.toUpperCase()+"'";
                ResultSet r3 = connection.createStatement().executeQuery(headerSQL);

                while (r3.next()){
                    rowHeaders.add(r3.getString(1));
                }

                Table t = new Table(r1.getString(1), rowHeaders, rows);
                System.out.println(t);
                System.out.println("Columns: " + t.getColumns());
                allTables.add(t);
            }
            return allTables;
        }catch(Exception e){
            System.out.println("could not load tables from db:   " + e);

        }

        return null;

    }

    public void startTables(){
        LinkedList<String> columns = new LinkedList<String>();
        columns.add("ColumnName");

        LinkedList<String> TableNames = new LinkedList<String>();
        TableNames.add("TableName");


        try{
            connection.createStatement().execute("SELECT * FROM ColumnTable");
            System.out.println("ColumnTable Already Exists");
        }catch(Exception e) {
            createTable("ColumnTable", columns);
            //System.out.println("ColumnTable Created");
        }

        try{
            connection.createStatement().execute("SELECT * FROM TableTable");
            System.out.println("TableTable Already Exists");
        }catch(Exception e) {
            createTable("TableTable", TableNames);
            //System.out.println("TableTable Created");
        }
    }

    public boolean saveDataEntry(String name, TableEntry entry){
        try{
            String insertSQL = "INSERT INTO " + name + " VALUES (";
            for(int i=0; i<entry.getValues().size(); i++){
                insertSQL+="'"+entry.getValues().get(i)+"'";
                if(i!=entry.getValues().size()-1){
                    insertSQL+=",";
                }else{
                    insertSQL+=")";
                }
            }

            System.out.println(insertSQL);
            connection.createStatement().execute(insertSQL);

        }catch(Exception e){
            System.out.println(e);
        }

        return true;
    }

}
