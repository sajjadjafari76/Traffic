package irstit.transport;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import co.ronash.pushe.Pushe;
import irstit.transport.AppController.AppController;
import irstit.transport.Citizens.Account.CitizensAccountActivity;
import irstit.transport.DataBase.DBManager;
import irstit.transport.Drivers.Login.ActivityLogin;
import irstit.transport.ViewPager.MainPager;

public class MainPage extends AppCompatActivity {


    @Override
    protected void onStart() {
        super.onStart();

        Log.e("ddddddd", DBManager.getInstance(getBaseContext()).getDriverInfo().getName() + " | " +
                DBManager.getInstance(getBaseContext()).getCitizenInfo().getUserId());
        if (DBManager.getInstance(getBaseContext()).getDriverInfo().getName() != null
                ||
                DBManager.getInstance(getBaseContext()).getCitizenInfo().getUserId() != null) {

            startActivity(new Intent(getBaseContext(), MainPager.class));
            finish();

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        Pushe.initialize(this,true);

        LinearLayout driver = findViewById(R.id.MainPage_Driver);
        LinearLayout citizen = findViewById(R.id.MainPage_Citizen);
        LinearLayout site = findViewById(R.id.MainPage_Site);
        LinearLayout dial = findViewById(R.id.MainPage_Dial1);


        driver.setOnClickListener(

                v->{
                      //Utils.getInstance(getApplicationContext()).isConnectingToInternet() == true

                    new CheckingInternetConnectionAccesss(internet -> {

                        if(internet == true){
                            startActivity(new Intent(getBaseContext(), ActivityLogin.class));
                     }

                        else {
                            Dialog  dialog = new Dialog(MainPage.this);
                            dialog.setContentView(R.layout.dialog_for_no_internet);
                            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

//                            dialog.setTitle("اینترنت در دسترس نیست!");
                            dialog.setCancelable(true);
                            Button button = (Button)dialog.findViewById(R.id.Delivery_Btn_NotInternet);
                                  button.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          dialog.dismiss();
                                      }
                                  });
                               dialog.show();
                           // Toast.makeText(getApplicationContext(),"عدم ارتباط با اینترنت",Toast.LENGTH_LONG).show();
                        }
                        /* do something with boolean response */
                    });


                });
        citizen.setOnClickListener(v->{
           // if(Utils.getInstance(getApplicationContext()).isConnectingToInternet() == true)
            new CheckingInternetConnectionAccesss(internet -> {

                 if(internet == true){

                     startActivity(new Intent(getBaseContext(), CitizensAccountActivity.class));

                 }   else {

                     Dialog  dialog = new Dialog(MainPage.this);
                     dialog.setContentView(R.layout.dialog_for_no_internet);
                     dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

//                     dialog.setTitle("اینترنت در دسترس نیست!");

                     dialog.setCancelable(true);
                     Button button = (Button)dialog.findViewById(R.id.Delivery_Btn_NotInternet);
//                     Toast.makeText(getApplicationContext(),"عدم ارتباط با اینترنت",Toast.LENGTH_LONG).show();


                     button.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View view) {
                             dialog.dismiss();
                         }
                     });


                     dialog.show();
                 }
            });

        }

            );
        dial.setOnClickListener(v->{

            Intent dial1 = new Intent();
            dial1.setAction("android.intent.action.DIAL");
            dial1.setData(Uri.parse("tel:"+"02835237500"));
            startActivity(dial1);

        });
        site.setOnClickListener(v->{

            Uri uri = Uri.parse("http://www.traffictakestan.ir"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);

        });



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

                            Toast.makeText(getApplicationContext(),"خطا در دریافت اطلاعات از سرور",Toast.LENGTH_LONG).show();

                        }
                    } catch (Exception e) {
                       e.printStackTrace();
                    }
                },
                error -> {

                    Log.e("ListLeavesError2", error.toString() + " |");
                    Toast.makeText(getApplicationContext(),"خطا در اتصال به سرور",Toast.LENGTH_LONG).show();


                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("phone", " 09033433776");
                map.put("TOKEN", "df837016d0fc7670f221197cd92439b5");
                Log.v("FromMainpage", map.get("phone"));

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

}

