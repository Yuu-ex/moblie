package com.example.myapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.Data.Book;
import com.example.myapplication.Data.DataBank;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class BookListMainActivity extends AppCompatActivity  {


    public DataBank dataBank;
    private TabLayout mTabLayout;
    private ViewPager2 mViewPage;
    private String[] tabTitles;//tab的标题
    private List<Fragment> mDatas = new ArrayList<>();//ViewPage2的Fragment容器

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
        //找到控件
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPage = findViewById(R.id.view_page);
        //创建适配器
        MyViewPageAdapter mAdapter = new MyViewPageAdapter(this,mDatas);
        mViewPage.setAdapter(mAdapter);

        //TabLayout与ViewPage2联动关键代码
        new TabLayoutMediator(mTabLayout, mViewPage, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabTitles[position]);
            }
        }).attach();

        mViewPage.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });
        //TabLayout的选中改变监听
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


        FloatingActionButton fabAdd=findViewById(R.id.floating_action_button_add);
        fabAdd.setOnClickListener(view -> {
            Intent intent=new Intent(BookListMainActivity.this,EditBookActivity.class);
            intent.putExtra("position",bookList.size());
            launcherAdd.launch(intent);
        });

        RecyclerView mainRecycleView = findViewById(R.id.recycle_view_books);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mainRecycleView.setLayoutManager(layoutManager);

        recyclerViewAdapter = new MyRecyclerViewAdapter(this, bookList);
        mainRecycleView.setAdapter(recyclerViewAdapter);
    }


    public void initData() {
        tabTitles = new String[]{"图书", "新闻","卖家"};
        //EditBookActivity EditBookFragment = new EditBookActivity();
        Fragmentone frgTone = new Fragmentone();
        mDatas.add(frgTone);
        mDatas.add(frgTone);
        mDatas.add(frgTone);

        DataBank dataBank = new DataBank(BookListMainActivity.this);
        bookList = dataBank.loadData();

    }


}

