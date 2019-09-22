package com.omicto.database.mssql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SimpleQueryExecutor {
    private final Connection connection;

    public SimpleQueryExecutor(Connection connection){
        this.connection = connection;
    }

    public void query(String query) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(query);
        ps.execute();
        ps.close();
    }

}
