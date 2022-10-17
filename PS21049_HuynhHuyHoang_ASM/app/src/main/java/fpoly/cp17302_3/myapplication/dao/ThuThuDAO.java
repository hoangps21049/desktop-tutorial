package fpoly.cp17302_3.myapplication.dao;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.cp17302_3.myapplication.database.DBHelper;
import fpoly.cp17302_3.myapplication.model.ThuThu;

public class ThuThuDAO {
    DBHelper dbHelper;
    SharedPreferences sharedPreferences;
    public ThuThuDAO(Context context) {
        dbHelper = new DBHelper(context);
        sharedPreferences = context.getSharedPreferences("THONGTIN", MODE_PRIVATE);
    }
    //Đăng Nhập
    public boolean checkDangNhap(String MaThuThu, String MatKhau){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM THUTHU WHERE matt = ? And MatKhau = ?", new String[]{MaThuThu, MatKhau});
        if (cursor.getCount() !=0) {
            cursor.moveToFirst();
            //Lưu sharedpreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("matt", cursor.getString(0));
            editor.putString("loaitaikhoan", cursor.getString(3));
            editor.putString("hoten", cursor.getString(1));
            editor.commit();
            return true;
        }else{
            return false;
        }
    }

    public int capNhatMatKhau(String userName, String oldPass, String newPass){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM THUTHU WHERE matt = ? AND matkhau = ?", new String[]{userName, oldPass});
        if (cursor.getCount() > 0){
            ContentValues contentValues = new ContentValues();
            contentValues.put("matkhau", newPass);
            long check = sqLiteDatabase.update("THUTHU", contentValues, "matt = ?", new String[]{userName});
            if (check == -1){
                return -1;
            }else{
                return 1;
            }
        }else {
            return 0;
        }
    }

    public ArrayList<ThuThu> getDSThuThu(){
        ArrayList<ThuThu> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM THUTHU", null);

        if (cursor.getCount() != 0){
            cursor.moveToNext();
            do {
                list.add(new ThuThu(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            }while (cursor.moveToNext());
        }

        return list;
    }

    public int xoaThongTinTT(String matt){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM PHIEUMUON WHERE matt = ?", new String[]{String.valueOf(matt)});

        long check = sqLiteDatabase.delete("THUTHU", "matt = ?", new String[]{String.valueOf(matt)});

        if (check == -1){
            return 0;
        }else {
            return 1;
        }

    }
    public boolean themThuThu(String matt,String hoten, String matkhau, String loaitaikhoan){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("matt", matt);
        contentValues.put("hoten", hoten);
        contentValues.put("matkhau", matkhau);
        contentValues.put("loaitaikhoan", loaitaikhoan);
        long check = sqLiteDatabase.insert("THUTHU", null, contentValues);
        if (check == -1){
            return false;
        }else {
            return true;
        }
    }
    public boolean capNhatThongTinTT(String matt, String hoten, String matkhau, String loaitaikhoan){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("matt", matt);
        contentValues.put("hoten", hoten);
        contentValues.put("matkhau", matkhau);
        contentValues.put("loaitaikhoan", loaitaikhoan);
        long check = sqLiteDatabase.update("THUTHU", contentValues, "matt = ?", new String[]{matt});
        if (check == -1){
            return false;
        }else {
            return true;
        }
    }
}
