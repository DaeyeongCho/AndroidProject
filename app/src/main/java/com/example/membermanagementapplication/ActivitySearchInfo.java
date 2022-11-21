package com.example.membermanagementapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
        TextView textViewName = (TextView) findViewById(R.id.TextViewName); //회원 정보 표현하는 뷰들
        TextView textViewPhone = (TextView) findViewById(R.id.TextViewPhone);
        TextView textViewGender = (TextView) findViewById(R.id.TextViewGender);
        TextView textViewBirthday = (TextView) findViewById(R.id.TextViewBirthday);
        TextView textViewInfo = (TextView) findViewById(R.id.textViewInfo);
        ImageView imageViewProfile = (ImageView) findViewById(R.id.ImageViewProfile);
        Integer pictureSrc[] = { R.drawable.profile1, R.drawable.profile2, R.drawable.profile3, R.drawable.profile4, R.drawable.profile5, R.drawable.profile6, R.drawable.profile7, R.drawable.profile8 };

        sqlDB = myHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM groupTBL;", null);

        while (cursor.moveToNext()) { //회원 버튼 생성 및 동작
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
                    textViewGender.setText(cursor.getString(2));
                    textViewBirthday.setText(cursor.getString(3));
                    textViewInfo.setText(cursor.getString(4));
                    imageViewProfile.setImageResource(pictureSrc[cursor.getInt(5)]);
                }
            });
            linearLayoutScrollButtonAdd.addView(btn);
        }

        final Button buttonDeleteMember = (Button) findViewById(R.id.buttonDeleteMember); //회원정보 삭제 이벤트
        buttonDeleteMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(textViewName.getText().toString().isEmpty()) { //회원정보 미지정 상태 삭제 시 예외처리
                    AlertDialog.Builder dlg = new AlertDialog.Builder(ActivitySearchInfo.this);
                    dlg.setTitle("경고!!");
                    dlg.setMessage("삭제 할 회원정보가 없거나 지정되지 않았습니다.");
                    dlg.setIcon(R.drawable.warning_icon);
                    dlg.setPositiveButton("확인", null);
                    dlg.show();
                }
                else {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(ActivitySearchInfo.this);
                    dlg.setTitle("경고!!");
                    dlg.setMessage("정말 삭제하시겠습니까?");
                    dlg.setIcon(R.drawable.warning_icon);
                    dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            sqlDB.execSQL("DELETE FROM groupTBL WHERE gName = '" + cursor.getString(0) + "';");
                            Intent intent = getIntent();
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(intent);
                            overridePendingTransition(0, 0);
                            Toast.makeText(getApplicationContext(), "삭제 완료", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dlg.setNegativeButton("취소", null);
                    dlg.show();
                }
            }
        });
    }
}