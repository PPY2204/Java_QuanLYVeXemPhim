package dao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connectDB.ConnectDB;
import entity.ChucVu;
import entity.NhanVien;
import entity.TaiKhoan;

public class TaiKhoanDAO {
    // Phương thức kiểm tra thông tin đăng nhập
	public String kiemTraDangNhap(String tenTaiKhoan, String matKhau) {
	    // Câu lệnh SQL để kiểm tra tài khoản và mật khẩu
	    String sql = "SELECT " +
	            "    cv.maChucVu " +
	            "FROM " +
	            "    TaiKhoan tk " +
	            "JOIN " +
	            "    NhanVien nv ON tk.tenTaiKhoan = nv.maNV " +
	            "JOIN " +
	            "    ChucVu cv ON nv.vaiTro = cv.maChucVu " +
	            "WHERE tk.tenTaiKhoan = ? AND tk.matKhau = ?;"; // Thêm điều kiện cho tên tài khoản và mật khẩu

	    try (Connection con = ConnectDB.getInstance().getConnection();
	         PreparedStatement stmt = con.prepareStatement(sql)) {

	        // Thiết lập giá trị cho tham số
	        stmt.setString(1, tenTaiKhoan);
	        stmt.setString(2, matKhau);

	        // Thực thi truy vấn
	        ResultSet rs = stmt.executeQuery();

	        // Kiểm tra kết quả trả về
	        if (rs.next()) {
	            // Lấy mã chức vụ
	            return rs.getString("maChucVu");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    // Đăng nhập thất bại
	    return null; // Trả về null nếu không tìm thấy thông tin
	}

}
