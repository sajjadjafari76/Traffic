package irstit.transport.PhonePay;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import irstit.transport.R;

public class pay_by_recipient_code extends AppCompatActivity {

    LinearLayout pay ;
    FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_by_recipient_code);
        pay = findViewById(R.id.pay);
        frameLayout = findViewById(R.id.fragment1);


        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction startF = getSupportFragmentManager().beginTransaction();
                Increasing_balance p1= new Increasing_balance();
                frameLayout.setVisibility(View.VISIBLE);
                startF.replace(R.id.fragment1,p1);
                startF.commit();

            }
        });
    }
}
