package fpoly.cp17302_3.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context){
        super(context, "DANGKYMONHOC", null, 1);
    }

    //Nơi tạo ra những bảng trong database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String dbThuThu = "create table THUTHU(matt text primary key,hoten text,matkhau text, loaitaikhoan text)";
        db.execSQL(dbThuThu);

        String dbThanhVien = "create table THANHVIEN(matv integer primary key autoincrement, hoten text, namsinh text)";
        db.execSQL(dbThanhVien);

        String dbLoai = "create table LOAISACH(maloai integer primary key autoincrement, tenloai text)";
        db.execSQL(dbLoai);

        String dbSach = "create table SACH(masach integer primary key autoincrement, tensach text, giathue integer, maloai integer references LOAISACH(maloai))";
        db.execSQL(dbSach);

        String dbPhieuMuon = "create table PHIEUMUON(mapm integer primary key autoincrement, matv integer references THANHVIEN(matv), matt text references THUTHU(matt), masach integer references SACH(masach), ngay text, trasach integer, tienthue integer)";
        db.execSQL(dbPhieuMuon);

        //data mẫu
        db.execSQL("INSERT INTO LOAISACH VALUES (1, 'Thiếu nhi'),(2,'Tình cảm'),(3, 'Giáo khoa')");
        db.execSQL("INSERT INTO SACH VALUES (1, 'Hãy đợi đấy', 2500, 1), (2, 'Thằng cuội', 1000, 1), (3, 'Lập trình Android', 2000, 3)");
        db.execSQL("INSERT INTO THUTHU VALUES ('thuthu01','Nguyễn Văn Anh','123456', 'Admin'),('thuthu02','Trần Văn Hùng','123456', 'thuthu')");
        db.execSQL("INSERT INTO THANHVIEN VALUES (1,'Cao Thu Trang','2000'),(2,'Trần Mỹ Kim','2000')");
        //trả sách: 1: đã trả - 0: chưa trả
        db.execSQL("INSERT INTO PHIEUMUON VALUES (1,1,'thuthu01', 1, '19/03/2022', 1, 2500),(2,1,'thuthu01', 3, '19/03/2022', 0, 2000),(3,2,'thuthu02', 1, '19/03/2022', 1, 2000)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if (i != i1){
            db.execSQL("DROP TABLE IF EXISTS THUTHU");
            db.execSQL("DROP TABLE IF EXISTS THANHVIEN");
            db.execSQL("DROP TABLE IF EXISTS SACH");
            db.execSQL("DROP TABLE IF EXISTS LOAISACH");
            db.execSQL("DROP TABLE IF EXISTS PHIEUMUON");
            onCreate(db);
        }
    }
}
