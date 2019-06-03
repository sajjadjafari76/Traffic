package irstit.transport.Drivers;

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

public class TechnicalDiagnosis extends AppCompatActivity {

    TextView txt_technical;
    ImageView imageBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technical_diagnosis);
        txt_technical = findViewById(R.id.link_technical);
        imageBack = findViewById(R.id.LetterRate_Back);

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        SpannableString ss = new SpannableString(txt_technical.getText());

        ClickableSpan clickableTerms = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                // show toast here
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://s1.symfa.ir/TestCenters/Inquiry/VinPetrolInquiry"));
                startActivity(browserIntent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);

            }
        };
        ss.setSpan(clickableTerms, 0,txt_technical.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(Color.BLUE), 0, txt_technical.length(), 0);
        txt_technical.setText(ss);
        txt_technical.setMovementMethod(LinkMovementMethod.getInstance());
        txt_technical.setHighlightColor(Color.TRANSPARENT);

    }
}
