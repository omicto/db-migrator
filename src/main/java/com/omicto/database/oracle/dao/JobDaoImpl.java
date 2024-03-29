package com.omicto.database.oracle.dao;

import com.omicto.database.dao.JobDao;
import com.omicto.domain.Job;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class JobDaoImpl implements JobDao {
    private Connection con;

    public JobDaoImpl(Connection con) {
        this.con = con;
    }

    @Override
    public void save(Job job) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement("INSERT INTO hr.jobs VALUES(?,?,?,?)");
        pstmt.setString(1, job.jobId);
        pstmt.setString(2, job.jobTitle);
        pstmt.setLong(3, job.minSalary);
        pstmt.setLong(4,job.maxSalary);
        pstmt.execute();
        pstmt.close();
    }

    @Override
    public void saveAll(List<Job> t) throws SQLException {
        for(Job job : t){
            save(job);
        }
    }

    @Override
    public void update(String id, Job job) throws SQLException {

    }

    @Override
    public List<Job> getAll() throws SQLException {
        PreparedStatement pstmt = con.prepareStatement("SELECT * FROM hr.jobs");
        ResultSet rs = pstmt.executeQuery();
        List<Job> jobs = new LinkedList<>();
        while(rs.next()){
            jobs.add(getJobFromResultset(rs));
        }
        pstmt.close();
        return jobs;
    }

    public Job getJobFromResultset(ResultSet rs) throws SQLException {
        return new Job(rs.getString(1),rs.getString(2),rs.getLong(3),rs.getLong(4));
    }

    @Override
    public Job getOne(String id) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement("SELECT * FROM hr.jobs WHERE job_id = ?");
        pstmt.setString(1,id);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        Job j = getJobFromResultset(rs);
        pstmt.close();
        return j;
    }

    @Override
    public Job deleteById(String id) {
        return null;
    }
}
