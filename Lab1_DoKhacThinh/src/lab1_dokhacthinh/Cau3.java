
package lab1_dokhacthinh;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent; // Đảm bảo import này có
import java.awt.event.ActionListener; // Đảm bảo import này có

public class Cau3 {

    private JFrame frame;
    private JTextField txtA, txtB, txtResult;
    private JRadioButton rbAdd, rbSubtract, rbMultiply, rbDivide;
    private JButton btnCalculate, btnClear, btnExit;

    // TẠO CONSTRUCTOR
    public Cau3() {
        frame = new JFrame("Cộng Trừ Nhân Chia");
        frame.setSize(800, 300); // Kích thước cửa sổ
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(6, 2, 10, 10)); // Sử dụng GridLayout: 6 hàng, 2 cột, khoảng cách 10px

        // Nhập a
        frame.add(new JLabel("Nhập a:"));
        txtA = new JTextField();
        frame.add(txtA);

        // Nhập b
        frame.add(new JLabel("Nhập b:"));
        txtB = new JTextField();
        frame.add(txtB);

        // Phép toán (Radio Buttons)
        frame.add(new JLabel("Phép toán:"));
        JPanel panel = new JPanel(); // Panel chứa các Radio Button
        // Panel này mặc định dùng FlowLayout, các Radio Button sẽ xếp ngang nhau

        rbAdd = new JRadioButton("Cộng");
        rbSubtract = new JRadioButton("Trừ");
        rbMultiply = new JRadioButton("Nhân");
        rbDivide = new JRadioButton("Chia");

        // Tạo ButtonGroup để chỉ cho phép chọn một Radio Button tại một thời điểm
        ButtonGroup group = new ButtonGroup();
        group.add(rbAdd);
        group.add(rbSubtract);
        group.add(rbMultiply);
        group.add(rbDivide);

        // Thêm các Radio Button vào panel
        panel.add(rbAdd);
        panel.add(rbSubtract);
        panel.add(rbMultiply);
        panel.add(rbDivide);
        
        // Thêm panel chứa các Radio Button vào frame
        frame.add(panel);

        // Kết quả
        frame.add(new JLabel("Kết quả:"));
        txtResult = new JTextField();
        txtResult.setEditable(false); // Không cho phép chỉnh sửa trường kết quả
        frame.add(txtResult);

        // Các nút chức năng
        btnCalculate = new JButton("Giải"); // Đổi tên thành "Giải" theo hình của Câu 1 nếu muốn nhất quán
        btnClear = new JButton("Xóa");
        btnExit = new JButton("Thoát");

        // Thêm các nút vào frame
        frame.add(btnCalculate);
        frame.add(btnClear);
        // Thêm một ô trống để cân bằng lưới nếu cần (vì có 3 nút trong 2 cột cuối)
        // Nếu không thêm JLabel trống này, nút Thoát sẽ bị đẩy sang trái
        frame.add(new JLabel("")); // Ô trống ở vị trí cột thứ 2 của hàng cuối
        frame.add(btnExit);


        // Thêm ActionListener cho các nút (sử dụng Lambda Expression)
        btnCalculate.addActionListener(e -> tinh());
        btnClear.addActionListener(e -> xoa());
        btnExit.addActionListener(e -> System.exit(0)); // Thoát ứng dụng

        frame.setLocationRelativeTo(null); // Đặt cửa sổ ra giữa màn hình
        frame.setVisible(true); // Hiển thị cửa sổ
    }

    // HÀM TÍNH TOÁN
    private void tinh() {
        try {
            double a = Double.parseDouble(txtA.getText());
            double b = Double.parseDouble(txtB.getText());

            if (rbAdd.isSelected()) {
                txtResult.setText(String.valueOf(a + b));
            } else if (rbSubtract.isSelected()) {
                txtResult.setText(String.valueOf(a - b));
            } else if (rbMultiply.isSelected()) {
                txtResult.setText(String.valueOf(a * b));
            } else if (rbDivide.isSelected()) {
                if (b != 0) {
                    txtResult.setText(String.valueOf(a / b));
                } else {
                    txtResult.setText("Lỗi: Không thể chia cho 0"); // Thông báo rõ ràng hơn
                }
            } else {
                txtResult.setText("Vui lòng chọn phép toán"); // Thông báo khi chưa chọn phép toán
            }
        } catch (NumberFormatException ex) {
            txtResult.setText("Lỗi nhập liệu: Vui lòng nhập số."); // Thông báo lỗi rõ ràng hơn
        }
    }

    // HÀM XÓA RỖNG
    private void xoa() {
        txtA.setText("");
        txtB.setText("");
        txtResult.setText("");
        // Bỏ chọn tất cả các radio button (tùy chọn)
        rbAdd.setSelected(false);
        rbSubtract.setSelected(false);
        rbMultiply.setSelected(false);
        rbDivide.setSelected(false);
        // Hoặc bạn có thể dùng group.clearSelection(); nhưng cần truy cập ButtonGroup
        // Tốt hơn là chọn lại nút Cộng làm mặc định nếu muốn
        // rbAdd.setSelected(true); 
        
        txtA.requestFocus(); // Đưa con trỏ về txtA sau khi xóa
    }

    // GỌI CONSTRUCTOR VÀO MAIN
    public static void main(String[] args) {
        // Đảm bảo rằng GUI được tạo trên Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            new Cau3();
        });
    }
}