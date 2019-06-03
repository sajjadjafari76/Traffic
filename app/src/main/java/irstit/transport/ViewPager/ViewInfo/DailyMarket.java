package irstit.transport.ViewPager.ViewInfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import irstit.transport.R;

public class DailyMarket extends AppCompatActivity {

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_market);
        imageView = findViewById(R.id.LetterRate_Back);
        imageView.setOnClickListener(v -> finish());
    }
}
