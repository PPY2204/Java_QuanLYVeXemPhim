package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.DanhMucPhim;

public class DanhMucPhimDAO {
    ArrayList<DanhMucPhim> dsdanhMucPhim;
	private ArrayList<DanhMucPhim> list;

    public DanhMucPhimDAO() {
        dsdanhMucPhim = new ArrayList<DanhMucPhim>();
        list = new ArrayList<DanhMucPhim>();
        try {
            list = getAllDanhMuc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<DanhMucPhim> getAllDanhMucPhim() {
        String sql = "SELECT * FROM DanhMucPhim";
        
        try (Connection con = ConnectDB.getConnection();
             Statement statement = con.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            if (con != null) {
                System.out.println("Kết nối thành công");
            } else {
                System.out.println("Kết nối thất bại");
            }

            // Xóa danh sách để tránh trùng lặp khi gọi lại hàm
            dsdanhMucPhim.clear();

            while (rs.next()) {
                String maDanhMuc = rs.getString("maDanhMuc");
                String tenDanhMuc = rs.getString("tenDanhMuc");
                DanhMucPhim dm = new DanhMucPhim(maDanhMuc, tenDanhMuc);
                dsdanhMucPhim.add(dm);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsdanhMucPhim;
    }
    public String getMaDanhMucFromTen(String tenDanhMuc) {
        String maDanhMuc = null;
        String query = "SELECT maDanhMuc FROM DanhMucPhim WHERE tenDanhMuc = ?";
        
        try (Connection con = ConnectDB.getConnection(); 
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setString(1, tenDanhMuc);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                maDanhMuc = rs.getString("maDanhMuc");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return maDanhMuc;
    }
    
    public ArrayList<DanhMucPhim> getAllDanhMuc() throws Exception{
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from DanhMucPhim";
        ps = con.getConnection().prepareStatement(sql);
        rs = ps.executeQuery();
        while(rs.next()){
        	DanhMucPhim dm = new DanhMucPhim();
            dm.setMaDanhMuc(rs.getString(1));
            dm.setTenDanhMuc(rs.getString(2));
            if(timDanhMuc(dm.getMaDanhMuc()) == null) {
                list.add(dm);
            }
        }
        return this.list;
    }
	
	public DanhMucPhim getDanhMuc(String tenDanhMuc){
        try{
            for(DanhMucPhim d : list){
                if(d.getTenDanhMuc().equalsIgnoreCase(tenDanhMuc)){
                    return d;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }
    public DanhMucPhim timDanhMuc(String maDanhMuc) {
        for(DanhMucPhim x : list) {
            if(x.getMaDanhMuc().equalsIgnoreCase(maDanhMuc)) {
                return x;
            }
        }
        return null;
    }


}
