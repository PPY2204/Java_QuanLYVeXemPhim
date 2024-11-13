package entity;

public class PhongChieu {
    private String maPhongChieu;
    private String tenPhongChieu;
    private int sucChua;
    private boolean trangThaiPhong;

    public PhongChieu(String maPhongChieu, String tenPhongChieu, int sucChua, boolean trangThaiPhong) {
        this.maPhongChieu = maPhongChieu;
        this.tenPhongChieu = tenPhongChieu;
        this.sucChua = sucChua;
        this.trangThaiPhong = trangThaiPhong;
    }
    
    public PhongChieu(String tenPhongChieu) {
		super();
		this.tenPhongChieu = tenPhongChieu;
	}

	// Getters and Setters
    public String getMaPhongChieu() {
        return maPhongChieu;
    }

    public void setMaPhongChieu(String maPhongChieu) {
        this.maPhongChieu = maPhongChieu;
    }

    public String getTenPhongChieu() {
        return tenPhongChieu;
    }

    public void setTenPhongChieu(String tenPhongChieu) {
        this.tenPhongChieu = tenPhongChieu;
    }

    public int getSucChua() {
        return sucChua;
    }

    public void setSucChua(int sucChua) {
        this.sucChua = sucChua;
    }

    public boolean isTrangThaiPhong() {
        return trangThaiPhong;
    }

    public void setTrangThaiPhong(boolean trangThaiPhong) {
        this.trangThaiPhong = trangThaiPhong;
    }

	@Override
	public String toString() {
		return tenPhongChieu;
	}
    
}
