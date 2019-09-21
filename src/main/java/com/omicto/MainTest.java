package com.omicto;

import com.omicto.database.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class MainTest {
    public static void main(String[] args) {
        try {
            Connection oracleConn = ConnectionFactory.getConnection(ConnectionFactory.ConnectionChoice.ORACLE);
            Connection mssqlConn = ConnectionFactory.getConnection(ConnectionFactory.ConnectionChoice.MSSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
