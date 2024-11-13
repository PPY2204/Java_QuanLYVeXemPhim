package entity;

import java.util.Objects;

public class DiemTichLuy {
    private String maDTL; 
    private float diemHienTai;
	public DiemTichLuy(String maDTL, float diemHienTai) {
		super();
		this.maDTL = maDTL;
		this.diemHienTai = diemHienTai;
	}
	
	public DiemTichLuy(float diemHienTai) {
		super();
		this.diemHienTai = diemHienTai;
	}

	public DiemTichLuy() {
		super();
	}
	public DiemTichLuy(String maDTL) {
		super();
		this.maDTL = maDTL;
	}
	public String getMaDTL() {
		return maDTL;
	}
	public void setMaDTL(String maDTL) {
		this.maDTL = maDTL;
	}
	public float getDiemHienTai() {
		return diemHienTai;
	}
	public void setDiemHienTai(float diemHienTai) {
		this.diemHienTai = diemHienTai;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(maDTL);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DiemTichLuy other = (DiemTichLuy) obj;
		return Objects.equals(maDTL, other.maDTL);
	}
	@Override
	public String toString() {
		return maDTL;
	}

    
}
