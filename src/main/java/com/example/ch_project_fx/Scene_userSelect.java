package com.example.ch_project_fx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Scene_userSelect {

    public Scene getUserSelectScene() {
        // 상단 로고
        HBox topBox = new HBox();
        topBox.setPadding(new Insets(30, 0, 20, 0));
        topBox.setAlignment(Pos.CENTER);
        ImageView logoView = new ImageView(new Image(getClass().getResource("/img/logo.png").toExternalForm()));
        logoView.setFitHeight(80);
        logoView.setPreserveRatio(true);
        logoView.setOnMousePressed(e->{
            Scene_Login login = new Scene_Login();
            login.Login();
            CH_Application.getInstance().stage.setScene(CH_Application.getInstance().currentScene);

        });
        topBox.getChildren().add(logoView);

        // 유저 인사말
        VBox welcomeBox = new VBox(10);
        welcomeBox.setAlignment(Pos.CENTER);
        welcomeBox.setStyle("-fx-background-color: linear-gradient(to bottom right, #cceeff, #99ccff);"
                + "-fx-background-radius: 15;"
                + "-fx-border-radius: 15;"
                + "-fx-border-color: #3399ff;"
                + "-fx-border-width: 2;");
        Label welcomeLabel = new Label(CH_Application.getInstance().currentUser.getName() + "님 반가워요");
        welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        welcomeLabel.setTextFill(Color.web("#0066cc"));


        Label greetingLabel = new Label("오늘도 독서하기 좋은 날이네요!");
        greetingLabel.setFont(Font.font(16));
        greetingLabel.setTextFill(Color.web("#003366"));
        welcomeBox.getChildren().addAll(welcomeLabel, greetingLabel);

        // 메뉴 카드 (이미지 + 라벨)
        GridPane gridPane = new GridPane();
        gridPane.setHgap(40);
        gridPane.setVgap(20);
        gridPane.setPadding(new Insets(30));
        gridPane.setAlignment(Pos.CENTER);

        VBox miniGameBox = createMenuCard("/img/minigame.png", "미니게임");
        miniGameBox.setOnMousePressed(e->{
            Scene_Minigame SM = new Scene_Minigame();
            SM.SelectGame();
        });
        VBox libraryBox = createMenuCard("/img/library.png", "책 대여");
        libraryBox.setOnMousePressed(e->{
            Scene_Library SL = new Scene_Library();
            SL.openLibrary();
        });

        VBox marketBox = createMenuCard("/img/bookmarket.png", "온라인 서점");
        marketBox.setOnMousePressed(e->{
            Scene_bookMarket BM = new Scene_bookMarket();
            BM.openMarket();
        });
        gridPane.add(miniGameBox, 0, 0);
        gridPane.add(libraryBox, 1, 0);
        gridPane.add(marketBox, 2, 0);

        // 전체 레이아웃
        VBox root = new VBox(20);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(30));
        root.getChildren().addAll(topBox, welcomeBox, gridPane, createUserInfoBox());
        root.setStyle("-fx-background-color: #ffffff;" );
        return new Scene(root, 800, 600); // 적절한 크기 지정
    }

    private VBox createMenuCard(String imagePath, String titleText) {
        ImageView imageView = new ImageView(new Image(getClass().getResource(imagePath).toExternalForm()));
        imageView.setFitHeight(200);
        imageView.setPreserveRatio(true);

        Label title = new Label(titleText);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        VBox box = new VBox(10, imageView, title);
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-background-color: #f5f5f5; -fx-border-color: #cccccc; -fx-border-radius: 10; -fx-background-radius: 10;");
        box.setPadding(new Insets(15));
        box.setOnMouseEntered(e -> box.setStyle("-fx-background-color: #e0f7fa; -fx-border-color: #00796b; -fx-border-radius: 10; -fx-background-radius: 10;"));
        box.setOnMouseExited(e -> box.setStyle("-fx-background-color: #f5f5f5; -fx-border-color: #cccccc; -fx-border-radius: 10; -fx-background-radius: 10;"));


        return box;
    }
    private VBox createUserInfoBox() {
        Label nameLabel = new Label("유저이름 : " + CH_Application.getInstance().currentUser.getName());
        Label pointLabel = new Label("잔여 포인트 : " + CH_Application.getInstance().currentUser.getPoint());
        Label idLabel = new Label("아이디 : " + CH_Application.getInstance().currentUser.getId());
        Label gradeLabel = new Label("회원 등급 : " + CH_Application.getInstance().currentUser.getGrade());

        nameLabel.setFont(Font.font(14));
        pointLabel.setFont(Font.font(14));
        idLabel.setFont(Font.font(14));
        gradeLabel.setFont(Font.font(14));

        HBox row1 = new HBox(40, nameLabel, pointLabel);
        HBox row2 = new HBox(40, idLabel, gradeLabel);
        row1.setAlignment(Pos.CENTER_LEFT);
        row2.setAlignment(Pos.CENTER_LEFT);

        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        Label logoutLabel = new Label("로그아웃");
        Label infoLabel = new Label("회원정보보기");
        logoutLabel.setTextFill(Color.BLUE);
        infoLabel.setTextFill(Color.BLUE);
        logoutLabel.setOnMouseClicked(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setGraphic(null);
            alert.setContentText(CH_Application.getInstance().currentUser.getName()+"님 로그아웃 되었습니다 다음에봐요~!");
            alert.showAndWait();
            Scene_Login login = new Scene_Login();
            login.Login();
            CH_Application.getInstance().stage.setScene(CH_Application.getInstance().currentScene);
        });
        infoLabel.setOnMouseClicked(e -> {
            Scene_userInfo info = new Scene_userInfo();
            CH_Application.getInstance().stage.setScene(info.getUserInfoScene(CH_Application.getInstance().getCurrentUser()));
        });

        buttonBox.getChildren().addAll(logoutLabel, infoLabel);

        VBox userInfoBox = new VBox(10, row1, row2, buttonBox);
        userInfoBox.setPadding(new Insets(20));
        userInfoBox.setStyle("-fx-background-color: #f0f8ff; -fx-border-color: #a0c0ff; -fx-border-radius: 10; -fx-background-radius: 10;");
        return userInfoBox;
    }

}