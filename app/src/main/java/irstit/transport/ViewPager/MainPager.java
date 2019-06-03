package irstit.transport.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.marozzi.roundbutton.RoundButton;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import irstit.transport.AboutUs;
import irstit.transport.Citizens.Account.CitizenLogin;
import irstit.transport.Citizens.CitizenMainActivity;
import irstit.transport.Citizens.complaint.Complaint;
import irstit.transport.Citizens.complaint.ComplaintTrack;
import irstit.transport.Citizens.Criticals_Suggestion;
import irstit.transport.Citizens.RegisterObject;
import irstit.transport.Citizens.SearchObject;
import irstit.transport.ConnectToUs;
import irstit.transport.DataBase.DBManager;
import irstit.transport.DataModel.NavModel;
import irstit.transport.Drivers.DriverMainActivityTwo;
import irstit.transport.Drivers.DriversMainActivity;
import irstit.transport.MainPage;
import irstit.transport.R;
import irstit.transport.Views.CFProvider;
import irstit.transport.Views.CustomButton;

public class MainPager extends AppCompatActivity {

    private FragmentAdapter mAdapter;
    private ViewPager mPager;

    private AlertDialog alertDialog;

    private DrawerLayout drawer;

    private NavigationView na;

    public static String incomingName;
    RelativeLayout relative_main_page,relative_back;


    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pager);

        LinearLayout Site = findViewById(R.id.MainPager_Site);
        LinearLayout Dial = findViewById(R.id.MainPager_Dial);
        relative_main_page = findViewById(R.id.relative_main_page);
        relative_back = findViewById(R.id.relative_back);

        relative_main_page.setVisibility(View.VISIBLE);
        relative_back.setVisibility(View.GONE);
        Dial.setOnClickListener(v -> {

            Intent dial1 = new Intent();
            dial1.setAction("android.intent.action.DIAL");
            dial1.setData(Uri.parse("tel:" + "02835237500"));
            startActivity(dial1);

        });
        Site.setOnClickListener(v -> {

            Uri uri = Uri.parse("http://www.traffictakestan.ir"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);

        });


        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(this, Page_2.class.getName()));
        fragments.add(Fragment.instantiate(this, Page_1.class.getName()));
        fragments.add(Fragment.instantiate(this, Page_3.class.getName()));
        mAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments);

        changeDefaultuser_Name();
        drawer = findViewById(R.id.MainPager_Drawer);
        // na = findViewById(R.id.MAinActivity_NavigationView_3);
        ImageView iconDrawer = findViewById(R.id.MainPager_NavigatorIcon);
        iconDrawer.setOnClickListener(v -> drawer.openDrawer(Gravity.END)
        );


        RecyclerView navigation_Recycler = findViewById(R.id.MainPager_Navigation_RecyClerView);
        navigation_Recycler.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));
        navigation_Recycler.setAdapter(new MyNavigationAdapter(navigationData()));


        mPager = findViewById(R.id.MainAPager_ViewPager);
        mPager.setAdapter(mAdapter);


        DotsIndicator dotsIndicator = findViewById(R.id.MainPage_Indicator);
        dotsIndicator.setViewPager(mPager);


//        IndefinitePagerIndicator Indicator = findViewById(R.id.MainPage_Indicator);
//        Indicator.attachToViewPager(mPager);


        NavigationView navigationView = findViewById(R.id.MAinActivity_NavigationView_3);
        View headerView = navigationView.getHeaderView(0);
        if (DBManager.getInstance(getBaseContext()).getDriverInfo().getName() != null) {
            RelativeLayout relative = headerView.findViewById(R.id.Navigation_Login);

            relative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getBaseContext(), DriversMainActivity.class);
                    intent.putExtra("Driver_profile", "true");
                    startActivity(intent);


                }
            });
        }
    }


