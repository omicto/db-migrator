package com.omicto;

import com.omicto.database.ConnectionFactory;
import com.omicto.database.oracle.metadata.DdlParser;
import com.omicto.database.oracle.metadata.MetaDataHandler;

import java.sql.Connection;
import java.sql.SQLException;

public class MainTest {
    public static void main(String[] args) {
        try {
            Connection oracleConn = ConnectionFactory.getConnection(ConnectionFactory.ConnectionChoice.ORACLE);
            Connection mssqlConn = ConnectionFactory.getConnection(ConnectionFactory.ConnectionChoice.MSSQL);
            MetaDataHandler oracleMetadataHandler = new MetaDataHandler(oracleConn);
            oracleMetadataHandler.tableNames().forEach(table -> {
                try {
                    System.out.println(new DdlParser(oracleMetadataHandler.getDdl(table)).getParsed());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
//            oracleMetadataHandler.schemas().forEach(System.out::println);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
