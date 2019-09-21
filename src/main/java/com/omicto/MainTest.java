package com.omicto;

import com.omicto.database.ConnectionFactory;
import com.omicto.database.dao.oracle.RegionOracleDao;
import com.omicto.domain.Region;

import java.sql.Connection;
import java.sql.SQLException;

public class MainTest {
    public static void main(String[] args) {
        try {
            Connection oracleConn = ConnectionFactory.getConnection(ConnectionFactory.ConnectionChoice.ORACLE);
            Connection mssqlConn = ConnectionFactory.getConnection(ConnectionFactory.ConnectionChoice.MSSQL);
            //RegionOracleDao r = new RegionOracleDao(mssqlConn);
            //r.save(new Region((long) 01,"abz"));
            //System.out.println(r.getOne((long) 01).regionName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
