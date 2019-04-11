package irstit.transport.Citizens.Account;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import irstit.transport.Drivers.Login.GetSms;
import irstit.transport.Globals;
import irstit.transport.R;
import irstit.transport.ViewPager.MainPager;
import irstit.transport.Views.CustomEdittext;
import irstit.transport.Views.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class CitizenGetInfo extends Fragment {


    public CitizenGetInfo() {
        // Required empty public constructor
    }

    private RoundButton sendInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_citizen_get_info, container, false);

        CustomEdittext Name = view.findViewById(R.id.CitizenGetInfo_Name);
        CustomEdittext Email = view.findViewById(R.id.CitizenGetInfo_Email);
        CustomEdittext Pass = view.findViewById(R.id.CitizenGetInfo_Pass);
        sendInfo = view.findViewById(R.id.CitizenGetInfo_Btn);

        sendInfo.setOnClickListener(v -> {

            if (!Utils.getInstance(getContext()).hasInternetAccess() && !Utils.getInstance(getContext()).isOnline()) {
                Toast.makeText(getContext(), "لطفا دسترسی به اینترنت خود را بررسی کنید!", Toast.LENGTH_SHORT).show();
            } else if (Name.getText().toString().length() == 0) {
                Toast.makeText(getContext(), "نام و نام خانوادگی را وارد کنید!", Toast.LENGTH_SHORT).show();
            } else if (Pass.getText().toString().length() < 5) {
                Toast.makeText(getContext(), "رمز عبور نمیتواند کمتر از 5 کاراکتر باشد!", Toast.LENGTH_SHORT).show();
            } else {
                sendInfoRequest(Name.getText().toString(), Email.getText().toString(), Pass.getText().toString());
                sendInfo.startAnimation();
            }

        });

        return view;
    }


    private void sendInfoRequest(String Name, String Email, String Pass) {
        StringRequest getPhoneRequest = new StringRequest(Request.Method.POST,
                Globals.APIURL + "/complatecitizen",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("GetPhoneResponse", response + " |");

                        try {
                            JSONObject object = new JSONObject(response);

                            if (object.getString("status").equals("true")) {

                                sendInfo.revertAnimation();

                                CitizenModel model = new CitizenModel();
                                model.setUserPhone(getArguments().getString("phone"));
                                model.setUserName(Name);
                                model.setUserEmail(Email);
                                model.setUserId(object.getJSONObject("result").getString("c_id"));


                                DBManager.getInstance(getContext()).setCitizenInfo(model);

                                startActivity(new Intent(getContext(), MainPager.class));
                                getActivity().finish();


                            } else if (object.getString("status").equals("false")) {
                                Toast.makeText(getContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                                sendInfo.revertAnimation();
                            }
                        } catch (Exception e) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("GetPhoneError", error.toString() + " |");
                sendInfo.revertAnimation();
            }


        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();

                map.put("phone", getArguments().getString("phone"));
                map.put("pass", Pass);
                map.put("email", Email);
                map.put("name", Name);


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
