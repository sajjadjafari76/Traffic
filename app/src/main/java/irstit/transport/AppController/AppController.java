package irstit.transport.AppController;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.io.File;

import irstit.transport.DataBase.ParentDBManger;

public class AppController extends Application {

    public static final String TAG = AppController.class
            .getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        if (!databaseExist()) {
            Log.e(TAG, "Database11 : database not create");
            new ParentDBManger(getApplicationContext());
//            AccessToken token = new AccessToken();
//            token.setStatus(0);
//            token.setAccessToken("");
////            token.setChildAccessToken("");
//            DBManager.getInstance(this).setTokenData(token);
        } else {
//            Log.e(TAG, "Database : database created");
        }

    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }


    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    private boolean databaseExist() {
//        Log.e(TAG , "Database : " + getDatabasePath(ParentDBManger.DATABASE_NAME).getPath());
        File dbFile = new File(getDatabasePath(ParentDBManger.DATABASE_NAME).getPath());
        return dbFile.exists();
    }
}