package lab4;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;

public class BookManagerFrame {
    private JTable table;
    private DefaultTableModel model;
    private JTextField[] fields;
    private final String[] labels = {"Mã sách", "Tựa sách", "Tác giả", "Năm xuất bản", "Nhà xuất bản", "Số trang", "Đơn giá", "ISBN"};
    private JComboBox<String> searchBox;
    private final ArrayList<Book> bookList = new ArrayList<>();
    // Đường dẫn file đã được sửa lại để trỏ đến thư mục gốc của project
    private final File dataFile = new File("DanhMucSach.txt");

    public BookManagerFrame() {
        JFrame frame = new JFrame("Quản lý sách");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(4, 4, 10, 10));
        fields = new JTextField[labels.length];
        for (int i = 0; i < labels.length; i++) {
            formPanel.add(new JLabel(labels[i] + ":"));
            fields[i] = new JTextField();
            formPanel.add(fields[i]);
        }
        formPanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1), " Records Editor "));
        panel.add(formPanel, BorderLayout.NORTH);

        // Table Panel
        model = new DefaultTableModel();
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Button Panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton addBtn = new JButton("Thêm");
        JButton updateBtn = new JButton("Sửa");
        JButton deleteBtn = new JButton("Xóa");
        JButton clearBtn = new JButton("Xóa rỗng");
        searchBox = new JComboBox<>();
        JButton searchBtn = new JButton("Tìm");
        
        bottomPanel.add(addBtn);
        bottomPanel.add(updateBtn);
        bottomPanel.add(deleteBtn);
        bottomPanel.add(clearBtn);
        bottomPanel.add(Box.createHorizontalStrut(30)); // Thêm khoảng trống
        bottomPanel.add(new JLabel("Tìm theo mã:"));
        bottomPanel.add(searchBox);
        bottomPanel.add(searchBtn);
        
        panel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(panel);
        
        loadBooks();

        // Add event listeners
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    fillFieldsFromSelectedRow();
                }
            }
        });

        addBtn.addActionListener(e -> addBook());
        updateBtn.addActionListener(e -> updateBook());
        deleteBtn.addActionListener(e -> deleteBook());
        clearBtn.addActionListener(e -> clearFields());
        searchBtn.addActionListener(e -> searchBook());

        frame.setVisible(true);
    }
    
    private void fillFieldsFromSelectedRow() {
        int row = table.getSelectedRow();
        if (row != -1) {
            for (int i = 0; i < fields.length; i++) {
                fields[i].setText((String) model.getValueAt(row, i));
            }
        }
    }

    private void loadBooks() {
        bookList.clear();
        model.setRowCount(0);
        model.setColumnIdentifiers(labels);
        searchBox.removeAllItems();

        if (!dataFile.exists()) {
             try {
                // Nếu file không tồn tại, tạo file với dữ liệu mẫu
                dataFile.createNewFile();
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(dataFile))) {
                    bw.write("MaSach;TuaSach;TacGia;NamXuatBan;NhaXuatBan;SoTrang;DonGia;ISBN\n");
                    bw.write("A001;Annotations Reflection;Jakob Jenkov;2014;Prentice Hall;420;70000;0-13-376131-1\n");
                    bw.write("J002;Java Enterprise;O'Reilly;2012;Prentice Hall;314;120000;0-7506-6098-8\n");
                    bw.write("H003;Hibernate Tutorial;Gavin King;2014;Prentice Hall;352;90000;978-0-13-37611-3\n");
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Không thể tạo file dữ liệu mẫu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        try (BufferedReader br = new BufferedReader(new FileReader(dataFile))) {
            br.readLine(); // Bỏ qua dòng tiêu đề
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] data = line.split(";", -1); // -1 để giữ lại các trường rỗng
                    if(data.length == labels.length) {
                        Book book = new Book(data);
                        bookList.add(book);
                        model.addRow(data);
                        searchBox.addItem(data[0]);
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi đọc file: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveBooks() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(dataFile))) {
            bw.write(String.join(";", labels) + "\n");
            for (Book book : bookList) {
                bw.write(book.toCsv() + "\n");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi lưu file: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void addBook() {
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Trường '" + labels[i] + "' không được để trống.", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        String id = fields[0].getText().trim();
        String title = fields[1].getText().trim();
        String isbn = fields[7].getText().trim();

        for (Book b : bookList) {
            if (b.getId().equalsIgnoreCase(id)) {
                JOptionPane.showMessageDialog(null, "Lỗi: Mã sách '" + id + "' đã tồn tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        if (title.isEmpty() || !id.substring(0, 1).equalsIgnoreCase(title.substring(0, 1)) || !id.substring(1).matches("\\d{3}")) {
            JOptionPane.showMessageDialog(null, "Lỗi định dạng Mã sách.\nQuy tắc: Ký tự đầu phải là ký tự đầu của Tựa sách, theo sau là 3 chữ số (ví dụ: A001).", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isbn.matches("^\\d+(-\\d+){3,4}$")) {
            JOptionPane.showMessageDialog(null, "Lỗi định dạng ISBN.\nQuy tắc: Gồm các chữ số, chia thành 4 hoặc 5 nhóm, ngăn cách bởi dấu '-'.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] data = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            data[i] = fields[i].getText().trim();
        }
        
        Book book = new Book(data);
        bookList.add(book);
        model.addRow(data);
        searchBox.addItem(data[0]);
        saveBooks();
        JOptionPane.showMessageDialog(null, "Thêm sách mới thành công!");
        clearFields();
    }
    
    private void updateBook() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một dòng để sửa.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Book book = bookList.get(row);
        book.update(fields);

        for (int i = 0; i < fields.length; i++) {
            model.setValueAt(fields[i].getText(), row, i);
        }

        searchBox.removeItemAt(row);
        searchBox.insertItemAt(fields[0].getText(), row);
        searchBox.setSelectedIndex(row);

        saveBooks();
        JOptionPane.showMessageDialog(null, "Cập nhật sách thành công!");
    }

    private void deleteBook() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một dòng để xóa.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa sách này không?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            bookList.remove(row);
            model.removeRow(row);
            searchBox.removeItemAt(row);
            saveBooks();
            JOptionPane.showMessageDialog(null, "Xóa sách thành công!");
        }
    }
    
    private void clearFields() {
        for (JTextField field : fields) {
            field.setText("");
        }
        table.clearSelection();
    }
    
    private void searchBook() {
        String selectedId = (String) searchBox.getSelectedItem();
        if (selectedId == null) return;
        
        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getId().equals(selectedId)) {
                table.setRowSelectionInterval(i, i);
                table.scrollRectToVisible(table.getCellRect(i, 0, true));
                fillFieldsFromSelectedRow();
                return;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BookManagerFrame::new);
    }
}