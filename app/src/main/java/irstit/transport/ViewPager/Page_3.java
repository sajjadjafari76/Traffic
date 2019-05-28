package irstit.transport.ViewPager;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import irstit.transport.Citizens.Criticals_Suggestion;
import irstit.transport.R;
import irstit.transport.ViewPager.ViewInfo.ViewInfo;
import irstit.transport.annoucment.announcement;


public class Page_3 extends Fragment {


    public Page_3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_page_3, container, false);

        LinearLayout critical = view.findViewById(R.id.Page3_Critical);
        LinearLayout project = view.findViewById(R.id.Page3_Project);
        LinearLayout anouncement = view.findViewById(R.id.announcement_spe);

        critical.setOnClickListener(v-> { startActivity(new Intent(getContext(), Criticals_Suggestion.class)); });
        project.setOnClickListener(v-> { startActivity(new Intent(getContext(), ViewInfo.class)); });

        anouncement.setOnClickListener(V-> {

            startActivity(new Intent(getContext(),announcement.class));

        });

        return view;


    }

}
