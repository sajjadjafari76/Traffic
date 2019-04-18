package irstit.transport.ViewPager;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import irstit.transport.Citizens.CitizenMainActivity;
import irstit.transport.DataBase.DBManager;
import irstit.transport.Drivers.DriverMainActivityTwo;
import irstit.transport.MainActivity;
import irstit.transport.R;
import irstit.transport.ViewPager.ViewInfo.ViewInfo;

/**
 * A simple {@link Fragment} subclass.
 */
public class Page_2 extends Fragment {


    public Page_2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page_2, container, false);

        LinearLayout cng = view.findViewById(R.id.Page2_CNG);
        LinearLayout technical = view.findViewById(R.id.Page2_Technical);
        LinearLayout wallet = view.findViewById(R.id.Page2_Wallet);
        LinearLayout Taxi = view.findViewById(R.id.Page2_Taxi);

        cng.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), ViewInfo.class));
        });
        technical.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), ViewInfo.class));
        });

        wallet.setOnClickListener(v -> {
            Toast.makeText(getContext(), "این قسمت در دست ساخت می باشد", Toast.LENGTH_SHORT).show();
        });

        Taxi.setOnClickListener(v -> {

            if (DBManager.getInstance(getContext()).getDriverInfo().getName() != null) {

                startActivity(new Intent(getContext(), DriverMainActivityTwo.class));

            } else if (DBManager.getInstance(getContext()).getCitizenInfo().getUserName() != null) {
                startActivity(new Intent(getContext(), CitizenMainActivity.class));
            }

        });


        return view;
    }


}
