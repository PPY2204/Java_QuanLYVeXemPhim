package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.Thue;

public class ThueDAO {
    private ArrayList<Thue> dsThue;

    public ThueDAO() {
        dsThue = new ArrayList<Thue>();
    }

    // Method to retrieve all taxes from the database
    public ArrayList<Thue> getAllThue() {
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

            // SQL query to select all taxes
            String sql = "SELECT * FROM Thue";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            // Process the result set
            while (rs.next()) {
                String maThue = rs.getString("maThue");
                String loaiThue = rs.getString("loaiThue");
                float tyLeThue = rs.getFloat("tyLeThue");

                // Create a new Thue object and add it to the list
                Thue thue = new Thue(maThue, loaiThue, tyLeThue);
                dsThue.add(thue);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return the list of taxes
        return dsThue;
    }
}
