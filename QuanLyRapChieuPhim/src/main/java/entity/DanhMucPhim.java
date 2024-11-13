package entity;

public class DanhMucPhim {
	private String maDanhMuc;
	private String tenDanhMuc;
	public DanhMucPhim(String maDanhMuc, String tenDanhMuc) {
		super();
		this.maDanhMuc = maDanhMuc;
		this.tenDanhMuc = tenDanhMuc;
	}
	
	public DanhMucPhim(String maDanhMuc) {
		super();
		this.maDanhMuc = maDanhMuc;
	}

	public DanhMucPhim() {
		super();
	}
	public String getMaDanhMuc() {
		return maDanhMuc;
	}
	public void setMaDanhMuc(String maDanhMuc) {
		this.maDanhMuc = maDanhMuc;
	}
	public String getTenDanhMuc() {
		return tenDanhMuc;
	}
	public void setTenDanhMuc(String tenDanhMuc) {
		this.tenDanhMuc = tenDanhMuc;
	}
	@Override
	public String toString() {
		return maDanhMuc;
	}
	
}
