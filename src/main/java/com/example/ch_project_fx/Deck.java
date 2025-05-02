package com.example.ch_project_fx;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    public ArrayList<Card> cards;
    Card unknown = new Card("?","?",0,"/img/Card-back.png");

    public Deck() {
        cards = new ArrayList<>();
        String[] suit = {"♠", "◆", "♥", "♣"};
        String[] rank = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
        int[] value = {1,2,3,4,5,6,7,8,9,10,10,10,10};
        cards.add(new Card(suit[0],rank[0],value[0],"/img/AS.png"));
        cards.add(new Card(suit[1],rank[0],value[0],"/img/AD.png"));
        cards.add(new Card(suit[2],rank[0],value[0],"/img/AH.png"));
        cards.add(new Card(suit[3],rank[0],value[0],"/img/AC.png"));
        cards.add(new Card(suit[0],rank[1],value[1],"/img/2S.png"));
        cards.add(new Card(suit[1],rank[1],value[1],"/img/2D.png"));
        cards.add(new Card(suit[2],rank[1],value[1],"/img/2H.png"));
        cards.add(new Card(suit[3],rank[1],value[1],"/img/2C.png"));
        cards.add(new Card(suit[0],rank[2],value[2],"/img/3S.png"));
        cards.add(new Card(suit[1],rank[2],value[2],"/img/3D.png"));
        cards.add(new Card(suit[2],rank[2],value[2],"/img/3H.png"));
        cards.add(new Card(suit[3],rank[2],value[2],"/img/3C.png"));
        cards.add(new Card(suit[0],rank[3],value[3],"/img/4S.png"));
        cards.add(new Card(suit[1],rank[3],value[3],"/img/4D.png"));
        cards.add(new Card(suit[2],rank[3],value[3],"/img/4H.png"));
        cards.add(new Card(suit[3],rank[3],value[3],"/img/4C.png"));
        cards.add(new Card(suit[0],rank[4],value[4],"/img/5S.png"));
        cards.add(new Card(suit[1],rank[4],value[4],"/img/5D.png"));
        cards.add(new Card(suit[2],rank[4],value[4],"/img/5H.png"));
        cards.add(new Card(suit[3],rank[4],value[4],"/img/5C.png"));
        cards.add(new Card(suit[0],rank[5],value[5],"/img/6S.png"));
        cards.add(new Card(suit[1],rank[5],value[5],"/img/6D.png"));
        cards.add(new Card(suit[2],rank[5],value[5],"/img/6H.png"));
        cards.add(new Card(suit[3],rank[5],value[5],"/img/6C.png"));
        cards.add(new Card(suit[0],rank[6],value[6],"/img/7S.png"));
        cards.add(new Card(suit[1],rank[6],value[6],"/img/7D.png"));
        cards.add(new Card(suit[2],rank[6],value[6],"/img/7H.png"));
        cards.add(new Card(suit[3],rank[6],value[6],"/img/7C.png"));
        cards.add(new Card(suit[0],rank[7],value[7],"/img/8S.png"));
        cards.add(new Card(suit[1],rank[7],value[7],"/img/8D.png"));
        cards.add(new Card(suit[2],rank[7],value[7],"/img/8H.png"));
        cards.add(new Card(suit[3],rank[7],value[7],"/img/8C.png"));
        cards.add(new Card(suit[0],rank[8],value[8],"/img/9S.png"));
        cards.add(new Card(suit[1],rank[8],value[8],"/img/9D.png"));
        cards.add(new Card(suit[2],rank[8],value[8],"/img/9H.png"));
        cards.add(new Card(suit[3],rank[8],value[8],"/img/9C.png"));
        cards.add(new Card(suit[0],rank[9],value[9],"/img/10S.png"));
        cards.add(new Card(suit[1],rank[9],value[9],"/img/10D.png"));
        cards.add(new Card(suit[2],rank[9],value[9],"/img/10H.png"));
        cards.add(new Card(suit[3],rank[9],value[9],"/img/10C.png"));
        cards.add(new Card(suit[0],rank[10],value[10],"/img/JS.png"));
        cards.add(new Card(suit[1],rank[10],value[10],"/img/JD.png"));
        cards.add(new Card(suit[2],rank[10],value[10],"/img/JH.png"));
        cards.add(new Card(suit[3],rank[10],value[10],"/img/JC.png"));
        cards.add(new Card(suit[0],rank[11],value[11],"/img/QS.png"));
        cards.add(new Card(suit[1],rank[11],value[11],"/img/QD.png"));
        cards.add(new Card(suit[2],rank[11],value[11],"/img/QH.png"));
        cards.add(new Card(suit[3],rank[11],value[11],"/img/QC.png"));
        cards.add(new Card(suit[0],rank[12],value[12],"/img/KS.png"));
        cards.add(new Card(suit[1],rank[12],value[12],"/img/KD.png"));
        cards.add(new Card(suit[2],rank[12],value[12],"/img/KH.png"));
        cards.add(new Card(suit[3],rank[12],value[12],"/img/KC.png"));
        Collections.shuffle(cards);
    }
}
