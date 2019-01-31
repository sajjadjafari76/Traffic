package irstit.transport.Views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import irstit.transport.Globals;


public class CFProvider {

    private static Typeface IRANIANSANS;

    private CFProvider(){}

    public static Typeface getIRANIANSANS(Context context) {
        if (IRANIANSANS == null) {
            Log.e("CfProvider", "ok");
            IRANIANSANS = Typeface.createFromAsset(context.getAssets(), Globals.IRANSANS);
        }
        return IRANIANSANS;
    }

}
