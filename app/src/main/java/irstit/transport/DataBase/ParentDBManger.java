package irstit.transport.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import irstit.transport.DataModel.DriverInfoModel;

public class ParentDBManger extends SQLiteOpenHelper {

    private static final String TAG = "ParentDBManger";

    public enum TokenType {
        AccessToken,
        Status
    }

    public static final String DATABASE_NAME = "traffictakestan_db";
    private static final int DATABASE_VERSION = 1;
    private static final String USER_TABLE_NAME = "tbl_user";


    private final String USER_COL_ID = "col_id";
    private final String USER_COL_NAME = "col_name";
    private final String USER_COL_FAMILY = "col_family";
    private final String USER_COL_PARENT = "col_parent";
    private final String USER_COL_NATIONAL_CODE = "col_national_code";
    private final String USER_COL_TELEPHONE = "col_telephone";
    private final String USER_COL_BIRTH_CERTIFICATE = "col_birth_certificate";
    private final String USER_COL_VEHICLE_CODE = "col_vehicle_code";
    private final String USER_COL_VEHICLE_PELAK = "col_vehicle_pelak";
    private final String USER_COL_VEHICLE_MODEL = "col_vehicle_model";
    private final String USER_COL_VEHICLE_TYPE = "col_vehicle_type";
    private final String USER_COL_LINE_TYPE = "col_line_type";
    private final String USER_COL_REGISTER_DATE = "col_registerdate";
    private final String USER_COL_PICTURE = "col_picture";
    private final String USER_COL_Owner = "col_owner";
    private final String USER_COL_IS_TAXI = "col_is_taxi";
    private final String USER_COL_OWNER_ID = "col_owner_id";
    private final String USER_TABLE = "CREATE TABLE IF NOT EXISTS " + USER_TABLE_NAME + "(" +
            USER_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            USER_COL_NAME + " TEXT, " +
            USER_COL_FAMILY + " TEXT, " +
            USER_COL_PARENT + " TEXT, " +
            USER_COL_NATIONAL_CODE + " TEXT, " +
            USER_COL_TELEPHONE + " TEXT, " +
            USER_COL_BIRTH_CERTIFICATE + " TEXT, " +
            USER_COL_VEHICLE_CODE + " TEXT, " +
            USER_COL_VEHICLE_PELAK + " TEXT, " +
            USER_COL_VEHICLE_MODEL + " TEXT, " +
            USER_COL_VEHICLE_TYPE + " TEXT, " +
            USER_COL_LINE_TYPE + " TEXT, " +
            USER_COL_REGISTER_DATE + " TEXT, " +
            USER_COL_PICTURE + " TEXT, " +
            USER_COL_Owner + " TEXT, " +
            USER_COL_IS_TAXI + " TEXT, " +
            USER_COL_OWNER_ID + " TEXT );";


