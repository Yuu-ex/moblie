package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
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

            MenuItem menuItemAdd = contextMenu.add(Menu.NONE, 1, 1, "Add");
            MenuItem menuItemEdit = contextMenu.add(Menu.NONE, 2, 2, "Edit");
            MenuItem menuItemDelete = contextMenu.add(Menu.NONE, 3, 3, "Delete");

            menuItemAdd.setOnMenuItemClickListener(this);
            menuItemEdit.setOnMenuItemClickListener(this);
            menuItemDelete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            int position = getAdapterPosition();
            switch (menuItem.getItemId()) {
                case 1:
                    View dialagueView= LayoutInflater.from(BookListMainActivity.this).inflate(R.layout.dialogue_input_item,null);
                    AlertDialog.Builder alertDialogBuiler = new AlertDialog.Builder(BookListMainActivity.this);
                    alertDialogBuiler.setView(dialagueView);

                    alertDialogBuiler.setPositiveButton("确定",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            EditText editName=dialagueView.findViewById(R.id.edit_text_name);
                            bookList.add(position,new Book(editName.getText().toString(),R.drawable.book_1));
                            MyRecyclerViewAdapter.this.notifyItemInserted(position);
                        }
                    });
                    alertDialogBuiler.setCancelable(false).setNegativeButton ("取消",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    alertDialogBuiler.create().show();;

                    break;
                case 2:
                    bookList.get(position).setName("测试修改");
                    MyRecyclerViewAdapter.this.notifyItemChanged(position);
                    break;

                case 3:
                    bookList.remove(position);
                    MyRecyclerViewAdapter.this.notifyItemRemoved(position);
                    break;
            }
            Toast.makeText(BookListMainActivity.this, "点击了" + menuItem.getItemId(), Toast.LENGTH_LONG).show();
            return false;
        }


    }
    }
}

