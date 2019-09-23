package com.omicto.database.oracle.dao;

import com.omicto.database.dao.DepartmentDao;
import com.omicto.domain.Department;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DepartmentDaoImpl implements DepartmentDao {
    private Connection con;

    public DepartmentDaoImpl(Connection con) {
        this.con = con;
    }

    @Override
    public void save(Department department) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement("INSERT INTO hr.departments VALUES(?,?,?,?)");
        pstmt.setLong(1, department.departmentId);
        pstmt.setString(2, department.departmentName);
        pstmt.setLong(3, department.managerId);
        pstmt.setLong(4, department.locationId);
        pstmt.execute();
        pstmt.close();
    }

    @Override
    public void saveAll(List<Department> t) throws SQLException {
        for (Department dept : t) {
            save(dept);
        }
    }

    @Override
    public void update(Long id, Department department) throws SQLException {

    }

    @Override
    public List<Department> getAll() throws SQLException {
        PreparedStatement pstmt = con.prepareStatement("SELECT * FROM hr.departments");
        ResultSet rs = pstmt.executeQuery();
        List<Department> deps = new LinkedList<>();
        while(rs.next()) {
            deps.add(getDepartmentFromResultset(rs));
        }
        pstmt.close();
        return deps;
    }
    public Department getDepartmentFromResultset(ResultSet rs) throws SQLException {
        return new Department(rs.getLong(1),rs.getString(2),rs.getLong(3),rs.getLong(4));
    }
    @Override
    public Department getOne(Long id) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement("SELECT * FROM hr.departments WHERE department_id = ?");
        pstmt.setLong(1,id);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        Department d = getDepartmentFromResultset(rs);
        pstmt.close();
        return d;
    }

    @Override
    public Department deleteById(Long id) {
        return null;
    }
}
