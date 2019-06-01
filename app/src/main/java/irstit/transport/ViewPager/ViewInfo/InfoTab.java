package irstit.transport.ViewPager.ViewInfo;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import irstit.transport.DataModel.SpinnerModel;
import irstit.transport.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoTab extends Fragment {


    public InfoTab() {
        // Required empty public constructor
    }

    private String type = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_info_tab, container, false);

        Spinner Category = view.findViewById(R.id.ViewInfo_Category);

        spinnerAdapter adapter = new spinnerAdapter(getContext(), R.layout.layout_custom_spinner);

        for (int i = 0 ; i < getCategory().size() ; i++) {
            adapter.add(getCategory().get(i).getName());
        }
        adapter.add("یک موضوع را انتخاب کنید!");
        Category.setAdapter(adapter);
        Category.setSelection(adapter.getCount());
        Category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position > (getCategory().size() - 1)) {

                }else {
                    type = String.valueOf(getCategory().get(position).getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
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


    private List<SpinnerModel> getCategory() {
        List<SpinnerModel> data = new ArrayList<>();

        for (int i = 0 ; i < 6 ; i++) {
            switch (i) {

                case 0:
                    SpinnerModel model = new SpinnerModel();
                    model.setId(5154);
                    model.setName("خودروهای خطی");
                    data.add(model);
                    break;
                case 1:
                    SpinnerModel model2 = new SpinnerModel();
                    model2.setId(5164);
                    model2.setName("روز بازار");
                    data.add(model2);
                    break;
                case 2:
                    SpinnerModel model3 = new SpinnerModel();
                    model3.setId(6164);
                    model3.setName("خودرو های دیزلی");
                    data.add(model3);
                    break;
                case 3:
                    SpinnerModel model4 = new SpinnerModel();
                    model4.setId(6214);
                    model4.setName("ترمینال");
                    data.add(model4);
                    break;
                case 4:
                    SpinnerModel model5 = new SpinnerModel();
                    model5.setId(7214);
                    model5.setName("CNG");
                    data.add(model5);
                    break;
                case 5:
                    SpinnerModel model6 = new SpinnerModel();
                    model6.setId(7214);
                    model6.setName("معاینه فنی");
                    data.add(model6);
                    break;
                case 6:
                    SpinnerModel model7 = new SpinnerModel();
                    model7.setId(7214);
                    model7.setName("پروژه های در دست اقدام");
                    data.add(model7);
                    break;
                case 7:
                    SpinnerModel model8 = new SpinnerModel();
                    model8.setId(7214);
                    model8.setName("پایانه مسافرری");
                    data.add(model8);
                    break;
            }
        }
        return data;
    }



}
