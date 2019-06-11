package irstit.transport.ViewPager.ViewInfo;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import irstit.transport.R;

public class DieselCars extends AppCompatActivity {

    ImageView imageView, imageView1, imageView3;
    TextView textLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diesel_cars);
        imageView = findViewById(R.id.LetterRate_Back);
        textLink = findViewById(R.id.link_diesel);
        imageView1 = findViewById(R.id.image1);
//        imageView2 = findViewById(R.id.image2);
        imageView3 = findViewById(R.id.image3);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imageView1.setOnClickListener(v -> {

//            String imgPath = path.toString();
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setData(path);
//            startActivity(intent);

            Uri path = Uri.parse("android.resource://irstit.transport/" + R.drawable.nerkh1);
            Intent fullScreenIntent = new Intent(this, FullScreenImageActivity.class);
            fullScreenIntent.setData(path);
            startActivity(fullScreenIntent);


        });
//        imageView2.setOnClickListener(v -> {
////            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("android.resource://irstit.transport/" + R.drawable.nerkh2))); /** replace with your own uri */
//
//            Uri path = Uri.parse("android.resource://irstit.transport/" + R.drawable.nerkh2);
//            Intent fullScreenIntent = new Intent(this, FullScreenImageActivity.class);
//            fullScreenIntent.setData(path);
//            startActivity(fullScreenIntent);
//
//        });
        imageView3.setOnClickListener(v -> {
//            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("android.resource://irstit.transport/" + R.drawable.nerkh3))); /** replace with your own uri */

            Uri path = Uri.parse("android.resource://irstit.transport/" + R.drawable.nerkh3);
            Intent fullScreenIntent = new Intent(this, FullScreenImageActivity.class);
            fullScreenIntent.setData(path);
            startActivity(fullScreenIntent);

        });
        textLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://baarbarg.ir/Content/APK/Baarbarg(1.1.0).apk"));
                startActivity(browserIntent);
            }
        });


    }
}
