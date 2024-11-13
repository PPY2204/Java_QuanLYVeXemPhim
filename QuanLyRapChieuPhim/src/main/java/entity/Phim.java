package entity;

import java.sql.Date;
import java.util.Objects;

public class Phim {
	private String maPhim;
	private String tenPhim;
	private Date ngayKhoiChieu;
	private String daoDien;
	private DanhMucPhim danhMuc;
	private String dienVien;
	private Date ngayPhatHanh;
	private String ngonNgu;
	private String moTa;
	private String hinhAnh;
	private String dinhDang;
	private boolean tinhTrang;
	private int doTuoiGioiHan;
	private int thoiLuong;
	private NuocSanXuat quocGia;
	private NhaSanXuat nhaSX;
	
	
	public Phim() {
		super();
	}
	
	public Phim(String maPhim) {
		super();
		this.maPhim = maPhim;
	}


	public Phim(String maPhim, String tenPhim, Date ngayKhoiChieu, String daoDien, DanhMucPhim danhMuc, String dienVien,
			Date ngayPhatHanh, String ngonNgu, String moTa, String hinhAnh, String dinhDang, boolean tinhTrang,
			int doTuoiGioiHan, int thoiLuong, NuocSanXuat quocGia, NhaSanXuat nhaSX) {
		super();
		this.maPhim = maPhim;
		this.tenPhim = tenPhim;
		this.ngayKhoiChieu = ngayKhoiChieu;
		this.daoDien = daoDien;
		this.danhMuc = danhMuc;
		this.dienVien = dienVien;
		this.ngayPhatHanh = ngayPhatHanh;
		this.ngonNgu = ngonNgu;
		this.moTa = moTa;
		this.hinhAnh = hinhAnh;
		this.dinhDang = dinhDang;
		this.tinhTrang = tinhTrang;
		this.doTuoiGioiHan = doTuoiGioiHan;
		this.thoiLuong = thoiLuong;
		this.quocGia = quocGia;
		this.nhaSX = nhaSX;
	}



	public String getMaPhim() {
		return maPhim;
	}


	public void setMaPhim(String maPhim) {
		this.maPhim = maPhim;
	}


	public String getTenPhim() {
		return tenPhim;
	}


	public void setTenPhim(String tenPhim) {
		this.tenPhim = tenPhim;
	}


	public Date getNgayKhoiChieu() {
		return ngayKhoiChieu;
	}


	public void setNgayKhoiChieu(Date ngayKhoiChieu) {
		this.ngayKhoiChieu = ngayKhoiChieu;
	}


	public String getDaoDien() {
		return daoDien;
	}


	public void setDaoDien(String daoDien) {
		this.daoDien = daoDien;
	}


	public DanhMucPhim getDanhMuc() {
		return danhMuc;
	}


	public void setDanhMuc(DanhMucPhim danhMuc) {
		this.danhMuc = danhMuc;
	}


	public String getDienVien() {
		return dienVien;
	}


	public void setDienVien(String dienVien) {
		this.dienVien = dienVien;
	}


	public Date getNgayPhatHanh() {
		return ngayPhatHanh;
	}


	public void setNgayPhatHanh(Date ngayPhatHanh) {
		this.ngayPhatHanh = ngayPhatHanh;
	}


	public String getNgonNgu() {
		return ngonNgu;
	}


	public void setNgonNgu(String ngonNgu) {
		this.ngonNgu = ngonNgu;
	}


	public String getMoTa() {
		return moTa;
	}


	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}


	public String getHinhAnh() {
		return hinhAnh;
	}


	public void setHinhAnh(String hinhAnh) {
		this.hinhAnh = hinhAnh;
	}


	public String getDinhDang() {
		return dinhDang;
	}


	public void setDinhDang(String dinhDang) {
		this.dinhDang = dinhDang;
	}


	public boolean getTinhTrang() {
		return tinhTrang;
	}


	public void setTinhTrang(boolean tinhTrang) {
		this.tinhTrang = tinhTrang;
	}


	public int getDoTuoiGioiHan() {
		return doTuoiGioiHan;
	}


	public void setDoTuoiGioiHan(int doTuoiGioiHan) {
		this.doTuoiGioiHan = doTuoiGioiHan;
	}


	public int getThoiLuong() {
		return thoiLuong;
	}


	public void setThoiLuong(int thoiLuong) {
		this.thoiLuong = thoiLuong;
	}


	public NuocSanXuat getQuocGia() {
		return quocGia;
	}


	public void setQuocGia(NuocSanXuat quocGia) {
		this.quocGia = quocGia;
	}


	public NhaSanXuat getNhaSX() {
		return nhaSX;
	}


	public void setNhaSX(NhaSanXuat nhaSX) {
		this.nhaSX = nhaSX;
	}


	@Override
	public int hashCode() {
		return Objects.hash(maPhim);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Phim other = (Phim) obj;
		return Objects.equals(maPhim, other.maPhim);
	}


	@Override
	public String toString() {
		return "Phim [maPhim=" + maPhim + ", tenPhim=" + tenPhim + ", ngayKhoiChieu=" + ngayKhoiChieu + ", daoDien="
				+ daoDien + ", danhMuc=" + danhMuc + ", dienVien=" + dienVien + ", ngayPhatHanh=" + ngayPhatHanh
				+ ", ngonNgu=" + ngonNgu + ", moTa=" + moTa + ", hinhAnh=" + hinhAnh + ", dinhDang=" + dinhDang
				+ ", tinhTrang=" + tinhTrang + ", doTuoiGioiHan=" + doTuoiGioiHan + ", thoiLuong=" + thoiLuong
				+ ", quocGia=" + quocGia + ", nhaSX=" + nhaSX + "]";
	}
	
	
}
	
	
