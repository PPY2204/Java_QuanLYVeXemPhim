package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.ChiTietKhuyenMai;
import entity.ChuongTrinhKhuyenMai;
import entity.DiemTichLuy;
import entity.KhachHang;
import entity.Ve;

public class ChiTietKhuyenMaiDAO {
	private ArrayList<ChiTietKhuyenMai> dsChiTietKhuyenMai;

    public ChiTietKhuyenMaiDAO() {
        dsChiTietKhuyenMai = new ArrayList<ChiTietKhuyenMai>();
    }

    public ArrayList<ChiTietKhuyenMai> getAlltbChitietKhuyenMai() {
    	String sql = "SELECT ctkm.ve, ctkm.chuongTrinhKM, ctkm.phanTramKM, ctkm.moTa, " +
                "ve.maVe, dtl.diemHienTai, km.maCTKM, km.tenCTKM, " +
                "km.thoiGianBatDau, km.thoiGianKetThuc " +
                "FROM ChiTietKhuyenMai ctkm " +
                "INNER JOIN Ve ve ON ctkm.ve = ve.maVe " +
                "INNER JOIN ChuongTrinhKhuyenMai km ON ctkm.chuongTrinhKM = km.maCTKM " +
                "INNER JOIN KhachHang kh ON ve.khachHang = kh.maKH " +
                "INNER JOIN DiemTichLuy dtl ON kh.diemTichLuy = dtl.maDTL;";


        ArrayList<ChiTietKhuyenMai> dsChiTietKhuyenMai = new ArrayList<>();

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Retrieve Ve attributes
                String maVe = rs.getString("maVe");
//                String khachhang = rs.getString("khachHang");
                Float dtlFloat = rs.getFloat("diemHienTai");
                DiemTichLuy diemTichLuy = new DiemTichLuy(dtlFloat);
                KhachHang khachHang = new KhachHang(diemTichLuy);
                Ve ve = new Ve(maVe,khachHang);

                // Retrieve ChuongTrinhKhuyenMai attributes
                String maCTKM = rs.getString("maCTKM");
                String tenCTKM = rs.getString("tenCTKM");
                java.sql.Date thoiGianBatDau = rs.getDate("thoiGianBatDau");
                java.sql.Date thoiGianKetThuc = rs.getDate("thoiGianKetThuc");

                ChuongTrinhKhuyenMai ctkm = new ChuongTrinhKhuyenMai(maCTKM, tenCTKM, thoiGianBatDau, thoiGianKetThuc);
                // Retrieve ChiTietKhuyenMai-specific fields
                double phanTramKM = rs.getDouble("phanTramKM");
                String moTa = rs.getString("moTa");
                
                
                // Create ChiTietKhuyenMai object and add it to the list
                ChiTietKhuyenMai ct = new ChiTietKhuyenMai(ve, ctkm, phanTramKM, moTa);
                System.out.println(thoiGianBatDau);
                dsChiTietKhuyenMai.add(ct);
            }

