package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.*;

public class PhimDAO {
	private static ArrayList<Phim> listPhim;
	private Connection con = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private Phim phim;
	private DanhMucPhimDAO dm_dao;
	private NhaSanXuatDAO nsx;
	private NuocSanXuatDAO nuoc;
	public PhimDAO() {
		listPhim = new ArrayList<Phim>();
		dm_dao = new DanhMucPhimDAO(); 
	    nsx = new NhaSanXuatDAO(); 
	    nuoc = new NuocSanXuatDAO();
	}

	public ArrayList<Phim> getAllPhim() {
	    ArrayList<Phim> dsPhim = new ArrayList<>();
	    String sql = "SELECT P.*, DM.tenDanhMuc, NSX.tenNSX, NS.tenNuocSX " +
	                 "FROM Phim P " +
	                 "LEFT JOIN DanhMucPhim DM ON P.danhMuc = DM.maDanhMuc " +
	                 "LEFT JOIN NhaSanXuat NSX ON P.nhaSX = NSX.maNSX " +
	                 "LEFT JOIN NuocSanXuat NS ON P.quocGia = NS.maNuocSX"; // Thực hiện phép nối với các bảng

	    try (Connection con = ConnectDB.getInstance().getConnection();
	         PreparedStatement stmt = con.prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {

	        while (rs.next()) {
	            String maPhim = rs.getString("maPhim");
	            String tenPhim = rs.getString("tenPhim");
	            java.sql.Date ngayKhoiChieu = rs.getDate("ngayKhoiChieu");
	            String daoDien = rs.getString("daoDien");
	            
	            // Lấy tên danh mục phim từ bảng DanhMucPhim
	            String maDM = rs.getString("danhMuc");
	            String tenDM = rs.getString("tenDanhMuc");
	            DanhMucPhim danhMuc = new DanhMucPhim(maDM, tenDM);

	            String dienVien = rs.getString("dienVien");
	            java.sql.Date ngayPhatHanh = rs.getDate("ngayPhatHanh");
	            String ngonNgu = rs.getString("ngonNgu");
	            String moTa = rs.getString("moTa");
	            String hinhAnh = rs.getString("hinhAnh");
	            String dinhDang = rs.getString("dinhDang");
	            boolean tinhTrang = rs.getBoolean("tinhTrang");
	            int doTuoiGioiHan = rs.getInt("doTuoiGioiHan");

	            // Lấy tên nước sản xuất từ bảng NuocSanXuat
	            String maNuocSX = rs.getString("quocGia");
	            String tenNuocSX = rs.getString("tenNuocSX");
	            NuocSanXuat quocGia = new NuocSanXuat(maNuocSX, tenNuocSX);

	            int thoiLuong = rs.getInt("thoiLuong");

	            // Lấy tên nhà sản xuất từ bảng NhaSanXuat
	            String maNSX = rs.getString("nhaSX");
	            String tenNSX = rs.getString("tenNSX");
	            NhaSanXuat nhaSX = new NhaSanXuat(maNSX, tenNSX);

	            // Khởi tạo đối tượng Phim
	            Phim phim = new Phim(maPhim, tenPhim, ngayKhoiChieu, daoDien, danhMuc,
	                                  dienVien, ngayPhatHanh, ngonNgu, moTa, hinhAnh,
	                                  dinhDang, tinhTrang, doTuoiGioiHan,thoiLuong, quocGia,
	                                  nhaSX);
	            dsPhim.add(phim);
	            
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return dsPhim;
	}
	
	public String getMaPhimByTenPhim(String tenPhim) {
	    String maPhim = null;
	    String sql = "SELECT maPhim FROM Phim WHERE tenPhim = ?";

	    try (Connection con = ConnectDB.getInstance().getConnection();
	         PreparedStatement stmt = con.prepareStatement(sql)) {

	        stmt.setString(1, tenPhim);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            maPhim = rs.getString("maPhim"); // Lấy mã phim từ kết quả truy vấn
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return maPhim;
	}
	
	public Phim timKiemPhim(String maPhimTimKiem) {
        String sql = "SELECT P.*, DM.tenDanhMuc, NSX.tenNSX, NS.tenNuocSX " +
                "FROM Phim P " +
                "LEFT JOIN DanhMucPhim DM ON P.danhMuc = DM.maDanhMuc " +
                "LEFT JOIN NhaSanXuat NSX ON P.nhaSX = NSX.maNSX " +
                "LEFT JOIN NuocSanXuat NS ON P.quocGia = NS.maNuocSX " +
                "WHERE P.maPhim = ?";

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, maPhimTimKiem);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                // Lấy dữ liệu từ ResultSet và khởi tạo đối tượng Phim
                String maPhim = rs.getString("maPhim");
                String tenPhim = rs.getString("tenPhim");
                java.sql.Date ngayKhoiChieu = rs.getDate("ngayKhoiChieu");
                String daoDien = rs.getString("daoDien");
                
                String maDM = rs.getString("danhMuc");
                String tenDM = rs.getString("tenDanhMuc");
                DanhMucPhim danhMuc = new DanhMucPhim(maDM, tenDM);

                String dienVien = rs.getString("dienVien");
                java.sql.Date ngayPhatHanh = rs.getDate("ngayPhatHanh");
                String ngonNgu = rs.getString("ngonNgu");
                String moTa = rs.getString("moTa");
                String hinhAnh = rs.getString("hinhAnh");
                String dinhDang = rs.getString("dinhDang");
                boolean tinhTrang = rs.getBoolean("tinhTrang");
                int doTuoiGioiHan = rs.getInt("doTuoiGioiHan");

                String maNuocSX = rs.getString("quocGia");
                String tenNuocSX = rs.getString("tenNuocSX");
                NuocSanXuat quocGia = new NuocSanXuat(maNuocSX, tenNuocSX);

                int thoiLuong = rs.getInt("thoiLuong");

                String maNSX = rs.getString("nhaSX");
                String tenNSX = rs.getString("tenNSX");
                NhaSanXuat nhaSX = new NhaSanXuat(maNSX, tenNSX);

                return new Phim(maPhim, tenPhim, ngayKhoiChieu, daoDien, danhMuc,
                                dienVien, ngayPhatHanh, ngonNgu, moTa, hinhAnh,
                                dinhDang, tinhTrang, doTuoiGioiHan,thoiLuong, quocGia, 
                                nhaSX);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

//	String maPhim, String tenPhim, Date ngayKhoiChieu, String daoDien, String danhMuc,
//	String dienVien, Date ngayPhatHanh, String ngonNgu, String moTa, String hinhAnh, String dinhDang,
//	boolean tinhTrang, int doTuoiGioiHan, int thoiLuong, String quocGia, String nhaSanXuat

	public boolean themPhim(String maPhim, String tenPhim, String ngayKhoiChieu, String daoDien, String danhMuc,
			String dienVien, String ngayPhatHanh, String ngonNgu, String moTa, String hinhAnh, String dinhDang,
			boolean tinhTrang, int doTuoiGioiHan, String quocGia, int thoiLuong, String nhaSX) {

// Cập nhật câu lệnh SQL để bao gồm tất cả các cột
		String sql = "INSERT INTO Phim (maPhim, tenPhim, ngayKhoiChieu, daoDien, danhMuc, dienVien, ngayPhatHanh, ngonNgu, "
				+ "moTa, hinhAnh, dinhDang, tinhTrang, doTuoiGioiHan, quocGia, thoiLuong, nhaSX) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection con = ConnectDB.getInstance().getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)) {

// Thiết lập giá trị cho các tham số trong câu lệnh SQL
			stmt.setString(1, maPhim);
			stmt.setString(2, tenPhim);
			stmt.setString(3, ngayKhoiChieu); // Ngày khởi chiếu
			stmt.setString(4, daoDien);
			stmt.setString(5,danhMuc);
			stmt.setString(6, dienVien);
			stmt.setString(7, ngayPhatHanh); // Ngày phát hành
			stmt.setString(8, ngonNgu);
			stmt.setString(9, moTa);
			stmt.setString(10, hinhAnh); // Hình ảnh
			stmt.setString(11, dinhDang); // Định dạng
			stmt.setBoolean(12, tinhTrang); // Tình trạng (true/false)
			stmt.setInt(13, doTuoiGioiHan); // Độ tuổi giới hạn
			stmt.setString(14, quocGia); // Quốc gia
			stmt.setInt(15, thoiLuong); // Thời lượng
			stmt.setString(16, nhaSX); // Nhà sản xuất

// Thực thi câu lệnh và kiểm tra kết quả
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0; // Trả về true nếu thêm thành công

		} catch (SQLException e) {
			e.printStackTrace();
			return false; // Trả về false nếu có lỗi xảy ra
		}
	}
	
	 public Phim getPhimByMaPhim(String maPhim) {
	        Connection con = null;
	        PreparedStatement statement = null;
	        ResultSet rs = null;
	        Phim phim = null;
	        try {
	            con = ConnectDB.getConnection();
	            String sql = "select * from Phim where maPhim = ?";
	            statement = con.prepareStatement(sql);
	            statement.setString(1, maPhim);
	            rs = statement.executeQuery();

	            if (rs.next()) {
	            	String maphim = rs.getString("maPhim");
	                String tenPhim = rs.getString("tenPhim");
	                Date ngayKhoiChieu = rs.getDate("ngayKhoiChieu");
	                String daoDien = rs.getString("daoDien");
	                DanhMucPhim danhMuc = new DanhMucPhim(rs.getString("danhMuc"));
	                String dienVien = rs.getString("dienVien");
	                Date ngayPhatHanh = rs.getDate("ngayPhatHanh");
	                String ngonNgu = rs.getString("ngonNgu");
	                String moTa = rs.getString("moTa");
	                String hinhAnh = rs.getString("hinhAnh");
	                String dinhDang = rs.getString("dinhDang");
	                boolean tinhTrang = rs.getBoolean("tinhTrang");
	                int doTuoiGioiHan = rs.getInt("doTuoiGioiHan");
	                NuocSanXuat quocGia = new NuocSanXuat(rs.getString("quocGia"));
	                int thoiLuong = rs.getInt("thoiLuong");
	                NhaSanXuat nhaSanXuat = new NhaSanXuat(rs.getString("nhaSX"));
	                phim = new Phim( maphim,  tenPhim,  ngayKhoiChieu,  daoDien,  danhMuc,  dienVien, ngayPhatHanh,  ngonNgu,  moTa,  hinhAnh,  dinhDang,  tinhTrang, doTuoiGioiHan,  thoiLuong,  quocGia,  nhaSanXuat);

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
	        return phim;
	    }
	
	
	public int countPhim(){
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        int count = 0;
        try{
            String query = "Select count(*) from Phim";
            con = ConnectDB.getConnection();
            statement = con.prepareStatement(query);
            rs = statement.executeQuery();
            while (rs.next()){
                count = rs.getInt(1);
            }
            rs.close();
            statement.close();
            con.close();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try{
                if (statement != null) {
                    statement.close();
                }
            }catch (SQLException e2){
                e2.printStackTrace();
            }
        }
        return count;
    }

	public Object[][] loadDataToTable(int currentPage, int rowsPerPage){
        con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        List<Object[]> rowsDataList = new ArrayList<>();
        try{
            // Note: OFFSET ? ROWS : Bỏ n dòng - FETCH NEXT ? ROWS ONLY: Chỉ lấy n dòng
            String query = "Select * from Phim order by maPhim offset ? rows fetch next ? rows only";
            con = ConnectDB.getConnection();
            statement = con.prepareStatement(query);
            statement.setInt(1, (currentPage - 1) * rowsPerPage);
            statement.setInt(2, rowsPerPage);
            rs = statement.executeQuery();
            while (rs.next()) {
                String maPhim = rs.getString("maPhim");
                String tenPhim = rs.getString("tenPhim");
                Date ngayKhoiChieu = rs.getDate("ngayKhoiChieu");
                String daoDien = rs.getString("daoDien");
                DanhMucPhim danhMuc = dm_dao.timDanhMuc(rs.getString("danhMuc"));
                String dienVien = rs.getString("dienVien");
                Date ngayPhatHanh = rs.getDate("ngayPhatHanh");
                String ngonNgu = rs.getString("ngonNgu");
                String moTa = rs.getString("moTa");
                String hinhAnh = rs.getString("hinhAnh");
                String dinhDang = rs.getString("dinhDang");
                boolean tinhTrang = rs.getBoolean("tinhTrang");
                int doTuoiGioiHan = rs.getInt("doTuoiGioiHan");
                NuocSanXuat quocGia = nuoc.timNuocSanXuat(rs.getString("quocGia"));
                int thoiLuong = rs.getInt("thoiLuong");
                NhaSanXuat nhaSanXuat = nsx.timNhaSX(rs.getString("nhaSX"));
             
                Object[] rowData = {maPhim,tenPhim,daoDien,dienVien,danhMuc.getTenDanhMuc(),nhaSanXuat.getTenNSX(),quocGia.getTenNuocSX(),ngonNgu,ngayPhatHanh,ngayKhoiChieu,doTuoiGioiHan,thoiLuong,dinhDang,moTa,hinhAnh};
                if(tinhTrang == true){
                    rowsDataList.add(rowData);
                }
            }

            rs.close();
            statement.close();
            con.close();
        }catch (SQLException e){
            System.out.println("Rows per page: " + rowsPerPage);
            System.out.println("Current page offset: " + ((currentPage - 1) * rowsPerPage));
            e.printStackTrace();
        }finally {
            try{
                if (statement != null) {
                    statement.close();
                }
                if (con != null) con.close();
            }catch (SQLException e2){
                e2.printStackTrace();
            }
        }
        return rowsDataList.toArray(new Object[0][]);
    }
	
	public String tuSinhMaPhim() {
	    ConnectDB con  = new ConnectDB();
	    con.connect();
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    int count = 0;
	    try {
	        String sql = "SELECT COUNT(*) FROM Phim";
	        ps = con.getConnection().prepareStatement(sql);
	        rs = ps.executeQuery();
	        if (rs.next()) {
	            count = rs.getInt(1);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    count += 1;

	    return String.format("PH%03d", count);
	}

	
	
	public boolean createPhim(Phim phim){
        Connection con = null;
        PreparedStatement psPhim = null;
        boolean flag = false;

        try{
            con = ConnectDB.getConnection();
            String query = "insert into Phim values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            psPhim = con.prepareStatement(query);
            psPhim.setString(1,tuSinhMaPhim());
            psPhim.setString(2, phim.getTenPhim());
            Date ngayKhoiChieu = phim.getNgayKhoiChieu(); 
            psPhim.setDate(3, new java.sql.Date(ngayKhoiChieu.getTime()));
            psPhim.setString(4, phim.getDaoDien());
            psPhim.setString(5, phim.getDanhMuc().getMaDanhMuc());
            psPhim.setString(6, phim.getDienVien());
            Date ngayPhatHanh = phim.getNgayPhatHanh(); 
            psPhim.setDate(7, new java.sql.Date(ngayPhatHanh.getTime()));
            psPhim.setString(8, phim.getNgonNgu());
            psPhim.setString(9, phim.getMoTa());
            psPhim.setString(10, phim.getHinhAnh());
            psPhim.setString(11, phim.getDinhDang());
            psPhim.setBoolean(12, phim.getTinhTrang());
            psPhim.setInt(13, phim.getDoTuoiGioiHan());
            psPhim.setString(14, phim.getQuocGia().getMaNuocSX());
            psPhim.setInt(15,phim.getThoiLuong());
            psPhim.setString(16, phim.getNhaSX().getMaNSX());
            psPhim.executeUpdate();
            con.commit();
            flag = true; 
        } catch (Exception e) {
            e.printStackTrace();
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            try {
                if (psPhim != null) psPhim.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    public boolean updatePhim(Phim phim){
        Connection con = null;
        PreparedStatement psPhim ;
        boolean flag = false;
        try{
            con = ConnectDB.getConnection();
            String sql = "update Phim set tenPhim = ?, ngayKhoiChieu = ?, daoDien = ?, danhMuc = ?, dienVien = ?, ngayPhatHanh = ?, ngonNgu = ?, moTa = ?, hinhAnh = ?, dinhDang = ?, tinhTrang = ?, doTuoiGioiHan = ?, quocGia = ?, thoiLuong = ?, nhaSX = ? where maPhim = ?";
            psPhim = con.prepareStatement(sql);
            psPhim.setString(1, phim.getTenPhim());
            Date ngayKhoiChieu = phim.getNgayKhoiChieu(); 
            psPhim.setDate(2, new java.sql.Date(ngayKhoiChieu.getTime()));
            psPhim.setString(3, phim.getDaoDien());
            psPhim.setString(4, phim.getDanhMuc().getMaDanhMuc());
            psPhim.setString(5, phim.getDienVien());
            Date ngayPhatHanh = phim.getNgayPhatHanh(); 
            psPhim.setDate(6, new java.sql.Date(ngayPhatHanh.getTime()));
            psPhim.setString(7, phim.getNgonNgu());
            psPhim.setString(8, phim.getMoTa());
            psPhim.setString(9, phim.getHinhAnh());
            psPhim.setString(10, phim.getDinhDang());
            psPhim.setBoolean(11, phim.getTinhTrang());
            psPhim.setInt(12, phim.getDoTuoiGioiHan());
            psPhim.setString(13, phim.getQuocGia().getMaNuocSX());
            psPhim.setInt(14,phim.getThoiLuong());
            psPhim.setString(15, phim.getNhaSX().getMaNSX());
            psPhim.setString(16, phim.getMaPhim());
            psPhim.executeUpdate();
            con.commit();
            flag = true;
        }catch (SQLException e){
            e.printStackTrace();
            if (con != null) {
                try {
                    con.rollback(); 
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }
    
    public boolean deletePhim(String maThuoc){
        Connection con;
        con = ConnectDB.getConnection();
        PreparedStatement ps;
        boolean flag = false;
        try{
            String sql = "update Phim set tinhTrang = 0 where maPhim = ? ";
            ps = con.prepareStatement(sql);
            ps.setString(1, maThuoc);
            ps.executeUpdate();
            con.commit();
            flag = true;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }
	
    public boolean checkTrung(ArrayList<Phim> list, String ma) {
        for(Phim x : list) {
            if(x.getMaPhim().equalsIgnoreCase(ma)){
                return false;
            }
        }
        return true;
    }
    
    public ArrayList<Phim> timPhimTheoHoTenVipProMax(String data) {
        int soKiTu = data.length();
        String[] tachData = data.split("\\s+");
        if(tachData.length > 1 ) {
            for(Phim s : listPhim) {

                String[] tachHoTen = s.getTenPhim().split("\\s+"); // Cắt từng từ trong ten phim
                for(String x : tachHoTen) {
                    for(String y : tachData) {
                        if(x.equalsIgnoreCase(y)) {
                            if(checkTrung(listPhim, s.getMaPhim())){
                            	listPhim.add(s);
                            }
                            break;
                        }
                    }
                }
            }
        } else {
            for(Phim x : listPhim) {
                String[] tach = x.getTenPhim().split("\\s+"); // Cắt từng từ trong ten
                for(String s : tach) {
                    if(s.length()>data.length()) {
                        if(s.substring(0, soKiTu).equalsIgnoreCase(data)) { //Cắt số lượng kí tự của 1 từ theo số lượng kí tự của dữ liệu nhập
                            if(checkTrung(listPhim, x.getMaPhim())){
                            	listPhim.add(x);
                            }
                        }
                    }
                }
            }
        }
        return listPhim;
    }
}
