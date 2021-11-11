package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BookListMainActivity extends AppCompatActivity implements MenuItem.OnMenuItemClickListener {

    public static final int RESULT_CODE = 901;
    public static final int REQUEST_CODE = 123;
    public static final int REQUEST_CODE_EDIT = REQUEST_CODE+1;
    private List<Book> bookList;
    private MyRecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_CODE){
            if (resultCode==RESULT_CODE){
                String Title= data.getStringExtra("Title");
                int positon=data.getIntExtra("positon",bookList.size());
                bookList.add(positon,new Book(Title,R.drawable.book_no_name));
                recyclerViewAdapter.notifyItemInserted(bookList.size());
            }
        }


        if(requestCode==REQUEST_CODE_EDIT){
            if (resultCode==RESULT_CODE){
                String Title= data.getStringExtra("Title");
                int positon=data.getIntExtra("positon",bookList.size());
                bookList.get(positon).setName(Title);
                recyclerViewAdapter.notifyItemInserted(bookList.size());
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();

        RecyclerView mainRecycleView = findViewById(R.id.recycle_view_books);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mainRecycleView.setLayoutManager(layoutManager);

        recyclerViewAdapter = new MyRecyclerViewAdapter(bookList);
        mainRecycleView.setAdapter(recyclerViewAdapter);
    }


    public void initData() {
        bookList = new ArrayList<Book>();
        bookList.add(new Book("软件项目管理案例教程（第4版）", R.drawable.book_2));
        bookList.add(new Book("创新工程实践", R.drawable.book_no_name));
        bookList.add(new Book("信息安全数学基础（第2版）", R.drawable.book_1));

    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

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
            return bookList.size();
        }





    private class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public static final int menu_id_add = 1;
        public static final int menu_id_edit = 2;
        public static final int menu_id_delete = 3;
        private final ImageView imageView;
        private final TextView textViewName;

        public MyViewHolder(View itemView) {
            super(itemView);

            this.imageView = itemView.findViewById(R.id.image_view_book_cover);
            this.textViewName = itemView.findViewById(R.id.text_view_book_title);

            itemView.setOnCreateContextMenuListener(this);

        }

        public ImageView getImageView() {
            return imageView;
        }

        public TextView getTextViewName() {
            return textViewName;
        }


        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

            MenuItem menuItemAdd = contextMenu.add(Menu.NONE, menu_id_add, menu_id_add, "Add");
            MenuItem menuItemEdit = contextMenu.add(Menu.NONE, menu_id_edit, menu_id_edit, "Edit");
            MenuItem menuItemDelete = contextMenu.add(Menu.NONE, menu_id_delete, menu_id_delete, "Delete");

            menuItemAdd.setOnMenuItemClickListener(this);
            menuItemEdit.setOnMenuItemClickListener(this);
            menuItemDelete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            int position = getAdapterPosition();
            Intent intent;
            switch (menuItem.getItemId()) {
                case menu_id_add:
                    intent=new Intent(BookListMainActivity.this,EditBookActivity.class);
                    intent.putExtra("position",position);
                    BookListMainActivity.this.startActivityForResult(intent, REQUEST_CODE);

                    break;
                case menu_id_edit:
                    intent=new Intent(BookListMainActivity.this,EditBookActivity.class);
                    intent.putExtra("Title",bookList.get(position).getTitle());
                    BookListMainActivity.this.startActivityForResult(intent, REQUEST_CODE_EDIT);
                    bookList.get(position).setName("测试修改");
                    MyRecyclerViewAdapter.this.notifyItemChanged(position);

                    break;
                case menu_id_delete:
                    bookList.remove(position);
                    MyRecyclerViewAdapter.this.notifyItemRemoved(position);

                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + menuItem.getItemId());
            }
            Toast.makeText(BookListMainActivity.this, "点击了" + menuItem.getItemId(), Toast.LENGTH_LONG).show();
            return false;
        }


    }
    }
}

