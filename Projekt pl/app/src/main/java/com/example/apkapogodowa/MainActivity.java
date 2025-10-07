package com.example.apkapogodowa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apkapogodowa.MainActivity2;
import com.example.apkapogodowa.MainActivity3;


public class MainActivity extends AppCompatActivity {

    private TextView Miasto, danepogodowe;
    private Button wybormiasta, GlowneOkno;
    private ImageView CLOSEAPP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Miasto = findViewById(R.id.miasto);
        danepogodowe = findViewById(R.id.danepogodowe);
        wybormiasta = findViewById(R.id.wybormiasta);
        GlowneOkno = findViewById(R.id.doMain);
        CLOSEAPP = findViewById(R.id.closeApp);

        //pobranie danych z sharedpreferences
        SharedPreferences preferences = getSharedPreferences("PogodaPrefs", MODE_PRIVATE);
        String zapisaneMiasto = preferences.getString("miasto", "Brak miasta");
        String zapisaneDane = preferences.getString("danePogodowe", "Brak danych pogodowych");

        Miasto.setText("Miasto: " + zapisaneMiasto);
        danepogodowe.setText(zapisaneDane);

        wybormiasta.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);
            finish();
        });

        GlowneOkno.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MainActivity4.class);
            startActivity(intent);
            finish();
        });

        CLOSEAPP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
    }
}
