package irstit.transport.Drivers;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import irstit.transport.R;

public class ReportDriverInfo extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report_driver_info, container, false);

        ImageView imageView = view.findViewById(R.id.ReportDriverInfo_Picture);
        Log.e("id123", getArguments().getString("id") + " |");
        Picasso.with(getContext())
                .load("http://cpanel.traffictakestan.ir/upload/decree/"+ getArguments().getString("id") +".png")
                .into(imageView);

        return view;
    }

}
