/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author Minh
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBContext {
    private final static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    
    private final static String url = "jdbc:sqlserver://localhost:1433;databaseName=LeaveManagement;encrypt=true;trustServerCertificate=true";
    private final static String user = "sa";
    private final static String pass = "12345";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        return DriverManager.getConnection(url, user, pass);
    }

//    public static void main(String[] args) {
//        System.out.println("--- [DEBUG] Thử kết nối đến SQL Server ---");
//        try {
//            // 1. Lấy kết nối
//            Connection conn = DBContext.getConnection();
//
//            // 2. Nếu dòng trên không ném Exception, nghĩa là thành công
//            if (conn != null) {
//                System.out.println("******************************************");
//                System.out.println(">>> KẾT NỐI THÀNH CÔNG! <<<");
//                System.out.println("Database: " + conn.getMetaData().getDatabaseProductName());
//                System.out.println("URL: " + conn.getMetaData().getURL());
//                System.out.println("User: " + conn.getMetaData().getUserName());
//                System.out.println("******************************************");
//                conn.close();
//            } else {
//                System.out.println("!!! KẾT NỐI THẤT BẠI (Connection là null) !!!");
//            }
//
//        } catch (Exception e) {
//            // 3. Nếu kết nối thất bại, nó sẽ nhảy vào đây
//            System.err.println("!!! KẾT NỐI THẤT BẠI (ĐÃ XẢY RA LỖI) !!!");
//            System.err.println("--- Nguyên nhân (Root Cause): ---");
//            // In ra lỗi
//            e.printStackTrace();
//        }
//    }
}
