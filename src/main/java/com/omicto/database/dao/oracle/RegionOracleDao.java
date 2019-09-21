package com.omicto.database.dao.oracle;

import com.omicto.database.dao.RegionDao;
import com.omicto.domain.Region;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class RegionOracleDao implements RegionDao {
    private Connection con;

    public RegionOracleDao(Connection con) {
        this.con = con;
    }

    @Override
    public void save(Region region) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement("INSERT INTO regions VALUES(?,?)");
        pstmt.setLong(1,region.regionId);
        pstmt.setString(2,region.regionName);
        pstmt.execute();
        pstmt.close();
    }

    @Override
    public void saveAll(List<Region> t) throws SQLException {
        for(Region r : t){
            save(r);
        }
    }

    @Override
    public void update(Long id, Region region) throws SQLException {

    }

    @Override
    public List<Region> getAll() throws SQLException {
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM regions");
        ResultSet rs = pstm.executeQuery();
        List<Region> reg = new LinkedList<>();
        while(rs.next()){
            reg.add(getRegionFromResultset(rs));
        }
        pstm.close();
        return reg;
    }

    public Region getRegionFromResultset(ResultSet rs) throws SQLException {
        return new Region(rs.getLong(1),rs.getString(1));
    }

    @Override
    public Region getOne(Long id) throws SQLException {
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM regions WHERE region_id = ?");
        pstm.setLong(1,id);
        ResultSet rs = pstm.executeQuery();
        rs.next();
        Region r = getRegionFromResultset(rs);
        pstm.close();
        return r;
    }

    @Override
    public Region deleteById(Long id) {
        return null;
    }
}
