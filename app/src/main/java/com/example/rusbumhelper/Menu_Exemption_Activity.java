package com.example.rusbumhelper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

@SuppressLint("Registered")
public class Menu_Exemption_Activity extends AppCompatActivity implements View.OnClickListener
{
    String mail;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_change);

        mail = Objects.requireNonNull(getIntent().getExtras()).getString("mail");

        if(mail.equals("guest@gmail.com"))
        {
            findViewById(R.id.setting_facilities).setVisibility(View.GONE);
        }
        else
        {
            findViewById(R.id.setting_facilities).setOnClickListener(this);
        }

        findViewById(R.id.education_facilities).setOnClickListener(this);
        findViewById(R.id.home_facilities).setOnClickListener(this);
        findViewById(R.id.realty_facilities).setOnClickListener(this);
        findViewById(R.id.tax_deduction).setOnClickListener(this);
        findViewById(R.id.other_facilities).setOnClickListener(this);
        findViewById(R.id.button_back1).setOnClickListener(this);

        intent = new Intent(Menu_Exemption_Activity.this,Selection_name_activity.class);
        intent.putExtra("mail",mail);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void onClick(View v)
    {
        try {
            switch(v.getId())
            {
                case R.id.education_facilities:
                {
                    intent.putExtra("ID",DatabaseHelper.ID_EDUCATION);
                    startActivity(intent);finish();
                    break;
                }
                case R.id.home_facilities:
                {
                    intent.putExtra("ID",DatabaseHelper.ID_HOME);
                    startActivity(intent);finish();
                    break;
                }
                case R.id.realty_facilities:
                {
                    intent.putExtra("ID",DatabaseHelper.ID_REALTY);
                    startActivity(intent);finish();
                    break;
                }
                case R.id.tax_deduction:
                {
                    intent.putExtra("ID",DatabaseHelper.ID_TAX);
                    startActivity(intent);finish();
                    break;
                }
                case R.id.other_facilities:
                {
                    intent.putExtra("ID",DatabaseHelper.ID_OTHER);
                    startActivity(intent);finish();
                    break;
                }
                case R.id.button_back1:
                {
                    onBackPressed();
                    break;
                }
                case R.id.setting_facilities:
                {
                    CustomDialogFragment dialog = new CustomDialogFragment();
                    Bundle args = new Bundle();
                    args.putString("mail",mail);
                    dialog.setArguments(args);
                    dialog.show(getSupportFragmentManager(), "Reg");
                    break;
                }
            }

        } catch (Exception ignored)
        {
        }
    }

    @Override
    public void onBackPressed()
    {
        try
        {
            Intent intent = new Intent(Menu_Exemption_Activity.this,Autoriz_Activity.class);
            startActivity(intent);finish();

        } catch (Exception ignored)
        {
        }
    }
}