package lab3;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MainFrame {

    private JFrame frame;
    private JTree tree;
    private DefaultTreeModel treeModel;
    private ArrayList<PhongBan> danhSachPhongBan = new ArrayList<>();

    public MainFrame() {
        frame = new JFrame("Quản lý nhân sự");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Panel nhập phòng ban
        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.setBorder(BorderFactory.createTitledBorder("THÔNG TIN TỔ CHỨC"));

        JTextField phongbanField = new JTextField(15);
        inputPanel.add(new JLabel("Tên phòng ban:"));
        inputPanel.add(phongbanField);

        JButton addButton = new JButton("Thêm");
        JButton exitButton = new JButton("Thoát");
        inputPanel.add(addButton);
        inputPanel.add(exitButton);

        // Tạo dữ liệu mẫu
        PhongBan phongTC = new PhongBan("Phòng tổ chức");
        phongTC.addNhanVien(new NhanVien("0111", "Nguyễn", "An", "Nam", "25", "3000"));
        phongTC.addNhanVien(new NhanVien("0222", "Lê", "Dung", "Nữ", "27", "7000"));
        danhSachPhongBan.add(phongTC);

        PhongBan phongKT = new PhongBan("Phòng kỹ thuật");
        phongKT.addNhanVien(new NhanVien("0333", "Hoàng", "Bình", "Nam", "25", "4500"));
        danhSachPhongBan.add(phongKT);

        // JTree
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Danh sách phòng ban");
        loadPhongban(root, danhSachPhongBan);
        treeModel = new DefaultTreeModel(root);
        tree = new JTree(treeModel);
        JScrollPane treeScrollPane = new JScrollPane(tree);

        // Sự kiện chọn phòng ban
        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                if (selectedNode != null && !selectedNode.isRoot()) {
                    phongbanField.setText(selectedNode.toString());
                    if (e.getClickCount() == 2) {
                        showEmployeeDialog(selectedNode.toString());
                    }
                }
            }
        });

        // Thêm phòng ban
        addButton.addActionListener(e -> {
            String tenPB = phongbanField.getText();
            if (!tenPB.isEmpty()) {
                danhSachPhongBan.add(new PhongBan(tenPB));
                loadPhongban(root, danhSachPhongBan);
                treeModel.reload();
                phongbanField.setText("");
            } else {
                JOptionPane.showMessageDialog(frame, "Vui lòng nhập tên phòng ban!");
            }
        });

        // Thoát
        exitButton.addActionListener(e -> System.exit(0));

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(treeScrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void showEmployeeDialog(String tenPhongBan) {
        JDialog dialog = new JDialog(frame, "Thông tin nhân viên - " + tenPhongBan, true);
        dialog.setSize(700, 400);
        dialog.setLocationRelativeTo(frame);

        PhongBan phongBan = danhSachPhongBan.stream()
                .filter(pb -> pb.getTen().equals(tenPhongBan))
                .findFirst().orElse(null);

        if (phongBan == null) return;

        String[] columnNames = {"Mã NV", "Họ", "Tên", "Phái", "Tuổi", "Tiền lương"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);

        loadData(tableModel, phongBan.getDanhSachNhanVien());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("THÔNG TIN NHÂN VIÊN"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField idField = new JTextField(15);
        JTextField hoField = new JTextField(15);
        JTextField tenField = new JTextField(15);
        JTextField tuoiField = new JTextField(15);
        JTextField luongField = new JTextField(15);

        JRadioButton male = new JRadioButton("Nam");
        JRadioButton female = new JRadioButton("Nữ");
        ButtonGroup group = new ButtonGroup();
        group.add(male);
        group.add(female);
        male.setSelected(true);

        JPanel genderPanel = new JPanel();
        genderPanel.add(male);
        genderPanel.add(female);

        // Layout
        gbc.gridx = 0; gbc.gridy = 0; inputPanel.add(new JLabel("Mã NV:"), gbc);
        gbc.gridx = 1; inputPanel.add(idField, gbc);
        gbc.gridx = 2; inputPanel.add(new JLabel("Họ:"), gbc);
        gbc.gridx = 3; inputPanel.add(hoField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; inputPanel.add(new JLabel("Tên:"), gbc);
        gbc.gridx = 1; inputPanel.add(tenField, gbc);
        gbc.gridx = 2; inputPanel.add(new JLabel("Tuổi:"), gbc);
        gbc.gridx = 3; inputPanel.add(tuoiField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; inputPanel.add(new JLabel("Lương:"), gbc);
        gbc.gridx = 1; inputPanel.add(luongField, gbc);
        gbc.gridx = 2; inputPanel.add(new JLabel("Phái:"), gbc);
        gbc.gridx = 3; inputPanel.add(genderPanel, gbc);

        JPanel controlPanel = new JPanel(new GridLayout(1, 6));
        JTextField searchField = new JTextField();
        JButton btnSearch = new JButton("Tìm");
        JButton btnAdd = new JButton("Thêm");
        JButton btnDelete = new JButton("Xóa");
        JButton btnSave = new JButton("Lưu");

        controlPanel.add(new JLabel("Mã cần tìm:"));
        controlPanel.add(searchField);
        controlPanel.add(btnSearch);
        controlPanel.add(btnAdd);
        controlPanel.add(btnDelete);
        controlPanel.add(btnSave);

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                idField.setText(tableModel.getValueAt(row, 0).toString());
                hoField.setText(tableModel.getValueAt(row, 1).toString());
                tenField.setText(tableModel.getValueAt(row, 2).toString());
                if (tableModel.getValueAt(row, 3).toString().equals("Nam")) {
                    male.setSelected(true);
                } else {
                    female.setSelected(true);
                }
                tuoiField.setText(tableModel.getValueAt(row, 4).toString());
                luongField.setText(tableModel.getValueAt(row, 5).toString());
            }
        });

        btnAdd.addActionListener(e -> {
            if (idField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng nhập đầy đủ thông tin!");
                return;
            }
            String gender = male.isSelected() ? "Nam" : "Nữ";
            NhanVien nv = new NhanVien(idField.getText(), hoField.getText(), tenField.getText(),
                    gender, tuoiField.getText(), luongField.getText());
            phongBan.addNhanVien(nv);
            loadData(tableModel, phongBan.getDanhSachNhanVien());
        });

        btnSearch.addActionListener(e -> {
            String key = searchField.getText();
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if (tableModel.getValueAt(i, 0).equals(key)) {
                    table.setRowSelectionInterval(i, i);
                    return;
                }
            }
            JOptionPane.showMessageDialog(dialog, "Không tìm thấy nhân viên!");
        });

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                phongBan.removeNhanVien(phongBan.getDanhSachNhanVien().get(row));
                loadData(tableModel, phongBan.getDanhSachNhanVien());
            }
        });

        btnSave.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                String gender = male.isSelected() ? "Nam" : "Nữ";
                NhanVien nv = new NhanVien(idField.getText(), hoField.getText(), tenField.getText(),
                        gender, tuoiField.getText(), luongField.getText());
                phongBan.updateNhanVien(nv, row);
                loadData(tableModel, phongBan.getDanhSachNhanVien());
            }
        });

        dialog.add(inputPanel, BorderLayout.NORTH);
        dialog.add(tableScrollPane, BorderLayout.CENTER);
        dialog.add(controlPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void loadData(DefaultTableModel model, ArrayList<NhanVien> ds) {
        model.setRowCount(0);
        for (NhanVien nv : ds) {
            model.addRow(new Object[]{
                    nv.getMa(), nv.getHo(), nv.getTen(), nv.getPhai(), nv.getTuoi(), nv.getLuong()
            });
        }
    }

    private void loadPhongban(DefaultMutableTreeNode root, ArrayList<PhongBan> dsPB) {
        root.removeAllChildren();
        for (PhongBan pb : dsPB) {
            root.add(new DefaultMutableTreeNode(pb.getTen()));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
//////