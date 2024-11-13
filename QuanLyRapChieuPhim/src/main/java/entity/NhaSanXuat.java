package entity;

public class NhaSanXuat {
	private String maNSX;
	private String tenNSX;
	public NhaSanXuat(String maNSX, String tenNSX) {
		super();
		this.maNSX = maNSX;
		this.tenNSX = tenNSX;
	}
	
	public NhaSanXuat(String maNSX) {
		super();
		this.maNSX = maNSX;
	}

	public NhaSanXuat() {
		super();
	}
	public String getMaNSX() {
		return maNSX;
	}
	public void setMaNSX(String maNSX) {
		this.maNSX = maNSX;
	}
	public String getTenNSX() {
		return tenNSX;
	}
	public void setTenNSX(String tenNSX) {
		this.tenNSX = tenNSX;
	}
	@Override
	public String toString() {
		return  maNSX;
	}
	

}
