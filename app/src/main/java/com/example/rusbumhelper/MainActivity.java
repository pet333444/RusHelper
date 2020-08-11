package com.example.rusbumhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    private long backPressedTime;
    private Toast backToast;//сообщение для выхода
    DatabaseHelper sqlHelper;

    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonStart = findViewById(R.id.button_start);

        buttonStart.setOnClickListener(this);

        sqlHelper = new DatabaseHelper(getApplicationContext());
        // создаем базу данных
        sqlHelper.create_db();

        backToast=Toast.makeText(getBaseContext(),"Нажмите ещё раз, чтобы выйти",Toast.LENGTH_SHORT);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void onClick(View v)
    {
        try {
            if (v.getId() == R.id.button_start) {
                Intent intent = new Intent(MainActivity.this, Autoriz_Activity.class);
                startActivity(intent);
                finish();
            }
        } catch (Exception ignored){
        }
    }

    @Override
    public void onBackPressed() {

        if(backPressedTime+2000>System.currentTimeMillis())
        {
            backToast.cancel();
            super.onBackPressed();
            return;
        }else {
            backToast.show();
        }
        backPressedTime=System.currentTimeMillis();
    }
}