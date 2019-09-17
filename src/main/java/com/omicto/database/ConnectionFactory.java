package com.omicto.database;

import com.omicto.support.AppProperties;

import java.sql.*;

public class ConnectionFactory {
    private static final String ORACLE_DRIVER = "oracle.jdbc.OracleDriver";
    private static final String MSSQL_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static Driver driver;

    public enum ConnectionChoice {
        MSSQL,
        ORACLE
    }

    private static void loadDriver(String driverName) {
        try {
            Class jdbcDriverClass = Class.forName(driverName);
            driver = (Driver) jdbcDriverClass.newInstance();
            DriverManager.registerDriver(driver);
        } catch (Exception e) {
            System.err.println("Failed to load " + driverName);
            e.printStackTrace();
        }
    }

    private ConnectionFactory() {
    }

    public static synchronized Connection getConnection(ConnectionChoice choice) throws SQLException {
        if (driver == null) {
            try {
                switch (choice) {
                    case MSSQL:
                        loadDriver(MSSQL_DRIVER);
                        break;
                    case ORACLE:
                    default:
                        loadDriver(ORACLE_DRIVER);
                }
            } catch (Exception e) {
                System.err.println("JDBC driver load failed");
                e.printStackTrace();
            }
        }
        String url = AppProperties.getPropertyValue(choice.name() + "_URL");
        String user = AppProperties.getPropertyValue(choice.name() + "_USER");
        String password = AppProperties.getPropertyValue(choice.name() + "_PASSWORD");
        return DriverManager.getConnection(url, user, password);
    }

    public static void close(ResultSet rs) {
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(PreparedStatement stmt) {
        try {
            if (stmt != null)
                stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(Connection c) {
        try {
            if (c != null)
                c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

