package irstit.transport.ViewPager;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import irstit.transport.CNG.CNG;
import irstit.transport.Citizens.CitizenMainActivity;
import irstit.transport.DataBase.DBManager;
import irstit.transport.Drivers.DriverMainActivityTwo;
import irstit.transport.Drivers.TechnicalDiagnosis;
import irstit.transport.MainActivity;
import irstit.transport.PhonePay.PhonePayMainActicity;
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
        ImageView taxi = view.findViewById(R.id.taxi);

        YoYo.with(Techniques.Flash)
                .duration(700)
                .repeat(100000)
                .playOn(taxi);

        cng.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), CNG.class));
        });
        technical.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), TechnicalDiagnosis.class));
        });

        wallet.setOnClickListener(v -> {
            Toast.makeText(getContext(), " به زودی فعال ميگردد", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(getContext(),PhonePayMainActicity.class));
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
