package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import dao.ChucVuDAO;
import dao.PhimDAO;
import dao.PhongChieuDAO;
import dao.SuatChieuDAO;
import entity.ChucVu;
import entity.Phim;
import entity.PhongChieu;
import entity.SuatChieu;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SuatChieuGUI extends JPanel implements MouseListener,ActionListener{
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JLabel titleLabel;
    private JSpinner startDateSpinner, endDateSpinner;
    private JTextField maXuatChieu, searchField;
    private DefaultTableModel tableModel;
    private JButton addButton, deleteButton, editButton, clearButton, searchButton;
    private JTable table;
    
	private JComboBox<String> phongcombo;
	private SuatChieuDAO suatchieudao;
	private PhongChieuDAO phongchieudao;
	private PhimDAO phimdao;
	private JComboBox<String> phimcombo;
	private JComboBox trangThaiXuatChieu;
	

    public SuatChieuGUI() {
        setLayout(new BorderLayout());
        suatchieudao = new SuatChieuDAO();
        phongchieudao = new PhongChieuDAO();
        phimdao = new PhimDAO();
        // Panel tiêu đề
        topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(new LineBorder(Color.BLACK, 2, true));

        titleLabel = new JLabel("THÔNG TIN SUẤT CHIẾU");
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 30));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(titleLabel);
        topPanel.add(Box.createVerticalStrut(10));

        // Khởi tạo JSpinner cho thời gian bắt đầu và kết thúc
        startDateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor startDateEditor = new JSpinner.DateEditor(startDateSpinner, "dd/MM/yyyy HH:mm");
        startDateSpinner.setEditor(startDateEditor);
        startDateSpinner.setValue(Calendar.getInstance().getTime());

        endDateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor endDateEditor = new JSpinner.DateEditor(endDateSpinner, "dd/MM/yyyy HH:mm");
        endDateSpinner.setEditor(endDateEditor);
        endDateSpinner.setValue(Calendar.getInstance().getTime());

        // Panel thông tin nhập liệu với BoxLayout
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
        inputPanel.setLayout(new GridLayout(1, 2, 20, 0)); // 1 hàng, 2 cột, khoảng cách ngang 20px
        inputPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        
        leftPanel.add(createInputBox("Mã suất chiếu:", maXuatChieu = new JTextField(15)));
        leftPanel.add(Box.createVerticalStrut(10));
        //combobox phong
        phongcombo = new JComboBox<>();;
        phongcombo.setEditable(true);
        ArrayList<PhongChieu> listCV = phongchieudao.getAllPhongChieu();
        for (PhongChieu cv : listCV) {
        	phongcombo.addItem(cv.getTenPhongChieu());
        }
        leftPanel.add(createInputBox("Tên Phòng:", phongcombo));
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(createInputBox("Thời gian bắt đầu:", startDateSpinner));
        
        
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(createInputBox("Trạng thái:", trangThaiXuatChieu = new JComboBox<>(new String[]{"Sẵn sàng", "Hết chỗ"})));
        
        rightPanel.add(Box.createVerticalStrut(10));
      //combobox phim
        phimcombo = new JComboBox<>();;
        phimcombo.setEditable(true);
        ArrayList<Phim> listCV1 = phimdao.getAllPhim();
        for (Phim cv : listCV1) {
        	phimcombo.addItem(cv.getTenPhim());
        }
        
        rightPanel.add(createInputBox("Phim:",phimcombo));
        
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(createInputBox("Thời gian kết thúc:", endDateSpinner));

        rightPanel.add(Box.createVerticalStrut(10)); // Add vertical space before the invisible box

        
        inputPanel.add(leftPanel);
        inputPanel.add(rightPanel);
        
        topPanel.add(inputPanel);
        topPanel.add(Box.createVerticalStrut(20));
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBorder(new LineBorder(Color.BLACK, 2, true));

        // Panel nút chức năng và tìm kiếm
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBorder(new LineBorder(Color.BLACK, 2, true));

        addButton = createButton("Thêm", 80, 30);
        deleteButton = createButton("Xóa", 80, 30);
        editButton = createButton("Sửa", 80, 30);
        clearButton = createButton("Xóa trắng", 100, 30);
        searchField = new JTextField(10);
        searchField.setPreferredSize(new Dimension(500, 27));
        searchField.setMaximumSize(new Dimension(500, 27));
        searchField.setMinimumSize(new Dimension(500, 27));
        searchButton = createButton("Tìm", 80, 30);
        
        //thêm sự kiên vào nut
        addButton.addActionListener(this);
        deleteButton.addActionListener(this);
        editButton.addActionListener(this);
        clearButton.addActionListener(this);
        searchButton.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBorder(new LineBorder(Color.BLACK, 2, true));

        buttonPanel.add(Box.createHorizontalStrut(40));
        buttonPanel.add(addButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(deleteButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(editButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(clearButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(searchField);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(searchButton);
        buttonPanel.add(Box.createHorizontalStrut(70));

        bottomPanel.add(buttonPanel, BorderLayout.NORTH);

        // Bảng thông tin xuất chiếu
        String[] colNames = {"Mã xuất chiếu", "Trạng thái", "Tên phòng", "Tên phim", "Thời gian bắt đầu", "Thời gian kết thúc"};
        tableModel = new DefaultTableModel(colNames, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.addMouseListener(this);
        table.setPreferredScrollableViewportSize(new Dimension(800, 300));
        bottomPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        //in ra màn hình
        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.CENTER);
        loadDataToTable();
    }
    private void loadDataToTable() {
        // Lấy danh sách suất chiếu từ DAO
        ArrayList<SuatChieu> dsSuatChieu = suatchieudao.getAllSuatChieu();

        // Xóa dữ liệu cũ trong bảng
        tableModel.setRowCount(0);

        // Duyệt qua danh sách suất chiếu và thêm từng dòng vào bảng
        for (SuatChieu sc : dsSuatChieu) {
            String maSuatChieu = sc.getMaSuatChieu();
            String tenPhim = sc.getPhim().getTenPhim();
            String tenPhong = sc.getPhongChieu().getTenPhongChieu();
            String thoiGianBatDau = sc.getThoiGianKhoiChieu().toString();
            String thoiGianKetThuc = sc.getThoiGianKetThuc().toString();
            String trangThai = sc.isTrangThai() ? "Sẵn sàng" : "Hết chỗ";

            // Thêm dữ liệu vào bảng
            tableModel.addRow(new Object[]{maSuatChieu,trangThai, tenPhong, tenPhim, thoiGianBatDau, thoiGianKetThuc});
        }
    }

    private JPanel createInputBox(String labelText, JComponent inputComponent) {
        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.X_AXIS));
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(150, 30)); // Đặt kích thước cố định cho nhãn để căn chỉnh đều
        box.add(label);
        box.add(Box.createHorizontalStrut(10));
        box.add(inputComponent);
        return box;
    }

    private JButton createButton(String text, int width, int height) {
        JButton button = new JButton(text);
        button.setBackground(Color.decode("#35a06c"));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(width, height));
        return button;
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(addButton)) {
			themSuatChieu();
		}
		if (e.getSource().equals(deleteButton)) {
			xoaSuatChieu();
		}
		if (e.getSource().equals(editButton)) {
			suaSuatChieu();
		}
		if (e.getSource().equals(clearButton)) {
			xoaTrangSuatChieu();
		}
		if (e.getSource().equals(searchButton)) {
			timSuatChieu(searchField.getText());
		}
	}

	public void suaSuatChieu() {
    // Kiểm tra nếu mã suất chiếu đã được chọn trong bảng
    int selectedRow = table.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một suất chiếu để sửa.");
        return;
    }

    // Lấy mã suất chiếu từ hàng được chọn trong bảng
    String maSuatChieu = (String) table.getValueAt(selectedRow, 0); // Giả sử cột 0 chứa mã suất chiếu

    // Lấy dữ liệu từ các trường nhập liệu trên giao diện
    String tenPhim = (String) phimcombo.getSelectedItem(); // Lấy tên phim từ ComboBox
    String tenPhongChieu = (String) phongcombo.getSelectedItem(); // Lấy tên phòng chiếu từ ComboBox
    Timestamp thoiGianKhoiChieu = new Timestamp(((Date) startDateSpinner.getValue()).getTime());
    Timestamp thoiGianKetThuc = new Timestamp(((Date) endDateSpinner.getValue()).getTime());
    boolean trangThai = "Sẵn sàng".equals(trangThaiXuatChieu.getSelectedItem());

 // Tạo đối tượng Phim và PhongChieu từ tên
    String maPhim = phimdao.getMaPhimByTenPhim(tenPhim);
    if (maPhim == null) {
        JOptionPane.showMessageDialog(this, "Không tìm thấy mã phim cho tên phim đã nhập!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }
    Phim phim = new Phim(maPhim);
    
    
    String maPhong = phongchieudao.getMaPhongChieuByTenPhong(tenPhongChieu);
    if (maPhong == null) {
        JOptionPane.showMessageDialog(this, "Không tìm thấy mã phim cho tên phim đã nhập!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    PhongChieu phongChieu = new PhongChieu(maPhong);
    // Tạo đối tượng SuatChieu mới
    SuatChieu suatChieu = new SuatChieu(maSuatChieu, phim, phongChieu, thoiGianKhoiChieu, thoiGianKetThuc, trangThai);

    boolean isUpdated = suatchieudao.suaSuatChieu(suatChieu);

    // Thông báo kết quả
    if (isUpdated) {
        JOptionPane.showMessageDialog(this, "Sửa suất chiếu thành công.");
        // Cập nhật lại dữ liệu trong bảng (có thể gọi lại hàm để load lại bảng)
        loadDataToTable(); // Giả sử bạn có hàm này để tải lại dữ liệu
    } else {
        JOptionPane.showMessageDialog(this, "Sửa suất chiếu thất bại.");
    }
}

	public void xoaTrangSuatChieu() {
	    // Xóa các trường JTextField
		maXuatChieu.setText(""); // Xóa mã suất chiếu
	    phimcombo.setSelectedIndex(0); // Reset lại combobox phim, chọn giá trị mặc định
	    phongcombo.setSelectedIndex(0); // Reset lại combobox phòng chiếu, chọn giá trị mặc định
	 // Đặt lại giá trị cho các JSpinner (thời gian bắt đầu và kết thúc)
	    startDateSpinner.setValue(Calendar.getInstance().getTime()); // Đặt lại thời gian hiện tại cho Start Date
	    endDateSpinner.setValue(Calendar.getInstance().getTime()); // Đặt lại thời gian hiện tại cho End Date
	    trangThaiXuatChieu.setSelectedIndex(0); // Reset trạng thái (nếu là combobox)
	}

	public void xoaSuatChieu() {
		int row = table.getSelectedRow();
		boolean result = false;
		String maSuatChieu = null;
        if (row != -1) {
            maSuatChieu = tableModel.getValueAt(row, 0).toString();
            result = suatchieudao.xoaSuatChieu(maSuatChieu); // Gọi phương thức xóa theo mã suất chiếu
        } else {
            JOptionPane.showMessageDialog(SuatChieuGUI.this, "Vui lòng chọn một suất chiếu để xóa", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
	    if (maSuatChieu.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Mã suất chiếu không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    if (result) {
	        // Xóa thành công, thông báo và cập nhật giao diện
	        JOptionPane.showMessageDialog(this, "Xóa suất chiếu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	        loadDataToTable(); // Tải lại dữ liệu vào bảng sau khi xóa
	    } else {
	        // Xóa không thành công
	        JOptionPane.showMessageDialog(this, "Xóa suất chiếu không thành công!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	    }
	}

	private void themSuatChieu() {
    // Lấy dữ liệu từ các thành phần giao diện
    String maSuatChieu = maXuatChieu.getText().trim();
    String tenPhim = (String) phimcombo.getSelectedItem();
    String tenPhongChieu = (String) phongcombo.getSelectedItem();
    Timestamp thoiGianKhoiChieu = new Timestamp(((Date) startDateSpinner.getValue()).getTime());
    Timestamp thoiGianKetThuc = new Timestamp(((Date) endDateSpinner.getValue()).getTime());
    boolean trangThai = trangThaiXuatChieu.getSelectedItem().equals("Sẵn sàng");
    
    // Kiểm tra dữ liệu đầu vào (có thể thêm các kiểm tra cần thiết)
    if (maSuatChieu.isEmpty() || tenPhim == null || tenPhongChieu == null) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Tạo đối tượng Phim và PhongChieu từ tên
    String maPhim = phimdao.getMaPhimByTenPhim(tenPhim);
    if (maPhim == null) {
        JOptionPane.showMessageDialog(this, "Không tìm thấy mã phim cho tên phim đã nhập!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }
    Phim phim = new Phim(maPhim);
    
    
    String maPhong = phongchieudao.getMaPhongChieuByTenPhong(tenPhongChieu);
    if (maPhong == null) {
        JOptionPane.showMessageDialog(this, "Không tìm thấy mã phim cho tên phim đã nhập!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    PhongChieu phongChieu = new PhongChieu(maPhong);

    // Tạo đối tượng SuatChieu
    SuatChieu suatChieu = new SuatChieu(maSuatChieu, phim, phongChieu, thoiGianKhoiChieu, thoiGianKetThuc, trangThai);

    // Gọi hàm themSuatChieu từ SuatChieuDAO

    boolean success = suatchieudao.themSuatChieu(suatChieu);

    // Hiển thị kết quả
    if (success) {
        JOptionPane.showMessageDialog(this, "Thêm suất chiếu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        loadDataToTable();

    } else {
        JOptionPane.showMessageDialog(this, "Thêm suất chiếu thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}
	private void timSuatChieu(String maSuatChieu) {

    if (maSuatChieu.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập mã suất chiếu cần tìm!");
        return;
    }

    // Gọi hàm findSuatChieuByMa từ DAO để kiểm tra xem suất chiếu có tồn tại
    if (suatchieudao.findSuatChieuByMa(maSuatChieu)) {
        // Tìm vị trí của suất chiếu trong bảng
        int rowCount = table.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            String maSC = table.getValueAt(i, 0).toString();
            if (maSC.equals(maSuatChieu)) {
                // Tô màu hàng tìm thấy
                table.setRowSelectionInterval(i, i);
                table.scrollRectToVisible(table.getCellRect(i, 0, true));

                // Thay đổi màu nền cho hàng tìm thấy
                table.setSelectionBackground(Color.YELLOW);
                JOptionPane.showMessageDialog(this, "Tìm thấy suất chiếu: " + maSuatChieu);
                return;
            }
        }
    } else {
        JOptionPane.showMessageDialog(this, "Không tìm thấy suất chiếu với mã: " + maSuatChieu);
    }
}


	@Override
	public void mouseClicked(MouseEvent e) {
		int selectedRow = table.getSelectedRow();
	    
	    // Nếu có hàng được chọn
	    if (selectedRow >= 0) {
	        // Lấy dữ liệu từ hàng được chọn
	        String maSuatChieu = (String) tableModel.getValueAt(selectedRow, 0);
	        String trangThai = (String) tableModel.getValueAt(selectedRow, 1);
	        String tenPhong = (String) tableModel.getValueAt(selectedRow, 2);
	        String tenPhim = (String) tableModel.getValueAt(selectedRow, 3);
	        String thoiGianBatDau = (String) tableModel.getValueAt(selectedRow, 4);
	        String thoiGianKetThuc = (String) tableModel.getValueAt(selectedRow, 5);

	        // Đẩy dữ liệu vào các textfield
	        maXuatChieu.setText(maSuatChieu); // Mã suất chiếu
	        trangThaiXuatChieu.setSelectedItem(trangThai); // Trạng thái (combo)
	        
	        // Cập nhật combo box Phòng chiếu
	        phongcombo.setSelectedItem(tenPhong);

	        // Cập nhật combo box Phim
	        phimcombo.setSelectedItem(tenPhim);

	        // Cập nhật Spinner (Thời gian bắt đầu và kết thúc)
	        // Chuyển đổi String thành java.sql.Timestamp để điền vào Spinner (giả sử thoiGianBatDau là kiểu Timestamp)
	        try {
	            java.sql.Timestamp startTime = java.sql.Timestamp.valueOf(thoiGianBatDau);
	            java.sql.Timestamp endTime = java.sql.Timestamp.valueOf(thoiGianKetThuc);

	            startDateSpinner.setValue(startTime);
	            endDateSpinner.setValue(endTime);
	        } catch (IllegalArgumentException e1) {
	            // Nếu không thể chuyển đổi đúng định dạng, có thể do format sai, xử lý trường hợp này
	            System.out.println("Invalid date format.");
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