            System.out.println("Số lượng chi tiết khuyến mãi lấy được: " + dsChiTietKhuyenMai.size());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dsChiTietKhuyenMai;
    }
    public boolean xoaChuongTrinhKhuyenMai(String maCTKM) {
        String sqlChiTiet = "DELETE FROM ChiTietKhuyenMai WHERE chuongTrinhKM = ?";
        String sqlChuongTrinh = "DELETE FROM ChuongTrinhKhuyenMai WHERE maCTKM = ?";
        
        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement stmtChiTiet = con.prepareStatement(sqlChiTiet);
             PreparedStatement stmtChuongTrinh = con.prepareStatement(sqlChuongTrinh)) {
            
            // Xóa các dòng trong bảng ChiTietKhuyenMai trước
            stmtChiTiet.setString(1, maCTKM);
            stmtChiTiet.executeUpdate();
            
            // Sau đó xóa dòng trong bảng ChuongTrinhKhuyenMai
            stmtChuongTrinh.setString(1, maCTKM);
            int affectedRows = stmtChuongTrinh.executeUpdate();
            
            return affectedRows > 0; // Trả về true nếu xóa thành công
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false; // Trả về false nếu không xóa được
    }
    public boolean searchByMaVe(String maCTKM) {
        boolean isFound = false;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Kết nối với cơ sở dữ liệu
            conn = ConnectDB.getConnection(); 
            String sql = "SELECT * FROM ChuongTrinhKhuyenMai WHERE maCTKM = ?"; 

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, maCTKM); 

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

 // Hàm thêm khuyến mãi và chi tiết khuyến mãi vào database
	public boolean themMaKhuyenMai(String maCTKM, String tenCTKM, String ngayBD, String ngayKT, String veID,
			double phanTramKM, String moTa, double diemTru) throws SQLException {
		Connection connection = ConnectDB.getConnection();
		try {
			connection.setAutoCommit(false); // Bắt đầu transaction

// 1. Kiểm tra điểm hiện tại
			String checkDiemSQL = "SELECT diemHienTai FROM DiemTichLuy " + "WHERE maDTL = ("
					+ "    SELECT kh.diemTichLuy " + "    FROM KhachHang kh "
					+ "    JOIN Ve ve ON ve.khachHang = kh.maKH " + "    WHERE ve.maVe = ?" + ")";
			double diemHienTai = 0;
			try (PreparedStatement checkDiemStmt = connection.prepareStatement(checkDiemSQL)) {
				checkDiemStmt.setString(1, veID);
				ResultSet resultSet = checkDiemStmt.executeQuery();
				if (resultSet.next()) {
					diemHienTai = resultSet.getDouble("diemHienTai");
				}
			}

// Kiểm tra nếu điểm tích lũy hiện tại nhỏ hơn điểm cần trừ hoặc nếu điểm sẽ trở thành âm
			if (diemHienTai - diemTru < 0) {
				throw new SQLException("Điểm tích lũy không đủ để trừ. Vui lòng kiểm tra lại.");
			}

// 2. Cập nhật điểm tích lũy
			String updateDiemSQL = "UPDATE DiemTichLuy SET diemHienTai = diemHienTai - ? " + "WHERE maDTL = ("
					+ "    SELECT kh.diemTichLuy " + "    FROM KhachHang kh "
					+ "    JOIN Ve ve ON ve.khachHang = kh.maKH " + "    WHERE ve.maVe = ?" + ")";
			try (PreparedStatement updateDiemStmt = connection.prepareStatement(updateDiemSQL)) {
				updateDiemStmt.setDouble(1, diemTru);
				updateDiemStmt.setString(2, veID);
				updateDiemStmt.executeUpdate();
			}

// 3. Thêm chương trình khuyến mãi
			String insertCTKMSQL = "INSERT INTO ChuongTrinhKhuyenMai (maCTKM, tenCTKM, thoiGianBatDau, thoiGianKetThuc) VALUES (?, ?, ?, ?)";
			try (PreparedStatement insertCTKMStmt = connection.prepareStatement(insertCTKMSQL)) {
				insertCTKMStmt.setString(1, maCTKM);
				insertCTKMStmt.setString(2, tenCTKM);
				insertCTKMStmt.setString(3, ngayBD);
				insertCTKMStmt.setString(4, ngayKT);
				insertCTKMStmt.executeUpdate();
			}

// 4. Thêm chi tiết khuyến mãi
			String insertCTKMSQL2 = "INSERT INTO ChiTietKhuyenMai (ve, chuongTrinhKM, phanTramKM, moTa) VALUES (?, ?, ?, ?)";
			try (PreparedStatement insertChiTietKMStmt = connection.prepareStatement(insertCTKMSQL2)) {
				insertChiTietKMStmt.setString(1, veID);
				insertChiTietKMStmt.setString(2, maCTKM);
				insertChiTietKMStmt.setDouble(3, phanTramKM);
				insertChiTietKMStmt.setString(4, moTa);
				insertChiTietKMStmt.executeUpdate();
			}

			connection.commit(); // Xác nhận transaction
			return true;

		} catch (SQLException e) {
			try {
				connection.rollback(); // Hủy bỏ transaction nếu có lỗi
			} catch (SQLException rollbackEx) {
				rollbackEx.printStackTrace();
			}
			e.printStackTrace();
			throw e; // Ném lại lỗi sau khi rollback
		} finally {
			try {
				connection.setAutoCommit(true); // Trở lại chế độ tự động commit
			} catch (SQLException autoCommitEx) {
				autoCommitEx.printStackTrace();
			}
		}
	}
	
	public boolean updateKhuyenMai(String maCTKM, String tenCTKM, String thoiGianBatDau, String thoiGianKetThuc, float phanTramKM, String moTa) {
	    String updateChuongTrinhSql = "UPDATE ChuongTrinhKhuyenMai SET tenCTKM = ?, thoiGianBatDau = ?, thoiGianKetThuc = ? WHERE maCTKM = ?";
	    String updateChiTietSql = "UPDATE ChiTietKhuyenMai SET phanTramKM = ?, moTa = ? WHERE chuongTrinhKM = ?";

	    try (Connection con = ConnectDB.getInstance().getConnection()) {
	        // Bắt đầu transaction
	        con.setAutoCommit(false);

	        // Cập nhật chương trình khuyến mãi
	        try (PreparedStatement stmt = con.prepareStatement(updateChuongTrinhSql)) {
	            stmt.setString(1, tenCTKM);
	            stmt.setString(2, thoiGianBatDau);
	            stmt.setString(3, thoiGianKetThuc);
	            stmt.setString(4, maCTKM);
	            int rowsUpdated1 = stmt.executeUpdate();
	            if (rowsUpdated1 == 0) {
	                con.rollback(); // Nếu không có dòng nào được cập nhật, rollback
	                return false;
	            }
	        }

	        // Cập nhật chi tiết khuyến mãi
	        try (PreparedStatement stmt = con.prepareStatement(updateChiTietSql)) {
	            stmt.setFloat(1, phanTramKM);
	            stmt.setString(2, moTa);
	            stmt.setString(3, maCTKM);
	            int rowsUpdated2 = stmt.executeUpdate();
	            if (rowsUpdated2 == 0) {
	                con.rollback(); // Nếu không có dòng nào được cập nhật, rollback
	                return false;
	            }
	        }

	        // Cam kết transaction
	        con.commit();
	        return true;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        try (Connection con = ConnectDB.getInstance().getConnection()) {
	            con.rollback(); // Nếu có lỗi, rollback transaction
	        } catch (SQLException rollbackEx) {
	            rollbackEx.printStackTrace();
	        }
	        return false;
	    }
	}

