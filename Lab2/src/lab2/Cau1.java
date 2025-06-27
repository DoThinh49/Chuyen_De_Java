package lab2; 
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat; // Để định dạng tiền lương

public class Cau1 extends JFrame { // Đã đổi tên class thành Lab2 theo yêu cầu

    private JTextField idField;
    private JTextField lastNameField;
    private JTextField firstNameField;
    private JTextField ageField;
    private JTextField salaryField;
    private JRadioButton maleButton;
    private JRadioButton femaleButton;
    private ButtonGroup genderGroup;
    private JTable table;
    private DefaultTableModel model;
    private JTextField searchField;

    public Cau1() { // Constructor cũng đổi tên thành Lab2
        // Tạo JFrame với tiêu đề "Thông tin nhân viên" và thiết lập các thuộc tính cơ bản 
        setTitle("Thông tin nhân viên"); //
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 
        setSize(800, 600); // Điều chỉnh kích thước để dễ nhìn hơn, ban đầu là 600, 500 

        // Tạo panel nhập liệu với bố cục GridBagLayout và viền có tiêu đề 
        JPanel inputPanel = new JPanel(new GridBagLayout()); // 
        inputPanel.setBorder(BorderFactory.createTitledBorder("THÔNG TIN NHÂN VIÊN")); // 

        // Cấu hình các tham số cho GridBagLayout 
        GridBagConstraints gbc = new GridBagConstraints(); // 
        gbc.insets = new Insets(5, 5, 5, 5); // Đặt khoảng cách giữa các thành phần 
        gbc.fill = GridBagConstraints.HORIZONTAL; // Các thành phần sẽ co giãn theo chiều ngang 

        // Khởi tạo các trường nhập liệu cho thông tin nhân viên 
        idField = new JTextField(15); // 
        lastNameField = new JTextField(15); // 
        firstNameField = new JTextField(15); // 
        ageField = new JTextField(15); // 
        salaryField = new JTextField(15); // 

        // Tạo các radio button cho giới tính 
        maleButton = new JRadioButton("Nam"); // 
        femaleButton = new JRadioButton("Nữ"); // Đã sửa từ "NO"  sang "Nữ" dựa trên hình ảnh

        // Gom nhóm các radio button để chỉ chọn được 1 trong 2 lựa chọn 
        genderGroup = new ButtonGroup(); // 
        genderGroup.add(maleButton); // 
        genderGroup.add(femaleButton); // 

        // Thêm label và trường nhập liệu cho "Mã nhân viên" và "Họ" vào inputPanel 
        gbc.gridx = 0; // 
        gbc.gridy = 0; // 
        inputPanel.add(new JLabel("Mã nhân viên:"), gbc); // 
        gbc.gridx = 1; // 
        inputPanel.add(idField, gbc); // 

        gbc.gridx = 2; // 
        inputPanel.add(new JLabel("Họ:"), gbc); // 
        gbc.gridx = 3; // 
        inputPanel.add(lastNameField, gbc); // 

        // Thêm label và trường nhập liệu cho "Tên nhân viên" và "Tuổi" 
        gbc.gridx = 0; // 
        gbc.gridy = 1; // 
        inputPanel.add(new JLabel("Tên nhân viên:"), gbc); // 
        gbc.gridx = 1; // 
        inputPanel.add(firstNameField, gbc); // 

        gbc.gridx = 2; // 
        inputPanel.add(new JLabel("Tuổi:"), gbc); // 
        gbc.gridx = 3; // 
        inputPanel.add(ageField, gbc); // 

        // Thêm label và trường nhập liệu cho "Tiền lương" và "Phái" 
        gbc.gridx = 0; // 
        gbc.gridy = 2; // 
        inputPanel.add(new JLabel("Tiền lương:"), gbc); // 
        gbc.gridx = 1; // 
        inputPanel.add(salaryField, gbc); // 

        gbc.gridx = 2; // 
        inputPanel.add(new JLabel("Phái:"), gbc); // 
        gbc.gridx = 3; // 
        // Tạo một panel riêng để chứa các radio button của giới tính 
        JPanel genderPanel = new JPanel(); // 
        genderPanel.add(maleButton); // 
        genderPanel.add(femaleButton); // 
        inputPanel.add(genderPanel, gbc); // 

        // Định nghĩa tiêu đề cột và dữ liệu mẫu cho bảng hiển thị thông tin nhân viên 
        String[] columnNames = {"Mã NV", "Họ", "Tên", "Phái", "Tuổi", "Tiền lương"}; //
        Object[][] data = { // 
                {"1111", "Nguyễn", "Hoàng", "Nam", 26, "4,500 $"}, // 
                {"2222", "Lê", "Thu", "Nữ", 28, "5,000 $"}, // 
                {"3333", "Hoàng", "Lê", "Nam", 30, "5,000 $"}, // 
                {"4444", "Trần", "Lan", "Nữ", 27, "3,500 $"} // 
        };

        // Tạo model cho JTable và ghi đè phương thức isCellEditable để cột 0 (Mã NV) không được chỉnh sửa 
        model = new DefaultTableModel(data, columnNames) { //
            @Override
            public boolean isCellEditable(int row, int column) { // 
                return column != 0; // 
            }
        };

        // Tạo JTable và đặt model vừa tạo, sau đó đặt trong JScrollPane để có thanh cuộn 
        table = new JTable(model); // 
        JScrollPane scrollPane = new JScrollPane(table); // 

        // Thiết lập editor cho cột "Phái" bằng cách sử dụng ComboBox với các lựa chọn "Nam" và "Nữ" 
        TableColumn genderColumn = table.getColumnModel().getColumn(3); //
        genderColumn.setCellEditor(new DefaultCellEditor(new JComboBox<>(new String[]{"Nam", "Nữ"}))); //  (Sửa "NO" thành "Nữ")

        // Thêm ListSelectionListener cho JTable để cập nhật thông tin lên các trường nhập liệu khi chọn dòng 
        table.getSelectionModel().addListSelectionListener(event -> { //
            // Kiểm tra xem sự kiện đã được hoàn tất và có dòng nào được chọn hay không 
            if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) { //
                int selectedRow = table.getSelectedRow(); // 
                // Cập nhật các trường nhập liệu với dữ liệu của dòng được chọn 
                idField.setText(model.getValueAt(selectedRow, 0).toString()); //
                lastNameField.setText(model.getValueAt(selectedRow, 1).toString()); //
                firstNameField.setText(model.getValueAt(selectedRow, 2).toString()); //

                // Set radio button giới tính 
                if (model.getValueAt(selectedRow, 3).toString().equals("Nam")) { //
                    maleButton.setSelected(true); //
                } else { //
                    femaleButton.setSelected(true); //
                }
                ageField.setText(model.getValueAt(selectedRow, 4).toString()); // 
                String salary = model.getValueAt(selectedRow, 5).toString(); // 
                // Loại bỏ ký tự " $" ở cuối tiền lương trước khi hiển thị trong trường nhập liệu 
                salaryField.setText(salary.replace(" $", "")); // Đã sửa lại cách cắt chuỗi để đảm bảo đúng định dạng và tránh lỗi
            }
        });

