package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.jfree.chart.ui.Align;

import dao.ChiTietKhuyenMaiDAO;
import dao.ChuongTrinhKhuyenMaiDAO;
import dao.DanhMucPhimDAO;
import dao.DiemTichLuyDAO;
import dao.KhachHangDAO;
import dao.PhimDAO;
import dao.PhongChieuDAO;
import dao.SuatChieuDAO;
import dao.VeDAO;
import entity.ChiTietKhuyenMai;
import entity.ChuongTrinhKhuyenMai;
import entity.DanhMucPhim;
import entity.DiemTichLuy;
import entity.KhachHang;
import entity.Phim;
import entity.PhongChieu;
import entity.SuatChieu;

public class DatVeGUI extends JPanel implements ActionListener{

    private JLabel lblTitle, totalCostLabel;
    private JComboBox<String> movieComboBox;
    private JTextField ticketQuantityField, txtTotal, txtDiscount, txtPayment, txtGiven, txtChange;
    private JTable ticketTable;
    private DefaultTableModel model;
	private JTextField txtTimKiemKH;
	private JButton btnTimKiemKH;
	private JTextField txtTenKhachHang;
	private JTextField txtSDTKH;
	private JTextField txtGiaTriDiemTL;
	private JCheckBox cmbDoiDiem;
	private JComboBox cmbChonLoaiKM;
	private JLabel lbNoiDungMoTa;
	private JRadioButton rbtnTienMat;
	private ButtonGroup paymentMethodGroup;
	private JRadioButton rbtnViDienTu;
	private JTextField txtTienKhachTra;
	private JTextField text_TienThoi;
	private JTextField text_TienThue;
	private JTextField txt_TienKhuyenMai;
	private JTextField txt_TienGiam;
	private JTextField txt_tongTienValue;
	private JButton btnThanhToan;
	private JPanel panelRight;
	private JScrollPane scrollCart;
	private JPanel panelCenter;
	private JPanel panelLeft;
	private JPanel panelContentCenter;
	private JComboBox<String> roomComboBox;
	private ArrayList<JButton> seatButtons;
	private JPanel pnlGhe;
	private JLabel lblTenPhim;
	private JComboBox<String> cmbTenPhim;
	private JLabel lblPhongChieu;
	private JComboBox<String> cmbPhongChieu;
	private JLabel lblSuatChieu;
	private JComboBox<Object> cmbSuatChieu;
	private PhimDAO phim_dao;
	private SuatChieuDAO suatChieu_dao;
	private Timestamp thoiGianKhoiChieu;
	private Timestamp thoiGianKetThuc;
	private PhongChieuDAO pc_dao;
	private JButton btnChonVe;
    private double ticketCost = 45000.0;
	private VeDAO ve_dao;
	private Object dtl_dao;
	private JLabel lblSDT;
	private JLabel lblDTL;
	private JLabel lblKH;
	private ChuongTrinhKhuyenMaiDAO ctkm_dao;
	private JLabel lblThue;
	private JLabel lblKM;
	private JLabel lblTongTien;
	private JLabel lblTienGiam;
	private JLabel lblTienKhachTra;
	private JLabel lblTienThoi;
	private ChuongTrinhKhuyenMaiDAO chuongtrinh_dao;
	private ChiTietKhuyenMaiDAO chiTietKM_dao;
	private Double totalPrice;
	private double tienThue;
	private double tienGiam;
	private double tienThoi; 
    public DatVeGUI() {
        setLayout(new BorderLayout());

        // Title Panel
        JPanel pnlTitle = new JPanel();
        pnlTitle.setBorder(new LineBorder(Color.BLACK, 2, true));
        lblTitle = new JLabel("Đặt vé xem phim");
        lblTitle.setFont(new Font("Roboto", Font.BOLD, 30));
        pnlTitle.add(lblTitle);
        add(pnlTitle, BorderLayout.NORTH);

        JPanel pnlContainer = new JPanel(new GridLayout(1, 2, 10, 10));
        pnlContainer.setBorder(new EmptyBorder(20, 0, 0, 20));

        JPanel pnlRoom = createSeatRoomPanel();
        pnlContainer.add(pnlRoom);


        JPanel pnlTicket = createTicketPanel();
        pnlContainer.add(pnlTicket);

        add(pnlContainer, BorderLayout.CENTER);
        
        cmbTenPhim.addActionListener(this);
        cmbSuatChieu.addActionListener(this);
        cmbPhongChieu.addActionListener(this);
        btnChonVe.addActionListener(this);
        btnTimKiemKH.addActionListener(this);
        rbtnTienMat.addActionListener(this);
        rbtnViDienTu.addActionListener(this);
        cmbChonLoaiKM.addActionListener(this);
        txtTienKhachTra.addActionListener(this);
    }
    
