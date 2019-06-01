package irstit.transport.CNG

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.TextView
import irstit.transport.R

class CNG : AppCompatActivity() {

  var h : TextView? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cng)

        h = findViewById(R.id.car_appereance);
        val names = listOf("آزمون آلاینده\u200Cهای خروجی از خودرو","آزمون چراغ\u200C های جلو","آزمون لغزش جانبی","آزمون کمک فنر","آزمون ترمزها","آزمون اتصالات جلوبندی و اهرم بندی","صدور برگه معاینه فنی")
        for (name in names.indices) {
//            h.setText(names[name])
        }


    }


}
