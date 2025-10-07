package com.example.apkapogodowa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity4 extends AppCompatActivity {


    private Button CheckPogoda,checkHistoria;
    private ImageView CLOSEapp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main4);

        CheckPogoda =findViewById(R.id.sprawdzPogode);
        CLOSEapp = findViewById(R.id.closeApp1);
        checkHistoria = findViewById(R.id.sprawdzHistorie);


        CheckPogoda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity4.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        CLOSEapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity(); //zamyka wszystkie aktywnosci
                System.exit(0);
            }
        });

        checkHistoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity4.this, MainActivity3.class);
                startActivity(intent);
                finish();
            }
        });




    }
}