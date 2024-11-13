Create database QuanLyVeXemPhim_DEMO
go

USE QuanLyVeXemPhim_DEMO
go
CREATE TABLE ChucVu (
    maChucVu VARCHAR(10) PRIMARY KEY,
    tenChucVu NVARCHAR(50) NOT NULL
);

CREATE TABLE NhanVien (
    maNV VARCHAR(10) PRIMARY KEY,
    tenNV NVARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    sdt VARCHAR(15) NOT NULL,
    ngaySinh DATE NOT NULL,
    gioiTinh BIT NOT NULL,
    cccd VARCHAR(55) NOT NULL,
    vaiTro VARCHAR(10) NOT NULL,
    FOREIGN KEY (vaiTro) REFERENCES ChucVu(maChucVu)
);

CREATE TABLE TaiKhoan (
    tenTaiKhoan VARCHAR(10) PRIMARY KEY,
    matKhau NVARCHAR(50) NOT NULL,
    FOREIGN KEY (tenTaiKhoan) REFERENCES NhanVien(maNV)
);

CREATE TABLE DiemTichLuy (
    maDTL VARCHAR(10) PRIMARY KEY,
    diemHienTai FLOAT NOT NULL 
);

CREATE TABLE KhachHang (
    maKH VARCHAR(10) PRIMARY KEY,
    tenKH NVARCHAR(50) NOT NULL,
    sdt VARCHAR(15)  NOT NULL,
    email NVARCHAR(100),
    diemTichLuy VARCHAR(10),
    FOREIGN KEY (diemTichLuy) REFERENCES DiemTichLuy(maDTL)
);

CREATE TABLE ChuongTrinhKhuyenMai (
    maCTKM VARCHAR(10) PRIMARY KEY,
    tenCTKM NVARCHAR(100),
    thoiGianBatDau DATE,
    thoiGianKetThuc DATE
);

CREATE TABLE Thue (
    maThue VARCHAR(10) PRIMARY KEY,
    loaiThue NVARCHAR(50) NOT NULL,
	tyLeThue REAL NOT NULL
);

CREATE TABLE Ve (
    maVe VARCHAR(10) PRIMARY KEY,
    khachHang VARCHAR(10),
	nhanVien VARCHAR(10),
    ngayLap DATE  NOT NULL,
    hinhThucThanhToan NVARCHAR(30)  NOT NULL,
	thue VARCHAR(10) NOT NULL,
    FOREIGN KEY (khachHang) REFERENCES KhachHang(maKH),
	FOREIGN KEY (nhanVien) REFERENCES NhanVien(maNV),
	FOREIGN KEY (thue) REFERENCES Thue(maThue)
);

CREATE TABLE ChiTietKhuyenMai (
    ve VARCHAR(10) PRIMARY KEY,
    chuongTrinhKM VARCHAR(10),
    phanTramKM FLOAT NOT NULL,
    moTa NVARCHAR(255),
	FOREIGN KEY (ve) REFERENCES Ve(maVe),
    FOREIGN KEY (chuongTrinhKM) REFERENCES ChuongTrinhKhuyenMai(maCTKM)
);


CREATE TABLE NhaSanXuat (
    maNSX VARCHAR(10) PRIMARY KEY,
    tenNSX NVARCHAR(30) NOT NULL
);

CREATE TABLE DanhMucPhim (
    maDanhMuc VARCHAR(10) PRIMARY KEY,
    tenDanhMuc NVARCHAR(30) NOT NULL
);

CREATE TABLE NuocSanXuat (
    maNuocSX VARCHAR(10) PRIMARY KEY,
    tenNuocSX NVARCHAR(30) NOT NULL
);

CREATE TABLE Phim (
    maPhim VARCHAR(10) PRIMARY KEY,
    tenPhim NVARCHAR(50) NOT NULL,
    ngayKhoiChieu DATE NOT NULL,
    daoDien NVARCHAR(30) NOT NULL,
	danhMuc VARCHAR(10) NOT NULL,
	dienVien NVARCHAR(30) NOT NULL,
	ngayPhatHanh DATE NOT NULL,
	ngonNgu NVARCHAR(20) NOT NULL,
	moTa NVARCHAR(255),
	hinhAnh NVARCHAR(MAX),
	dinhDang NVARCHAR(20),
	tinhTrang BIT  NOT NULL,
    doTuoiGioiHan INT NOT NULL,
    quocGia VARCHAR(10) NOT NULL,
    thoiLuong INT NOT NULL,
    nhaSX VARCHAR(10) NOT NULL,
    FOREIGN KEY (nhaSX) REFERENCES NhaSanXuat(maNSX),
	FOREIGN KEY (danhMuc) REFERENCES DanhMucPhim(maDanhMuc),
	FOREIGN KEY (quocGia) REFERENCES NuocSanXuat(maNuocSX)
);

