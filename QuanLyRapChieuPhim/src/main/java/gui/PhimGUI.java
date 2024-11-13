package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Properties;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import dao.DanhMucPhimDAO;
import dao.NhaSanXuatDAO;
import dao.NuocSanXuatDAO;
import dao.PhimDAO;
import entity.*;

public class PhimGUI extends JPanel implements ActionListener {
    private JPanel boxTitle, boxduoi, boxnut, boxtrai, boxphai, boxtraiphai;
    private JLabel titlelb;
    private JTextField tenPhim, daoDien, dienVien, ngonNgu, thoiLuong, doTuoiGioiHan, dinhDang, hinhAnh, txtSearch;
    private JTextArea moTa;
    private JDatePickerImpl ngayKhoiChieu, ngayPhatHanh;
    private JButton btnAdd, btnDelete, btnUpdate, btnReload, btnSearch;
    private DefaultTableModel tablemodel;
    private JTable table;
    private JComboBox<String> nhaSanXuat , quocGia;
    private SqlDateModel modelDateNgayKhoiChieu;
    private SqlDateModel modelDateNgayPhatHanh;
    private JDatePanelImpl datePanelNgayKhoiChieu;
    private JDatePanelImpl datePanelNgayPhatHanh;
	private JComboBox<String> theLoai;
	private JPanel boxContainer;
	private JPanel boxNorth;
	private JScrollPane scrMoTa;
	public int currentPage = 1;
    public int rowsPerPage = 12;
    public int totalPages;
    public int totalRows ;
	private JLabel lblPageInfo;
	private JButton btnFirst;
	private JButton btnNext;
	private JButton btnPrev;
	private JButton btnLast;
	private JPanel pPag;
	private JScrollPane scrTable;
	private PhimDAO phim_dao;
	private DanhMucPhimDAO dm_DAO;
	private NhaSanXuatDAO nsx_DAO;
	private NuocSanXuatDAO nuoc_DAO;
	private DanhMucPhim dm;
	private NhaSanXuat nsx;
	private NuocSanXuat nuoc;
	private JButton btnDeleteInput;

    public PhimGUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        initializeTitlePanel();
        
        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");
        
        JPanel boxRight = new JPanel();
        boxRight.setLayout(new BoxLayout(boxRight, BoxLayout.Y_AXIS));
        modelDateNgayKhoiChieu = new SqlDateModel();
        datePanelNgayKhoiChieu = new JDatePanelImpl(modelDateNgayKhoiChieu, properties);
        boxRight.add(createInputBox("Đạo diễn:", daoDien = new JTextField(10)));
        boxRight.add(Box.createVerticalStrut(10));
        boxRight.add(createInputBox("Ngày khởi chiếu:", ngayKhoiChieu = new CustomDatePicker(datePanelNgayKhoiChieu, new DateLabelFormatter())));
        boxRight.add(Box.createVerticalStrut(10));
       
        boxRight.add(createInputBox("Thể loại:", theLoai = new JComboBox<>()));
        loadComboBoxDanhMuc();
        boxRight.add(Box.createVerticalStrut(10));
        boxRight.add(createInputBox("Quốc gia:", quocGia = new JComboBox<>()));
        loadComboBoxNuocSX();
        boxRight.add(Box.createVerticalStrut(10));
        boxRight.add(createInputBox("Ngôn ngữ:", ngonNgu = new JTextField(15)));
        boxRight.add(Box.createVerticalStrut(10));
        boxRight.add(createInputBox("Định dạng:", dinhDang = new JTextField(15)));
        boxRight.add(Box.createVerticalStrut(10));
        boxRight.add(createInputBox("Hình ảnh:", hinhAnh = new JTextField(15)));
        
