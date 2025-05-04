package com.example.ch_project_fx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Random;

public class Scene_LiarsGame {
    User user = CH_Application.getInstance().getCurrentUser();
    LiarsPlayer player1;
    LiarsPlayer player2;
    Liars first;
    Liars second;
    Liars third;
    Liars winner = null;
    Liars loser = null;
    int stake;
    Card fieldCard;
    String mainRank;
    Random random = new Random();
    List<Card> gameDeck;
    List<Card> LastPlayerCard;
    HBox userDeck = new HBox(10);
    HBox player1Deck = new HBox(10);
    HBox player2Deck = new HBox(10);
    VBox textArea = new VBox(10);
    int BetAmount;

    void startBet() {
        if (this.user.getPoint() == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(null);
            alert.setContentText("포인트가 없어 입장이 불가능합니다 잘가요 ㅠㅠ");
            alert.showAndWait();
            Scene_Minigame SM = new Scene_Minigame();
            SM.SelectGame();
        }

        Stage smallStage = new Stage();
        VBox small = new VBox(20);

        VBox vipBox = new VBox(20);
        VBox vipSet = new VBox(20);

        Label inputBet = new Label("배팅할 금액을 입력하세요");

        Label userMoneyInfo = new Label(this.user.getName() + " 보유 money: " + this.user.getPoint());

        TextField textFieldBet = new TextField();

        textFieldBet.setAlignment(Pos.CENTER);

        textFieldBet.setOnAction(a -> {
            if (this.user.getPoint() < Integer.parseInt(textFieldBet.getText())) {
                vipSet.getChildren().clear();
                Label sayNo = new Label("배팅 포인트가 부족합니다");
                vipSet.getChildren().add(sayNo);
            } else {
                vipSet.getChildren().clear();
                this.BetAmount = Integer.parseInt(textFieldBet.getText());
                this.user.setPoint(this.user.getPoint() - this.BetAmount);
                UserDAO UD = new UserDAO();
                UD.subtractPointFromUser(this.user.getId(), this.BetAmount);
                smallStage.close();
                playLiarsGame();
            }
        });

        vipBox.getChildren().addAll(inputBet, userMoneyInfo, textFieldBet);

        small.getChildren().addAll(vipBox, vipSet);

        Scene smallScene = new Scene(small, 400, 300);
        smallStage.setScene(smallScene);
        smallStage.show();
    }

