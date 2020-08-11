package com.example.rusbumhelper;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.DialogFragment;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

public class CustomDialogFragment extends DialogFragment
{

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String mail = Objects.requireNonNull(getArguments()).getString("mail");
        DatabaseHelper sqlHelper = new DatabaseHelper(getContext());
        SQLiteDatabase db = sqlHelper.open();

        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.fragment_seting_layout, null);
        Objects.requireNonNull(getDialog()).setTitle("Редактировать");

        EditText editText_income = view.findViewById(R.id.editText__money_frag);
        EditText editText_age = view.findViewById(R.id.editText_age_frag);
        AutoCompleteTextView editText_region = view.findViewById(R.id.editText_region_frag);

        @SuppressLint("Recycle") Cursor reg = db.rawQuery("SELECT DISTINCT "
                +DatabaseHelper.COLUMN_Region
                +" FROM " + DatabaseHelper.TABLE_Facilities , null);

        ArrayList <String> Arrregion = new ArrayList<>();
        while(reg.moveToNext())
        {
            Arrregion.add(reg
                    .getString(reg
                            .getColumnIndex(DatabaseHelper.COLUMN_Region)));
        }
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                        R.layout.support_simple_spinner_dropdown_item,
                        Objects.requireNonNull(Arrregion));
        editText_region.setAdapter(adapter);

        CheckBox liq = view.findViewById(R.id.checkBox_liq_frag);
        CheckBox gowerment = view.findViewById(R.id.checkBox_gowerment_frag);
        CheckBox orphan = view.findViewById(R.id.checkBox_orphan_frag);

        SeekBar SeekBar_children = view.findViewById(R.id.seekBar_children_frag);
        SeekBar SeekBar_dis= view.findViewById(R.id.seekBar_disab_frag);

        Cursor person  = sqlHelper.Find(mail,0,DatabaseHelper.TYPE_CLIENT,db);
        person.moveToFirst();

        editText_age.setText(person.getString(person.getColumnIndex(DatabaseHelper.COLUMN_age)));
        editText_income.setText(person.getString(person.getColumnIndex(DatabaseHelper.COLUMN_Income)));
        editText_region.setText(person.getString(person.getColumnIndex(DatabaseHelper.COLUMN_Region)));

        if(person.getInt(person.getColumnIndex(DatabaseHelper.COLUMN_liquidator))==1)
        {
            liq.setChecked(true);
        }

        if(person.getInt(person.getColumnIndex(DatabaseHelper.COLUMN_Official))==1)
        {
            gowerment.setChecked(true);
        }

        if(person.getInt(person.getColumnIndex(DatabaseHelper.COLUMN_Orphan))==1)
        {
            orphan.setChecked(true);
        }

        int ProgresDIs = person.getInt(person.getColumnIndex(DatabaseHelper.COLUMN_Disability));
        int di = (ProgresDIs==0)?0:(ProgresDIs==4)? 1: (ProgresDIs==3)?2:(ProgresDIs==2)?3:4;
        SeekBar_dis.setProgress(di);

        int ProgresChild = person.getInt(person.getColumnIndex(DatabaseHelper.COLUMN_ТumberСhildren));
        SeekBar_children.setProgress(ProgresChild);

        view.findViewById(R.id.OK_frag).setOnClickListener((v) -> {
            String region = (editText_region.getText().toString().length()==0)?
                    "Россия": editText_region.getText().toString();
            if(Arrregion.contains(region))
            {
                String income =(editText_income.getText().toString().length()==0)?
                        String.valueOf(0):editText_income.getText().toString();
                String age = (editText_age.getText().toString().length()==0)?
                        String.valueOf(0):editText_age.getText().toString();
                String liqidatr = (liq.isChecked())? "1":"0";
                String gower =(gowerment.isChecked())? "1":"0";
                String orp = (orphan.isChecked())? "1":"0";
                String children = String.valueOf(SeekBar_children.getProgress());
                int buf = SeekBar_dis.getProgress();
                String dis = (buf==0)?"0":(buf==1)? "4": (buf==2)?"3":(buf==3)?"2":"1";
                sqlHelper.UPD(dis,liqidatr,gower,income,age,children,mail,orp,region,db);
                dismiss();
            }
            else
            {
                Toast.makeText(getContext(), "Введите верное название региона",
                        Toast.LENGTH_LONG).show();
            }
        });

        view.findViewById(R.id.Cancel_frag).setOnClickListener((v) -> {
            dismiss();
        });

        return view;
    }
}