//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//
//        FragmentManager n = getSupportFragmentManager().findFragmentById()
//    }

    private void deleteSavedDefaultUsername() {
        try {

            SharedPreferences sh = getSharedPreferences("complaint", MODE_PRIVATE);
            SharedPreferences.Editor editor = sh.edit();
            editor.clear();
            editor.commit();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void changeDefaultuser_Name() {

        try {

//            SharedPreferences sh = getSharedPreferences("complaint", MODE_PRIVATE);
//            String objectForDriverName = sh.getString("complaintArray", "-1");
//            JSONObject jsonObject = new JSONObject(objectForDriverName);
//            JSONObject name = new JSONObject(jsonObject.getString("userdata"));
//            Log.e("nameFromMainActivity", name.toString());

            NavigationView navigationView = findViewById(R.id.MAinActivity_NavigationView_3);
            View headerView = navigationView.getHeaderView(0);


            if (DBManager.getInstance(getBaseContext()).getDriverInfo().getName() != null) {

                incomingName = DBManager.getInstance(getBaseContext()).getDriverInfo().getName();
//                incomingName = name.getString("d_name");

                TextView navUsername = headerView.findViewById(R.id.Navigation_Enter);
                navUsername.setText(incomingName);


            } else if (DBManager.getInstance(getBaseContext()).getCitizenInfo().getUserName() != null) {

                incomingName = DBManager.getInstance(getBaseContext()).getCitizenInfo().getUserName();
//                incomingName = name.getString("d_name");
                TextView navUsername = headerView.findViewById(R.id.Navigation_Enter);
                navUsername.setText(incomingName);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public class FragmentAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragments;

        public FragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            mFragments = fragments;
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

    }


    private class MyNavigationAdapter extends RecyclerView.Adapter<MyNavigationAdapter.MyCustomView> {

        List<NavModel> data;

        public MyNavigationAdapter(List<NavModel> data) {
            this.data = data;
        }

        @Override
        public MyNavigationAdapter.MyCustomView onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyNavigationAdapter.MyCustomView(LayoutInflater
                    .from(getBaseContext()).inflate(R.layout.layout_navigation_recycler, null));
        }

        @Override
        public void onBindViewHolder(MyNavigationAdapter.MyCustomView holder, final int position) {

            holder.textView.setText(data.get(position).getName());
            holder.imageView.setImageDrawable(data.get(position).getImage());

            holder.Root.setOnClickListener(v -> {

                if (DBManager.getInstance(getApplicationContext()).getCitizenInfo().getUserName() != null) {
                    switch (position) {
                        case 0:
                            Intent intent = new Intent(getBaseContext(), SearchObject.class);
//                        intent.putExtra("RequestVacation", "true");
                            startActivity(intent);
                            closeDrawer();
                            break;
                        case 1:
                            Intent intent1 = new Intent(getBaseContext(), RegisterObject.class);
//                        intent1.putExtra("data", getIntent().getExtras().getString("data"));
                            startActivity(intent1);
                            closeDrawer();
                            break;
                        case 2:
                            Intent intent12 = new Intent(getBaseContext(), Complaint.class);
                            if (getIntent().getExtras() != null && getIntent().getExtras().getString("data") != null) {
                                intent12.putExtra("data", getIntent().getExtras().getString("data"));
                            }
                            startActivity(intent12);
                            closeDrawer();
                            break;
                        case 3:
                            startActivity(new Intent(getBaseContext(), ComplaintTrack.class));
                            closeDrawer();
                            break;
                        case 4:
                            startActivity(new Intent(getBaseContext(), Criticals_Suggestion.class));
                            closeDrawer();
                            break;
                        case 5:
                            startActivity(new Intent(getBaseContext(), ConnectToUs.class));
                            closeDrawer();
                            break;
                        case 6:
                            startActivity(new Intent(getBaseContext(), AboutUs.class));
                            break;

                        case 7:
                            deleteSavedDefaultUsername();
                            ShowDialog();
                            break;


                    }
                } else if (DBManager.getInstance(getApplicationContext()).getDriverInfo().getName() != null) {
                    switch (position) {
                        case 0:
                            Intent intent = new Intent(getBaseContext(), SearchObject.class);
//                        intent.putExtra("RequestVacation", "true");
                            startActivity(intent);
                            closeDrawer();
                            break;
                        case 1:
                            Intent intent1 = new Intent(getBaseContext(), RegisterObject.class);
//                        intent1.putExtra("data", getIntent().getExtras().getString("data"));
                            startActivity(intent1);
                            closeDrawer();
                            break;
//                        case 2:
//                            Intent intent12 = new Intent(getBaseContext(), Complaint.class);
//                            if (getIntent().getExtras() != null && getIntent().getExtras().getString("data") != null) {
//                                intent12.putExtra("data", getIntent().getExtras().getString("data"));
//                            }
//                            startActivity(intent12);
//                            closeDrawer();
//                            break;
//                        case 3:
//                            startActivity(new Intent(getBaseContext(), ComplaintTrack.class));
//                            closeDrawer();
//                            break;
                        case 2:
                            startActivity(new Intent(getBaseContext(), Criticals_Suggestion.class));
                            closeDrawer();
                            break;
                        case 3:
                            startActivity(new Intent(getBaseContext(), ConnectToUs.class));
                            closeDrawer();
                            break;
                        case 4:
                            startActivity(new Intent(getBaseContext(), AboutUs.class));
                            break;

                        case 5:
                            deleteSavedDefaultUsername();
                            ShowDialog();
                            break;

                    }
                }
            });


        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyCustomView extends RecyclerView.ViewHolder {

            private RelativeLayout Root;
            private TextView textView;
            private ImageView imageView;

            public MyCustomView(View itemView) {
                super(itemView);
                Root = itemView.findViewById(R.id.NavigationRecycler_Root);
                textView = itemView.findViewById(R.id.NavigationRecycler_Text);
                imageView = itemView.findViewById(R.id.NavigationRecycler_Image);

            }
        }
    }


    private List<NavModel> navigationData() {
        List<NavModel> data = new ArrayList<>();

        CitizenLogin citizenLogin = new CitizenLogin();

        //  DBManager.getInstance(getBaseContext()).getDriverInfo();

        // this if condition is for citizen section
        if (DBManager.getInstance(getApplicationContext()).getCitizenInfo().getUserName() != null) {
            for (int i = 0; i <= 7; i++) {

                if (i == 0) {
                    NavModel model = new NavModel();
                    model.setName("جستجو اشیا گمشده");
                    model.setImage(ContextCompat.getDrawable(getBaseContext(), R.drawable.img_search));
                    data.add(model);
                } else if (i == 1) {
                    NavModel model = new NavModel();
                    model.setName("ثبت شی  گمشده");
                    model.setImage(ContextCompat.getDrawable(getBaseContext(), R.drawable.img_complaint));
                    data.add(model);
                } else if (i == 2) {
                    NavModel model = new NavModel();
                    model.setName("شکایات");
                    model.setImage(ContextCompat.getDrawable(getBaseContext(), R.drawable.img_complaint));
                    data.add(model);
                } else if (i == 3) {
                    NavModel model = new NavModel();
                    model.setName("پیگیری شکایت");
                    model.setImage(ContextCompat.getDrawable(getBaseContext(), R.drawable.img_complaint));
                    data.add(model);
                } else if (i == 4) {
                    NavModel model = new NavModel();
                    model.setName("پیشنهادات و انتقادات");
                    model.setImage(ContextCompat.getDrawable(getBaseContext(), R.drawable.img_critical));
                    data.add(model);
                } else if (i == 5) {
                    NavModel model = new NavModel();
                    model.setName("تماس با ما");
                    model.setImage(ContextCompat.getDrawable(getBaseContext(), R.drawable.img_contact));
                    data.add(model);
                } else if (i == 6) {
                    NavModel model = new NavModel();
                    model.setName("درباره ی ما");
                    model.setImage(ContextCompat.getDrawable(getBaseContext(), R.drawable.img_about_us));
                    data.add(model);
                } else if (i == 7) {

                    NavModel model = new NavModel();
                    model.setName("خروج");
                    model.setImage(ContextCompat.getDrawable(getBaseContext(), R.drawable.img_about_us));
                    data.add(model);
                }


            }
        } else if (DBManager.getInstance(getApplicationContext()).getDriverInfo().getName() != null) {

            // this else is for driver section


            for (int i = 0; i <= 8; i++) {

                if (i == 0) {
                    NavModel model = new NavModel();
                    model.setName("جستجو اشیا یافت شده");
                    model.setImage(ContextCompat.getDrawable(getBaseContext(), R.drawable.img_search));
                    data.add(model);
                } else if (i == 1) {
                    NavModel model = new NavModel();
                    model.setName("ثبت شی یافت شده");
                    model.setImage(ContextCompat.getDrawable(getBaseContext(), R.drawable.img_complaint));
                    data.add(model);
                } else if (i == 2) {
                    NavModel model = new NavModel();
                    model.setName("پیشنهادات و انتقادات");
                    model.setImage(ContextCompat.getDrawable(getBaseContext(), R.drawable.img_critical));
                    data.add(model);
                } else if (i == 3) {
                    NavModel model = new NavModel();
                    model.setName("تماس با ما");
                    model.setImage(ContextCompat.getDrawable(getBaseContext(), R.drawable.img_contact));
                    data.add(model);
                } else if (i == 4) {
                    NavModel model = new NavModel();
                    model.setName("درباره ی ما");
                    model.setImage(ContextCompat.getDrawable(getBaseContext(), R.drawable.img_about_us));
                    data.add(model);
                } else if (i == 8) {
                    NavModel model = new NavModel();
                    model.setName("خروج");
                    model.setImage(ContextCompat.getDrawable(getBaseContext(), R.drawable.img_about_us));
                    data.add(model);
                }

            }
        }

        return data;
    }

    void closeDrawer() {
        if (drawer.isDrawerOpen(Gravity.RIGHT)) {
            drawer.closeDrawers();
        }
    }


    private void ShowDialog() {

        try {
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainPager.this);
            dialog.setView(R.layout.exit_permission);

            alertDialog = dialog.create();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            LayoutInflater inflater = getLayoutInflater();
            alertDialog.setContentView(inflater.inflate(R.layout.exit_permission, null));
            alertDialog.setCancelable(false);
            alertDialog.show();

            CustomButton Yes = (CustomButton) alertDialog.findViewById(R.id.CustomPermission_Yes);
            CustomButton Cancel = (CustomButton) alertDialog.findViewById(R.id.CustomPermission_Cancel);
            if (Yes != null && Cancel != null) {
                Yes.setTypeface(CFProvider.getIRANIANSANS(getBaseContext()));
                Cancel.setTypeface(CFProvider.getIRANIANSANS(getBaseContext()));

                Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                Yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        DBManager.getInstance(getApplicationContext()).deleteDrivers();
                        DBManager.getInstance(getApplicationContext()).deleteCitizen();

                        alertDialog.dismiss();
                        Intent intent = new Intent(getBaseContext(), MainPage.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            relative_main_page.setVisibility(View.GONE);
            relative_back.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {

                    finish();

                }
            }, 2000);
//            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "برای خروج دوباره کلید کنید", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;

            }
        }, 2000);
    }


}
