package irstit.transport.ViewPager;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import irstit.transport.ForDisel;
import irstit.transport.R;
import irstit.transport.ViewPager.ViewInfo.ViewInfo;
import irstit.transport.ViewPager.ViewInfo.ViewInfoTerminal;

/**
 * A simple {@link Fragment} subclass.
 */
public class Page_1 extends Fragment {


    public Page_1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_page_1, container, false);

        LinearLayout lineCar = view.findViewById(R.id.Page1_LineCar);
        LinearLayout roozbazar = view.findViewById(R.id.Page1_RoozBazar);
        LinearLayout disel = view.findViewById(R.id.Page1_Diesel);
        LinearLayout terminal = view.findViewById(R.id.Page1_Terminal);

        lineCar.setOnClickListener(v-> { startActivity(new Intent(getContext(), ViewInfo.class)); });
        roozbazar.setOnClickListener(v-> { startActivity(new Intent(getContext(), ViewInfo.class)); });
        disel.setOnClickListener(v-> { startActivity(new Intent(getContext(), ViewInfoTerminal.class)); });
        terminal.setOnClickListener(v-> { startActivity(new Intent(getContext(), ViewInfo.class)); });

        return view;
    }

}
