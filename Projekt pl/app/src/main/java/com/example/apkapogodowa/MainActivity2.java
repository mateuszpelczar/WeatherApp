package com.example.apkapogodowa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity2 extends AppCompatActivity {

    private EditText miastoEdit;
    private Button zapiszMiasto, doStronyGlownej;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        miastoEdit = findViewById(R.id.miastoEdit);
        zapiszMiasto = findViewById(R.id.zapiszMiasto);
        doStronyGlownej = findViewById(R.id.do1);

        zapiszMiasto.setOnClickListener(view -> {
            String miasto = miastoEdit.getText().toString().trim();
            if (!miasto.isEmpty()) {
                pobierzDanePogodowe(miasto);
            } else {
                Toast.makeText(this, "Proszę wprowadzić nazwę miasta!", Toast.LENGTH_SHORT).show();
            }
        });

        doStronyGlownej.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity2.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void pobierzDanePogodowe(String miasto) {
        String apiKey = "63a6407c54ba0dcc30ba448a8bde4927";
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + miasto + "&units=metric&appid=" + apiKey;

        //wyslanie zadania do API
        OkHttpClient klient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        //obsluga odpowiedzi API
        klient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(MainActivity2.this, "Błąd podczas pobierania danych!", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    Gson gson = new Gson();
                    PogodaDane pogodaDane = gson.fromJson(responseData, PogodaDane.class);

                    runOnUiThread(() -> {
                        SharedPreferences preferences = getSharedPreferences("PogodaPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("miasto", pogodaDane.getName());
                        editor.putString("danePogodowe", "Temperatura: " + pogodaDane.getMain().getTemp() + "°C\n" +
                                "Temperatura_min: " + pogodaDane.getMain().getTemp_min() + "°C\n" +
                                "Temperatura_max: " + pogodaDane.getMain().getTemp_max() + "°C\n" +
                                "Temperatura_odczuwalna: " + pogodaDane.getMain().getFeels_like() + "°C\n" +
                                "Wilgotność: " + pogodaDane.getMain().getHumidity() + "%\n" +
                                "Opis: " + pogodaDane.getWeather()[0].getDescription());
                        editor.apply();

                        // Wywołanie funkcji zapisującej do historii
                        ZapiszDoHistorii(pogodaDane.getName(), pogodaDane.getMain().getTemp(), pogodaDane.getMain().getHumidity());

                        Toast.makeText(MainActivity2.this, "Dane zapisane pomyślnie!", Toast.LENGTH_SHORT).show();
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(MainActivity2.this, "Nie znaleziono miasta!", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void ZapiszDoHistorii(String miasto, double temperatura, int wilgotnosc) {
        SharedPreferences sharedPreferences = getSharedPreferences("PogodaPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Pobranie historii jako JSON
        Gson gson = new Gson();
        String jsonHistoria = sharedPreferences.getString("historia", "[]");
        Type typListy = new TypeToken<List<String>>() {}.getType();
        List<String> listaHistorii = gson.fromJson(jsonHistoria, typListy);

        // Tworzenie nowego wpisu
        String nowyWpis = miasto + " - Temp: " + temperatura + "°C, Wilgotność: " + wilgotnosc + "%";

        // Dodanie nowego wpisu na początek listy
        listaHistorii.add(0, nowyWpis);

        // Ograniczenie listy do 5 elementów
        if (listaHistorii.size() > 5) {
            listaHistorii = listaHistorii.subList(0, 5);
        }

        // Konwersja listy z powrotem na JSON i zapis
        String nowaHistoriaJson = gson.toJson(listaHistorii);
        editor.putString("historia", nowaHistoriaJson);
        editor.apply();
    }
}
