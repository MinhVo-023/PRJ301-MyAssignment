/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Minh
 */

import util.DBContext;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class FeatureDAO {

    /**
     * Kiểm tra user có quyền truy cập uri (action) hay không.
     * Cách đơn giản: lấy tất cả feature urls của các role của user và so sánh contains.
     */
    public boolean userHasAccess(int userId, String uri) throws Exception {
        String sql = "SELECT f.url FROM Feature f " +
                     "JOIN Role_Feature rf ON f.id = rf.feature_id " +
                     "JOIN User_Role ur ON rf.role_id = ur.role_id " +
                     "WHERE ur.user_id = ?";
        try (Connection con = DBContext.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String featureUrl = rs.getString("url");
                if (featureUrl == null) continue;
                if (uri.contains(featureUrl) || uri.equals(featureUrl) || uri.endsWith(featureUrl)) {
                    return true;
                }
            }
        }
        return false;
    }
}

