package com.example.rusbumhelper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

@SuppressLint("Registered")
public class Text_Facil_View_Activity extends AppCompatActivity implements View.OnClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_view_layout);

        TextView tv = findViewById(R.id.textView_view);

        tv.setText(Objects.requireNonNull(getIntent().getExtras()).getString("Facil"));

        findViewById(R.id.button_back__view).setOnClickListener(this);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void onClick(View v)
    {
        onBackPressed();
    }
    @Override
    public void onBackPressed()
    {
        try
        {
            Intent intent = new Intent(Text_Facil_View_Activity.this,Selection_name_activity.class);
            intent.putExtra("mail",Objects.requireNonNull(getIntent().getExtras()).getString("mail"));
            intent.putExtra("ID",Objects.requireNonNull(getIntent().getExtras()).getInt("ID"));
            startActivity(intent);finish();

        } catch (Exception ignored)
        {
        }
    }
}
