package com.omicto;

import com.omicto.database.ConnectionFactory;
import com.omicto.database.mssql.SimpleQueryExecutor;
import com.omicto.database.oracle.metadata.DdlParser;
import com.omicto.database.oracle.metadata.MetaDataHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MainTest {
    public static void main(String[] args) {
        List<String> tableCreationQueries = new LinkedList<>();
        try {
            Connection oracleConn = ConnectionFactory.getConnection(ConnectionFactory.ConnectionChoice.ORACLE);
            Connection mssqlConn = ConnectionFactory.getConnection(ConnectionFactory.ConnectionChoice.MSSQL);
            SimpleQueryExecutor execute = new SimpleQueryExecutor(mssqlConn);
            execute.query("CREATE DATABASE NUEVO");
            execute.query("USE NUEVO");
            execute.query("CREATE SCHEMA HR");
            MetaDataHandler oracleMetadataHandler = new MetaDataHandler(oracleConn);

            String[] tableNames = {"REGIONS", "COUNTRIES", "LOCATIONS", "DEPARTMENTS", "JOBS", "EMPLOYEES", "JOB_HISTORY"};
            String[] referentialTables = {"COUNTRIES", "LOCATIONS", "DEPARTMENTS", "JOBS", "EMPLOYEES", "JOB_HISTORY"};
            String s = oracleMetadataHandler.getDdl(Arrays.asList(tableNames));
            DdlParser parser = new DdlParser(s);
            System.out.println(parser.getParsedDdl());
            execute.query(parser.getParsedDdl());
            String b = oracleMetadataHandler.getReferentialConstraints(Arrays.asList(referentialTables));
            System.out.println(b);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
