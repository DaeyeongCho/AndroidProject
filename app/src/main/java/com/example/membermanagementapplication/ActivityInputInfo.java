package com.example.membermanagementapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityInputInfo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_info_activity);

        Button buttonReturn = (Button) findViewById(R.id.ButtonReturn); //뒤로가기 버튼 이벤트
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final Button ButtonBirthdayDialog = (Button) findViewById(R.id.buttonBirthday); //생년월일 선택 버튼 이벤트
        ButtonBirthdayDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(ActivityInputInfo.this);
                dlg.setTitle("생년월일 선택");
                dlg.setMessage("내용");
                dlg.show();
            }
        });
    }
}