CREATE TABLE PhongChieu (
    maPhongChieu VARCHAR(10) PRIMARY KEY,
	tenPhongChieu NVARCHAR(30) NOT NULL,
    sucChua INT NOT NULL,
    trangThai BIT NOT NULL
);


CREATE TABLE SuatChieu (
	maSuatChieu VARCHAR(10) PRIMARY KEY,
    phim VARCHAR(10) NOT NULL,
	phongChieu VARCHAR(10) NOT NULL,
    thoiGianKhoiChieu DATETIME,
    thoiGianKetThuc DATETIME,
	trangThai BIT NOT NULL
	FOREIGN KEY (phim) REFERENCES Phim(maPhim),
	FOREIGN KEY (phongChieu) REFERENCES PhongChieu(maPhongChieu)
);


CREATE TABLE ChiTietVe (
    ve VARCHAR(10) NOT NULL,
    viTriGhe VARCHAR(10) NOT NULL,
    giaVe FLOAT NOT NULL,
	suatChieu VARCHAR(10) NOT NULL,
	loaiVe NVARCHAR(30) NOT NULL,
    FOREIGN KEY (ve) REFERENCES Ve(maVe),
	FOREIGN KEY (suatChieu) REFERENCES SuatChieu(maSuatChieu)
);

-- Bảng ChucVu
INSERT INTO ChucVu (maChucVu, tenChucVu) VALUES
('1', N'Nhân viên bán vé'),
('2', N'Nhân viên quản lí'),
('0', N'Nhân viên quản trị viên');


-- Bảng NhanVien
INSERT INTO NhanVien (maNV, tenNV, email, sdt, ngaySinh, gioiTinh, cccd, vaiTro) VALUES
('NV001', N'Nguyễn Văn Đủ', 'nguyenvana@gmail.com', '0123456789', '1990-05-20', 1, '012345678901', '1'),
('NV002', N'Lê Thị Bình', 'lethib@gmail.com', '0987654321', '1992-08-15', 0, '012345678902', '1'),
('NV003', N'Phạm Văn Chiêu', 'phamvanc@gmail.com', '0912345678', '1988-12-01', 1, '012345678903', '1'),
('NV004', N'Trần Văn Đức', 'tranvand@gmail.com', '0923456789', '1995-11-25', 1, '012345678904', '1'),
('NV005', N'Hoàng Thị Mỹ', 'hoangthie@gmail.com', '0934567890', '1993-03-05', 0, '012345678905', '1'),
('NV006', N'Đỗ Văn Tùng', 'dovanf@gmail.com', '0945678901', '1997-07-18', 1, '012345678906', '1'),
('QL007', N'Ngô Văn Gia', 'ngovang@gmail.com', '0956789012', '1985-09-09', 1, '012345678907', '2'),
('QL008', N'Vũ Thị Thanh', 'vuthih@gmail.com', '0967890123', '1996-10-10', 0, '012345678908', '2'),
('QL009', N'Bùi Văn Thảo', 'buivani@gmail.com', '0978901234', '1998-04-14', 1, '012345678909', '2'),
('ADMIN', N'Trịnh Thị An', 'trinhtij@gmail.com', '0989012345', '1991-01-22', 0, '012345678910', '0');


-- Bảng TaiKhoan
INSERT INTO TaiKhoan (tenTaiKhoan, matKhau) VALUES
('NV001', '123'),
('NV002', '123'),
('NV003', '123'),
('NV004', '123'),
('NV005', '123'),
('NV006', '123'),
('QL007', '369'),
('QL008', '369'),
('QL009', '369'),
('ADMIN', 'admin');

-- Bảng DiemTichLuy
INSERT INTO DiemTichLuy (maDTL, diemHienTai) VALUES
('DTL001', 100.0),
('DTL002', 150.0),
('DTL003', 200.0),
('DTL004', 250.0),
('DTL005', 300.0),
('DTL006', 350.0),
('DTL007', 400.0),
('DTL008', 450.0),
('DTL009', 500.0),
('DTL010', 550.0);


