package irstit.transport.Drivers;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.koushikdutta.async.http.body.JSONObjectBody;
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.feeeei.circleseekbar.CircleSeekBar;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import irstit.transport.AppController.AppController;
import irstit.transport.Citizens.SearchObject;
import irstit.transport.DataBase.DBManager;
import irstit.transport.DataModel.NewsModel;
import irstit.transport.DataModel.SpinnerModel;
import irstit.transport.Globals;
import irstit.transport.MainActivity;
import irstit.transport.MainPage;
import irstit.transport.R;
import irstit.transport.Views.CFProvider;

import static android.content.Context.MODE_PRIVATE;

public class RequestVacation extends Fragment implements TimePickerDialog.OnTimeSetListener {


    private PersianDatePickerDialog picker;
    private String StartDate, EndDate;
    private TextView startDate, endDate, startTime, endTime, Remaining, Used, Entire, TextPersent, Status;
    private PersianCalendar StartDateCalender = new PersianCalendar(), EndDateCalender = new PersianCalendar();
    private int status = 0;
    private spinnerAdapter adapter;
    private String type;
    private RelativeLayout Loading;
    private EditText destination;
    private CircleSeekBar SeekBar;
    private LinearLayout Form, Btn;
    Context context;
    SharedPreferences shared;
    SharedPreferences.Editor editor;
    private RelativeLayout warning;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request_vacation, container, false);
        shared = getActivity().getSharedPreferences("complaint", MODE_PRIVATE);
        editor = shared.edit();
//        AnyChartView anyChartView = view.findViewById(R.id.RequestVacation_Chart);
        Button btn = view.findViewById(R.id.RequestVacation_Btn);
        Spinner Category = view.findViewById(R.id.RequestVacation_Category);
        destination = view.findViewById(R.id.RequestVacation_Destination);
        startDate = view.findViewById(R.id.RequestVacation_StartDate);
        endDate = view.findViewById(R.id.RequestVacation_EndDate);
        startTime = view.findViewById(R.id.RequestVacation_StartTime);
        endTime = view.findViewById(R.id.RequestVacation_EndTime);
        Loading = view.findViewById(R.id.RequestVacation_Loading);
        TextPersent = view.findViewById(R.id.RequestVacation_TextPersent);
        SeekBar = view.findViewById(R.id.RequestVacation_SeekBar);
        Status = view.findViewById(R.id.RequestVacation_Status);
        Btn = view.findViewById(R.id.RequestVacation_Btn1);
        Form = view.findViewById(R.id.RequestVacation_Form);
        warning = view.findViewById(R.id.warning);

        Remaining = view.findViewById(R.id.RequestVacation_Remaining);
        Used = view.findViewById(R.id.RequestVacation_Used);
        Entire = view.findViewById(R.id.RequestVacation_Entire);

        Log.e("lojhgfdsa", getArguments().getString("dataVacation") + " |");

        //  if (getArguments().getString("dataVacation") != null &&
        //         !getArguments().getString("dataVacation").isEmpty()) {

        if (shared.getString("canleave", "false").equals("false")) {
            Status.setVisibility(View.VISIBLE);
            Btn.setVisibility(View.GONE);
            Form.setVisibility(View.GONE);
        } else {
            Status.setVisibility(View.GONE);
            Btn.setVisibility(View.VISIBLE);
            Form.setVisibility(View.VISIBLE);
        }
        getNews();