//	public ArrayList<ChiTietKhuyenMai> getChiTietKMByCTKM(ChuongTrinhKhuyenMai chuongTrinhKhuyenMai) throws Exception {
//        ArrayList<ChiTietKhuyenMai> dsCTKM = new ArrayList<ChiTietKhuyenMai>();
//        Connection con = null;
//        PreparedStatement statement = null;
//        ResultSet rs = null;
//
//        try {
//            con = ConnectDB.getConnection();
//            String sql = "SELECT * FROM ChiTietKhuyenMai WHERE maCTKM = ?";
//            statement = con.prepareStatement(sql);
//            statement.setString(1, chuongTrinhKhuyenMai.getMaCTKM());
//            rs = statement.executeQuery();
//
//            while (rs.next()) {
//            	String maVe = "";
//                String maCTKM = rs.getString("maCTKM");
//                double phanTramKM = rs.getDouble("phanTramKM");
//                String moTa = rs.getString("moTa");
//
//                // Create ChiTietKhuyenMai object
//                ChiTietKhuyenMai chiTietKhuyenMai = new ChiTietKhuyenMai(chuongTrinhKhuyenMai, thuoc, tyLeKhuyenMai, soLuongToiThieu);
//
//                // Add to the list
//                dsCTKM.add(chiTietKhuyenMai);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (statement != null) {
//                    statement.close();
//                }
//            } catch (SQLException e2) {
//                e2.printStackTrace();
//            }
//        }
//        return dsCTKM;
//    }

}



