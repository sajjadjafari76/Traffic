package irstit.transport.Drivers;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import irstit.transport.AppController.AppController;
import irstit.transport.DataBase.DBManager;
import irstit.transport.DataModel.ReqVacationModel;
import irstit.transport.Globals;
import irstit.transport.R;
import irstit.transport.Views.CustomTextView;
import irstit.transport.Views.Utils;


public class VacationSearch extends Fragment {

    private RecyclerView navigation_Recycler;
    private MyNavigationAdapter adapter;
    private RelativeLayout Loading;
    private CustomTextView empty;
    private LinearLayout notConnectToInternet;
    private Button reTry;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vacation_search, container, false);

        EditText TextSearch = view.findViewById(R.id.RequestVacation_TextSearch);
        ImageView IconSearch = view.findViewById(R.id.RequestVacation_Search);
        empty = view.findViewById(R.id.VacationSearch_Empty);
        navigation_Recycler = view.findViewById(R.id.RequestVacation_Recycler);
        Loading = view.findViewById(R.id.VacationSearch_Loading);

        notConnectToInternet = view.findViewById(R.id.Delivery_Connectivity);
        notConnectToInternet=view.findViewById(R.id.Delivery_Connectivity);
        reTry = view.findViewById(R.id.Delivery_Btn);
        reTry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Loading.setVisibility(View.VISIBLE);
                criticalRequest();
            }
        });
        checkIfInternet();


        IconSearch.setOnClickListener(v -> {

        });
