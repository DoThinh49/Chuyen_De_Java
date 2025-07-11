package lab5_6;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/QLDIEM";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Mật khẩu rỗng cho XAMPP

    public static Connection getConnection() {
        try {
            // Class.forName("com.mysql.cj.jdbc.Driver"); // Không cần thiết ở các bản JDBC mới
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            // Ném một ngoại lệ runtime để ứng dụng dừng lại nếu không kết nối được
            throw new RuntimeException("Không thể kết nối đến cơ sở dữ liệu!", e);
        }
    }
}