package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Feature;
import util.DBContext;

public class FeatureDAO {
    
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
    // Lấy tất cả Feature mà 1 Role có quyền truy cập
    public List<Feature> getFeaturesByRoleId(int roleId) {
        List<Feature> list = new ArrayList<>();
        String sql = "SELECT f.id, f.name, f.url " +
                     "FROM Role_Feature rf " +
                     "JOIN Feature f ON rf.feature_id = f.id " +
                     "WHERE rf.role_id = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, roleId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Feature f = new Feature(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("url")
                    );
                    list.add(f);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Kiểm tra xem role có quyền truy cập vào 1 URL cụ thể hay không
    public boolean hasAccess(int roleId, String actionUrl) {
        String sql = "SELECT COUNT(*) FROM Role_Feature rf " +
                     "JOIN Feature f ON rf.feature_id = f.id " +
                     "WHERE rf.role_id = ? AND f.url = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, roleId);
            ps.setString(2, actionUrl);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
}
