package gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import dao.ChiTietKhuyenMaiDAO;
import dao.ChucVuDAO;
import dao.VeDAO;
import entity.ChiTietKhuyenMai;
import entity.ChucVu;
import entity.Ve;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;

public class KhuyenMaiGUI extends JPanel implements MouseListener,ActionListener{
    // Khai báo các thành phần
    private JPanel boxtren;
    private JLabel titlelb;
    private JPanel box1, box2, box3, box4, box5, box6;
    private JPanel boxtrai, boxphai, boxtraiphai;
    private JTextField makmfi, tenkmfi, phantramfi, motafi;
    private JSpinner startDateSpinner;
    private JSpinner endDateSpinner;
    private JPanel boxduoi;
    private JPanel boxnut;
    private JButton thembt, xoabt, suabt, xoatrangbt, timbt;
    private JTextField timfi;
    private DefaultTableModel tablemodel;
    private JTable table;
	private ChiTietKhuyenMaiDAO chitietkhuyenmaidao;
	private JPanel box8;
	private JComboBox<String> vecombo;
	private JPanel box7;
	private VeDAO vedao;
	private JTextField diemfi;
	private JLabel malb;

    public KhuyenMaiGUI() {
        setLayout(new BorderLayout());
        chitietkhuyenmaidao = new ChiTietKhuyenMaiDAO();
        vedao = new VeDAO();
        // Khởi tạo các panel và layout
        boxtren = new JPanel();
        boxtren.setLayout(new BoxLayout(boxtren, BoxLayout.Y_AXIS));
        boxtren.setBorder(new LineBorder(Color.BLACK, 2, true));

        boxduoi = new JPanel();
        boxduoi.setLayout(new BorderLayout());
        boxduoi.setBorder(new LineBorder(Color.BLACK, 2, true));

        titlelb = new JLabel("THÔNG TIN KHUYẾN MÃI");
        titlelb.setFont(new Font("Roboto", Font.BOLD, 30));
        titlelb.setAlignmentX(Component.CENTER_ALIGNMENT);
     // Khởi tạo JSpinner cho thời gian bắt đầu
        startDateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor startDateEditor = new JSpinner.DateEditor(startDateSpinner, "dd-MM-yyyy");
        startDateSpinner.setEditor(startDateEditor);

        // Thiết lập thời gian hiện tại cho JSpinner
        Calendar startCal = Calendar.getInstance();
        startDateSpinner.setValue(startCal.getTime()); // Thiết lập thời gian hiện tại

        // Khởi tạo JSpinner cho thời gian kết thúc
        endDateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor endDateEditor = new JSpinner.DateEditor(endDateSpinner, "dd-MM-yyyy");
        endDateSpinner.setEditor(endDateEditor);



        // Khởi tạo các box con
        box1 = createInputBox("Mã khuyến mãi:", makmfi = new JTextField(15));
        box2 = createInputBox("Tên Khuyến Mãi:", tenkmfi = new JTextField(10));
        box3 = createInputBox("Thời gian bắt đầu:", startDateSpinner);
        box4 = createInputBox("Thời gian kết thúc:", endDateSpinner);
        box5 = createInputBox("Phần trăm giảm:", phantramfi = new JTextField(15));
        box6 = createInputBox("Mô tả:", motafi = new JTextField(15));
        //tạo combobox
        vecombo = new JComboBox<>();
        vecombo.setPreferredSize(new Dimension(150,30));
        vecombo.setEditable(true);
        vedao = new VeDAO();
        ArrayList<Ve> listCV;
		try {
			listCV = vedao.getVeKhongCoChiTietKhuyenMai();
			for (Ve cv : listCV) {
	        	vecombo.addItem(cv.getMaVe());
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        box7 = createInputBox("Mã vé:", vecombo);


        //field tạo điểm
        box8 = createInputBox("Điểm đổi mã:", diemfi = new JTextField(15));

        // Tạo box trái và box phải
        boxtrai = new JPanel();
        boxtrai.setLayout(new BoxLayout(boxtrai, BoxLayout.Y_AXIS));
        boxphai = new JPanel();
        boxphai.setLayout(new BoxLayout(boxphai, BoxLayout.Y_AXIS));

        // Thêm các box vào boxtrai và boxphai
        boxtrai.add(box1);
        boxtrai.add(Box.createVerticalStrut(10));
        boxtrai.add(box3);
        boxtrai.add(Box.createVerticalStrut(10));
        boxtrai.add(box5);
        boxtrai.add(Box.createVerticalStrut(10));
        boxtrai.add(box7);

        boxphai.add(box2);
        boxphai.add(Box.createVerticalStrut(10));
        boxphai.add(box4);
        boxphai.add(Box.createVerticalStrut(10));
        boxphai.add(box6);
        boxphai.add(Box.createVerticalStrut(10));
        boxphai.add(box8);

        // Khởi tạo boxtraiphai
        boxtraiphai = new JPanel();
        boxtraiphai.setLayout(new BoxLayout(boxtraiphai, BoxLayout.X_AXIS));
        boxtraiphai.add(Box.createHorizontalStrut(10));
        boxtraiphai.add(boxtrai);
        boxtraiphai.add(Box.createHorizontalStrut(10));
        boxtraiphai.add(boxphai);
        boxtraiphai.add(Box.createHorizontalStrut(10));

        // Thêm thành phần vào boxtren
        boxtren.add(titlelb);
        boxtren.add(Box.createVerticalStrut(10));
        boxtren.add(boxtraiphai);
        boxtren.add(Box.createVerticalStrut(20));

        // Khởi tạo bảng
        String[] colname = {"Mã khuyến mãi", "Tên khuyến mãi", "Thời gian bắt đầu", "Thời gian kết thúc", "Phần trăm giảm", "Mô tả","Mã vé","Điểm đổi mã"};
        tablemodel = new DefaultTableModel(colname, 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        table = new JTable(tablemodel);
        table.addMouseListener(this);
        // Khởi tạo panel chứa các nút
        boxnut = new JPanel();
        boxnut.setLayout(new BoxLayout(boxnut, BoxLayout.X_AXIS));
        boxnut.setBorder(new LineBorder(Color.BLACK, 2, true));

        // Thêm các nút vào boxnut
        thembt = createButton("Thêm", 70, 30);
        xoabt = createButton("Xóa", 80, 30);
        suabt = createButton("Sửa", 80, 30);
        xoatrangbt = createButton("Xóa Trắng", 100, 30);
        timfi = new JTextField(10);
        timfi.setPreferredSize(new Dimension(80, 30));
        timfi.setMaximumSize(new Dimension(500, 25));
        timfi.setMinimumSize(new Dimension(500, 25));
        timbt = createButton("Tìm", 80, 30);
        //thêm sự kiên cho nút
        thembt.addActionListener(this);
        xoabt.addActionListener(this);
        suabt.addActionListener(this);
        xoatrangbt.addActionListener(this);
        timbt.addActionListener(this);

        boxnut.add(Box.createHorizontalStrut(40));
        boxnut.add(thembt);
        boxnut.add(Box.createHorizontalStrut(10));
        boxnut.add(xoabt);
        boxnut.add(Box.createHorizontalStrut(10));
        boxnut.add(suabt);
        boxnut.add(Box.createHorizontalStrut(10));
        boxnut.add(xoatrangbt);
        boxnut.add(Box.createHorizontalStrut(10));
        boxnut.add(timfi);
        boxnut.add(Box.createHorizontalStrut(10));
        boxnut.add(timbt);
        boxnut.add(Box.createHorizontalStrut(70));

        // Thêm boxnut vào boxduoi và bảng vào vị trí CENTER
        boxduoi.add(boxnut, BorderLayout.NORTH);
        boxduoi.add(new JScrollPane(table), BorderLayout.CENTER);

        // Thêm các panel vào layout chính
        add(boxtren, BorderLayout.NORTH);
        add(boxduoi, BorderLayout.CENTER);

        // Thiết lập kích thước cho bảng
        table.setPreferredScrollableViewportSize(new Dimension(800, 300));
        loadDataFromDatabase();
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(xoatrangbt)) {
			clearFields();
		}
		if(e.getSource().equals(xoabt)) {
			xoaChuongTrinhKhuyenMai();
		}
		if(e.getSource().equals(timbt)) {
			String ma = timfi.getText(); 
			searchAndHighlightRowByMaCTKM(ma);
		}
		if(e.getSource().equals(thembt)) {
			addKhuyenMai();
			chitietkhuyenmaidao.searchByMaVe(makmfi.getText());
		}
		if(e.getSource().equals(suabt)) {
			suaKhuyenmai();
		}
		
	}

	private void suaKhuyenmai() {
		String maCTKM = makmfi.getText();
        String tenCTKM = tenkmfi.getText();
        String thoiGianBatDau = formatDate(startDateSpinner.getValue());
        String thoiGianKetThuc = formatDate(endDateSpinner.getValue());
        float phanTramKM = Float.parseFloat(phantramfi.getText());
        String moTa = motafi.getText();

        // Cập nhật thông tin khuyến mãi
        boolean updateSuccess = chitietkhuyenmaidao.updateKhuyenMai(maCTKM, tenCTKM, thoiGianBatDau, thoiGianKetThuc, phanTramKM, moTa);
        loadDataFromDatabase();
        // Thông báo kết quả
        if (updateSuccess) {
            JOptionPane.showMessageDialog(null, "Cập nhật khuyến mãi thành công!");
        } else {
            JOptionPane.showMessageDialog(null, "Cập nhật khuyến mãi thất bại!");
        }
		
	}
	private void addKhuyenMai() {

	    try {
	        // Lấy dữ liệu từ các trường nhập liệu
			String maCTKM = makmfi.getText();
	        String tenCTKM = tenkmfi.getText(); // Tên chương trình khuyến mãi
	        String thoiGianBatDau = formatDate(startDateSpinner.getValue()); // Định dạng thời gian bắt đầu
	        String thoiGianKetThuc = formatDate(endDateSpinner.getValue()); // Định dạng thời gian kết thúc
	        String ve = (String) vecombo.getSelectedItem(); // Lấy mã vé từ combobox
	        double phanTramKM = Double.parseDouble(phantramfi.getText()); // Lấy phần trăm khuyến mãi
	        String moTa = motafi.getText(); // Mô tả khuyến mãi
	        float diemtru = Float.parseFloat(diemfi.getText());

	        boolean isAdded = chitietkhuyenmaidao.themMaKhuyenMai(maCTKM, tenCTKM, thoiGianBatDau, thoiGianKetThuc,ve, phanTramKM, moTa,diemtru);
	        
	        // Thông báo cho người dùng
	        if (isAdded) {
	            JOptionPane.showMessageDialog(null, "Khuyến mãi đã được thêm thành công!");
	        } else {
	            JOptionPane.showMessageDialog(null, "Có lỗi khi thêm khuyến mãi!");
	        }
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "Mã chi tiết khuyến mãi không được trùng!");
	    }
	    loadDataFromDatabase();
	}

	// Hàm để định dạng ngày tháng từ JSpinner thành chuỗi đúng định dạng SQL (yyyy-MM-dd)
	private String formatDate(Object dateValue) {
	    if (dateValue instanceof Date) {
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        return sdf.format(dateValue);
	    }
	    return "";
	}


	// Hàm tìm kiếm và tô sáng dòng trong bảng
	public void searchAndHighlightRowByMaCTKM(String maCTKMToSearch) {
	    boolean isFound = chitietkhuyenmaidao.searchByMaVe(maCTKMToSearch); // Tìm kiếm theo mã CTKM

	    if (isFound) { // Nếu tìm thấy mã khuyến mãi
	        // Lặp qua tất cả các dòng trong bảng để tìm dòng có mã khuyến mãi khớp
	        for (int row = 0; row < table.getRowCount(); row++) {
	            String maCTKM = table.getValueAt(row, 0).toString(); // Lấy mã khuyến mãi của dòng
	            if (maCTKM.equals(maCTKMToSearch)) {
	                table.setRowSelectionInterval(row, row); // Chọn dòng này
	                table.setSelectionBackground(Color.YELLOW); // Tô màu nền của dòng thành vàng
	                // Cuộn đến vị trí dòng đó trong bảng
	                table.scrollRectToVisible(table.getCellRect(row, 0, true));
	                break;
	            }
	        }
	    } else {
	        JOptionPane.showMessageDialog(null, "Không tìm thấy mã khuyến mãi với mã: " + maCTKMToSearch); // Nếu không tìm thấy
	    }
	}

	private void xoaChuongTrinhKhuyenMai() {
	    // Lấy mã CTKM từ dòng được chọn trong bảng
		int selectedRow = table.getSelectedRow();
	    String maCTKM = table.getValueAt(selectedRow, 0).toString();
	    int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa chương trình khuyến mãi và các chi tiết liên quan?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
	    
	    if (confirm == JOptionPane.YES_OPTION) {
	        boolean isDeleted = chitietkhuyenmaidao.xoaChuongTrinhKhuyenMai(maCTKM);
	        
	        if (isDeleted) {
	        	loadDataFromDatabase();
	            JOptionPane.showMessageDialog(null, "Xóa thành công!");
	            // Cập nhật lại bảng nếu cần
	        } else {
	            JOptionPane.showMessageDialog(null, "Xóa thất bại.");
	        }
	    }
	}

	public void clearFields() {
	    // Xóa trắng các JTextField
	    makmfi.setText("");
	    tenkmfi.setText("");
	    phantramfi.setText("");
	    motafi.setText("");
	    timfi.setText("");

	    // Đặt lại JSpinner về giá trị mặc định (có thể là ngày hiện tại)
	    startDateSpinner.setValue(new java.util.Date()); // Đặt về ngày hiện tại
	    endDateSpinner.setValue(new java.util.Date());   // Đặt về ngày hiện tại
	}
	private void loadDataFromDatabase() {
        ArrayList<ChiTietKhuyenMai> list = chitietkhuyenmaidao.getAlltbChitietKhuyenMai(); // Lấy danh sách chi tiết khuyến mãi từ DAO

        // In ra số lượng chi tiết khuyến mãi
        System.out.println("Số lượng chi tiết khuyến mãi lấy được: " + (list != null ? list.size() : "null"));

        tablemodel.setRowCount(0); // Xóa các hàng cũ trong tableModel

        if (list != null && !list.isEmpty()) { // Kiểm tra xem danh sách có null không
            for (ChiTietKhuyenMai ct : list) {
                // Lấy thông tin chương trình khuyến mãi
                String maCTKM = "";
                String tenCTKM = "";
                Date thoiGianBatDau = null; // Thay đổi kiểu về Date
                Date thoiGianKetThuc = null; // Thay đổi kiểu về Date

                if (ct.getChuongTrinhKM() != null) {
                    maCTKM = ct.getChuongTrinhKM().getMaCTKM();
                    tenCTKM = ct.getChuongTrinhKM().getTenCTKM();
                    thoiGianBatDau = ct.getChuongTrinhKM().getThoiGianBatDau(); // Giả sử trả về java.util.Date
                    thoiGianKetThuc = ct.getChuongTrinhKM().getThoiGianKetThuc(); // Giả sử trả về java.util.Date
                }

                // Lấy thông tin chi tiết khuyến mãi
                String maVe = ct.getVe().getMaVe();
                double phanTramKM = ct.getPhanTramKM();
                String moTa = ct.getMoTa();
                System.out.println(thoiGianBatDau);
                // Định dạng ngày tháng
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                float diem = ct.getVe().getKhachHang().getDiemTichLuy().getDiemHienTai();
                
                // Thêm hàng vào tableModel
                tablemodel.addRow(new Object[]{
                    maCTKM,
                    tenCTKM,
                    thoiGianBatDau != null ? dateFormat.format(thoiGianBatDau) : "", // Chỉ lấy ngày tháng
                    thoiGianKetThuc != null ? dateFormat.format(thoiGianKetThuc) : "", // Chỉ lấy ngày tháng
                    phanTramKM,
                    moTa,
                    maVe,
                    diem
                });
            }
        }
    }



	// Hàm tạo một box với nhãn và ô nhập
    private JPanel createInputBox(String labelText, JComponent component) {
        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.X_AXIS));
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(150, 30)); // Kích thước cố định cho nhãn
        box.add(label);
        box.add(Box.createHorizontalStrut(10)); // Khoảng cách
        box.add(component);
        return box;
    }

    // Hàm tạo JSpinner cho ngày tháng
    private JSpinner createDateSpinner() {
        Calendar calendar = Calendar.getInstance();
        JSpinner spinner = new JSpinner(new SpinnerDateModel(calendar.getTime(), null, null, Calendar.DAY_OF_MONTH));
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "dd/MM/yyyy");
        spinner.setEditor(editor);
        return spinner;
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
	public void mouseClicked(MouseEvent e) {
	    // Lấy chỉ số hàng được chọn
	    int row = table.getSelectedRow();

	    // Kiểm tra nếu có hàng nào được chọn
	    if (row >= 0) {
	        // Hiển thị thông tin chi tiết khuyến mãi vào các trường nhập liệu
	        makmfi.setText(table.getValueAt(row, 0) != null ? table.getValueAt(row, 0).toString() : ""); // Mã khuyến mãi
	        tenkmfi.setText(table.getValueAt(row, 1) != null ? table.getValueAt(row, 1).toString() : ""); // Tên khuyến mãi
	        phantramfi.setText(table.getValueAt(row, 4) != null ? table.getValueAt(row, 4).toString() : ""); // Phần trăm giảm
	        motafi.setText(table.getValueAt(row, 5) != null ? table.getValueAt(row, 5).toString() : ""); // Mô tả
	        vecombo.setSelectedItem(table.getValueAt(row, 6).toString());
	        diemfi.setText(table.getValueAt(row, 7).toString());
	        // Định dạng chuỗi ngày
	        SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd-MM-yyyy");
	        try {
	            // Lấy chuỗi ngày từ bảng
	            String startDateString = table.getValueAt(row, 2) != null ? table.getValueAt(row, 2).toString() : "";
	            String endDateString = table.getValueAt(row, 3) != null ? table.getValueAt(row, 3).toString() : "";

	            // Phân tích cú pháp chuỗi ngày
	            java.util.Date thoiGianBatDau = inputDateFormat.parse(startDateString); // Ngày bắt đầu từ bảng
	            java.util.Date thoiGianKetThuc = inputDateFormat.parse(endDateString); // Ngày kết thúc từ bảng
	            
	            // Đặt giá trị cho JSpinner
	            startDateSpinner.setValue(thoiGianBatDau);
	            endDateSpinner.setValue(thoiGianKetThuc);
	        } catch (ParseException ex) {
	            ex.printStackTrace(); // Xử lý lỗi phân tích cú pháp nếu cần
	        }
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
