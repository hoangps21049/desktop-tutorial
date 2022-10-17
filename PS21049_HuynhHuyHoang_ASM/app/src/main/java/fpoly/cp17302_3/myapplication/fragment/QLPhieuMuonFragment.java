package fpoly.cp17302_3.myapplication.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import fpoly.cp17302_3.myapplication.R;
import fpoly.cp17302_3.myapplication.adapter.PhieuMuonAdapter;
import fpoly.cp17302_3.myapplication.dao.PhieuMuonDAO;
import fpoly.cp17302_3.myapplication.dao.SachDAO;
import fpoly.cp17302_3.myapplication.dao.ThanhVienDAO;
import fpoly.cp17302_3.myapplication.model.PhieuMuon;
import fpoly.cp17302_3.myapplication.model.Sach;
import fpoly.cp17302_3.myapplication.model.ThanhVien;

public class QLPhieuMuonFragment extends Fragment {
    PhieuMuonDAO phieuMuonDAO;
    RecyclerView recyclerPhieuMuon;
    ArrayList<PhieuMuon> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlphieumuon, container, false);

        recyclerPhieuMuon = view.findViewById(R.id.recyclerPhieuMuon);
        FloatingActionButton floatAdd = view.findViewById(R.id.floatAdd);

        //adapter
        loadData();

        //dialog
        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiaLog();
            }
        });
        return view;
    }

    private void loadData() {
        phieuMuonDAO = new PhieuMuonDAO(getContext());
        list = phieuMuonDAO.getDSPhieuMuon();

        //Log.d("dulieu", String.valueOf(list.size()));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerPhieuMuon.setLayoutManager(linearLayoutManager);

        PhieuMuonAdapter adapter = new PhieuMuonAdapter(list, getContext());
        recyclerPhieuMuon.setAdapter(adapter);
    }

    private void showDiaLog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_themphieumuon, null);
        Spinner spnThanhVien = view.findViewById(R.id.spnThanhVien);
        Spinner spnSach = view.findViewById(R.id.spnSach);
        //EditText edtTien = view.findViewById(R.id.edtTien);

        getDataThanhVien(spnThanhVien);
        getDataSach(spnSach);

        builder.setView(view);

        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Lấy mã thành viên
                HashMap<String, Object> hsTV = (HashMap<String, Object>) spnThanhVien.getSelectedItem();
                int matv = (int) hsTV.get("matv");

                //Lấy mã sách
                HashMap<String, Object> hsSach = (HashMap<String, Object>) spnSach.getSelectedItem();
                int masach = (int) hsSach.get("masach");

                //int tien = Integer.parseInt(edtTien.getText().toString());

                int tien = (int) hsSach.get("giathue");
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("THONGTIN", Context.MODE_PRIVATE);
                String matt = sharedPreferences.getString("matt", "");

                //Lấy ngày hiện tại
                Date curentTime = Calendar.getInstance().getTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String ngay = simpleDateFormat.format(curentTime);

                PhieuMuon phieuMuon = new PhieuMuon(matv, matt, masach, ngay, 0, tien);

                boolean kiemtra = phieuMuonDAO.themPhieuMuon(phieuMuon);
                if (kiemtra) {
                    Toast.makeText(getContext(), "Thêm phiếu mượn thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                } else {
                    Toast.makeText(getContext(), "Thêm phiếu mượn thất bại", Toast.LENGTH_SHORT).show();
                }

            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void getDataThanhVien(Spinner spnThanhVien) {
        ThanhVienDAO thanhVienDAO = new ThanhVienDAO(getContext());

        ArrayList<ThanhVien> list = thanhVienDAO.getDSThanhVien();

        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (ThanhVien tv : list) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("matv", tv.getMatv());
            hs.put("hoten", tv.getHoten());
            listHM.add(hs);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"hoten"},
                new int[]{android.R.id.text1});
        spnThanhVien.setAdapter(simpleAdapter);
    }

    private void getDataSach(Spinner spnSach) {
        SachDAO sachDAO = new SachDAO(getContext());

        ArrayList<Sach> list = sachDAO.getDSDauSach();

        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (Sach sc : list) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("masach", sc.getMasach());
            hs.put("tensach", sc.getTensach());
            hs.put("giathue", sc.getGiathue());
            listHM.add(hs);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"tensach"},
                new int[]{android.R.id.text1});
        spnSach.setAdapter(simpleAdapter);
    }


}
