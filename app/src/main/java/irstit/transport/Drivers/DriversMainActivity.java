package irstit.transport.Drivers;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import irstit.transport.DataBase.DBManager;
import irstit.transport.DataModel.ReqVacationModel;
import irstit.transport.R;

public class DriversMainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private TabLayout tab_layout;
    private BottomNavigationView bottomNavigationMenuView;
    public static List<ReqVacationModel> mData = new ArrayList<>();

    @Override
    protected void onStart() {
        super.onStart();
        if (DBManager.getInstance(getBaseContext()).getDriverInfo().getOwnerId() == null) {
//            bottomNavigationMenuView.getMenu().removeItem(R.id.DriversMainMenu_ReqVacation);
            bottomNavigationMenuView.setVisibility(View.GONE);

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().addToBackStack(null);
            fragmentTransaction.replace(R.id.DriversMainActivity_Container, new Profile());
            fragmentTransaction.commit();
        } else {
            if (DBManager.getInstance(getBaseContext()).getDriverInfo().getOwnerId().equals("3")) {
//            bottomNavigationMenuView.getMenu().removeItem(R.id.DriversMainMenu_ReqVacation);
                bottomNavigationMenuView.setVisibility(View.GONE);

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().addToBackStack(null);
                fragmentTransaction.replace(R.id.DriversMainActivity_Container, new Profile());
                fragmentTransaction.commit();
            }else if (DBManager.getInstance(getBaseContext()).getDriverInfo().getIsTaxi().equals("0")) {
                //            bottomNavigationMenuView.getMenu().removeItem(R.id.DriversMainMenu_ReqVacation);
                bottomNavigationMenuView.setVisibility(View.GONE);

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().addToBackStack(null);
                fragmentTransaction.replace(R.id.DriversMainActivity_Container, new Profile());
                fragmentTransaction.commit();
            }else {

                if (getIntent().getExtras() != null) {
                    if (getIntent().getExtras().getString("RequestVacation") != null && getIntent().getExtras().getString("RequestVacation").equals("true")) {
                        bottomNavigationMenuView.setSelectedItemId(R.id.DriversMainMenu_ReqVacation);
                    }else {
                        bottomNavigationMenuView.setSelectedItemId(R.id.DriversMainMenu_Profile);
                    }
                } else {
                    bottomNavigationMenuView.setSelectedItemId(R.id.DriversMainMenu_Profile);
                }
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drivers_main);
        bottomNavigationMenuView = findViewById(R.id.DriversMainActivity_BottomNavigation);
        bottomNavigationMenuView.setOnNavigationItemSelectedListener(this);


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (menuItem.getItemId()) {
            case R.id.DriversMainMenu_Profile:
                fragmentTransaction.replace(R.id.DriversMainActivity_Container, new Profile());
                fragmentTransaction.commit();
                return true;
            case R.id.DriversMainMenu_ReqVacation:
                RequestVacation vacation = new RequestVacation();
                Bundle bundle = new Bundle();
                if (getIntent().getExtras() != null && getIntent().getExtras().getString("dataVacation") != null) {
                    bundle.putString("dataVacation", getIntent().getExtras().getString("dataVacation"));
                }
                vacation.setArguments(bundle);
                fragmentTransaction.replace(R.id.DriversMainActivity_Container, vacation);
                fragmentTransaction.commit();
                return true;
            case R.id.DriversMainMenu_VacationSearch:
                fragmentTransaction.replace(R.id.DriversMainActivity_Container, new VacationSearch());
                fragmentTransaction.commit();
                return true;
        }
        return false;
    }


    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            getSupportFragmentManager().popBackStack();
            super.onBackPressed();
        }else {
            super.onBackPressed();
        }
        Log.e("bac11kback", getSupportFragmentManager().getBackStackEntryCount() + " |");


        Log.e("backback", getSupportFragmentManager().getBackStackEntryCount() + " |");




        if (DBManager.getInstance(getBaseContext()).getDriverInfo().getOwnerId() == null) {

        } else {
            if (DBManager.getInstance(getBaseContext()).getDriverInfo().getOwnerId().equals("3")) {

            }else if (DBManager.getInstance(getBaseContext()).getDriverInfo().getIsTaxi().equals("0")) {

            }
        }

    }
}
