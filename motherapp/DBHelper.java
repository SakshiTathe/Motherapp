package com.example.motherapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="Customer.db";

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }
    //when there is no database and the app needs one.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE cust_info(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT ,phone INTEGER, neck INTEGER DEFAULT null, hand INTEGER DEFAULT null, height INTEGER DEFAULT null, chest INTEGER DEFAULT null,waist INTEGER DEFAULT null)";
        db.execSQL(query);
        String billquery="CREATE TABLE bill_info(Bid INTEGER PRIMARY KEY AUTOINCREMENT," +
                "cust_id INTEGER,description TEXT, photo BLOB, price INTEGER, paid_price INTEGER DEFAULT 0 ,givenDate TEXT DEFAULT null,received_date TEXT DEFAULT null)";
        db.execSQL(billquery);

    }
    public void insertCust(String name, int phone){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put("name",name);
        cv.put("phone",phone);

        cv.put("neck",0);
        cv.put("hand",0);
        cv.put("height",0);
        cv.put("chest",0);
        cv.put("waist",0);

        db.insert("cust_info",null,cv);
        Log.d("DB", "Inserting: " + name + ", " + phone);

    }
    public List<CustomModel> getallcust(){
        List<CustomModel> list1=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT FROM cust_info",null);
        if(cursor.moveToNext()){
            do{
                int id=cursor.getInt(0);
                String name=cursor.getString(1);
                int phone=cursor.getInt(2);
                list1.add(new CustomModel(id, name, phone));
                Log.d("DB", "Inserting: " + name + ", " + phone);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list1;
    }

    public void insertBill(String description, byte[] photosbyte,int price,String givendate,int custid){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put("description",description);
        cv.put("photo",photosbyte);
        cv.put("price",price);
        cv.put("givenDate",givendate);
        cv.put("cust_id",custid);
        db.insert("bill_info",null,cv);
    }
    public List<BillModel> getallBill(int custID){
        List<BillModel> list1=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT FROM bill_info WHERE cust_id=?",new String[]{String.valueOf(custID)});
        if(cursor !=null && cursor.getCount()>0){
            while (cursor.moveToNext()){
                int id=cursor.getInt(0);
                String desc=cursor.getString(2);
                byte[] photos=cursor.getBlob(3);
                int prioce=cursor.getInt(4);
                String date=cursor.getString(5);
                String recdate=cursor.getString(6);
                int paadprice=cursor.getInt(7);
                list1.add(new BillModel(id, desc, photos,prioce,date,recdate,paadprice));
                Log.d("DB", "Inserting: " + desc + ", " );
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return list1;
    }
    public void update_pricedate(int custID,int billID,String newDate, int newPrice){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put("received_date", newDate);
        cv.put("paid_price", newPrice);
        db.update("bill_info", cv, "cust_id = ? AND Bid = ?", new String[]{
                String.valueOf(custID), String.valueOf(billID)
        });
        Log.d("DB", "Updated price/date for custID=" + custID + ", billID=" + billID);
    }

    public void update_measure(int custID,String columnName,float updatem){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put(columnName,updatem);
        db.update("cust_info",cv,"id=?",new String[]{String.valueOf(custID)});
    }
    public List<Cust_Measure_model> getallMeasures(int custID){
        List<Cust_Measure_model> list1 = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT hand, chest, neck, waist, height FROM cust_info WHERE id=?", new String[]{String.valueOf(custID)});

        if (cursor != null && cursor.moveToFirst()) {
            list1.add(new Cust_Measure_model(1, "Hand", cursor.getFloat(0)));
            list1.add(new Cust_Measure_model(2, "Chest", cursor.getFloat(1)));
            list1.add(new Cust_Measure_model(3, "Neck", cursor.getFloat(2)));
            list1.add(new Cust_Measure_model(4, "Waist", cursor.getFloat(3)));
            list1.add(new Cust_Measure_model(5, "Height", cursor.getFloat(4)));
            cursor.close();
        }
        return list1;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS cust_info");
        onCreate((db));
    }
}





 