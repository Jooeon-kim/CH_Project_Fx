package com.example.ch_project_fx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.Comparator;
import java.util.List;

public class Scene_bookMarket {
    BookDAO dao = new BookDAO();
    List<Book> books;
    List<Book> bestSeller;
    VBox Menu = new VBox(20);
    HBox Bookinfo = new HBox(20);

    void openMarket() {
        this.books = dao.getAllBooksFromDB();
        List<Book> bestSeller = books.stream().sorted(Comparator.comparingInt(Book::getAmount).reversed()) // amount 기준 내림차순
                .limit(3)
                .toList();
        this.bestSeller = bestSeller;

        for (Book b : this.books) {
            ImageView img = new ImageView(b.getImage());
            img.setPreserveRatio(true);
            img.setFitHeight(100);
            VBox info = new VBox(10);
            Label title = new Label(b.getTitle());
            Label price = new Label(b.getPrice() + "원");
            info.getChildren().addAll(title, price);
            HBox element = new HBox(10);
            element.getChildren().addAll(img, info);
            element.setOnMousePressed(u -> {
                updateInfo(b);
            });
            this.Menu.getChildren().add(element);
        }


        // 메인 VBox 설정
        VBox main = new VBox(20);
        main.setAlignment(Pos.TOP_CENTER);

        // 로고 이미지 및 클릭 이벤트
        Image logo = new Image(getClass().getResource("/img/logo.png").toExternalForm());
        ImageView imageViewLogo = new ImageView(logo);
        imageViewLogo.setFitHeight(50); // 로고 크기 조정
        imageViewLogo.setPreserveRatio(true);
        imageViewLogo.setOnMousePressed(e -> {
            Scene_userSelect su = new Scene_userSelect();
            CH_Application.getInstance().stage.setScene(su.getUserSelectScene());
        });

        // 유저 정보와 로그아웃 버튼이 포함된 GridPane
        GridPane userInfoBox = new GridPane();
        userInfoBox.setMaxWidth(200);
        userInfoBox.setStyle("-fx-background-color: #e0f7fa; -fx-border-color: #00796b; -fx-border-radius: 10; -fx-padding: 20;");

        // 유저 정보 라벨 (예시: 이름, 보유 포인트, 등급, ID)
        Label userNameLabel = new Label("이름: " + CH_Application.getInstance().currentUser.getName());
        Label userPointsLabel = new Label("포인트: " + CH_Application.getInstance().currentUser.getPoint());
        Label userGradeLabel = new Label("등급: " + CH_Application.getInstance().currentUser.getGrade());
        Label userIdLabel = new Label("ID: " + CH_Application.getInstance().currentUser.getId());
        userInfoBox.add(userNameLabel, 0, 0);
        userInfoBox.add(userPointsLabel, 0, 1);
        userInfoBox.add(userGradeLabel, 1, 0);
        userInfoBox.add(userIdLabel, 1, 1);

        // 로그아웃 버튼
        Button logoutButton = new Button("로그아웃");
        logoutButton.setStyle("-fx-background-color: #00796b; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-border-radius: 5;");
        logoutButton.setOnAction(e -> {
            // 로그아웃 처리 로직 (예시)
            System.out.println("로그아웃 되었습니다.");
        });

        // 유저 정보와 로그아웃 버튼을 한 곳에 배치
        VBox userInfoVBox = new VBox(10);
        userInfoVBox.getChildren().addAll(userInfoBox, logoutButton);

        Image bookMarketLogo = new Image(getClass().getResource("/img/bookmarketlogo.png").toExternalForm());
        ImageView imageView = new ImageView(bookMarketLogo);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(150);

        // 로고와 유저 정보를 한 줄로 배치
        BorderPane header = new BorderPane();
        header.setPadding(new Insets(10));
        header.getChildren().addAll(imageViewLogo,imageView, userInfoVBox);

        // 베스트셀러 책들
        HBox bestBooks = new HBox(10);
        bestBooks.setAlignment(Pos.CENTER);
        bestBooks.setStyle("-fx-padding: 10;");

        for (int i = 0; i < bestSeller.size(); i++) {
            Book b = bestSeller.get(i);

            // 베스트셀러 1위에 "베스트셀러 1위" 라벨 추가
            HBox element = new HBox(10);
            element.setAlignment(Pos.CENTER_LEFT);
            element.setStyle("-fx-padding: 10; -fx-border-color: #d3d3d3; -fx-border-radius: 5; -fx-background-color: #f9f9f9;");

            // 책 이미지 크기 설정
            ImageView img = new ImageView(b.image);
            img.setFitHeight(90); // 책 이미지 크기 조정
            img.setFitWidth(60);
            img.setPreserveRatio(true);

            // 책 정보 (제목, 저자)
            VBox infoTags = new VBox(5);
            infoTags.getChildren().addAll(new Label(b.title), new Label(b.author));
            infoTags.setAlignment(Pos.CENTER);

            // 책을 베스트셀러처럼 강조
            infoTags.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");

            // 1위 책에 "베스트셀러 1위" 라벨 추가
            if (i == 0) {
                Label rankLabel = new Label("베스트셀러 1위");
                rankLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: gold; -fx-font-size: 14px;");
                infoTags.getChildren().add(0, rankLabel); // 1위 라벨을 맨 앞에 추가
            }

            element.getChildren().addAll(img, infoTags);
            element.setOnMousePressed(e->{
                updateInfo(b);
            });
            bestBooks.getChildren().add(element);
        }
        HBox middle = new HBox(20);
        ChoiceBox<String> category = new ChoiceBox<>();
        category.getItems().addAll("전부", "제목", "저자", "출판사", "카테고리");
        category.setValue("전부");
        TextField inputSearch = new TextField();
        inputSearch.setOnAction(e -> {
            switch (category.getValue()) {
                case "전부":
                    clearMenu();
                    for (Book b : this.books) {
                        ImageView img = new ImageView(b.getImage());
                        img.setPreserveRatio(true);
                        img.setFitHeight(100);
                        VBox info = new VBox(10);
                        Label title = new Label(b.getTitle());
                        Label price = new Label(b.getPrice() + "원");
                        info.getChildren().addAll(title, price);
                        HBox element = new HBox(10);
                        element.getChildren().addAll(img, info);
                        element.setOnMousePressed(u -> {
                            updateInfo(b);
                        });
                        this.Menu.getChildren().add(element);
                    }
                    break;
                case "제목":
                    clearMenu();
                    for (Book b : this.books) {
                        if (b.getTitle().contains(inputSearch.getText())) {
                            ImageView img = new ImageView(b.getImage());
                            img.setPreserveRatio(true);
                            img.setFitHeight(100);
                            VBox info = new VBox(10);
                            Label title = new Label(b.getTitle());
                            Label price = new Label(b.getPrice() + "원");
                            info.getChildren().addAll(title, price);
                            HBox element = new HBox(10);
                            element.getChildren().addAll(img, info);
                            element.setOnMousePressed(u -> {
                                updateInfo(b);
                            });
                            this.Menu.getChildren().add(element);
                        }
                    }
                    break;
                case "저자":
                    clearMenu();
                    for (Book b : this.books) {
                        if (b.getAuthor().contains(inputSearch.getText())) {
                            ImageView img = new ImageView(b.getImage());
                            img.setPreserveRatio(true);
                            img.setFitHeight(100);
                            VBox info = new VBox(10);
                            Label title = new Label(b.getTitle());
                            Label price = new Label(b.getPrice() + "원");
                            info.getChildren().addAll(title, price);
                            HBox element = new HBox(10);
                            element.getChildren().addAll(img, info);
                            element.setOnMousePressed(u -> {
                                updateInfo(b);
                            });
                            this.Menu.getChildren().add(element);
                        }
                    }
                    break;
                case "출판사":
                    clearMenu();
                    for(Book b : this.books){
                        if(b.getPublisher().contains(inputSearch.getText())) {
                            ImageView img = new ImageView(b.getImage());
                            img.setPreserveRatio(true);
                            img.setFitHeight(100);
                            VBox info = new VBox(10);
                            Label title = new Label(b.getTitle());
                            Label price = new Label(b.getPrice() + "원");
                            info.getChildren().addAll(title, price);
                            HBox element = new HBox(10);
                            element.getChildren().addAll(img, info);
                            element.setOnMousePressed(u -> {
                                updateInfo(b);
                            });
                            this.Menu.getChildren().add(element);
                        } }
                    break;
                case "카테고리":
                    clearMenu();
                    for(Book b : this.books){
                        if(b.getCategory().contains(inputSearch.getText())) {
                            ImageView img = new ImageView(b.getImage());
                            img.setPreserveRatio(true);
                            img.setFitHeight(100);
                            VBox info = new VBox(10);
                            Label title = new Label(b.getTitle());
                            Label price = new Label(b.getPrice() + "원");
                            info.getChildren().addAll(title, price);
                            HBox element = new HBox(10);
                            element.getChildren().addAll(img, info);
                            element.setOnMousePressed(u -> {
                                updateInfo(b);
                            });
                            this.Menu.getChildren().add(element);
                        } }
                    break;

            }
        });
        middle.getChildren().addAll(category, inputSearch);


        HBox bottom = new HBox(20);
        bottom.setAlignment(Pos.CENTER);
        this.Menu.setStyle("-fx-boarder-color: black; -fx-border-radius: 5;");
        this.Menu.setMinWidth(300);
        this.Menu.setMinHeight(300);
        this.Bookinfo.setStyle("-fx-boarder-color: black; -fx-border-radius: 5;");
        this.Bookinfo.setMinHeight(300);
        this.Bookinfo.setMinWidth(300);
        ScrollPane menuScroll = new ScrollPane(this.Menu);
        Node content = menuScroll.getContent();
        content.setOnMousePressed(e -> {
            e.consume();  // 이벤트 차단
        });
        menuScroll.setFitToWidth(true);
        menuScroll.setPrefViewportHeight(450);
        menuScroll.setPrefViewportWidth(400);
        menuScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        bottom.getChildren().addAll(menuScroll, this.Bookinfo);


        // 전체 레이아웃에 로고, 유저 정보, 베스트셀러 책 추가
        HBox ButtonBox = new HBox(20);
        ButtonBox.setAlignment(Pos.CENTER);
        Button cart = new Button("장바구니");
        ButtonBox.getChildren().add(cart);


        main.getChildren().addAll(header, bestBooks, middle, bottom, ButtonBox);

        // Scene 설정
        Scene bookMarketScene = new Scene(main, 800, 600);
        CH_Application.getInstance().stage.setScene(bookMarketScene);
    }

    void clearMenu() {
        this.Menu.getChildren().clear();
    }

    void updateInfo(Book book) {
        this.Bookinfo.getChildren().clear();
        VBox bookInfo = new VBox(10);
        Button toCart = new Button("장바구니에 담기");
        toCart.setAlignment(Pos.CENTER);
        toCart.setOnMouseClicked(e->{

        });
        ImageView img = new ImageView(book.getImage());
        img.setPreserveRatio(true);
        img.setFitHeight(250);
        VBox infoTag = new VBox();
        Label bookName = new Label(book.getTitle());
        Label bookAuthor = new Label(book.getAuthor());
        Label bookPublisher = new Label(book.getPublisher());
        Label bookPrice = new Label(book.getPrice() + "원");
        Text info = new Text(book.getDescription());
        info.setWrappingWidth(200);
        bookInfo.getChildren().addAll(toCart,img, infoTag, bookName, bookAuthor, bookPublisher, bookPrice, info);
        this.Bookinfo.getChildren().add(bookInfo);

    }
}