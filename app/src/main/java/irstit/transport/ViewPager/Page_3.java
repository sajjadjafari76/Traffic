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
import irstit.transport.ViewPager.ViewInfo.AboutTeam;
import irstit.transport.ViewPager.ViewInfo.ProjectOngoing;
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
        LinearLayout about_team = view.findViewById(R.id.about_team);

        critical.setOnClickListener(v-> {


            Intent critical_ = new Intent(getContext(), Criticals_Suggestion.class);
            critical_.putExtra("critical","criticalRequest");
            startActivity(critical_);

        });

        about_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getContext(), AboutTeam.class);
                startActivity(intent);
            }
        });

        project.setOnClickListener(v-> {

             Intent project_ = new Intent(getContext(), ProjectOngoing.class);
             startActivity(project_);

        });

        anouncement.setOnClickListener(V-> {


            Intent announcement_ = new Intent(getContext(),announcement.class);
            announcement_.putExtra("announcement","announcementRequest");
            startActivity(announcement_);

        });

        return view;


    }

}