        JPanel boxLeft = new JPanel();
        boxLeft.setLayout(new BoxLayout(boxLeft, BoxLayout.Y_AXIS));
        modelDateNgayPhatHanh = new SqlDateModel();
        datePanelNgayPhatHanh = new JDatePanelImpl(modelDateNgayPhatHanh, properties);
        boxLeft.add(createInputBox("Tên phim:", tenPhim = new JTextField(15)));
        boxLeft.add(Box.createVerticalStrut(10));
        boxLeft.add(createInputBox("Ngày phát hành:", ngayPhatHanh = new CustomDatePicker(datePanelNgayPhatHanh, new DateLabelFormatter())));
        boxLeft.add(Box.createVerticalStrut(10));
        boxLeft.add(createInputBox("Diễn viên:", dienVien = new JTextField(10)));
        boxLeft.add(Box.createVerticalStrut(10));
        boxLeft.add(createInputBox("Nhà sản xuất:", nhaSanXuat = new JComboBox<>()));
        loadComboBoxNhaSX();
        boxLeft.add(Box.createVerticalStrut(10));
        boxLeft.add(createInputBox("Thời lượng:", thoiLuong = new JTextField(10)));
        boxLeft.add(Box.createVerticalStrut(10));
        boxLeft.add(createInputBox("Độ tuổi giới hạn:", doTuoiGioiHan = new JTextField(10)));
        boxLeft.add(Box.createVerticalStrut(10));
        moTa= new JTextArea(2,3);
        moTa.setWrapStyleWord(true);
        moTa.setLineWrap(true);
        scrMoTa = new JScrollPane(moTa);
        boxLeft.add(createInputBox("Mô tả:", scrMoTa ));
        
        boxContainer = new JPanel(new GridLayout(1,2,20,20));
        boxContainer.add(boxLeft);
        boxContainer.add(boxRight);
        
        initializeButtonPanel();
        initializeTablePanel();
        add(boxTitle);
        add(Box.createVerticalStrut(10));
        add(boxContainer);
        add(Box.createVerticalStrut(10));
        add(boxnut);
        add(Box.createVerticalStrut(10));
        add(boxduoi);
        add(Box.createVerticalStrut(40));

        btnFirst.addActionListener(this);
        btnNext.addActionListener(this);
        btnPrev.addActionListener(this);
        btnLast.addActionListener(this);
        