    private JPanel createSeatRoomPanel() {
        JPanel pnlRoom = new JPanel(new BorderLayout());
        pnlRoom.setBorder(new EmptyBorder(0, 20, 20, 0));

        // Screen Label
        JPanel pnlRoomTitle = new JPanel();
        JLabel lblManHinh = new JLabel("Màn hình");
        lblManHinh.setFont(new Font("Roboto", Font.BOLD, 15));
        pnlRoomTitle.setBackground(Color.decode("#35a06c"));
        pnlRoomTitle.add(lblManHinh);
        pnlRoom.add(pnlRoomTitle, BorderLayout.NORTH);

        // Seat Grid
        pnlGhe = new JPanel(new GridLayout(8, 8, 10, 10));
        pnlGhe.setBorder(new EmptyBorder(20, 0, 0, 0));
        char rowLabel = 'A';
        while (rowLabel <= 'H') {
            for (int col = 1; col <= 8; col++) {
                String seatLabel = rowLabel + String.valueOf(col);
                JButton seatButton = createButton(seatLabel, 15, 15);
                seatButton.setBackground(getSeatColor(rowLabel));
                pnlGhe.add(seatButton);
            }
            rowLabel++;
        }
        setupSeatSelectionListeners();
        pnlRoom.add(pnlGhe, BorderLayout.CENTER);

        pnlRoom.add(createSeatLegend(), BorderLayout.SOUTH);

        return pnlRoom;
    }

    private Color getSeatColor(char rowLabel) {
        if (rowLabel >= 'G') return Color.RED;
        else if (rowLabel >= 'D') return Color.decode("#074f2c");
        return Color.decode("#35a06c");
    }

    // Seat Legend Panel
    private JPanel createSeatLegend() {
        JPanel pnlLoaiGhe = new JPanel(new GridLayout(1, 4, 10, 10));
        pnlLoaiGhe.add(createLegendItem("Ghế thường", Color.decode("#35a06c")));
        pnlLoaiGhe.add(createLegendItem("Ghế VIP", Color.decode("#074f2c")));
        pnlLoaiGhe.add(createLegendItem("Ghế Couple", Color.RED));
        pnlLoaiGhe.add(createLegendItem("Ghế đã chọn", Color.BLACK));
        return pnlLoaiGhe;
    }

    // Helper method to create a legend item
    private JPanel createLegendItem(String labelText, Color color) {
        JPanel panel = new JPanel();
        JButton btn = createButton("", 25, 25);
        btn.setBackground(color);
        panel.add(btn);
        panel.add(new JLabel(labelText));
        return panel;
    }

    private JPanel createTicketPanel() {
        JPanel pnlTicket = new JPanel(new BorderLayout());
        pnlTicket.setBorder(new EmptyBorder(0, 10, 10, 0));

        panelRight = new JPanel();
        panelRight.setLayout(new BoxLayout(panelRight, BoxLayout.Y_AXIS));
        panelRight.add(Box.createVerticalStrut(10));
        panelRight.add(createTicketTablePanel());
        panelRight.add(Box.createVerticalStrut(10));
        panelRight.add(createCustomerInfoPanel());
        panelRight.add(Box.createVerticalStrut(10));
        panelRight.add(createPaymentPanel());
        panelRight.add(Box.createVerticalStrut(10));
        panelRight.add(createActionButtonPanel());

        JPanel panelCenter = new JPanel(new BorderLayout());
        panelCenter.add(createInputInforVe(), BorderLayout.NORTH);
        
        panelCenter.add(panelRight, BorderLayout.CENTER);

        pnlTicket.add(panelCenter, BorderLayout.CENTER);
        return pnlTicket;
    }


