package com.example.membermanagementapplication;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Explanation extends AppCompatActivity{
    int i, max;
    ImageView imageView;
    TypedArray typedArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explanation);

        imageView = (ImageView)findViewById(R.id.imageview);
        typedArray = getResources().obtainTypedArray(R.array.app_image);

        i = 0;
        max = typedArray.length();
        imageView.setImageResource(typedArray.getResourceId(i, -1));

        Button left_btn = (Button)findViewById(R.id.left_btn);
        left_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                i--;
                if(i<=0)
                    i=0;
                imageView.setImageResource(typedArray.getResourceId(i, -1));
            }
        });

        Button right_btn = (Button)findViewById(R.id.right_btn);
        right_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                i++;
                if(i>=max)
                {
                    i = max -1;
                }
                imageView.setImageResource(typedArray.getResourceId(i, -1));
            }
        });

        Button return_btn = (Button) findViewById(R.id.return_btn);
        return_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                finish();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

    }

}