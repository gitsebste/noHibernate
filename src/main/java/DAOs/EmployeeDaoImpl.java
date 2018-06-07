/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import util.PropertyReader;
import domain.Employee;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author device02
 */
public class EmployeeDaoImpl implements EmployeeDAO{
    
    Connection connection;
    
    public void addSomeData(){
        Employee emp = new Employee();
        emp.setEmail("emma@email.com");
        emp.setName("Emma");
        emp.setSalary(2100);
        save(emp);
        
        emp = new Employee();
        emp.setEmail("nathan@email.com");
        emp.setName("Nathan");
        emp.setSalary(2100);
        save(emp);
        
        emp = new Employee();
        emp.setEmail("levi@email.com");
        emp.setName("Levi");
        emp.setSalary(2100);
        save(emp);
        
                emp = new Employee();
        emp.setEmail("chloe@email.com");
        emp.setName("Chloe");
        emp.setSalary(2100);
        save(emp);
        
                emp = new Employee();
        emp.setEmail("rose@email.com");
        emp.setName("Rose");
        emp.setSalary(2100);
        save(emp);
        
                emp = new Employee();
        emp.setEmail("sophia@email.com");
        emp.setName("Sophia");
        emp.setSalary(2100);
        save(emp);
        
                emp = new Employee();
        emp.setEmail("adeline@email.com");
        emp.setName("Adeline");
        emp.setSalary(2100);
        save(emp);
        
                emp = new Employee();
        emp.setEmail("evelyn@email.com");
        emp.setName("Evelyn");
        emp.setSalary(2100);
        save(emp);
    }
    
    public EmployeeDaoImpl() throws FileNotFoundException, IOException, SQLException{
        connection = getDBConnection();
        createTable();
    }
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

    @Override
    public Optional<Employee> findOne(Integer id) {
        List<Employee> tmp = exeGetQuery("select * from EMPLOYEE where id ="+id);
        Employee cont = null;
        if(tmp.size()==1)cont = tmp.get(0);
        //Optional<Employee> ret = new Optional<>(cont);
         return Optional.ofNullable(cont);
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> ret = exeGetQuery("select * from EMPLOYEE");
        return ret;
    }

    @Override
    public Optional<Employee> findByNAme(String name) {
                List<Employee> tmp = exeGetQuery("select * from EMPLOYEE where name ='"+name+"'");
        Employee cont = null;
        if(tmp.size()==1)cont = tmp.get(0);
        //Optional<Employee> ret = new Optional<>(cont);
         return Optional.ofNullable(cont);
    }

    @Override
    public void delete(Employee employee) {
        PreparedStatement preparedStatement = null;
        String SelectQuery = "delete EMPLOYEE where id = ?";//"select * from EMPLOYEE"; 
        try { 
            preparedStatement = connection.prepareStatement(SelectQuery);
            preparedStatement.setLong(1, employee.getId());
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void save(Employee employee) {
        if(employee.getId()==null){
            insert(employee);
        }else
        {
            update(employee);
        }
    }

    private List<Employee> exeGetQuery(String query) {
        List<Employee> ret = new ArrayList<>();
                PreparedStatement selectPreparedStatement = null;

        String SelectQuery = query;//"select * from EMPLOYEE"; 
        try {
            selectPreparedStatement = connection.prepareStatement(SelectQuery);
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ResultSet rs = selectPreparedStatement.executeQuery();
                        while (rs.next()) {
                Employee tmp = new Employee();
                tmp.setEmail(rs.getString("email"));
                tmp.setId(rs.getLong("id"));
                tmp.setName(rs.getString("name"));
                tmp.setSalary(rs.getDouble("salary"));
                ret.add(tmp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    private void insert(Employee e) {
        try {
            PreparedStatement insertPreparedStatement = null;
            
            
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
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void update(Employee e) {
                    try {
                PreparedStatement preparedStatement = null;
                
                ////Employee id,name,email,salary
                String query = "UPDATE EMPLOYEE SET name = ?, email = ?, salary= ? WHERE id = ?";
                //String SelectQuery = "select * from PERSON";
                
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, e.getName());
                preparedStatement.setString(2, e.getEmail());
                preparedStatement.setDouble(3, e.getSalary());
                preparedStatement.setLong(4, e.getId());
                preparedStatement.executeUpdate();
                preparedStatement.close();
                connection.commit();
            } catch (SQLException ex) {
                Logger.getLogger(EmployeeDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
           private  void createTable() throws IOException, SQLException {
        connection = getDBConnection();
        PreparedStatement createPreparedStatement = null;        
//        String CreateQuery = "CREATE TABLE PERSON(id int primary key, name varchar(255))";
String CreateQuery = "CREATE TABLE EMPLOYEE(id bigint auto_increment primary key, name varchar(255), email varchar(255), salary decimal(20, 2))";
                    connection.setAutoCommit(false);

            createPreparedStatement = connection.prepareStatement(CreateQuery);
            createPreparedStatement.executeUpdate();
            createPreparedStatement.close();
            
    } 
}
