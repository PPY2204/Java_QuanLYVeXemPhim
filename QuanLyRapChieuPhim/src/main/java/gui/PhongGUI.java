package gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import org.kordamp.ikonli.swing.FontIcon;

import dao.PhongChieuDAO;
import entity.PhongChieu;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class PhongGUI extends JPanel implements ActionListener,MouseListener{
    private JPanel boxtren;
	private JPanel boxduoi;
	private JLabel titlelb;
	private JPanel box1;
	private JPanel box2;
	private JPanel box3;
	private JPanel box4;
	private JTextField maPhongfi;
	private JTextField tenPhongfi;
	private JTextField sucChuafi;
	private JPanel boxtrai;
	private JPanel boxphai;
	private JPanel boxtraiphai;
	private DefaultTableModel tablemodel;
	private JTable table;
	private JPanel boxnut;
	private JButton suabt;
	private JButton xoatrangbt;
	private JTextField timfi;
	private JButton timbt;
	private PhongChieuDAO phongchieudao;
	private JComboBox<String> phongcombo;

	public PhongGUI() {
    	setLayout(new BorderLayout());
    	phongchieudao = new PhongChieuDAO();
        // Khởi tạo các panel và layout
        boxtren = new JPanel();
        boxtren.setLayout(new BoxLayout(boxtren, BoxLayout.Y_AXIS));
        boxtren.setBorder(new LineBorder(Color.BLACK, 2, true));

        boxduoi = new JPanel();
        boxduoi.setLayout(new BorderLayout());
        boxduoi.setBorder(new LineBorder(Color.BLACK, 2, true));

        titlelb = new JLabel("THÔNG TIN PHÒNG");
        titlelb.setFont(new Font("Roboto", Font.BOLD, 30));
        titlelb.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Khởi tạo các box con
        box1 = createInputBox("Mã phòng:", maPhongfi = new JTextField(15));
        box2 = createInputBox("Tên phòng:", tenPhongfi = new JTextField(10));
        box3 = createInputBox("Sức chứa:", sucChuafi= new JTextField(10));
        String[] options = {"Hoạt động", "Bảo trì"};

        // Tạo ComboBox với mảng các lựa chọn
        phongcombo = new JComboBox<>(options);
        box4 = createInputBox("Trạng thái phòng:", phongcombo);
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

        boxphai.add(box2);
        boxphai.add(Box.createVerticalStrut(10));
        boxphai.add(box4);
        boxphai.add(Box.createVerticalStrut(10));

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
        String[] colname = {"Mã phòng","Tên phòng","Sức Chứa","Trạng thái"};
        tablemodel = new DefaultTableModel(colname, 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        table = new JTable(tablemodel);
        table.addMouseListener(this);
        //tạo nút
        boxnut = new JPanel();
        boxnut.setLayout(new BoxLayout(boxnut, BoxLayout.X_AXIS));
        boxnut.setBorder(new LineBorder(Color.BLACK, 2, true));

        // Thêm các nút vào boxnut
        suabt = createButton("Sửa", 80, 30);
        xoatrangbt = createButton("Xóa Trắng", 100, 30);
        timfi = new JTextField(10);
        timfi.setPreferredSize(new Dimension(80, 30));
        timfi.setMaximumSize(new Dimension(500, 25));
        timfi.setMinimumSize(new Dimension(500, 25));
        timbt = createButton("Tìm", 80, 30);
        //thêm sự kiên cho nút
        suabt.addActionListener(this);
        xoatrangbt.addActionListener(this);
        timbt.addActionListener(this);

        boxnut.add(Box.createHorizontalStrut(150));
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
		if(e.getSource().equals(suabt)) {
			suaPhong();
		}
		if(e.getSource().equals(xoatrangbt)) {
			xoaTrangPhong();
		}
		if(e.getSource().equals(timbt)) {
			timPhong(timfi.getText());
			
		}
	}


	// Trong PhongGUI.java
	private void timPhong(String maPhong) {
	    boolean found = phongchieudao.timKiemPhongChieu(maPhong); // Gọi hàm tìm kiếm từ PhongDAO
	    
	    if (found) {
	        // Nếu tìm thấy, duyệt qua các hàng của bảng để tìm hàng tương ứng
	        for (int i = 0; i < table.getRowCount(); i++) {
	            if (table.getValueAt(i, 0).toString().equals(maPhong)) {
	                // Tô màu hàng được tìm thấy
	                table.setRowSelectionInterval(i, i); // Chọn hàng
	                table.setSelectionBackground(Color.YELLOW);
	                table.scrollRectToVisible(table.getCellRect(i, 0, true)); // Cuộn đến hàng

	                break;
	            }
	        }
	    } else {
	        JOptionPane.showMessageDialog(this, "Không tìm thấy mã phòng chiếu: " + maPhong);
	    }
	}

	private void xoaTrangPhong() {
		// Xóa nội dung trong các JTextField
        maPhongfi.setText("");
        maPhongfi.requestFocus();
        tenPhongfi.setText("");
        sucChuafi.setText("");
        
        // Đặt JComboBox về giá trị mặc định, ví dụ mặc định là "Hoạt động"
        phongcombo.setSelectedIndex(0);  // 0 là "Hoạt động" nếu "Hoạt động" là phần tử đầu tiên
	}
	// Trong PhongGUI.java
	private void suaPhong() {
	    String maPhong = maPhongfi.getText().trim();
	    String tenPhong = tenPhongfi.getText().trim();
	    int sucChua;
	    
	    // Lấy giá trị từ ComboBox và chuyển thành boolean
	    String trangThaiStr = phongcombo.getSelectedItem().toString();
	    boolean trangThai = trangThaiStr.equals("1"); // "1" cho Hoạt động, "0" cho Bảo trì
	    System.out.println("Trạng thái phòng chiếu: " + trangThai);
	    
	    try {
	        sucChua = Integer.parseInt(sucChuafi.getText().trim());
	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(this, "Sức chứa phải là số nguyên.");
	        return;
	    }

	    // Gọi hàm cập nhật trong PhongDAO
	    boolean isUpdated = phongchieudao.capNhatPhongChieu(maPhong, tenPhong, sucChua, trangThai);

	    if (isUpdated) {
	        // Nếu cập nhật thành công, cập nhật bảng (table) với giá trị mới
	        int selectedRow = table.getSelectedRow(); // Lấy dòng đã chọn trong bảng

	        if (selectedRow != -1) { // Kiểm tra có dòng được chọn không
	            // Cập nhật các giá trị mới vào bảng
	            tablemodel.setValueAt(maPhong, selectedRow, 0);    // Cập nhật Mã phòng chiếu
	            tablemodel.setValueAt(tenPhong, selectedRow, 1);   // Cập nhật Tên phòng chiếu
	            tablemodel.setValueAt(sucChua, selectedRow, 2);    // Cập nhật Sức chứa
	            tablemodel.setValueAt(trangThai ? "Hoạt động" : "Bảo trì", selectedRow, 3); // Cập nhật Trạng thái

	            JOptionPane.showMessageDialog(this, "Cập nhật phòng chiếu thành công.");
	        }
	       
	    } else {
	        JOptionPane.showMessageDialog(this, "Không tìm thấy phòng chiếu với mã " + maPhong + " hoặc có lỗi khi cập nhật.");
	    }
	}


	private void loadDataFromDatabase() {
	    // Xóa hết dữ liệu cũ trong bảng
	    tablemodel.setRowCount(0);

	    // Lấy danh sách phòng chiếu từ DAO
	    ArrayList<PhongChieu> list = phongchieudao.getAllPhongChieu(); // Lấy danh sách phòng chiếu từ DAO
	    
	    // Kiểm tra xem danh sách có null không
	    if (list != null) {
	        // Duyệt qua danh sách và thêm từng dòng vào bảng
	        for (PhongChieu phongChieu : list) {
	            // Thêm dòng vào bảng trực tiếp với phương thức addRow
	        	tablemodel.addRow(new Object[] {
	                phongChieu.getMaPhongChieu(),                // Mã phòng chiếu
	                phongChieu.getTenPhongChieu(),               // Tên phòng chiếu
	                phongChieu.getSucChua(),                     // Sức chứa
	                phongChieu.isTrangThaiPhong() ? "Hoạt động" : "Bảo trì"  // Trạng thái phòng chiếu
	            });
	        }
	    } else {
	        // Xử lý trường hợp danh sách phòng chiếu là null
	        System.out.println("Không có dữ liệu phòng chiếu để hiển thị.");
	    }
	}



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
		int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            // Lấy dữ liệu từ dòng đã chọn
            String maPhongChieu = table.getValueAt(selectedRow, 0).toString();
            String tenPhongChieu = table.getValueAt(selectedRow, 1).toString();
            String sucChua = table.getValueAt(selectedRow, 2).toString();
            String trangThai = table.getValueAt(selectedRow, 3).toString();
            System.out.println(trangThai);
            // Đẩy dữ liệu vào các JTextField
            maPhongfi.setText(maPhongChieu);
            tenPhongfi.setText(tenPhongChieu);
            sucChuafi.setText(sucChua);
            phongcombo.setSelectedItem(trangThai);
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
