package lab1_dokhacthinh;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent; // Thêm import này
import java.awt.event.ActionListener; // Thêm import này

public class Cau2 {

    private JFrame frame;
    private JTextField txtN;
    private JTextArea txtArea;
    private JButton btnGenerate;

    // TẠO CONSTRUCTOR
    public Cau2() {
        frame = new JFrame("Số Nguyên Tố");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout()); // Sử dụng FlowLayout

        // Nhãn và ô nhập số N
        frame.add(new JLabel("Nhập số nguyên tố cần hiển thị:"));
        txtN = new JTextField(10); // Kích thước ô nhập liệu
        frame.add(txtN);

        // Nút Generate
        btnGenerate = new JButton("Generate");
        frame.add(btnGenerate);

        // Vùng hiển thị kết quả (JTextArea trong JScrollPane)
        txtArea = new JTextArea(10, 30); // 10 hàng, 30 cột
        txtArea.setEditable(false); // Không cho phép chỉnh sửa
        JScrollPane scrollPane = new JScrollPane(txtArea); // Thêm JScrollPane
        frame.add(scrollPane); 

        // Thêm ActionListener cho nút Generate (sử dụng Lambda Expression)
        // Nếu vẫn gặp lỗi "Illegal character: '\u0000'" hoặc liên quan đến lambda
        // thì bạn cần thay thế bằng Anonymous Inner Class như ghi chú bên dưới.
        btnGenerate.addActionListener(e -> taoSoNguyenTo());

        /*
        // Nếu phiên bản Java của bạn không hỗ trợ Lambda Expressions (Java 7 trở xuống),
        // bạn cần sử dụng Anonymous Inner Class như sau:
        btnGenerate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taoSoNguyenTo();
            }
        });
        */

        frame.setLocationRelativeTo(null); // Đặt cửa sổ ra giữa màn hình
        frame.setVisible(true);
    }

    // HÀM TẠO SỐ NGUYÊN TỐ
    private void taoSoNguyenTo() {
        try {
            int n = Integer.parseInt(txtN.getText());
            if (n <= 0) {
                txtArea.setText("Vui lòng nhập số nguyên dương.");
                return;
            }
            txtArea.setText(""); // Xóa nội dung cũ trước khi generate mới

            int count = 0; // Đếm số lượng số nguyên tố đã tìm thấy
            int num = 2;   // Bắt đầu kiểm tra từ số 2

            // Vòng lặp tính toán số nguyên tố được chạy trực tiếp trên EDT.
            // Điều này có thể làm giao diện đơ nếu 'n' rất lớn.
            // Nếu bạn gặp tình trạng đơ, hãy cân nhắc nâng cấp JDK lên 8+ và sử dụng ExecutorService/SwingWorker
            // như trong phiên bản code trước đó của tôi.
            while (count < n) {
                if (laSoNguyenTo(num)) {
                    txtArea.append(num + " ");
                    count++;
                }
                num++;
            }

        } catch (NumberFormatException ex) {
            txtArea.setText("Lỗi nhập liệu: Vui lòng nhập một số nguyên.");
        }
    }

    // HÀM KIỂM TRA SỐ NGUYÊN TỐ
    private boolean laSoNguyenTo(int num) {
        if (num < 2) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    // Phương thức main để chạy ứng dụng
    public static void main(String[] args) {
        // Đảm bảo rằng GUI được tạo trên Event Dispatch Thread (EDT)
        // Nếu vẫn gặp lỗi "Illegal character: '\u0000'" hoặc liên quan đến lambda
        // thì bạn cần thay thế bằng Anonymous Inner Class như ghi chú bên dưới.
        SwingUtilities.invokeLater(() -> {
            new Cau2();
        });
        
        /*
        // Nếu phiên bản Java của bạn không hỗ trợ Lambda Expressions (Java 7 trở xuống),
        // bạn cần sử dụng Anonymous Inner Class như sau:
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Cau2();
            }
        });
        */
    }
}