package entity;

import java.sql.Timestamp;

public class SuatChieu {
    private String maSuatChieu; // Mã suất chiếu
    private Phim phim; // Thông tin phim
    private PhongChieu phongChieu; // Thông tin phòng chiếu
    private Timestamp thoiGianKhoiChieu; // Thời gian bắt đầu chiếu
    private Timestamp thoiGianKetThuc; // Thời gian kết thúc chiếu
    private boolean trangThai; // Trạng thái suất chiếu (đang diễn ra hay không)

    // Constructor
    public SuatChieu(String maSuatChieu, Phim phim, PhongChieu phongChieu, 
                     Timestamp thoiGianKhoiChieu, Timestamp thoiGianKetThuc, 
                     boolean trangThai) {
        this.maSuatChieu = maSuatChieu;
        this.phim = phim;
        this.phongChieu = phongChieu;
        this.thoiGianKhoiChieu = thoiGianKhoiChieu;
        this.thoiGianKetThuc = thoiGianKetThuc;
        this.trangThai = trangThai;
    }

    
    public SuatChieu(Phim phim, PhongChieu phongChieu, Timestamp thoiGianKhoiChieu) {
		super();
		this.phim = phim;
		this.phongChieu = phongChieu;
		this.thoiGianKhoiChieu = thoiGianKhoiChieu;
	}



	// Getter và Setter cho maSuatChieu
    public String getMaSuatChieu() {
        return maSuatChieu;
    }

    public void setMaSuatChieu(String maSuatChieu) {
        this.maSuatChieu = maSuatChieu;
    }

    // Getter và Setter cho phim
    public Phim getPhim() {
        return phim;
    }

    public void setPhim(Phim phim) {
        this.phim = phim;
    }

    // Getter và Setter cho phongChieu
    public PhongChieu getPhongChieu() {
        return phongChieu;
    }

    public void setPhongChieu(PhongChieu phongChieu) {
        this.phongChieu = phongChieu;
    }

    // Getter và Setter cho thoiGianKhoiChieu
    public Timestamp getThoiGianKhoiChieu() {
        return thoiGianKhoiChieu;
    }

    public void setThoiGianKhoiChieu(Timestamp thoiGianKhoiChieu) {
        this.thoiGianKhoiChieu = thoiGianKhoiChieu;
    }

    // Getter và Setter cho thoiGianKetThuc
    public Timestamp getThoiGianKetThuc() {
        return thoiGianKetThuc;
    }

    public void setThoiGianKetThuc(Timestamp thoiGianKetThuc) {
        this.thoiGianKetThuc = thoiGianKetThuc;
    }

    // Getter và Setter cho trangThai
    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    // Phương thức để in thông tin suất chiếu (tuỳ chọn)
    @Override
    public String toString() {
        return "SuatChieu{" +
                "maSuatChieu='" + maSuatChieu + '\'' +
                ", phim=" + phim +
                ", phongChieu=" + phongChieu +
                ", thoiGianKhoiChieu=" + thoiGianKhoiChieu +
                ", thoiGianKetThuc=" + thoiGianKetThuc +
                ", trangThai=" + trangThai +
                '}';
    }

}
