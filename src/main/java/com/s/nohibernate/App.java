/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.s.nohibernate;

import DAOs.EmployeeDAO;
import DAOs.EmployeeDaoImpl;
import domain.Employee;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import util.H2MemoryDatabaseExample;

/**
 *
 * @author device02
 */
public class App {
    
    static EmployeeDAO edao;
    
    public static void main(String[] args) throws Exception {
        //H2MemoryDatabaseExample.main(args);                
        
        edao = new EmployeeDaoImpl();
        
        //((EmployeeDaoImpl)edao).createTable();

        System.out.println("Adding Jan");
        Employee emp = new Employee();
        emp.setEmail("email@email.com");
        emp.setName("Jan");
        emp.setSalary(2100);
        edao.save(emp);
        
        System.out.println("All:");        
        for(Employee e : edao.findAll()){
            System.out.println(e);
            //emp=e;
        }
        System.out.println("One with id=1:");
        System.out.println(edao.findOne(1).get());
        
        System.out.println("One with name=Jan");
        Employee jan = edao.findByNAme("Jan").get();
        System.out.println(jan);
        
        System.out.println("Updating name Jan to Janka");
        jan.setName("Janka");edao.save(jan);
        System.out.println("All:");        
        for(Employee e : edao.findAll())System.out.println(e);
        
        System.out.println("Deleting:");System.out.println(jan);
        edao.delete(jan);
        System.out.println("All:");        
        for(Employee e : edao.findAll())System.out.println(e);
        
    }

}
