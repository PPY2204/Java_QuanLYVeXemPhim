package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.Ve;
import entity.KhachHang;
import entity.NhanVien;
import entity.Thue;
import entity.ChuongTrinhKhuyenMai;
import entity.DiemTichLuy;
import entity.ChiTietKhuyenMai;
import entity.ChucVu;

public class VeDAO {
    private ArrayList<Ve> dsVe;

    public VeDAO() {
        dsVe = new ArrayList<>();
    }

    public ArrayList<Ve> getAllVe() {
    	String sql = "SELECT " +
    		    "v.maVe, " +
    		    "v.ngayLap, " +
    		    "v.hinhThucThanhToan, " +
    		    "v.thue, " +
    		    "kh.maKH, " +
    		    "kh.tenKH, " +
    		    "nv.maNv, "+
    		    "kh.sdt, " +
    		    "nv.tenNV, " +
    		    "nv.email, " +
    		    "nv.sdt AS sdtNV, " +
    		    "t.maThue, " +
    		    "t.tyLeThue, " +
    		    "ctkm.phanTramKM, " +
    		    "ctkm.phanTramKM, " +
    		    "ctkm.moTa, " +
    		    "km.maCTKM, " +
    		    "km.tenCTKM " +
    		    "FROM " +
    		    "Ve v " +
    		    "LEFT JOIN KhachHang kh ON v.khachHang = kh.maKH " +
    		    "LEFT JOIN NhanVien nv ON v.nhanVien = nv.maNV " +
    		    "LEFT JOIN Thue t ON v.thue = t.maThue " +
    		    "LEFT JOIN ChiTietKhuyenMai ctkm ON v.maVe = ctkm.ve " +
    		    "LEFT JOIN ChuongTrinhKhuyenMai km ON ctkm.chuongTrinhKM = km.maCTKM;";


        ArrayList<Ve> dsVe = new ArrayList<>();

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
            	String maVe = rs.getString("maVe");

                // Lấy thông tin khach hang
            	String ma = rs.getString("maKH");
                String tenKH = rs.getString("tenKH");
                KhachHang kh = new KhachHang(ma, tenKH);
                // Lấy thông tin từ bảng NhanVien
                String maNV = rs.getString("maNV");
                String tenNV = rs.getString("tenNV");
               // Tạo đối tượng NhanVien
               NhanVien nv = new NhanVien(maNV,tenNV);
               
               //ngày lập
               Date ngayLap = rs.getDate("ngayLap"); // Lấy ngày lập
               //hinh thuc thanh taon
               String hinhThucThanhToan = rs.getString("hinhThucThanhToan"); 
                // Lấy thông tin từ bảng Thue
               String maThue = rs.getString("maThue");
               Thue thue = new Thue(maThue);
               String tenCTKM = rs.getString("maCTKM");
               Double phantramkm = rs.getDouble("phanTramKM");
               ChuongTrinhKhuyenMai chuongtrinhkhuyenmai =new ChuongTrinhKhuyenMai(tenCTKM);
               ChiTietKhuyenMai chitietkhuyenmai = new ChiTietKhuyenMai(chuongtrinhkhuyenmai,phantramkm);
               Ve ve = new Ve(maVe,kh,nv,ngayLap,hinhThucThanhToan,thue,chitietkhuyenmai);
                
               
                // Thêm vé vào danh sách
                dsVe.add(ve);
            }

            System.out.println("Số lượng vé lấy được: " + dsVe.size());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dsVe;
    }
    // Giả sử bạn đã có phương thức kết nối database
    public ArrayList<Ve> getVeKhongCoChiTietKhuyenMai() throws SQLException {
        ArrayList<Ve> listVe = new ArrayList<>();
        String sql = "SELECT v.maVe " +
                     "FROM Ve v " +
                     "LEFT JOIN ChiTietKhuyenMai ctm ON v.maVe = ctm.ve " +
                     "WHERE ctm.ve IS NULL";
        
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Ve ve = new Ve();
                ve.setMaVe(rs.getString("maVe"));
                listVe.add(ve);
            }
        }
        return listVe;
    }
    
	public String tuSinhMaVe() {
	    ConnectDB con  = new ConnectDB();
	    con.connect();
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    int count = 0;
	    try {
	        String sql = "SELECT COUNT(*) FROM Ve";
	        ps = con.getConnection().prepareStatement(sql);
	        rs = ps.executeQuery();
	        if (rs.next()) {
	            count = rs.getInt(1);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    count += 1;

	    return String.format("VE%03d", count);
	}

}
