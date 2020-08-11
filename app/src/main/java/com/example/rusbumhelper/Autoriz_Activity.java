package com.example.rusbumhelper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("Registered")
public class Autoriz_Activity extends AppCompatActivity implements View.OnClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autoriz_layout);

        Button btnGoToSignInt = findViewById(R.id.signIn_autor);
        Button btnGoToRegistrationt = findViewById(R.id.registration_autor);
        Button btn_guest = findViewById(R.id.btn_guest);
        ImageButton btnBack = findViewById(R.id.button_back_autoriz_main);

        btnGoToRegistrationt.setOnClickListener(this);
        btnGoToSignInt.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btn_guest.setOnClickListener(this);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void onClick(View v)
    {
        try {
            switch(v.getId())
            {
                case R.id.signIn_autor:
                {
                    Intent intent = new Intent(Autoriz_Activity.this,SignIn.class);
                    startActivity(intent);finish();
                    break;
                }

                case R.id.registration_autor:
                {
                    Intent intent = new Intent(Autoriz_Activity.this,Registration.class);
                    startActivity(intent);finish();
                    break;
                }
                case R.id.button_back_autoriz_main:
                {
                    onBackPressed();
                    break;
                }
                case R.id.btn_guest:
                {
                    Intent intent = new Intent(Autoriz_Activity.this,Menu_Exemption_Activity.class);
                    intent.putExtra("mail",DatabaseHelper.GUEST);
                    startActivity(intent);finish();
                    break;
                }
            }

        } catch (Exception ignored){
        }
    }

    @Override
    public void onBackPressed(){
        try {
            Intent intent = new Intent(Autoriz_Activity.this,MainActivity.class);
            startActivity(intent);finish();
        } catch (Exception ignored){
        }
    }
}