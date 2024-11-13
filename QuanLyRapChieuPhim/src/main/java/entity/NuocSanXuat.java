package entity;

public class NuocSanXuat {
	private String maNuocSX;
	private String tenNuocSX;
	public NuocSanXuat(String maNuocSX, String tenNuocSX) {
		super();
		this.maNuocSX = maNuocSX;
		this.tenNuocSX = tenNuocSX;
	}
	
	public NuocSanXuat(String maNuocSX) {
		super();
		this.maNuocSX = maNuocSX;
	}

	public NuocSanXuat() {
		super();
	}
	public String getMaNuocSX() {
		return maNuocSX;
	}
	public void setMaNuocSX(String maNuocSX) {
		this.maNuocSX = maNuocSX;
	}
	public String getTenNuocSX() {
		return tenNuocSX;
	}
	public void setTenNuocSX(String tenNuocSX) {
		this.tenNuocSX = tenNuocSX;
	}
	@Override
	public String toString() {
		return "NuocSanXuat [maNuocSX=" + maNuocSX + ", tenNuocSX=" + tenNuocSX + "]";
	}
	
}
