package irstit.transport;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ConnectToUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_to_us);

        ImageView Back = findViewById(R.id.ConnectToUs_Back);
        Back.setOnClickListener((View view)->{

            finish();

        });

    }
}