    public ParentDBManger(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(USER_TABLE);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    boolean setDriverInfo(DriverInfoModel drivers) {

        try {
            long status = -11;
            SQLiteDatabase database = getWritableDatabase();

            ContentValues cp = new ContentValues();
            cp.put(USER_COL_NAME, drivers.getName());
            cp.put(USER_COL_FAMILY, drivers.getFamily());
            cp.put(USER_COL_PARENT, drivers.getParent());
            cp.put(USER_COL_NATIONAL_CODE, drivers.getNationalCode());
            cp.put(USER_COL_TELEPHONE, drivers.getTelephone());
            cp.put(USER_COL_BIRTH_CERTIFICATE, drivers.getBirthCertificate());
            cp.put(USER_COL_VEHICLE_PELAK, drivers.getVehiclePelak());
            cp.put(USER_COL_VEHICLE_MODEL, drivers.getVehicleModel());
            cp.put(USER_COL_LINE_TYPE, drivers.getLineType());
            cp.put(USER_COL_REGISTER_DATE, drivers.getRegisterDate());
            cp.put(USER_COL_VEHICLE_TYPE, drivers.getVehicleType());
            cp.put(USER_COL_VEHICLE_CODE, drivers.getVehicleCode());
            cp.put(USER_COL_PICTURE, drivers.getPicture());
            cp.put(USER_COL_Owner, drivers.getOwner());
            cp.put(USER_COL_IS_TAXI, drivers.getIsTaxi());
            cp.put(USER_COL_OWNER_ID, drivers.getOwnerId());

            status = database.insert(USER_TABLE_NAME, null, cp);

            Log.e("childApps insert : ", status + " |");
            if (status > 0) {
                database.close();
                return true;
            } else {
                database.close();
                return false;
            }

        } catch (Exception e) {
            e.toString();
            Log.e(TAG, e.toString());
            return false;
        }
    }

    boolean updateDriverInfo(String phone, String newPhone) {

        try {
            long status = -11;
            SQLiteDatabase database = getWritableDatabase();

            ContentValues cp = new ContentValues();
            cp.put(USER_COL_TELEPHONE, newPhone);

            status = database.update(USER_TABLE_NAME, cp, USER_COL_TELEPHONE+"="+phone, null);

            Log.e("childApps updated : ", status + " |");
            if (status > 0) {
                database.close();
                return true;
            } else {
                database.close();
                return false;
            }

        } catch (Exception e) {
            e.toString();
            Log.e(TAG, e.toString());
            return false;
        }
    }

    DriverInfoModel getDriverInfo() {

        DriverInfoModel driver = new DriverInfoModel();

        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + USER_TABLE_NAME, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                driver.setName(cursor.getString(1));
                driver.setFamily(cursor.getString(2));
                driver.setParent(cursor.getString(3));
                driver.setNationalCode(cursor.getString(4));
                driver.setTelephone(cursor.getString(5));
                driver.setBirthCertificate(cursor.getString(6));
                driver.setVehicleCode(cursor.getString(7));
                driver.setVehiclePelak(cursor.getString(8));
                driver.setVehicleModel(cursor.getString(9));
                driver.setVehicleType(cursor.getString(10));
                driver.setLineType(cursor.getString(11));
                driver.setRegisterDate(cursor.getString(12));
                driver.setPicture(cursor.getString(13));
                driver.setOwner(cursor.getString(14));
                driver.setIsTaxi(cursor.getString(15));
                driver.setOwnerId(cursor.getString(16));
                cursor.moveToNext();
            }
        }
        cursor.close();
        database.close();
        return driver;
    }

    void deleteDrivers() {
        SQLiteDatabase database = getReadableDatabase();
        database.execSQL("delete from " + USER_TABLE_NAME);
    }
//
//  String getMaxTimeChildApps (String packageName) {
//
//    String data = null;
//    Cursor cursor = null;
//    try {
//
//      SQLiteDatabase database = getReadableDatabase();
//      cursor = database.rawQuery("SELECT " + CHILD_APPS_COL_MAX_TIME + " FROM " + CHILD_APPS_TABLE_NAME + " WHERE " + CHILD_APPS_COL_PACKAGE_NAME + "=" + "'" + packageName + "'", null);
//
//
//      cursor.moveToFirst();
//      if (cursor.getCount() > 0) {
//        while (!cursor.isAfterLast()) {
//          data = cursor.getString(0);
//          cursor.moveToNext();
//        }
//      }
//      cursor.close();
//      database.close();
//    } catch (Exception e) {
//      Log.e("eroordatabase", e.toString() + " |");
//    }
//
//
//    return data;
//  }
//
//  boolean updateMaxTime () {
//
//    try {
//      ContentValues cp = new ContentValues();
//      cp.put(CHILD_APPS_COL_MAX_TIME, "0");
//
//      SQLiteDatabase database = getWritableDatabase();
////            database.delete(CHILD_TABLE_NAME,)
//      long status = database.update(CHILD_APPS_TABLE_NAME, cp, CHILD_APPS_COL_MAX_TIME + "=?", new String[]{"1"});
//
//      Log.e("child update : ", status + " |");
//      if (status > 0) {
//        database.close();
//        return true;
//      } else {
//        database.close();
//        return false;
//      }
//
//    } catch (Exception e) {
//      e.toString();
//      Log.e(TAG, e.toString());
//      return false;
//    }
//  }


//
//  public boolean deleteDB (Context context) {
//    if (Utils.getInstance(context).dataBaseExist()) {
//      return context.deleteDatabase(ParentDBManger.DATABASE_NAME);
//    }
//    return false;
//  }
}
