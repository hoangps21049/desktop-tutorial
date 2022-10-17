package fpoly.cp17302_3.myapplication.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.HashMap;

import fpoly.cp17302_3.myapplication.R;
import fpoly.cp17302_3.myapplication.adapter.SachAdapter;
import fpoly.cp17302_3.myapplication.dao.LoaiSachDAO;
import fpoly.cp17302_3.myapplication.dao.SachDAO;
import fpoly.cp17302_3.myapplication.model.LoaiSach;
import fpoly.cp17302_3.myapplication.model.Sach;

public class QLSachFragment extends Fragment {
    RecyclerView recyclerViewSach;
    SachDAO sachDAO;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlsach, container, false);

        recyclerViewSach = view.findViewById(R.id.recyclerSach);
        FloatingActionButton floatAdd = view.findViewById(R.id.floatAdd);
        sachDAO = new SachDAO(getContext());

        loadData();

        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    showDiaLog();
            }
        });
        return view;
    }
    private void loadData(){
        ArrayList<Sach> list = sachDAO.getDSDauSach();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewSach.setLayoutManager(linearLayoutManager);

        SachAdapter adapter = new SachAdapter(getContext(), list, getDSLoaiSach(), sachDAO);
        recyclerViewSach.setAdapter(adapter);
    }

    private void showDiaLog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_them_sach, null);
        builder.setView(view);

        EditText edtTenSach = view.findViewById(R.id.edtTenSach);
        EditText edtTien = view.findViewById(R.id.edtTien);
        Spinner spnLoaiSach = view.findViewById(R.id.spnLoaiSach);

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                getDSLoaiSach(),
                android.R.layout.simple_list_item_1,
                new String[]{"tenloai"},
                new int[]{android.R.id.text1}
        );
        spnLoaiSach.setAdapter(simpleAdapter);


        builder.setNegativeButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tensach = edtTenSach.getText().toString();
                String tien = edtTien.getText().toString();
                HashMap<String, Object> hs = (HashMap<String, Object>) spnLoaiSach.getSelectedItem();
                int maloai = (int) hs.get("maloai");
                if(tien.equalsIgnoreCase("" )|| tensach.equalsIgnoreCase("")){
                    Toast.makeText(getContext(), "Không được để rỗng", Toast.LENGTH_SHORT).show();
                }else {
                    int tien1 = Integer.parseInt(tien);
                    boolean check = sachDAO.themSachMoi(tensach, tien1, maloai);
                    if (check == true){
                        Toast.makeText(getContext(), "Thêm sách thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                    }else {
                        Toast.makeText(getContext(), "Thêm sách thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private ArrayList<HashMap<String, Object>> getDSLoaiSach(){
        LoaiSachDAO loaiSachDAO = new LoaiSachDAO(getContext());
        ArrayList<LoaiSach> list = loaiSachDAO.getDSLoaiSach();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();

        for(LoaiSach loai : list){
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("maloai", loai.getId());
            hs.put("tenloai", loai.getTenloai());
            listHM.add(hs);
        }
        return listHM;
    }
}
