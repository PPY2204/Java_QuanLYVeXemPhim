package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.PhongChieu;

public class PhongChieuDAO {
    private ArrayList<PhongChieu> dsPhongChieu;

    public PhongChieuDAO() {
        dsPhongChieu = new ArrayList<PhongChieu>();
    }

    // Method to retrieve all rooms from the database
    public ArrayList<PhongChieu> getAllPhongChieu() {
        try {
            // Initialize database connection
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();

            // Verify connection
            if (con != null) {
                System.out.println("Kết nối thành công");
            } else {
                System.out.println("Kết nối thất bại");
            }

            // SQL query to select all rooms
            String sql = "SELECT * FROM PhongChieu";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            // Process the result set
            while (rs.next()) {
                String maPhongChieu = rs.getString("maPhongChieu");
                String tenPhongChieu = rs.getString("tenPhongChieu");
                int sucChua = rs.getInt("sucChua");
                boolean trangThaiPhong = rs.getBoolean("trangThai");

                // Create a new PhongChieu object and add it to the list
                PhongChieu phongChieu = new PhongChieu(maPhongChieu, tenPhongChieu, sucChua, trangThaiPhong);
                dsPhongChieu.add(phongChieu);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return the list of rooms
        return dsPhongChieu;
    }
 // Trong PhongDAO.java
    public boolean timKiemPhongChieu(String maPhong) {
        boolean exists = false;
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();

            // Câu truy vấn kiểm tra mã phòng
            String sql = "SELECT 1 FROM PhongChieu WHERE maPhongChieu = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maPhong);
            
            // Thực hiện truy vấn
            ResultSet rs = ps.executeQuery();
            exists = rs.next(); // True nếu có kết quả, false nếu không có
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }
    
 // Trong PhongDAO.java
    public boolean capNhatPhongChieu(String maPhong, String tenPhong, int sucChua, boolean trangThai) {
        Connection con = ConnectDB.getConnection();
        String sql = "UPDATE PhongChieu SET tenPhongChieu = ?, sucChua = ?, trangThai = ? WHERE maPhongChieu = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, tenPhong);           // Cập nhật tên phòng chiếu
            ps.setInt(2, sucChua);                // Cập nhật sức chứa
            ps.setBoolean(3, trangThai);          // Cập nhật trạng thái (0: bảo trì, 1: hoạt động)
            ps.setString(4, maPhong);             // Điều kiện mã phòng chiếu

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;               // Trả về true nếu có hàng được cập nhật
        } catch (SQLException e) {
            e.printStackTrace();
            return false;                         // Trả về false nếu có lỗi
        }
    }
    public String getMaPhongChieuByTenPhong(String tenPhong) {
        String maPhongChieu = null;
        String sql = "SELECT maPhongChieu FROM PhongChieu WHERE tenPhongChieu = ?";

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, tenPhong);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                maPhongChieu = rs.getString("maPhongChieu");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maPhongChieu;
    }


}