    private JPanel createCustomerInfoPanel() {
        JPanel panelKhachHang = new JPanel(new FlowLayout(FlowLayout.LEFT));
        TitledBorder borderKhachHang = BorderFactory.createTitledBorder("Thông tin khách hàng");
        borderKhachHang.setTitleColor(Color.GRAY);
        panelKhachHang.setBorder(borderKhachHang);
        panelKhachHang.setPreferredSize(new Dimension(600, 160));

        // Create panel for search
        JPanel panelTimKiem = new JPanel(new FlowLayout(FlowLayout.LEFT));
       
        btnTimKiemKH = createStyledButton("Tìm kiếm", Color.decode("#35a06c"));
        panelTimKiem.add(lblSDT = new JLabel("Số điện thoại:"));
        panelTimKiem.add(txtSDTKH = new JTextField(13));
        txtSDTKH.setPreferredSize(new Dimension(25,25));
        panelTimKiem.add(btnTimKiemKH);
        panelKhachHang.add(panelTimKiem);

        JPanel panelCustomerDetails = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelCustomerDetails.add(lblKH = new JLabel("Tên khách hàng:"));
        panelCustomerDetails.add(txtTenKhachHang = new JTextField(10));
        txtTenKhachHang.setPreferredSize(new Dimension(10,25));
        panelCustomerDetails.add(lblDTL = new JLabel("Điểm tích lũy:"));
        panelCustomerDetails.add(txtGiaTriDiemTL = new JTextField(10));
        txtGiaTriDiemTL.setPreferredSize(new Dimension(10,25));
        panelCustomerDetails.add(cmbDoiDiem = new JCheckBox("Đổi điểm tích lũy"));
        panelKhachHang.add(panelCustomerDetails);

        JPanel panelKhuyenMai = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelKhuyenMai.setPreferredSize(new Dimension(600, 100));
        JLabel lbChonLoaiKH = new JLabel("Khuyến mãi: ");
        lbChonLoaiKH.setPreferredSize(lblKH.getPreferredSize());
        lblSDT.setPreferredSize(lblKH.getPreferredSize());
        cmbChonLoaiKM = new JComboBox<>(new String[]{"Chọn loại khuyến mãi"});
        loadDataToCmbKhuyenMai();
        panelKhuyenMai.add(lbChonLoaiKH);
        panelKhuyenMai.add(cmbChonLoaiKM);
        panelKhachHang.add(panelKhuyenMai);

        return panelKhachHang;
    }


    private JPanel createPaymentPanel() {
        JPanel panelThanhToan = new JPanel();
        panelThanhToan.setPreferredSize(new Dimension(600, 170));
        panelThanhToan.setLayout(new BoxLayout(panelThanhToan, BoxLayout.Y_AXIS));
        panelThanhToan.setBorder(BorderFactory.createTitledBorder("Thanh toán"));
        
        // Payment Method Section
        panelThanhToan.add(createPaymentMethodPanel());

        // Create labeled text fields for payment information
        JPanel pnlTinhKhauTru = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlTinhKhauTru.add(lblThue = new JLabel("VAT (10%):"));
        pnlTinhKhauTru.add(text_TienThue = new JTextField(15));
        text_TienThue.setPreferredSize(new Dimension(10,25));
        pnlTinhKhauTru.add(lblKM = new JLabel("Khuyến mãi:"));
        pnlTinhKhauTru.add(txt_TienKhuyenMai = new JTextField(15));
        txt_TienKhuyenMai.setPreferredSize(new Dimension(10,25));

        
        JPanel pnlTongTien = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlTongTien.add(lblTongTien = new JLabel("Tổng tiền:"));
        pnlTongTien.add(txt_tongTienValue = new JTextField(15));
        txt_tongTienValue.setPreferredSize(new Dimension(10,25));
        pnlTongTien.add(lblTienGiam = new JLabel("Tiền sau cùng:"));
        pnlTongTien.add(txt_TienGiam = new JTextField(15));
        txt_TienGiam.setPreferredSize(new Dimension(10,25));

        JPanel pnlTinhTienThoi = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlTinhTienThoi.add(lblTienKhachTra = new JLabel("Tiền khách trả:"));
        pnlTinhTienThoi.add(txtTienKhachTra = new JTextField(15));
        txtTienKhachTra.setPreferredSize(new Dimension(10,25));
        
        pnlTinhTienThoi.add(lblTienThoi = new JLabel("Tiền thối:"));
        pnlTinhTienThoi.add(text_TienThoi = new JTextField(15));
        text_TienThoi.setPreferredSize(new Dimension(10,25));

        lblThue.setPreferredSize(lblTienKhachTra.getPreferredSize());
        lblKM.setPreferredSize(lblTienKhachTra.getPreferredSize());
        lblTongTien.setPreferredSize(lblTienKhachTra.getPreferredSize());
        lblTienGiam.setPreferredSize(lblTienKhachTra.getPreferredSize());
        lblTienThoi.setPreferredSize(lblTienKhachTra.getPreferredSize());
        
        panelThanhToan.add(pnlTinhKhauTru);
        panelThanhToan.add(pnlTongTien);
        panelThanhToan.add(pnlTinhTienThoi);


        return panelThanhToan;
    }


