package irstit.transport.Citizens.complaint;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import irstit.transport.AppController.AppController;
import irstit.transport.DataBase.DBManager;
import irstit.transport.DataModel.ReqVacationModel;
import irstit.transport.Drivers.DriversMainActivity;
import irstit.transport.Drivers.RequestVacation;
import irstit.transport.Drivers.VacationSearch;
import irstit.transport.Globals;
import irstit.transport.R;


public class ComplaintTrack extends AppCompatActivity {

    private TextView ComplaintTrack_Code, ComplaintTrack_Date, ComplaintTrack_Response, ComplaintTrack_Status;
    private EditText ComplaintTrack_Search;
    private ImageView ComplaintTrack_BtnSearch;
    private CardView ComplaintTrack_Card, ComplaintTrack_Root;
    private RelativeLayout ComplaintTrack_Loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_track);

        ComplaintTrack_Code = findViewById(R.id.ComplaintTrack_Code);
        ComplaintTrack_Date = findViewById(R.id.ComplaintTrack_Date);
        ComplaintTrack_Response = findViewById(R.id.ComplaintTrack_Response);
        ComplaintTrack_Search = findViewById(R.id.ComplaintTrack_Search);
        ComplaintTrack_BtnSearch = findViewById(R.id.ComplaintTrack_BtnSearch);
        ComplaintTrack_Card = findViewById(R.id.ComplaintTrack_Card);
        ComplaintTrack_Root = findViewById(R.id.ComplaintTrack_Root);
        ComplaintTrack_Loading = findViewById(R.id.ComplaintTrack_Loading);
        ComplaintTrack_Status = findViewById(R.id.ComplaintTrack_Status);


        ComplaintTrack_BtnSearch.setOnClickListener(v -> {
            if (ComplaintTrack_Search.getText().toString().isEmpty()) {
                Toast.makeText(getBaseContext(), "کد پیگیری نمیتواند خالی باشد", Toast.LENGTH_SHORT).show();
            } else {
                getComplaintCode(ComplaintTrack_Search.getText().toString());
                ComplaintTrack_Root.setVisibility(View.GONE);
                ComplaintTrack_Loading.setVisibility(View.VISIBLE);
            }
        });


    }


    private void getComplaintCode(String code) {
        StringRequest getPhoneRequest = new StringRequest(Request.Method.POST, Globals.APIURL + "/complainttrack",
                response -> {
                    Log.e("ListLeavesResponse", response + " |");

                    try {

                        JSONObject object = new JSONObject(response);
                        if (object.getString("status").equals("true")) {

                            JSONObject object2 = object.getJSONObject("result");
                            Log.e("ListLeavesResponse", object2.getInt("c_status") + " | " + object2.getString("c_status"));
                            if (object2.getInt("c_status") == 1) {

                                ComplaintTrack_Root.setVisibility(View.VISIBLE);
                                ComplaintTrack_Loading.setVisibility(View.GONE);
                                JSONObject object1 = object.getJSONObject("result");

                                PersianCalendar date = new PersianCalendar(
                                        new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(object1.getString("updated_at")).getTime());


                                ComplaintTrack_Code.setText("کد پیگیری : " + object1.getString("tracking_code"));
                                ComplaintTrack_Date.setText("پاسخ در تاریخ : " + date.getPersianYear() + "/" + date.getPersianMonth() + "/" + date.getPersianDay());
                                ComplaintTrack_Response.setText("پاسخ مدیر : " + object1.getString("c_adminresponse"));
                            } else {
                                ComplaintTrack_Status.setVisibility(View.VISIBLE);
                                ComplaintTrack_Loading.setVisibility(View.GONE);
                            }
                        } else if (object.getString("status").equals("false")) {
                            ComplaintTrack_Root.setVisibility(View.VISIBLE);
                            ComplaintTrack_Loading.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        Log.e("ListLeavesError1", e.toString() + " |");
                        ComplaintTrack_Loading.setVisibility(View.GONE);
                    }
                },
                error -> {
                    Log.e("ListLeavesError2", error.toString() + " |");
                    ComplaintTrack_Loading.setVisibility(View.GONE);
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("code", code);
                return map;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> map = new HashMap<>();
                map.put("token", "df837016d0fc7670f221197cd92439b5");
                return map;
            }
        };

        getPhoneRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(getPhoneRequest);

    }


}
