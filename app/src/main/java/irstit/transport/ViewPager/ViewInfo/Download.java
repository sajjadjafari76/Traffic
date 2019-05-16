package irstit.transport.ViewPager.ViewInfo;


import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import irstit.transport.R;

import static android.content.Context.DOWNLOAD_SERVICE;

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

        // WebView webView = view.findViewById(R.id.mydown);
        (view.findViewById(R.id.uniqueBtn)).setOnClickListener((vie) -> {

            Uri uri = Uri.parse("https://baarbarg.ir/Content/APK/Baarbarg(1.1.0).apk"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW,  uri);
            startActivity(intent);


////            webView.getSettings().setJavaScriptEnabled(true);
//            Log.e("downloadapp","click");
//            webView.setVisibility(View.VISIBLE);

//            webView.setWebViewClient(new WebViewClient());
//            webView.loadUrl("https://baarbarg.ir/Content/APK/Baarbarg(1.1.0).apk");
////            webView.loadUrl("https://baarbarg.ir/Content/APK/Baarbarg(1.1.0).apk");
//           webView.setVisibility(View.VISIBLE);
//            webView.setDownloadListener(new DownloadListener() {
//                @Override
//                public void onDownloadStart(String s, String s1, String s2, String s3, long l) {
//
//                    Intent i = new Intent(Intent.ACTION_VIEW);
//                    i.setData(Uri.parse("https://baarbarg.ir/Content/APK/Baarbarg(1.1.0).apk"));
//                    startActivity(i);
//
//                }
//            });

//            webView.setVisibility(View.VISIBLE);
//            webView.setDownloadListener(new DownloadListener() {
//
//
//                @Override
//                public void onDownloadStart(String url, String userAgent,
//                                            String contentDisposition, String mimetype,
//                                            long contentLength) {
//                    DownloadManager.Request request = new DownloadManager.Request(
//                            Uri.parse("https://baarbarg.ir/Content/APK/Baarbarg(1.1.0).apk"));
//
//                    request.allowScanningByMediaScanner();
//                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //Notify client once download is completed!
//                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "barbarg ");
//                    DownloadManager dm = (DownloadManager)getActivity().getSystemService(DOWNLOAD_SERVICE);
//                    dm.enqueue(request);
//                    Toast.makeText(getActivity(), "Downloading File", //To notify the Client that the file is being downloaded
//                            Toast.LENGTH_LONG).show();
//
//                }
//            });
////
        });


        // Inflate the layout for this fragment
        return view;    }




}
