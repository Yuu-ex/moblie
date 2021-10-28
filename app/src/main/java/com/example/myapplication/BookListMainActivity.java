package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookListMainActivity extends AppCompatActivity {

    private List<Book> bookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();

        RecyclerView mainRecycleView = findViewById(R.id.recycle_view_books);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mainRecycleView.setLayoutManager(layoutManager);

        mainRecycleView.setAdapter(new MyRecyclerViewAdapter(bookList));
    }


    public void initData() {
        bookList = new ArrayList<Book>();
        bookList.add(new Book("软件项目管理案例教程（第4版）", R.drawable.book_2));
        bookList.add(new Book("创新工程实践", R.drawable.book_no_name));
        bookList.add(new Book("信息安全数学基础（第2版）", R.drawable.book_1));

    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter {
        private List<Book> bookList;

        public MyRecyclerViewAdapter(List<Book> bookList) {
            this.bookList = bookList;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.booklist_holder, parent, false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder Holder, int position) {
            MyViewHolder holder = (MyViewHolder) Holder;

            holder.getImageView().setImageResource(bookList.get(position).getCoverResourceId());
            holder.getTextViewName().setText(bookList.get(position).getTitle());

        }

        @Override
        public int getItemCount() {
            return 0;
        }


    }


    private class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView textViewName;

        public MyViewHolder(View view) {
            super(view);

            this.imageView = view.findViewById(R.id.image_view_book_cover);
            this.textViewName = view.findViewById(R.id.text_view_book_title);

        }

        public ImageView getImageView() {
            return imageView;
        }

        public TextView getTextViewName() {
            return textViewName;
        }
    }

}