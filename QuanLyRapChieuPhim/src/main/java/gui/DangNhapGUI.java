package gui;

import javax.swing.*;
import dao.TaiKhoanDAO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DangNhapGUI extends JFrame implements ActionListener {
    private JLabel northlb;
    private JPanel boxall;
    private JPanel boxnorth;
    private JPanel boxcenter;
    private JPanel boxeast;
    private JLabel eastlb;
    private JPanel boxeastcon;
    private JTextField taikhoanfi;
    private JTextField matkhaufi;
    private JPanel demcon;
    private JButton dangNhapBt;
    private JButton thoat;
    private JPanel boxnut;
    private TaiKhoanDAO taikhoandao;

    public DangNhapGUI() {
        super("Đăng nhập");
        setSize(700, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        taikhoandao = new TaiKhoanDAO();

        boxall = new JPanel();
        boxall.setBackground(Color.decode("#1B7449"));
        boxall.setLayout(new BorderLayout());
        add(boxall);

        boxnorth = new JPanel();
        boxnorth.setBackground(Color.decode("#1B7449"));

        boxcenter = new JPanel();
        boxcenter.setBackground(Color.decode("#1B7449"));

        boxeast = new JPanel();
        boxeast.setBackground(Color.decode("#1B7449"));
        boxeast.setLayout(new BoxLayout(boxeast, BoxLayout.Y_AXIS));

        // Thiết lập thành phần cho boxnorth
        boxnorth.add(Box.createRigidArea(new Dimension(0, 60)));
        northlb = new JLabel("HỆ THỐNG QUẢN LÝ BÁN VÉ XEM PHIM");
        northlb.setFont(new Font("Arial", Font.BOLD, 30));
        northlb.setForeground(Color.WHITE); // Màu trắng
        boxnorth.add(Box.createVerticalStrut(30));
        boxnorth.add(northlb);
        boxall.add(boxnorth, BorderLayout.NORTH);

        // Thiết lập hình ảnh cho boxcenter
        ImageIcon imageIcon = new ImageIcon("image\\user.png");
        Image scaledImage = imageIcon.getImage().getScaledInstance(180, 220, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));

        boxcenter.add(imageLabel);
        boxall.add(boxcenter, BorderLayout.CENTER);

        boxeast.setPreferredSize(new Dimension(350, 300));
        demcon = new JPanel();
        demcon.setBackground(Color.decode("#1B7449"));
        demcon.setLayout(new BoxLayout(demcon, BoxLayout.X_AXIS));

        eastlb = new JLabel("Đăng nhập");
        eastlb.setFont(new Font("Arial", Font.BOLD, 20));
        eastlb.setForeground(Color.WHITE); // Màu trắng
        eastlb.setAlignmentX(Component.CENTER_ALIGNMENT);
        boxeast.add(Box.createRigidArea(new Dimension(0, 30)));
        boxeast.add(eastlb);

        boxeastcon = new JPanel();
        boxeastcon.setLayout(new BoxLayout(boxeastcon, BoxLayout.Y_AXIS));
        boxeastcon.setBackground(Color.decode("#1B7449"));
        
        boxeast.add(demcon);
        demcon.add(boxeastcon);
        demcon.add(Box.createHorizontalStrut(60));
        boxeastcon.add(createInputBox("Tài khoản:", taikhoanfi = new JTextField(15), 370, 30));
        boxeastcon.add(createInputBox("Mật khẩu:  ", matkhaufi = new JTextField(15), 370, 30));
        boxeastcon.add(Box.createHorizontalStrut(20));
        boxall.add(boxeast, BorderLayout.EAST);

        boxnut = new JPanel();
        boxnut.setBackground(Color.decode("#1B7449"));
        boxeastcon.add(boxnut);
        boxnut.setLayout(new BoxLayout(boxnut, BoxLayout.X_AXIS));

        dangNhapBt = new JButton("Đăng nhập");
        dangNhapBt.setForeground(Color.WHITE); // Màu trắng
        dangNhapBt.setBackground(Color.decode("#B5B682")); // Nền cùng màu với tổng thể
        thoat = new JButton("Thoát");
        thoat.setForeground(Color.WHITE); // Màu trắng
        thoat.setBackground(Color.decode("#B5B682")); // Nền cùng màu với tổng thể

        boxnut.add(dangNhapBt);
        boxnut.add(Box.createHorizontalStrut(20));
        boxnut.add(thoat);

        dangNhapBt.addActionListener(this);
        thoat.addActionListener(this);

        setVisible(true);
    }

    private JPanel createInputBox(String labelText, JComponent inputComponent, int width, int height) {
        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.X_AXIS));
        box.setBackground(Color.decode("#1B7449"));

        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(100, 30));
        label.setForeground(Color.WHITE); // Màu trắng cho nhãn
        box.add(label);
        box.add(Box.createHorizontalStrut(10));

        inputComponent.setPreferredSize(new Dimension(width, height));
        inputComponent.setMaximumSize(new Dimension(width, height));
        if (inputComponent instanceof JTextField) {
            inputComponent.setForeground(Color.BLACK); // Màu trắng cho văn bản trong JTextField
            inputComponent.setBackground(Color.decode("#66CC99")); // Màu nền cho JTextField
        }
        box.add(inputComponent);

        return box;
    }

    public static void main(String[] args) {
        new DangNhapGUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(dangNhapBt)) {
            dangnhap();
        }
        if (e.getSource().equals(thoat)) {
            System.exit(0);
        }
    }

    private void dangnhap() {
        String tenTaiKhoan = taikhoanfi.getText();
        String matKhau = new String(matkhaufi.getText());

        String maChucVu = taikhoandao.kiemTraDangNhap(tenTaiKhoan, matKhau);
        System.out.println(maChucVu);
        if (maChucVu != null) {
            if (maChucVu.equals("1")) {
                TrangChuNhanVienGUI trangChuNhanVienGUI = new TrangChuNhanVienGUI();
                trangChuNhanVienGUI.setVisible(true);
                dispose();
            } else if (maChucVu.equals("2")) {
                TrangChuNguoiQuanLyGUI trangChuNguoiQuanLyGUI = new TrangChuNguoiQuanLyGUI();
                trangChuNguoiQuanLyGUI.setVisible(true);
                dispose();
            }
        } else {
            JOptionPane.showMessageDialog(DangNhapGUI.this, "Sai tên tài khoản hoặc mật khẩu.", "Lỗi đăng nhập", JOptionPane.ERROR_MESSAGE);
        }
    }
}
