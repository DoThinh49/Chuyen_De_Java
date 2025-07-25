package lab5_6;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StudentManagement {
    private JFrame frame;
    private JTable table;
    private JTextField txtMaSV, txtHoSV, txtTenSV, txtNgaySinh, txtNoiSinh, txtMaKH, txtHocBong, txtTimKiem;
    private JRadioButton rdoNam, rdoNu;
    private JButton btnThem, btnSua, btnXoa, btnTimKiem;
    private StudentDAO studentDAO;
    private DefaultTableModel model;

    public static void main(String[] args) {
        new StudentManagement();
    }

    public StudentManagement() {
        studentDAO = new StudentDAO();
        frame = new JFrame("Quản lý sinh viên");
        frame.setSize(900, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Bảng
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Mã SV", "Họ", "Tên", "Giới tính", "Ngày sinh", "Nơi sinh", "Mã KH", "Học bổng"});
        table = new JTable(model);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        // Panel nhập liệu
        JPanel inputPanel = new JPanel(new GridLayout(4, 4));
        txtMaSV = new JTextField(); txtHoSV = new JTextField(); txtTenSV = new JTextField();
        rdoNam = new JRadioButton("Nam"); rdoNu = new JRadioButton("Nữ");
        ButtonGroup genderGroup = new ButtonGroup(); genderGroup.add(rdoNam); genderGroup.add(rdoNu);
        JPanel gtPanel = new JPanel(); gtPanel.add(rdoNam); gtPanel.add(rdoNu);
        txtNgaySinh = new JTextField(); txtNoiSinh = new JTextField(); txtMaKH = new JTextField(); txtHocBong = new JTextField();
        inputPanel.add(new JLabel("Mã SV:")); inputPanel.add(txtMaSV);
        inputPanel.add(new JLabel("Họ:")); inputPanel.add(txtHoSV);
        inputPanel.add(new JLabel("Tên:")); inputPanel.add(txtTenSV);
        inputPanel.add(new JLabel("Giới tính:")); inputPanel.add(gtPanel);
        inputPanel.add(new JLabel("Ngày sinh:")); inputPanel.add(txtNgaySinh);
        inputPanel.add(new JLabel("Nơi sinh:")); inputPanel.add(txtNoiSinh);
        inputPanel.add(new JLabel("Mã KH:")); inputPanel.add(txtMaKH);
        inputPanel.add(new JLabel("Học bổng:")); inputPanel.add(txtHocBong);
        frame.add(inputPanel, BorderLayout.NORTH);

        // Nút + tìm kiếm
        JPanel buttonPanel = new JPanel();
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        txtTimKiem = new JTextField(15);
        btnTimKiem = new JButton("Tìm kiếm");

        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(txtTimKiem);     // giữa nút Xóa và Tìm kiếm
        buttonPanel.add(btnTimKiem);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Load dữ liệu
        loadStudents(null);

        // Click dòng -> hiển thị
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                txtMaSV.setText(model.getValueAt(row, 0).toString());
                txtHoSV.setText(model.getValueAt(row, 1).toString());
                txtTenSV.setText(model.getValueAt(row, 2).toString());
                (model.getValueAt(row, 3).equals("Nam") ? rdoNam : rdoNu).setSelected(true);
                txtNgaySinh.setText(model.getValueAt(row, 4).toString());
                txtNoiSinh.setText(model.getValueAt(row, 5).toString());
                txtMaKH.setText(model.getValueAt(row, 6).toString());
                txtHocBong.setText(model.getValueAt(row, 7).toString());
            }
        });

        // Thêm
        btnThem.addActionListener(e -> {
            Student s = getFormInput();
            if (studentDAO.insertStudent(s)) {
                JOptionPane.showMessageDialog(frame, "Thêm thành công!");
                loadStudents(null);
            }
        });

        // Sửa
        btnSua.addActionListener(e -> {
            Student s = getFormInput();
            if (studentDAO.updateStudent(s)) {
                JOptionPane.showMessageDialog(frame, "Sửa thành công!");
                loadStudents(null);
            }
        });

        // Xóa
        btnXoa.addActionListener(e -> {
            String maSV = txtMaSV.getText();
            if (studentDAO.deleteStudent(maSV)) {
                JOptionPane.showMessageDialog(frame, "Xóa thành công!");
                loadStudents(null);
            }
        });

        // Tìm kiếm
        btnTimKiem.addActionListener(e -> {
            loadStudents(txtTimKiem.getText());
        });

        frame.setVisible(true);
    }

    private Student getFormInput() {
        return new Student(
                txtMaSV.getText(), txtHoSV.getText(), txtTenSV.getText(),
                rdoNam.isSelected(), txtNgaySinh.getText(),
                txtNoiSinh.getText(), txtMaKH.getText(),
                Float.parseFloat(txtHocBong.getText())
        );
    }

    private void loadStudents(String keyword) {
        model.setRowCount(0);
        for (Student s : studentDAO.getAllStudents(keyword)) {
            model.addRow(new Object[]{
                    s.getMaSV(), s.getHoSV(), s.getTenSV(),
                    s.isPhai() ? "Nam" : "Nữ",
                    s.getNgaySinh(), s.getNoiSinh(),
                    s.getMaKH(), s.getHocBong()
            });
        }
    }
}
