package irstit.transport.ViewPager.ViewInfo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import irstit.transport.R;

public class AboutTeam extends AppCompatActivity {

    TextView text, text_about, textView_site, textView_tel, textView_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_create);

        text = findViewById(R.id.text);
        text_about = findViewById(R.id.text_about);
        textView_site = findViewById(R.id.text_about_site);
        textView_tel = findViewById(R.id.text_about_tel);
        textView_address = findViewById(R.id.text_about_address);
        text.setText("شرکت فناوران هوشمند ایده پرداز");
        text_about.setText("تیم فناوران در سال 1390 به منظور فعاليت در زمينه فناوري اطلاعات در کنار هم تشکیل گردید . توانستیم شرکت را  در سال 94 تاسيس کنیم و راه پر فراز و نشيب رشد و پيشرفت در اين عرصه را به منظور کسب تجربه و خدمت به پيشرفت تکنولوژي در کشور با همراهي مديران و متخصصين علوم فناوري اطلاعات و ارتباطات بپیمائیم.\n" +
                "همواره اهداف مديران متعهد و همکاران متخصص ما بر شناخت نيازهاي مشتريان و ارائه خدمات بهينه مبتني بر پيشرفته ترين تکنولوژي هاي روز دنيا به سراسر کشور مي باشد.\n" +
                "هم اکنون اين مجموعه شامل  واحد جداگانه طراحي  و اجرای اپلیکیشن های هوشمند موبایل، خدمات پشتيباني و تعميرات و نگهداري ، انیمیشن های موبایل و ویندوز ، نرم افزار، خدمات وب و پهناي باند، ، نرم افزار های ویندوزی، پردازش تصویر در حوزه های پلاک ، تصویر و آموزش نيروي متخصص مي باشد و با گردآوري اين بخشها تحت نامي يکسان سعي در ارائه کامل خدمات IT به سازمان ها و نهادهاي دولتي ، شرکتهاي خصوصي و اشخاص دارد.\n" +
                "در این مدت کوتاه توانستیم استارتاپ های موفقی در استان قزوین اجرا کرده و کسب رتبه های برتر استارپ های مختلف را کسب نمائیم.\n" +
                "بالا بردن کيفيت کارهاي ارائه شده در چهارچوب استانداردهاي حاکم در دنيا، امري نيست که به طور اتفاقي حاصل گردد. ديدگاه بلندمدت در تحقيق، توليد، عرضه و پشتيباني ازجمله نگرش راهبردي ما در کار است، نگرشي که به شهادت آنچه انجام گرفته است براي مجموعه فناوري اطلاعات   شرکت تخصص و براي مشتريان آن رضايت را به همراه داشته است .\n" +
                "اميد است که از تجربه و تخصصمان براي يافتن بهترين نتيجه بهره گيريم.\n"
        );


        textView_tel.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel: 02833656232"));
            startActivity(intent);
        });
        textView_address.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.google.com/maps/place/Qazvin+Science+%26+Technology+Park/@36.3261131,50.0386099,16.98z/data=!4m5!3m4!1s0x3f8b556b1dd64bbd:0xec9c537352c20416!8m2!3d36.3263326!4d50.0419897"));
            startActivity(intent);
        });
        textView_site.setText("وب سایت: stit.ir");
        textView_site.setOnClickListener(v -> {
            String url = "http://stit.ir";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });


    }

}