        // Tạo panel chứa các nút chức năng và ô tìm kiếm, sử dụng GridLayout 
        JPanel buttonPanel = new JPanel(new GridLayout(1, 6)); // Điều chỉnh thành 6 cột để thêm nút "Xóa trắng"
        searchField = new JTextField(); // 
        JButton searchButton = new JButton("Tìm"); // 
        JButton addButton = new JButton("Thêm"); // 
        JButton clearButton = new JButton("Xóa trắng"); // Thêm nút "Xóa trắng" dựa trên hình ảnh 
        JButton deleteButton = new JButton("Xóa"); // 
        JButton saveButton = new JButton("Lưu"); // 

        // Thêm các thành phần vào panel chức năng 
        buttonPanel.add(new JLabel("Nhập mã số cần tìm:")); //
        buttonPanel.add(searchField); // 
        buttonPanel.add(searchButton); // 
        buttonPanel.add(addButton); // 
        buttonPanel.add(clearButton); // Thêm nút "Xóa trắng"
        buttonPanel.add(deleteButton); // 
        buttonPanel.add(saveButton); // 

        // Xử lý sự kiện khi nhấn các nút 

        // Xử lý sự kiện khi nhấn nút "Thêm" 
        addButton.addActionListener(e -> { //
            String id = idField.getText(); // 
            String lastName = lastNameField.getText(); // 
            String firstName = firstNameField.getText(); // 
            String ageStr = ageField.getText(); // 
            String salaryStr = salaryField.getText(); // 
            String gender = maleButton.isSelected() ? "Nam" : "Nữ"; //  (Sửa "NO" thành "Nữ")

            // Kiểm tra xem các trường nhập liệu có được điền đầy đủ hay không 
            if (!id.isEmpty() && !lastName.isEmpty() &&
                    !firstName.isEmpty() && !ageStr.isEmpty() && !salaryStr.isEmpty()) { //
                try {
                    int age = Integer.parseInt(ageStr);
                    String formattedSalary = salaryStr;
                    if (!formattedSalary.endsWith(" $")) {
                        formattedSalary += " $";
                    }

                    // Thêm một dòng mới vào model bảng với thông tin nhân viên 
                    model.addRow(new Object[]{id, lastName, firstName, gender, age, formattedSalary}); // 
                    // Xóa trắng các trường nhập liệu sau khi thêm
                    clearInputFields();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Tuổi và Tiền lương phải là số hợp lệ!", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
                }
            } else { // 
                // Hiển thị thông báo nếu chưa điền đầy đủ thông tin 
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE); //
            }
        });

        // Xử lý sự kiện khi nhấn nút "Xóa trắng" (mới thêm)
        clearButton.addActionListener(e -> {
            clearInputFields();
        });

        // Xử lý sự kiện khi nhấn nút "Tìm" 
        searchButton.addActionListener(e -> { // 
            String searchKey = searchField.getText(); // 
            boolean found = false;
            // Duyệt qua các dòng trong bảng để tìm mã nhân viên phù hợp 
            for (int i = 0; i < model.getRowCount(); i++) { // 
                if (model.getValueAt(i, 0).equals(searchKey)) { // 
                    // Chọn dòng tương ứng nếu tìm thấy 
                    table.setRowSelectionInterval(i, i); //
                    found = true;
                    break; // 
                }
            }
            if (!found) { // 
                // Hiển thị thông báo nếu không tìm thấy nhân viên 
                JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên!", "Không tìm thấy", JOptionPane.INFORMATION_MESSAGE); //
            }
        });

        // Xử lý sự kiện khi nhấn nút "Lưu" để cập nhật thông tin chỉnh sửa từ bảng 
        saveButton.addActionListener(e -> { //
            int selectedRow = table.getSelectedRow(); // 
            if (selectedRow != -1) { // 
                // Về bản chất, khi người dùng chỉnh sửa trực tiếp trên JTable, DefaultTableModel
                // sẽ tự động cập nhật dữ liệu. Nút "Lưu" ở đây có thể dùng để xác nhận
                // hoặc để lưu dữ liệu vào một nguồn bên ngoài (ví dụ: cơ sở dữ liệu, file).
                // Với bài tập này, giả định "Lưu" chỉ là xác nhận đã cập nhật trong bộ nhớ.
                JOptionPane.showMessageDialog(this, "Thông tin đã được cập nhật/lưu!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để lưu!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Xử lý sự kiện khi nhấn nút "Xóa" để loại bỏ dòng được chọn khỏi bảng 
        deleteButton.addActionListener(e -> { //
            int selectedRow = table.getSelectedRow(); // 
            if (selectedRow != -1) { // 
                model.removeRow(selectedRow); // 
                clearInputFields(); // Xóa trắng các trường sau khi xóa
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Sắp xếp các thành phần vào JFrame sử dụng BorderLayout 
        setLayout(new BorderLayout()); // 
        add(inputPanel, BorderLayout.NORTH); // Panel nhập liệu ở phía trên 
        add(scrollPane, BorderLayout.CENTER); // Bảng thông tin ở giữa 
        add(buttonPanel, BorderLayout.SOUTH); // Panel chức năng (tìm, thêm, xóa, lưu) ở dưới 

        // Hiển thị JFrame 
        setVisible(true); // 
    }

    // Phương thức để xóa trắng các trường nhập liệu
    private void clearInputFields() {
        idField.setText("");
        lastNameField.setText("");
        firstNameField.setText("");
        ageField.setText("");
        salaryField.setText("");
        genderGroup.clearSelection(); // Bỏ chọn radio button giới tính
    }

    public static void main(String[] args) { // 
        // Đảm bảo rằng việc tạo giao diện Swing được thực hiện trên Event Dispatch Thread 
        SwingUtilities.invokeLater(() -> new Cau1()); //  (Đã sửa lại để gọi constructor Lab2())
    }
}