package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Data.Book;
import com.example.myapplication.Data.DataBank;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookFragment extends Fragment {



    public BookFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static BookFragment newInstance() {
        BookFragment fragment = new BookFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_book, container, false);
        initData();

        FloatingActionButton fabAdd = rootView.findViewById(R.id.floating_action_button_add);
        fabAdd.setOnClickListener(view -> {
            Intent intent = new Intent(this.getContext(), inputActivity.class);
            intent.putExtra("position", books.size());
            launcherAdd.launch(intent);
        });

        RecyclerView mainRecycleView = rootView.findViewById(R.id.recycle_view_books);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        mainRecycleView.setLayoutManager(layoutManager);
        recycleViewAdapter = new BookFragment.MyRecyclerViewAdapter(books);
        mainRecycleView.setAdapter(recycleViewAdapter);
        return rootView;
    }
    public static final int RESULT_CODE_ADD_DATA = 996;
    private List<Book> books;
    private BookFragment.MyRecyclerViewAdapter recycleViewAdapter;
    private DataBank dataBank;

    ActivityResultLauncher<Intent> launcherAdd = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        public void onActivityResult(ActivityResult result){
            Intent data = result.getData();
            int resultCode = result.getResultCode();
            if (resultCode == RESULT_CODE_ADD_DATA) {
                if(null==data) return;
                String name = data.getStringExtra("name");  //String类型的，为空系统自己为我们设置为NULL
                int position = data.getIntExtra("position", books.size());  //将传过去的位置传回来
                books.add(position, new Book(name, R.drawable.book_no_name));
                dataBank.saveData();
                recycleViewAdapter.notifyItemInserted(position);
            }
        }
    });
    ActivityResultLauncher<Intent> launcherEdit = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        public void onActivityResult(ActivityResult result){
            Intent data = result.getData();
            int resultCode = result.getResultCode();
            if (resultCode == RESULT_CODE_ADD_DATA) {
                if(null==data) return;
                String name = data.getStringExtra("name");  //String类型的，为空系统自己为我们设置为NULL
                int position = data.getIntExtra("position", books.size());  //将传过去的位置传回来
                books.get(position).setTitle(name);
                dataBank.saveData();
                recycleViewAdapter.notifyItemChanged(position);
            }
        }
    });

    public void initData() {   //直接创建一个文件夹用于存储数据
        dataBank= new DataBank(this.getContext());
        books=dataBank.loadData();
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter {
        private List<Book> books;

        public MyRecyclerViewAdapter(List<Book> books) {
            this.books = books;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.booklist_holder, parent, false);

            return new BookFragment.MyRecyclerViewAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            BookFragment.MyRecyclerViewAdapter.MyViewHolder Holder = (BookFragment.MyRecyclerViewAdapter.MyViewHolder) holder;

            Holder.getImageViewBook().setImageResource(books.get(position).getCoverResourceId());
            Holder.getTextViewLookTitle().setText(books.get(position).getTitle() + "");
        }

        @Override
        public int getItemCount() {
            return books.size();
        }


        private class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
            public static final int CONTEXT_MENU_ID_ADD = 1;
            public static final int CONTEXT_MENU_ID_UPDATE =CONTEXT_MENU_ID_ADD+1;
            public static final int CONTEXT_MENU_ID_DELETE = CONTEXT_MENU_ID_ADD+2;
            private final ImageView imageviewbook;
            private final TextView textviewlooktitle;

            public MyViewHolder(View view) {
                super(view);
                this.imageviewbook = view.findViewById(R.id.image_view_book_cover);
                this.textviewlooktitle = view.findViewById(R.id.text_view_book_title);

                view.setOnCreateContextMenuListener(this);
            }

            public ImageView getImageViewBook() {
                return imageviewbook;
            }

            public TextView getTextViewLookTitle() {
                return textviewlooktitle;
            }

            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                int position = getAdapterPosition();
                MenuItem menuItemAdd = menu.add(Menu.NONE,  CONTEXT_MENU_ID_ADD,  CONTEXT_MENU_ID_ADD, "Add" + position);
                MenuItem menuItemEdit = menu.add(Menu.NONE, CONTEXT_MENU_ID_UPDATE, CONTEXT_MENU_ID_UPDATE, "Edit" + position);
                MenuItem menuItemDelete = menu.add(Menu.NONE, CONTEXT_MENU_ID_DELETE, CONTEXT_MENU_ID_DELETE, "Delete");

                menuItemAdd.setOnMenuItemClickListener(this);
                menuItemEdit.setOnMenuItemClickListener(this);
                menuItemDelete.setOnMenuItemClickListener(this);
            }

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int position = getAdapterPosition();
                switch (item.getItemId()) {
                    case CONTEXT_MENU_ID_ADD:
                        Intent intent=new Intent( BookFragment.this.getContext(),inputActivity.class);  //直接在一个新的窗口里面添加，添加按钮的界面
                        intent.putExtra("position",position);
                        launcherAdd.launch(intent);
                        //  BookFragment.this.getContext.startActivityForResult(intent, REQUEST_CODE_ADD_ID);
                        break;
//                        books.add(new Book("软件项目管理案例教程（第4版）", R.drawable.book_2));
//                        MyRecyclerViewAdapter.this.notifyItemInserted(position+1);
                    case CONTEXT_MENU_ID_UPDATE:
                        intent=new Intent( BookFragment.this.getContext(),inputActivity.class);  //直接在一个新的窗口里面添加，添加按钮的界面
                        intent.putExtra("position",position);
                        intent.putExtra("name",books.get(position).getTitle());
                        launcherEdit.launch(intent);
                        break;
                    case CONTEXT_MENU_ID_DELETE:
                        books.remove(position);
                        dataBank.saveData();
                        BookFragment.MyRecyclerViewAdapter.this.notifyItemRemoved(position);
                        break;
                }
                Toast.makeText( BookFragment.this.getContext(), "点击了" + item.getItemId(), Toast.LENGTH_LONG).show();
                return false;
            }
        }
    }
    }
