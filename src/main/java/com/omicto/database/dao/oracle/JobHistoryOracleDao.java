package com.omicto.database.dao.oracle;

import com.omicto.database.dao.JobHistoryDao;
import com.omicto.domain.JobHistory;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class JobHistoryOracleDao implements JobHistoryDao {
    private Connection con;

    public JobHistoryOracleDao(Connection con) {
        this.con = con;
    }

    @Override
    public void save(JobHistory jobHistory) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement("INSERT INTO job_history VALUES(?,?,?,?,?)");
        pstmt.setLong(1,jobHistory.employeeId);
        pstmt.setDate(2, Date.valueOf(jobHistory.startDate));
        pstmt.setDate(3, Date.valueOf(jobHistory.endDate));
        pstmt.setString(4,jobHistory.jobId);
        pstmt.setLong(5,jobHistory.departmentId);
        pstmt.execute();
        pstmt.close();
    }

    @Override
    public void saveAll(List<JobHistory> t) throws SQLException {
        for(JobHistory jh : t){
            save(jh);
        }
    }

    @Override
    public void update(JobHistory.Id id, JobHistory jobHistory) throws SQLException {

    }

    @Override
    public List<JobHistory> getAll() throws SQLException {
        PreparedStatement pstmt = con.prepareStatement("SELECT * FROM job_history ");
        ResultSet rs = pstmt.executeQuery();
        List<JobHistory> jh = new LinkedList<>();
        while (rs.next()){
            jh.add(getJobHistoryFromResultset(rs));
        }
        pstmt.close();
        return jh;
    }

    public JobHistory getJobHistoryFromResultset(ResultSet rs) throws SQLException {
        return new JobHistory(rs.getLong(1),rs.getDate(2).toLocalDate(),rs.getDate(3).toLocalDate(),rs.getString(4),rs.getLong(5));
    }

    @Override
    public JobHistory getOne(JobHistory.Id id) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement("SELECT * FROM job_history WHERE employee_id = ? AND start_date = ?");
        pstmt.setLong(1,id.employeeId);
        pstmt.setDate(2,Date.valueOf(id.startDate));
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        JobHistory jh = getJobHistoryFromResultset(rs);
        pstmt.close();
        return jh;
    }

    @Override
    public JobHistory deleteById(JobHistory.Id id) {
        return null;
    }
}
