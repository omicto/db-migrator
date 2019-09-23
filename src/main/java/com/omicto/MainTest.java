package com.omicto;

import com.omicto.database.ConnectionFactory;
import com.omicto.database.dao.Dao;
import com.omicto.database.mssql.SimpleQueryExecutor;
import com.omicto.database.oracle.dao.*;
import com.omicto.database.oracle.metadata.DdlParser;
import com.omicto.database.oracle.metadata.MetaDataHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MainTest {
    public static void main(String[] args) {
        String targetDataBase;
        if (args.length < 1) {
            System.err.println("You must specify a name for the target database. Default will be NUEVO_HR");
            targetDataBase = "NUEVO_HR";
        }else {
            targetDataBase = args[0];
        }

        List<String> tableCreationQueries = new LinkedList<>();
        try {
            Connection oracleConn = ConnectionFactory.getConnection(ConnectionFactory.ConnectionChoice.ORACLE);
            Connection mssqlConn = ConnectionFactory.getConnection(ConnectionFactory.ConnectionChoice.MSSQL);
            SimpleQueryExecutor execute = new SimpleQueryExecutor(mssqlConn);
            execute.query("CREATE DATABASE " + targetDataBase);
            execute.query("USE " + targetDataBase);
            execute.query("CREATE SCHEMA HR");
            MetaDataHandler oracleMetadataHandler = new MetaDataHandler(oracleConn);

            String[] tableNames = {"REGIONS", "COUNTRIES", "LOCATIONS", "DEPARTMENTS", "JOBS", "EMPLOYEES", "JOB_HISTORY"};
            String[] referentialTables = {"COUNTRIES", "LOCATIONS", "DEPARTMENTS", "JOBS", "EMPLOYEES", "JOB_HISTORY"};
            String s = oracleMetadataHandler.getDdl(Arrays.asList(tableNames));
            DdlParser parser = new DdlParser(s);
            System.out.println(parser.getParsedDdl());
            execute.query(parser.getParsedDdl());

            //TABLE EMPLOYEES
            EmployeeDaoImpl employeeDataOracle = new EmployeeDaoImpl(oracleConn);
            EmployeeDaoImpl employeeDataMSSQL = new EmployeeDaoImpl(mssqlConn);
            exchangeData(employeeDataOracle, employeeDataMSSQL);

            //TABLE DEPARTMENTS
            DepartmentDaoImpl departmentDataOracle = new DepartmentDaoImpl(oracleConn);
            DepartmentDaoImpl departmentDataMSSQL = new DepartmentDaoImpl(mssqlConn);
            exchangeData(departmentDataOracle, departmentDataMSSQL);

            //TABLE COUNTRIES
            CountryDaoImpl countryDataOracle = new CountryDaoImpl(oracleConn);
            CountryDaoImpl countryDataMSSQL = new CountryDaoImpl(mssqlConn);
            exchangeData(countryDataOracle, countryDataMSSQL);

            //TABLE JOBS
            JobDaoImpl jobDataOracle = new JobDaoImpl(oracleConn);
            JobDaoImpl jobDataMSSQL = new JobDaoImpl(mssqlConn);
            exchangeData(jobDataOracle, jobDataMSSQL);

            //TABLE JOB HISTORY
            JobHistoryDaoImpl jhDataOracle = new JobHistoryDaoImpl(oracleConn);
            JobHistoryDaoImpl jhDataMSSQL = new JobHistoryDaoImpl(mssqlConn);
            exchangeData(jhDataOracle, jhDataMSSQL);

            //TABLE LOCATIONS
            LocationDaoImpl locationDataOracle = new LocationDaoImpl(oracleConn);
            LocationDaoImpl locationDataMSSQL = new LocationDaoImpl(mssqlConn);
            exchangeData(locationDataOracle, locationDataMSSQL);

            //TABLE REGIONS
            RegionDaoImpl regionDataOracle = new RegionDaoImpl(oracleConn);
            RegionDaoImpl regionDataMSSQL = new RegionDaoImpl(mssqlConn);
            exchangeData(regionDataOracle, regionDataMSSQL);

            String referentialConstraints = oracleMetadataHandler.getReferentialConstraints(Arrays.asList(referentialTables));
            referentialConstraints = referentialConstraints.replace("ENABLE", "");
            System.out.println(referentialConstraints);
            execute.query(referentialConstraints);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void exchangeData(Dao source, Dao target) throws SQLException {
        target.saveAll(source.getAll());
    }
}