    private JPanel createPaymentMethodPanel() {
        JPanel panelPhuongThucThanh = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel lbPhuongThucTT = new JLabel("Chọn phương thức thanh toán:");
        
        rbtnTienMat = new JRadioButton("Tiền mặt");
        rbtnViDienTu = new JRadioButton("Ví điện tử");

        paymentMethodGroup = new ButtonGroup();
        paymentMethodGroup.add(rbtnTienMat);
        paymentMethodGroup.add(rbtnViDienTu);

        panelPhuongThucThanh.add(lbPhuongThucTT);
        panelPhuongThucThanh.add(rbtnTienMat);
        panelPhuongThucThanh.add(rbtnViDienTu);

        return panelPhuongThucThanh;
    }


    private JPanel createActionButtonPanel() {
        JPanel panelThanhToanButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelThanhToanButton.setPreferredSize(new Dimension(300, 40));
        panelThanhToanButton.add(createStyledButton("Thanh toán", Color.decode("#35a06c")));

        return panelThanhToanButton;
    }

    private JPanel createInputInforVe() {
    	JPanel panelInput = new JPanel();
    	panelInput.setPreferredSize(new Dimension(600, 170));
    	panelInput.setLayout(new BoxLayout(panelInput, BoxLayout.Y_AXIS));
    	panelInput.setBorder(BorderFactory.createTitledBorder("Thông tin nhập"));

        JPanel pnlInforPhim = new JPanel(new GridLayout(1,2,10,10));
        JPanel pnlBoxInfor = new JPanel(new GridLayout(3,2,10,10));
        pnlBoxInfor.add(lblTenPhim = new JLabel("Tên phim:"));
        pnlBoxInfor.add(cmbTenPhim = new JComboBox<>());
        cmbTenPhim.setPreferredSize(new Dimension(100,30));
        cmbTenPhim.addItem("Chọn");
        loadDataToCmbTenPhim();
        pnlBoxInfor.add(lblSuatChieu = new JLabel("Suất chiếu:"));
        pnlBoxInfor.add(cmbSuatChieu = new JComboBox<>());
        cmbSuatChieu.setPreferredSize(new Dimension(100,30));
        cmbSuatChieu.addItem("Chọn");
        loadDataToCmbSuatChieu();
        pnlBoxInfor.add(lblPhongChieu = new JLabel("Phòng chiếu:"));
        pnlBoxInfor.add(cmbPhongChieu = new JComboBox<>());
        cmbPhongChieu.setPreferredSize(new Dimension(100,30));
        cmbPhongChieu.addItem("Chọn");
        loadDataToCmbPhongChieu();

        pnlInforPhim.add(pnlBoxInfor);
        JPanel pnlChonVe = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        btnChonVe = createStyledButton("Chọn vé", Color.decode("#35a06c"));
        pnlChonVe.add(btnChonVe, gbc);

        pnlInforPhim.add(pnlChonVe);
        
        return pnlInforPhim;
    }
    
