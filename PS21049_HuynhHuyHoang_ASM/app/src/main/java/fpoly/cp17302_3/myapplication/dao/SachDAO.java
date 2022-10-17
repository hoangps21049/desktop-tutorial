package fpoly.cp17302_3.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.cp17302_3.myapplication.database.DBHelper;
import fpoly.cp17302_3.myapplication.model.Sach;

public class SachDAO {
    DBHelper dbHelper;
    public SachDAO(Context context){
        dbHelper = new DBHelper(context);
    }

    //Lấy toàn bọ đầu sách có ở trong thư viện
    public ArrayList<Sach> getDSDauSach(){
        ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT sc.masach, sc.tensach, sc.giathue, sc.maloai, ls.tenloai FROM SACH sc, LOAISACH ls WHERE sc.maloai = ls.maloai", null);
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                list.add(new Sach(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3), cursor.getString(4)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public boolean themSachMoi(String tensach, int giathue, int maloai){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tensach", tensach);
        contentValues.put("giathue", giathue);
        contentValues.put("maloai", maloai);
        long check = sqLiteDatabase.insert("SACH", null, contentValues);
        if (check == -1){
            return false;
        }else {
            return true;
        }

    }

    public boolean capNhatThongTinSach(int masach, String tensach, int giathue, int maloai){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tensach", tensach);
        contentValues.put("giathue", giathue);
        contentValues.put("maloai", maloai);
        long check = sqLiteDatabase.update("SACH", contentValues, "masach = ?", new String[]{String.valueOf(masach)});

        if (check == -1){
            return false;
        }else {
            return true;
        }

    }

    public int xoaSach(int masach){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM PHIEUMUON WHERE masach = ? ", new String[]{String.valueOf(masach)});
        if (cursor.getCount() != 0){
            return -1;
        }

        long check = sqLiteDatabase.delete("SACH", "masach = ?", new String[]{String.valueOf(masach)});

        if (check == -1){
            return 0;
        }else {
            return 1;
        }

    }

}
