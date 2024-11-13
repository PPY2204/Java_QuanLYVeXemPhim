package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.DiemTichLuy;

public class DiemTichLuyDAO {
    private ArrayList<DiemTichLuy> dsdtl;

    public DiemTichLuyDAO() {
        dsdtl = new ArrayList<>();
    }

    public ArrayList<DiemTichLuy> getAllDiemTichLuy() { 
        String sql = "SELECT * FROM DiemTichLuy";

        try (Connection con = ConnectDB.getConnection(); 
             Statement statement = con.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            // Duyệt qua kết quả trả về.
            while (rs.next()) { 
                String maDTL = rs.getString("maDTL"); 
                float diemHienTai = rs.getFloat("diemHienTai"); 
                DiemTichLuy dtl = new DiemTichLuy(maDTL, diemHienTai);
                dsdtl.add(dtl);
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return dsdtl;
    }
    
    public DiemTichLuy getDiemTichLuyBySDT(String sdt) throws Exception{
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        DiemTichLuy diemTichLuy = null;

        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT dtl.maDTL, dtl.diemHienTai FROM DiemTichLuy dtl JOIN KhachHang kh ON dtl.maDTL = kh.diemTichLuy WHERE kh.SDT = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, sdt);
            rs = statement.executeQuery();

       
            if (rs.next()) {
                String maDTL = rs.getString(1);
                float diemHienTai = rs.getFloat(2);

                diemTichLuy = new DiemTichLuy(maDTL, diemHienTai);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return diemTichLuy;
    }
}