//        try {
//
////            getNews();
//            SharedPreferences sh = this.getActivity().getSharedPreferences("complaint", MODE_PRIVATE);
////            if(sh.toString().equals("-1"))
////                Log.e("compliantArray",sh.toString());
//            String Jso = sh.getString("complaintArray", "-1");
//
//            JSONObject object = new JSONObject(Jso);
//            JSONObject allOffTimes = new JSONObject(object.getString("fulltime"));
//
//            Log.e("fulltimefromhere", allOffTimes.toString());
//
//            //getArguments().getString("dataVacation")
//
////                Log.e("canleave" , object.getString("canleave") + " | ");
//            if (allOffTimes.getString("canleave").equals("false")) {
//                Status.setVisibility(View.VISIBLE);
//                Btn.setVisibility(View.GONE);
//                Form.setVisibility(View.GONE);
//            }else {
//                Status.setVisibility(View.GONE);
//                Btn.setVisibility(View.VISIBLE);
//                Form.setVisibility(View.VISIBLE);
//            }
//
//
//            Used.setText(allOffTimes.getString("timeuse"));
//            Remaining.setText(allOffTimes.getString("havetime"));
//            Entire.setText(allOffTimes.getString("alltime"));
//
//            SeekBar.setCurProcess(Integer.parseInt(allOffTimes.getString("timeuse")));
//            SeekBar.setMaxProcess(Integer.parseInt(allOffTimes.getString("alltime")));
//
//            int persent = (Integer.parseInt(allOffTimes.getString("timeuse")) * 100) /
//                    Integer.parseInt(allOffTimes.getString("alltime"));
//
//            TextPersent.setText(persent + "%");
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e("fulltimefromhere", "error from RequestVaction.java");
//
//        }


        //     }

        startDate.setOnClickListener(v -> {
            configureStartDate();
        });
        endDate.setOnClickListener(v -> {
            configureEndDate();
        });
        startTime.setOnClickListener(v -> {
            TimePickerDialog dialog = TimePickerDialog.newInstance(this, 0, 0, true);
            dialog.show(getActivity().getFragmentManager(), "start");
            status = 1;
        });
        endTime.setOnClickListener(v -> {
            TimePickerDialog dialog = TimePickerDialog.newInstance(this, 0, 0, true);
            dialog.show(getActivity().getFragmentManager(), "end");
            status = 2;
        });

        btn.setOnClickListener(v -> {
            if (startDate.getText().toString().isEmpty() || endDate.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "تاریخ نمیتواند خالی باشد", Toast.LENGTH_SHORT).show();
            } else if (EndDateCalender.before(StartDateCalender)) {
                Toast.makeText(getContext(), "تاریخ وارد شده اشتباه است", Toast.LENGTH_SHORT).show();
            } else if (startTime.getText().toString().isEmpty() || endTime.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "ساعات نمیتواند خالی باشد", Toast.LENGTH_SHORT).show();
            } else if (destination.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "مقصد نمیتواند خالی باشد", Toast.LENGTH_SHORT).show();
            } else {
                getNews(startDate.getText().toString(),
                        endDate.getText().toString(),
                        startTime.getText().toString(),
                        endTime.getText().toString(),
                        destination.getText().toString(), type, "");
                Loading.setVisibility(View.VISIBLE);


            }
        });
//        anyChartView.setProgressBar(findViewById(R.id.progress_bar));

//        Pie pie = AnyChart.pie();

//        pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
//            @Override
//            public void onClick(Event event) {
//                Toast.makeText(getContext(), event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
//            }
//        });


//        List<DataEntry> data = new ArrayList<>();
//        data.add(new ValueDataEntry("استفاده شده", 6371664));
//        data.add(new ValueDataEntry("باقیمانده", 789622));
//
//        pie.data(data);

//        pie.title("Fruits imported in 2015 (in kg)");

//        pie.labels().position("outside");

//        pie.legend().title().enabled(true);
//        pie.legend().title()
//                .text("Retail channels")
//                .padding(0d, 0d, 10d, 0d);

//        pie.legend()
//                .position("center-bottom")
//                .itemsLayout(LegendLayout.HORIZONTAL)
//                .align(Align.CENTER);

