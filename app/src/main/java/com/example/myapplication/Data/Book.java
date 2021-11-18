package com.example.myapplication.Data;
import java.io.Serializable;
public class Book implements Serializable{
    private String Title;       //书名
    private int CoverResourceId;    //封面

    public Book(String Title,int CoverResourceId){
        this.Title=Title;
        this.CoverResourceId=CoverResourceId;
    }


    public String getTitle() {
        return Title;
    }

    public int getCoverResourceId() {
        return CoverResourceId;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }
    public void setCoverResourceId(int CoverResourceId) {
        this.CoverResourceId = CoverResourceId;
    }
}
