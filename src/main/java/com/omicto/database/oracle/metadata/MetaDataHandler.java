package com.omicto.database.oracle.metadata;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class MetaDataHandler {
    private Connection connection;
    private DatabaseMetaData databaseMetaData;

    public MetaDataHandler(Connection connection) throws SQLException {
        this.connection = connection;

    }

    private String retrieveDdl(Iterable<String> tables) throws SQLException {
        Statement s = connection.createStatement();
        s.executeUpdate("BEGIN\n" +
                "DBMS_METADATA.SET_TRANSFORM_PARAM(DBMS_METADATA.SESSION_TRANSFORM,'STORAGE',false);\n" +
                "DBMS_METADATA.SET_TRANSFORM_PARAM(DBMS_METADATA.session_transform,'SQLTERMINATOR', true);\n" +
                "DBMS_METADATA.SET_TRANSFORM_PARAM(DBMS_METADATA.session_transform,'SEGMENT_ATTRIBUTES', false);\n" +
                "DBMS_METADATA.SET_TRANSFORM_PARAM(DBMS_METADATA.SESSION_TRANSFORM, 'CONSTRAINTS',true);\n" +
                "DBMS_METADATA.SET_TRANSFORM_PARAM(DBMS_METADATA.session_transform, 'CONSTRAINTS_AS_ALTER', true);\n" +
                "DBMS_METADATA.SET_TRANSFORM_PARAM(DBMS_METADATA.session_transform, 'REF_CONSTRAINTS', false);\n" +
                "END;");
        String tableString = commaSeparatedTables(tables);
        System.out.println(tableString);
        System.out.println(tableString);
        ResultSet ddl = s.executeQuery("SELECT dbms_metadata.get_ddl('TABLE', u.table_name)" +
                "  FROM user_tables u WHERE u.table_name in (" + tableString + ")");
        String ddlString = "";
        while (ddl.next()) {
            ddlString += ddl.getString(1) + "\n";
        }
        return ddlString;
    }

    private String commaSeparatedTables(Iterable<String> tables) {
        String tableString = "";
        for (String tableName : tables) {
            tableString += "'" + tableName + "', ";
        }
        tableString = tableString.substring(0, tableString.length() - 2);
        return tableString;
    }

    public String getDdl(Iterable<String> tableNames) throws SQLException {
        return retrieveDdl(tableNames);
    }

    public String getReferentialConstraints(Iterable<String> tableNames) throws SQLException {
        return retrievedConstraints(tableNames);
    }

    private String retrievedConstraints(Iterable<String> tableNames) throws SQLException {
        Statement s = connection.createStatement();
        String tableString = commaSeparatedTables(tableNames);
        ResultSet constraints = s.executeQuery(
                "SELECT dbms_metadata.get_dependent_ddl('REF_CONSTRAINT', t.table_name) " +
                " FROM user_tables t " +
                " WHERE t.table_name IN ("+commaSeparatedTables(tableNames)+") " +
                        "AND EXISTS (SELECT 1 FROM user_constraints WHERE table_name = t.table_name AND constraint_type = 'R')");
        String constraintString = "";
        while (constraints.next()) {
            constraintString += constraints.getString(1) + "\n";
        }
        return constraintString;
    }

}
