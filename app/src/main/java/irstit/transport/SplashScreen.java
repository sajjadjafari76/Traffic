package irstit.transport;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import irstit.transport.AppController.AppController;
import irstit.transport.DataBase.DBManager;
import irstit.transport.DataModel.NewsModel;

public class SplashScreen extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        startWelcomeActivity();

    }

    void startWelcomeActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                getNews();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }


    private void getNews() {
        StringRequest getPhoneRequest = new StringRequest(Request.Method.POST, Globals.APIURL + "/news",
                response -> {
                    Log.e("NewsResponse", response + " |");
                    getlog(response);
                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.getString("status").equals("true")) {

                            Intent mainIntent = new Intent(getApplicationContext(), MainPage.class);
                            mainIntent.putExtra("data", response);
                            mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(mainIntent);
                            finish();

                        } else if (object.getString("status").equals("false")) {
                            Intent mainIntent = new Intent(getApplicationContext(), MainPage.class);
                            mainIntent.putExtra("data", response);
                            mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(mainIntent);
                            finish();
                        }
                    } catch (Exception e) {
                        Log.e("ListLeavesError1", e.toString() + " |");
                        Intent mainIntent = new Intent(getApplicationContext(), MainPage.class);
                        mainIntent.putExtra("data", response);
                        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(mainIntent);
                        finish();
                    }
                },
                error -> {
                    Log.e("ListLeavesError2", error.toString() + " |");
                    Intent mainIntent = new Intent(getApplicationContext(), MainPage.class);

                    mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(mainIntent);
                    finish();
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("phone", (DBManager.getInstance(getBaseContext()).getDriverInfo().getTelephone() != null) ?
                        DBManager.getInstance(getBaseContext()).getDriverInfo().getTelephone() : "");
                return map;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> map = new HashMap<>();
//                map.put("token", "df837016d0fc7670f221197cd92439b5");
                return super.getHeaders();
            }
        };

        getPhoneRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(getPhoneRequest);

    }

    public void getlog(String veryLongString) {
        int maxLogSize = 1500;
        for(int i = 0; i <= veryLongString.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i+1) * maxLogSize;
            end = end > veryLongString.length() ? veryLongString.length() : end;
            Log.e("ShowAssChild1", veryLongString.substring(start, end));
        }
    }

}
