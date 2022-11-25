package com.example.membermanagementapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static Context context_main;
    public myDBHelper myHelper;
    public SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context_main = this;

        String[] hello = {"안녕하세요!", "어서오세요!", "반갑습니다!", "오늘도 좋은 하루!",
                "도움이 필요하시면\n오른쪽 상단을 눌러보세요!","초기화 버튼은 위험해요!" ,
                "죽는 날까지 하늘을 우러러\n한 점 부끄럼 없기를..",
                "얇은 사 하이얀 고깔은\n고이 접어서 나빌레라",
                "기다리고 있었습니다 (_ _)", "(･_･o)응?(o･_･)응?(o･_･o)응?",
                "(｡•̀ᴗ-)✧ 찡긋~**"};
        TextView textViewSayHello = (findViewById(R.id.TextViewSayHello));
        Random random = new Random();
        textViewSayHello.setText(hello[random.nextInt(hello.length)]);
        textViewSayHello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                finish();
                overridePendingTransition(0, 0);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });


        Button buttonInputInfo = (Button) findViewById(R.id.ButtonInputInfo); //회원추가 버튼 이벤트
        buttonInputInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityInputInfo.class);
                startActivity(intent);
            }
        });

        Button buttonSearchInfo = (Button) findViewById(R.id.ButtonSearchInfo); //회원 조회 버튼 이벤트
        buttonSearchInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivitySearchInfo.class);
                startActivity(intent);
            }
        });

        myHelper = new myDBHelper(this);
        TextView textViewHowManyPeople = (TextView) findViewById(R.id.TextViewHowManyPeople); //현재 회원 수
        sqlDB = myHelper.getWritableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM groupTBL;", null);
        int howManySqlRow = cursor.getCount();
        textViewHowManyPeople.setText(Integer.toString(howManySqlRow));
        sqlDB.close();
    }

    public void refresh() { //메인 액티비티 리프레쉬 함수
        Intent intent = getIntent();
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater mInflater = getMenuInflater();
        mInflater.inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemInitialization:
                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                dlg.setTitle("모든 회원정보가 삭제됩니다.");
                dlg.setMessage("정말 초기화하시겠습니까?");
                dlg.setIcon(R.drawable.warning_icon);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sqlDB = myHelper.getWritableDatabase();
                        myHelper.onUpgrade(sqlDB, 1, 2);
                        sqlDB.close();
                        Toast.makeText(getApplicationContext(), "초기화 완료", Toast.LENGTH_SHORT).show();
                    }
                });
                dlg.setNegativeButton("취소", null);
                dlg.show();
                return true;

            case R.id.itemShowManual:
                Intent intent = new Intent(getApplicationContext(), Explanation.class);
                startActivity(intent);
        }
        return false;
    }

    public class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(Context context) {
            super(context, "groupDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE groupTBL (gName CHAR(20) PRIMARY KEY, gPhone CHAR(20), gGender CHAR(20), gBirthday CHAR(20), gInfo CHAR(200), gPicture INTEGER);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS groupTBL");
            onCreate(db);
        }
    }
}