-- Bảng KhachHang
INSERT INTO KhachHang (maKH, tenKH, sdt, email, diemTichLuy) VALUES
('KH001', N'Trần Văn K', '0988765432', N'emailcuatoi@gmail.com', 'DTL001'),
('KH002', N'Lê Thị L', '0978765432', N'emailcuatoi@gmail.com', 'DTL002'),
('KH003', N'Nguyễn Văn M', '0968765432', N'emailcuatoi@gmail.com', 'DTL003'),
('KH004', N'Hoàng Thị N', '0958765432', N'emailcuatoi@gmail.com', 'DTL004'),
('KH005', N'Phạm Văn O', '0948765432', N'emailcuatoi@gmail.com', 'DTL005'),
('KH006', N'Đỗ Thị P', '0938765432', N'emailcuatoi@gmail.com', 'DTL006'),
('KH007', N'Trương Văn Q', '0928765432', N'emailcuatoi@gmail.com', 'DTL007'),
('KH008', N'Nguyễn Thị R', '0918765432', N'emailcuatoi@gmail.com', 'DTL008'),
('KH009', N'Bùi Văn S', '0908765432', N'emailcuatoi@gmail.com', 'DTL009'),
('KH010', N'Phan Thị T', '0898765432', N'emailcuatoi@gmail.com', 'DTL010');


-- Bảng ChuongTrinhKhuyenMai
INSERT INTO ChuongTrinhKhuyenMai (maCTKM, tenCTKM, thoiGianBatDau, thoiGianKetThuc) VALUES
('CTKM001', N'Giảm giá 20%', '2024-10-01', '2024-10-31'),
('CTKM002', N'Mua 1 tặng 1', '2024-11-01', '2024-11-15'),
('CTKM003', N'Giảm giá 30%', '2024-11-16', '2024-11-30'),
('CTKM004', N'Giảm giá vé vào cửa', '2024-12-01', '2024-12-15'),
('CTKM005', N'Giảm giá cho sinh viên', '2024-12-16', '2025-01-01'),
('CTKM006', N'Giảm giá nhóm', '2025-01-02', '2025-01-15'),
('CTKM007', N'Đêm VIP', '2025-01-16', '2025-01-31'),
('CTKM008', N'Thứ 5 vui vẻ', '2025-02-01', '2025-02-28'),
('CTKM009', N'Thẻ thành viên', '2025-03-01', '2025-03-31'),
('CTKM010', N'Khuyến mãi lễ hội', '2025-04-01', '2025-04-30');


-- Bảng Thue
INSERT INTO Thue (maThue, loaiThue, tyLeThue) VALUES
('TH001', N'Thuế VAT', 10.0),
('TH002', N'Thuế tiêu thụ đặc biệt', 15.0),
('TH003', N'Thuế suất bán hàng', 5.0),
('TH004', N'Thuế dịch vụ', 8.0),
('TH005', N'Thuế bảo vệ môi trường', 12.0),
('TH006', N'Thuế chuyển nhượng', 20.0),
('TH007', N'Thuế nhập khẩu', 25.0),
('TH008', N'Thuế xuất khẩu', 10.0),
('TH009', N'Thuế khấu trừ', 30.0),
('TH010', N'Thuế tài nguyên', 18.0);


-- Bảng Ve
INSERT INTO Ve (maVe, khachHang, nhanVien, ngayLap, hinhThucThanhToan, thue) VALUES
('VE001', 'KH001', 'NV001', '2024-10-15', N'Tiền mặt', 'TH001'),
('VE002', 'KH002', 'NV002', '2024-10-16', N'Thẻ tín dụng', 'TH002'),
('VE003', 'KH003', 'NV001', '2024-10-17', N'Chuyển khoản', 'TH003'),
('VE004', 'KH004', 'NV002', '2024-10-18', N'Tiền mặt', 'TH004'),
('VE005', 'KH005', 'NV001', '2024-10-19', N'Thẻ ghi nợ', 'TH005'),
('VE006', 'KH006', 'NV002', '2024-10-20', N'Tiền mặt', 'TH006'),
('VE007', 'KH007', 'NV001', '2024-10-21', N'Chuyển khoản', 'TH007'),
('VE008', 'KH008', 'NV002', '2024-10-22', N'Tiền mặt', 'TH008'),
('VE009', 'KH009', 'NV001', '2024-10-23', N'Thẻ tín dụng', 'TH009'),
('VE010', 'KH010', 'NV002', '2024-10-24', N'Tiền mặt', 'TH010');


