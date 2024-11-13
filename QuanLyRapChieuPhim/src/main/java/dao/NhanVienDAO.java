package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import connectDB.ConnectDB;
import entity.ChucVu;
import entity.NhanVien;

public class NhanVienDAO {
	private ArrayList<NhanVien> dsnv;


	public NhanVienDAO() {
		dsnv = new ArrayList<NhanVien>();
	}
	public ArrayList<NhanVien> getAllNhanVien() {
	    ArrayList<NhanVien> danhSachNhanVien = new ArrayList<>();
	    try (Connection con = ConnectDB.getInstance().getConnection();
	            Statement statement = con.createStatement();
	            ResultSet rs = statement.executeQuery("SELECT NV.*, CV.tenChucVu FROM NhanVien NV JOIN ChucVu CV ON NV.vaiTro = CV.maChucVu")) {

	           while (rs.next()) {
	               String maNV = rs.getString("maNV");
	               String tenNV = rs.getString("tenNV");
	               String email = rs.getString("email");
	               String sdt = rs.getString("sdt");
	               Date ngaySinh = rs.getDate("ngaySinh");
	               boolean gioiTinh = rs.getBoolean("gioiTinh");
	               String cccd = rs.getString("cccd");
	               String maChucVu = rs.getString("vaiTro");
	               String tenChucVu = rs.getString("tenChucVu");
	               
	               // Khởi tạo đối tượng ChucVu
	               ChucVu vaiTro = new ChucVu(maChucVu, tenChucVu);

	               // Tạo đối tượng NhanVien
	               NhanVien nv = new NhanVien(maNV, tenNV, email, sdt, ngaySinh, gioiTinh, cccd, vaiTro);
	               danhSachNhanVien.add(nv);
	           }

	       } catch (SQLException e) {
	           e.printStackTrace();
	       }
	    return danhSachNhanVien;
	}

