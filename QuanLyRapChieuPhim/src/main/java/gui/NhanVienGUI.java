package gui;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

import connectDB.ConnectDB;
import dao.ChucVuDAO;
import dao.NhanVienDAO;
import entity.ChucVu;
import entity.NhanVien;

public class NhanVienGUI extends JPanel implements MouseListener{
	private JPanel topPanel, bottomPanel;
    private JLabel titleLabel;
    private JTextField maNV, tenNV, sdt, cccd, email, ngaySinh, timField;
    private JComboBox<String> chucVuCombo;
    private JRadioButton gioiTinhNam, gioiTinhNu;
    private DefaultTableModel tableModel;
    private JButton themButton, xoaButton, suaButton, xoaTrangButton, timButton;
    private JTable table;
    private ButtonGroup gioiTinhGroup;
    private ChucVuDAO chucvudao;
    private NhanVienDAO nhanviendao;

    public NhanVienGUI() {
        setLayout(new BorderLayout());
        nhanviendao = new NhanVienDAO();
        
        initTopPanel();
        initBottomPanel();
        
        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.CENTER);
        
        initButtonActions();
        loadDataFromDatabase();
    }

    private void initTopPanel() {
        topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(new LineBorder(Color.BLACK, 2, true));

        titleLabel = new JLabel("THÔNG TIN NHÂN VIÊN");
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 30));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        topPanel.add(titleLabel);
        topPanel.add(Box.createVerticalStrut(10));

        JPanel inputPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        inputPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        inputPanel.add(createLeftInputPanel());
        inputPanel.add(createRightInputPanel());

        topPanel.add(inputPanel);
        topPanel.add(Box.createVerticalStrut(20));
    }

    private JPanel createLeftInputPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(createInputBox("Mã nhân viên:", maNV = new JTextField(15)));
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(createInputBox("Tên nhân viên:", tenNV = new JTextField(15)));
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(createInputBox("Email:", email = new JTextField(15)));
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(createInputBox("Số điện thoại:", sdt = new JTextField(15)));
        return leftPanel;
    }

    private JPanel createRightInputPanel() {
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(createInputBox("Ngày sinh:", ngaySinh = new JTextField(15)));
        rightPanel.add(Box.createVerticalStrut(10));

        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genderPanel.add(new JLabel("Giới tính:"));
        genderPanel.add(Box.createHorizontalStrut(35));
        gioiTinhNam = new JRadioButton("Nam");
        gioiTinhNu = new JRadioButton("Nữ");
        gioiTinhGroup = new ButtonGroup();
        gioiTinhGroup.add(gioiTinhNam);
        gioiTinhGroup.add(gioiTinhNu);
        genderPanel.add(gioiTinhNam);
        genderPanel.add(gioiTinhNu);
        rightPanel.add(genderPanel);

        rightPanel.add(createInputBox("Căn Cước:", cccd = new JTextField(15)));
        rightPanel.add(Box.createVerticalStrut(10));

        
        chucVuCombo = new JComboBox<>();
        chucVuCombo.setEditable(true);
        chucvudao = new ChucVuDAO();
        ArrayList<ChucVu> listCV = chucvudao.getalltbChucVu();
        for (ChucVu cv : listCV) {
            chucVuCombo.addItem(cv.getTenChucVu());
        }
		rightPanel.add(createInputBox("Chức vụ:", chucVuCombo));
        return rightPanel;
    }

    private void initBottomPanel() {
        bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(new LineBorder(Color.BLACK, 2, true));

        String[] columnNames = {"Mã nhân viên", "Tên nhân viên", "Email", "Số điện thoại", "Ngày sinh", "Giới tính", "Căn cước", "Chức vụ"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.addMouseListener(this);
        table.setPreferredScrollableViewportSize(new Dimension(800, 300));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        themButton = createButton("Thêm", 80, 30);
        xoaButton = createButton("Xóa", 80, 30);
        suaButton = createButton("Sửa", 80, 30);
        xoaTrangButton = createButton("Xóa Trắng", 100, 30);
        timField = new JTextField(35);
        timField.setPreferredSize(new Dimension(70, 27));
        timField.setMaximumSize(new Dimension(70, 27));
        timField.setMinimumSize(new Dimension(70, 27));
        timButton = createButton("Tìm", 80, 30);
        
        buttonPanel.add(themButton);
        buttonPanel.add(xoaButton);
        buttonPanel.add(suaButton);
        buttonPanel.add(xoaTrangButton);
        buttonPanel.add(timField);
        buttonPanel.add(timButton);

        bottomPanel.add(buttonPanel, BorderLayout.NORTH);
        bottomPanel.add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void initButtonActions() {
        themButton.addActionListener(e -> addNhanVien()); // Thêm nhân viên
        xoaButton.addActionListener(e -> deleteNhanVien()); // Xóa nhân viên
        suaButton.addActionListener(e -> {
			try {
				editNhanVien();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}); // Sửa thông tin nhân viên
        xoaTrangButton.addActionListener(e -> clearFields()); // Xóa các trường nhập
        timButton.addActionListener(e -> searchNhanVien()); // Tìm kiếm nhân viên
    }


    private void loadDataFromDatabase() {
        ArrayList<NhanVien> list = nhanviendao.getAllNhanVien(); // Lấy danh sách nhân viên từ DAO

        tableModel.setRowCount(0); // Xóa các hàng cũ trong tableModel

        if (list != null) { // Kiểm tra xem danh sách có null không
            for (NhanVien s : list) {
                String gioiTinh = s.isGioiTinh() ? "Nam" : "Nữ"; // Kiểm tra giới tính

                // Chuyển đổi ngày sinh từ Date sang String nếu cần
                String ngaySinhString = "";
                if (s.getNgaySinh() != null) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    ngaySinhString = dateFormat.format(s.getNgaySinh());
                }

                // Thêm hàng vào tableModel
                tableModel.addRow(new Object[]{
                    s.getMaNV(),
                    s.getTenNV(),
                    s.getEmail(),
                    s.getSdt(), // Nếu có getter cho số điện thoại
                    ngaySinhString, // Hiển thị ngày sinh đã định dạng
                    gioiTinh,
                    s.getCccd(), // Nếu có getter cho CCCD
                    s.getVaiTro() != null ? s.getVaiTro().getTenChucVu() : "" // Kiểm tra null cho vai trò
                });
            }
        } else {
            // Xử lý trường hợp danh sách nhân viên là null
            System.out.println("Không có dữ liệu nhân viên để hiển thị.");
        }

        table.setModel(tableModel); // Cập nhật table model
    }
    
    private void addNhanVien() {
        // Lấy thông tin từ các trường nhập
        String ma = maNV.getText();
        String ten = tenNV.getText();
        String emailText = email.getText();
        String sdtText = sdt.getText();
        String cccdText = cccd.getText();
        String ngaySinhText = ngaySinh.getText(); // Lấy chuỗi từ ô nhập

        boolean gioiTinh = gioiTinhNam.isSelected(); // Lưu giá trị boolean cho giới tính
        String tenChucVu = (String) chucVuCombo.getSelectedItem(); // Mã chức vụ được chọn từ ComboBox
        String machucvu = "";
        if ("Nhân viên bán vé".equals(tenChucVu)) {
            machucvu = "1";
        } else if ("Nhân viên quản lí".equals(tenChucVu)) {
            machucvu = "2";
        } else if ("Nhân viên quản trị viên".equals(tenChucVu)) {
            machucvu = "0";
        }
        String matKhauText = JOptionPane.showInputDialog(this, "Nhập mật khẩu cho tài khoản:"); // Nhập mật khẩu từ người dùng

        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy"); // Định dạng đầu vào từ người dùng

        try {
            // Phân tích cú pháp chuỗi ngày sinh
            java.util.Date utilDate = inputFormat.parse(ngaySinhText); // Sử dụng java.util.Date để phân tích
            java.sql.Date ngaySinhSQL = new java.sql.Date(utilDate.getTime()); // Chuyển đổi sang java.sql.Date

            // Kiểm tra xem mã nhân viên đã tồn tại chưa
            if (nhanviendao.existsMaNV(ma)) {
                JOptionPane.showMessageDialog(this, "Mã nhân viên đã tồn tại. Vui lòng nhập mã khác.");
                return; // Kết thúc phương thức nếu mã nhân viên đã tồn tại
            }

            // Tạo đối tượng NhanVien mới
            NhanVien nv = new NhanVien(ma, ten, emailText, sdtText, ngaySinhSQL, gioiTinh, cccdText, new ChucVu(machucvu, tenChucVu));
            // Gọi phương thức thêm nhân viên
            if (nhanviendao.themNhanVien(nv)) {
                // Nếu thêm thành công, thêm tài khoản cho nhân viên
                if (nhanviendao.themTaiKhoan(ma, matKhauText)) {
                    // Nếu thêm tài khoản thành công, cập nhật bảng model
                    tableModel.addRow(new Object[]{ma, ten, emailText, sdtText, ngaySinhSQL, gioiTinh ? "Nam" : "Nữ", cccdText, tenChucVu});
                } else {
                    // Thông báo lỗi nếu không thêm tài khoản thành công
                    JOptionPane.showMessageDialog(this, "Không thể thêm tài khoản cho nhân viên. Vui lòng kiểm tra thông tin.");
                }
            } else {
                // Thông báo lỗi nếu không thêm thành công
                JOptionPane.showMessageDialog(this, "Không thể thêm nhân viên. Vui lòng kiểm tra thông tin.");
            }
        } catch (ParseException e) {
            // Xử lý lỗi phân tích cú pháp
            JOptionPane.showMessageDialog(this, "Ngày sinh không hợp lệ. Vui lòng nhập theo định dạng yyyy-MM-dd.");
        }

        // Làm sạch các trường nhập
        clearFields();
    }
    


    private void deleteNhanVien() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String maNV = tableModel.getValueAt(selectedRow, 0).toString(); // Giả định cột 0 chứa mã nhân viên

            // Xóa nhân viên
            boolean isDeleted = nhanviendao.delete(maNV);

            if (isDeleted) {
                tableModel.removeRow(selectedRow); // Xóa hàng khỏi tableModel
                JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công.");
            } else {
                JOptionPane.showMessageDialog(this, "Xóa nhân viên thất bại.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhân viên để xóa.");
        }
    }



    private void editNhanVien() throws ParseException {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            // Lấy mã nhân viên từ ô nhập liệu
            String ma = maNV.getText(); // Mã nhân viên không được thay đổi
            String ten = tenNV.getText(); // Tên nhân viên
            String emailText = email.getText(); // Email
            String sdtText = sdt.getText(); // Số điện thoại
            String cccdText = cccd.getText(); // CCCD
            String ngaySinhText = ngaySinh.getText(); // Ngày sinh dưới dạng chuỗi

            boolean gioiTinh = gioiTinhNam.isSelected(); // Lưu giá trị boolean cho giới tính
            String maChucVu = (String) chucVuCombo.getSelectedItem(); // Mã chức vụ được chọn từ ComboBox
            String machucvu = "";

            // Xác định mã chức vụ tương ứng
            if ("Nhân viên bán vé".equals(maChucVu)) {
                machucvu = "1";
            } else if ("Nhân viên quản lí".equals(maChucVu)) {
                machucvu = "2";
            } else if ("Nhân viên quản trị viên".equals(maChucVu)) {
                machucvu = "0";
            }

            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd"); // Định dạng đầu vào từ người dùng

            try {
                // Phân tích cú pháp chuỗi ngày sinh
                java.util.Date utilDate = inputFormat.parse(ngaySinhText); // Sử dụng java.util.Date để phân tích
                java.sql.Date ngaySinhSQL = new java.sql.Date(utilDate.getTime()); // Chuyển đổi sang java.sql.Date

                // Tạo đối tượng NhanVien mới với thông tin cập nhật
                NhanVien nv = new NhanVien(ma, ten, emailText, sdtText, ngaySinhSQL, gioiTinh, cccdText, new ChucVu(machucvu, null));

                // Cập nhật thông tin nhân viên trong cơ sở dữ liệu
                if (nhanviendao.update(nv)) {
                    // Cập nhật bảng hiển thị thông tin nhân viên
                    tableModel.setValueAt(ten, selectedRow, 1);
                    tableModel.setValueAt(emailText, selectedRow, 2);
                    tableModel.setValueAt(sdtText, selectedRow, 3);
                    tableModel.setValueAt(new SimpleDateFormat("dd-MM-yyyy").format(ngaySinhSQL), selectedRow, 4);
                    tableModel.setValueAt(gioiTinh ? "Nam" : "Nữ", selectedRow, 5);
                    tableModel.setValueAt(cccdText, selectedRow, 6);
                    tableModel.setValueAt(maChucVu, selectedRow, 7); // Cập nhật chức vụ
                    clearFields(); // Xóa các trường nhập liệu
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật không thành công.");
                }
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "Ngày sinh không hợp lệ. Vui lòng nhập lại theo định dạng yyyy-MM-dd.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhân viên để sửa.");
        }
    }




    private void clearFields() {
        maNV.setText("");
        tenNV.setText("");
        sdt.setText("");
        cccd.setText("");
        email.setText("");
        ngaySinh.setText("");
        gioiTinhGroup.clearSelection();
        chucVuCombo.setSelectedIndex(0);
        timField.setText("");
    }

    private void searchNhanVien() {
        String searchText = timField.getText();
        NhanVien nhanVien = nhanviendao.findByMa(searchText); // Tìm kiếm theo mã nhân viên
        
        if (nhanVien != null) {
            // Tìm chỉ số hàng trong bảng
            int rowIndex = getRowIndexByMaNV(nhanVien.getMaNV()); // Tìm hàng dựa trên mã nhân viên
            if (rowIndex != -1) {
                table.setRowSelectionInterval(rowIndex, rowIndex); // Tô sáng hàng
                table.setSelectionBackground(Color.YELLOW);
                table.scrollRectToVisible(table.getCellRect(rowIndex, 0, true)); // Cuộn đến hàng
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Không tìm thấy kết quả nào.");
    }

    // Phương thức hỗ trợ để tìm chỉ số hàng trong bảng dựa trên mã nhân viên
    private int getRowIndexByMaNV(String maNV) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 0).equals(maNV)) { // Giả sử cột 0 là mã nhân viên
                return i;
            }
        }
        return -1; // Trả về -1 nếu không tìm thấy
    }



    private JPanel createInputBox(String labelText, JComponent inputComponent) {
        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.X_AXIS));
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(100, 30));
        label.setMaximumSize(new Dimension(100, 30));
        label.setMinimumSize(new Dimension(100, 30));
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
	public void mouseClicked(MouseEvent e) {
	    // Lấy chỉ số hàng được chọn
	    int row = table.getSelectedRow();

	    // Kiểm tra nếu có hàng nào được chọn
	    if (row >= 0) {
	        // Hiển thị thông tin nhân viên vào các trường nhập liệu
	        maNV.setText(tableModel.getValueAt(row, 0).toString()); // Mã Nhân viên
	        tenNV.setText(tableModel.getValueAt(row, 1).toString()); // Tên Nhân viên
	        email.setText(tableModel.getValueAt(row, 2).toString()); // Số điện thoại
	        sdt.setText(tableModel.getValueAt(row, 3).toString()); // CCCD
	        ngaySinh.setText(tableModel.getValueAt(row, 4).toString()); // Địa chỉ email
	        gioiTinhNam.setSelected(tableModel.getValueAt(row, 5).toString().equals("Nam")); // Giới tính
	        gioiTinhNu.setSelected(tableModel.getValueAt(row, 5).toString().equals("Nữ")); // Giới tính
	        cccd.setText(tableModel.getValueAt(row, 6).toString()); // Ngày sinh
	        chucVuCombo.setSelectedItem(tableModel.getValueAt(row, 7).toString()); // Chức vụ
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
