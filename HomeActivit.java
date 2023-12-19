package com.example.cs4433qr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class HomeActivit extends AppCompatActivity {

    Button GenButton, ScanButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        GenButton = findViewById(R.id.GenButton);
        ScanButton = findViewById(R.id.ScanButton);

        GenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivit.this,GenerateActivity.class);
                startActivity(i);
            }
        });

        ScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivit.this, ScanActivity.class);
                startActivity(i);
            }
        });
    }
}