    //+++++++++++++++++++++++++++++++++++++++++++배팅 완료 +++++++++++++++++++++++++++++++++++++++++++++++++++++
    void playLiarsGame() {
        Deck LiarsDeck = new Deck();
        this.gameDeck = LiarsDeck.LiarsDecK();

        LiarsPlayer player1 = new LiarsPlayer();
        LiarsPlayer player2 = new LiarsPlayer();

        while (true) {
            if (player2.name.equals(player1.name))
                player2 = new LiarsPlayer();
            else break;
        }
        this.player1 = player1;
        this.player1.img = new Image(getClass().getResource("/img/liarplayer1.png").toExternalForm());

        this.player2 = player2;
        this.player2.img = new Image(getClass().getResource("/img/liarplayer2.png").toExternalForm());

        for (int i = 0; i < 5; i++) {
            player1.PlayerDeck.add(this.gameDeck.remove(0));
            player2.PlayerDeck.add(this.gameDeck.remove(0));
            user.PlayerDeck.add(this.gameDeck.remove(0));
        }


        this.fieldCard = gameDeck.remove(0);
        if (this.fieldCard.getRank().equals("joker")) {
            this.fieldCard = gameDeck.remove(0);
        }
        if (this.fieldCard.getRank().equals("joker")) {
            this.fieldCard = gameDeck.remove(0);
        }
        this.mainRank = fieldCard.getRank();
        player1.mainRank = mainRank;
        player2.mainRank = mainRank;
        user.mainRank = mainRank;
        updatePlayersDeck();

        RollWhoWillBeFirst();
//+++++++++++++++++++++++++++++++++++++++게임준비++++++++++++++++++++++++++++++++++++++++++++++++++
        BorderPane main = new BorderPane();

        HBox top = new HBox(50);

        HBox playersBox1 = new HBox(20);
        HBox playersBox2 = new HBox(20);
        VBox player1Info = new VBox(10);
        VBox player2Info = new VBox(10);

        ImageView player1img = new ImageView(this.player1.img);
        player1img.setFitHeight(120);
        player1img.setPreserveRatio(true);
        Label player1name = new Label(this.player1.getName());
        player1Info.getChildren().addAll(player1img, player1name);

        playersBox1.getChildren().addAll(player1Info, this.player1Deck);

        ImageView player2img = new ImageView(this.player2.img);
        player2img.setFitHeight(120);
        player2img.setPreserveRatio(true);
        Label player2name = new Label(this.player2.getName());
        player2Info.getChildren().addAll(player2img, player2name);

        playersBox2.getChildren().addAll(player2Info, this.player2Deck);

        playersBox1.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-border-radius: 20px;;");
        playersBox2.setStyle("-fx-border-color: blue; -fx-border-width: 2px; -fx-border-radius: 20px;");

        top.getChildren().addAll(playersBox1, playersBox2);
        main.setTop(top);
//++++++++++++++++++++++++++++ 상단 상대플레이어 덱 정보 박스 ++++++++++++++++++++++++++++++++++++++++

        HBox middle = new HBox(20);
        ImageView MainRankCard = new ImageView(this.fieldCard.img);
        MainRankCard.setFitHeight(200);
        MainRankCard.setPreserveRatio(true);
        middle.setAlignment(Pos.CENTER);
        middle.getChildren().addAll(this.textArea,MainRankCard);


        main.setCenter(middle);
//+++++++++++++++++++++++++++중단 딜러 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        HBox bottom = new HBox(20);
        VBox userInfoBox =  new VBox(10);
        Label userName = new Label(this.user.getName());
        ImageView userImg = new ImageView(this.user.getImg());
        userImg.setFitHeight(200);
        userImg.setPreserveRatio(true);
        userInfoBox.getChildren().addAll(userName,userImg);

        bottom.getChildren().addAll(userInfoBox,this.userDeck);


        main.setBottom(bottom);
//++++++++++++++++++++++++++++하단 유저정보++++++++++++++++++++++++++++++++++++++++++++++++
        Scene LiarsGameScene = new Scene(main);
        CH_Application.getInstance().stage.setScene(LiarsGameScene);
    }

    void RollWhoWillBeFirst() {
        int roll = random.nextInt(3);
        switch (roll) {
            case 0:
                this.first = this.player1;
                this.second = this.player2;
                this.third = this.user;
                break;
            case 1:
                this.first = this.player2;
                this.second = this.user;
                this.third = this.player1;
                break;
            case 2:
                this.first = this.user;
                this.second = this.player1;
                this.third = this.player2;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("게임 시작 안내");
        alert.setHeaderText("순서: " + this.first.getName() + " > " + this.second.getName() + " > " + this.third.getName());
        alert.setContentText("제시 랭크: [" + this.mainRank + "] 입니다");
        alert.showAndWait();
        setText("순서: " + this.first.getName() + " > " + this.second.getName() + " > " + this.third.getName(),"제시 랭크: [" + this.mainRank + "] 입니다",this.first+"부터 시작!");
    }

    void updatePlayersDeck() {
        this.player1Deck.getChildren().clear();
        this.player2Deck.getChildren().clear();
        this.userDeck.getChildren().clear();
        for (Card c : this.player1.PlayerDeck) {
            c.img = new Image(getClass().getResource("/img/Card-back.png").toExternalForm());
            ImageView img = new ImageView(c.img);
            img.setFitHeight(50);
            img.setPreserveRatio(true);
            this.player1Deck.getChildren().add(img);
        }
        for (Card c : this.player2.PlayerDeck) {
            c.img = new Image(getClass().getResource("/img/Card-back.png").toExternalForm());
            ImageView img = new ImageView(c.img);
            img.setFitHeight(50);
            img.setPreserveRatio(true);
            this.player2Deck.getChildren().add(img);
        }
        for (Card c : this.user.PlayerDeck) {
            ImageView img = new ImageView(c.img);
            img.setPreserveRatio(true);
            img.setFitHeight(160);
            this.userDeck.getChildren().add(img);
        }

    }
    void setText(String text,String text1,String text2){
        this.textArea.getChildren().clear();
        Label input1 = new Label(text);
        Label input2 = new Label(text1);
        Label input3 = new Label(text2);
        this.textArea.getChildren().addAll(input1,input2,input3);
    }
}
