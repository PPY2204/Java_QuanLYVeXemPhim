package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import connectDB.ConnectDB;
import dao.KhachHangDAO;
import entity.DiemTichLuy;
import entity.KhachHang;
import entity.NhanVien;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ThanhVienGUI extends JPanel implements MouseListener{
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JLabel titleLabel;
    private JTextField maKH, tenKH, sdt, email, diemTichLuy, searchFi;
    private DefaultTableModel tablemodel;
    private JButton addButton, searchButton, dAllButton, editButton, delButton;
    private JTable table;
	private KhachHangDAO khachhangdao;
    
    public ThanhVienGUI() {
        setLayout(new BorderLayout());
        khachhangdao = new KhachHangDAO();
        topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(new LineBorder(Color.BLACK, 2, true));

        titleLabel = new JLabel("THÔNG TIN KHÁCH HÀNG");
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 30));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        topPanel.add(titleLabel);
        topPanel.add(Box.createVerticalStrut(10));

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(1, 2, 20, 0));
        inputPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(createInputBox("Mã Khách hàng:", maKH = new JTextField(15)));
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(createInputBox("Tên Khách hàng:", tenKH = new JTextField(15)));
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(createInputBox("Điểm tích lũy:", diemTichLuy = new JTextField(15)));

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(createInputBox("Email:", email = new JTextField(15)));
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(createInputBox("Số điện thoại:", sdt = new JTextField(15)));
        

        inputPanel.add(leftPanel);
        inputPanel.add(rightPanel);

        topPanel.add(inputPanel);
        topPanel.add(Box.createVerticalStrut(20));
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBorder(new LineBorder(Color.BLACK, 2, true));

        String[] colname = {"Mã Khách hàng", "Tên Khách hàng", "Số điện thoại", "Email", "Điểm tích lũy"};
        tablemodel = new DefaultTableModel(colname, 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        table = new JTable(tablemodel);
        table.addMouseListener(this);
        table.setPreferredScrollableViewportSize(new Dimension(800, 300));

        JPanel boxButton = new JPanel();
        boxButton.setLayout(new BoxLayout(boxButton, BoxLayout.X_AXIS));
        boxButton.setBorder(new LineBorder(Color.BLACK, 2, true));

        addButton = createButton("Thêm", 80, 30);
        delButton = createButton("Xóa", 80, 30);
        editButton = createButton("Sửa", 80, 30);
        dAllButton = createButton("Xóa Trắng", 100, 30);
        searchFi = new JTextField(10);
        searchFi.setPreferredSize(new Dimension(80, 30));
        searchFi.setMaximumSize(new Dimension(500, 25));
        searchFi.setMinimumSize(new Dimension(500, 25));
        searchButton = createButton("Tìm", 80, 30);

        boxButton.add(Box.createHorizontalStrut(40));
        boxButton.add(addButton);
        boxButton.add(Box.createHorizontalStrut(10));
        boxButton.add(delButton);
        boxButton.add(Box.createHorizontalStrut(10));
        boxButton.add(editButton);
        boxButton.add(Box.createHorizontalStrut(10));
        boxButton.add(dAllButton);
        boxButton.add(Box.createHorizontalStrut(10));
        boxButton.add(searchFi);
        boxButton.add(Box.createHorizontalStrut(10));
        boxButton.add(searchButton);
        boxButton.add(Box.createHorizontalStrut(70));

        bottomPanel.add(boxButton, BorderLayout.NORTH);
        bottomPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.CENTER);
        loadDataFromDatabase();
        // Add action listeners for buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addKhachHang();
            }
        });

        delButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteKhachHang();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editKhachHang();
            }
        });

        dAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchKhachHang();
            }
        });
    }

    private JPanel createInputBox(String labelText, JComponent inputComponent) {
        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.X_AXIS));
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(100, 30));
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
        button.setMaximumSize(new Dimension(width, height));
        button.setMinimumSize(new Dimension(width, height));
        return button;
    }

    private void addKhachHang() {
        // Lấy thông tin từ các trường nhập liệu
        String ma = maKH.getText();
        String ten = tenKH.getText();
        String sdtText = sdt.getText();
        String emailText = email.getText();
        String diemMa = diemTichLuy.getText(); // Mã điểm tích lũy
        float diem;

        // Kiểm tra tính hợp lệ của dữ liệu đầu vào
        if (ma.isEmpty() || ten.isEmpty() || sdtText.isEmpty() || emailText.isEmpty() || diemMa.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin khách hàng.", "Lỗi dữ liệu", JOptionPane.ERROR_MESSAGE);
            return; // Ngưng thực hiện nếu có trường thông tin còn trống
        }

        try {
            diem = Float.parseFloat(diemMa); // Ép kiểu từ String sang float
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Điểm tích lũy không hợp lệ. Vui lòng nhập lại.", "Lỗi dữ liệu", JOptionPane.ERROR_MESSAGE);
            return; // Nếu không thể chuyển đổi, dừng phương thức
        }
        // Kiểm tra xem mã điểm tích lũy có tồn tại trong cơ sở dữ liệu không


        // Tạo đối tượng DiemTichLuy
        DiemTichLuy diemTichLuyObj = new DiemTichLuy(diemMa, diem); // Tạo đối tượng DiemTichLuy với mã và điểm hiện tại

        // Tạo đối tượng KhachHang
        KhachHang khachHang = new KhachHang(ma, ten, sdtText, emailText, diemTichLuyObj); // Tạo đối tượng KhachHang

        // Tạo đối tượng DAO để thêm khách hàng vào cơ sở dữ liệu
        if (khachhangdao.create(khachHang)) { // Nếu thêm thành công
            // Thêm vào model table
            tablemodel.addRow(new Object[]{ma, ten, sdtText, emailText, diem});
            JOptionPane.showMessageDialog(null, "Khách hàng đã được thêm thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Xử lý khi thêm không thành công
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm khách hàng. Vui lòng kiểm tra lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        clearFields(); // Xóa các trường nhập liệu sau khi thêm
    }

    private void deleteKhachHang() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String maKH = tablemodel.getValueAt(selectedRow, 0).toString(); // Giả định cột 0 chứa mã khách hàng

            // Hiển thị hộp thoại xác nhận
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Bạn có chắc chắn muốn xóa khách hàng này không?", 
                "Xác nhận xóa", 
                JOptionPane.YES_NO_OPTION);

            // Nếu người dùng chọn "Có", thực hiện xóa
            if (confirm == JOptionPane.YES_OPTION) {
                // Xóa khách hàng
                boolean isDeleted = khachhangdao.delete(maKH); // Sử dụng phương thức delete trong lớp DAO

                if (isDeleted) {
                    tablemodel.removeRow(selectedRow); // Xóa hàng khỏi tableModel
                    JOptionPane.showMessageDialog(this, "Xóa khách hàng thành công.");
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa khách hàng thất bại.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một khách hàng để xóa.");
        }
    }


    private void editKhachHang() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            // Lấy mã khách hàng từ hàng đã chọn
            String maKHValue = tablemodel.getValueAt(selectedRow, 0).toString();
            
            // Tạo đối tượng KhachHang mới với thông tin từ các trường nhập liệu
            KhachHang kh = new KhachHang();
            kh.setMaKH(maKHValue); // Đặt mã khách hàng
            kh.setTenKH(tenKH.getText()); // Đặt tên khách hàng
            kh.setSdt(sdt.getText()); // Đặt số điện thoại
            kh.setEmail(email.getText()); // Đặt email

            // Biến lưu mã điểm tích lũy
            String diemtichluy;
            float diemTichLuyValue;

            // Kiểm tra và lấy giá trị điểm tích lũy
            if (!isDiemTichLuyValid(diemTichLuy.getText())) {
                JOptionPane.showMessageDialog(this, "Điểm tích lũy không hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return; // Kết thúc hàm nếu điểm không hợp lệ
            }
            
            diemTichLuyValue = Float.parseFloat(diemTichLuy.getText()); // Đặt điểm tích lũy
            diemtichluy = convertDiemToMa(diemTichLuyValue); // Chuyển đổi điểm tích lũy thành mã tương ứng

            // Hiển thị thông tin trước khi cập nhật
            String message = String.format("Mã KH: %s\nTên KH: %s\nSĐT: %s\nEmail: %s\nĐiểm Tích Lũy: %s",
                    kh.getMaKH(), kh.getTenKH(), kh.getSdt(), kh.getEmail(), diemTichLuyValue);
            int confirm = JOptionPane.showConfirmDialog(this, message, "Xác nhận cập nhật", JOptionPane.YES_NO_OPTION);

            if (confirm != JOptionPane.YES_OPTION) {
                return; // Nếu người dùng không đồng ý, kết thúc hàm
            }
            
            
            kh.setDiemTichLuy(new DiemTichLuy(diemtichluy,diemTichLuyValue)); // Đặt điểm tích lũy vào khách hàng
			System.out.println(kh);
            // Gọi phương thức update để cập nhật khách hàng trong cơ sở dữ liệu
            boolean isUpdated = khachhangdao.update(kh);
            System.out.println("ket quả của hàm update " + isUpdated);
            if (isUpdated) {
                // Cập nhật thông tin trong tableModel
                tablemodel.setValueAt(kh.getTenKH(), selectedRow, 1); // Cập nhật tên khách hàng
                tablemodel.setValueAt(kh.getSdt(), selectedRow, 2); // Cập nhật số điện thoại
                tablemodel.setValueAt(kh.getEmail(), selectedRow, 3); // Cập nhật email
                tablemodel.setValueAt(kh.getDiemTichLuy().getDiemHienTai(), selectedRow, 4); // Cập nhật mã điểm tích lũy
                
                // Thông báo thành công
                JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thành công.");
                clearFields(); // Làm sạch các trường nhập liệu
            } else {
                JOptionPane.showMessageDialog(this, "Hãy chọn đúng mã.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một khách hàng để sửa.");
        }
    }
 // Phương thức kiểm tra tính hợp lệ của điểm tích lũy
    private boolean isDiemTichLuyValid(String diemTichLuyText) {
        try {
            float value = Float.parseFloat(diemTichLuyText);
            return value == 100.0 || value == 150.0 || value == 200.0 || value == 250.0 ||
                   value == 300.0 || value == 350.0 || value == 400.0 || value == 450.0 ||
                   value == 500.0 || value == 550.0;
        } catch (NumberFormatException e) {
            return false; // Nếu không thể chuyển đổi, trả về false
        }
    }

    // Phương thức chuyển đổi điểm tích lũy thành mã tương ứng
    private String convertDiemToMa(float diemTichLuyValue) {
        if (diemTichLuyValue == 100.0) return "DTL001";
        else if (diemTichLuyValue == 150.0) return "DTL002";
        else if (diemTichLuyValue == 200.0) return "DTL003";
        else if (diemTichLuyValue == 250.0) return "DTL004";
        else if (diemTichLuyValue == 300.0) return "DTL005";
        else if (diemTichLuyValue == 350.0) return "DTL006";
        else if (diemTichLuyValue == 400.0) return "DTL007";
        else if (diemTichLuyValue == 450.0) return "DTL008";
        else if (diemTichLuyValue == 500.0) return "DTL009";
        else if (diemTichLuyValue == 550.0) return "DTL010";
        else return ""; // Nếu không hợp lệ, trả về chuỗi rỗng
    }




    private void clearFields() {
        maKH.setText("");
        tenKH.setText("");
        sdt.setText("");
        email.setText("");
        diemTichLuy.setText("");
        searchFi.setText("");
    }

    private void searchKhachHang() {
        String searchText = searchFi.getText().toLowerCase();
        for (int i = 0; i < tablemodel.getRowCount(); i++) {
            boolean found = false;
            for (int j = 0; j < tablemodel.getColumnCount(); j++) {
                String value = tablemodel.getValueAt(i, j).toString().toLowerCase();
                if (value.contains(searchText)) {
                    found = true;
                    break;
                }
            }
            if (found) {
                table.setRowSelectionInterval(i, i);
                table.setSelectionBackground(Color.YELLOW);
                table.scrollRectToVisible(table.getCellRect(i, 0, true));
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Không tìm thấy kết quả nào.");
    }
    private void loadDataFromDatabase() {
        ArrayList<KhachHang> list = khachhangdao.getAllKhachHang(); // Lấy danh sách khách hàng từ DAO

        // In ra số lượng khách hàng
        System.out.println("Số lượng khách hàng lấy được: " + (list != null ? list.size() : "null"));

        tablemodel.setRowCount(0); // Xóa các hàng cũ trong tableModel
        
        if (list != null && !list.isEmpty()) { // Kiểm tra xem danh sách có null không
            for (KhachHang kh : list) {
                // Lấy điểm tích lũy, kiểm tra xem đối tượng DiemTichLuy có null không
                float diemHienTai = 0.0f; // Giá trị mặc định
                
                if (kh.getDiemTichLuy() != null) {
                    diemHienTai = kh.getDiemTichLuy().getDiemHienTai(); // Sử dụng diemHienTai từ đối tượng DiemTichLuy
                }

                // Thêm hàng vào tableModel
                tablemodel.addRow(new Object[]{
                    kh.getMaKH(),
                    kh.getTenKH(),
                    kh.getSdt(),
                    kh.getEmail(),
                    diemHienTai
                });
            }
        } else {
            // Xử lý trường hợp danh sách khách hàng là null
            System.out.println("Không có dữ liệu khách hàng để hiển thị.");
        }
    }




    public void mouseClicked(MouseEvent e) {
        // Lấy chỉ số hàng được chọn
        int row = table.getSelectedRow();
        
        // Kiểm tra nếu có hàng nào được chọn
        if (row >= 0) {
            // Hiển thị thông tin khách hàng vào các trường nhập liệu
            maKH.setText(tablemodel.getValueAt(row, 0).toString()); // Mã Khách hàng
            tenKH.setText(tablemodel.getValueAt(row, 1).toString()); // Tên Khách hàng
            sdt.setText(tablemodel.getValueAt(row, 2).toString()); // Số điện thoại
            email.setText(tablemodel.getValueAt(row, 3).toString()); // Địa chỉ
            diemTichLuy.setText(tablemodel.getValueAt(row, 4).toString()); // Điểm Hiện Tại
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