//        if(Te)

        TextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(DriversMainActivity.mData.isEmpty()){
                    TextSearch.setFocusable(false);
                    TextSearch.getText().clear();
                    Toast.makeText(getActivity(),"لیست خالی است !",Toast.LENGTH_LONG).show();
//                                        TextSearch.setText("");


                }
                 else {
                    adapter.getFilter().filter(TextSearch.getText().toString());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Loading.setVisibility(View.VISIBLE);
        criticalRequest();


        return view;
    }

    private class MyNavigationAdapter extends RecyclerView.Adapter<MyNavigationAdapter.MyCustomView> implements Filterable {

        List<ReqVacationModel> data;
        List<ReqVacationModel> dataFiltered;

        public MyNavigationAdapter(List<ReqVacationModel> data) {
            this.data = data;
            dataFiltered = data;
        }

        @Override
        public MyNavigationAdapter.MyCustomView onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyNavigationAdapter.MyCustomView(LayoutInflater
                    .from(getContext()).inflate(R.layout.layout_request_vacation, null));
        }

        @Override
        public void onBindViewHolder(MyNavigationAdapter.MyCustomView holder, final int position) {

            try {
                PersianCalendar fromCalendar = new PersianCalendar(
                        new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dataFiltered.get(position).getFromDate()).getTime());
                PersianCalendar toCalender = new PersianCalendar(
                        new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dataFiltered.get(position).getToDate()).getTime());

                holder.Code.setText(" کد رهگیری ".concat(dataFiltered.get(position).getCode()));
                holder.FromDate.setText(" از ".concat(fromCalendar.getPersianYear() + "/" + fromCalendar.getPersianMonth() + "/" + fromCalendar.getPersianDay()));
                holder.ToDate.setText(" تا ".concat(toCalender.getPersianYear() + "/" + toCalender.getPersianMonth() + "/" + toCalender.getPersianDay()));
                holder.Type.setText(dataFiltered.get(position).getVacationType());
                holder.Desc.setText(" توضیحات : "
                        .concat((dataFiltered.get(position).getDesc() == null) ? " " : dataFiltered.get(position).getDesc()));

                if (dataFiltered.get(position).getStatus().equals("0")) {
                    holder.Code.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.deep_orange_300));
                    holder.Print.setEnabled(false);
                } else if (dataFiltered.get(position).getStatus().equals("1")) {
                    holder.Code.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green_300));
                    holder.Print.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_print_));
                    holder.Print.setEnabled(true);
                } else if (dataFiltered.get(position).getStatus().equals("2")) {
                    holder.Code.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.red_A200));
                    holder.Print.setEnabled(false);
                    holder.Desc.setText(" پاسخ مدیریت : "
                            .concat((dataFiltered.get(position).getDesc() == null) ? "" : dataFiltered.get(position).getReason()));
                }

                holder.Print.setOnClickListener(v -> {

                    if (holder.Print.isEnabled()) {

                        FragmentTransaction transaction =
                                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null);
                        ReportDriverInfo info = new ReportDriverInfo();
                        Bundle bundle = new Bundle();
                        bundle.putString("id", dataFiltered.get(position).getCode());
                        info.setArguments(bundle);
                        transaction.replace(R.id.DriversMainActivity_Container, info);
                        transaction.commit();

                    }

                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return dataFiltered.size();
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {

                    Log.e("datafilterHabib00", data.toString());
                    String charString = constraint.toString();
                    if (charString.isEmpty()) {
                        dataFiltered = data;
                    } else {
                        List<ReqVacationModel> filteredList = new ArrayList<>();
                        for (ReqVacationModel row : data) {

                            // name match condition. this might differ depending on your requirement
                            // here we are looking for name or phone number match
                            if (row.getCode().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }

                        dataFiltered = filteredList;
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = dataFiltered;
                    return filterResults;

                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {

                    dataFiltered = (List<ReqVacationModel>) results.values;
                    Log.e("datafilterHabib", dataFiltered.toString());
                    notifyDataSetChanged();

                }
            };
        }

        class MyCustomView extends RecyclerView.ViewHolder {

            private TextView Code, FromDate, ToDate, Type, Desc;
            private CardView Root;
            private ImageView Print;

            public MyCustomView(View itemView) {
                super(itemView);
                Code = itemView.findViewById(R.id.RequestVacation_Code);
                FromDate = itemView.findViewById(R.id.RequestVacation_FromDate);
                ToDate = itemView.findViewById(R.id.RequestVacation_ToDate);
                Type = itemView.findViewById(R.id.RequestVacation_Type);
                Desc = itemView.findViewById(R.id.RequestVacation_Desc);
                Root = itemView.findViewById(R.id.RequestVacation_Root);
                Print = itemView.findViewById(R.id.RequestVacation_Print);

            }
        }
    }


    private void criticalRequest() {
        StringRequest getPhoneRequest = new StringRequest(Request.Method.POST, Globals.APIURL + "/listleave",
                response -> {
                    Log.e("ListLeavesResponse", response + " |");

                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.getString("status").equals("true")) {

                            JSONArray array = object.getJSONArray("leaves");

                            if (array.length() == 0) {
                                Loading.setVisibility(View.GONE);
                                navigation_Recycler.setVisibility(View.VISIBLE);
                                empty.setVisibility(View.VISIBLE);
                            } else {
                                DriversMainActivity.mData.clear();

                                for (int i = 0; i < array.length(); i++) {

                                    JSONObject myObject = array.getJSONObject(i);
                                    ReqVacationModel model = new ReqVacationModel();
                                    model.setCode(myObject.getString("l_drivercode"));
                                    model.setFromDate(myObject.getString("l_startdate"));
                                    model.setToDate(myObject.getString("l_enddate"));
                                    model.setVacationType(myObject.getString("l_type"));
                                    model.setDesc(myObject.getString("l_text"));
                                    model.setStatus(myObject.getString("l_status"));
                                    model.setReason(myObject.getString("l_reson"));

                                    DriversMainActivity.mData.add(model);
                                }

                                Loading.setVisibility(View.GONE);
                                navigation_Recycler.setVisibility(View.VISIBLE);
                                empty.setVisibility(View.GONE);

                                adapter = new MyNavigationAdapter(DriversMainActivity.mData);
                                navigation_Recycler.setLayoutManager(new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false));
                                navigation_Recycler.setAdapter(adapter);

                            }
                        } else if (object.getString("status").equals("false")) {
                            Loading.setVisibility(View.GONE);
                            navigation_Recycler.setVisibility(View.VISIBLE);
                            empty.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        Log.e("ListLeavesError1", e.toString() + " |");
                        Loading.setVisibility(View.GONE);
                        navigation_Recycler.setVisibility(View.VISIBLE);
                        empty.setVisibility(View.GONE);
                    }
                },
                error -> {
                    Log.e("ListLeavesError2", error.toString() + " |");
                    Loading.setVisibility(View.GONE);
                    navigation_Recycler.setVisibility(View.VISIBLE);
                    empty.setVisibility(View.GONE);
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("phone", DBManager.getInstance(getContext()).getDriverInfo().getTelephone());
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


    // addes methods by HabibUllah

    private void checkIfInternet() {

        if (Utils.getInstance(getActivity()).isOnline())
            notConnectToInternet.setVisibility(View.GONE);
        else {
            notConnectToInternet.setVisibility(View.VISIBLE);

        }


    }

}
