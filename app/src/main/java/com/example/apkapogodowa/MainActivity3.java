package com.example.apkapogodowa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity3 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HistoriaAdapter adapter;
    private List<String> historiaList;
    private Button Cofnij,Wyczysc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Cofnij = findViewById(R.id.cofnijButton);
        Wyczysc = findViewById(R.id.ClearButton);

        
        // Pobranie historii zapisanej w SharedPreferences
        SharedPreferences preferences = getSharedPreferences("PogodaPrefs", MODE_PRIVATE);
        String jsonHistoria = preferences.getString("historia", "[]");

        // Konwersja JSON -> Lista
        Gson gson = new Gson();
        Type typListy = new TypeToken<List<String>>() {}.getType();
        historiaList = gson.fromJson(jsonHistoria, typListy);

        if (historiaList == null || historiaList.isEmpty()) {
            historiaList = new ArrayList<>();
            Toast.makeText(this, "Brak zapisanych wyszukiwań", Toast.LENGTH_SHORT).show();
        }

        // Ustawienie adaptera do RecyclerView
        adapter = new HistoriaAdapter(historiaList);
        recyclerView.setAdapter(adapter);

        Cofnij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity3.this, MainActivity4.class);
                startActivity(intent);
                finish();

            }
        });

        Wyczysc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("PogodaPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("historia", "[]"); // czyszczenie historii
                editor.apply();

                historiaList.clear();
                adapter.notifyDataSetChanged(); // odswiezenie recycler view
                Toast.makeText(MainActivity3.this, "Historia została usunieta",Toast.LENGTH_SHORT);
            }
        });
        
    }
}
