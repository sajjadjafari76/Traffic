package irstit.transport.Views;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static irstit.transport.AppController.AppController.TAG;

public class Utils {


    private static Utils INSTANCE;
    private Context mContext;

    public Utils(Context context) {
        mContext = context;
    }

    public static synchronized Utils getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new Utils(context);
        }
        return INSTANCE;
    }

    public boolean isOnline() {
        ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            return (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI
                    ||
                    activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) ? true : false;
        }
        return false;
    }

    public boolean isConnectingToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (NetworkInfo anInfo : info)
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }

        return false;
    }

    public boolean checkWriteExternalPermission() {
        String permission = android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
        int res = mContext.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public File getPublicAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("Image_Log", "Directory not created");
        }
        return file;
    }

    public boolean hasInternetAccess() {

        final Boolean[] status = {false};
        if (isOnline()) {
            new Thread(() -> {
                try {
                    HttpURLConnection urlc = (HttpURLConnection)
                            (new URL("http://clients3.google.com/generate_204")
                                    .openConnection());
                    urlc.setRequestProperty("User-Agent", "Android");
                    urlc.setRequestProperty("Connection", "close");
                    urlc.setConnectTimeout(1500);
                    urlc.connect();
                    status[0] = urlc.getResponseCode() == 204 &&
                            urlc.getContentLength() == 0;
                } catch (IOException e) {
                    Log.e(TAG, "Error checking internet connection", e);
                }
            }).start();


        } else {
            Log.d(TAG, "No network available!");
        }
        return status[0];
    }

    public void getlog(String veryLongString) {
        int maxLogSize = 1500;
        for (int i = 0 ; i <= veryLongString.length() / maxLogSize ; i++) {
            int start = i * maxLogSize;
            int end = (i + 1) * maxLogSize;
            end = end > veryLongString.length() ? veryLongString.length() : end;
            Log.e("ShowAssChild1", veryLongString.substring(start, end));
        }
    }

    public double getDeviceSize(Activity activity) {

        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        double wi = (double) width / (double) dm.xdpi;
        double hi = (double) height / (double) dm.ydpi;
        double x = Math.pow(wi, 2);
        double y = Math.pow(hi, 2);

        return Math.sqrt(x + y);
    }

    public void getDeviceSize1(Activity activity) {

        int screenSize = activity.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;

        String toastMsg;
        switch (screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                toastMsg = "Large screen";
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                toastMsg = "Normal screen";
                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                toastMsg = "Small screen";
                break;
            default:
                toastMsg = "Screen size is neither large, normal or small";
        }
        Toast.makeText(activity, toastMsg, Toast.LENGTH_LONG).show();

    }

    public String DecodeData(String data) {
        byte[] decededString = Base64.decode(data, Base64.DEFAULT);
        String stringconverted = new String(decededString, StandardCharsets.UTF_8);
        Log.e("dnmc", stringconverted + " |");
        String newData =  new String(Base64.decode
                (stringconverted.substring(20, stringconverted.length() - 20 ), Base64.DEFAULT), StandardCharsets.UTF_8);
        Log.e("dnmc1", newData + " |");
        Log.e("dnmc2", new String(Base64.decode(newData, Base64.DEFAULT), StandardCharsets.UTF_8) + " |");
        return new String(Base64.decode(newData, Base64.DEFAULT), StandardCharsets.UTF_8);
    }

    public String DecodeData1(String data) {
        byte[] decededString = Base64.decode(data, Base64.DEFAULT);
        String stringconverted = new String(decededString, StandardCharsets.UTF_8);
        Log.e("dnmc", stringconverted + " |");
        String newData =  new String(Base64.decode
                (stringconverted.substring(10, stringconverted.length() - 10 ), Base64.DEFAULT), StandardCharsets.UTF_8);
        Log.e("dnmc1", newData + " |");
//        Log.e("dnmc2", new String(Base64.decode(newData, Base64.DEFAULT), StandardCharsets.UTF_8) + " |");
        return newData;
    }

    public String EncodeData(String data) {
        byte[] decededString = Base64.encode(data.getBytes(), Base64.DEFAULT);

        byte[] array = new byte[20]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));

        byte[] array1 = new byte[20]; // length is bounded by 7
        new Random().nextBytes(array1);
        String generatedString1 = new String(array, StandardCharsets.UTF_8);


        String stringconverted = new String(decededString, StandardCharsets.UTF_8);

        stringconverted = generatedString + stringconverted + generatedString1;

        return new String(Base64.encode(stringconverted.getBytes(), Base64.DEFAULT), StandardCharsets.UTF_8);
    }

    public String convertStatus(int status) {
        switch (status) {
            case 0:
                return "در انتظار بررسی";
            case 1:
                return "تایید شده";
            case 2:
                return "تایید نشده";
        }
        return "";
    }

}
