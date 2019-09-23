package com.omicto.database.oracle.dao;

import com.omicto.database.dao.LocationDao;
import com.omicto.domain.Location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class LocationDaoImpl implements LocationDao {
    private Connection con;

    public LocationDaoImpl(Connection con) {
        this.con = con;
    }

    @Override
    public void save(Location location) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement("INSERT INTO hr.locations VALUES(?,?,?,?,?,?)");
        pstmt.setLong(1,location.locationId);
        pstmt.setString(2,location.streetAddress);
        pstmt.setString(3,location.postalCode);
        pstmt.setString(4,location.city);
        pstmt.setString(5,location.stateProvince);
        pstmt.setString(6,location.countryId);
        pstmt.execute();
        pstmt.close();
    }

    @Override
    public void saveAll(List<Location> t) throws SQLException {
        for(Location l: t){
            save(l);
        }
    }

    @Override
    public void update(Long id, Location location) throws SQLException {

    }

    @Override
    public List<Location> getAll() throws SQLException {
        PreparedStatement pstmt = con.prepareStatement("SELECT * FROM hr.locations");
        ResultSet rs = pstmt.executeQuery();
        List<Location> loc = new LinkedList<>();
        while(rs.next()){
            loc.add(getLocationFromResultset(rs));
        }
        pstmt.close();
        return loc;

    }

    public Location getLocationFromResultset(ResultSet rs) throws SQLException {
        return new Location(rs.getLong(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));
    }

    @Override
    public Location getOne(Long id) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement("SELECT * FROM hr.locations WHERE location_id = ?");
        pstmt.setLong(1,id);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        Location l = getLocationFromResultset(rs);
        pstmt.close();
        return l;
    }

    @Override
    public Location deleteById(Long id) {
        return null;
    }
}
