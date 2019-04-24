package irstit.transport.Citizens.Account.forgettenpasswrod;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
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
import irstit.transport.Citizens.Account.CitizenGetSms;
import irstit.transport.Globals;
import irstit.transport.R;
import irstit.transport.Views.Utils;

public class CitizenGetPhoneForgotten extends Fragment {

    private EditText phone;
    private RoundButton sendInfo;
    private TextView text;
    private String state = "null";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_citizen_get_phone_forgotten_password, container, false);


        sendInfo = view.findViewById(R.id.CitizenGetPhoneForgotten_Btn);
        text = view.findViewById(R.id.CitizenGetPhoneForgotten_Text);

        sendInfo.setOnClickListener(view1 -> {
            if (!Utils.getInstance(getContext()).hasInternetAccess() && !Utils.getInstance(getContext()).isOnline()) {
                Toast.makeText(getContext(), "لطفا دسترسی به اینترنت خود را بررسی کنید!", Toast.LENGTH_SHORT).show();
            } else if (phone.getText().toString().length() > 11 || phone.getText().toString().length() < 11 || phone.getText().toString().equals("")) {
                Toast.makeText(getContext(), "شماره تلفن صحیح نمی باشد", Toast.LENGTH_SHORT).show();
            } else {

                GetPhoneRequest();
                sendInfo.startAnimation();

            }

        });


//        if (getArguments() != null && getArguments().getString("state").equals("ChangePass")) {
//            text.setText("شماره موبایل جدید خود را وارد کنید");
//        }else {
//
//        }

        phone = view.findViewById(R.id.CitizenGetPhoneForgotten_Edittext);


        return view;
    }


    private void GetPhoneRequest() {
        StringRequest getPhoneRequest = new StringRequest(Request.Method.POST,
                Globals.APIURL + "/forgetcitizen",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("forgetCitizenResponse", response + " |");

                        try {
                            JSONObject object = new JSONObject(response);

                            if (object.getString("status").equals("true")) {
                                sendInfo.revertAnimation();

                                // after getting phone number we must going to GetSms Class
                                FragmentTransaction transaction = getActivity()
                                        .getSupportFragmentManager().beginTransaction().addToBackStack("GetSms");
                                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                                Bundle bundle = new Bundle();
//                                bundle.putString("phone", phone.getText().toString());
//                                if ((getArguments() != null && getArguments().getString("state").equals("ChangePass"))) {
//                                    bundle.putString("state", "ChangePass");
//                                } else {
                                    bundle.putString("phone", phone.getText().toString());
//                                }
                                Log.e("phoneFRomGetPhoneClass1",phone.getText().toString());
                                CitizenGetSmsForgotten getSms = new CitizenGetSmsForgotten();
                                getSms.setArguments(bundle);
                                transaction.replace(R.id.CitizenActivity_Frame, getSms);
                                transaction.commit();


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

                Log.e("regCitizenError", error.toString() + " |");
                sendInfo.revertAnimation();
            }

        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("phone", phone.getText().toString());
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
