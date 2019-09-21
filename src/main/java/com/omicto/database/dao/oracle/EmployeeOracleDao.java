package com.omicto.database.dao.oracle;

import com.omicto.database.dao.EmployeeDao;
import com.omicto.domain.Employee;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;


public class EmployeeOracleDao implements EmployeeDao {
    private Connection con;

    public EmployeeOracleDao(Connection con) {
        this.con = con;
    }

    @Override
    public void save(Employee employee) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement("INSERT INTO employees VALUES(?,?,?,?,?,?,?,?,?,?,?)");
        pstmt.setLong(1, employee.employeeId);
        pstmt.setString(2, employee.firstName);
        pstmt.setString(3, employee.lastName);
        pstmt.setString(4, employee.email);
        pstmt.setString(5, employee.phoneNumber);
        pstmt.setString(6, employee.jobId);
        pstmt.setLong(7, employee.managerId);
        pstmt.setDate(8, Date.valueOf(employee.hireDate));
        pstmt.setDouble(9, employee.salary);
        pstmt.setDouble(10, employee.commission);
        pstmt.setLong(11, employee.managerId);
        pstmt.execute();
        pstmt.close();
    }

    @Override
    public void saveAll(List<Employee> t) throws SQLException {
        for (Employee employee : t) {
            save(employee);
        }
    }

    @Override
    public void update(Long id, Employee employee) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement("UPDATE EMPLOYEES SET first_name = ?, last_name = ?, " +
                "email = ?, phone_number = ?, hide_date = ?, job_id = ?, salary = ?, commission_pct = ?, manager_id = ?," +
                " department_id = ? WHERE employee_id = ? ");
        pstmt.setString(1, employee.firstName);
        pstmt.setString(2, employee.lastName);
        pstmt.setString(3, employee.email);
        pstmt.setString(4, employee.phoneNumber);
        pstmt.setString(5, employee.jobId);
        pstmt.setLong(6, employee.managerId);
        pstmt.setDate(7, Date.valueOf(employee.hireDate));
        pstmt.setDouble(8, employee.salary);
        pstmt.setDouble(9, employee.commission);
        pstmt.setLong(10, employee.managerId);
        pstmt.setLong(11,employee.employeeId);
        pstmt.execute();
        pstmt.close();
    }
    private Employee getEmployeeFromResultset(ResultSet rs) throws SQLException {
        return new Employee(rs.getLong(1),rs.getString(2),rs.getString(3),
                rs.getString(4),rs.getString(5),rs.getString(6),
                rs.getLong(7),rs.getDate(8).toLocalDate(),rs.getDouble(9),rs.getDouble(10),rs.getLong(11));
    }
    @Override
    public List<Employee> getAll() throws SQLException {
        PreparedStatement pstmt = con.prepareStatement("SELECT * FROM employees");
        ResultSet rs = pstmt.executeQuery();
        List<Employee> emps = new LinkedList<>();
        while(rs.next()){
            Employee e = getEmployeeFromResultset(rs);
            emps.add(e);
        }
        pstmt.close();
        return emps;
    }

    @Override
    public Employee getOne(Long id) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement("SELECT * FROM employees WHERE employee_id = ?");
        pstmt.setLong(1,id);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        return getEmployeeFromResultset(rs);
    }

    /**
     * By the moment we don't need this
     * @param id the id
     * @return null
     */
    @Override
    public Employee deleteById(Long id) {
        return null;
    }
}
