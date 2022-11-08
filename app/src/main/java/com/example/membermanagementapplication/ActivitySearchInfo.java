package com.example.membermanagementapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ActivitySearchInfo extends Activity {
    MainActivity.myDBHelper myHelper = ((MainActivity)MainActivity.context_main).myHelper;
    SQLiteDatabase sqlDB = ((MainActivity)MainActivity.context_main).sqlDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_info_activity);
        LinearLayout linearLayoutScrollButtonAdd = (LinearLayout) findViewById(R.id.LinearLayoutScrollButtonAdd);

        Button buttonReturn = (Button) findViewById(R.id.ButtonReturn); //뒤로가기 버튼 이벤트
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView textViewName = (TextView) findViewById(R.id.TextViewName);
        TextView textViewPhone = (TextView) findViewById(R.id.TextViewPhone);

        sqlDB = myHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM groupTBL;", null);

        while (cursor.moveToNext()) {
            Button btn = new Button(this);
            btn.setText(cursor.getString(0));
            btn.setId(cursor.getPosition());
            int position = cursor.getPosition();
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("log", "position :" + position);
                    int y = position;
                    cursor.moveToPosition(position);
                    textViewName.setText(cursor.getString(0));
                    textViewPhone.setText(cursor.getString(1));
                }
            });
            linearLayoutScrollButtonAdd.addView(btn);
        }
    }
}