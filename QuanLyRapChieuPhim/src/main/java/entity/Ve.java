package entity;

import java.sql.Date;

public class Ve {
    private String maVe;
    private KhachHang khachHang;
    private NhanVien nhanVien;
    private Date ngayLap;
    private String phuongThucThanhToan;
    private Thue thue;
    private ChiTietKhuyenMai ctkm;  // Thêm thuộc tính ChiTietKhuyenMai
    
    // Constructor với tất cả các tham số, bao gồm cả ctkm
    public Ve(String maVe, KhachHang khachHang, NhanVien nhanVien, Date ngayLap, String phuongThucThanhToan,
              Thue thue, ChiTietKhuyenMai ctkm) {
        super();
        this.maVe = maVe;
        this.khachHang = khachHang;
        this.nhanVien = nhanVien;
        this.ngayLap = ngayLap;
        this.phuongThucThanhToan = phuongThucThanhToan;
        this.thue = thue;
        this.ctkm = ctkm;  // Khởi tạo ctkm
    }

    // Constructor chỉ với maVe
    public Ve(String maVe) {
        super();
        this.maVe = maVe;
    }
    

	// Constructor không tham số
    public Ve() {
        super();
    }

    // Getter và setter cho maVe
    public String getMaVe() {
        return maVe;
    }
    public void setMaVe(String maVe) {
        this.maVe = maVe;
    }
    

    public Ve(String maVe, KhachHang khachHang) {
		super();
		this.maVe = maVe;
		this.khachHang = khachHang;
	}

	// Getter và setter cho khachHang
    public KhachHang getKhachHang() {
        return khachHang;
    }
    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    // Getter và setter cho nhanVien
    public NhanVien getNhanVien() {
        return nhanVien;
    }
    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    // Getter và setter cho ngayLap
    public Date getNgayLap() {
        return ngayLap;
    }
    public void setNgayLap(Date ngayLap) {
        this.ngayLap = ngayLap;
    }

    // Getter và setter cho phuongThucThanhToan
    public String getPhuongThucThanhToan() {
        return phuongThucThanhToan;
    }
    public void setPhuongThucThanhToan(String phuongThucThanhToan) {
        this.phuongThucThanhToan = phuongThucThanhToan;
    }

    // Getter và setter cho thue
    public Thue getThue() {
        return thue;
    }
    public void setThue(Thue thue) {
        this.thue = thue;
    }

    // Getter và setter cho ctkm (Chương trình khuyến mãi)
    public ChiTietKhuyenMai getCtkm() {
        return ctkm;
    }

    public void setCtkm(ChiTietKhuyenMai ctkm) {
        this.ctkm = ctkm;
    }

    @Override
    public String toString() {
        return "Ve [maVe=" + maVe + ", khachHang=" + khachHang + ", nhanVien=" + nhanVien + ", ngayLap=" + ngayLap
                + ", phuongThucThanhToan=" + phuongThucThanhToan + ", thue=" + thue + ", ctkm=" + ctkm + "]";  // Cập nhật method toString
    }
}
