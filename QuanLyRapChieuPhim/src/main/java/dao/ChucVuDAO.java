package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.ChucVu;


public class ChucVuDAO {
	ArrayList<ChucVu> dschucvu;
	public ChucVuDAO() {
		dschucvu = new ArrayList<ChucVu>();
	}
	public ArrayList<ChucVu> getalltbChucVu() {
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			if (con != null) {
			    System.out.println("Kết nối thành công");
			} else {
			    System.out.println("Kết nối thất bại");
			}
			String sql = "Select * from ChucVu";
			Statement statement = con.createStatement();
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet.
			ResultSet rs = statement.executeQuery(sql);
			// Duyệt trên kết quả trả về.
			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
				String maChucVu = rs.getString(1);
				String tenChucVu = rs.getString(2);
				ChucVu cv = new ChucVu(maChucVu, tenChucVu);
				dschucvu.add(cv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dschucvu;
	}

}