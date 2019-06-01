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


public class Page_4 extends Fragment {


    public Page_4() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_page_4, container, false);

        LinearLayout critical = view.findViewById(R.id.Page3_Critical);
        LinearLayout project = view.findViewById(R.id.Page3_Project);
        LinearLayout coperat = view.findViewById(R.id.coperatewithYourProfession);
        LinearLayout validation = view.findViewById(R.id.validation);

        critical.setOnClickListener(v-> { startActivity(new Intent(getContext(), Criticals_Suggestion.class)); });
        project.setOnClickListener(v-> { startActivity(new Intent(getContext(), ViewInfo.class)); });
        coperat.setOnClickListener(V->{startActivity(new Intent(getContext(),ViewInfo.class));});
        validation.setOnClickListener(V->{startActivity(new Intent(getContext(),ViewInfo.class));});

        return view;


    }

}
