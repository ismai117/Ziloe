package com.brand.ziloe.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, "User_Info.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table userDetail(phoneID TEXT primary key, firstname TEXT, lastname TEXT, email TEXT, address TEXT, building TEXT, city TEXT, country TEXT, postcode TEXT, phone TEXT)");
        db.execSQL("create Table userOrder(phoneID TEXT primary key, backpack INTERGER, cb1 INTERGER, cb2 INTERGER, cb3 INTERGER, subtotaL INTERGER, total INTERGER)");
        db.execSQL("create Table newsletter(phoneID TEXT primary key, email TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists userDetail");
        db.execSQL("drop Table if exists userOrder");
        db.execSQL("drop Table if exists newsletter");
    }

    public boolean insert_userDetail(String androidid, String firstname, String lastname, String email, String address, String building, String city, String country, String postcode, String phone) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues informationValues = new ContentValues();
        informationValues.put("phoneID", androidid);
        informationValues.put("firstname", firstname);
        informationValues.put("lastname", lastname);
        informationValues.put("email", email);
        informationValues.put("address", address);
        informationValues.put("building", building);
        informationValues.put("city", city);
        informationValues.put("country", country);
        informationValues.put("postcode", postcode);
        informationValues.put("phone", phone);


        long informationValues_result = db.insert("userDetail", null, informationValues);

        if (informationValues_result == -1) {
            return false;
        } else {
            return true;
        }


    }

    public boolean insert_userOrder(String androidid, int backpack, int cb1, int cb2 , int cb3 , int subtotaL, int total) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues orderValues = new ContentValues();
        orderValues.put("phoneID", androidid);
        orderValues.put("backpack", backpack);
        orderValues.put("cb1", cb1);
        orderValues.put("cb2", cb2);
        orderValues.put("cb3", cb3);
        orderValues.put("subtotal", subtotaL);
        orderValues.put("total", total);



        long orderValues_result = db.insert("userOrder", null, orderValues);

        if (orderValues_result == -1) {
            return false;
        } else {
            return true;
        }

    }

    public boolean insert_newsletter(String androidid, String email) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues subscribedValues = new ContentValues();
        subscribedValues.put("phoneID", androidid);
        subscribedValues.put("email", email);


        long subcribed_result = db.insert("newsletter", null, subscribedValues);

        if (subcribed_result == -1) {
            return false;
        } else {
            return true;
        }

    }
}
