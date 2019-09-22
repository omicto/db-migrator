package com.omicto;

import com.omicto.database.ConnectionFactory;
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

            /*
            *
            * TODO: Aqui va la movida de datos
            *   Ok -- Julieta
            *
            * */
            //TABLE EMPLOYEES
            EmployeeOracleDao employeeDataOracle = new EmployeeOracleDao(oracleConn);
            EmployeeOracleDao employeeDataMSSQL = new EmployeeOracleDao(mssqlConn);
            List<Employee> employeeDataList = new LinkedList<>() ;
            employeeDataList = employeeDataOracle.getAll();
            employeeDataMSSQL.saveAll(employeeDataList);

            //TABLE DEPARTMENTS
            DepartmentOracleDao departmentDataOracle = new DepartmentOracleDao(oracleConn);
            DepartmentOracleDao departmentDataMSSQL = new DepartmentOracleDao(mssqlConn);
            List<Department> departmentDataList = new LinkedList<>();
            departmentDataList = departmentDataOracle.getAll();
            departmentDataMSSQL.saveAll(departmentDataList);

            //TABLE COUNTRIES
            CountryOracleDao countryDataOracle = new CountryOracleDao(oracleConn);
            CountryOracleDao countryDataMSSQL = new CountryOracleDao(mssqlConn);
            List<Country> countryDataList = new LinkedList<>();
            countryDataList = countryDataOracle.getAll();
            countryDataMSSQL.saveAll(countryDataList);

            //TABLE JOBS
            JobOracleDao jobDataOracle = new JobOracleDao(oracleConn);
            JobOracleDao jobDataMSSQL = new JobOracleDao(mssqlConn);
            List<Job> jobDataList = new LinkedList<>();
            jobDataList = jobDataOracle.getAll();
            jobDataMSSQL.saveAll(jobDataList);

            //TABLE JOB HISTORY
            JobHistoryOracleDao jhDataOracle = new JobHistoryOracleDao(oracleConn);
            JobHistoryOracleDao jhDataMSSQL = new JobHistoryOracleDao(mssqlConn);
            List<JobHistory> jhDataList = new LinkedList<>();
            jhDataList = jhDataOracle.getAll();
            jhDataMSSQL.saveAll(jhDataList);

            //TABLE LOCATIONS
            LocationOracleDao locationDataOracle = new LocationOracleDao(oracleConn);
            LocationOracleDao locationDataMSSQL = new LocationOracleDao(mssqlConn);
            List<Location> locationDataList = new LinkedList<>();
            locationDataList = locationDataOracle.getAll();
            locationDataMSSQL.saveAll(locationDataList);

            //TABLE REGIONS
            RegionOracleDao regionDataOracle = new RegionOracleDao(oracleConn);
            RegionOracleDao regionDataMSSQL = new RegionOracleDao(mssqlConn);
            List<Region> regionDataList = new LinkedList<>();
            regionDataList = regionDataOracle.getAll();
            regionDataMSSQL.saveAll(regionDataList);

            String b = oracleMetadataHandler.getReferentialConstraints(Arrays.asList(referentialTables));
            String constraints = b.replace("ENABLE", "");
            System.out.println(b);
            execute.query(constraints);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
