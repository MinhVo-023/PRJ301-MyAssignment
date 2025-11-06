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
import model.Request;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestDAO {
    public void insertRequest(Request r) throws Exception {
        String sql = "INSERT INTO LeaveRequest (title, from_date, to_date, reason, status, created_by) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DBContext.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, r.getTitle());
            ps.setDate(2, new java.sql.Date(r.getFromDate().getTime()));
            ps.setDate(3, new java.sql.Date(r.getToDate().getTime()));
            ps.setString(4, r.getReason());
            ps.setString(5, r.getStatus());
            ps.setInt(6, r.getCreatedBy());
            ps.executeUpdate();
        }
    }

    public List<Request> getRequestsByUser(int userId) throws Exception {
        String sql = "SELECT * FROM LeaveRequest WHERE created_by = ? ORDER BY created_at DESC";
        List<Request> list = new ArrayList<>();
        try (Connection con = DBContext.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Request r = new Request();
                r.setId(rs.getInt("id"));
                r.setTitle(rs.getString("title"));
                r.setFromDate(rs.getDate("from_date"));
                r.setToDate(rs.getDate("to_date"));
                r.setReason(rs.getString("reason"));
                r.setStatus(rs.getString("status"));
                r.setCreatedBy(rs.getInt("created_by"));
                int pb = rs.getInt("processed_by");
                if (rs.wasNull()) r.setProcessedBy(null); else r.setProcessedBy(pb);
                list.add(r);
            }
        }
        return list;
    }

    public void updateStatus(int requestId, String status, Integer processedBy) throws Exception {
        String sql = "UPDATE LeaveRequest SET status = ?, processed_by = ? WHERE id = ?";
        try (Connection con = DBContext.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            if (processedBy == null) ps.setNull(2, Types.INTEGER); else ps.setInt(2, processedBy);
            ps.setInt(3, requestId);
            ps.executeUpdate();
        }
    }
    public Map<Integer, List<Request>> getLeavesByDepartmentAndDateRange(int depId, java.util.Date from, java.util.Date to) throws Exception {
        Map<Integer, List<Request>> map = new HashMap<>();
        String sql = "SELECT lr.*, u.department_id FROM LeaveRequest lr " +
                     "JOIN [User] u ON lr.created_by = u.id " +
                     "WHERE u.department_id = ? AND lr.status = 'Approved' " +
                     "AND (lr.from_date <= ? AND lr.to_date >= ?)";
        try (Connection con = util.DBContext.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, depId);
            ps.setDate(2, new java.sql.Date(to.getTime()));
            ps.setDate(3, new java.sql.Date(from.getTime()));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Request r = new Request();
                r.setId(rs.getInt("id"));
                r.setTitle(rs.getString("title"));
                r.setFromDate(rs.getDate("from_date"));
                r.setToDate(rs.getDate("to_date"));
                r.setReason(rs.getString("reason"));
                r.setStatus(rs.getString("status"));
                r.setCreatedBy(rs.getInt("created_by"));
                int pb = rs.getInt("processed_by");
                if (rs.wasNull()) r.setProcessedBy(null); else r.setProcessedBy(pb);

                map.computeIfAbsent(r.getCreatedBy(), k -> new java.util.ArrayList<>()).add(r);
            }
        }
        return map;
    }

}

