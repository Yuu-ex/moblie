package com.example.myapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Data.Book;
import com.example.myapplication.Data.DataBank;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class BookListMainActivity extends AppCompatActivity  {
    private DataBank dataBank;
    public static final int RESULT_CODE = 901;
    public static final int REQUEST_CODE = 123;
    public static final int REQUEST_CODE_EDIT = REQUEST_CODE+1;
    private List<Book> bookList;
    private MyRecyclerViewAdapter recyclerViewAdapter;

    ActivityResultLauncher<Intent> launcherAdd=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),new ActivityResultCallback<ActivityResult>(){

        @Override
        public void onActivityResult(ActivityResult result) {
            Intent data = result.getData();
            int resultCode = result.getResultCode();
            if(resultCode== RESULT_CODE) {
                if (null == data) return;
                String Title = data.getStringExtra("Title");
                int position = data.getIntExtra("position", bookList.size());
                bookList.add(position, new Book(Title, R.drawable.book_no_name));
                dataBank.saveData();
                recyclerViewAdapter.notifyItemInserted(position);
            }
        }
    });
    
    ActivityResultLauncher<Intent> launcherEdit= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            Intent data = result.getData();
            int resultCode = result.getResultCode();
            if(resultCode== RESULT_CODE) {
                if (null == data) return;
                String Title = data.getStringExtra("Title");
                int position = data.getIntExtra("position", bookList.size());
                bookList.get(position).setTitle(Title);
                dataBank.saveData();
                recyclerViewAdapter.notifyItemChanged(position);
            }
        }
    });

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
                bookList.get(positon).setTitle(Title);
                recyclerViewAdapter.notifyItemInserted(bookList.size());
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();

        FloatingActionButton fabAdd=findViewById(R.id.floating_action_button_add);
        fabAdd.setOnClickListener(view -> {
            Intent intent=new Intent(BookListMainActivity.this,EditBookActivity.class);
            intent.putExtra("position",bookList.size());
            launcherAdd.launch(intent);
        });

        RecyclerView mainRecycleView = findViewById(R.id.recycle_view_books);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mainRecycleView.setLayoutManager(layoutManager);

        recyclerViewAdapter = new MyRecyclerViewAdapter(bookList);
        mainRecycleView.setAdapter(recyclerViewAdapter);
    }


    public void initData() {
        DataBank dataBank = new DataBank(BookListMainActivity.this);
        bookList = dataBank.loadData();

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
                    launcherAdd.launch(intent);

                    break;
                case menu_id_edit:
                    intent=new Intent(BookListMainActivity.this,EditBookActivity.class);
                    intent.putExtra("position",position);
                    intent.putExtra("Title",bookList.get(position).getTitle());
                    launcherEdit.launch(intent);

                    break;
                case menu_id_delete:
                    AlertDialog.Builder alertDB = new AlertDialog.Builder(BookListMainActivity.this);
                    alertDB.setPositiveButton("enter", (dialogInterface, i) -> {
                        bookList.remove(position);
                        dataBank.saveData();
                        MyRecyclerViewAdapter.this.notifyItemRemoved(position);
                    });
                    alertDB.setNegativeButton("cancel", (dialogInterface, i) -> {

                    });
                    alertDB.setMessage("Are you sure delete" +bookList.get(position).getTitle()+"？");
                    alertDB.setTitle("hint");

                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + menuItem.getItemId());
            }

            return false;
        }


    }
    }
}

