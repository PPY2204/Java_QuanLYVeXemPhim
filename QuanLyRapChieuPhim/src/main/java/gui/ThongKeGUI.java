package gui;

import javax.swing.*;
import java.awt.*;

public class ThongKeGUI extends JPanel {
    public ThongKeGUI() {
        // Khởi tạo các thành phần ở đây
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Chào mừng đến với Thống Kê");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);
    }
}
