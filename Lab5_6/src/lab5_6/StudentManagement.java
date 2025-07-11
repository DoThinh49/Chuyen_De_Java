package lab5_6;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StudentManagement {
    private JFrame frame;
    private JTable table;
    private JTextField txtMaSV, txtHoSV, txtTenSV, txtNgaySinh, txtNoiSinh, txtMaKH, txtHocBong, txtTimKiem;
    private JRadioButton rdoNam, rdoNu;
    private JButton btnSua, btnXoa, btnTimKiem;
    private StudentDAO studentDAO;
    private DefaultTableModel model;

    public StudentManagement() {
        studentDAO = new StudentDAO();
        createAndShowGUI();
        addListeners();
        loadAllStudents();
    }

    private void createAndShowGUI() {
        frame = new JFrame("Quản lý sinh viên");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));
        frame.setLocationRelativeTo(null);

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Mã SV", "Họ", "Tên", "Giới tính", "Ngày sinh", "Nơi sinh", "Mã Khoa", "Học bổng"});
        table = new JTable(model);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel inputPanel = createInputPanel();
        frame.add(inputPanel, BorderLayout.NORTH);

        JPanel buttonPanel = createButtonPanel();
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
    
    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 4, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Thông tin sinh viên"));

        txtMaSV = new JTextField();
        txtHoSV = new JTextField();
        txtTenSV = new JTextField();
        txtNgaySinh = new JTextField();
        txtNoiSinh = new JTextField();
        txtMaKH = new JTextField();
        txtHocBong = new JTextField();
        rdoNam = new JRadioButton("Nam");
        rdoNu = new JRadioButton("Nữ", true);
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(rdoNam);
        genderGroup.add(rdoNu);
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genderPanel.add(rdoNu);
        genderPanel.add(rdoNam);

        panel.add(new JLabel("Mã SV:")); panel.add(txtMaSV);
        panel.add(new JLabel("Họ SV:")); panel.add(txtHoSV);
        panel.add(new JLabel("Tên SV:")); panel.add(txtTenSV);
        panel.add(new JLabel("Giới tính:")); panel.add(genderPanel);
        panel.add(new JLabel("Ngày sinh (yyyy-mm-dd):")); panel.add(txtNgaySinh);
        panel.add(new JLabel("Nơi sinh:")); panel.add(txtNoiSinh);
        panel.add(new JLabel("Mã Khoa:")); panel.add(txtMaKH);
        panel.add(new JLabel("Học bổng:")); panel.add(txtHocBong);
        
        txtMaSV.setEditable(false); // Mã SV không nên sửa
        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Chức năng"));

        panel.add(new JLabel("Tìm kiếm theo tên:"));
        txtTimKiem = new JTextField(15);
        panel.add(txtTimKiem);

        btnTimKiem = new JButton("Tìm kiếm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");

        panel.add(btnTimKiem);
        panel.add(btnSua);
        panel.add(btnXoa);
        return panel;
    }

    private void addListeners() {
        // Yêu cầu 1: Click vào bảng
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                populateFormFromSelectedRow();
            }
        });

        // Yêu cầu 2: Nút Tìm kiếm
        btnTimKiem.addActionListener(e -> handleSearch());

        // Yêu cầu 3: Nút Sửa
        btnSua.addActionListener(e -> handleUpdate());

        // Yêu cầu 4: Nút Xóa
        btnXoa.addActionListener(e -> handleDelete());
    }

    private void populateFormFromSelectedRow() {
        int row = table.getSelectedRow();
        txtMaSV.setText(model.getValueAt(row, 0).toString());
        txtHoSV.setText(model.getValueAt(row, 1).toString());
        txtTenSV.setText(model.getValueAt(row, 2).toString());
        boolean isFemale = model.getValueAt(row, 3).toString().equals("Nữ");
        rdoNu.setSelected(isFemale);
        rdoNam.setSelected(!isFemale);
        txtNgaySinh.setText(model.getValueAt(row, 4).toString().split(" ")[0]);
        txtNoiSinh.setText(model.getValueAt(row, 5).toString());
        txtMaKH.setText(model.getValueAt(row, 6).toString());
        txtHocBong.setText(model.getValueAt(row, 7).toString());
    }

    private void handleSearch() {
        String keyword = txtTimKiem.getText().trim();
        if (keyword.isEmpty()) {
            loadAllStudents();
        } else {
            List<Student> students = studentDAO.searchStudentsByName(keyword);
            displayStudents(students);
        }
    }

    private void handleUpdate() {
        if (table.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(frame, "Vui lòng chọn sinh viên để sửa.", "Chưa chọn sinh viên", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            Student student = new Student(
                txtMaSV.getText(),
                txtHoSV.getText(),
                txtTenSV.getText(),
                rdoNu.isSelected(),
                txtNgaySinh.getText(),
                txtNoiSinh.getText(),
                txtMaKH.getText(),
                Float.parseFloat(txtHocBong.getText())
            );
            if (studentDAO.updateStudent(student)) {
                JOptionPane.showMessageDialog(frame, "Cập nhật thành công!");
                loadAllStudents();
            } else {
                JOptionPane.showMessageDialog(frame, "Cập nhật thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Học bổng phải là một số.", "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleDelete() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Vui lòng chọn sinh viên để xóa.", "Chưa chọn sinh viên", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int choice = JOptionPane.showConfirmDialog(frame, "Bạn có chắc chắn muốn xóa sinh viên này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            String maSV = txtMaSV.getText();
            if (studentDAO.deleteStudent(maSV)) {
                JOptionPane.showMessageDialog(frame, "Xóa thành công!");
                loadAllStudents();
            } else {
                JOptionPane.showMessageDialog(frame, "Xóa thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadAllStudents() {
        List<Student> students = studentDAO.getAllStudents();
        displayStudents(students);
    }

    private void displayStudents(List<Student> students) {
        model.setRowCount(0); // Xóa dữ liệu cũ
        for (Student s : students) {
            model.addRow(new Object[]{
                s.getMaSV(), s.getHoSV(), s.getTenSV(),
                s.isPhai() ? "Nữ" : "Nam",
                s.getNgaySinh(), s.getNoiSinh(), s.getMaKH(), s.getHocBong()
            });
        }
    }

    public static void main(String[] args) {
        // Đảm bảo giao diện được tạo trên Event Dispatch Thread
        SwingUtilities.invokeLater(StudentManagement::new);
    }
}