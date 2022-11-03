package com.example.membermanagementapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.time.Year;
import java.util.Calendar;

public class ActivityInputInfo extends Activity {
    int selectYear, selectMonth, selectDay;

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

        TextView textViewYear = (TextView) findViewById(R.id.textViewYear);
        TextView textViewMonth = (TextView) findViewById(R.id.textViewMonth);
        TextView textViewDay = (TextView) findViewById(R.id.textViewDay);

        Button ButtonBirthdayDialog = (Button) findViewById(R.id.buttonBirthday); //생년월일 선택 버튼 이벤트
        ButtonBirthdayDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View birthdayDialogView = (View) View.inflate(ActivityInputInfo.this, R.layout.dialog_birthday_calendar, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(ActivityInputInfo.this);
                dlg.setTitle("생년월일 입력");
                dlg.setView(birthdayDialogView);

                DatePicker datePickerBirthday = (DatePicker) birthdayDialogView.findViewById(R.id.DatePickerBirthday);
                Calendar cal = Calendar.getInstance();
                int cYear = cal.get(Calendar.YEAR);
                int cMonth = cal.get(Calendar.MONTH);
                int cDay = cal.get(Calendar.DAY_OF_MONTH);

                datePickerBirthday.init(cYear, cMonth, cDay, new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        selectYear = year;
                        selectMonth = monthOfYear+1;
                        selectDay = dayOfMonth;
                    }
                });

                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        textViewYear.setText(Integer.toString(selectYear));
                        textViewMonth.setText(Integer.toString(selectMonth));
                        textViewDay.setText(Integer.toString(selectDay));
                    }
                });
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });

        Button buttonInputInfo = (Button) findViewById(R.id.ButtonInputInfo); //회원정보 등록 버튼 이벤트
        buttonInputInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}