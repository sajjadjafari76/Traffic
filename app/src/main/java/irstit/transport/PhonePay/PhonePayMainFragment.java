package irstit.transport.PhonePay;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import irstit.transport.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhonePayMainFragment extends Fragment {


    public PhonePayMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_phone_pay_main, container, false);
    }

}
