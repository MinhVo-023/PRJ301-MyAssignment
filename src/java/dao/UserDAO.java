package dao;

/**
 *
 * @author Minh
 */

import model.User;
import util.DBContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public User authenticate(String username, String password) throws Exception {
        String sql = "SELECT id, username, fullname, department_id, manager_id FROM [User] WHERE username = ? AND password = ?";
        try (Connection con = DBContext.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password); // in prod: use hashed password
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setFullname(rs.getString("fullname"));
                u.setDepartmentId(rs.getInt("department_id"));
                int mgr = rs.getInt("manager_id");
                if (rs.wasNull()) u.setManagerId(null); else u.setManagerId(mgr);
                return u;
            }
        }
        return null;
    }

    // get roles of user
    public java.util.List<String> getRolesByUserId(int userId) throws Exception {
        String sql = "SELECT r.name FROM [Role] r JOIN User_Role ur ON r.id = ur.role_id WHERE ur.user_id = ?";
        java.util.List<String> roles = new java.util.ArrayList<>();
        try (Connection con = DBContext.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) roles.add(rs.getString("name"));
        }
        return roles;
    }
    public List<User> getUsersByDepartment(int departmentId) throws Exception {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM [User] WHERE department_id = ?";
        try (Connection con = util.DBContext.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, departmentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setFullname(rs.getString("fullname"));
                u.setDepartmentId(rs.getInt("department_id"));
                int mgr = rs.getInt("manager_id");
                if (rs.wasNull()) u.setManagerId(null); else u.setManagerId(mgr);
                list.add(u);
            }
        }
        return list;
    }
    

}
