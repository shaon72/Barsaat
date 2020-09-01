package com.solutions.specialtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView time,status,wind,sunrise,sunset,temp,mintmp,maxtmp,pressure,humidity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        time=findViewById(R.id.cur_time);
        String current = java.text.DateFormat.getDateTimeInstance().format(new Date());
        status=findViewById(R.id.status);
        time.setText(current);
        temp=(TextView)findViewById(R.id.tv1);
        mintmp=findViewById(R.id.tv2);
        maxtmp=findViewById(R.id.tv3);
        sunrise=findViewById(R.id.sunrise);
        sunset=findViewById(R.id.sunset);
        wind=findViewById(R.id.wind);
        pressure=findViewById(R.id.pressure);
        humidity=findViewById(R.id.humidity);
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);
        String API = "a90bbb98713d32ce632992fa22eba192";
        String url="https://api.openweathermap.org/data/2.5/weather?q=kolkata&units=metric&appid=" + API;

        final JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject main = response.getJSONObject("main");
                    temp.setText(main.getString("temp")+"°C");
                    mintmp.setText("Min : "+main.getString("temp_min")+"°C");
                    maxtmp.setText("Max : "+main.getString("temp_max")+"°C");
                    pressure.setText(main.getString("pressure")+"mb");
                    humidity.setText(main.getString("humidity")+"%");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);

        final JsonObjectRequest jsonObjectRequest1=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject weather = response.getJSONArray("weather").getJSONObject(0);
                    status.setText(weather.getString("main"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest1);

        final JsonObjectRequest jsonObjectRequest2=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject sys = response.getJSONObject("sys");
                    Long snr = sys.getLong("sunrise");
                    sunrise.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(snr * 1000)));
                    Long snt=sys.getLong("sunset");
                    sunset.setText(new SimpleDateFormat("hh:mm a",Locale.ENGLISH).format(new Date(snt*1000)));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest2);

        final JsonObjectRequest jsonObjectRequest3=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject sys = response.getJSONObject("wind");
                    wind.setText(sys.getString("speed")+"km/h");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest3);

    }
}
