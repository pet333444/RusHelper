package com.example.rusbumhelper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.util.Log;


import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static String DB_PATH; // полный путь к базе данных
    private static final String DB_NAME  = "Nalog.db"; // название бд
    private static final int SCHEMA = 1; // версия базы данных
    static final String GUEST = "guest@gmail.com";
    private Context myContext;
    //-------------------------------------------------------------------------------ТАБЛИЦЫ-----------------------------------------------------------------------------//
    static final String TABLE_Client = "Person";
    static final String TABLE_Facilities= "Facilities";
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    static final int TYPE_CLIENT = 0;
    static final int TYPE__Facilities= 1;
    //-----------------------------------------------------------------------------------СТОЛБЦЫ-------------------------------------------------------------------------//
   //------------------------------------------------------------Facilities------------------------------------------------------------//
    static final String COLUMN_Exemption = "Exemption";
    static final String COLUMN_Type = "Type";
    static final String COLUMN_Name = "Name";
    //------------------------------------------------------------Common------------------------------------------------------------//
    static final String COLUMN_Disability = "Disability";
    static final String COLUMN_liquidator  = "liqid";
    static final String COLUMN_Official = "Official";
    static final String COLUMN_Income = "Income";
    static final String COLUMN_age = "age";
    static final String COLUMN_ТumberСhildren = "child";
    static final String COLUMN_Orphan = "Orphan";
    static final String COLUMN_Region = "Region";
    //------------------------------------------------------------Person----------------------------------------------------------------//
    static final String COLUMN_e_mail = "e_mail";
    static final String COLUMN_password = "password";
    //------------------------------------------------------------FIND-KEY-------------------------------------------------------------------------------------//
    static final int ID_TAX = 0;
    static final int ID_HOME = 1;
    static final int ID_REALTY = 2;
    static final int ID_EDUCATION = 3;
    static final int ID_OTHER = 4;
    //-----------------------------------------------------------------------------------------------------------------------------------------------------//

    @SuppressLint("SdCardPath")
    DatabaseHelper(Context context)
    {
        super(context, DB_NAME, null, SCHEMA);
        this.myContext=context;
        DB_PATH ="/data/data/"+context.getPackageName()+"/databases" +"/" + DB_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
    }

    long Add(String Diisab, String liquidat, String Official,
             String Income, String age, String Children, String mail,
             String pass,String Orpha, String Region, @NotNull SQLiteDatabase db )
    {
        @SuppressLint("Recycle") Cursor day = Find(mail,0,TYPE_CLIENT,db);

        if (day.getCount() <= 0)
        {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_Disability,Diisab);
            cv.put(COLUMN_liquidator,liquidat);
            cv.put(COLUMN_Official,Official);
            cv.put(COLUMN_Income,Income);
            cv.put(COLUMN_age, age);
            cv.put(COLUMN_ТumberСhildren,Children);
            cv.put(COLUMN_e_mail, mail);
            cv.put(COLUMN_password,pass);
            cv.put(COLUMN_Orphan,Orpha);
            cv.put(COLUMN_Region,Region);
            db.insert(TABLE_Client, null, cv);

            return 0;
        }
        else
        {
            return -1;
        }
    }

    void UPD(String Diisab, String liquidat, String Official,
             String Income, String age, String Children, String mail,
             String Orpha,String Region,@NotNull SQLiteDatabase db )
    {
        @SuppressLint("Recycle") Cursor day = db.query(TABLE_Client,
                null,
                COLUMN_e_mail + " =?",
                new String[] {mail},
                null, null, null);

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_Disability,Diisab);
        cv.put(COLUMN_liquidator,liquidat);
        cv.put(COLUMN_Official,Official);
        cv.put(COLUMN_Income,Income);
        cv.put(COLUMN_age, age);
        cv.put(COLUMN_ТumberСhildren,Children);
        cv.put(COLUMN_Orphan,Orpha);
        cv.put(COLUMN_Region,Region);
        db.update(TABLE_Client,cv,COLUMN_e_mail + " = ?", new String[]{mail});
    }

    @SuppressLint("Recycle")
    @NonNull
    Cursor Find(String mail,int Type_ex,int type,
                @NotNull SQLiteDatabase db )
    {
        Cursor person;

        switch (type)
        {
            case TYPE__Facilities:
            {
                person=db.query(TABLE_Facilities,
                        null,COLUMN_Type + "=?" ,
                        new String[] {String.valueOf(Type_ex)},
                        null, null, null);
                return person;
            }
            case TYPE_CLIENT:
            {
                person = db.query(TABLE_Client,
                        null,
                        COLUMN_e_mail + " =?",
                        new String[] {mail},
                        null, null, null);
                return person;
            }
            default:
            {
                throw new IllegalArgumentException("Ошибка id типа");
            }
        }
    }

    void create_db()
    {
        InputStream myInput = null;
        OutputStream myOutput = null;
        try {
            File file = new File(DB_PATH);
            if (!file.exists()) {
                this.getReadableDatabase();
                //получаем локальную бд как поток
                myInput = myContext.getAssets().open(DB_NAME);
                // Путь к новой бд
                String outFileName = DB_PATH;

                // Открываем пустую бд
                myOutput = new FileOutputStream(outFileName);

                // побайтово копируем данные
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }

                myOutput.flush();
                myOutput.close();
                myInput.close();
            }
        }
        catch(IOException ex){
            Log.d("DatabaseHelper", Objects.requireNonNull(ex.getMessage()));
        }
    }

    SQLiteDatabase open()throws SQLException
    {
        return SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }
}