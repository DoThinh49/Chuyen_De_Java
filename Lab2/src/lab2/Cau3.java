package lab2; // Đặt class này vào package lab2

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

// Import JDatePicker components
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class Cau3 extends JFrame { // Đổi tên class thành Cau3 theo yêu cầu

    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

    public Cau3() {
        setTitle("Phòng tổ chức"); // 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500); // Điều chỉnh kích thước cho phù hợp

        // Định nghĩa tiêu đề cột và dữ liệu mẫu cho bảng
        String[] columnNames = {"Mã số", "Họ", "Tên nhân viên", "Phái", "Ngày sinh", "Tiền lương"}; // 
        Object[][] data = { // 
                {"NV111", "Nguyễn", "An", "Nam", "01/03/1997", "3,000 $"}, // 
                {"NV222", "Trần", "Bình", "Nam", "21/03/1994", "4,000 $"}, // 
                {"NV333", "Hoàng", "Hoa", "Nam", "21/01/1995", "3,500 $"}  // 
        };

        // Chuyển đổi chuỗi ngày sinh sang đối tượng Date cho mô hình dữ liệu
        // Đây là bước cần thiết để JDatePicker và DateCellRenderer xử lý đúng
        Object[][] processedData = new Object[data.length][data[0].length];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (j == 4) { // Cột "Ngày sinh"
                    try {
                        processedData[i][j] = dateFormatter.parse((String) data[i][j]);
                    } catch (ParseException e) {
                        processedData[i][j] = null; // Xử lý lỗi nếu không phân tích được ngày
                        System.err.println("Lỗi phân tích ngày: " + data[i][j] + " - " + e.getMessage());
                    }
                } else {
                    processedData[i][j] = data[i][j];
                }
            }
        }


        // Tạo DefaultTableModel.
        // Ghi đè isCellEditable để cho phép chỉnh sửa tất cả trừ cột Mã số (0) nếu cần,
        // hoặc tất cả các cột đều có thể chỉnh sửa như trong bài này
        tableModel = new DefaultTableModel(processedData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Cho phép chỉnh sửa tất cả các cột
                // Nếu muốn Mã số không chỉnh sửa được: return column != 0;
                return true;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 4) { // Cột "Ngày sinh"
                    return Date.class;
                }
                // Nếu bạn có các cột số học (Tiền lương, Tuổi), bạn có thể trả về Integer.class hoặc Double.class
                // Ví dụ: if (columnIndex == 5) return Double.class;
                return Object.class;
            }
        };

        employeeTable = new JTable(tableModel);
        employeeTable.setRowHeight(25); // Tăng chiều cao hàng để JDatePicker dễ nhìn hơn

        JScrollPane scrollPane = new JScrollPane(employeeTable); // 
        add(scrollPane, BorderLayout.CENTER);

        // --- Thiết lập Cell Editor và Renderer cho cột "Phái" (Gender) ---
        TableColumn genderColumn = employeeTable.getColumnModel().getColumn(3);
        JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"Nam", "Nữ"}); // 
        genderColumn.setCellEditor(new DefaultCellEditor(genderComboBox));

        // --- Thiết lập Custom Cell Editor và Renderer cho cột "Ngày sinh" ---
        TableColumn dobColumn = employeeTable.getColumnModel().getColumn(4); // Cột "Ngày sinh" là index 4

        // Thiết lập Cell Editor tùy chỉnh với JDatePicker
        dobColumn.setCellEditor(new DatePickerCellEditor());

        // Thiết lập Cell Renderer tùy chỉnh để định dạng ngày
        dobColumn.setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            protected void setValue(Object value) {
                if (value instanceof Date) {
                    setText(dateFormatter.format((Date) value));
                } else {
                    super.setValue(value);
                }
            }
        });

        setLocationRelativeTo(null); // Đặt cửa sổ ra giữa màn hình
        setVisible(true);
    }

    // --- Custom TableCellEditor cho JDatePicker ---
    class DatePickerCellEditor extends AbstractCellEditor implements TableCellEditor {
        private JDatePickerImpl datePicker;
        private Date currentValue;

        public DatePickerCellEditor() {
            UtilDateModel model = new UtilDateModel();
            Properties p = new Properties();
            p.put("text.today", "Today");
            p.put("text.month", "Month");
            p.put("text.year", "Year");
            JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
            datePicker = new JDatePickerImpl(datePanel, new JFormattedTextField.AbstractFormatter() {
                private String datePattern = "dd/MM/yyyy";
                private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

                @Override
                public Object stringToValue(String text) throws ParseException {
                    return dateFormatter.parse(text);
                }

                @Override
                public String valueToString(Object value) throws ParseException {
                    if (value != null) {
                        Calendar cal = (Calendar) value;
                        return dateFormatter.format(cal.getTime());
                    }
                    return "";
                }
            });

            // Quan trọng: Thêm listener để khi ngày được chọn, editor sẽ dừng chỉnh sửa
            datePicker.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped(); // Thông báo rằng việc chỉnh sửa đã dừng
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            // Đặt giá trị hiện tại cho date picker
            if (value instanceof Date) {
                currentValue = (Date) value;
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(currentValue);
                datePicker.getModel().setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePicker.getModel().setSelected(true);
            } else {
                datePicker.getModel().setSelected(false);
            }
            return datePicker;
        }

        @Override
        public Object getCellEditorValue() {
            // Trả về ngày đã chọn
            if (datePicker.getModel().getValue() != null) {
                // JDatePicker trả về java.util.Date khi dùng UtilDateModel
                return datePicker.getModel().getValue();
            }
            return null;
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Cau3());
    }
}