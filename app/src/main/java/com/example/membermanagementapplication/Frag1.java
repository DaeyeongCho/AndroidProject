package com.example.membermanagementapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class Frag1 extends Fragment {

    MainActivity mainActivity;
    View viewBirthday;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity)getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.frag1,container,false);

        Button buttonBirthday = (Button) v.findViewById(R.id.buttonBirthday);
        buttonBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "선택 버튼 누름", Toast.LENGTH_SHORT).show();
            }
        });

        Button buttonInputInfo = (Button) v.findViewById(R.id.ButtonInputInfo);
        buttonInputInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "회원정보등록 버튼 누름", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }
}