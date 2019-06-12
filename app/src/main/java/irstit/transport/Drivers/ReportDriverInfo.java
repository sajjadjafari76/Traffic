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
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import irstit.transport.R;

public class ReportDriverInfo extends Fragment {

    ProgressBar progressbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report_driver_info, container, false);

        ImageView imageView = view.findViewById(R.id.ReportDriverInfo_Picture);
        progressbar = view.findViewById(R.id.progressbar);
        Log.e("id123", getArguments().getString("id") + " |");
        Picasso.with(getContext())
                .load("http://cpanel.traffictakestan.ir/upload/decree/" + getArguments().getString("id") + ".png")
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressbar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        progressbar.setVisibility(View.GONE);
                    }
                });

        return view;
    }

}
