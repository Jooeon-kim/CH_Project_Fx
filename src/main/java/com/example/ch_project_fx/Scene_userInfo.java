package com.example.ch_project_fx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Scene_userInfo {

    public Scene getUserInfoScene(User user) {
        VBox root = new VBox(20);  // root를 VBox로 다시 설정
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: #ffffff;");

        // 로고 상단 중앙
        ImageView logo = new ImageView(new Image(getClass().getResource("/img/logo.png").toExternalForm()));
        logo.setFitHeight(80);
        logo.setPreserveRatio(true);
        HBox logoBox = new HBox(logo);
        logoBox.setAlignment(Pos.CENTER);

        // 왼쪽에 유저 이미지 (사이즈 키움)
        VBox userImageBox = new VBox(10);
        ImageView userImg = new ImageView(user.getImg());
        userImg.setFitHeight(250);  // 유저 이미지 크기 키움
        userImg.setPreserveRatio(true);
        userImageBox.setAlignment(Pos.CENTER_LEFT);
        userImageBox.getChildren().add(userImg);

        // 오른쪽에 유저 정보 라벨들 (왼쪽 정렬)
        VBox userInfoBox = new VBox(10);
        userInfoBox.setAlignment(Pos.TOP_LEFT);  // 왼쪽 정렬
        Font labelFont = Font.font("Arial", FontWeight.BOLD, 14);

        userInfoBox.getChildren().add(createLabel("이름 : "+user.getName(), labelFont));

        userInfoBox.getChildren().add(createLabel("아이디 : "+user.getId(), labelFont));

        userInfoBox.getChildren().add(createLabel("잔여 포인트 : "+String.valueOf(user.getPoint()), labelFont));

        userInfoBox.getChildren().add(createLabel("회원 등급 : "+user.getGrade(), labelFont));

        userInfoBox.getChildren().add(createLabel("전화번호 : "+user.getPhone(), labelFont));

        userInfoBox.getChildren().add(createLabel("주소 :"+user.getAddress(), labelFont));

        userInfoBox.getChildren().add(createLabel("총 구매금액 :"+user.getTotalPayed()+" 원", labelFont));



        // 수정 필드 영역 (초기엔 숨김)
        VBox editBox = new VBox(10);
        editBox.setAlignment(Pos.CENTER_LEFT);
        editBox.setPadding(new Insets(20, 0, 0, 0));

        TextField phoneField = new TextField(user.getPhone());
        TextField addressField = new TextField(user.getAddress());
        PasswordField pwField = new PasswordField();
        pwField.setPromptText("새 비밀번호 입력");

        Button confirmButton = new Button("확인");
        confirmButton.setStyle("-fx-background-color: #66cc66; -fx-text-fill: white;");

        confirmButton.setOnAction(e -> {
            // 유저 정보 업데이트
            user.setPhone(phoneField.getText());
            user.setAddress(addressField.getText());
            user.setPw(pwField.getText());

            // DB 반영
            updateUserInfoInDB(user);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("수정 완료");
            alert.setHeaderText(null);
            alert.setContentText("회원 정보가 성공적으로 수정되었습니다.");
            alert.showAndWait();

            editBox.setVisible(false);
        });


        editBox.getChildren().addAll(
                new Label("전화번호:"), phoneField,
                new Label("주소:"), addressField,
                new Label("비밀번호:"), pwField,
                confirmButton
        );
        editBox.setVisible(false);
        // 회원정보수정 버튼과 뒤로가기 버튼
        HBox buttonBox = new HBox(10);
        Button editButton = new Button("회원정보수정");
        editButton.setPrefWidth(150);
        editButton.setOnAction(e -> editBox.setVisible(true));
        editButton.setStyle("-fx-background-color: #3399ff; -fx-text-fill: white; -fx-font-weight: bold;");

        Button backButton = new Button("뒤로가기");
        backButton.setPrefWidth(150);
        backButton.setStyle("-fx-background-color: #ff6666; -fx-text-fill: white; -fx-font-weight: bold;");
        backButton.setOnMouseClicked(e->{
            Scene_userSelect userSelect= new Scene_userSelect();
            CH_Application.getInstance().stage.setScene(userSelect.getUserSelectScene());
        });
        buttonBox.getChildren().addAll(editButton, backButton);
        buttonBox.setAlignment(Pos.CENTER);

        // 최종 레이아웃
        HBox contentBox = new HBox(20);
        contentBox.setAlignment(Pos.CENTER_LEFT);
        contentBox.getChildren().addAll(userImageBox, userInfoBox);

        root.getChildren().addAll(logoBox, contentBox, buttonBox, editBox);
        return new Scene(root, 800, 700);
    }

    // 내부 유틸 메소드
    private Label createLabel(String text, Font font) {
        Label label = new Label(text);
        label.setFont(font);
        return label;
    }
    public void updateUserInfoInDB(User user) {
        String sql = "UPDATE users SET phone = ?, address = ?, pw = ? WHERE id = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getPhone());
            pstmt.setString(2, user.getAddress());
            pstmt.setString(3, user.getPw());
            pstmt.setString(4, user.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}