    private JPanel createTicketTablePanel() {
        String[] columns = {"Mã vé", "Phim", "Ghế", "Phòng", "Suất chiếu", "Giá vé", "Loại vé"};
        model = new DefaultTableModel(columns, 0);
        ticketTable = new JTable(model);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollCart = new JScrollPane(ticketTable), BorderLayout.CENTER);
        scrollCart.setPreferredSize(new Dimension(450, 150));
        return panel;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        return button;
    }





    private JButton createButton(String text, int width, int height) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(width, height));
        return button;
    }
    
    private void calculateTotal() {

        double discount = Double.parseDouble(txt_TienKhuyenMai.getText());
        double vat = 0.1 * ticketCost;

        double total = ticketCost - discount + vat;
        txt_tongTienValue.setText(String.valueOf(total));
    }
    
    private Double calculateTotalPrice() {
        TableModel model = ticketTable.getModel();
        double totalPrice = 0;

        for (int i = 0; i < model.getRowCount(); i++) {
            Object priceObj = model.getValueAt(i, 5);  
            if (priceObj != null) {
                try {
                    double price = Double.parseDouble(priceObj.toString());
                    totalPrice += price;
                } catch (NumberFormatException e) {
                    System.out.println("Dữ liệu không hợp lệ ở hàng " + i + ": " + priceObj);
                }
            }
        }

        return totalPrice;
    }
    
    private boolean isSeatAlreadyBooked(String seat) {
        for (int i = 0; i < model.getRowCount(); i++) {
            String existingSeat = (String) model.getValueAt(i, 2);
            if (existingSeat.equals(seat)) {
                return true;
            }
        }
        return false;
    }
    
    private void bookTicket() {
    	ve_dao = new VeDAO();
        String phim = (String) cmbTenPhim.getSelectedItem();
        String suatChieu = (String) cmbSuatChieu.getSelectedItem();
        String phong = (String) cmbPhongChieu.getSelectedItem();
        
        ArrayList<String> selectedSeats = getSelectedSeat();
        
        if (selectedSeats.isEmpty()) {
            System.out.println("Chưa chọn ghế.");
            return;
        }
        
        for (int i = 0; i < model.getRowCount(); i++) {
            String existingSeat = (String) model.getValueAt(i, 2); 
            if (!selectedSeats.contains(existingSeat)) {
                model.removeRow(i); 
                i--; 
            }
        }
        
        for (String soGhe : selectedSeats) {
        	if (isSeatAlreadyBooked(soGhe)) {
                continue; 
            }
        	
            String maVe = ve_dao.tuSinhMaVe();
            String loaiVe = "";

            char row = soGhe.charAt(0);
            if (row >= 'G') {
                loaiVe = "Vé Couple";
            } else if (row >= 'D') {
                loaiVe = "Vé VIP";
            } else {
                loaiVe = "Vé thường";
            }

            int gia = convertPrice(loaiVe);

            Object[] rowData = {maVe, phim, soGhe, phong, suatChieu, gia, loaiVe};
            model.addRow(rowData);
        }
    }
  
    
    private void processPayment(boolean printReceipt) {
        double amountGiven = Double.parseDouble(txtTienKhachTra.getText());
        double total = Double.parseDouble(txt_tongTienValue.getText());

        if (amountGiven < total) {
            JOptionPane.showMessageDialog(this, "Insufficient funds!");
        } else {
            double change = amountGiven - total;
            text_TienThoi.setText(String.valueOf(change));

            if (printReceipt) {
                printReceipt();
            }
        }
    }
    
