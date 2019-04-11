package irstit.transport.Citizens.Account;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.marozzi.roundbutton.RoundButton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import irstit.transport.AppController.AppController;
import irstit.transport.DataBase.DBManager;
import irstit.transport.DataModel.CitizenModel;
import irstit.transport.Globals;
import irstit.transport.R;
import irstit.transport.ViewPager.MainPager;
import irstit.transport.Views.CustomEdittext;
import irstit.transport.Views.Utils;

public class CitizenLogin extends AppCompatActivity {

    private RoundButton Btn;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen_login);

        CustomEdittext UserName = findViewById(R.id.CitizenLogin_UserName);
        CustomEdittext Pass = findViewById(R.id.CitizenLogin_Password);
        Btn = findViewById(R.id.CitizenLogin_Btn);

        Btn.setOnClickListener(v -> {

            if (!Utils.getInstance(getBaseContext()).hasInternetAccess() && !Utils.getInstance(getBaseContext()).isOnline()) {
                Toast.makeText(getBaseContext(), "لطفا دسترسی به اینترنت خود را بررسی کنید!", Toast.LENGTH_SHORT).show();
            } else if (UserName.getText().toString().length() == 0) {
                Toast.makeText(getBaseContext(), "شماره همراه را وارد کنید!", Toast.LENGTH_SHORT).show();
            } else if (Pass.getText().toString().length() < 5) {
                Toast.makeText(getBaseContext(), "رمز عبور نمیتواند کمتر از 5 کاراکتر باشد.", Toast.LENGTH_SHORT).show();
            }else {
                citizenLogin(UserName.getText().toString(), Pass.getText().toString());
            }
        });


    }



    private void citizenLogin(String Phone, String Pass) {
        StringRequest getPhoneRequest = new StringRequest(Request.Method.POST,
                Globals.APIURL + "/loginc",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("GetPhoneResponse", response + " |");

                        try {
                            JSONObject object = new JSONObject(response);

                            if (object.getString("status").equals("true")) {

                                Btn.revertAnimation();

                                CitizenModel model = new CitizenModel();
                                model.setUserPhone(Phone);
                                model.setUserName(object.getJSONObject("result").getString("c_name"));
                                model.setUserEmail(object.getJSONObject("result").getString("c_email"));
                                model.setUserId(object.getJSONObject("result").getString("c_id"));


                                DBManager.getInstance(getBaseContext()).setCitizenInfo(model);

                                startActivity(new Intent(getBaseContext(), MainPager.class));
                                finish();


                            } else if (object.getString("status").equals("false")) {
                                Toast.makeText(getBaseContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                                Btn.revertAnimation();
                            }
                        } catch (Exception e) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("GetPhoneError", error.toString() + " |");
                Btn.revertAnimation();
            }


        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();

                map.put("phone", Phone);
                map.put("pass", Pass);


                return map;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
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
