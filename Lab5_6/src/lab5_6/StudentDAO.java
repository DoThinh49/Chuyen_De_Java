package lab5_6;

import java.sql.*;
import java.util.*;

public class StudentDAO {
    public List<Student> getAllStudents(String keyword) {
        List<Student> students = new ArrayList<>();
        String query;

        if (keyword == null || keyword.trim().isEmpty()) {
            query = "SELECT * FROM DMSV";
        } else {
            query = "SELECT * FROM DMSV WHERE CONCAT(HOSV, ' ', TENSV) LIKE ?";
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            if (keyword != null && !keyword.trim().isEmpty()) {
                pstmt.setString(1, "%" + keyword + "%");
            }

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                students.add(new Student(
                        rs.getString("MASV"),
                        rs.getString("HOSV"),
                        rs.getString("TENSV"),
                        rs.getBoolean("PHAI"),
                        rs.getString("NGAYSINH"),
                        rs.getString("NOISINH"),
                        rs.getString("MAKH"),
                        rs.getFloat("HOCBONG")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public boolean insertStudent(Student student) {
        String query = "INSERT INTO DMSV VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, student.getMaSV());
            pstmt.setString(2, student.getHoSV());
            pstmt.setString(3, student.getTenSV());
            pstmt.setBoolean(4, student.isPhai());
            pstmt.setString(5, student.getNgaySinh());
            pstmt.setString(6, student.getNoiSinh());
            pstmt.setString(7, student.getMaKH());
            pstmt.setFloat(8, student.getHocBong());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateStudent(Student student) {
        String query = "UPDATE DMSV SET HOSV=?, TENSV=?, PHAI=?, NGAYSINH=?, NOISINH=?, MAKH=?, HOCBONG=? WHERE MASV=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, student.getHoSV());
            pstmt.setString(2, student.getTenSV());
            pstmt.setBoolean(3, student.isPhai());
            pstmt.setString(4, student.getNgaySinh());
            pstmt.setString(5, student.getNoiSinh());
            pstmt.setString(6, student.getMaKH());
            pstmt.setFloat(7, student.getHocBong());
            pstmt.setString(8, student.getMaSV());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteStudent(String maSV) {
        String query = "DELETE FROM DMSV WHERE MASV=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, maSV);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}