//        anyChartView.setChart(pie);


        adapter = new spinnerAdapter(getActivity(), R.layout.layout_custom_spinner);

        for (int i = 0; i < getCategory().size(); i++) {
            adapter.add(getCategory().get(i).getName());
        }
        adapter.add("یک موضوع را انتخاب کنید!");
        Category.setAdapter(adapter);
        Category.setSelection(adapter.getCount());
        Category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position > (getCategory().size() - 1)) {

                } else {
                    type = String.valueOf(getCategory().get(position).getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }


    private List<SpinnerModel> getCategory() {
        List<SpinnerModel> data = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            switch (i) {

                case 0:
                    SpinnerModel model = new SpinnerModel();
                    model.setId(4434);
                    model.setName("استحقاقی");
                    data.add(model);
                    break;
                case 1:
                    SpinnerModel model2 = new SpinnerModel();
                    model2.setId(4435);
                    model2.setName("استعلاجی");
                    data.add(model2);
                    break;
            }
        }
        return data;
    }


    public void configureStartDate() {
        PersianCalendar calendar = new PersianCalendar(System.currentTimeMillis());
        picker = new PersianDatePickerDialog(getActivity())
                .setPositiveButtonString("باشه")
                .setNegativeButton("بیخیال")
//                .setTodayButton("امروز")
                .setTodayButtonVisible(true)
                .setInitDate(calendar)
                .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                .setMinYear(1300)
                .setActionTextColor(Color.GRAY)
                .setTypeFace(CFProvider.getIRANIANSANS(getContext()))
                .setListener(new Listener() {
                    @Override
                    public void onDateSelected(PersianCalendar persianCalendar) {
                        StartDate = persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay();
                        startDate.setText(StartDate);
                        StartDateCalender.setPersianDate(persianCalendar.getPersianYear(), persianCalendar.getPersianMonth(), persianCalendar.getPersianDay());
                    }

                    @Override
                    public void onDismissed() {

                    }
                });
        picker.show();

    }

    public void configureEndDate() {
        PersianCalendar calendar = new PersianCalendar(System.currentTimeMillis());
        picker = new PersianDatePickerDialog(getActivity())
                .setPositiveButtonString("باشه")
                .setNegativeButton("بیخیال")
//                .setTodayButton("امروز")
                .setTodayButtonVisible(true)
                .setInitDate(calendar)
                .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                .setMinYear(1300)
                .setActionTextColor(Color.GRAY)
                .setTypeFace(CFProvider.getIRANIANSANS(getContext()))
                .setListener(new Listener() {
                    @Override
                    public void onDateSelected(PersianCalendar persianCalendar) {
                        EndDate = persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay();
                        endDate.setText(EndDate);
                        EndDateCalender.setPersianDate(persianCalendar.getPersianYear(), persianCalendar.getPersianMonth(), persianCalendar.getPersianDay());
                    }

                    @Override
                    public void onDismissed() {

                    }
                });
        picker.show();

    }


    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        Log.e("time", status + " | " + hourOfDay + " | " + minute);
        String hour = ((hourOfDay == 0) ? "00" : String.valueOf(hourOfDay));
        String minutes = ((minute == 0) ? "00" : String.valueOf(minute));
        Log.e("time1", status + " | " + hour + " | " + minutes);
        if (status == 1) {
            startTime.setText(hour + ":" + minutes);
        } else if (status == 2) {
            endTime.setText(hour + ":" + minutes);
        }
    }


    private void getNews(String startDate, String endDate, String startTime, String endTime, String destination, String Type, String text) {
        Log.e("sajjadddd", "ojoj");

        StringRequest getPhoneRequest = new StringRequest(Request.Method.POST, Globals.APIURL + "/addleave",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("NewsResponse", response + " |");
                        Log.e("sajjadaaaaa", response + " |");
                        Loading.setVisibility(View.GONE);

                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.getString("status").equals("true")) {
                                Clear();
                                Toast.makeText(RequestVacation.this.getContext(), "درخواست مرخصی شما با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();
                                editor.putString("canleave", "false").apply();

                                if (shared.getString("canleave", "false").equals("false")) {
                                    Status.setVisibility(View.VISIBLE);
                                    Btn.setVisibility(View.GONE);
                                    Form.setVisibility(View.GONE);
                                } else {
                                    Status.setVisibility(View.GONE);
                                    Btn.setVisibility(View.VISIBLE);
                                    Form.setVisibility(View.VISIBLE);
                                }
                            } else if (object.getString("status").equals("false") && object.has("statusLeave")) {
                            } else if (object.getString("status").equals("false")) {
                                Toast.makeText(RequestVacation.this.getContext(), "وضعیت اخرین مرخصی ارسالی هنوز مشخص نشده است لطفا تا بررسی مدیر صبر کنید", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("ListLeavesError1", e.toString() + " |");
                            Toast.makeText(RequestVacation.this.getContext(), "درخواست مرخصی با مشکل مواجه شد", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                error -> {
                    Log.e("ListLeavesError2", error.toString() + " |");
                    Log.e("sajjadaaaaa1", error.toString() + " |");
                    Loading.setVisibility(View.GONE);
                    Toast.makeText(RequestVacation.this.getContext(), "درخواست مرخصی با مشکل مواجه شد", Toast.LENGTH_SHORT).show();
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

//                Log.e("loglog", startDate + " | " + endDate + " | " + startTime + " | " + endTime + " | " + destination + " | " + type + " | " + DBManager.getInstance(getContext()).getDriverInfo().getTelephone());

                Map<String, String> map = new HashMap<>();
                map.put("startdate", startDate);
                map.put("enddate", endDate);
                map.put("starttime", startTime);
                map.put("endtime", endTime);
                map.put("phone", DBManager.getInstance(getContext()).getDriverInfo().getTelephone());
                map.put("destination", destination);
                map.put("type", type);
                map.put("text", text);
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


    public class spinnerAdapter extends ArrayAdapter<String> {

        spinnerAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount() {

            // TODO Auto-generated method stub
            int count = super.getCount();

            return count > 0 ? count - 1 : count;

        }
    }

    void Clear() {
        startDate.setText("");
        endDate.setText("");
        startTime.setText("");
        endTime.setText("");
        destination.setText("");
    }


    private void getNews() {
        String internetStatus = "true";

        StringRequest getPhoneRequest = new StringRequest(Request.Method.POST, Globals.APIURL + "/timing",
                response -> {
                    Log.e("NewsResponse", response + " |");
                    getlog(response);
                    try {
                        JSONObject object = new JSONObject(response);
                        warning.setVisibility(View.GONE);

                        if (object.getString("status").equals("true")) {

//

//                            editor.commit();

                            try {
                                JSONObject object1 = object.getJSONObject("fulltime");

                                Log.e("NewsResponse", object1 + " ||||");
////            getNews();
//                                SharedPreferences sh = this.getActivity().getSharedPreferences("complaint", MODE_PRIVATE);
////            if(sh.toString().equals("-1"))
////                Log.e("compliantArray",sh.toString());
//                                String Jso = sh.getString("complaintArray", "-1");
//
//                                JSONObject object = new JSONObject(Jso);
//                                JSONObject allOffTimes = new JSONObject(object.getString("fulltime"));
//
//                                Log.e("fulltimefromhere", allOffTimes.toString());

                                //getArguments().getString("dataVacation")

//                Log.e("canleave" , object.getString("canleave") + " | ");


                                editor.putString("canleave", object1.getString("canleave")).apply();

                                if (shared.getString("canleave", "false").equals("false")) {
                                    Status.setVisibility(View.VISIBLE);
                                    Btn.setVisibility(View.GONE);
                                    Form.setVisibility(View.GONE);
                                } else {
                                    Status.setVisibility(View.GONE);
                                    Btn.setVisibility(View.VISIBLE);
                                    Form.setVisibility(View.VISIBLE);
                                }
                                Used.setText(object1.getString("timeuse"));
                                Remaining.setText(object1.getString("havetime"));
                                Entire.setText(object1.getString("alltime"));

                                SeekBar.setCurProcess(Integer.parseInt(object1.getString("timeuse")));
                                SeekBar.setMaxProcess(Integer.parseInt(object1.getString("alltime")));

                                int persent = (Integer.parseInt(object1.getString("timeuse")) * 100) /
                                        Integer.parseInt(object1.getString("alltime"));

                                if (persent >= 100){

                                    warning.setVisibility(View.VISIBLE);
                                }

                                TextPersent.setText(persent + "%");


                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e("fulltimefromhere", "error from RequestVaction.java");

                            }

                        }

                    } catch (Exception e) {
                        Log.e("ListLeavesError1", e.toString() + " |");

                    }
                },
                error -> {
                    Log.e("ListLeavesError2", error.toString() + " |");


                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("phone", DBManager.getInstance(getContext()).getDriverInfo().getTelephone());
//                    map.put("phone", " 09033433776");
                map.put("TOKEN", "df837016d0fc7670f221197cd92439b5");
                Log.v("FromSplash", map.get("phone"));

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
                5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(getPhoneRequest);


    }

    public void getlog(String veryLongString) {
        int maxLogSize = 1500;
        for (int i = 0; i <= veryLongString.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i + 1) * maxLogSize;
            end = end > veryLongString.length() ? veryLongString.length() : end;
            Log.e("ShowAssChild1", veryLongString.substring(start, end));
        }

        Log.e("work", "yessssssssss");
    }


}
