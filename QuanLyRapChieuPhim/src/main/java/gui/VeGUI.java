package gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import entity.ChiTietKhuyenMai;
import entity.ChiTietVe;
import entity.Ve;
import dao.ChiTietVeDAO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class VeGUI extends JPanel implements ActionListener,MouseListener{
	private JPanel boxtren;
    private JLabel titlelb;
    private JPanel box1, box2, box3, box4, box5, box6, box7, box8, box9, box10, box11, box12, box13;
    private JPanel hang1,hang2,hang3,hang4,hang5;
    private JPanel boxduoi;
    private JPanel boxnut;
    private JButton thongKeBt, xoatrangbt, timbt;
    private JTextField timfi;
    private DefaultTableModel tablemodel;
    private JTable table;
	private JPanel boxtraiphai;
	private JPanel boxchatraiphai;
	private JTextField maVefi,ngayLapfi,loaiVefi,khachHangfi,nhanVienfi,phongChieufi,phimfi,viTriGhefi,suatChieufi,tenCTKMfi,mathuefi,giaVefi,hinhThucThanhToanfi;
	private ThongKeGUI thongKeGUI;
	private JSpinner startDateSpinner;
	private ChiTietVeDAO chitietvedao;
	private JTextField thoiGianLap;
	private JLabel phuongthuclb;

    public VeGUI() {
        setLayout(new BorderLayout());
        // Khởi tạo ThongKeGUI
        thongKeGUI = new ThongKeGUI();
        chitietvedao = new ChiTietVeDAO();
        // Khởi tạo boxtren và đặt layout theo trục Y
        boxtren = new JPanel();
        boxtren.setLayout(new BoxLayout(boxtren, BoxLayout.Y_AXIS));
        boxtren.setBorder(new LineBorder(Color.BLACK, 2, true));

        // Khởi tạo boxduoi và đặt layout
        boxduoi = new JPanel();
        boxduoi.setLayout(new BorderLayout());
        boxduoi.setBorder(new LineBorder(Color.BLACK, 2, true));

        // Tạo tiêu đề
        titlelb = new JLabel("THÔNG TIN VÉ");
        titlelb.setFont(new Font("Roboto", Font.BOLD, 30));
        titlelb.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Khởi tạo các box nhập liệu
        box1 = createInputBox("Mã vé:", maVefi = new JTextField(15));
        box2 = createInputBox("Thời gian lập:", thoiGianLap = new  JTextField(15));
        box3 = createInputBox("Loại vé:", loaiVefi = new JTextField(15));
        box4 = createInputBox("Khách hàng:", khachHangfi = new JTextField(15));
        box5 = createInputBox("Nhân viên:", nhanVienfi = new JTextField(15));
        box6 = createInputBox("Phòng chiếu:", phongChieufi = new JTextField(15));
        box7 =(createInputBox("Phim:", phimfi = new JTextField(15)));
        box8= (createInputBox("Mã số ghế:", viTriGhefi = new JTextField(15)));
        box9=(createInputBox("Suất chiếu:", suatChieufi = new JTextField(15)));
        box10=(createInputBox("Khuyến mãi:", tenCTKMfi = new JTextField(15)));
        box11=(createInputBox("Thuế:", mathuefi = new JTextField(15)));
        box12=(createInputBox("Giá vé:", giaVefi = new JTextField(15)));
        box13 = new JPanel();
        phuongthuclb = new JLabel("Phương thức thanh toán:");
        phuongthuclb.setPreferredSize(new Dimension(150, 30)); // Đặt kích thước cố định cho nhãn
        box13.add(phuongthuclb);
        box13.add(Box.createHorizontalStrut(10)); // Khoảng cách
        hinhThucThanhToanfi = new JTextField(60);
        hinhThucThanhToanfi.setPreferredSize(new Dimension(600,30));
        hinhThucThanhToanfi.setMaximumSize(new Dimension(600,30));
        hinhThucThanhToanfi.setMinimumSize(new Dimension(600,30));
        box13.add(hinhThucThanhToanfi);

        // Khởi tạo hàng
        hang1 = new JPanel();
        hang1.setLayout(new BoxLayout(hang1, BoxLayout.X_AXIS));
        hang2 = new JPanel();
        hang2.setLayout(new BoxLayout(hang2, BoxLayout.X_AXIS));
        hang3 = new JPanel();
        hang3.setLayout(new BoxLayout(hang3, BoxLayout.X_AXIS));
        hang4 = new JPanel();
        hang4.setLayout(new BoxLayout(hang4, BoxLayout.X_AXIS));
        hang5 = new JPanel();
        hang5.setLayout(new BoxLayout(hang5, BoxLayout.X_AXIS));
        // Thêm các box vào hàng
        hang1.add(box1);
        hang1.add(Box.createHorizontalStrut(15));
        hang1.add(box2);
        hang1.add(Box.createHorizontalStrut(15));
        hang1.add(box3);
        
        hang2.add(box4);
        hang2.add(Box.createHorizontalStrut(15));
        hang2.add(box5);
        hang2.add(Box.createHorizontalStrut(15));
        hang2.add(box6);        

        hang3.add(box7);
        hang3.add(Box.createHorizontalStrut(15));
        hang3.add(box8);
        hang3.add(Box.createHorizontalStrut(15));
        hang3.add(box9);        

        hang4.add(box10);
        hang4.add(Box.createHorizontalStrut(15));
        hang4.add(box11);
        hang4.add(Box.createHorizontalStrut(15));
        hang4.add(box12);
        
        hang5.add(box13);
        

        hang5.add(Box.createVerticalStrut(10));


        // Khởi tạo boxtraiphai và thêm các hàng
        boxtraiphai = new JPanel();
        boxtraiphai.setLayout(new BoxLayout(boxtraiphai, BoxLayout.Y_AXIS));
        boxtraiphai.add(Box.createVerticalStrut(10));
        boxtraiphai.add(hang1);
        boxtraiphai.add(Box.createVerticalStrut(10));
        boxtraiphai.add(hang2);
        boxtraiphai.add(Box.createVerticalStrut(10));
        boxtraiphai.add(hang3);
        boxtraiphai.add(Box.createVerticalStrut(10));
        boxtraiphai.add(hang4);
        boxtraiphai.add(Box.createVerticalStrut(10));
        boxtraiphai.add(hang5);
        
        //tạo khoảng cách trại phải cho boxtraiphai
        boxchatraiphai = new JPanel();
        boxchatraiphai.setLayout(new BoxLayout(boxchatraiphai,BoxLayout.X_AXIS));
        boxchatraiphai.add(Box.createHorizontalStrut(10));
        boxchatraiphai.add(boxtraiphai);
        boxchatraiphai.add(Box.createHorizontalStrut(10));
        // Thêm thành phần vào boxtren
        boxtren.add(titlelb);
        boxtren.add(Box.createVerticalStrut(10));
        boxtren.add(boxchatraiphai);
        boxtren.add(Box.createVerticalStrut(20));

        // Khởi tạo bảng
        String[] colname = {"Mã vé","Thời gian lập","Loại vé","Khách hàng","Nhân viên","Phòng chiếu","Phim","Mã số ghế","Suất chiếu","Khuyến mãi","Thuế","Giá vé","Phương thức thanh toán"};
        tablemodel = new DefaultTableModel(colname, 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        table = new JTable(tablemodel);
        table.addMouseListener(this);
        // Khởi tạo panel chứa các nút (boxnut)
        boxnut = new JPanel();
        boxnut.setLayout(new BoxLayout(boxnut, BoxLayout.X_AXIS));
        boxnut.setBorder(new LineBorder(Color.BLACK, 2, true));

        // Thêm các nút vào boxnut
        thongKeBt = createButton("Thống kê", 100, 30);
        xoatrangbt = createButton("Xóa Trắng", 100, 30);
        timfi = new JTextField(10);timfi.setPreferredSize(new Dimension(80, 30));
        timfi.setMaximumSize(new Dimension(400, 25));
        timfi.setMinimumSize(new Dimension(400, 25));
        timbt = createButton("Tìm", 80, 30);
        
        //thêm lệnh nghe sự kiện cho các nút
        thongKeBt.addActionListener(this);
        xoatrangbt.addActionListener(this);
        timbt.addActionListener(this);

        boxnut.add(Box.createHorizontalStrut(100));
        boxnut.add(thongKeBt);
        boxnut.add(Box.createHorizontalStrut(10));
        boxnut.add(xoatrangbt);
        boxnut.add(Box.createHorizontalStrut(10));
        boxnut.add(timfi);
        boxnut.add(Box.createHorizontalStrut(10));
        boxnut.add(timbt);
        boxnut.add(Box.createHorizontalStrut(70));

        // Thêm boxnut vào boxduoi và bảng vào vị trí CENTER
        boxduoi.add(boxnut, BorderLayout.NORTH);
        boxduoi.add(new JScrollPane(table), BorderLayout.CENTER); // Bảng trong JScrollPane

        // Thêm các panel vào layout chính
        add(boxtren, BorderLayout.NORTH);
        add(boxduoi, BorderLayout.CENTER);

        // Thiết lập kích thước cho bảng
        table.setPreferredScrollableViewportSize(new Dimension(800, 300));
        loadDataChiTietVe();
    }

    private void loadDataChiTietVe() {
        ArrayList<ChiTietVe> list = chitietvedao.getAllChiTietVe(); // Lấy danh sách chi tiết vé từ cơ sở dữ liệu
        
        // In ra số lượng chi tiết vé
        System.out.println("Số lượng chi tiết vé lấy được: " + (list != null ? list.size() : "null"));

        tablemodel.setRowCount(0); // Xóa các hàng cũ trong tableModel

        if (list != null && !list.isEmpty()) { // Kiểm tra xem danh sách có null không
            for (ChiTietVe ctv : list) {
                // Lấy thông tin vé
                String maVe = ctv.getVe().getMaVe();
                // Định dạng ngày tháng
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String ngayLap = dateFormat.format(ctv.getVe().getNgayLap());
                
                //ten khach hàng
                String tenKH = ctv.getVe().getKhachHang().getTenKH();
                //tên nhân viên
                String tenNV = ctv.getVe().getNhanVien().getTenNV();
                String tenPhongChieu = ctv.getSuatChieu().getPhongChieu().getTenPhongChieu();
                String tenPhim = ctv.getSuatChieu().getPhim().getTenPhim();
                System.out.println(tenPhim);
                String viTriGhe = ctv.getViTriGhe();
                String loaiVe = ctv.getLoaiVe();
                double giaVe = ctv.getGiaVe();      
                String phuongThucThanhToan = ctv.getVe().getPhuongThucThanhToan();
                String thoiGianKhoiChieu = dateFormat.format(ctv.getSuatChieu().getThoiGianKhoiChieu());
                String maThue = ctv.getVe().getThue().getMaThue();
                //Double khuyenMai
                String makhuyenMai = ctv.getVe().getCtkm().getChuongTrinhKM().getMaCTKM();

                // Thêm hàng vào tableModel
                tablemodel.addRow(new Object[]{
                    maVe,
                    ngayLap,
                    loaiVe,
                    tenKH,
                    tenNV,
                    tenPhongChieu,
                    tenPhim,
                    viTriGhe,
                    thoiGianKhoiChieu,
                    makhuyenMai,
                    maThue,
                    giaVe,
                    phuongThucThanhToan
                });
            }
        }
    }


	// Hàm tạo một box với nhãn và ô nhập
    private JPanel createInputBox(String labelText, JComponent inputComponent) {
        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.X_AXIS));
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(80, 30)); // Đặt kích thước cố định cho nhãn
        box.add(label);
        box.add(Box.createHorizontalStrut(10)); // Khoảng cách
        box.add(inputComponent);
        return box;
    }

    // Hàm tạo button
    private JButton createButton(String text, int width, int height) {
        JButton button = new JButton(text);
        button.setBackground(Color.decode("#35a06c"));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(width, height));
        button.setMaximumSize(new Dimension(width, height));
        button.setMinimumSize(new Dimension(width, height));
        return button;
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(thongKeBt)) {
			removeAll();
	        // Thêm panel mới vào boxcenter
			add(thongKeGUI, BorderLayout.CENTER);
	        revalidate(); 
	        repaint(); 
		}
		if(e.getSource().equals(xoatrangbt)) {
			clearFields(); 
		}
		if(e.getSource().equals(timbt)) {
			String ma = timfi.getText();
			searchTicketByMaVe(ma);
		}
	}


	public void searchTicketByMaVe(String maVeToSearch) {
	    boolean isFound = chitietvedao.searchByMaVe(maVeToSearch);  // Tìm kiếm theo mã vé

	    if (isFound) {  // Nếu tìm thấy vé
	        // Lặp qua tất cả các dòng trong bảng để tìm dòng có mã vé khớp
	        for (int row = 0; row < table.getRowCount(); row++) {
	            String maVe = table.getValueAt(row, 0).toString();  // Lấy mã vé của dòng
	            if (maVe.equals(maVeToSearch)) {
	                table.setRowSelectionInterval(row, row);  // Chọn dòng này
	                table.setSelectionBackground(Color.YELLOW);  // Tô màu nền của dòng thành vàng
	                
	                // Cuộn đến vị trí dòng đó trong bảng
	                table.scrollRectToVisible(table.getCellRect(row, 0, true));
	                break;
	            }
	        }
	    } else {
	        JOptionPane.showMessageDialog(null, "Không tìm thấy vé với mã: " + maVeToSearch);  // Nếu không tìm thấy
	    }
	}


	// Hàm xóa trắng các JTextField
	public void clearFields() {
	    maVefi.setText("");
	    thoiGianLap.setText("");
	    loaiVefi.setText("");
	    khachHangfi.setText("");
	    nhanVienfi.setText("");
	    phongChieufi.setText("");
	    phimfi.setText("");
	    viTriGhefi.setText("");
	    suatChieufi.setText("");
	    tenCTKMfi.setText("");
	    mathuefi.setText("");
	    giaVefi.setText("");
	    hinhThucThanhToanfi.setText("");
	}


	@Override
	public void mouseClicked(MouseEvent e) {
	    // Lấy chỉ số dòng được chọn
	    int row = table.getSelectedRow();
	    
	    if (row != -1) { // Kiểm tra nếu có dòng được chọn
	        // Lấy dữ liệu từ các cột của dòng được chọn
	        String maVe = table.getValueAt(row, 0).toString();
	        String ngayLap = table.getValueAt(row, 1).toString();
	        String loaiVe = table.getValueAt(row, 2).toString();
	        String tenKH = table.getValueAt(row, 3).toString();
	        String tenNV = table.getValueAt(row, 4).toString();
	        String tenPhongChieu = table.getValueAt(row, 5).toString();
	        String tenPhim = table.getValueAt(row, 6).toString();
	        String viTriGhe = table.getValueAt(row, 7).toString();
	        String thoiGianKhoiChieu = table.getValueAt(row, 8).toString();
	        String makhuyenMai = table.getValueAt(row, 9).toString();
	        String maThue = table.getValueAt(row, 10).toString();
	        String giaVe = table.getValueAt(row, 11).toString();
	        String phuongThucThanhToan = table.getValueAt(row, 12).toString();
	        
	        // Đẩy dữ liệu lên các JTextField
	        maVefi.setText(maVe);
	        thoiGianLap.setText(ngayLap);
	        loaiVefi.setText(loaiVe);
	        khachHangfi.setText(tenKH);
	        nhanVienfi.setText(tenNV);
	        phongChieufi.setText(tenPhongChieu);
	        phimfi.setText(tenPhim);
	        viTriGhefi.setText(viTriGhe);
	        suatChieufi.setText(thoiGianKhoiChieu);
	        tenCTKMfi.setText(makhuyenMai);
	        mathuefi.setText(maThue);
	        giaVefi.setText(giaVe);
	        hinhThucThanhToanfi.setText(phuongThucThanhToan);
	      
	        
	        // Và tiếp tục với các JTextField khác...
	    }

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
