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

    public boolean createTable(String name, LinkedList<String> columnList, boolean tt){
        try {
            Statement statement = connection.createStatement();
            String tableSQL = "CREATE TABLE "+ name + " (\n";
            String columnType = "varchar(10000)";
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
                if(name=="TABLETABLE"){
                    return true;
                }
            }catch(Exception e){
                System.out.println("Failure to create "+name);
            }

            if(tt) {
                try {
                    PreparedStatement pst = connection.prepareStatement("INSERT INTO TABLETABLE (TABLENAME) VALUES (?)");
                    pst.setString(1, name);
                    pst.execute();
                } catch (Exception e) {
                    System.out.println(name + " is not added to TABLETABLE");
                }

                MyView v = new MyView(new Table(name, columnList));
                v.saveMyView();
            }
            return true;
        }catch(Exception e){
            System.out.println(e);
        }


        return false;
    }

    public boolean addColumnName(String c){
        String addNameSQL = "INSERT INTO COLUMNTABLE (COLUMNNAME) VALUES (?)";

        try {
            PreparedStatement pst = connection.prepareStatement(addNameSQL);
            pst.setString(1,c);
            pst.execute();

            return true;
        }catch(Exception e){
            System.out.println(e);
        }



        return false;
    }

    public LinkedList<String> getColumnNames(){
        try{
            LinkedList<String> columnNames = new LinkedList<String>();
            ResultSet r = connection.createStatement().executeQuery("SELECT * FROM COLUMNTABLE");
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
            ResultSet r1 = connection.createStatement().executeQuery("SELECT * FROM TABLETABLE");
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

                //System.out.println("Name:        " + name);
                String headerSQL = "select sys.syscolumns.columnname, sys.systables.tablename from sys.systables join sys.syscolumns on sys.systables.tableid = sys.syscolumns.referenceid where sys.systables.tablename = '"+name.toUpperCase()+"'";
                ResultSet r3 = connection.createStatement().executeQuery(headerSQL);

                while (r3.next()){
                    rowHeaders.add(r3.getString(1));
                }

                Table t = new Table(r1.getString(1), rowHeaders, rows);
                //System.out.println(t);
                //System.out.println("Columns: " + t.getColumns());
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
        columns.add("COLUMNNAME");

        LinkedList<String> TableNames = new LinkedList<String>();
        TableNames.add("TABLENAME");

        LinkedList<String> myViewColumns = new LinkedList<String>();
        myViewColumns.add("MYVIEWNAME");
        myViewColumns.add("MYVIEWCOLUMNS");
        myViewColumns.add("MYVIEWTABLES");
        myViewColumns.add("MYVIEWKEY");


        try{
            connection.createStatement().execute("SELECT * FROM COLUMNTABLE");
            System.out.println("COLUMNTABLE Already Exists");
        }catch(Exception e) {
            createTable("COLUMNTABLE", columns, false);
            //System.out.println("ColumnTable Created");
        }

        try{
            connection.createStatement().execute("SELECT * FROM MYVIEWTABLE");
            System.out.println("MYVIEWTABLE Already Exists");
        }catch(Exception e) {
            createTable("MYVIEWTABLE", myViewColumns, false);
            //System.out.println("ColumnTable Created");
        }

        try{
            connection.createStatement().execute("SELECT * FROM TABLETABLE");
            System.out.println("TABLETABLE Already Exists");
        }catch(Exception e) {
            createTable("TABLETABLE", TableNames, false);
            //System.out.println("TableTable Created");
        }
    }

    public boolean saveDataEntry(String name, TableEntry entry){
        try{
            String insertSQL = "INSERT INTO " + name + " VALUES (";
            for(int i=0; i<entry.getValues().size(); i++){
                insertSQL+=" ?";
                if(i!=entry.getValues().size()-1){
                    insertSQL+=",";
                }else{
                    insertSQL+=")";
                }
            }



            System.out.println("------Saving Data Entry------");
            System.out.println(insertSQL);
            PreparedStatement pst = connection.prepareStatement(insertSQL);
            for(int j=0; j< entry.getValues().size(); j++){
                pst.setString(j+1, entry.getValues().get(j));
            }
            pst.execute();

        }catch(Exception e){
            System.out.println(e);
            return false;
        }

        return true;
    }

    public boolean saveMyView(MyView v){
        String columnS = "";
        String tableS = "";
        for(int i=0; i<v.columns.size(); i++){
            columnS += v.columns.get(i);
            if(i != v.columns.size() -1){
                columnS += ",";
            }
        }

        for(int i=0; i<v.tables.size(); i++){
            tableS += v.tables.get(i);
            if(i != v.tables.size() -1){
                tableS += ",";
            }
        }

        String addViewSQL = "INSERT INTO MYVIEWTABLE (MYVIEWNAME, MYVIEWCOLUMNS, MYVIEWTABLES, MYVIEWKEY) VALUES ('"+ v.name + "', '" + columnS + "', '" + tableS + "', '" + v.keyColumn + "')";


        System.out.println("------Saving MyView------");
        System.out.println(addViewSQL);
        //createTable(v.name, v.columns);

        try {
            connection.createStatement().execute(addViewSQL);
            System.out.println("Success");
            //LinkedList<TableEntry> myData = saveMyViewData(v);
            return true;
        }catch(Exception e){
            System.out.println("Failed to save View:   " + e);
            return false;
        }


    }

    public LinkedList<TableEntry> saveMyViewData(MyView v){
        LinkedList<TableEntry> myViewData = new LinkedList<TableEntry>();
        System.out.println(v.columns);


        String joinSQL = "SELECT ";

        for(int k=0; k<v.columns.size(); k++){
            for(int m=0; m<v.tables.size(); m++){
                if(v.tables.get(m).getColumns().contains(v.columns.get(k))){
                    joinSQL+=v.tables.get(m).toString() + "." + v.columns.get(k);
                    break;
                }

            }
            if(k != v.columns.size() -1){
                joinSQL+= ", ";
            }
        }


        joinSQL += " FROM ";

        for (int i=0; i<v.tables.size(); i++){
            joinSQL += v.tables.get(i);
            if(i!=v.tables.size()-1){
                joinSQL += " join ";
            }
        }

        if(v.tables.size() > 1) {
            joinSQL += " on ";

            for (int j = 0; j < v.tables.size(); j++) {
                joinSQL += v.tables.get(j) + "." + v.keyColumn;
                if (j != v.tables.size() - 1) {
                    joinSQL += " = ";
                }
            }
        }

        System.out.println("------Joining MyView Components------");
        System.out.println(joinSQL);

        try {
            ResultSet r = connection.createStatement().executeQuery(joinSQL);

            while (r.next()){
                LinkedList<String> entryStrings = new LinkedList<String>();
                for(int l=1; l<=r.getMetaData().getColumnCount(); l++){
                    entryStrings.add(r.getString(l));
                }
                myViewData.add(new TableEntry(entryStrings));
            }
        }catch(Exception e){
            System.out.println("Problem getting myViewData: " + e);
            return null;

        }

        return myViewData;
    }

    public LinkedList<MyView> setMyViews(LinkedList<Table> allTables){
        LinkedList<MyView> allMyViews = new LinkedList<MyView>();
        try {
            ResultSet r1 = connection.createStatement().executeQuery("SELECT * FROM MYVIEWTABLE");
            while (r1.next()){
                String name = r1.getString(1);

                LinkedList<Table> myViewTables = new LinkedList<Table>();
                String[] tString = r1.getString(3).split(",");
                for (String s : tString){
                    for(Table t: allTables){
                        if(t.getName().equals(s)){
                            myViewTables.add(t);
                            break;
                        }
                    }
                }

                LinkedList<String> myViewColumns = new LinkedList<String>();
                String[] cString = r1.getString(2).split(",");
                for (String c : cString){
                    myViewColumns.add(c);
                }

                String key = r1.getString(4);

                MyView v = new MyView(name, myViewTables, myViewColumns, key);
                //System.out.println(t);
                //System.out.println("Columns: " + t.getColumns());
                allMyViews.add(v);
            }
            return allMyViews;
        }catch(Exception e){
            System.out.println("could not load tables from db:   " + e);

        }

        return null;

    }

}
