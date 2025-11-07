package dao;

import model.Department;
import util.DBContext;
import java.sql.*;
import java.util.*;

public class DepartmentDAO {

    // Lấy tất cả phòng ban
    public List<Department> getAllDepartments() throws Exception {
        List<Department> list = new ArrayList<>();
        String sql = "SELECT id, name FROM Department";
        try (Connection con = DBContext.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Department d = new Department();
                d.setId(rs.getInt("id"));       // <-- dùng setId, không phải setID
                d.setName(rs.getString("name"));
                list.add(d);
            }
        }
        return list;
    }

    // Lấy phòng ban theo id
    public Department getDepartmentById(int id) throws Exception {
        String sql = "SELECT id, name FROM Department WHERE id = ?";
        try (Connection con = DBContext.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Department d = new Department();
                d.setId(rs.getInt("id"));     // <-- setId
                d.setName(rs.getString("name"));
                return d;
            }
        }
        return null;
    }

    // (Tùy chọn) Thêm phòng ban mới
    public void insertDepartment(String name) throws Exception {
        String sql = "INSERT INTO Department (name) VALUES (?)";
        try (Connection con = DBContext.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.executeUpdate();
        }
    }
    public String getDepartmentNameById(int id) throws Exception {
        String sql = "SELECT name FROM Department WHERE id = ?";
        
        try (Connection con = DBContext.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy DepartmentName: " + e.getMessage());
            throw e; // Ném lỗi ra ngoài để MainController xử lý
        }
        return null; // Trả về null nếu không tìm thấy  
    }
}
