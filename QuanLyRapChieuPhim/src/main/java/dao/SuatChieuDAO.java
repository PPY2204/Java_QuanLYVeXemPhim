package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.DanhMucPhim;
import entity.NhaSanXuat;
import entity.NuocSanXuat;
import entity.Phim;
import entity.PhongChieu;
import entity.SuatChieu;

public class SuatChieuDAO {
    public ArrayList<SuatChieu> getAllSuatChieu() {
        ArrayList<SuatChieu> dsSuatChieu = new ArrayList<>();
        String sql = "SELECT SC.*, P.*, PC.* " +
                     "FROM SuatChieu SC " +
                     "LEFT JOIN Phim P ON SC.phim = P.maPhim " +
                     "LEFT JOIN PhongChieu PC ON SC.phongChieu = PC.maPhongChieu"; // Thực hiện phép nối với các bảng

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String maSuatChieu = rs.getString("maSuatChieu");

                // Lấy thông tin phim từ bảng Phim
                String maPhim = rs.getString("maPhim");
	            String tenPhim = rs.getString("tenPhim");
	            java.sql.Date ngayKhoiChieu = rs.getDate("ngayKhoiChieu");
	            String daoDien = rs.getString("daoDien");
	            
	            // Lấy tên danh mục phim từ bảng DanhMucPhim
	            String maDM = rs.getString("danhMuc");
	            DanhMucPhim danhMuc = new DanhMucPhim(maDM);

	            String dienVien = rs.getString("dienVien");
	            java.sql.Date ngayPhatHanh = rs.getDate("ngayPhatHanh");
	            String ngonNgu = rs.getString("ngonNgu");	
	            String moTa = rs.getString("moTa");
	            String hinhAnh = rs.getString("hinhAnh");
	            String dinhDang = rs.getString("dinhDang");
	            boolean tinhTrang = rs.getBoolean("tinhTrang");
	            int doTuoiGioiHan = rs.getInt("doTuoiGioiHan");

	            // Lấy tên nước sản xuất từ bảng NuocSanXuat
	            String maNuocSX = rs.getString("quocGia");
	            NuocSanXuat quocGia = new NuocSanXuat(maNuocSX);

	            int thoiLuong = rs.getInt("thoiLuong");

	            // Lấy tên nhà sản xuất từ bảng NhaSanXuat
	            String maNSX = rs.getString("nhaSX");
	            NhaSanXuat nhaSX = new NhaSanXuat(maNSX);

	            // Khởi tạo đối tượng Phim 
	            Phim phim = new Phim(maPhim, tenPhim, ngayKhoiChieu, daoDien, danhMuc,
	                                  dienVien, ngayPhatHanh, ngonNgu, moTa, hinhAnh,
	                                  dinhDang, tinhTrang, doTuoiGioiHan,thoiLuong, quocGia,
	                                  nhaSX);
                // Lấy thông tin phòng chiếu từ bảng PhongChieu
	            String maPhongChieu = rs.getString("maPhongChieu");
                String tenPhongChieu = rs.getString("tenPhongChieu");
                int sucChua = rs.getInt("sucChua");
                boolean trangThaiPhong = rs.getBoolean("trangThai");

                // Create a new PhongChieu object and add it to the list
                PhongChieu phongChieu = new PhongChieu(maPhongChieu, tenPhongChieu, sucChua, trangThaiPhong);

                Timestamp thoiGianKhoiChieu = rs.getTimestamp("thoiGianKhoiChieu");
                Timestamp thoiGianKetThuc = rs.getTimestamp("thoiGianKetThuc");
                boolean trangThai = rs.getBoolean("trangThai");

                // Khởi tạo đối tượng SuatChieu
                SuatChieu suatChieu = new SuatChieu(maSuatChieu, phim, phongChieu, thoiGianKhoiChieu, thoiGianKetThuc, trangThai);
                dsSuatChieu.add(suatChieu);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsSuatChieu;
    }
    
    public boolean findSuatChieuByMa(String maSuatChieu) {
        String sql = "SELECT * FROM SuatChieu WHERE maSuatChieu = ?";

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, maSuatChieu);
            ResultSet rs = stmt.executeQuery();

            return rs.next(); // Trả về true nếu tìm thấy, ngược lại false

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Trả về false nếu có lỗi xảy ra
    }

    public boolean themSuatChieu(SuatChieu suatChieu) {
        String sql = "INSERT INTO SuatChieu (maSuatChieu, phim, phongChieu, thoiGianKhoiChieu, thoiGianKetThuc, trangThai) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            // Thiết lập giá trị cho các tham số trong câu lệnh SQL
            stmt.setString(1, suatChieu.getMaSuatChieu());
            stmt.setString(2, suatChieu.getPhim().getMaPhim());
            stmt.setString(3, suatChieu.getPhongChieu().getTenPhongChieu());
            stmt.setTimestamp(4, suatChieu.getThoiGianKhoiChieu());
            stmt.setTimestamp(5, suatChieu.getThoiGianKetThuc());
            stmt.setBoolean(6, suatChieu.isTrangThai());

            // Thực thi câu lệnh và kiểm tra kết quả
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu thêm thành công

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi xảy ra
        }
    }
    public boolean xoaSuatChieu(String maSuatChieu) {
        String sql = "DELETE FROM SuatChieu WHERE maSuatChieu = ?";

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, maSuatChieu);
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0; // Trả về true nếu xóa thành công

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi xảy ra
        }
    }
    public boolean suaSuatChieu(SuatChieu suatChieu) {
        String sql = "UPDATE SuatChieu SET phim = ?, phongChieu = ?, thoiGianKhoiChieu = ?, thoiGianKetThuc = ?, trangThai = ? WHERE maSuatChieu = ?";

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            // Cập nhật các giá trị cho các tham số trong câu lệnh SQL
            stmt.setString(1, suatChieu.getPhim().getTenPhim());
            stmt.setString(2, suatChieu.getPhongChieu().getTenPhongChieu());
            stmt.setTimestamp(3, suatChieu.getThoiGianKhoiChieu());
            stmt.setTimestamp(4, suatChieu.getThoiGianKetThuc());
            stmt.setBoolean(5, suatChieu.isTrangThai());
            stmt.setString(6, suatChieu.getMaSuatChieu());  // Điều kiện WHERE

            // Thực thi câu lệnh và kiểm tra kết quả
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu cập nhật thành công

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi xảy ra
        }
    }


}