        btnAdd.addActionListener(this);
        btnDelete.addActionListener(this);
        btnDeleteInput.addActionListener(this);
        btnReload.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnSearch.addActionListener(this);
        
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int row = table.getSelectedRow();
                    loadDataPhimToForm(row);
                }
            }
        });
        
    }

    private void initializeTitlePanel() {
        boxTitle = new JPanel();
        boxTitle.setLayout(new BoxLayout(boxTitle, BoxLayout.Y_AXIS));
        boxTitle.setBorder(new LineBorder(Color.BLACK, 2, true));

        titlelb = new JLabel("THÔNG TIN PHIM");
        titlelb.setFont(new Font("Roboto", Font.BOLD, 30));
        titlelb.setAlignmentX(Component.CENTER_ALIGNMENT);

        boxTitle.add(titlelb);
        boxTitle.add(Box.createVerticalStrut(10));
    }
   

    private void initializeButtonPanel() {
        boxnut = new JPanel();
        boxnut.setLayout(new BoxLayout(boxnut, BoxLayout.X_AXIS));
        
        btnAdd = createButton("Thêm", 80, 30);
        btnDelete = createButton("Xóa", 80, 30);
        btnDeleteInput = createButton("Xóa trắng", 100, 30);
        btnUpdate = createButton("Sửa", 80, 30);
        btnReload = createButton("Làm mới", 100, 30);
        btnSearch = createButton("Tìm", 80, 30);
        
        txtSearch = new JTextField(10);
        txtSearch.setMaximumSize(new Dimension(500, 30));
        
        boxnut.add(Box.createHorizontalStrut(40));
        boxnut.add(btnAdd);
        boxnut.add(Box.createHorizontalStrut(10));
        boxnut.add(btnDelete);
        boxnut.add(Box.createHorizontalStrut(10));
        boxnut.add(btnDeleteInput);
        boxnut.add(Box.createHorizontalStrut(10));
        boxnut.add(btnUpdate);
        boxnut.add(Box.createHorizontalStrut(10));
        boxnut.add(btnReload);
        boxnut.add(Box.createHorizontalStrut(10));
        boxnut.add(txtSearch);
        boxnut.add(Box.createHorizontalStrut(10));
        boxnut.add(btnSearch);
        boxnut.add(Box.createHorizontalStrut(70));
    }

    
    private void initializeTablePanel() {
    	
        // Table setup with extended column headers
        String[] colname = {"Mã phim", "Tên phim", "Đạo diễn", "Diễn viên", "Thể loại", "Nhà sản xuất", "Quốc gia", "Ngôn ngữ", "Ngày phát hành", "Ngày khởi chiếu", "Độ tuổi giới hạn", "Thời lượng", "Định dạng", "Mô tả", "Hình ảnh"};
        
        tablemodel = new DefaultTableModel(colname, 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        table = new JTable(tablemodel);
        table.setRowHeight(30);
        table.setPreferredScrollableViewportSize(new Dimension(800, 100));
        try {
        	loadDataPhimToTable(currentPage, rowsPerPage);
        }catch (Exception e) {
			e.printStackTrace();
		}
        
        
        lblPageInfo = new JLabel();
        pPag = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnFirst = new JButton("<<");
        btnPrev = new JButton("<");
        btnNext = new JButton(">");
        btnLast = new JButton(">>");

        pPag.add(btnFirst);
        pPag.add(btnPrev);
        lblPageInfo.setText("1" + "/" + totalPages);
        pPag.add(lblPageInfo);
        pPag.add(btnNext);
        pPag.add(btnLast);
        boxduoi = new JPanel();
        boxduoi.setLayout(new BorderLayout());
        
        boxNorth = new JPanel();
        boxNorth.add(boxContainer, BorderLayout.CENTER);
        boxNorth.add(boxnut,BorderLayout.SOUTH);
        boxduoi.add(boxNorth, BorderLayout.NORTH);
        boxduoi.add(scrTable = new JScrollPane(table), BorderLayout.CENTER);
        boxduoi.add(pPag, BorderLayout.SOUTH);
        
    }
    
    
    
    

    private JPanel createInputBox(String labelText, JComponent component) {
        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.X_AXIS));
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(150, 30));
        box.add(label);
        box.add(Box.createHorizontalStrut(10));
        box.add(component);
        return box;
    }

    private JButton createButton(String text, int width, int height) {
        JButton button = new JButton(text);
        button.setBackground(Color.decode("#35a06c"));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(width, height));
        button.setMaximumSize(new Dimension(width, height));
        return button;
    }

    class CustomDatePicker extends JDatePickerImpl {
        public CustomDatePicker(JDatePanelImpl datePanel, JFormattedTextField.AbstractFormatter formatter) {
            super(datePanel, formatter);
            getJFormattedTextField().setText("Chọn ngày");
            getJFormattedTextField().addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (getJFormattedTextField().getText().equals("Chọn ngày")) {
                        getJFormattedTextField().setText("");
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (getJFormattedTextField().getText().isEmpty()) {
                        getJFormattedTextField().setText("Chọn ngày");
                    }
                }
            });
        }
    }

    class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private String datePattern = "dd-MM-yyyy";
        private java.text.SimpleDateFormat dateFormatter = new java.text.SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws java.text.ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws java.text.ParseException {
            if (value != null) {
                java.util.Calendar cal = (java.util.Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "";
        }
    }

    public void loadComboBoxDanhMuc(){
        try {
            dm_DAO = new DanhMucPhimDAO();
            ArrayList<DanhMucPhim> listDanhMuc = dm_DAO.getAllDanhMuc();
            for(DanhMucPhim dm : listDanhMuc){
                theLoai.addItem(dm.getTenDanhMuc());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public void loadComboBoxNhaSX(){
        try {
            nsx_DAO = new NhaSanXuatDAO();
            ArrayList<NhaSanXuat> listNhaSX = nsx_DAO.getAllNhaSanXuat();
            for(NhaSanXuat nsx : listNhaSX){
                nhaSanXuat.addItem(nsx.getTenNSX());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public void loadComboBoxNuocSX(){
        try {
            nuoc_DAO = new NuocSanXuatDAO();
            ArrayList<NuocSanXuat> listQuocGia = nuoc_DAO.getAllNuocSanXuat();
            for(NuocSanXuat nuoc : listQuocGia){
                quocGia.addItem(nuoc.getTenNuocSX());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
   
    public void loadDataPhimToTable(int currentPage, int rowsPerPage) throws Exception {
        phim_dao = new PhimDAO();
        totalRows = phim_dao.countPhim();
        totalPages = (int) Math.ceil((double) totalRows / rowsPerPage);
        tablemodel.setRowCount(0);
        Object[][] rowsData = phim_dao.loadDataToTable(currentPage, rowsPerPage);
        for (Object[] rowData : rowsData) {
        	tablemodel.addRow(rowData);
        }
    }
   
    public void loadDataPhimToForm(int row){
        if( row != -1){
            String maThuoc = String.valueOf(table.getValueAt(row, 0));
            Phim phim = phim_dao.getPhimByMaPhim(maThuoc);
            if (phim != null) {
                tenPhim.setText(phim.getTenPhim());
                daoDien.setText(phim.getDaoDien());

                java.sql.Date sqlDate = null;
                if (phim.getNgayPhatHanh() != null) {
                    sqlDate = new java.sql.Date(phim.getNgayPhatHanh().getTime());
                }
                ((SqlDateModel) ngayPhatHanh.getModel()).setValue(sqlDate);
                ngayPhatHanh.getJFormattedTextField().repaint();

                if (phim.getNgayKhoiChieu() != null) {
                    sqlDate = new java.sql.Date(phim.getNgayKhoiChieu().getTime());
                }
                ((SqlDateModel) ngayKhoiChieu.getModel()).setValue(sqlDate);
                ngayKhoiChieu.getJFormattedTextField().repaint();

                dienVien.setText(phim.getDienVien());
                try {
                    dm_DAO = new DanhMucPhimDAO();
                    dm = dm_DAO.timDanhMuc(phim.getDanhMuc().getMaDanhMuc());
                    if (dm != null) {
                        theLoai.setSelectedItem(dm.getTenDanhMuc());
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                
                try {
                    nsx_DAO = new NhaSanXuatDAO();
                    nsx = nsx_DAO.timNhaSX(phim.getNhaSX().getMaNSX());
                    if (nsx != null) {
                        nhaSanXuat.setSelectedItem(nsx.getTenNSX());
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                
                try {
                    nuoc_DAO = new NuocSanXuatDAO();
                    nuoc = nuoc_DAO.timNuocSanXuat(phim.getQuocGia().getMaNuocSX());
                    if (nuoc != null) {
                        quocGia.setSelectedItem(nuoc.getTenNuocSX());
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                thoiLuong.setText(String.valueOf(phim.getThoiLuong()));
                ngonNgu.setText(phim.getNgonNgu());
                doTuoiGioiHan.setText(String.valueOf(phim.getDoTuoiGioiHan()));
                dinhDang.setText(phim.getDinhDang());
                moTa.setText(phim.getMoTa());
                hinhAnh.setText(phim.getHinhAnh());
            } else {
                System.out.println("Phim không tồn tại.");
            }
        }
    }
    
	public void clearData() {
		tablemodel.setRowCount(0);
	}
    
	public void timKiemPhim(String maPhimTimKiem) {
	    if (!maPhimTimKiem.isEmpty()) {
	        // Gọi hàm timKiemPhim trong PhimDAO
	        Phim phimTimThay = phim_dao.timKiemPhim(maPhimTimKiem);

	        if (phimTimThay != null) {
	            // Duyệt qua các hàng để tìm hàng có mã phim trùng khớp
	            for (int i = 0; i < table.getRowCount(); i++) {
	                if (table.getValueAt(i, 0).toString().equals(maPhimTimKiem)) {
	                    // Tô vàng hàng tìm thấy
	                    table.setRowSelectionInterval(i, i);
	                    table.setSelectionBackground(Color.YELLOW);
	                    table.scrollRectToVisible(table.getCellRect(i, 0, true));

	                    // Thông báo tìm thấy phim
	                    JOptionPane.showMessageDialog(null, "Tìm thấy phim: " + phimTimThay.getTenPhim());
	                    return;
	                }
	            }
	        } else {
	            // Thông báo không tìm thấy phim
	            JOptionPane.showMessageDialog(null, "Không tìm thấy phim với mã: " + maPhimTimKiem);
	        }
	    } else {
	        JOptionPane.showMessageDialog(null, "Vui lòng nhập mã phim để tìm kiếm.");
	    }
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		Runnable loadDataAndUpdate = () -> {
            try {
            	loadDataPhimToTable(currentPage, rowsPerPage);
                lblPageInfo.setText(currentPage + " / " + totalPages);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu : " + ex.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        };
        
        if (o.equals(btnFirst) && currentPage > 1) {
            currentPage = 1;
            loadDataAndUpdate.run();
            System.out.println("Clicked ");
        }

        if (o.equals(btnLast) && currentPage < totalPages) {
            currentPage = totalPages;
            loadDataAndUpdate.run();
        }

        if (o.equals(btnPrev) && currentPage > 1) {
            currentPage--;
            loadDataAndUpdate.run();
        }

        if (o.equals(btnNext) && currentPage < totalPages) {
            currentPage++;
            loadDataAndUpdate.run();
        }
        
        if (o.equals(btnAdd) ) {
        	PhimDAO phim_dao = new PhimDAO();

            String maPhim = phim_dao.tuSinhMaPhim();
            String tenphim = tenPhim.getText().trim();
            java.util.Date dateNgayKhoiChieu = modelDateNgayKhoiChieu.getValue();
            java.sql.Date ngayKC = new java.sql.Date(dateNgayKhoiChieu.getTime());
            String daodien = daoDien.getText().trim();
            DanhMucPhim danhMuc = dm_DAO.getDanhMuc(theLoai.getSelectedItem().toString());
            String dienvien = dienVien.getText().trim();
            java.util.Date dateNgayPhatHanh = modelDateNgayPhatHanh.getValue();
            java.sql.Date ngayPH = new java.sql.Date(dateNgayPhatHanh.getTime());
            String ngonngu = ngonNgu.getText().trim();
            String mota = moTa.getText().trim();
            String hinhanh = hinhAnh.getText().trim();
            String dinhdang = dinhDang.getText().trim();
            boolean tinhtrang = true;
            int dotuoigioihan = Integer.parseInt(doTuoiGioiHan.getText().trim());
            NuocSanXuat nuocSanXuat = nuoc_DAO.getNuocSX(quocGia.getSelectedItem().toString());
            int thoiluong = Integer.parseInt(thoiLuong.getText().trim());
            NhaSanXuat nhaSX = nsx_DAO.getNSX(nhaSanXuat.getSelectedItem().toString());
          

            Phim phim = new Phim(maPhim, tenphim, ngayKC, daodien, danhMuc, dienvien, ngayPH, ngonngu, mota, hinhanh, dinhdang, tinhtrang, dotuoigioihan, thoiluong, nuocSanXuat, nhaSX);
            
            boolean themPhimThanhCong = phim_dao.createPhim(phim);
            if (themPhimThanhCong) {
                JOptionPane.showMessageDialog(null, "Thêm phim thành công!");
            } else {
                JOptionPane.showMessageDialog(null, "Thêm phim thất bại");
            }
        }
        
        if (o.equals(btnDelete) ) {
        	int row = table.getSelectedRow();
            if(row < 0) JOptionPane.showMessageDialog(this, "Chưa chọn dòng cần xóa");
            else{
                String maPhim = String.valueOf(table.getValueAt(row, 0));
                if(phim_dao.deletePhim(maPhim)){
                    int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa không?", "Lưu ý", JOptionPane.YES_NO_OPTION);
                    if(confirm == JOptionPane.YES_OPTION){
                        JOptionPane.showMessageDialog(this, "Đã xóa thuốc thành công");
                        clearData();
                        try {
                            loadDataPhimToTable(currentPage,rowsPerPage);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }

            }
        }
        
        if (o.equals(btnDeleteInput)) {
        	tenPhim.setText("");
        	daoDien.setText("");
        	datePanelNgayKhoiChieu.getModel().setValue(null);
        	datePanelNgayPhatHanh.getModel().setValue(null);
        	dienVien.setText("");
        	theLoai.setSelectedItem(null);
        	nhaSanXuat.setSelectedItem(null);
        	quocGia.setSelectedItem(null);
        	thoiLuong.setText("");
        	ngonNgu.setText("");
        	doTuoiGioiHan.setText("");
        	dinhDang.setText("");
        	moTa.setText("");
        	hinhAnh.setText("");
        }
        
        if (o.equals(btnReload) ) {
        	clearData();
            try {
                loadDataPhimToTable(currentPage,rowsPerPage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        
        if (o.equals(btnUpdate) ) {
        	int row = table.getSelectedRow();
            if(row < 0) JOptionPane.showMessageDialog(this, "Chưa chọn dòng cần sửa");
            else {
            	PhimDAO phim_dao = new PhimDAO();

                String maPhim = String.valueOf(table.getValueAt(row, 0));
                String tenphim = tenPhim.getText().trim();
                java.util.Date dateNgayKhoiChieu = modelDateNgayKhoiChieu.getValue();
                java.sql.Date ngayKC = new java.sql.Date(dateNgayKhoiChieu.getTime());
                String daodien = daoDien.getText().trim();
                DanhMucPhim danhMuc = dm_DAO.getDanhMuc(theLoai.getSelectedItem().toString());
                String dienvien = dienVien.getText().trim();
                java.util.Date dateNgayPhatHanh = modelDateNgayPhatHanh.getValue();
                java.sql.Date ngayPH = new java.sql.Date(dateNgayPhatHanh.getTime());
                String ngonngu = ngonNgu.getText().trim();
                String mota = moTa.getText().trim();
                String hinhanh = hinhAnh.getText().trim();
                String dinhdang = dinhDang.getText().trim();
                boolean tinhtrang = true;
                int dotuoigioihan = Integer.parseInt(doTuoiGioiHan.getText().trim());
                NuocSanXuat nuocSanXuat = nuoc_DAO.getNuocSX(quocGia.getSelectedItem().toString());
                int thoiluong = Integer.parseInt(thoiLuong.getText().trim());
                NhaSanXuat nhaSX = nsx_DAO.getNSX(nhaSanXuat.getSelectedItem().toString());

                Phim phim = phim_dao.getPhimByMaPhim(maPhim);
                phim.setTenPhim(tenphim);
                phim.setNgayKhoiChieu(ngayKC);
                phim.setDaoDien(daodien);
                phim.setDanhMuc(danhMuc);
                phim.setDienVien(dienvien);
                phim.setNgayPhatHanh(ngayPH);
                phim.setNgonNgu(ngonngu);
                phim.setMoTa(mota);
                phim.setHinhAnh(hinhanh);
                phim.setDinhDang(dinhdang);
                phim.setTinhTrang(tinhtrang);
                phim.setDoTuoiGioiHan(dotuoigioihan);
                phim.setQuocGia(nuocSanXuat);
                phim.setThoiLuong(thoiluong);
                phim.setNhaSX(nhaSX);

                boolean capNhatPhimThanhCong = phim_dao.updatePhim(phim);
                if (capNhatPhimThanhCong) {
                    JOptionPane.showMessageDialog(null, "Cập nhật phim thành công!");

                } else {
                    JOptionPane.showMessageDialog(null, "Cập nhật thất bại");
                }
            }
        	
        }
        if(o.equals(btnSearch)) {
        	timKiemPhim(txtSearch.getText());
        }
		
	}
}
