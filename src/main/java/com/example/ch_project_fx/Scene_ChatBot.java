package com.example.ch_project_fx;

import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Scene_ChatBot {

    VBox chatBox;
    User user  = CH_Application.getInstance().getCurrentUser();
    Random random = new Random();
    BookDAO BD = new BookDAO();
    List <Book> books = BD.getAllBooksFromDB();
    List<Book> bestSeller = books.stream().sorted(Comparator.comparingInt(Book::getAmount).reversed()) // amount 기준 내림차순
            .limit(3)
            .toList();
    void runChatBot(){
        Stage chatStage  = new Stage();
        chatStage.setTitle("북봇이에용 0.<");
        chatBox = new VBox(10);
        chatBox.setPadding(new Insets(10));
        chatBox.setMinHeight(550);
        ScrollPane scrollPane = new ScrollPane(chatBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setMinHeight(550);
        TextField inputField = new TextField();
        chatBox.heightProperty().addListener((obs, oldVal, newVal) -> {
            scrollPane.setVvalue(1.0);
        });
        inputField.setOnAction(e -> {
            String userInput = inputField.getText();
            setUserText(user, userInput);
            inputField.clear();
            if(userInput.contains("안녕") ||userInput.contains("반가워") || userInput.contains("하이") || userInput.contains("ㅎㅇ") || userInput.contains("hello")){
                String [] hello = {user.getName()+"님! 저도 반가워요",user.getName()+"님 안녕안녕! > . < ", "안녕하세요"+user.getName()+"님!"};
                String [] niceToMeetYou = {"독서하기 좋은날이네요!","점심 맛있게 드셨나요", "프로젝트 기간동안 화이팅!"};
                setBotText(hello[random.nextInt(hello.length)]+" "+niceToMeetYou[random.nextInt(niceToMeetYou.length)]);



            }
            if(userInput.contains("내정보") ||userInput.contains("유저정보") ){
                    setBotTextRapid("이름 :"+user.getName()+ "ID :"+user.getId());
                if(this.user.getGrade().equals("gold")){
                    setBotText("골드등급 이상이네요! 당신은 독서왕입니다 > < ");
                }

            }if(userInput.contains("점메추") || userInput.contains("뭐먹지") || userInput.contains("뭐먹을까") || userInput.contains("배고파")){
                String [] foodMenu = {"무난하게 제육볶음","내사랑 쌀국수","김밥","한식뷔페","햄버거","국밥쟁이 가서 국밥 거하게 딱 때리기 ","편의점메뉴"};
                setBotText("제 추천메뉴는 "+foodMenu[random.nextInt(foodMenu.length)]+" 입니다! ");

            }if(userInput.contains("베스트") || userInput.contains("잘팔린") || userInput.contains("책추천") || userInput.contains("뭐사지")){
                setBotText("제가 추천드리는 도서는 "+bestSeller.get(0).getTitle()+" 입니다! 최근들어 가장 많이 팔린 상품이네요!" );
            }else if(userInput.contains("그다음") || userInput.contains("두번째") ){
                setBotText("그다음 추천 도서는 "+bestSeller.get(1).getTitle()+" 입니다! 요즘 소비자분들이 많이 찾으세요~!" );
            }else if(userInput.contains("뭐읽지") || userInput.contains("아무거나") || userInput.contains("랜덤") || userInput.contains("흠")){
                setBotText("어떤 책을 읽으실지 고민인가요? 제가 하나 추천해드릴게요! "+books.get(random.nextInt(books.size())).getTitle()+" 한번 읽어보세요!");
            }
            if(userInput.contains("등급")){
                setBotText("회원등급은 총 구매금액이 5만원이상 시 silver ,10만원 이상 일 시 gold, 30만원 이상 일 시 vip 입니다! 현재 회원님은 "+user.getGrade()+"(이) 네요!");
            }
            if(userInput.contains("라이어게임")){
                setBotText("A, K, Q 중 랜덤으로 랭크 하나가 제시됩니다\n" +
                        "                \n" +
                        "승리조건은 자신의 모든 패를 제출. 혹은 상대방의 거짓말을 간파\n" +
                        "                \n" +
                        "같거나 다른 랭크의 카드를 3장까지 동시에 낼수 있습니다.\n" +
                        "제시랭크와 다른 랭크의 카드도 제출 가능하나 상대방이 라이어를 외친다면 패배!\n" +
                        "                \n" +
                        "조커의 경우, 제출할 때 제시 랭크와 같은 랭크로 취급됩니다\n" +
                        "                \n" +
                        "순서는 매 판 랜덤으로 결정됩니다\n" +
                        "                \n" +
                        "라이어 지목은 자신의 다음 사람을 대상으로만 지목할 수 있습니다.");
            }
            if(userInput.contains("블랙잭")){
                setBotText("유저와 컴퓨터와 서로 각자 패에 보유하고있는 카드로\n" +
                        "점수를 비교합니다. 점수가 더 낮은 사람이 패배하게 됩니다.\n" +
                        "Hit은 카드한장을 먹습니다.\n" +
                        "DoubleDown은 카드 한장을 강제로 먹고 판돈을 한번 더 걸고 승리시 4배로 먹습니다.\n" +
                        "Split은 카드 한장을 다음판으로 넘깁니다. 판돈과 배율은 그대로입니다.\n" +
                        "Stand는 행동을 취하지 않고 차례를 넘깁니다.\n"+
                        "21점이 넘으면 버스트로 무조건 패배!");
            }

        });

        VBox layout = new VBox(10, scrollPane, inputField);
        layout.setPadding(new Insets(10));
        layout.setPrefSize(400, 600);
        Scene scene = new Scene(layout);
        chatStage.setScene(scene);
        chatStage.initModality(Modality.NONE);
        chatStage.show();
        setBotTextRapid("안녕하세요 >.< 무엇을 도와드릴까요");




    }



    void setUserText(User user, String input) {
        HBox messageRow = new HBox(10);
        messageRow.setAlignment(Pos.TOP_LEFT);

        ImageView profileImage = new ImageView(user.getImg());
        profileImage.setFitHeight(30);
        profileImage.setFitWidth(30);
        profileImage.setClip(new Circle(15, 15, 15));

        Label nameLabel = new Label(user.getName());
        nameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #444444;");

        Label messageLabel = new Label(input);
        messageLabel.setWrapText(true);
        messageLabel.setMaxWidth(300);
        messageLabel.setStyle("-fx-background-color: #e0e0e0; -fx-padding: 8 12 8 12; -fx-background-radius: 12;");

        VBox userMessageBox = new VBox(nameLabel, messageLabel);

        messageRow.getChildren().addAll(profileImage, userMessageBox);
        chatBox.getChildren().add(messageRow);

        if (chatBox.getChildren().size() > 12) {
            chatBox.getChildren().remove(0);
        }
    }
    void setBotText(String response) {

        Label sayDelay = new Label("북봇이 응답중입니다 > , <");
        sayDelay.setAlignment(Pos.TOP_RIGHT);
        sayDelay.setStyle("-fx-background-color: lightblue; -fx-padding: 10; -fx-background-radius: 10;");
        HBox H = new HBox(sayDelay);
        H.setAlignment(Pos.TOP_RIGHT);
        this.chatBox.getChildren().add(H);
        PauseTransition userInput = new PauseTransition(Duration.seconds(1.9));
        userInput.setOnFinished(e->{
                chatBox.getChildren().remove(H);
        });
        userInput.play();

        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(e -> {
            Label botLabel = new Label(response);
            botLabel.setStyle("-fx-background-color: lightblue; -fx-padding: 10; -fx-background-radius: 10;");
            botLabel.setMaxWidth(300);
            botLabel.setWrapText(true);
            botLabel.setAlignment(Pos.CENTER_RIGHT);
            HBox botBox = new HBox(botLabel);
            botBox.setAlignment(Pos.CENTER_RIGHT);
            chatBox.getChildren().add(botBox);


            if (chatBox.getChildren().size() > 12) {
                chatBox.getChildren().remove(0);
            }
        });
        delay.play();
    }
    void setBotTextRapid(String s){
        Label botLabel = new Label(s);
        botLabel.setStyle("-fx-background-color: lightblue; -fx-padding: 10; -fx-background-radius: 10;");
        botLabel.setMaxWidth(300);
        botLabel.setWrapText(true);
        botLabel.setAlignment(Pos.CENTER_RIGHT);
        HBox botBox = new HBox(botLabel);
        botBox.setAlignment(Pos.CENTER_RIGHT);
        chatBox.getChildren().add(botBox);
        if (chatBox.getChildren().size() > 12) {
            chatBox.getChildren().remove(0);
        }
    }
}

