package entity;

import java.sql.Date;

public class NhanVien {
    private String maNV;
    private String tenNV;
    private String email;
    private String sdt;
    private Date ngaySinh;
    private boolean gioiTinh; // true: Nam, false: Nữ
    private String cccd;
    private ChucVu vaiTro; // Vai trò, tương ứng với maChucVu trong bảng ChucVu

    // Constructor với tất cả các thuộc tính
    public NhanVien(String maNV, String tenNV, String email, String sdt, Date ngaySinh, boolean gioiTinh, String cccd, ChucVu vaiTro) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.email = email;
        this.sdt = sdt;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.cccd = cccd;
        this.vaiTro = vaiTro; // Cập nhật vai trò
    }
    

    public NhanVien(String maNV, String tenNV) {
		super();
		this.maNV = maNV;
		this.tenNV = tenNV;
	}


	// Constructor chỉ với mã nhân viên
    public NhanVien(String maNV) {
        this.maNV = maNV;
    }

    // Constructor không tham số
    public NhanVien() {
    }

    // Getter và Setter cho tất cả các thuộc tính
    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public ChucVu getVaiTro() {
        return vaiTro; // Getter cho vai trò
    }

    public void setVaiTro(ChucVu vaiTro) {
        this.vaiTro = vaiTro; // Setter cho vai trò
    }

    @Override
    public String toString() {
        return "NhanVien [maNV=" + maNV + ", tenNV=" + tenNV + ", email=" + email + ", sdt=" + sdt +
                ", ngaySinh=" + ngaySinh + ", gioiTinh=" + gioiTinh + ", cccd=" + cccd +
                ", chucVu=" + (vaiTro != null ? vaiTro.getTenChucVu() : "N/A") + "]"; // In ra tên chức vụ
    }
}
