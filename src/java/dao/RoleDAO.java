package dao;

import model.Role;
import util.DBContext;
import java.sql.*;
import java.util.*;

public class RoleDAO {

    // Lấy tất cả role trong hệ thống
    public List<Role> getAllRoles() throws Exception {
        List<Role> list = new ArrayList<>();
        String sql = "SELECT * FROM [Role]";
        try (Connection con = DBContext.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Role r = new Role();
                r.setId(rs.getInt("id"));
                r.setName(rs.getString("name"));
                list.add(r);
            }
        }
        return list;
    }

    // Lấy các role của một user cụ thể
    public List<Role> getRolesByUserId(int userId) throws Exception {
        List<Role> list = new ArrayList<>();
        String sql = "SELECT r.id, r.name FROM [Role] r " +
                     "JOIN User_Role ur ON r.id = ur.role_id " +
                     "WHERE ur.user_id = ?";
        try (Connection con = DBContext.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Role r = new Role();
                r.setId(rs.getInt("id"));
                r.setName(rs.getString("name"));
                list.add(r);
            }
        }
        return list;
    }

    // (Tùy chọn) Thêm role mới
    public void insertRole(String name) throws Exception {
        String sql = "INSERT INTO [Role] (name) VALUES (?)";
        try (Connection con = DBContext.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.executeUpdate();
        }
    }
}

