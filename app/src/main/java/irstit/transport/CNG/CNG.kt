package irstit.transport.CNG

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import irstit.transport.R

class CNG : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cng)

        val imageView: ImageView = findViewById(R.id.LetterRate_Back)

        imageView.setOnClickListener { finish() }
    }


}
