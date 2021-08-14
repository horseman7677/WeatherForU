package com.horseman.weatherforu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView place,degreeTop, degreeDown, pressure, windSpeed, humidity, visibility, seaLevel,condition;
    TextInputEditText search;
    String s = "jamshedpur";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        place = findViewById(R.id.out_place);
        degreeTop = findViewById(R.id.out_degree_top);
        degreeDown = findViewById(R.id.out_degree_down);
        condition = findViewById(R.id.out_condition);
        pressure = findViewById(R.id.out_presssure);
        windSpeed = findViewById(R.id.out_wind_pressure);
        humidity = findViewById(R.id.out_humidity);
        visibility = findViewById(R.id.out_visibility);
        seaLevel = findViewById(R.id.out_sea_level);

        fetchData(s);

        search = findViewById(R.id.txtIET);
        search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
               if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                   String s =search.getText().toString().trim();
                   fetchData(s);
               }

                return false;
            }
        });


    }

    private void fetchData(String s) {

        String url = "https://api.openweathermap.org/data/2.5/weather?q="+s+"&appid=99355db73c456ecafeeb7e0c25d2b9f5";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    place.setText(s);
                    //Toast.makeText(MainActivity.this, "entering", Toast.LENGTH_SHORT).show();
                    String scondition = response.getJSONArray("weather").getJSONObject(0).getString("description");
                    condition.setText(scondition);
                    String temp = response.getJSONObject("main").getString("temp");
                    degreeTop.setText(temp);
                    degreeDown.setText(temp);
                    String spressure = response.getJSONObject("main").getString("pressure");
                    pressure.setText(spressure);
                    String wind_speed = response.getJSONObject("wind").getString("speed");
                    windSpeed.setText(wind_speed);
                    String shumidity = response.getJSONObject("main").getString("humidity");
                    humidity.setText(shumidity);
                    String svisibility = response.getString("visibility");
                    visibility.setText(svisibility);
                    String ssea_level = response.getJSONObject("main").getString("sea_level");
                    seaLevel.setText(ssea_level);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        String s = "jamshedpur";
//        fetchData(s);
//    }
}