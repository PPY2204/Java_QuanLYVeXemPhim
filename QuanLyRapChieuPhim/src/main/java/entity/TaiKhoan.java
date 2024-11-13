package entity;

public class TaiKhoan {
	private NhanVien tenTaiKhoan;
	private String matKhau;
	public TaiKhoan(NhanVien tenTaiKhoan, String matKhau) {
		super();
		this.tenTaiKhoan = tenTaiKhoan;
		this.matKhau = matKhau;
	}
	public TaiKhoan() {
		super();
	}
	public NhanVien getTenTaiKhoan() {
		return tenTaiKhoan;
	}
	public void setTenTaiKhoan(NhanVien tenTaiKhoan) {
		this.tenTaiKhoan = tenTaiKhoan;
	}
	public String getMatKhau() {
		return matKhau;
	}
	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}
	@Override
	public String toString() {
		return "TaiKhoan [tenTaiKhoan=" + tenTaiKhoan + ", matKhau=" + matKhau + "]";
	}
	
}	
