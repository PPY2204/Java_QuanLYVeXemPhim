package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.NhaSanXuat;

public class NhaSanXuatDAO {
    ArrayList<NhaSanXuat> dsnhaSanXuat;
	private ArrayList<NhaSanXuat> list;

    public NhaSanXuatDAO() {
        dsnhaSanXuat = new ArrayList<NhaSanXuat>();
        list = new ArrayList<NhaSanXuat>();
        try {
            list = getAllNhaSanXuat();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getMaNhaSXFromTen(String tenNhaSX) {
        String maNhaSX = null;
        String query = "SELECT maNSX FROM NhaSanXuat WHERE tenNSX = ?";
        
        try (Connection con = ConnectDB.getConnection(); 
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setString(1, tenNhaSX);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                maNhaSX = rs.getString("maNSX");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return maNhaSX;
    }

    public ArrayList<NhaSanXuat> getAllNhaSanXuat() throws Exception{
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from NhaSanXuat";
        ps = con.getConnection().prepareStatement(sql);
        rs = ps.executeQuery();
        while(rs.next()){
            NhaSanXuat nsx = new NhaSanXuat();
            nsx.setMaNSX((rs.getString(1)));;
            nsx.setTenNSX(rs.getString(2));
            if(timNhaSX(nsx.getMaNSX()) == null) {
                list.add(nsx);
            }
        }
        return this.list;
    }
    public NhaSanXuat getNSX(String tenNSX) {
        try{
            for(NhaSanXuat nsx : list){
                if(nsx.getTenNSX().equalsIgnoreCase(tenNSX)){
                    return nsx;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
   
    public NhaSanXuat timNhaSX(String ma) {
        for(NhaSanXuat x : list) {
            if(x.getMaNSX().equalsIgnoreCase(ma)) {
                return x;
            }
        }
        return null;
    }
    
}