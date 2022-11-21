package com.example.membermanagementapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {
    public static Context context_main;
    public myDBHelper myHelper;
    public SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context_main = this;

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