package irstit.transport.ViewPager.ViewInfo;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import irstit.transport.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Download extends Fragment {


    public Download() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         View view = inflater.inflate(R.layout.fragment_download, container, false);

         WebView webView = view.findViewById(R.id.mydown);
        (view.findViewById(R.id.ForDisel_Btnd)).setOnClickListener((vie) -> {
            Uri uri = Uri.parse("https://baarbarg.ir/Content/APK/Baarbarg(1.1.0).apk"); // missing 'http://' will cause crashed
//            Intent intent = new Intent(Intent.ACTION_VIEW,  uri);
//            startActivity(intent);


            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl("https://baarbarg.ir/Content/APK/Baarbarg(1.1.0).apk");
        });
        // Inflate the layout for this fragment
        return view;    }

}