//    private void searchCustomer() {
//        String searchQuery = txtTimKiemKH.getText();
//        if (searchQuery.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin khách hàng.");
//            return;
//        }
//
//    }
  
    
    private void setupSeatSelectionListeners() {
        Component[] components = pnlGhe.getComponents();
        seatButtons = new ArrayList<>();
        for (Component comp : components) {
            if (comp instanceof JButton) {
                JButton btn = (JButton) comp;
                seatButtons.add(btn);
                btn.addActionListener(e -> {
                	Color currentColor = btn.getBackground();
                	if (currentColor.equals(Color.decode("#35a06c"))) {
                        btn.setBackground(Color.BLACK); 
                    } else if (currentColor.equals(Color.decode("#074f2c"))) {
                        btn.setBackground(Color.BLACK);   
                    } else if (currentColor.equals(Color.RED)) {
                        btn.setBackground(Color.BLACK); 
                    } else {
                        btn.setBackground(getSeatColor(btn.getText().charAt(0))); 
                    }              
                });
            }
        }
    }


    private ArrayList<String> getSelectedSeat() {
    	Component[] components = pnlGhe.getComponents();
    	ArrayList<String> selectedSeats = new ArrayList<>();
    	seatButtons = new ArrayList<JButton>();
    	for(Component comp : components) {
    		if(comp instanceof JButton) {
    			JButton btn = (JButton) comp;
    			seatButtons.add(btn);	
    			if (btn.getBackground().equals(Color.BLACK)) {
    				selectedSeats.add(btn.getText());
    			}
    		}
    	}
		return selectedSeats;
    }
    
    private int convertPrice(String loaiVe) {
        switch (loaiVe) {
            case "Vé Couple":
                return 200000;
            case "Vé VIP":
                return 150000;
            case "Vé thường":
                return 100000;
            default:
                return 0;
        }
    }

    private void printReceipt() {
        JOptionPane.showMessageDialog(this, "Receipt printed successfully.");
    }

    public void loadDataToCmbTenPhim() {
    	
    	try {
            phim_dao = new PhimDAO();
            ArrayList<Phim> listPhim = phim_dao.getAllPhim();
            for(Phim phim : listPhim){
            	cmbTenPhim.addItem(phim.getTenPhim());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }     
    }
    
    public void loadDataToCmbSuatChieu() {
    	try {
            suatChieu_dao = new SuatChieuDAO();
            ArrayList<SuatChieu> listSuatChieu = suatChieu_dao.getAllSuatChieu();
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            cmbSuatChieu.removeAllItems();
            for(SuatChieu sc : listSuatChieu){
            	if(sc.getPhim().getTenPhim().equals(cmbTenPhim.getSelectedItem().toString())) {
            		thoiGianKhoiChieu = sc.getThoiGianKhoiChieu();
                	String thoiGianKhoiChieuToString = timeFormat.format(thoiGianKhoiChieu);
                	thoiGianKetThuc = sc.getThoiGianKetThuc();
                	String thoiGianKetThucToString = timeFormat.format(thoiGianKetThuc);
            		cmbSuatChieu.addItem(thoiGianKhoiChieuToString + "-" + thoiGianKetThucToString);
            	}
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }     
    }
    
    
    public void loadDataToCmbPhongChieu() {
    	
    	try {
            pc_dao = new PhongChieuDAO();
            ArrayList<PhongChieu> listPhongChieu = pc_dao.getAllPhongChieu();
            for(PhongChieu pc : listPhongChieu){
            	cmbPhongChieu.addItem(pc.getTenPhongChieu());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }     
    }
    
    public void loadDataToCmbKhuyenMai() {
    	
    	try {
            ctkm_dao = new ChuongTrinhKhuyenMaiDAO();
            ArrayList<ChuongTrinhKhuyenMai> listKM = ctkm_dao.getAlltbChuongTrinhKhuyenMai();
            for(ChuongTrinhKhuyenMai km : listKM){
            	cmbChonLoaiKM.addItem(km.getTenCTKM());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }     
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		
		if(o.equals(cmbTenPhim)) {
			loadDataToCmbSuatChieu();
		}
		
		if(o.equals(btnChonVe)) {
	        bookTicket();
	        totalPrice = calculateTotalPrice();
	        txt_tongTienValue.setText(totalPrice.toString());

	        tienThue = totalPrice * 0.1;
	        text_TienThue.setText(tienThue +"");

	        tienGiam = totalPrice + tienThue;
	        txt_TienGiam.setText(tienGiam + "");
	        
	      
	        
	}
		if(e.equals(cmbChonLoaiKM)) {
			double tienKhuyenMai = 0;
	        chiTietKM_dao = new ChiTietKhuyenMaiDAO();
	        chuongtrinh_dao = new ChuongTrinhKhuyenMaiDAO();
	        String loaiKM = (String) cmbChonLoaiKM.getSelectedItem();
       	 if (loaiKM != null && !loaiKM.equals("Chọn loại khuyến mãi")) {
	            ArrayList<ChiTietKhuyenMai> dsChiTietKhuyenMai = chiTietKM_dao.getAlltbChitietKhuyenMai();
	            ArrayList<ChuongTrinhKhuyenMai> dsChuongTrinhKhuyenMai = chuongtrinh_dao.getAlltbChuongTrinhKhuyenMai();

	            for (ChiTietKhuyenMai chiTietKM : dsChiTietKhuyenMai) {
	                for (ChuongTrinhKhuyenMai chuongTrinhKhuyenMai : dsChuongTrinhKhuyenMai) {
	                    if (chuongTrinhKhuyenMai.getTenCTKM().equals(loaiKM) &&
	                        chuongTrinhKhuyenMai.getMaCTKM().equals(chiTietKM.getChuongTrinhKM())) {
	                        tienKhuyenMai = tienGiam * (chiTietKM.getPhanTramKM() / 100.0);
	                        break;
	                    }
	                }
	            }
	            txt_TienKhuyenMai.setText(tienKhuyenMai + "đ");
	        }
       }else {
            txt_TienKhuyenMai.setText("0đ");
        }
		if(o.equals(txtTienKhachTra)) {
        	Double tienKhachTra = Double.parseDouble(txtTienKhachTra.getText());
		    Double tienGiam = Double.parseDouble(txt_TienGiam.getText());

		    if (tienKhachTra < tienGiam) {
	            JOptionPane.showMessageDialog(this, "Khách chưa trả đủ tiền!", "Thông báo", JOptionPane.WARNING_MESSAGE);
	            return;
	        }else
	        	tienThoi = tienKhachTra - tienGiam;
		    text_TienThoi.setText(tienThoi + "");

        }
		if(o.equals(btnThanhToan)) {
			ve_dao = new VeDAO();
	        JOptionPane.showMessageDialog(this, "Thanh toán thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
		}
		
		if(o == rbtnTienMat) {
		    if (txtTienKhachTra != null) {
		        txtTienKhachTra.setEnabled(true);
		    }
		} else if (o == rbtnViDienTu) {
		    if (txtTienKhachTra != null) {
		        txtTienKhachTra.setEnabled(false);
		        txtTienKhachTra.setText("");
		        text_TienThoi.setText("0đ");
		    }
		} else if(o == btnTimKiemKH) {
		    String SDT = txtSDTKH.getText().trim();
		    boolean khachHangTonTai = false;
		    KhachHang khachHang = null;
		    KhachHangDAO kh_dao = new KhachHangDAO();
		    ArrayList<KhachHang> danhSachKhachHang = null;
		    try {
		        danhSachKhachHang = kh_dao.getAllKhachHang();
		    } catch (Exception ex) {
		        throw new RuntimeException(ex);
		    }
		    for (KhachHang kh : danhSachKhachHang) {
		        if (kh.getSdt().equals(SDT)) {
		            khachHangTonTai = true;
		            khachHang = kh;
		            break;
		        }
		    }
		    DiemTichLuyDAO dtl_dao = new DiemTichLuyDAO();
		    if(khachHangTonTai) {
		        try {
		            DiemTichLuy diemTichLuy = dtl_dao.getDiemTichLuyBySDT(khachHang.getSdt());
		            txtGiaTriDiemTL.setText(diemTichLuy.getDiemHienTai() + "đ");
		            txtTenKhachHang.setText(khachHang.getTenKH());
		            txtSDTKH.setText(khachHang.getSdt());
		        } catch (Exception ex) {
		            throw new RuntimeException(ex);
		        }
		    } else {
		        JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng, vui lòng nhập số điện thoại khác");
		        txtSDTKH.setText("");
		        txtSDTKH.requestFocus();
		        return;
		    }
		}
	}
}
