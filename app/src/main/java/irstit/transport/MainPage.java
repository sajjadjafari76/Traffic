package irstit.transport;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import irstit.transport.Citizens.Account.CitizensAccountActivity;
import irstit.transport.DataBase.DBManager;
import irstit.transport.Drivers.Login.ActivityLogin;
import irstit.transport.ViewPager.MainPager;

public class MainPage extends AppCompatActivity {


    @Override
    protected void onStart() {
        super.onStart();

        Log.e("ddddddd", DBManager.getInstance(getBaseContext()).getDriverInfo().getName() + " | " +
                DBManager.getInstance(getBaseContext()).getCitizenInfo().getUserId());
        if (DBManager.getInstance(getBaseContext()).getDriverInfo().getName() != null
                ||
                DBManager.getInstance(getBaseContext()).getCitizenInfo().getUserId() != null) {

            startActivity(new Intent(getBaseContext(), MainPager.class));
            finish();

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        LinearLayout driver = findViewById(R.id.MainPage_Driver);
        LinearLayout citizen = findViewById(R.id.MainPage_Citizen);
        LinearLayout site = findViewById(R.id.MainPage_Site);
        LinearLayout dial = findViewById(R.id.MainPage_Dial1);





        driver.setOnClickListener(v->{startActivity(new Intent(getBaseContext(), ActivityLogin.class));});
        citizen.setOnClickListener(v->{startActivity(new Intent(getBaseContext(), CitizensAccountActivity.class));});
        dial.setOnClickListener(v->{

            Intent dial1 = new Intent();
            dial1.setAction("android.intent.action.DIAL");
            dial1.setData(Uri.parse("tel:"+"02835237500"));
            startActivity(dial1);

        });
        site.setOnClickListener(v->{

            Uri uri = Uri.parse("http://www.traffictakestan.ir"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);

        });



    }


}

