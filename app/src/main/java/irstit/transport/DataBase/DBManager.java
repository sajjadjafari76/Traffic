package irstit.transport.DataBase;

import android.content.Context;

import irstit.transport.DataModel.DriverInfoModel;

public class DBManager {

    private ParentDBManger pDb; // pDb -> parent data base
    private static DBManager INSTANCE;
    private Context mContext;

    public DBManager(Context context){
        mContext = context;
    }

    public static synchronized DBManager getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = new DBManager(context);
        }
        return INSTANCE;
    }

    public boolean setDriverInfo(DriverInfoModel info) {
        pDb = new ParentDBManger(mContext);
        return pDb.setDriverInfo(info);
    }

    public DriverInfoModel getDriverInfo() {
        pDb = new ParentDBManger(mContext);
        return pDb.getDriverInfo();
    }


}
