package irstit.transport.Drivers;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import irstit.transport.AppController.AppController;
import irstit.transport.DataModel.ReqVacationModel;
import irstit.transport.Globals;
import irstit.transport.R;


public class VacationSearch extends Fragment {

    private RecyclerView navigation_Recycler;
    private MyNavigationAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vacation_search, container, false);

        EditText TextSearch = view.findViewById(R.id.RequestVacation_TextSearch);
        ImageView IconSearch = view.findViewById(R.id.RequestVacation_Search);
        navigation_Recycler = view.findViewById(R.id.RequestVacation_Recycler);


        IconSearch.setOnClickListener(v -> {

        });

        TextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(TextSearch.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        criticalRequest();
        return view;
    }

    private class MyNavigationAdapter extends RecyclerView.Adapter<MyNavigationAdapter.MyCustomView>  implements Filterable {

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

            holder.Code.setText( " کد رهگیری ".concat(dataFiltered.get(position).getCode()));
            holder.FromDate.setText(" از ".concat(dataFiltered.get(position).getFromDate()));
            holder.ToDate.setText(" تا ".concat(dataFiltered.get(position).getToDate()));
            holder.Type.setText(dataFiltered.get(position).getVacationType());
            holder.Desc.setText(" توضیحات : ".concat(dataFiltered.get(position).getDesc()));

            if (dataFiltered.get(position).getStatus().equals("0")) {
                holder.Code.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.deep_orange_300));
            }else if (dataFiltered.get(position).getStatus().equals("1")) {
                holder.Code.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.green_300));
            }else if (dataFiltered.get(position).getStatus().equals("2")) {
                holder.Code.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.red_A200));
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
                protected FilterResults performFiltering (CharSequence constraint) {

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
                protected void publishResults (CharSequence constraint, FilterResults results) {

                    dataFiltered = (List<ReqVacationModel>) results.values;
                    notifyDataSetChanged();

                }
            };
        }

        class MyCustomView extends RecyclerView.ViewHolder {

            private TextView Code, FromDate, ToDate, Type, Desc;
            private CardView Root;

            public MyCustomView(View itemView) {
                super(itemView);
                Code = itemView.findViewById(R.id.RequestVacation_Code);
                FromDate = itemView.findViewById(R.id.RequestVacation_FromDate);
                ToDate = itemView.findViewById(R.id.RequestVacation_ToDate);
                Type = itemView.findViewById(R.id.RequestVacation_Type);
                Desc = itemView.findViewById(R.id.RequestVacation_Desc);
                Root = itemView.findViewById(R.id.RequestVacation_Root);

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

                            }else {
                                DriversMainActivity.mData.clear();

                                for (int i = 0 ; i < array.length() ; i++) {

                                    JSONObject myObject = array.getJSONObject(i);
                                    ReqVacationModel model = new ReqVacationModel();
                                    model.setCode(myObject.getString("l_drivercode"));
                                    model.setFromDate(myObject.getString("l_startdate"));
                                    model.setToDate(myObject.getString("l_enddate"));
                                    model.setVacationType(myObject.getString("l_type"));
                                    model.setDesc(myObject.getString("l_text"));
                                    model.setStatus(myObject.getString("l_status"));

                                    DriversMainActivity.mData.add(model);
                                }

                                adapter = new MyNavigationAdapter(DriversMainActivity.mData);
                                navigation_Recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                                navigation_Recycler.setAdapter(adapter);

                            }
                        } else if (object.getString("status").equals("false")) {

                        }
                    } catch (Exception e) {
                        Log.e("ListLeavesError1", e.toString() + " |");

                    }
                },
                error -> {
                    Log.e("ListLeavesError2", error.toString() + " |");

                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("phone", "9190467144");
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


}
