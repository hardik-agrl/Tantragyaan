package com.example.thirdmilestone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private TextView tvtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvtext = findViewById(R.id.textView3);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this,Activity2.class);
                startActivity(intent) ;
            }
//        });


    });


    
//    public void openActivity2() {
//        Intent intent = new Intent(this, Activity2.class);
//        startActivity(intent);
//    }
//
//    public void textdisp(){
//        Intent intent = new Intent(this,Activity2.class);
//        startActivity(intent) ;
//    }


}}