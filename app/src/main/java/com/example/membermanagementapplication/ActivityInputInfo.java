package com.example.membermanagementapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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
    int selectPicture = -1;
    TextView textViewYear, textViewMonth, textViewDay;
    String gender, birthdayYMD;
    MainActivity.myDBHelper myHelper = ((MainActivity)MainActivity.context_main).myHelper;
    SQLiteDatabase sqlDB = ((MainActivity)MainActivity.context_main).sqlDB;

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

        ImageView imageViewProfilePicture = (ImageView) findViewById(R.id.ImageViewProfile); //이미지 선택 이벤트
        imageViewProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer pictureSrc[] = { R.drawable.profile1, R.drawable.profile2, R.drawable.profile3, R.drawable.profile4, R.drawable.profile5, R.drawable.profile6, R.drawable.profile7, R.drawable.profile8 };
                final String[] pictureArray = new String[] { "수의사(남자)", "수의사(여자)", "간호사(남자)", "간호사(여자)", "농부(여자)", "농부(남자)", "엔지니어(여자)", "엔지니어(남자)" };
                AlertDialog.Builder dlg = new AlertDialog.Builder(ActivityInputInfo.this);
                dlg.setTitle("프로필 사진 선택");
                dlg.setSingleChoiceItems(pictureArray, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        imageViewProfilePicture.setImageResource(pictureSrc[i]);
                        selectPicture = i;
                    }
                });

                dlg.setPositiveButton("확인", null);
                dlg.setNegativeButton("취소", null);
                dlg.show();
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

        buttonInputInfo.setOnClickListener(new View.OnClickListener() { //회원정보 등록 버튼 이벤트
            @Override
            public void onClick(View view) {
                birthdayYMD = selectYear + "년 " + selectMonth + "월 " + selectDay + "일";
                if(editTextName.getText().toString().isEmpty() ||  editTextPhone.getText().toString().isEmpty() || (gender == null || gender.isEmpty()) || birthdayYMD.equals("0년 0월 0일") || editTextInfo.getText().toString().isEmpty() || selectPicture == -1) {
                    Toast.makeText(getApplicationContext(), "미입력된 항목 존재", Toast.LENGTH_SHORT).show(); //미입력 항목 존재 시 예외처리
                }
                else {
                    sqlDB = myHelper.getWritableDatabase();
                    sqlDB.execSQL("INSERT INTO groupTBL VALUES ( '" + editTextName.getText().toString() + "' , " + editTextPhone.getText().toString() + " , '" + gender + "' , '" + birthdayYMD + "' , '" + editTextInfo.getText().toString() + "' , '" + selectPicture + "');");
                    sqlDB.close();
                    Intent intent = getIntent();
                    finish();
                    overridePendingTransition(0, 0);
                    ((MainActivity)MainActivity.context_main).refresh();
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    Toast.makeText(getApplicationContext(), "추가 완료", Toast.LENGTH_SHORT).show();
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