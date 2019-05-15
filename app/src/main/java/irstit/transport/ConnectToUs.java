package irstit.transport;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class ConnectToUs extends AppCompatActivity {

    WebView visitTakestanAddress;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_to_us);



        progressBar =  findViewById(R.id.loadingprogressbar);

         visitTakestanAddress =findViewById(R.id.map);

        visitTakestanAddress.getSettings().setJavaScriptEnabled(true);
        String url = "https://www.google.com/maps/d/embed?mid=1B9OM5iaLqBYD2PAUgh8ByNJ963CDZQgO";

        progressBar.setVisibility(View.VISIBLE);
        visitTakestanAddress.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }
        }); //UPDATE HERE
        visitTakestanAddress.loadUrl(url);






        ImageView Back = findViewById(R.id.ConnectToUs_Back);
        Back.setOnClickListener((View view)->{


            finish();

        });



        //   visitTakestanAddress.loadUrl();

    }



    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            url = "https://www.google.com/maps/d/embed?mid=1B9OM5iaLqBYD2PAUgh8ByNJ963CDZQgO";
            view.loadUrl(url);
            return true;
        }
    }

}


