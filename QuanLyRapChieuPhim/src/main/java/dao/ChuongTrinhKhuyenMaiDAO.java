package dao;

import java.sql.Connection;
import java.sql.Date; // Import java.sql.Date
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.ChuongTrinhKhuyenMai;

public class ChuongTrinhKhuyenMaiDAO {
    private ArrayList<ChuongTrinhKhuyenMai> dsChuongTrinhKhuyenMai;

    public ChuongTrinhKhuyenMaiDAO() {
        dsChuongTrinhKhuyenMai = new ArrayList<ChuongTrinhKhuyenMai>();
    }

    // Method to retrieve all promotion programs from the database
    public ArrayList<ChuongTrinhKhuyenMai> getAlltbChuongTrinhKhuyenMai() {
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

            // SQL query to select all promotion programs
            String sql = "SELECT * FROM ChuongTrinhKhuyenMai";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            // Process the result set
            while (rs.next()) {
                String maCTKM = rs.getString(1);
                String tenCTKM = rs.getString(2);
                
                // Lấy ngày từ ResultSet và chuyển đổi sang java.sql.Date
                Date thoiGianBatDau = rs.getDate(3);
                Date thoiGianKetThuc = rs.getDate(4);

                // Create a new ChuongTrinhKhuyenMai object and add it to the list
                ChuongTrinhKhuyenMai ct = new ChuongTrinhKhuyenMai(maCTKM, tenCTKM, thoiGianBatDau, thoiGianKetThuc);
                dsChuongTrinhKhuyenMai.add(ct);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return the list of promotion programs
        return dsChuongTrinhKhuyenMai;
    }
}
