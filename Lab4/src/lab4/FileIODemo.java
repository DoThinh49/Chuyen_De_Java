package lab4;

import java.io.*;
import java.util.Scanner;

public class FileIODemo {
    // Khai báo tên file là một hằng số để dễ dàng thay đổi nếu cần
    private static final String FILE_NAME = "demo.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n----- MENU -----");
            System.out.println("Chọn chức năng:");
            System.out.println("1. Ghi thêm nội dung vào file");
            System.out.println("2. Đọc nội dung từ file");
            System.out.println("3. Thoát");
            System.out.print("Lựa chọn của bạn: ");

            // Dùng try-catch để bắt lỗi nếu người dùng nhập chữ thay vì số
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Tiêu thụ ký tự newline còn lại

                switch (choice) {
                    case 1:
                        System.out.print("Nhập nội dung cần ghi: ");
                        String content = scanner.nextLine();
                        writeFile(content);
                        break;
                    case 2:
                        readFile();
                        break;
                    case 3:
                        System.out.println("Đã thoát chương trình.");
                        scanner.close();
                        return; // Kết thúc hàm main, thoát khỏi vòng lặp
                    default:
                        System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại!");
                }
            } catch (Exception e) {
                System.out.println("Lỗi: Vui lòng chỉ nhập số (1, 2, hoặc 3).");
                scanner.nextLine(); // Xóa bộ đệm đầu vào để tránh lặp vô hạn
            }
        }
    }

    /**
     * Ghi một chuỗi nội dung vào cuối file demo.txt
     * @param content Nội dung cần ghi
     */
    private static void writeFile(String content) {
        // Sử dụng try-with-resources để đảm bảo file luôn được đóng sau khi dùng
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(content); // Ghi nội dung
            writer.newLine();      // Ghi một dòng mới để lần ghi sau không bị dính liền
            System.out.println(">> Ghi file thành công!");
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }

    /**
     * Đọc toàn bộ nội dung từ file demo.txt và in ra màn hình
     */
    private static void readFile() {
        System.out.println("\n--- Nội dung file " + FILE_NAME + " ---");
        // Sử dụng try-with-resources để đảm bảo file luôn được đóng
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            // Vòng lặp đọc từng dòng cho đến khi hết file
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Lỗi: Không tìm thấy file '" + FILE_NAME + "'. Vui lòng ghi file trước.");
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
        }
        System.out.println("--- Kết thúc nội dung ---");
    }
}