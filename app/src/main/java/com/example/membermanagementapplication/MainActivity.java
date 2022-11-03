package com.example.membermanagementapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }
}