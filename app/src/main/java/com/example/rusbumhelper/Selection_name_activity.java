package com.example.rusbumhelper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Objects;

@SuppressLint("Registered")
public class Selection_name_activity extends AppCompatActivity implements View.OnClickListener
{

    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    int type;
    String mail;
    HashMap<String,String> pack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name_selection_layout);

        sqlHelper = new DatabaseHelper(this);
        db = sqlHelper.open();

        type = Objects.requireNonNull(getIntent().getExtras()).getInt("ID");
        mail = Objects.requireNonNull(getIntent().getExtras()).getString("mail");


        Cursor person = sqlHelper.Find(mail,
                0,DatabaseHelper.TYPE_CLIENT,db);
                person.moveToFirst();

                int dis = person.getInt(person.getColumnIndex(DatabaseHelper.COLUMN_Disability));
                boolean disFlag = dis == 1;
                int Liqid = person.getInt(person.getColumnIndex(DatabaseHelper.COLUMN_liquidator));
                int Ofic = person.getInt(person.getColumnIndex(DatabaseHelper.COLUMN_Official));
                int Income = person.getInt(person.getColumnIndex(DatabaseHelper.COLUMN_Income));
                int age = person.getInt(person.getColumnIndex(DatabaseHelper.COLUMN_age));
                int Child = person.getInt(person.getColumnIndex(DatabaseHelper.COLUMN_ТumberСhildren));
                int Orp = person.getInt(person.getColumnIndex(DatabaseHelper.COLUMN_Orphan));
                String reg = person.getString(person.getColumnIndex(DatabaseHelper.COLUMN_Region));

                Cursor facility = sqlHelper.Find(null,type,DatabaseHelper.TYPE__Facilities,db);

                LinearLayout Layout = findViewById(R.id.btn_selection_layout);
        Button Select_name;
        pack = new HashMap<>();

        while(facility.moveToNext())
        {
            int r =facility.getInt(facility.getColumnIndex(DatabaseHelper.COLUMN_liquidator));
            if(
                    (dis <= facility.getInt(facility.getColumnIndex(DatabaseHelper.COLUMN_Disability)) || disFlag) &&
                            Liqid >= facility.getInt(facility.getColumnIndex(DatabaseHelper.COLUMN_liquidator))&&
                            Ofic >= facility.getInt(facility.getColumnIndex(DatabaseHelper.COLUMN_Official))&&
                            Income >= facility.getInt(facility.getColumnIndex(DatabaseHelper.COLUMN_Income))&&
                            age >= facility.getInt(facility.getColumnIndex(DatabaseHelper.COLUMN_age))&&
                            Child >= facility.getInt(facility.getColumnIndex(DatabaseHelper.COLUMN_ТumberСhildren))&&
                            Orp >= facility.getInt(facility.getColumnIndex(DatabaseHelper.COLUMN_Orphan))&&
                            (reg.equals(facility.getString(facility.getColumnIndex(DatabaseHelper.COLUMN_Region)))
                                    || facility.getString(facility.getColumnIndex(DatabaseHelper.COLUMN_Region)).equals("Россия")))
            {
                String name = facility.getString(facility.getColumnIndex(DatabaseHelper.COLUMN_Name));
                String facil = facility.getString(facility.getColumnIndex(DatabaseHelper.COLUMN_Exemption))
                        .replace("\\n","\n");
                pack.put(name,facil);
                Select_name= new Button(this);
                Select_name.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                Select_name.setBackgroundResource(R.drawable.button_stroke_yellow_press_white);
                Select_name.setAllCaps(false);
                Select_name.setTextAlignment(TextView.TEXT_ALIGNMENT_TEXT_START);
                Select_name.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                Select_name.setTextSize(20);
                Select_name.setText(name);
                Select_name.setPadding(10,0,0,0);
                Select_name.setOnClickListener(this);

                Layout.addView(Select_name);
            }
        }

        findViewById(R.id.button_back__select).setOnClickListener(this);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void onClick(View v)
    {
        try {
            if (v.getId() == R.id.button_back__select) {
                onBackPressed();
            }
            else
            {
                db.close();
                Intent intent = new Intent(Selection_name_activity.this,Text_Facil_View_Activity.class);
                Button btn = (Button)v;
                intent.putExtra("Facil",Objects.requireNonNull(pack.get(btn.getText().toString())));
                intent.putExtra("mail",mail);
                intent.putExtra("ID",type);

                startActivity(intent);finish();
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
            db.close();
            Intent intent = new Intent(Selection_name_activity.this,Menu_Exemption_Activity.class);
            intent.putExtra("mail",mail);
            startActivity(intent);finish();

        } catch (Exception ignored)
        {
        }
    }
}