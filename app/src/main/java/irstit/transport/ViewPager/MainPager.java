package irstit.transport.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import irstit.transport.Citizens.Complaint;
import irstit.transport.Citizens.ComplaintTrack;
import irstit.transport.Citizens.Criticals_Suggestion;
import irstit.transport.Citizens.RegisterObject;
import irstit.transport.Citizens.SearchObject;
import irstit.transport.ConnectToUs;
import irstit.transport.DataModel.NavModel;
import irstit.transport.MainPage;
import irstit.transport.R;

public class MainPager extends AppCompatActivity {

    private FragmentAdapter mAdapter;
    private ViewPager mPager;

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pager);

        LinearLayout Site = findViewById(R.id.MainPager_Site);
        LinearLayout Dial = findViewById(R.id.MainPager_Dial);

        Dial.setOnClickListener(v->{

            Intent dial1 = new Intent();
            dial1.setAction("android.intent.action.DIAL");
            dial1.setData(Uri.parse("tel:"+"02835237500"));
            startActivity(dial1);

        });
        Site.setOnClickListener(v->{

            Uri uri = Uri.parse("http://www.traffictakestan.ir"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);

        });


        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(this, Page_2.class.getName()));
        fragments.add(Fragment.instantiate(this, Page_1.class.getName()));
        fragments.add(Fragment.instantiate(this, Page_3.class.getName()));
        mAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments);


        drawer = findViewById(R.id.MainPager_Drawer);
        ImageView iconDrawer = findViewById(R.id.MainPager_NavigatorIcon);
        iconDrawer.setOnClickListener(v-> drawer.openDrawer(Gravity.RIGHT));


        RecyclerView navigation_Recycler = findViewById(R.id.MainPager_Navigation_RecyClerView);
        navigation_Recycler.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));
        navigation_Recycler.setAdapter(new MyNavigationAdapter(navigationData()));


        mPager = findViewById(R.id.MainAPager_ViewPager);
        mPager.setAdapter(mAdapter);


        DotsIndicator dotsIndicator =  findViewById(R.id.MainPage_Indicator);
        dotsIndicator.setViewPager(mPager);

//        IndefinitePagerIndicator Indicator = findViewById(R.id.MainPage_Indicator);
//        Indicator.attachToViewPager(mPager);
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
                        break;
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
        for (int i = 0 ; i <= 7 ; i++) {

            if (i == 0) {
                NavModel model = new NavModel();
                model.setName("جستجو اشیا گمشده");
                model.setImage(ContextCompat.getDrawable(getBaseContext(), R.drawable.img_search));
                data.add(model);
            } else if (i == 1) {
                NavModel model = new NavModel();
                model.setName("ثبت شی گمشده");
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
            }

        }

        return data;
    }

    void closeDrawer() {
        if (drawer.isDrawerOpen(Gravity.RIGHT)) {
            drawer.closeDrawers();
        }
    }

}
