package com.example.myapplication;

public class Book {
    private String Title;       //书名
    private int CoverResourceId;    //封面

    public Book(String name,int pictureId){
        this.Title=Title;
        this.CoverResourceId=CoverResourceId;
    }


    public String getTitle() {
        return Title;
    }

    public int getCoverResourceId() {
        return CoverResourceId;
    }

    public void setName(String name) {
        this.Title = name;
    }
}
