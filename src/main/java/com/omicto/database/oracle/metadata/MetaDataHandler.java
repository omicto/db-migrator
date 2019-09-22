package com.omicto.database.oracle.metadata;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class MetaDataHandler {
    private Connection connection;
    private DatabaseMetaData databaseMetaData;

    public MetaDataHandler(Connection connection) throws SQLException {
        this.connection = connection;
        this.databaseMetaData = connection.getMetaData();
    }

    public List<String> tableNames() throws SQLException {

        ResultSet tables = databaseMetaData.getTables(null, "HR", null, null);
        List<String> names = new LinkedList<>();
        while(tables.next()){
            names.add(tables.getString("TABLE_NAME"));
        }
        return names;
    }

    public List<String> schemas() throws SQLException {
        List<String> schemas = new LinkedList<>();
        ResultSet schemasRs = databaseMetaData.getSchemas();
        while(schemasRs.next()){
            schemas.add(schemasRs.getString("TABLE_SCHEM"));
        }
        return schemas;
    }

    /*public List<String> getPrimaryKeys(String schema, String table) throws SQLException {
        databaseMetaData.getPrimaryKeys(null, schema, table);
        return null;
    }

    public List<String> getForeignKeys(String schema, String table){
        return null;
    }

    public List<String> getColumns(String schema, String table){
//        databaseMetaData.getColumns(null, );
        return null;
    }*/

    public String getDdl(String tableName) throws SQLException {
        PreparedStatement ddlStatement =
                connection.prepareStatement("SELECT dbms_metadata.get_ddl('TABLE', '"+tableName+"') ddl FROM dual");
        ddlStatement.execute();
        ResultSet ddl = ddlStatement.getResultSet();
        ddl.next();
        String ddlString = ddl.getString("ddl");
        ddl.close();
        return ddlString;
    }

}
