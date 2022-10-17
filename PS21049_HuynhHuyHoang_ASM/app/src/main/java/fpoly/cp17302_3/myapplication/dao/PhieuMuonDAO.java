package fpoly.cp17302_3.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import fpoly.cp17302_3.myapplication.database.DBHelper;
import fpoly.cp17302_3.myapplication.model.PhieuMuon;

public class PhieuMuonDAO {
    DBHelper dbHelper;

    public PhieuMuonDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public ArrayList<PhieuMuon> getDSPhieuMuon() {
        ArrayList<PhieuMuon> list = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT pm.mapm, pm.matv, tv.hoten, pm.matt, tt.hoten, pm.masach, sc.tensach, pm.ngay, pm.trasach, pm.tienthue FROM PHIEUMUON pm, THANHVIEN tv, THUTHU tt, SACH sc  WHERE pm.matv = tv.matv and pm.matt = tt.matt AND pm.masach = sc.masach ORDER BY pm.mapm DESC", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new PhieuMuon(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getString(6), cursor.getString(7), cursor.getInt(8), cursor.getInt(9)));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public boolean thayDoiTrangThai(int mapm) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("trasach", 1);
        long check = sqLiteDatabase.update("PHIEUMUON", contentValues, "mapm = ?", new String[]{String.valueOf(mapm)});
        if (check == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean themPhieuMuon(PhieuMuon phieuMuon) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("matv", phieuMuon.getMatv());
        contentValues.put("matt", phieuMuon.getMatt());
        contentValues.put("masach", phieuMuon.getMasach());
        contentValues.put("ngay", phieuMuon.getNgay());
        contentValues.put("trasach", phieuMuon.getTrasach());
        contentValues.put("tienthue", phieuMuon.getTienthue());

        long check = sqLiteDatabase.insert("PHIEUMUON", null, contentValues);
        if (check == -1) {
            return false;
        } else {
            return true;
        }

    }

}
