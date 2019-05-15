package irstit.transport;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import irstit.transport.AppController.AppController;
import irstit.transport.Citizens.complaint.Complaint;
import irstit.transport.Views.Utils;

//implements Parcelable
public class SplashScreen extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1500;
    private int Inite;





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
                      if(Utils.getInstance(getBaseContext()).isOnline()){


                          getNews();
                      }
                      else {

                          Intent mainIntent = new Intent(getApplicationContext(), MainPage.class);
                      //    mainIntent.putExtra("data", response);
                          mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                          startActivity(mainIntent);

                      }
                  }
              }, SPLASH_DISPLAY_LENGTH);





        }

    private void sendDateToComplaint(){

        Intent send = new Intent(this,Complaint.class);

    }
    private void getNews() {
         String internetStatus = "true";



            StringRequest getPhoneRequest = new StringRequest(Request.Method.POST, Globals.APIURL + "/news",
                    response -> {
                        Log.e("NewsResponse", response + " |");
                        getlog(response);
                        try {
                            JSONObject object = new JSONObject(response);

                            if (object.getString("status").equals("true")) {
                                Log.v("news", object.getString("news").toString());
                                Log.e("userData", object.getString("userdata"));
                                JSONArray jsonArray = object.getJSONArray("complainttype");

                                SharedPreferences shared = getSharedPreferences("complaint", MODE_PRIVATE);
                                SharedPreferences.Editor editor = shared.edit();
                                editor.putString("complaintArray", object.toString());
                                editor.commit();


                                Log.e("Complaint", jsonArray.toString());
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
                    map.put("phone", " 09033433776");
                    map.put("TOKEN", "df837016d0fc7670f221197cd92439b5");
                    Log.v("FromSplash", map.get("phone"));

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
                    5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
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

        Log.e("work","yessssssssss");
    }
 /*
    @Override
    public int describeContents() {

        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(SPLASH_DISPLAY_LENGTH);
    }

    */
}
