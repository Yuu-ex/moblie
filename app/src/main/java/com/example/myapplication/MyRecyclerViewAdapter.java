package com.example.myapplication;

import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Data.Book;

import java.util.List;

class MyRecyclerViewAdapter extends RecyclerView.Adapter {
    private final BookListMainActivity bookListMainActivity;
    private List<Book> bookList;

    public MyRecyclerViewAdapter(BookListMainActivity bookListMainActivity, List<Book> bookList) {
        this.bookListMainActivity = bookListMainActivity;
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
                    intent = new Intent(bookListMainActivity, EditBookActivity.class);
                    intent.putExtra("position", position);
                    bookListMainActivity.launcherAdd.launch(intent);

                    break;
                case menu_id_edit:
                    intent = new Intent(bookListMainActivity, EditBookActivity.class);
                    intent.putExtra("position", position);
                    intent.putExtra("Title", bookList.get(position).getTitle());
                    bookListMainActivity.launcherEdit.launch(intent);

                    break;
                case menu_id_delete:
                    AlertDialog.Builder alertDB = new AlertDialog.Builder(bookListMainActivity);
                    alertDB.setPositiveButton("enter", (dialogInterface, i) -> {
                        bookList.remove(position);
                        bookListMainActivity.dataBank.saveData();
                        MyRecyclerViewAdapter.this.notifyItemRemoved(position);
                    });
                    alertDB.setNegativeButton("cancel", (dialogInterface, i) -> {

                    });
                    alertDB.setMessage("Are you sure delete" + bookList.get(position).getTitle() + "？");
                    alertDB.setTitle("hint");

                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + menuItem.getItemId());
            }

            return false;
        }


    }
}
