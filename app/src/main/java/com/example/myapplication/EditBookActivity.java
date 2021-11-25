package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditBookActivity extends AppCompatActivity {

    public static final int RESULT_CODE = 901;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        Intent intent=getIntent();
        int positon=intent.getIntExtra("position",0);

        EditText editText=findViewById(R.id.edit_text_name);
        String Title=intent.getStringExtra("Title");
        if(null!=Title){//判断，如果Title为空
            editText.setText(Title);
        }
        Button enter=this.findViewById(R.id.enter);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.putExtra("positon",positon);
                intent.putExtra("Title",editText.getText().toString());
                setResult(RESULT_CODE,intent);
                EditBookActivity.this.finish();
            }
        });


    }
}