-- Bảng ChiTietKhuyenMai
INSERT INTO ChiTietKhuyenMai (ve, chuongTrinhKM, phanTramKM, moTa) VALUES
('VE001', 'CTKM001', 20.0, N'Giảm giá 20% khi mua vé trong tháng 10'),
('VE002', 'CTKM002', 50.0, N'Mua 1 tặng 1 cho vé thứ 2'),
('VE003', 'CTKM003', 30.0, N'Giảm giá 30% trong tuần đầu tháng 11'),
('VE004', 'CTKM004', 15.0, N'Giảm giá cho vé vào cửa buổi tối'),
('VE005', 'CTKM005', 10.0, N'Giảm giá cho sinh viên có thẻ sinh viên'),
('VE006', 'CTKM006', 25.0, N'Giảm giá khi mua theo nhóm'),
('VE007', 'CTKM007', 40.0, N'Đêm VIP giảm 40%'),
('VE008', 'CTKM008', 10.0, N'Khuyến mãi vào thứ 5 hàng tuần'),
('VE009', 'CTKM009', 20.0, N'Khuyến mãi cho khách hàng có thẻ thành viên'),
('VE010', 'CTKM010', 15.0, N'Khuyến mãi mùa lễ hội');


-- Bảng NhaSanXuat
INSERT INTO NhaSanXuat (maNSX, tenNSX) VALUES
('NSX001', 'Marvel Studios'),
('NSX002', 'Warner Bros'),
('NSX003', 'Pixar Animation Studios'),
('NSX004', 'Universal Pictures'),
('NSX005', '20th Century Studios'),
('NSX006', 'Sony Pictures'),
('NSX007', 'Paramount Pictures'),
('NSX008', 'Walt Disney Studios'),
('NSX009', 'Lionsgate'),
('NSX010', 'Columbia Pictures');


-- Bảng DanhMucPhim
INSERT INTO DanhMucPhim (maDanhMuc, tenDanhMuc) VALUES
('DM001', N'Hành động'),
('DM002', N'Hài hước'),
('DM003', N'Kinh dị'),
('DM004', N'Tâm lý'),
('DM005', N'Khoa học viễn tưởng'),
('DM006', N'Hoạt hình'),
('DM007', N'Phiêu lưu'),
('DM008', N'Kỳ ảo'),
('DM009', N'Thể thao'),
('DM010', N'Tài liệu');


-- Bảng NuocSanXuat
INSERT INTO NuocSanXuat (maNuocSX, tenNuocSX) VALUES
('QG001', N'Hoa Kỳ'),
('QG002', N'Anh'),
('QG003', N'Pháp'),
('QG004', N'Nhật Bản'),
('QG005', N'Hàn Quốc'),
('QG006', N'Ấn Độ'),
('QG007', N'Trung Quốc'),
('QG008', N'Canada'),
('QG009', N'Brazil'),
('QG010', N'Australia');


-- Bảng Phim
INSERT INTO Phim (maPhim, tenPhim, ngayKhoiChieu, daoDien, danhMuc, dienVien, ngayPhatHanh, ngonNgu, moTa, hinhAnh, dinhDang, tinhTrang, doTuoiGioiHan, quocGia, thoiLuong, nhaSX) VALUES
('PH001', 'Avengers: Endgame', '2019-04-26', 'Anthony Russo', 'DM001', 'Robert Downey Jr.', '2019-04-26', N'Tiếng Anh', N'Siêu anh hùng cứu thế giới', 'avengers.jpg', '3D', 1, 13, 'QG001', 181, 'NSX001'),
('PH002', 'Joker', '2019-10-04', 'Todd Phillips', 'DM004', 'Joaquin Phoenix', '2019-10-04', N'Tiếng Anh', N'Cuộc đời của Joker', 'joker.jpg', '2D', 1, 18, 'QG002', 122, 'NSX002'),
('PH003', 'Toy Story 4', '2019-06-21', 'Josh Cooley', 'DM006', 'Tom Hanks', '2019-06-21', N'Tiếng Anh', N'Cuộc phiêu lưu của đồ chơi', 'toystory.jpg', '3D', 1, 0, 'QG001', 100, 'NSX003'),
('PH004', 'Parasite', '2019-05-30', 'Bong Joon-ho', 'DM004', 'Song Kang-ho', '2019-05-30', N'Tiếng Hàn', N'Cuộc sống của tầng lớp dưới', 'parasite.jpg', '2D', 1, 18, 'QG005', 132, 'NSX005'),
('PH005', 'Interstellar', '2014-11-07', 'Christopher Nolan', 'DM005', 'Matthew McConaughey', '2014-11-07', N'Tiếng Anh', 'Khám phá không gian', 'interstellar.jpg', 'IMAX', 1, 13, 'QG001', 169, 'NSX004'),
('PH006', 'The Dark Knight', '2008-07-18', 'Christopher Nolan', 'DM001', 'Christian Bale', '2008-07-18', N'Tiếng Anh', 'Batman đối đầu Joker', 'darkknight.jpg', 'IMAX', 1, 13, 'QG001', 152, 'NSX002'),
('PH007', 'Coco', '2017-11-22', 'Lee Unkrich', 'DM006', 'Anthony Gonzalez', '2017-11-22', N'Tiếng Anh', N'Hành trình đến thế giới người chết', 'coco.jpg', '3D', 1, 0, 'QG001', 105, 'NSX003'),
('PH008', 'The Conjuring', '2013-07-19', 'James Wan', 'DM003', 'Vera Farmiga', '2013-07-19', N'Tiếng Anh', N'Ma ám và pháp sư', 'conjuring.jpg', '2D', 1, 18, 'QG001', 112, 'NSX006'),
('PH009', 'Frozen', '2013-11-27', 'Chris Buck', 'DM006', 'Kristen Bell', '2013-11-27', N'Tiếng Anh', N'Cuộc phiêu lưu của Elsa', 'frozen.jpg', '3D', 1, 0, 'QG001', 102, 'NSX008'),
('PH010', 'Dangal', '2016-12-21', 'Nitesh Tiwari', 'DM009', 'Aamir Khan', '2016-12-21', N'Tiếng Hindi', N'Câu chuyện của một đô vật', 'dangal.jpg', '2D', 1, 13, 'QG006', 161, 'NSX009');


