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
import irstit.transport.Citizens.Account.CitizenLogin;
import irstit.transport.DataBase.DBManager;
import irstit.transport.DataModel.DriverInfoModel;
import irstit.transport.Drivers.DriversMainActivity;
import irstit.transport.Globals;
import irstit.transport.R;
import irstit.transport.ViewPager.MainPager;
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
                btn.startAnimation();
            }
        });
        btn.setOnClickListener((View v) -> {
            if (!Utils.getInstance(getContext()).hasInternetAccess() && !Utils.getInstance(getContext()).isOnline()) {
                Toast.makeText(getContext(), "لطفا دسترسی به اینترنت خود را بررسی کنید!", Toast.LENGTH_SHORT).show();
            } else {
                getDriverInfo();
                btn.startAnimation();
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {

    }

    private void getDriverInfo() {

        StringRequest getDriverInfo = new StringRequest(Request.Method.POST,
                Globals.APIURL + ((getArguments() != null && getArguments().getString("state").equals("ChangePass")) ? "/codenewphone" : "/validation"),
                response -> {

                    Log.e("getDriverInfoResponse", response + " |");

                    try {
                        JSONObject object = new JSONObject(response);

                        if ((getArguments() != null && getArguments().getString("state").equals("ChangePass"))) {

                            if (object.getString("status").equals("true")) {

                                Log.e("phone", getArguments().getString("phone") + " | " + " loh");
                                Log.e("phone", getArguments().getString("phone") + " | " +
                                        DBManager.getInstance(getContext()).updateDriverInfo(
                                                DBManager.getInstance(getContext()).getDriverInfo().getTelephone(), getArguments().getString("phone")));
                                startActivity(new Intent(getActivity(), DriversMainActivity.class));

                                // this variable is used for identifying person's role
                                CitizenLogin golobalVaribale = new CitizenLogin();
                                golobalVaribale.identifyingOfPerson =1;

                                getActivity().finish();
                            } else {
                                Toast.makeText(getContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                                btn.revertAnimation();
                            }

                            return;
                        }
                        if (object.getJSONObject("result").getString("status").equals("false")) {
                            btn.revertAnimation();
                            Toast.makeText(getContext(), object.getJSONObject("result").getString("message"), Toast.LENGTH_SHORT).show();
                        } else if (object.getJSONObject("result").getString("status").equals("true")) {
                            btn.revertAnimation();

                            DriverInfoModel info = new DriverInfoModel();
                            info.setName(object.getJSONObject("driverdata").getString("d_name"));
                            info.setFamily(object.getJSONObject("driverdata").getString("d_family"));
                            info.setParent(object.getJSONObject("driverdata").getString("d_parent"));
                            info.setNationalCode(Utils.getInstance(getContext()).DecodeData1(
                                    object.getJSONObject("driverdata").getString("d_nmc")));
                            info.setBirthCertificate(object.getJSONObject("driverdata").getString("d_passcode"));
                            info.setTelephone(Utils.getInstance(getContext()).DecodeData1(
                                    object.getJSONObject("driverdata").getString("d_tel")));
                            info.setPicture(object.getJSONObject("driverdata").getString("d_pic"));

                            Log.e("vehicle", object.getJSONObject("vehicledata").toString() + "|");

                            if (object.getJSONObject("vehicledata").length() != 0) {
                                info.setLineType(object.getJSONObject("vehicledata").getString("v_activitytype"));
                                info.setVehicleType(object.getJSONObject("vehicledata").getString("v_vhicletype"));

                                info.setVehicleCode(object.getJSONObject("vehicledata").getString("v_code"));

                                info.setVehicleModel(object.getJSONObject("vehicledata").getString("v_model"));
                                info.setRegisterDate(object.getJSONObject("vehicledata").getString("v_regtime"));

                                info.setVehiclePelak(object.getJSONObject("vehicledata").getString("v_plate"));

                                info.setOwner(object.getJSONObject("vehicledata").getString("driver_type"));
                                info.setIsTaxi(object.getJSONObject("vehicledata").getString("is_taxi"));
                                info.setOwnerId(object.getJSONObject("vehicledata").getString("is_owner"));
                            }

                            DBManager.getInstance(getContext()).setDriverInfo(info);

//                            startActivity(new Intent(getActivity(), DriversMainActivity.class));
                            startActivity(new Intent(getActivity(), MainPager.class));
                            getActivity().finish();

                        }
                    } catch (Exception e) {
                        btn.revertAnimation();
                        Log.e("getSmsError", e.toString() + " | ");
                        Toast.makeText(getActivity(), "خطایی رخ داد لطفا دوباره تلاش کنید", Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
            btn.revertAnimation();
            Log.e("getDriverInfoError", error.toString() + " |");
            btn.revertAnimation();
            Toast.makeText(getContext(), "خطایی رخ داد لطفا دوباره تلاش کنید", Toast.LENGTH_SHORT).show();
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                map.put("validationCode", otpView.getText().toString());
                map.put("phone", getArguments().getString("phone"));
                return map;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("token", "df837016d0fc7670f221197cd92439b5");
                return map;
            }
        };

        getDriverInfo.setRetryPolicy(new DefaultRetryPolicy(
                10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(getDriverInfo);

    }

}
