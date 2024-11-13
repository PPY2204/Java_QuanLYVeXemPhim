package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.ChiTietKhuyenMai;
import entity.ChiTietVe;
import entity.ChuongTrinhKhuyenMai;
import entity.DanhMucPhim;
import entity.DiemTichLuy;
import entity.KhachHang;
import entity.NhaSanXuat;
import entity.NhanVien;
import entity.NuocSanXuat;
import entity.Phim;
import entity.PhongChieu;
import entity.SuatChieu;
import entity.Thue;
import entity.Ve;

public class ChiTietVeDAO {
	private ArrayList<ChiTietVe> dsChiTietVe;
	public ChiTietVeDAO() {
		dsChiTietVe = new ArrayList<>();
	}
	public ArrayList<ChiTietVe> getAllChiTietVe() {
		String sql = "SELECT "
			    + "ve.maVe, "
			    + "ve.ngayLap, "
			    + "chitietve.loaiVe, "
			    + "khachhang.maKH, "
			    + "khachhang.tenKH, "
			    + "nhanvien.maNV, "
			    + "nhanvien.tenNV, "
			    + "phongchieu.tenPhongChieu, "
			    + "phim.tenPhim, "
			    + "chitietve.viTriGhe, "
			    + "suatchieu.thoiGianKhoiChieu, "
			    + "chuongtrinhkhuyenmai.maCTKM, "
			    + "thue.maThue, "
			    + "chitietve.giaVe, "
			    + "ve.hinhThucThanhToan, "
			    + "suatchieu.thoiGianKhoiChieu "
			    + "FROM "
			    + "Ve ve "
			    + "JOIN "
			    + "ChiTietVe chitietve ON ve.maVe = chitietve.ve "
			    + "JOIN "
			    + "KhachHang khachhang ON ve.khachHang = khachhang.maKH "
			    + "JOIN "
			    + "NhanVien nhanvien ON ve.nhanVien = nhanvien.maNV "
			    + "JOIN "
			    + "SuatChieu suatchieu ON chitietve.suatChieu = suatchieu.maSuatChieu "
			    + "JOIN "
			    + "PhongChieu phongchieu ON suatchieu.phongChieu = phongchieu.maPhongChieu "
			    + "JOIN "
			    + "Phim phim ON suatchieu.phim = phim.maPhim "
			    + "LEFT JOIN "
			    + "ChiTietKhuyenMai chitietkhuyenmai ON chitietve.ve = chitietkhuyenmai.ve "
			    + "LEFT JOIN "
			    + "ChuongTrinhKhuyenMai chuongtrinhkhuyenmai ON chitietkhuyenmai.chuongTrinhKM = chuongtrinhkhuyenmai.maCTKM "
			    + "LEFT JOIN "
			    + "Thue thue ON ve.thue = thue.maThue;";

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
               ChuongTrinhKhuyenMai chuongtrinhkhuyenmai =new ChuongTrinhKhuyenMai(tenCTKM);
               ChiTietKhuyenMai chitietkhuyenmai = new ChiTietKhuyenMai(chuongtrinhkhuyenmai);
               Ve ve = new Ve(maVe,kh,nv,ngayLap,hinhThucThanhToan,thue,chitietkhuyenmai);
               //vi tri ghế
               String vtg = rs.getString("viTriGhe");
               float gia = rs.getFloat("giaVe");
               //suat chieu
               String tenPhim = rs.getString("tenPhim");
               Phim phim = new Phim(tenPhim);
               String tenPhongChieu = rs.getString("tenPhongChieu");
               PhongChieu phongchieu = new PhongChieu(tenPhongChieu);
               Timestamp giochieu = rs.getTimestamp("thoiGianKhoiChieu");
               SuatChieu suatchieu = new SuatChieu(phim,phongchieu,giochieu);

               //loai vé
               String loaive = rs.getString("loaiVe");
               ChiTietVe ctv = new ChiTietVe(ve,vtg,gia,suatchieu,loaive);
               System.out.println(ctv);
               dsChiTietVe.add(ctv);

               
        	}
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsChiTietVe;
	}
	    
    // Phương thức tìm kiếm chi tiết vé theo mã vé trong cơ sở dữ liệu
    public boolean searchByMaVe(String maVe) {
        boolean isFound = false;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Kết nối với cơ sở dữ liệu
            conn = ConnectDB.getConnection(); 
            String sql = "SELECT * FROM ChiTietVe WHERE ve = ?"; // Câu lệnh SQL để tìm vé theo mã

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, maVe); // Set mã vé vào câu lệnh SQL

            rs = stmt.executeQuery();

            if (rs.next()) {
                // Nếu tìm thấy, trả về true
                isFound = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return isFound;
    }
}



        
       
