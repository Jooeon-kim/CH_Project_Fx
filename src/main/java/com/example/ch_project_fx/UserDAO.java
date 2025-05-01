package com.example.ch_project_fx;

import javafx.scene.image.Image;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class UserDAO {
public void saveUserToDB(User user) {
    String sql = "INSERT INTO users (id, pw, name, phone, address, imgPath, img, point, grade, totalPayed) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try (Connection conn = DBConnector.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, user.getId());
        pstmt.setString(2, user.getPw());
        pstmt.setString(3, user.getName());
        pstmt.setString(4, user.getPhone());
        pstmt.setString(5, user.getAddress());
        pstmt.setString(6, user.getImgPath());
        pstmt.setInt(8, user.getPoint());
        pstmt.setString(9, user.getGrade());
        pstmt.setInt(10, user.getTotalPayed());

        pstmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    public User login(String id, String pw) {
        String sql = "SELECT * FROM users WHERE id = ? AND pw = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            pstmt.setString(2, pw);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                // DB에서 유저 정보 가져와서 User 객체 생성
                User user = new User();
                user.setId(rs.getString("id"));
                user.setPw(rs.getString("pw"));
                user.setName(rs.getString("name"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setImgPath(rs.getString("imgPath"));
                user.setImg(new Image(getClass().getResource(user.getImgPath()).toExternalForm()));
                user.setPoint(rs.getInt("point"));
                user.setGrade(rs.getString("grade"));
                user.setTotalPayed(rs.getInt("totalPayed"));

                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // 로그인 실패
    }
}
