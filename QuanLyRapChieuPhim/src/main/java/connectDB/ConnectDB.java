package connectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectDB {
    private static ConnectDB instance;
    private static Connection connection;
    private static final String url = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyVeXemPhim";
    private static final String username = "sa";
    private static final String password = "sa123";

    // Private constructor to prevent external instantiation
    public ConnectDB() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Kết nối thành công!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to get the single instance of ConnectDB
    public static ConnectDB getInstance() {
        if (instance == null) {
            instance = new ConnectDB();
        }
        return instance;
    }

    // Method to get the connection
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url, username, password);
                System.out.println("Kết nối thành công!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void connect() {
		String url = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyVeXemPhim";
		String user = "sa";
		String password = "sa123";
		try {
			Connection con = DriverManager.getConnection(url, user, password);
			System.out.println("Kết nối cơ sở dữ liệu thành công");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Kết nối cơ sở dữ liệu thất bại");
			// TODO: handle exception
		}
	}
    
    public String getMaDanhMucFromTen(String tenDanhMuc) {
        String maDanhMuc = null;
        String query = "SELECT maDanhMuc FROM DanhMuc WHERE tenDanhMuc = ?";
        
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
}
