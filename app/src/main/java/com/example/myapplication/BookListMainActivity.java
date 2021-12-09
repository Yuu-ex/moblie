package com.example.myapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager2 viewPagerFragment=findViewById(R.id.viewpager2_content);
        viewPagerFragment.setAdapter(new MyFragmentAdapter(this));

        TabLayout tableLayoutHeader=findViewById(R.id.tabLayout_header);
        TabLayoutMediator tabLayoutMediator=new TabLayoutMediator(tableLayoutHeader, viewPagerFragment, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch(position){
                    case 0:
                        tab.setText("图书");
                        break;
                    case 1:
                        tab.setText("新闻");
                        break;
                    case 2:
                        tab.setText("卖家");
                        break;
                }
            }
        });
        tabLayoutMediator.attach();
    }

    private class MyFragmentAdapter extends FragmentStateAdapter {
        public MyFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch(position){
                case 0:
                    return BookFragment.newInstance();
                case 1:
                    return WebViewFragment.newInstance("test1","test2");
                default:
                    return MapFragment.newInstance("test1","test2");
            }

        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}