	public boolean existsMaNV(String maNV) {
        String sql = "SELECT COUNT(*) FROM NhanVien WHERE maNV = ?";
        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
             
            stmt.setString(1, maNV);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Trả về true nếu đã tồn tại
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý ngoại lệ
        }
        return false; // Trả về false nếu không tìm thấy
    }
	public boolean themNhanVien(NhanVien nv) {
	    if (nv == null || nv.getVaiTro() == null || nv.getVaiTro().getMaChucVu() == null) {
	        JOptionPane.showMessageDialog(null, "Thông tin nhân viên hoặc vai trò không hợp lệ.", "Lỗi dữ liệu", JOptionPane.ERROR_MESSAGE);
	        return false;
	    }

	    String sql = "INSERT INTO NhanVien (maNV, tenNV, email, sdt, ngaySinh, gioiTinh, cccd, vaiTro) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	    try (Connection con = ConnectDB.getInstance().getConnection();
	         PreparedStatement stmt = con.prepareStatement(sql)) {

	        stmt.setString(1, nv.getMaNV()); // Mã nhân viên
	        stmt.setString(2, nv.getTenNV()); // Tên nhân viên
	        stmt.setString(3, nv.getEmail()); // Email
	        stmt.setString(4, nv.getSdt()); // Số điện thoại
	        stmt.setDate(5, new java.sql.Date(nv.getNgaySinh().getTime())); // Ngày sinh
	        stmt.setBoolean(6, nv.isGioiTinh()); // Giới tính
	        stmt.setString(7, nv.getCccd()); // CCCD
	        stmt.setString(8, nv.getVaiTro().getMaChucVu()); // Mã chức vụ

	        return stmt.executeUpdate() > 0; // Trả về true nếu thêm thành công
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Lỗi khi thêm nhân viên: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
	    }
	    return false; // Trả về false nếu không thêm được
	}



	public boolean themTaiKhoan(String tenTaiKhoan, String matKhau) {
	    String sql = "INSERT INTO TaiKhoan (tenTaiKhoan, matKhau) VALUES (?, ?)";
	    try (Connection con = ConnectDB.getInstance().getConnection();
	         PreparedStatement stmt = con.prepareStatement(sql)) {
	        
	        stmt.setString(1, tenTaiKhoan); // Tên tài khoản
	        stmt.setString(2, matKhau); // Mật khẩu từ người dùng
	        return stmt.executeUpdate() > 0; // Trả về true nếu thêm tài khoản thành công
	    } catch (SQLException e) {
	        e.printStackTrace(); // Xử lý ngoại lệ
	    }
	    return false; // Trả về false nếu không thêm được
	}





	public boolean delete(String maNV) {
	    if (maNV == null || maNV.isEmpty()) {
	        throw new IllegalArgumentException("Mã nhân viên không hợp lệ");
	    }

	    // Xóa tài khoản liên quan
	    String deleteAccountSql = "DELETE FROM TaiKhoan WHERE tenTaiKhoan = ?";
	    try (Connection con = ConnectDB.getInstance().getConnection();
	         PreparedStatement accountStmt = con.prepareStatement(deleteAccountSql)) {

	        accountStmt.setString(1, maNV);
	        accountStmt.executeUpdate(); // Thực thi xóa tài khoản
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false; // Trả về false nếu có lỗi xảy ra
	    }

	    // Sau khi xóa tài khoản, tiếp tục xóa nhân viên
	    String sql = "DELETE FROM NhanVien WHERE maNV = ?";
	    try (Connection con = ConnectDB.getInstance().getConnection();
	         PreparedStatement stmt = con.prepareStatement(sql)) {
	        
	        stmt.setString(1, maNV);
	        return stmt.executeUpdate() > 0; // Trả về true nếu có ít nhất một bản ghi được xóa
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false; // Trả về false nếu có lỗi xảy ra
	}




	public static boolean update(NhanVien nv) {
	    if (nv == null) {
	        throw new IllegalArgumentException("NhanVien cannot be null");
	    }

	    // Mã nhân viên không được phép thay đổi, nên chỉ lấy mã từ đối tượng nv
	    String sql = "UPDATE NhanVien SET tenNV = ?, email = ?, sdt = ?, ngaySinh = ?, gioiTinh = ?, cccd = ?, vaiTro = ? WHERE maNV = ?";
	    try (Connection con = ConnectDB.getInstance().getConnection();
	         PreparedStatement stmt = con.prepareStatement(sql)) {
	         
	        // Cập nhật các thuộc tính của nhân viên
	        stmt.setString(1, nv.getTenNV()); // Tên nhân viên
	        stmt.setString(2, nv.getEmail()); // Email
	        stmt.setString(3, nv.getSdt()); // Số điện thoại
	        stmt.setDate(4, new java.sql.Date(nv.getNgaySinh().getTime())); // Ngày sinh
	        stmt.setBoolean(5, nv.isGioiTinh()); // Giới tính
	        stmt.setString(6, nv.getCccd()); // CCCD
	        stmt.setString(7, nv.getVaiTro().getMaChucVu()); // Mã chức vụ
	        stmt.setString(8, nv.getMaNV()); // Mã nhân viên để xác định bản ghi cần cập nhật

	        // Thực thi câu lệnh cập nhật
	        return stmt.executeUpdate() > 0; // Trả về true nếu có ít nhất một bản ghi được cập nhật
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false; // Trả về false nếu có lỗi xảy ra
	}




    public static NhanVien findByMa(String maNV) {
        String sql = "SELECT nv.*, cv.tenChucVu " + 
                     "FROM NhanVien nv " + 
                     "JOIN ChucVu cv ON nv.vaiTro = cv.maChucVu " +
                     "WHERE nv.maNV = ?";
        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            
            stmt.setString(1, maNV);
            ResultSet rs = stmt.executeQuery();
            
            // Kiểm tra nếu có kết quả trả về
            if (rs.next()) {
                String tenNV = rs.getString("tenNV"); // Tên nhân viên
                String email = rs.getString("email"); // Email
                String sdt = rs.getString("sdt");     // Số điện thoại
                Date ngaySinh = rs.getDate("ngaySinh"); // Ngày sinh
                boolean gioiTinh = rs.getBoolean("gioiTinh"); // Giới tính
                String cccd = rs.getString("cccd");   // CCCD
                String maChucVu = rs.getString("vaiTro"); // Mã chức vụ
                String tenChucVu = rs.getString("tenChucVu"); // Tên chức vụ từ bảng ChucVu
                
                ChucVu vaiTro = new ChucVu(maChucVu, tenChucVu);
                return new NhanVien(maNV, tenNV, email, sdt, ngaySinh, gioiTinh, cccd, vaiTro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Trả về null nếu không tìm thấy nhân viên
    }
    



}
