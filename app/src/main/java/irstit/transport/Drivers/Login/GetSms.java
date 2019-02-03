package irstit.transport.Drivers.Login;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.marozzi.roundbutton.RoundButton;
import com.mukesh.OtpView;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import irstit.transport.AppController.AppController;
import irstit.transport.DataBase.DBManager;
import irstit.transport.DataModel.DriverInfoModel;
import irstit.transport.Drivers.DriversMainActivity;
import irstit.transport.Globals;
import irstit.transport.R;
import irstit.transport.Views.Utils;

public class GetSms extends Fragment implements View.OnClickListener {

    private OtpView otpView;
    private RoundButton btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_get_sms, container, false);

        otpView = view.findViewById(R.id.GetSms_Otp);
        btn = view.findViewById(R.id.GetSms_Btn);
        otpView.setOtpCompletionListener((String s) -> {
            if (!Utils.getInstance(getContext()).hasInternetAccess() && !Utils.getInstance(getContext()).isOnline()) {
                Toast.makeText(getContext(), "لطفا دسترسی به اینترنت خود را بررسی کنید!", Toast.LENGTH_SHORT).show();
            } else {
                getDriverInfo();
            }
        });
        btn.setOnClickListener((View v) -> {
            if (!Utils.getInstance(getContext()).hasInternetAccess() && !Utils.getInstance(getContext()).isOnline()) {
                Toast.makeText(getContext(), "لطفا دسترسی به اینترنت خود را بررسی کنید!", Toast.LENGTH_SHORT).show();
            } else {
                getDriverInfo();
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {

    }

    private void getDriverInfo() {

        StringRequest getDriverInfo = new StringRequest(Request.Method.POST, Globals.APIURL + "/validation",
                response -> {

                    Log.e("getDriverInfoResponse", response + " |");

                    try {
                        JSONObject object = new JSONObject(response);

                        if (object.getJSONObject("result").getString("status").equals("true")) {
                            btn.revertAnimation();

                            DriverInfoModel info = new DriverInfoModel();
                            info.setName(object.getJSONObject("driverdata").getString("d_name"));
                            info.setFamily(object.getJSONObject("driverdata").getString("d_family"));
                            info.setParent(object.getJSONObject("driverdata").getString("d_parent"));
                            info.setNationalCode(new String(Base64.decode(
                                    object.getJSONObject("driverdata").getString("d_nmc"),Base64.DEFAULT), StandardCharsets.UTF_8));
                            info.setBirthCertificate(object.getJSONObject("driverdata").getString("d_passcode"));
                            info.setTelephone(new String(Base64.decode(
                                    object.getJSONObject("driverdata").getString("d_tel"),Base64.DEFAULT), StandardCharsets.UTF_8));
                            DBManager.getInstance(getContext()).setDriverInfo(info);

                            startActivity(new Intent(getActivity(), DriversMainActivity.class));
                            getActivity().finish();
                        }
                    } catch (Exception e) {

                    }
                }, error -> {

            Log.e("getDriverInfoError", error.toString() + " |");
            btn.revertAnimation();
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("validateCode", otpView.getText().toString());
                map.put("phone", getArguments().getString("phone"));
                return map;
            }
        };

        getDriverInfo.setRetryPolicy(new DefaultRetryPolicy(
                10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(getDriverInfo);

    }

}
