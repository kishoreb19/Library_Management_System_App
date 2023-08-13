package com.example.lms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class UserDatabase extends SQLiteOpenHelper {
    private static final String DATABASENAME = "lmsdb.db";
    public UserDatabase(Context context){
        super(context,DATABASENAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE user (id INTEGER PRIMARY KEY AUTOINCREMENT,type TEXT,name TEXT,email TEXT,password TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE books(book_id INTEGER PRIMARY KEY AUTOINCREMENT,book_name TEXT,book_author TEXT,book_subject TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE location(loc_id INTEGER PRIMARY KEY AUTOINCREMENT,latitude TEXT,longitude TEXT,address TEXT,range Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists "+"lms_db.db");
        onCreate(sqLiteDatabase);
    }
    //login details
    public long addRecord(String usertype,String name1, String email1,String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues newRecord = new ContentValues();
        newRecord.put("type",usertype);
        newRecord.put("name", name1);
        newRecord.put("email",email1);
        newRecord.put("password",password);
        long x = db.insert("user",null,newRecord);
        return x;
    }
    public boolean checkRecord(String type,String email,String pwd){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM user WHERE type = ? AND email = ? AND password = ?",new String[]{type,email,pwd});
        if(c.getCount() == 1){
            return true;
        }
        else{
            return false;
        }
    }
    public Cursor getUserRecords(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT type,name,email FROM user",null);
    }
    public long delRecord(String uemail,String utype){
        SQLiteDatabase db = this.getWritableDatabase();
        long x = db.delete("user","type = ? AND email = ?",new String[]{utype,uemail});
        return x;
    }
    //Books
    public long addBooks(String b_name,String b_author,String b_subject){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("book_name",b_name);
        cv.put("book_author",b_author);
        cv.put("book_subject",b_subject);
        return db.insert("books",null,cv);
    }
    public Cursor getBooksRecords(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM books",null);
    }
    public long updateBooks(int id,String bk_name,String bk_author,String bk_subject){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("book_name",bk_name);
        cv.put("book_author",bk_author);
        cv.put("book_subject",bk_subject);
        return db.update("books",cv,"book_id = ?" ,new String[]{Integer.toString(id)});
    }
    public long delBooksRecord(String b_id,String b_name){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("books","book_id = ? AND book_name = ?",new String[]{b_id,b_name});
    }
    public ArrayList<String> getLibrarians(){
        ArrayList<String>data = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        CircularEncryption ce = new CircularEncryption();
        Cursor c = db.rawQuery("SELECT email from user where type = ?",new String[]{"Librarian"});
        int i=-1;
        String s[] = new String[c.getCount()-1];
        while (c.moveToNext()){
            //i++;
            //s[i] = ce.circularEncryption(c.getString(0),-3,-5);
            data.add(ce.circularEncryption(c.getString(0),-3,-5));
        }
        return data;
    }
    public long insertLocation(double latitude,double longitude,String address,String range){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("latitude",latitude);
        cv.put("longitude",longitude);
        cv.put("address",address);
        cv.put("range",range);
        return db.insert("location",null,cv);
    }
    public Cursor getLocationDetails(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select latitude,longitude,address,range from location",null);
    }
    public long updateLocationDetails(double latitude,double longitude,String address,String range){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("latitude",latitude);
        cv.put("longitude",longitude);
        cv.put("address",address);
        cv.put("range",range);
        return db.update("location",cv,"loc_id = ?",new String[]{"1"});
    }
}
