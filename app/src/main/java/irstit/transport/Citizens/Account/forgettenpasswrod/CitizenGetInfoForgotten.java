package irstit.transport.Citizens.Account.forgettenpasswrod;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import irstit.transport.Globals;
import irstit.transport.R;
import irstit.transport.ViewPager.MainPager;
import irstit.transport.Views.CustomEdittext;
import irstit.transport.Views.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class CitizenGetInfoForgotten extends Fragment {


    public CitizenGetInfoForgotten() {
        // Required empty public constructor
    }

    private RoundButton sendInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_citizen_get_infoforgotten, container, false);


        CustomEdittext renterpass = view.findViewById(R.id.CitizenGetSms_enterPasswordForgotten);
        CustomEdittext Pass = view.findViewById(R.id.CitizenGetSms_RenterPasswordForgotten);
        String newPass = Pass.getText().toString();
        sendInfo = view.findViewById(R.id.CitizenGetSms_BtnForgotten);

        sendInfo.setOnClickListener(v -> {

            if (!Utils.getInstance(getContext()).hasInternetAccess() && !Utils.getInstance(getContext()).isOnline()) {
                Toast.makeText(getContext(), "لطفا دسترسی به اینترنت خود را بررسی کنید!", Toast.LENGTH_SHORT).show();
            }  else if (Pass.getText().toString().length() < 5 && !renterpass.getText().toString().equals(Pass.getText().toString())) {
                Toast.makeText(getContext(), "رمز عبور نمیتواند کمتر از 5 کاراکتر باشد و تکرار رمز باید یکسان باشد!", Toast.LENGTH_SHORT).show();
            } else {
                sendInfoRequest(Pass.getText().toString());
                sendInfo.startAnimation();
            }

        });

        return view;
    }


    private void sendInfoRequest(String newpassword) {
        StringRequest getPhoneRequest = new StringRequest(Request.Method.POST,
                Globals.APIURL + "/forgetcp",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("ChangingPassword", response + " |");

                        try {
                            JSONObject object = new JSONObject(response);

                            if (object.getString("status").equals("true")) {

                                sendInfo.revertAnimation();

                                /* below we must  update DataBase Password Field by an approperate
                                     way
                                CitizenModel model = new CitizenModel();
                                model.setUserPhone(getArguments().getString("phone"));
                                model.setUserName(Name);
                                model.setUserEmail(renterpass);
                                model.setUserId(object.getJSONObject("result").getString("c_id"));
                                DBManager.getInstance(getContext()).setCitizenInfo(model);

                              */

                                Log.e("changingpass","successful");
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

                Log.e("phone",getArguments().getString("phone"));
                Log.e("lognewPass",newpassword);
                Log.e("logphone",getArguments().getString("phone"));


                // pay attenion here to the code
                map.put("phone", getArguments().getString("phone"));
                map.put("newpass", newpassword);



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
