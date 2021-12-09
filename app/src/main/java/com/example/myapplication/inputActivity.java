package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class inputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        Intent intent=getIntent();  //获取当先按键的位置
        int position=intent.getIntExtra("position",0);
        EditText editTextName=findViewById(R.id.edit_text_name);
        String name=intent.getStringExtra("name");
        if(null!=name){
            editTextName.setText(name);
        }

        Button buttonOk=this.findViewById(R.id.enter);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("position",position);  //将位置传回去
                intent.putExtra("name",editTextName.getText().toString());
                setResult(BookFragment.RESULT_CODE_ADD_DATA,intent); //传参数和状态码给主程序
                inputActivity.this.finish();  //应用程序将自己给干掉，避免不能将前面的那个窗口显示出来
            }
        });

        Button buttonCancel=this.findViewById(R.id.cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputActivity.this.finish();  //应用程序将自己给干掉，避免不能将前面的那个窗口显示出来
            }
        });
    }
}
