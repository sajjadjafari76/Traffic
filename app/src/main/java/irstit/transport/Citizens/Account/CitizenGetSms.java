package irstit.transport.Citizens.Account;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.marozzi.roundbutton.RoundButton;
import com.mukesh.OtpView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import irstit.transport.AppController.AppController;
import irstit.transport.DataBase.DBManager;
import irstit.transport.DataModel.DriverInfoModel;
import irstit.transport.Drivers.DriversMainActivity;
import irstit.transport.Drivers.Login.GetSms;
import irstit.transport.Globals;
import irstit.transport.R;
import irstit.transport.Views.Utils;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CitizenGetSms.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class CitizenGetSms extends Fragment {

    private OnFragmentInteractionListener mListener;

    public CitizenGetSms() {
        // Required empty public constructor
    }

    private OtpView otpView;
    private RoundButton btn;
    private TextView timerText;
    private ImageView synchornize;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_citizen_get_sms, container, false);

        timerText = view.findViewById(R.id.CitizenGetSmsTimer);
        otpView = view.findViewById(R.id.CitizenGetSms_Otp);
        btn = view.findViewById(R.id.CitizenGetSms_Btn);
     // /*

        synchornize =  view.findViewById(R.id.resendNumber);
        synchornize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                synchornize.setVisibility(View.GONE);
                counter();
                GetPhoneRequest();
            }
        });
        //*/
        counter();
        otpView.setOtpCompletionListener((String s) -> {
            if (!Utils.getInstance(getContext()).hasInternetAccess() && !Utils.getInstance(getContext()).isOnline()) {
                Toast.makeText(getContext(), "لطفا دسترسی به اینترنت خود را بررسی کنید!", Toast.LENGTH_SHORT).show();
            } else {
                getCitizenInfo();
                btn.startAnimation();
            }
        });
        btn.setOnClickListener((View v) -> {
            if (!Utils.getInstance(getContext()).hasInternetAccess() && !Utils.getInstance(getContext()).isOnline()) {
                Toast.makeText(getContext(), "لطفا دسترسی به اینترنت خود را بررسی کنید!", Toast.LENGTH_SHORT).show();
            } else {

                getCitizenInfo();
                btn.startAnimation();
            }

        });


        return view;

    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private void getCitizenInfo() {

        StringRequest getDriverInfo = new StringRequest(Request.Method.POST,
                Globals.APIURL + "/logincitizen",
                response -> {

                    Log.e("getDriverInfoResponse", response + " |");

                    try {
                        JSONObject object = new JSONObject(response);

                        if (object.getString("status").equals("true")) {
                            btn.revertAnimation();

                            FragmentTransaction transaction = getActivity()
                                    .getSupportFragmentManager().beginTransaction();  //.addToBackStack("GetSms")
                            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                            Bundle bundle = new Bundle();
//                                bundle.putString("phone", phone.getText().toString());
//                                if ((getArguments() != null && getArguments().getString("state").equals("ChangePass"))) {
//                                    bundle.putString("state", "ChangePass");
//                                } else {
                            bundle.putString("phone", getArguments().getString("phone"));
//                                }
                            CitizenGetInfo getSms = new CitizenGetInfo();
                            getSms.setArguments(bundle);
                            transaction.replace(R.id.CitizenActivity_Frame, getSms);
                            transaction.commit();

                          //  transaction.remove(getActivity().getSupportFragmentManager().getFragments().get(0));



                        }
                    } catch (Exception e) {
                        btn.revertAnimation();
                        Log.e("getSmsError", e.toString() + " | ");
                        Toast.makeText(getActivity(), "خطایی رخ داد لطفا دوباره تلاش کنید", Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
            btn.revertAnimation();
            Log.e("getDriverInfoError", error.toString() + " |");
            Toast.makeText(getContext(), "خطایی رخ داد لطفا دوباره تلاش کنید", Toast.LENGTH_SHORT).show();
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                map.put("verifycode", otpView.getText().toString());
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


    public  void counter() {

        new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long l) {
                Log.e("clickOnAnimation0","clickOnAnimationDOne0");


                int second  = (int)l/1000;
                timerText.setText(" زمان باقیمانده   "+second);
                Log.e("clickOnAnimation1","clickOnAnimationDOne1");
                //  btn.startAnimation();
            }

            @Override
            public void onFinish() {

               // Fragment fragmentA = fragmentManager.findFragmentByTag("frag1");

                if (getActivity() != null) {
                    Toast.makeText(getActivity(), "لطفا دوباره امتحان کنید", Toast.LENGTH_LONG).show();
                }
//                else if(getActivity().getSupportFragmentManager().executePendingTransactions()){
//
//                    }
                timerText.setText("0");
                timerText.setText("");
                synchornize.setVisibility(View.VISIBLE);


                //  btn.stopAnimation();
                // btn.revertAnimation();

            }
        }.start();
    }

  //  getArguments().getString("phone")

    private void GetPhoneRequest() {
        StringRequest getPhoneRequest = new StringRequest(Request.Method.POST,
                Globals.APIURL + "/regcitizen",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("regCitizenResponse", response + " |");

                        try {
                            JSONObject object = new JSONObject(response);

                            if (object.getString("status").equals("true")) {
                             //   sendInfo.revertAnimation();

                                // after getting phone number we must going to GetSms Class
                                FragmentTransaction transaction = getActivity()
                                        .getSupportFragmentManager().beginTransaction();  //.addToBackStack("GetSms")
                                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                                Bundle bundle = new Bundle();
//                                bundle.putString("phone", phone.getText().toString());
//                                if ((getArguments() != null && getArguments().getString("state").equals("ChangePass"))) {
//                                    bundle.putString("state", "ChangePass");
//                                } else {
                                bundle.putString("phone", getArguments().getString("phone"));
//                                }
                                CitizenGetSms getSms = new CitizenGetSms();
                                getSms.setArguments(bundle);
                                transaction.replace(R.id.CitizenActivity_Frame, getSms);
                                transaction.commit();


                            } else if (object.getString("status").equals("false")) {
                                Toast.makeText(getContext(), object.getString("message")+" لطفا وارد حساب خود شوید ", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getActivity(),CitizenLogin.class));

//                                sendInfo.revertAnimation();
                            }
                        } catch (Exception e) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("regCitizenError", error.toString() + " |");

//                sendInfo.revertAnimation();
            }

        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
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

        getPhoneRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(getPhoneRequest);

    }

}