-- Bảng PhongChieu
INSERT INTO PhongChieu (maPhongChieu, tenPhongChieu, sucChua, trangThai) VALUES
('PC001', N'Phòng 1', 200, 1),
('PC002', N'Phòng 2', 150, 1),
('PC003', N'Phòng 3', 100, 1),
('PC004', N'Phòng 4', 50, 1),
('PC005', N'Phòng 5', 80, 1),
('PC006', N'Phòng 6', 60, 1),
('PC007', N'Phòng 7', 120, 1),
('PC008', N'Phòng 8', 90, 1),
('PC009', N'Phòng 9', 110, 1),
('PC010', N'Phòng 10', 130, 1);


-- Bảng SuatChieu
INSERT INTO SuatChieu (maSuatChieu, phim, phongChieu, thoiGianKhoiChieu, thoiGianKetThuc, trangThai) VALUES
('SC001', 'PH001', 'PC001', '2024-10-25 18:00:00', '2024-10-25 20:30:00', 1),
('SC002', 'PH002', 'PC002', '2024-10-25 21:00:00', '2024-10-25 23:00:00', 1),
('SC003', 'PH003', 'PC003', '2024-10-26 10:00:00', '2024-10-26 12:00:00', 1),
('SC004', 'PH004', 'PC004', '2024-10-26 15:00:00', '2024-10-26 17:00:00', 1),
('SC005', 'PH005', 'PC005', '2024-10-26 18:00:00', '2024-10-26 21:00:00', 1),
('SC006', 'PH006', 'PC006', '2024-10-27 12:00:00', '2024-10-27 14:00:00', 1),
('SC007', 'PH007', 'PC007', '2024-10-27 15:30:00', '2024-10-27 17:30:00', 1),
('SC008', 'PH008', 'PC008', '2024-10-28 11:00:00', '2024-10-28 13:00:00', 1),
('SC009', 'PH009', 'PC009', '2024-10-28 20:00:00', '2024-10-28 22:30:00', 1),
('SC010', 'PH010', 'PC010', '2024-10-29 09:00:00', '2024-10-29 11:30:00', 1);


-- Bảng ChiTietVe
INSERT INTO ChiTietVe (ve, viTriGhe, giaVe, suatChieu, loaiVe) VALUES
('VE001', 'A1', 100000, 'SC001', N'Thường'),
('VE002', 'B2', 120000, 'SC002', N'VIP'),
('VE003', 'C3', 90000,  'SC003', N'Thường'),
('VE004', 'D4', 110000, 'SC004', N'Thường'),
('VE005', 'E5', 150000, 'SC005', N'VIP'),
('VE006', 'F6', 130000, 'SC006', N'Thường'),
('VE007', 'G7', 140000, 'SC007', N'Thường'),
('VE008', 'H8', 160000, 'SC008', N'VIP'),
('VE009', 'I9', 105000, 'SC009', N'Thường'),
('VE010', 'J10', 115000, 'SC010', N'Thường');


