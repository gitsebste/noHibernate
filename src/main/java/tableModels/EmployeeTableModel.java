/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tableModels;

import domain.Employee;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author device02
 */
public class EmployeeTableModel extends AbstractTableModel{
    
    private static final int ID_COL = 0;//{Id,Name,Email,Salary}
    private static final int NAME_COL = 1;
    private static final int EMAIL_COL = 2;
    private static final int SALARY_COL = 3;
    
    private String[] columnNames = {"Id","Name","Email","Salary"};
    
    private List<Employee> employees;
    
    public EmployeeTableModel(List<Employee> employees){
        this.employees=employees;
    }
    
    @Override
    public int getRowCount() {
        return employees.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Class getColumnClass(int col) {
        return getValueAt(0,col).getClass();
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Employee tmp = employees.get(rowIndex);
        switch(columnIndex){
            case NAME_COL:
                return tmp.getName();
            case ID_COL:
                return tmp.getId();
            case EMAIL_COL:
                return tmp.getEmail();
            case SALARY_COL:
                return tmp.getSalary();
            default:
                return "";
        }        
    }
    public Object getValueAt(int rowIndex) {
        return employees.get(rowIndex);        
        }            
}
