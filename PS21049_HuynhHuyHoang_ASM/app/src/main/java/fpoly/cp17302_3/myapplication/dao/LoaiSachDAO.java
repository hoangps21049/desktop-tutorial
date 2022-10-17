package fpoly.cp17302_3.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.cp17302_3.myapplication.database.DBHelper;
import fpoly.cp17302_3.myapplication.model.LoaiSach;

public class LoaiSachDAO {
    DBHelper dbHelper;
    public LoaiSachDAO(Context context){
        dbHelper = new DBHelper(context);
    }

    //Lấy danh sách loại sách
    public ArrayList<LoaiSach> getDSLoaiSach(){
        ArrayList<LoaiSach> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM LOAISACH", null);
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                list.add(new LoaiSach(cursor.getInt(0), cursor.getString(1)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    //Thêm loại sách
    public boolean themLoaiSach(String tenloai){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenloai", tenloai);
        long check = sqLiteDatabase.insert("LOAISACH", null, contentValues);
        if (check == -1){
            return false;
        }else {
            return true;
        }
    }

    //1: Xóa thành công
    //0: Xóa thất bại
    //-1: Có sách tồn tại trong thể loại đó
    public int xoaLoaiSach(int id){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM SACH WHERE maloai = ?", new String[]{String.valueOf(id)});
        if (cursor.getCount() != 0){
            return -1;
            //-1: Có sách tồn tại trong thể loại đó
        }

        long check = sqLiteDatabase.delete("LOAISACH", "maloai = ?", new String[]{String.valueOf(id)});
        if (check == -1){
            return 0;
            //0: Xóa thất bại
        }else {
            return 1;
            //1: Xóa thành công
        }

    }

    public boolean thayDoiLoaiSach(LoaiSach loaiSach){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenloai", loaiSach.getTenloai());
        long check = sqLiteDatabase.update("LOAISACH", contentValues, "maloai = ?", new String[]{String.valueOf(loaiSach.getId())});
        if (check == -1){
            return false;
        }else {
            return true;
        }
    }
}
