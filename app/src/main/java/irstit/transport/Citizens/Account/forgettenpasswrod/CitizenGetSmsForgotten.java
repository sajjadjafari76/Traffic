package irstit.transport.Citizens.Account.forgettenpasswrod;

import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.marozzi.roundbutton.RoundButton;
import com.mukesh.OtpView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import irstit.transport.AppController.AppController;
import irstit.transport.Citizens.Account.CitizenGetInfo;
import irstit.transport.Globals;
import irstit.transport.R;
import irstit.transport.Views.Utils;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CitizenGetSmsForgotten.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class CitizenGetSmsForgotten extends Fragment {

    private OnFragmentInteractionListener mListener;

    public CitizenGetSmsForgotten() {
        // Required empty public constructor
    }

    private OtpView otpView;
    private RoundButton btn;
    TextView timerText;
    //Thread runOnUiThread ;


    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_citizen_get_sms, container, false);


        otpView = view.findViewById(R.id.CitizenGetSms_Otp);
        btn = view.findViewById(R.id.CitizenGetSms_Btn);
        timerText = view.findViewById(R.id.timer);
        conter();
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

//    private  void timerFunction (){
//
//        Thread t = new Thread() {
//
//            @Override
//            public void run() {
//                try {
//                    while (!isInterrupted()) {
//                        Thread.sleep(1000);
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//
//                           /*     Calendar c = Calendar.getInstance();
//
//                                int hours = c.get(Calendar.HOUR_OF_DAY);
//                                int minutes = c.get(Calendar.MINUTE);
//                                int seconds = c.get(Calendar.SECOND);
//
//                                String curTime = String.format("%02d  %02d  %02d", hours, minutes, seconds);
//                          */
//                                int countertime=0;
//                               // String stringcounter = countertime+"";
//                                countertime++;
//
//                                timerText.setText(countertime+""); //change clock to your textview
//                            }
//                        });
//                    }
//                } catch (InterruptedException e) {
//                }
//            }
//        };
//
//        t.start();
//
//    }

//
//    private void runOnUiThread(Runnable runnable) {
//         timerFunction();
//    }


    public  void conter() {

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
                ///
                Toast.makeText(getActivity(),"لطفا دوباره امتحان کنید",Toast.LENGTH_LONG).show();
                timerText.setText("0");
                timerText.setText("");
              //  btn.stopAnimation();
               // btn.revertAnimation();

            }
        }.start();
    }


    private void getCitizenInfo() {
        Log.e("step0","works");
        Log.e("phoneFromGetSmsClass2",  getArguments().getString("phone"));
        Log.e("verfied_send_Code",otpView.getText().toString());
        StringRequest getDriverInfo = new StringRequest(Request.Method.POST,
                Globals.APIURL + "/forgetcitizensms",
                response -> {

                    Log.e("ForgetCitizenSms", response + " |");

                    try {
                        JSONObject object = new JSONObject(response);
                        Log.e("step1","works");

                        if (object.getString("status").equals("true")) {
                            btn.revertAnimation();

                            Log.e("step2","works");
                            FragmentTransaction transaction = getActivity()
                                    .getSupportFragmentManager().beginTransaction().addToBackStack("GetSms");
                            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                            Bundle bundle = new Bundle();
//                                bundle.putString("phone", phone.getText().toString());
//                                if ((getArguments() != null && getArguments().getString("state").equals("ChangePass"))) {
//                                    bundle.putString("state", "ChangePass");
//                                } else {
                            bundle.putString("phone", getArguments().getString("phone"));
//                                }
                            Log.e("phoneFromGetSmsClass2",  getArguments().getString("phone"));

                            CitizenGetInfoForgotten getSms = new CitizenGetInfoForgotten();
                            getSms.setArguments(bundle);
                            transaction.replace(R.id.CitizenActivity_Frame, getSms);
                            transaction.commit();


                        }
                    } catch (Exception e) {
                         btn.revertAnimation();
                        Log.e("getSmsError", e.toString() + " | ");
                        Toast.makeText(getActivity(), "خطایی رخ داد لطفا دوباره تلاش کنید", Toast.LENGTH_SHORT).show();
                        //btn.stopAnimation();

                    }
                }, error -> {
            btn.revertAnimation();
            Log.e("ForgetCitizenSmsError", error.toString() + " |");
            Toast.makeText(getContext(), "خطایی رخ داد لطفا دوباره تلاش کنید", Toast.LENGTH_SHORT).show();
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                Log.e("step3",otpView.getText().toString());
                Log.e("step4",getArguments().getString("phone"));
                map.put("phone", getArguments().getString("phone"));
                map.put("verifycode", otpView.getText().toString());
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
