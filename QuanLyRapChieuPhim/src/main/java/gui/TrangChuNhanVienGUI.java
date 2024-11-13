package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.kordamp.ikonli.fontawesome5.FontAwesomeRegular;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class TrangChuNhanVienGUI extends JFrame implements ActionListener {
	private JPanel boxall;
    private JPanel boxcenter;
    private JPanel boxwest;
    private ImageIcon anhuser;
    private Font fontChu;
    private JLabel userlb;
    private JButton selectedButton = null;
    
    // Nút với icon
    private JButton trangChuBt;
    private JButton khachHangBt;
    private JButton dangnhapbt;
    private ImagePanel imgPanel;
	private JButton DatVeBt;

    public TrangChuNhanVienGUI() {
        // Thiết lập cửa sổ chính
        super("Trang chủ");
        setSize(1400, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        // Tạo bố cục chính
        boxall = new JPanel();
        boxall.setLayout(new BorderLayout());

        // Panel trung tâm chứa hình nền và nội dung để xử lý
        boxcenter = new JPanel();
        boxcenter.setLayout(new BorderLayout()); // Để thêm các nội dung mới vào giữa
        imgPanel = new ImagePanel("image\\rapchieuphim.jpg");
        boxcenter.add(imgPanel, BorderLayout.CENTER);
        boxall.add(boxcenter, BorderLayout.CENTER);

        // Panel bên trái chứa các nút điều hướng
        boxwest = new JPanel();
        boxwest.setLayout(new BoxLayout(boxwest, BoxLayout.Y_AXIS));
        boxwest.setPreferredSize(new Dimension(200, 400));
        boxwest.setBackground(Color.decode("#1b7449"));
        boxall.add(boxwest, BorderLayout.WEST);

        add(boxall);

        fontChu = new Font("Roboto", Font.BOLD, 20);

        anhuser = new ImageIcon("image\\user.png");
        JPanel imguser = new ImagePanel(anhuser);
        imguser.setPreferredSize(new Dimension(70, 250));
        imguser.setMaximumSize(new Dimension(70, 250));

        userlb = new JLabel("Nhân Viên");
        userlb.setFont(new Font("Arial", Font.BOLD, 15));

        trangChuBt = createIconButton("Trang Chủ", FontIcon.of(FontAwesomeSolid.HOME, 20));
        DatVeBt = createIconButton("Vé", FontIcon.of(FontAwesomeSolid.FILM, 20));
        khachHangBt = createIconButton("Khách hàng", FontIcon.of(FontAwesomeSolid.USER_FRIENDS, 20));
        dangnhapbt = createIconButton("Đăng xuất", FontIcon.of(FontAwesomeRegular.USER_CIRCLE, 20));

        trangChuBt.addActionListener(this);
        DatVeBt.addActionListener(this);
        khachHangBt.addActionListener(this);
        dangnhapbt.addActionListener(this);

        // Căn chỉnh tất cả các thành phần về giữa theo chiều ngang
        imguser.setAlignmentX(Component.CENTER_ALIGNMENT);
        userlb.setAlignmentX(Component.CENTER_ALIGNMENT);
        trangChuBt.setAlignmentX(Component.CENTER_ALIGNMENT);
        DatVeBt.setAlignmentX(Component.CENTER_ALIGNMENT);
        khachHangBt.setAlignmentX(Component.CENTER_ALIGNMENT);
        dangnhapbt.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Căn chỉnh thành phần và thêm vào boxwest
        boxwest.add(Box.createRigidArea(new Dimension(0, 0))); // Khoảng cách trên cùng
        boxwest.add(imguser);
        boxwest.add(userlb);
        boxwest.add(Box.createRigidArea(new Dimension(0, 12)));
        boxwest.add(trangChuBt);
        boxwest.add(Box.createRigidArea(new Dimension(0, 12)));
        boxwest.add(DatVeBt);
        boxwest.add(Box.createRigidArea(new Dimension(0, 12)));
        boxwest.add(khachHangBt);
        boxwest.add(Box.createRigidArea(new Dimension(0, 340)));
        boxwest.add(dangnhapbt);
        boxwest.add(Box.createRigidArea(new Dimension(0, 10)));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new TrangChuNhanVienGUI();
    }

    private JButton createIconButton(String text, FontIcon icon) {
        JButton button = new JButton(icon);
        button.setText(text);
        button.setHorizontalTextPosition(SwingConstants.RIGHT); // Đặt vị trí văn bản bên phải icon
        button.setVerticalTextPosition(SwingConstants.CENTER); // Đặt vị trí văn bản ở giữa icon
        button.setIconTextGap(10); // Điều chỉnh khoảng cách giữa icon và văn bản
        button.setBackground(Color.decode("#35a06c"));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(180, 40)); // Tăng chiều cao cho nút
        button.setMaximumSize(new Dimension(180, 40));
        button.setMinimumSize(new Dimension(180, 40));
        button.setFont(fontChu);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setHorizontalAlignment(SwingConstants.LEFT); // Căn chỉnh icon và văn bản về bên trái
        button.setMargin(new Insets(0, 20, 0, 0)); // Thêm padding left để dời tổ hợp ra giữa
        return button;
    }




    private class ImagePanel extends JPanel {
        private ImageIcon imageIcon;

        public ImagePanel(String imagePath) {
            this.imageIcon = new ImageIcon(imagePath);
        }

        public ImagePanel(ImageIcon imageIcon) {
            this.imageIcon = imageIcon;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (imageIcon != null) {
                g.drawImage(imageIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    private void changeContentPanel(JPanel newPanel) {
        boxcenter.removeAll();
        boxcenter.add(newPanel, BorderLayout.CENTER);
        boxcenter.revalidate();
        boxcenter.repaint();
    }

    public void setFrameTitle(String title) {
        setTitle(title);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();

        if (selectedButton != null) {
            selectedButton.setBackground(Color.decode("#35a06c"));
        }

        clickedButton.setBackground(Color.decode("#69d4a0"));
        selectedButton = clickedButton;

        if (clickedButton == trangChuBt) {
            setFrameTitle("Trang Chủ");
            changeContentPanel(imgPanel);
        } else if (clickedButton == DatVeBt) {
            setFrameTitle("Quản lý phim");
            changeContentPanel(new DatVeGUI());
        } else if (clickedButton == khachHangBt) {
            setFrameTitle("Quản lý thành viên");
            changeContentPanel(new ThanhVienGUI());
        } else if (clickedButton == dangnhapbt) {
            setVisible(false);
            new DangNhapGUI().setVisible(true);
        }
    }

public void mouseClicked(MouseEvent e) {}
public void mousePressed(MouseEvent e) {}
public void mouseReleased(MouseEvent e) {}
public void mouseEntered(MouseEvent e) {}
public void mouseExited(MouseEvent e) {}
}
