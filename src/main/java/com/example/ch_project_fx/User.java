package com.example.ch_project_fx;

import javafx.scene.image.Image;

import java.util.ArrayList;


public class User {
    private String id;
    private String pw;
    private String name;
    private String phone;
    private String address;
    private String imgPath;
    private Image img;
    private int point;
    private String grade;
    private int totalPayed;
    private ArrayList<Product> buyList = new ArrayList<>();
    private ArrayList<Product> borrowList = new ArrayList<>();

    public String getId() {
        return id;
    }

    public String getPw() {
        return pw;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getImgPath() {
        return imgPath;
    }

    public Image getImg() {
        return img;
    }

    public int getPoint() {
        return point;
    }

    public String getGrade() {
        return grade;
    }

    public int getTotalPayed() {
        return totalPayed;
    }

    public ArrayList<Product> getBuyList() {
        return buyList;
    }

    public ArrayList<Product> getBorrowList() {
        return borrowList;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setTotalPayed(int totalPayed) {
        this.totalPayed = totalPayed;
    }

    public void setBuyList(ArrayList<Product> buyList) {
        this.buyList = buyList;
    }

    public void setBorrowList(ArrayList<Product> borrowList) {
        this.borrowList = borrowList;
    }
}
