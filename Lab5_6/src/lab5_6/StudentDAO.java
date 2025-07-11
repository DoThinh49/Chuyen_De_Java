package lab5_6;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM DMSV";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                students.add(mapRowToStudent(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
    
    public List<Student> searchStudentsByName(String name) {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM DMSV WHERE CONCAT(HOSV, ' ', TENSV) LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "%" + name + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    students.add(mapRowToStudent(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public boolean updateStudent(Student student) {
        String query = "UPDATE DMSV SET HOSV = ?, TENSV = ?, PHAI = ?, NGAYSINH = ?, NOISINH = ?, MAKH = ?, HOCBONG = ? WHERE MASV = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            setPreparedStatementFromStudent(pstmt, student);
            pstmt.setString(8, student.getMaSV()); // For WHERE clause
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteStudent(String maSV) {
        String query = "DELETE FROM DMSV WHERE MASV = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, maSV);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Student mapRowToStudent(ResultSet rs) throws SQLException {
        return new Student(
                rs.getString("MASV"),
                rs.getString("HOSV"),
                rs.getString("TENSV"),
                rs.getBoolean("PHAI"),
                rs.getString("NGAYSINH"),
                rs.getString("NOISINH"),
                rs.getString("MAKH"),
                rs.getFloat("HOCBONG")
        );
    }
    
    private void setPreparedStatementFromStudent(PreparedStatement pstmt, Student student) throws SQLException {
        pstmt.setString(1, student.getHoSV());
        pstmt.setString(2, student.getTenSV());
        pstmt.setBoolean(3, student.isPhai());
        pstmt.setString(4, student.getNgaySinh());
        pstmt.setString(5, student.getNoiSinh());
        pstmt.setString(6, student.getMaKH());
        pstmt.setFloat(7, student.getHocBong());
    }
}