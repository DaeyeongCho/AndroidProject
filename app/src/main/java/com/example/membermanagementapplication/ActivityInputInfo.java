package com.example.membermanagementapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Year;
import java.util.Calendar;

public class ActivityInputInfo extends Activity {
    int selectYear, selectMonth, selectDay;
    TextView textViewYear, textViewMonth, textViewDay;
    String name, phone, gender, birthdayYear, birthdayMonth, birthdayDay, info, fileName;

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

        textViewYear = (TextView) findViewById(R.id.textViewYear);
        textViewMonth = (TextView) findViewById(R.id.textViewMonth);
        textViewDay = (TextView) findViewById(R.id.textViewDay);

        Button ButtonBirthdayDialog = (Button) findViewById(R.id.buttonBirthday); //생년월일 선택 버튼 이벤트
        ButtonBirthdayDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View birthdayDialogView = (View) View.inflate(ActivityInputInfo.this, R.layout.dialog_birthday_calendar, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(ActivityInputInfo.this);
                dlg.setTitle("생년월일 입력");
                dlg.setView(birthdayDialogView);

                DatePicker datePickerBirthday = (DatePicker) birthdayDialogView.findViewById(R.id.DatePickerBirthday); //데이트피커 사용 감지
                Calendar cal = Calendar.getInstance();
                int cYear = cal.get(Calendar.YEAR);
                int cMonth = cal.get(Calendar.MONTH);
                int cDay = cal.get(Calendar.DAY_OF_MONTH);

                datePickerBirthday.init(cYear, cMonth, cDay, new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        selectYear = year;
                        selectMonth = monthOfYear + 1;
                        selectDay = dayOfMonth;
                    }
                });

                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() { //확인 버튼 누를 시 생년월일 입력
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

        EditText editTextName = (EditText) findViewById(R.id.EditTextName); //회원 정보에 포함 될 객체들
        EditText editTextPhone = (EditText) findViewById(R.id.EditTextPhone);
        RadioButton radioButtonMale = (RadioButton) findViewById(R.id.RadioButtonMale);
        RadioButton radioButtonFemale = (RadioButton) findViewById(R.id.RadioButtonFemale);
        EditText editTextInfo = (EditText) findViewById(R.id.editTextInfo);
        Button buttonInputInfo = (Button) findViewById(R.id.ButtonInputInfo);
        TextView textViewTest = (TextView) findViewById(R.id.TextViewTest);
        buttonInputInfo.setOnClickListener(new View.OnClickListener() { //회원정보 등록 버튼 이벤트
            @Override
            public void onClick(View view) {
                name = editTextName.getText().toString();
                phone = editTextPhone.getText().toString();
                birthdayYear = textViewYear.getText().toString();
                birthdayMonth = textViewMonth.getText().toString();
                birthdayDay = textViewDay.getText().toString();
                info = editTextInfo.getText().toString();
                if((name == null || name.isEmpty()) || (phone == null || phone.isEmpty()) || (gender == null || gender.isEmpty()) || (birthdayYear.equals("0000") || birthdayYear.equals("0")) || (birthdayMonth.equals("00") || birthdayMonth.equals("0")) || (birthdayDay.equals("00") || birthdayDay.equals("0")) || info == null || info.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "미입력된 항목 존재", Toast.LENGTH_SHORT).show(); //미입력 항목 존재 시 토스트 띄움
                }
                else { //실제 회원정보 등록 이벤트 처리 내용
                    fileName = name + ".txt";
                    try {
                        FileOutputStream outFs = openFileOutput(fileName, Context.MODE_PRIVATE);
                        String str = name + "/" + phone + "/" + gender + "/" + birthdayYear + "/" + birthdayMonth + "/" + birthdayDay + "/" + info;
                        outFs.write(str.getBytes());
                        outFs.close();
                        Toast.makeText(getApplicationContext(), "저장 완료", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) { }

                }
            }
        });

        radioButtonMale.setOnClickListener(new View.OnClickListener() { //남자 선택 시
            @Override
            public void onClick(View view) {
                gender = "남성";
            }
        });

        radioButtonFemale.setOnClickListener(new View.OnClickListener() { //여자 선택 시
            @Override
            public void onClick(View view) {
                gender = "여성";
            }
        });
    }
}