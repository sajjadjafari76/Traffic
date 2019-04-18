package irstit.transport;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

public class ConnectToUs extends AppCompatActivity {

    WebView visitTakestanAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_to_us);

        //visitTakestanAddress.setWebViewClient(new HelloWebViewClient());


        visitTakestanAddress =findViewById(R.id.map);

        visitTakestanAddress.getSettings().setJavaScriptEnabled(true);
        String url = "https://www.google.com/maps/d/embed?mid=1B9OM5iaLqBYD2PAUgh8ByNJ963CDZQgO";

        visitTakestanAddress.setWebViewClient(new WebViewClient()); //UPDATE HERE
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


