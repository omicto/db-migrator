package com.omicto.database.dao.oracle;

import com.omicto.database.dao.JobDao;
import com.omicto.domain.Job;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class JobOracleDao implements JobDao {
    private Connection con;

    public JobOracleDao(Connection con) {
        this.con = con;
    }

    @Override
    public void save(Job job) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement("INSERT INTO jobs VALUES(?,?,?,?)");
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
        PreparedStatement pstmt = con.prepareStatement("SELECT * FROM jobs");
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
        PreparedStatement pstmt = con.prepareStatement("SELECT * FROM jobs WHERE job_id = ?");
        pstmt.setString(1,id);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        //Parece redundante o largo pero al parecer si no se cierran los cursores se puede llegar a un error de maximun open cursos exceeded
        Job j = getJobFromResultset(rs);
        pstmt.close();
        return j;
    }

    @Override
    public Job deleteById(String id) {
        return null;
    }
}
