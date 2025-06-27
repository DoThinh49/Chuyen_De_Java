package lab2;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.stream.IntStream; // Dùng cho isPrime

public class Cau2 extends JFrame { // Đổi tên class thành Cau2 theo yêu cầu

    private JTextField inputField;
    private JCheckBox allowNegativeCheckbox;
    private DefaultListModel<Integer> listModel;
    private JList<Integer> numberList;

    // Enum để quản lý trạng thái tô đen
    private enum HighlightMode {
        NONE, EVEN, ODD, PRIME
    }
    private HighlightMode currentHighlightMode = HighlightMode.NONE;

    public Cau2() { // Constructor cũng đổi tên thành Cau2
        setTitle("Thao tác trên JList - Checkbox"); // Đặt tiêu đề cửa sổ 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 450); // Điều chỉnh kích thước

        // --- Panel trên cùng: Nhập liệu và Checkbox ---
        JPanel inputControlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10)); //
        inputControlPanel.setBorder(new TitledBorder("Nhập thông tin:")); // Đặt tiêu đề cho border 

        inputField = new JTextField(10); // Trường nhập liệu
        JButton addButton = new JButton("Nhập"); // Nút nhập 
        allowNegativeCheckbox = new JCheckBox("Cho nhập số âm"); // Checkbox cho phép số âm 

        inputControlPanel.add(inputField); //
        inputControlPanel.add(addButton); //
        inputControlPanel.add(allowNegativeCheckbox); //

        // --- Panel chính giữa: JList và các nút thao tác ---
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10)); //

        // Left Panel: Các nút chọn tác vụ
        JPanel actionButtonsPanel = new JPanel(); //
        actionButtonsPanel.setLayout(new GridLayout(6, 1, 5, 5)); // 6 hàng, 1 cột, khoảng cách 5px
        actionButtonsPanel.setBorder(new TitledBorder("Chọn tác vụ")); // Đặt tiêu đề cho border 

        JButton highlightEvenButton = new JButton("Tô đen số chẵn"); // Nút tô đen số chẵn 
        JButton highlightOddButton = new JButton("Tô đen số lẻ"); // Nút tô đen số lẻ 
        JButton highlightPrimeButton = new JButton("Tô đen số nguyên tố"); // Nút tô đen số nguyên tố 
        JButton clearHighlightButton = new JButton("Bỏ tô đen"); // Nút bỏ tô đen 
        JButton deleteHighlightedButton = new JButton("Xóa các giá trị đang tô đen"); // Nút xóa các giá trị đang tô đen 
        JButton sumButton = new JButton("Tổng giá trị trong JList"); // Nút tổng giá trị 

        actionButtonsPanel.add(highlightEvenButton); //
        actionButtonsPanel.add(highlightOddButton); //
        actionButtonsPanel.add(highlightPrimeButton); //
        actionButtonsPanel.add(clearHighlightButton); //
        actionButtonsPanel.add(deleteHighlightedButton); //
        actionButtonsPanel.add(sumButton); //

        mainPanel.add(actionButtonsPanel, BorderLayout.WEST); //

        // Right Panel: JList
        listModel = new DefaultListModel<>(); //
        // Thêm một vài số mẫu để dễ kiểm tra, dựa trên hình ảnh 
        listModel.addElement(4);
        listModel.addElement(5);
        listModel.addElement(6);
        listModel.addElement(9);
        listModel.addElement(11);
        listModel.addElement(17);
        listModel.addElement(18);
        listModel.addElement(35);
        listModel.addElement(67);

        numberList = new JList<>(listModel); //
        numberList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); // Cho phép chọn nhiều mục
        // Thiết lập Cell Renderer tùy chỉnh
        numberList.setCellRenderer(new CustomListCellRenderer()); //

        JScrollPane scrollPane = new JScrollPane(numberList); //
        mainPanel.add(scrollPane, BorderLayout.CENTER); //

        // --- Panel dưới cùng: Nút Đóng ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); //
        JButton closeButton = new JButton("Đóng chương trình"); // Nút đóng chương trình 
        bottomPanel.add(closeButton); //

        // --- Thêm các panel vào Frame chính ---
        add(inputControlPanel, BorderLayout.NORTH); //
        add(mainPanel, BorderLayout.CENTER); //
        add(bottomPanel, BorderLayout.SOUTH); //

        // --- Xử lý sự kiện ---

        // Nút "Nhập"
        addButton.addActionListener(e -> {
            try {
                String inputStr = inputField.getText();
                if (inputStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập một số.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int number = Integer.parseInt(inputStr);

                if (!allowNegativeCheckbox.isSelected() && number < 0) {
                    JOptionPane.showMessageDialog(this, "Không được nhập số âm khi chưa chọn 'Cho nhập số âm'.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                } else {
                    listModel.addElement(number);
                    inputField.setText(""); // Xóa trường nhập liệu
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Giá trị nhập vào không phải là số nguyên hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Nút "Tô đen số chẵn"
        highlightEvenButton.addActionListener(e -> {
            currentHighlightMode = HighlightMode.EVEN;
            numberList.repaint(); // Yêu cầu JList vẽ lại
        });

        // Nút "Tô đen số lẻ"
        highlightOddButton.addActionListener(e -> {
            currentHighlightMode = HighlightMode.ODD;
            numberList.repaint(); // Yêu cầu JList vẽ lại
        });

        // Nút "Tô đen số nguyên tố"
        highlightPrimeButton.addActionListener(e -> {
            currentHighlightMode = HighlightMode.PRIME;
            numberList.repaint(); // Yêu cầu JList vẽ lại
        });

        // Nút "Bỏ tô đen"
        clearHighlightButton.addActionListener(e -> {
            currentHighlightMode = HighlightMode.NONE;
            numberList.repaint(); // Yêu cầu JList vẽ lại
            numberList.clearSelection(); // Xóa lựa chọn (nếu có)
        });

        // Nút "Xóa các giá trị đang tô đen"
        deleteHighlightedButton.addActionListener(e -> {
            if (currentHighlightMode == HighlightMode.NONE) {
                JOptionPane.showMessageDialog(this, "Chưa có chế độ tô đen nào được chọn để xóa.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Xóa từ cuối về đầu để tránh lỗi chỉ mục khi xóa liên tiếp
            for (int i = listModel.size() - 1; i >= 0; i--) {
                int number = listModel.getElementAt(i);
                boolean shouldDelete = false;
                switch (currentHighlightMode) {
                    case EVEN:
                        shouldDelete = isEven(number);
                        break;
                    case ODD:
                        shouldDelete = isOdd(number);
                        break;
                    case PRIME:
                        shouldDelete = isPrime(number);
                        break;
                    case NONE: // Không bao giờ đến đây vì đã kiểm tra ở trên
                        break;
                }
                if (shouldDelete) {
                    listModel.remove(i);
                }
            }
            numberList.clearSelection(); // Xóa lựa chọn
            currentHighlightMode = HighlightMode.NONE; // Về trạng thái không tô đen sau khi xóa
            numberList.repaint();
        });


        // Nút "Tổng giá trị trong JList"
        sumButton.addActionListener(e -> {
            int sum = 0;
            for (int i = 0; i < listModel.size(); i++) {
                sum += listModel.getElementAt(i);
            }
            JOptionPane.showMessageDialog(this, "Tổng giá trị trong JList là: " + sum, "Tổng giá trị", JOptionPane.INFORMATION_MESSAGE);
        });

        // Nút "Đóng chương trình"
        closeButton.addActionListener(e -> System.exit(0));

        setLocationRelativeTo(null); // Đặt cửa sổ ra giữa màn hình
        setVisible(true);
    }

    // --- Các hàm trợ giúp ---
    private boolean isEven(int n) {
        return n % 2 == 0;
    }

    private boolean isOdd(int n) {
        return n % 2 != 0;
    }

    private boolean isPrime(int n) {
        if (n <= 1) { // Số nguyên tố phải lớn hơn 1
            return false;
        }
        // Kiểm tra các ước số từ 2 đến căn bậc hai của n
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    // --- Custom ListCellRenderer để tô màu ---
    private class CustomListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            // Gọi phương thức của lớp cha để có được thành phần mặc định (JLabel)
            Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (value instanceof Integer) {
                int number = (Integer) value;

                // Đặt màu nền mặc định
                c.setBackground(list.getBackground());
                c.setForeground(list.getForeground());

                // Tô màu dựa trên chế độ tô đen hiện tại
                switch (currentHighlightMode) {
                    case EVEN:
                        if (isEven(number)) {
                            c.setBackground(Color.BLACK); // Tô đen
                            c.setForeground(Color.WHITE); // Chữ trắng để dễ nhìn
                        }
                        break;
                    case ODD:
                        if (isOdd(number)) {
                            c.setBackground(Color.BLACK);
                            c.setForeground(Color.WHITE);
                        }
                        break;
                    case PRIME:
                        if (isPrime(number)) {
                            c.setBackground(Color.BLACK);
                            c.setForeground(Color.WHITE);
                        }
                        break;
                    case NONE:
                        // Không làm gì, để màu mặc định
                        break;
                }

                // Giữ nguyên màu nền và chữ khi được chọn
                if (isSelected) {
                    c.setBackground(list.getSelectionBackground());
                    c.setForeground(list.getSelectionForeground());
                }
            }
            return c;
        }
    }

    public static void main(String[] args) {
        // Đảm bảo rằng việc tạo giao diện Swing được thực hiện trên Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new Cau2()); // Tạo một instance của class Cau2
    }
}