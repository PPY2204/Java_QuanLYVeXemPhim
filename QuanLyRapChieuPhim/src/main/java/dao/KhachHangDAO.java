package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import connectDB.ConnectDB;
import entity.DiemTichLuy;
import entity.KhachHang;

public class KhachHangDAO {
	private ArrayList<KhachHang> dskh;

    public KhachHangDAO() {
    	dskh = new ArrayList<KhachHang>();
    }

    public ArrayList<KhachHang> getAllKhachHang() {
        ArrayList<KhachHang> dskh = new ArrayList<>();
        String sql = "SELECT KH.*, DTL.maDTL, DTL.diemHienTai  FROM KhachHang KH " +
                     "LEFT JOIN DiemTichLuy DTL ON KH.diemTichLuy = DTL.maDTL"; // Kết hợp với bảng DiemTichLuy

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                String maKH = rs.getString("maKH");
                String tenKH = rs.getString("tenKH");
                String sdt = rs.getString("sdt");
                String email = rs.getString("email");
                String maDTL = rs.getString("maDTL"); // Lấy mã điểm tích lũy
                float diemHienTai = rs.getFloat("diemHienTai"); // Lấy điểm hiện tại

                // Khởi tạo đối tượng DiemTichLuy với các thuộc tính
                DiemTichLuy diemTichLuy = new DiemTichLuy(maDTL, diemHienTai);
                
                // Khởi tạo đối tượng KhachHang
                KhachHang kh = new KhachHang(maKH, tenKH, sdt, email, diemTichLuy);
                dskh.add(kh); // Thêm vào danh sách khách hàng
            }
            
            System.out.println("Số lượng khách hàng lấy được: " + dskh.size()); // In số lượng khách hàng

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return dskh;
    }



    public boolean create(KhachHang kh) {
        // Kiểm tra tính hợp lệ của đối tượng KhachHang
        if (kh == null || kh.getMaKH() == null || kh.getTenKH() == null || kh.getSdt() == null || kh.getEmail() == null || kh.getDiemTichLuy() == null) {
            JOptionPane.showMessageDialog(null, "Thông tin khách hàng không hợp lệ.", "Lỗi dữ liệu", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Câu lệnh SQL để thêm điểm tích lũy nếu nó chưa tồn tại
        String insertDiemTichLuySQL = "IF NOT EXISTS (SELECT * FROM DiemTichLuy WHERE maDTL = ?) " +
                                       "BEGIN " +
                                       "INSERT INTO DiemTichLuy (maDTL, diemHienTai) VALUES (?, ?); " +
                                       "END";

        // Câu lệnh SQL để thêm khách hàng
        String insertKhachHangSQL = "INSERT INTO KhachHang (maKH, tenKH, sdt, email, diemTichLuy) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement stmtDiemTichLuy = con.prepareStatement(insertDiemTichLuySQL);
             PreparedStatement stmtKhachHang = con.prepareStatement(insertKhachHangSQL)) {

            // Thêm điểm tích lũy
            stmtDiemTichLuy.setString(1, kh.getDiemTichLuy().getMaDTL()); // Mã điểm tích lũy
            stmtDiemTichLuy.setString(2, kh.getDiemTichLuy().getMaDTL());
            stmtDiemTichLuy.setFloat(3, kh.getDiemTichLuy().getDiemHienTai()); // Điểm hiện tại

            // Thực thi câu lệnh thêm điểm tích lũy
            stmtDiemTichLuy.executeUpdate();

            // Thêm khách hàng
            stmtKhachHang.setString(1, kh.getMaKH()); // Mã khách hàng
            stmtKhachHang.setString(2, kh.getTenKH()); // Tên khách hàng
            stmtKhachHang.setString(3, kh.getSdt()); // Số điện thoại
            stmtKhachHang.setString(4, kh.getEmail()); // Email
            stmtKhachHang.setString(5, kh.getDiemTichLuy().getMaDTL()); // Mã điểm tích lũy

            // Kiểm tra và thực thi truy vấn
            return stmtKhachHang.executeUpdate() > 0; // Trả về true nếu thêm thành công
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm khách hàng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return false; // Trả về false nếu có lỗi xảy ra
    }



    public boolean update(KhachHang kh) {
        // Kiểm tra tính hợp lệ của đối tượng KhachHang
        if (kh == null || kh.getMaKH() == null || kh.getTenKH() == null || 
            kh.getSdt() == null || kh.getEmail() == null || 
            kh.getDiemTichLuy() == null) {
            throw new IllegalArgumentException("Thông tin khách hàng không hợp lệ.");
        }

        // Kiểm tra xem khách hàng đã tồn tại chưa
        if (!isCustomerExists(kh.getMaKH())) {
            System.out.println("Khách hàng không tồn tại trong hệ thống.");
            return false; // Không thực hiện cập nhật nếu khách hàng không tồn tại
        }

        String sql = "UPDATE KhachHang SET tenKH = ?, sdt = ?, email = ? WHERE maKH = ?";
        try (Connection con = ConnectDB.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, kh.getTenKH()); // Cập nhật tên khách hàng
            stmt.setString(2, kh.getSdt()); // Cập nhật số điện thoại
            stmt.setString(3, kh.getEmail()); // Cập nhật email\
            //không cho sữa điểm
            stmt.setString(4, kh.getMaKH()); // Điều kiện cập nhật theo mã khách hàng

            // Thực thi câu lệnh cập nhật
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu có ít nhất một bản ghi được cập nhật
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Trả về false nếu có lỗi xảy ra
    }

    // Hàm kiểm tra khách hàng có tồn tại hay không
    private boolean isCustomerExists(String maKH) {
        String sql = "SELECT COUNT(*) FROM KhachHang WHERE maKH = ?";
        try (Connection con = ConnectDB.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, maKH); // Truyền vào mã khách hàng

            // Thực thi câu lệnh SELECT và kiểm tra số lượng kết quả
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Nếu có ít nhất một bản ghi, trả về true
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Nếu có lỗi hoặc không tìm thấy khách hàng, trả về false
    }





    // Phương thức xóa khách hàng khỏi cơ sở dữ liệu
    public boolean delete(String maKH) {
        String sql = "DELETE FROM KhachHang WHERE maKH = ?";
        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
             
            stmt.setString(1, maKH); // Đặt mã khách hàng để xóa
            
            // Thực thi câu lệnh xóa
            return stmt.executeUpdate() > 0; // Trả về true nếu có ít nhất một bản ghi được xóa
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Trả về false nếu có lỗi xảy ra
    }

    public KhachHang findByMa(String maKH) {
        String sql = "SELECT * FROM KhachHang WHERE maKH = ?";
        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
             
            stmt.setString(1, maKH);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String tenKH = rs.getString(2);
                String sdt = rs.getString(3);
                String email = rs.getString(4);
                DiemTichLuy diemTichLuy = new DiemTichLuy(rs.getString(5), rs.getFloat(6)); 
                return new KhachHang(maKH, tenKH, sdt, email, diemTichLuy);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Trả về null nếu không tìm thấy khách hàng
    }

    
}
