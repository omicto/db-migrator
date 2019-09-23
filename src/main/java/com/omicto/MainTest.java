package com.omicto;

import com.omicto.database.ConnectionFactory;
import com.omicto.database.dao.Dao;
import com.omicto.database.mssql.SimpleQueryExecutor;
import com.omicto.database.oracle.dao.*;
import com.omicto.database.oracle.metadata.DdlParser;
import com.omicto.database.oracle.metadata.MetaDataHandler;
import com.omicto.domain.*;

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

            //TABLE EMPLOYEES
            EmployeeOracleDao employeeDataOracle = new EmployeeOracleDao(oracleConn);
            EmployeeOracleDao employeeDataMSSQL = new EmployeeOracleDao(mssqlConn);
            exchangeData(employeeDataOracle, employeeDataMSSQL);

            //TABLE DEPARTMENTS
            DepartmentOracleDao departmentDataOracle = new DepartmentOracleDao(oracleConn);
            DepartmentOracleDao departmentDataMSSQL = new DepartmentOracleDao(mssqlConn);
            exchangeData(departmentDataOracle, departmentDataMSSQL);

            //TABLE COUNTRIES
            CountryOracleDao countryDataOracle = new CountryOracleDao(oracleConn);
            CountryOracleDao countryDataMSSQL = new CountryOracleDao(mssqlConn);
            exchangeData(countryDataOracle,countryDataMSSQL);

            //TABLE JOBS
            JobOracleDao jobDataOracle = new JobOracleDao(oracleConn);
            JobOracleDao jobDataMSSQL = new JobOracleDao(mssqlConn);
            exchangeData(jobDataOracle,jobDataMSSQL);

            //TABLE JOB HISTORY
            JobHistoryOracleDao jhDataOracle = new JobHistoryOracleDao(oracleConn);
            JobHistoryOracleDao jhDataMSSQL = new JobHistoryOracleDao(mssqlConn);
            exchangeData(jhDataOracle,jhDataMSSQL);

            //TABLE LOCATIONS
            LocationOracleDao locationDataOracle = new LocationOracleDao(oracleConn);
            LocationOracleDao locationDataMSSQL = new LocationOracleDao(mssqlConn);
            exchangeData(locationDataOracle,locationDataMSSQL);

            //TABLE REGIONS
            RegionOracleDao regionDataOracle = new RegionOracleDao(oracleConn);
            RegionOracleDao regionDataMSSQL = new RegionOracleDao(mssqlConn);
            exchangeData(regionDataOracle,regionDataMSSQL);

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
