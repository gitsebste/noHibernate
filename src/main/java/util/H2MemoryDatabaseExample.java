package util;

import domain.Employee;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.h2.Driver;
import util.PropertyReader;

// H2 In-Memory Database Example shows about storing the database contents into memory.

public class H2MemoryDatabaseExample {
    
    static Connection connection;

    //private static final String DB_DRIVER = "org.h2.Driver";
    //private static final String DB_CONNECTION = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    //private static final String DB_USER = "";
    //private static final String DB_PASSWORD = "";

    public static void main(String[] args) throws Exception {
        try {
            createTable();
            Employee e = new Employee();
            e.setEmail("email@email.com");e.setId((long)1);e.setName("Jan");e.setSalary(2100);
            insertEmployee(e);
            insertWithPreparedStatement();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertWithPreparedStatement() throws SQLException, IOException {
        
        PreparedStatement insertPreparedStatement = null;
        PreparedStatement selectPreparedStatement = null;
        
        //String InsertQuery = "INSERT INTO PERSON" + "(id, name) values" + "(?,?)";
        String SelectQuery = "select * from EMPLOYEE";

        try {
//            insertPreparedStatement = connection.prepareStatement(InsertQuery);
//            insertPreparedStatement.setInt(1, 1);
//            insertPreparedStatement.setString(2, "Jose");
//            insertPreparedStatement.executeUpdate();
//            insertPreparedStatement.close();

            selectPreparedStatement = connection.prepareStatement(SelectQuery);
            ResultSet rs = selectPreparedStatement.executeQuery();
            System.out.println("H2 In-Memory Database inserted through PreparedStatement");
            while (rs.next()) {
                Employee tmp = new Employee();
                tmp.setEmail(rs.getString("email"));
                tmp.setId(rs.getLong("id"));
                tmp.setName(rs.getString("name"));
                tmp.setSalary(rs.getDouble("salary"));
                System.out.println(tmp);
            }
            selectPreparedStatement.close();

//            connection.commit();
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

//    private static void insertWithStatement() throws SQLException, IOException {
//        Connection connection = getDBConnection();
//        Statement stmt = null;
//        try {
//            connection.setAutoCommit(false);
//            stmt = connection.createStatement();
//            stmt.execute("CREATE TABLE PERSON(id int primary key, name varchar(255))");
//            stmt.execute("INSERT INTO PERSON(id, name) VALUES(1, 'Anju')");
//            stmt.execute("INSERT INTO PERSON(id, name) VALUES(2, 'Sonia')");
//            stmt.execute("INSERT INTO PERSON(id, name) VALUES(3, 'Asha')");
//
//            ResultSet rs = stmt.executeQuery("select * from PERSON");
//            System.out.println("H2 In-Memory Database inserted through Statement");
//            while (rs.next()) {
//                System.out.println("Id " + rs.getInt("id") + " Name " + rs.getString("name"));
//            }
//
//            stmt.execute("DROP TABLE PERSON");
//            stmt.close();
//            connection.commit();
//        } catch (SQLException e) {
//            System.out.println("Exception Message " + e.getLocalizedMessage());
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            connection.close();
//        }
//    }

    private static Connection getDBConnection() throws IOException {
        Connection dbConnection = null;
        PropertyReader pr = new PropertyReader("config.properties");
        try {
            Class.forName(pr.getProperty("driver"));
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            dbConnection = DriverManager.getConnection(
                    pr.getProperty("conn"), 
                    pr.getProperty("user"), 
                    pr.getProperty("pass"));
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }

    private static void createTable() throws IOException, SQLException {
        connection = getDBConnection();
        PreparedStatement createPreparedStatement = null;        
//        String CreateQuery = "CREATE TABLE PERSON(id int primary key, name varchar(255))";
String CreateQuery = "CREATE TABLE EMPLOYEE(id bigint auto_increment primary key, name varchar(255), email varchar(255), salary decimal(20, 2))";
                    connection.setAutoCommit(false);

            createPreparedStatement = connection.prepareStatement(CreateQuery);
            createPreparedStatement.executeUpdate();
            createPreparedStatement.close();
            
    }

    private static void insertEmployee(Employee e) throws SQLException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
                PreparedStatement insertPreparedStatement = null;
        PreparedStatement selectPreparedStatement = null;
        
        
        ////Employee id,name,email,salary
        String InsertQuery = "INSERT INTO EMPLOYEE" + "(name,email,salary) values" + "(?,?,?)";
        //String SelectQuery = "select * from PERSON";

            insertPreparedStatement = connection.prepareStatement(InsertQuery);
            insertPreparedStatement.setString(1, e.getName());            
            insertPreparedStatement.setString(2, e.getEmail());
            insertPreparedStatement.setDouble(3, e.getSalary());
            insertPreparedStatement.executeUpdate();
            insertPreparedStatement.close();
            connection.commit();
    }
}