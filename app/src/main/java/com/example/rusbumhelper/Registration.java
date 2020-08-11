package com.example.rusbumhelper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Objects;

@SuppressLint("Registered")
public class Registration extends AppCompatActivity implements View.OnClickListener
{

    EditText editText_mail;
    EditText editText_pass;
    EditText editText_income;
    EditText editText_age;
    AutoCompleteTextView editText_region;

    CheckBox liq;
    CheckBox gowerment;
    CheckBox orphan;

    SeekBar SeekBar_children;
    SeekBar SeekBar_dis;

    ArrayList<String> Arrregion;

    DatabaseHelper sqlHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registation_layout);

        sqlHelper = new DatabaseHelper(this);
        db = sqlHelper.open();

        Button btnToRegistrationtOk = findViewById(R.id.registration_ok);
        ImageButton btnBack = findViewById(R.id.button_back_autor_reg);

        btnToRegistrationtOk.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        editText_mail = findViewById(R.id.editText_e_mail);
        editText_pass = findViewById(R.id.editText_password);
        editText_income = findViewById(R.id.editText__money);
        editText_age = findViewById(R.id.editText_age);
        editText_region = findViewById(R.id.editText_region);


        @SuppressLint("Recycle") Cursor reg = db.rawQuery("SELECT DISTINCT "
                +DatabaseHelper.COLUMN_Region
                +" FROM " + DatabaseHelper.TABLE_Facilities , null);

        Arrregion = new ArrayList<>();
        while(reg.moveToNext())
        {
            Arrregion.add(reg
                    .getString(reg
                            .getColumnIndex(DatabaseHelper.COLUMN_Region)));
        }
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(Objects.requireNonNull(this),
                        R.layout.support_simple_spinner_dropdown_item,
                        Objects.requireNonNull(Arrregion));
        editText_region.setAdapter(adapter);

        liq = findViewById(R.id.checkBox_liq);
        gowerment = findViewById(R.id.checkBox_gowerment);
        orphan = findViewById(R.id.checkBox_orphan);

        SeekBar_children = findViewById(R.id.seekBar_children);
        SeekBar_dis= findViewById(R.id.seekBar_disab);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void onClick(View v)
    {
        try {

            switch(v.getId())
            {
                case R.id.registration_ok:
                {
                    String mail = editText_mail.getText().toString();
                    String pass = editText_pass.getText().toString();
                    String income =(editText_income.getText().toString().length()==0)?
                        String.valueOf(0):editText_age.getText().toString();
                    String age = (editText_age.getText().toString().length()==0)?
                            String.valueOf(0):editText_age.getText().toString();
                    String liqidatr = (liq.isChecked())? "1":"0";
                    String gower =(gowerment.isChecked())? "1":"0";
                    String orp = (orphan.isChecked())? "1":"0";
                    String children = String.valueOf(SeekBar_children.getProgress());
                    String region = (editText_region.getText().toString().length()==0)?
                            "Россия": editText_region.getText().toString();
                    int buf = SeekBar_dis.getProgress();
                    String dis = (buf==0)?"0":(buf==1)? "4": (buf==2)?"3":(buf==3)?"2":"1";

                    if(mail.length()>0 && pass.length()>0)
                    {
                        if(Arrregion.contains(region))
                        {
                            if(sqlHelper.Add(dis,liqidatr,gower,income,age,children,mail,pass,orp,region,db)==0)
                            {
                                db.close();
                                Intent intent = new Intent(Registration.this,Menu_Exemption_Activity.class);
                                intent.putExtra("mail",mail);
                                startActivity(intent);finish();
                                break;
                            }
                            else
                            {
                                Toast.makeText(this, "Клиент с таких e-mail уже существует",
                                        Toast.LENGTH_LONG).show();
                                break;
                            }
                        }
                        else
                        {
                            Toast.makeText(this, "Введите верное название региона",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(this, "Заполните поля помеченные *",
                                Toast.LENGTH_LONG).show();
                        break;
                    }
                }
                case R.id.button_back_autor_reg:
                {
                    onBackPressed();
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
            db.close();
            Intent intent = new Intent(Registration.this,Autoriz_Activity.class);
            startActivity(intent);finish();

        } catch (Exception ignored)
        {
        }
    }
}