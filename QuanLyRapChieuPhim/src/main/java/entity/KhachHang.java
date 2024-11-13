package entity;

public class KhachHang {
	private String maKH;
	private String tenKH;
	private String sdt;
	private String email;
	private DiemTichLuy diemTichLuy;
	
	
	public KhachHang(String maKH, String tenKH, String sdt, String email, DiemTichLuy diemTichLuy) {
		super();
		this.maKH = maKH;
		this.tenKH = tenKH;
		this.sdt = sdt;
		this.email = email;
		this.diemTichLuy = diemTichLuy;
	}

	public KhachHang(String maKH) {
		super();
		this.maKH = maKH;
	}
	

	public KhachHang(DiemTichLuy diemTichLuy) {
		super();
		this.diemTichLuy = diemTichLuy;
	}

	public KhachHang() {
		super();
	}
	
	public KhachHang(String maKH, String tenKH) {
		super();
		this.maKH = maKH;
		this.tenKH = tenKH;
	}

	public String getMaKH() {
		return maKH;
	}
	public void setMaKH(String maKH) {
		this.maKH = maKH;
	}
	public String getTenKH() {
		return tenKH;
	}
	public void setTenKH(String tenKH) {
		this.tenKH = tenKH;
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
	public DiemTichLuy getDiemTichLuy() {
		return diemTichLuy;
	}
	public void setDiemTichLuy(DiemTichLuy diemTichLuy) {
		this.diemTichLuy = diemTichLuy;
	}

	@Override
	public String toString() {
		return "KhachHang [maKH=" + maKH + ", tenKH=" + tenKH + ", sdt=" + sdt + ", email=" + email + ", diemTichLuy="
				+ diemTichLuy + "]";
	}
	
	
}


