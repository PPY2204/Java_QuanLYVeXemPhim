package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.NuocSanXuat;

public class NuocSanXuatDAO {
    ArrayList<NuocSanXuat> dsnnuocSanXuat;
	private ArrayList<NuocSanXuat> list;

    public NuocSanXuatDAO() {
        dsnnuocSanXuat = new ArrayList<NuocSanXuat>();
        list = new ArrayList<NuocSanXuat>();
        try {
            list = getAllNuocSanXuat();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getMaNuocSXFromTen(String tenNuocSX) {
        String maNuocSX = null;
        String query = "SELECT maNuocSX FROM NuocSanXuat WHERE tenNuocSX = ?";
        
        try (Connection con = ConnectDB.getConnection(); 
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setString(1, tenNuocSX);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                maNuocSX = rs.getString("maNuocSX");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return maNuocSX;
    }

    public ArrayList<NuocSanXuat> getAllNuocSanXuat() throws Exception{
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from NuocSanXuat";
        ps = con.getConnection().prepareStatement(sql);
        rs = ps.executeQuery();
        while(rs.next()){
            NuocSanXuat nuoc = new NuocSanXuat();
            nuoc.setMaNuocSX(rs.getString(1));
            nuoc.setTenNuocSX(rs.getString(2));
            if(timNuocSanXuat(nuoc.getMaNuocSX()) == null){
                list.add(nuoc);
            }
        }
        return this.list;
    }
    public NuocSanXuat getNuocSX(String tenNuocSX) {
        try{
            for(NuocSanXuat nuoc : list){
                if(nuoc.getTenNuocSX().equalsIgnoreCase(tenNuocSX)){
                    return nuoc;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public NuocSanXuat timNuocSanXuat(String maNSX) {
        for(NuocSanXuat x : list) {
            if(x.getMaNuocSX().equalsIgnoreCase(maNSX)) {
                return x;
            }
        }
        return null;
    }

}
