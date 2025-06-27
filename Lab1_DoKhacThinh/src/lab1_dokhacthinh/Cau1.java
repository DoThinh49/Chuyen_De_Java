package lab1_DoKhacThinh; // Thay đổi package name nếu package của bạn khác



import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder; // Thêm import cho EmptyBorder

public class Cau1 {

    private JFrame frame;
    private JTextField txtA, txtB, txtC, txtResult;
    private JButton btnSolve, btnClear, btnExit;

    // TẠO CONSTRUCTOR
    public Cau1() {
        frame = new JFrame("Giải Phương Trình Bậc Hai");
        frame.setSize(450, 350); // Tăng kích thước để có không gian
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout()); // Sử dụng BorderLayout cho JFrame chính

        // --- 1. PANEL TIÊU ĐỀ (NORTH) ---
        JPanel panelTitle = new JPanel();
        panelTitle.setBackground(new Color(173, 216, 230)); // Màu xanh nhạt (LightBlue)
        JLabel lblTitle = new JLabel("GIẢI PHƯƠNG TRÌNH BẬC HAI");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20)); // Font chữ lớn, in đậm
        lblTitle.setForeground(Color.BLUE); // Màu chữ xanh
        panelTitle.add(lblTitle);
        frame.add(panelTitle, BorderLayout.NORTH); // Thêm panel tiêu đề vào phía Bắc của frame

        // --- 2. PANEL NHẬP LIỆU (CENTER) ---
        JPanel panelInput = new JPanel();
        // Sử dụng GridLayout cho các cặp label/textfield (4 hàng, 2 cột)
        panelInput.setLayout(new GridLayout(4, 2, 10, 10)); // 4 hàng, 2 cột, khoảng cách 10px
        panelInput.setBorder(new EmptyBorder(20, 30, 20, 30)); // Tạo khoảng trống xung quanh panelInput

        // Nhập a
        panelInput.add(new JLabel("Nhập a:"));
        txtA = new JTextField();
        panelInput.add(txtA);

        // Nhập b
        panelInput.add(new JLabel("Nhập b:"));
        txtB = new JTextField();
        panelInput.add(txtB);

        // Nhập c
        panelInput.add(new JLabel("Nhập c:"));
        txtC = new JTextField();
        panelInput.add(txtC);

        // Kết quả
        panelInput.add(new JLabel("Kết quả:"));
        txtResult = new JTextField();
        txtResult.setEditable(false); // Không cho phép chỉnh sửa trường kết quả
        panelInput.add(txtResult);

        frame.add(panelInput, BorderLayout.CENTER); // Thêm panel nhập liệu vào trung tâm của frame

        // --- 3. PANEL NÚT (SOUTH) ---
        JPanel panelButtonsContainer = new JPanel(new BorderLayout()); // Panel chứa cả "Chọn tác vụ" và các nút
        panelButtonsContainer.setBorder(new EmptyBorder(10, 30, 20, 30)); // Khoảng trống xung quanh

        // JLabel "Chọn tác vụ"
        JLabel lblTask = new JLabel("Chọn tác vụ");
        lblTask.setFont(new Font("Arial", Font.PLAIN, 12)); // Font nhỏ hơn cho nhãn
        panelButtonsContainer.add(lblTask, BorderLayout.NORTH); // Đặt nhãn ở phía Bắc của panel nút

        // Panel chứa các nút (dùng FlowLayout để căn giữa)
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0)); // Căn giữa, khoảng cách 15px giữa các nút
        
        btnSolve = new JButton("Giải");
        btnClear = new JButton("Xóa rỗng");
        btnExit = new JButton("Thoát");

        panelButtons.add(btnSolve);
        panelButtons.add(btnClear);
        panelButtons.add(btnExit);

        panelButtonsContainer.add(panelButtons, BorderLayout.SOUTH); // Đặt panel nút ở phía Nam của panel chứa nút

        frame.add(panelButtonsContainer, BorderLayout.SOUTH); // Thêm panel chứa nút vào phía Nam của frame

        // Thêm ActionListener cho các nút (sử dụng Lambda Expression)
        btnSolve.addActionListener(e -> tinhKetQua());
        btnClear.addActionListener(e -> xoa());
        btnExit.addActionListener(e -> System.exit(0)); // Thoát ứng dụng

        frame.setLocationRelativeTo(null); // Đặt cửa sổ ra giữa màn hình
        frame.setVisible(true); // Hiển thị cửa sổ
    }

    // HÀM TÍNH KẾT QUẢ
    private void tinhKetQua() {
        try {
            double a = Double.parseDouble(txtA.getText());
            double b = Double.parseDouble(txtB.getText());
            double c = Double.parseDouble(txtC.getText());

            // Kiểm tra trường hợp a = 0 (phương trình bậc nhất)
            if (a == 0) {
                if (b == 0) {
                    if (c == 0) {
                        txtResult.setText("Phương trình vô số nghiệm");
                    } else {
                        txtResult.setText("Phương trình vô nghiệm");
                    }
                } else {
                    double x = -c / b;
                    txtResult.setText("P.trình bậc nhất: x = " + String.format("%.2f", x)); // Rút gọn chữ P.trình
                }
                return; // Kết thúc hàm nếu là bậc nhất
            }

            double delta = b * b - 4 * a * c;

            if (delta < 0) {
                txtResult.setText("Vô nghiệm");
            } else if (delta == 0) {
                double x = -b / (2 * a);
                txtResult.setText("Nghiệm kép: x = " + String.format("%.2f", x));
            } else {
                double x1 = (-b + Math.sqrt(delta)) / (2 * a);
                double x2 = (-b - Math.sqrt(delta)) / (2 * a);
                txtResult.setText("x1 = " + String.format("%.2f", x1) + ", x2 = " + String.format("%.2f", x2));
            }
        } catch (NumberFormatException ex) {
            txtResult.setText("Lỗi: Vui lòng nhập số.");
        }
    }

    // HÀM XÓA RỖNG
    private void xoa() {
        txtA.setText("");
        txtB.setText("");
        txtC.setText("");
        txtResult.setText("");
        txtA.requestFocus(); // Đưa con trỏ về txtA sau khi xóa
    }

    // Phương thức main để chạy ứng dụng
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Cau1();
        });
    }
}