package com.example.ch_project_fx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class Scene_userInfo {

    public Scene getUserInfoScene(User user) {
        VBox userInfoRoot  = new VBox(20);  // root를 VBox로 다시 설정
        userInfoRoot .setPadding(new Insets(30));
        userInfoRoot .setAlignment(Pos.TOP_CENTER);
        userInfoRoot.getStyleClass().add("root");
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
        if(!user.getCoupons().isEmpty()){
            userInfoBox.getChildren().add(createLabel("보유 쿠폰 👇🏻",labelFont));
            for(Coupon c : user.getCoupons()){
                userInfoBox.getChildren().add(createLabel(c.getName(),labelFont));
            }
        }


        // 수정 필드 영역 (초기엔 숨김)
        VBox editBox = new VBox(10);
        editBox.setAlignment(Pos.CENTER_LEFT);
        editBox.setPadding(new Insets(20, 0, 0, 0));
        editBox.getStyleClass().add("user-info-container");
        TextField phoneField = new TextField(user.getPhone());
        TextField addressField = new TextField(user.getAddress());
        PasswordField pwField = new PasswordField();
        phoneField.getStyleClass().add("text-field");
        addressField.getStyleClass().add("text-field");
        pwField.getStyleClass().add("text-field");
        pwField.setPromptText("새 비밀번호 입력");

        Button confirmButton = new Button("확인");
        confirmButton.getStyleClass().add("button-green");

        confirmButton.setOnAction(e -> {
            // 유저 정보 업데이트
            user.setPhone(phoneField.getText());
            user.setAddress(addressField.getText());
            user.setPw(pwField.getText());

            // DB 반영
            updateUserInfoInDB(user);

            UserDAO userDAO = new UserDAO();
            User updatedUser = userDAO.login(user.getId(), user.getPw());

            CH_Application.getInstance().setCurrentUser(updatedUser);


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("수정 완료");
            alert.setHeaderText(null);
            alert.setContentText("회원 정보가 성공적으로 수정되었습니다.");
            alert.showAndWait();

            editBox.setVisible(false);
            Scene_userInfo newScene = new Scene_userInfo();
            Scene refreshedScene = newScene.getUserInfoScene(user);  // 현재 user 객체 다시 사용 (단점: 반영 X일 수 있음)
            CH_Application.getInstance().stage.setScene(refreshedScene);
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
        editButton.getStyleClass().add("button-blue");
        editButton.setPrefWidth(150);
        editButton.setOnAction(e -> editBox.setVisible(true));
        editButton.setStyle("-fx-background-color: #3399ff; -fx-text-fill: white; -fx-font-weight: bold;");

        Button showPurchaseList = new Button("구매목록");
        showPurchaseList.setPrefWidth(150);
        showPurchaseList.setStyle("-fx-background-color: #3399ff; -fx-text-fill: white; -fx-font-weight: bold;");
        showPurchaseList.setOnMousePressed(e->{
            PurchaseDAO PD = new PurchaseDAO();
            List <Book> books = PD.getUserPurchaseHistory(user.getId());
            if(books.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setGraphic(null);
                alert.setContentText("구매내역이 없습니다");
                alert.showAndWait();
            }else{
                VBox buyList = new VBox(10);
                buyList.setStyle("-fx-background-color: #f4f4f4;");
                Label titleLabel = new Label("📚 구매 내역");
                titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
                titleLabel.setTextFill(Color.DARKBLUE);
                buyList.getChildren().add(titleLabel);

                for(Book b : books){
                    HBox itemBox = new HBox(10);
                    itemBox.setPadding(new Insets(5));
                    itemBox.setAlignment(Pos.CENTER_LEFT);

                    Label bookTitle = new Label("《" + b.getTitle() + "》");
                    bookTitle.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
                    bookTitle.setTextFill(Color.BLACK);

                    Label amountLabel = new Label(b.getAmount() + "개");
                    amountLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
                    amountLabel.setTextFill(Color.GRAY);

                    itemBox.getChildren().addAll(bookTitle, amountLabel);
                    buyList.getChildren().add(itemBox);
                }
                Button check = new Button("확인");
                check.setStyle("-fx-background-color: #3399ff; -fx-text-fill: white; -fx-font-weight: bold;");
                check.setOnMousePressed(u -> {
                    ((Stage) check.getScene().getWindow()).close();
                });

                buyList.getChildren().add(check);
                buyList.setAlignment(Pos.CENTER);

                Scene miniScene = new Scene(buyList,500,400);
                Stage popUp = new Stage();
                popUp.setScene(miniScene);
                popUp.initModality(Modality.APPLICATION_MODAL);
                popUp.showAndWait();

            }
        });

        Button backButton = new Button("뒤로가기");
        backButton.setPrefWidth(150);
        backButton.getStyleClass().add("button-red");
        backButton.setStyle("-fx-background-color: #ff6666; -fx-text-fill: white; -fx-font-weight: bold;");
        backButton.setOnMouseClicked(e->{
            Scene_userSelect userSelect= new Scene_userSelect();
            CH_Application.getInstance().stage.setScene(userSelect.getUserSelectScene());
        });
        buttonBox.getChildren().addAll(editButton, backButton,showPurchaseList);
        buttonBox.setAlignment(Pos.CENTER);

        // 최종 레이아웃
        HBox contentBox = new HBox(20);
        contentBox.setAlignment(Pos.CENTER_LEFT);

        userImageBox.getStyleClass().add("user-image-box");
        userInfoBox.getStyleClass().add("user-info-box");
        contentBox.getChildren().addAll(userImageBox, userInfoBox);
        contentBox.getStyleClass().add("user-info-container");

        userInfoRoot.getChildren().addAll(logoBox, contentBox, buttonBox, editBox);
        Scene userInfoScene = new Scene(userInfoRoot);
        userInfoScene.getStylesheets().add(getClass().getResource("/css/userinfo.css").toExternalForm());

        return userInfoScene;
    }

    // 내부 유틸 메소드
    private Label createLabel(String text, Font font) {
        Label label = new Label(text);
        label.getStyleClass().add("label-info");
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