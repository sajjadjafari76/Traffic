package irstit.transport.ViewPager.ViewInfo;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import irstit.transport.R;
import irstit.transport.Views.CFProvider;

public class ViewInfo extends AppCompatActivity {

    private TabLayout ViewInfo_Tab;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_info);

        ViewInfo_Tab = findViewById(R.id.ViewInfo_Tab);
        viewPager = findViewById(R.id.ViewInfo_ViewPager);

        SimpleFragmentPagerAdapter adapter =
                new SimpleFragmentPagerAdapter(getBaseContext(), getSupportFragmentManager());

        viewPager.setAdapter(adapter);
        ViewInfo_Tab.setupWithViewPager(viewPager);

        for (int i = 0; i < ViewInfo_Tab.getChildCount(); i++) {
            if (ViewInfo_Tab.getChildAt(i) instanceof TextView) {
                ((TextView) ViewInfo_Tab.getChildAt(i)).setTypeface(CFProvider.getIRANIANSANS(getBaseContext()));
            }
        }

        viewPager.setCurrentItem(1);

    }


    public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

        private Context mContext;

        public SimpleFragmentPagerAdapter(Context context, FragmentManager fm) {
            super(fm);
            mContext = context;
        }

        // This determines the fragment for each tab
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new DataTab();
            } else {
                return new InfoTab();
            }
        }

        // This determines the number of tabs
        @Override
        public int getCount() {
            return 2;
        }

        // This determines the title for each tab
        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            switch (position) {
                case 0:
                    return "بخشنامه ها";
                case 1:
                    return "اطلاعات";
                default:
                    return null;
            }
        }

    }

}
