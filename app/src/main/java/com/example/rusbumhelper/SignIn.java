package com.example.rusbumhelper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("Registered")
public class SignIn extends AppCompatActivity implements View.OnClickListener
{
    EditText editText_pass;
    EditText editText_mail;
    DatabaseHelper sqlHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_layout);

        findViewById(R.id.signIn).setOnClickListener(this);
        findViewById(R.id.button_back_autoriz_sign).setOnClickListener(this);
        editText_pass = findViewById(R.id.editText_password);
        editText_mail=findViewById(R.id.editText_e_mail);
        sqlHelper = new DatabaseHelper(this);
        db = sqlHelper.open();

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void onClick(View v)
    {
        try {
            switch(v.getId())
            {
                case R.id.signIn:
                {
                    String strCatPassword = editText_pass.getText().toString();
                    String strCatMail = editText_mail.getText().toString();

                    if(strCatPassword.length()>0 && strCatMail.length()>0)
                    {
                        Cursor Person = sqlHelper.Find(strCatMail,0,DatabaseHelper.TYPE_CLIENT,db);
                        if(Person.getCount()>0)
                        {
                            Person.moveToFirst();
                            String db_person_pass = Person.getString(Person.getColumnIndex(DatabaseHelper.COLUMN_password));
                            if(strCatPassword.equals(db_person_pass))
                            {
                                db.close();
                                Intent intent = new Intent(SignIn.this,Menu_Exemption_Activity.class);
                                intent.putExtra("mail",strCatMail);
                                startActivity(intent);finish();
                            }
                            else
                            {
                                Toast.makeText(this, "Не верный пароль",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(this, "Не верный e-mail",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(this, "Заполните все поля",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    break;
                }
                case R.id.button_back_autoriz_sign:
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
            Intent intent = new Intent(SignIn.this,Autoriz_Activity.class);
            startActivity(intent);finish();

        } catch (Exception ignored)
        {
        }
    }
}