package irstit.transport.ViewPager;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import irstit.transport.ForDisel;
import irstit.transport.R;
import irstit.transport.ViewPager.ViewInfo.CarsLine;
import irstit.transport.ViewPager.ViewInfo.DailyMarket;
import irstit.transport.ViewPager.ViewInfo.DieselCars;
import irstit.transport.ViewPager.ViewInfo.Terminal;
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

        ImageView shop = view.findViewById(R.id.shop);
        ImageView terminall = view.findViewById(R.id.terminal);
        ImageView line = view.findViewById(R.id.line);
        ImageView disellll = view.findViewById(R.id.disel);

        YoYo.with(Techniques.Flash)
                .duration(700)
                .repeat(100000)
                .playOn(shop);

        YoYo.with(Techniques.Flash)
                .duration(700)
                .repeat(100000)
                .playOn(terminall);

        YoYo.with(Techniques.Flash)
                .duration(700)
                .repeat(100000)
                .playOn(line);

        YoYo.with(Techniques.Flash)
                .duration(700)
                .repeat(100000)
                .playOn(disellll);

        lineCar.setOnClickListener(v-> { startActivity(new Intent(getContext(), CarsLine.class)); });
        roozbazar.setOnClickListener(v-> { startActivity(new Intent(getContext(), DailyMarket.class)); });
        disel.setOnClickListener(v-> { startActivity(new Intent(getContext(), DieselCars.class)); });
        terminal.setOnClickListener(v-> { startActivity(new Intent(getContext(), Terminal.class)); });

        return view;
    }

}
