package entity;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Objects;

public class ChuongTrinhKhuyenMai {
	private String maCTKM;
	private String tenCTKM;
	private Date thoiGianBatDau;
	private Date thoiGianKetThuc;
	
	public ChuongTrinhKhuyenMai(String maCTKM) {
		super();
		this.maCTKM = maCTKM;
	}

	public ChuongTrinhKhuyenMai() {
		super();
	}
	
	public ChuongTrinhKhuyenMai(String maCTKM, String tenCTKM, Date thoiGianBatDau, Date thoiGianKetThuc) {
		super();
		this.maCTKM = maCTKM;
		this.tenCTKM = tenCTKM;
		this.thoiGianBatDau = thoiGianBatDau;
		this.thoiGianKetThuc = thoiGianKetThuc;
	}
	

	public String getMaCTKM() {
		return maCTKM;
	}

	public void setMaCTKM(String maCTKM) {
		this.maCTKM = maCTKM;
	}

	public String getTenCTKM() {
		return tenCTKM;
	}

	public void setTenCTKM(String tenCTKM) {
		this.tenCTKM = tenCTKM;
	}

	public Date getThoiGianBatDau() {
		return thoiGianBatDau;
	}

	public void setThoiGianBatDau(Date thoiGianBatDau) {
		this.thoiGianBatDau = thoiGianBatDau;
	}

	public Date getThoiGianKetThuc() {
		return thoiGianKetThuc;
	}

	public void setThoiGianKetThuc(Date thoiGianKetThuc) {
		this.thoiGianKetThuc = thoiGianKetThuc;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maCTKM);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChuongTrinhKhuyenMai other = (ChuongTrinhKhuyenMai) obj;
		return Objects.equals(maCTKM, other.maCTKM);
	}

	@Override
	public String toString() {
		return maCTKM;
	}
	
	
}
