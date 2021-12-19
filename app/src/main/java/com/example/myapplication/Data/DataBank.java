package com.example.myapplication.Data;

import android.content.Context;
import com.example.myapplication.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataBank {
    public static final String DATA_FILE_NAME = "data";
    private final Context context;
    List<Book> bookList;

    public DataBank(Context context) {
        this.context = context;
    }


    public List<Book> loadData() {    //加载报春的文件/数据
        bookList=new ArrayList<>();
        bookList.add(new Book("软件项目管理案例教程（第4版）",R.drawable.book_2));
        bookList.add(new Book("创新工程实践", R.drawable.book_no_name));
        bookList.add(new Book("信息安全数学基础（第二版）" , R.drawable.book_1));
        try{
            ObjectInputStream objectInputStream=new ObjectInputStream(context.openFileInput(DATA_FILE_NAME));
            bookList=(ArrayList<Book>)objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    public void saveData() {   //保存当前的数据，下一次打开显示前面修改过的数据
        ObjectOutputStream objectOutputStream=null;
        try{
            objectOutputStream=new ObjectOutputStream(context.openFileOutput(DATA_FILE_NAME, Context.MODE_PRIVATE));
            objectOutputStream.writeObject(bookList);
        } catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if (objectOutputStream!=null){
                    objectOutputStream.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

}
