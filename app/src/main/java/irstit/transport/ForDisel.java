package irstit.transport;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ForDisel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_disel);


        (findViewById(R.id.ForDisel_Btn)).setOnClickListener((view) -> {
            Uri uri = Uri.parse("https://baarbarg.ir/Content/APK/Baarbarg(1.1.0).apk"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });


    }

}
