package com.omicto.database.dao.oracle;

import com.omicto.database.dao.CountryDao;
import com.omicto.domain.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class CountryOracleDao implements CountryDao {
    private Connection con;

    public CountryOracleDao(Connection con) {
        this.con = con;
    }

    @Override
    public void save(Country country) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement("INSERT INTO countries VALUES(?,?,?)");
        pstmt.setString(1, country.countryId);
        pstmt.setString(2, country.countryName);
        pstmt.setLong(3, country.regionId);
        pstmt.execute();
        pstmt.close();
    }

    @Override
    public void saveAll(List<Country> t) throws SQLException {
        for (Country country : t) {
            save(country);
        }
    }

    @Override
    public void update(Long id, Country country) throws SQLException {

    }

    @Override
    public List<Country> getAll() throws SQLException {
        PreparedStatement pstmt = con.prepareStatement("SELECT * FROM countries");
        ResultSet rs = pstmt.executeQuery();
        List<Country> countries = new LinkedList<>();
        while(rs.next()) {
            countries.add(getCountryFromResultset(rs));
        }
        pstmt.close();
        return countries;
    }

    public Country getCountryFromResultset(ResultSet rs) throws SQLException {
        return new Country(rs.getString(1),rs.getString(2),rs.getLong(3));
    }

    @Override
    public Country getOne(Long id) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement("SELECT * FROM countries WHERE country_id = ?");
        pstmt.setLong(1,id);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        Country c = getCountryFromResultset(rs);
        pstmt.close();
        return c;
    }

    @Override
    public Country deleteById(Long id) {
        return null;
    }
}
