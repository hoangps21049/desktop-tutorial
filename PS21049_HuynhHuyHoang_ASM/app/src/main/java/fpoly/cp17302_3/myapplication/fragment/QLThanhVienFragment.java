package fpoly.cp17302_3.myapplication.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import fpoly.cp17302_3.myapplication.R;
import fpoly.cp17302_3.myapplication.adapter.ThanhVienAdapter;
import fpoly.cp17302_3.myapplication.dao.ThanhVienDAO;
import fpoly.cp17302_3.myapplication.model.ThanhVien;

public class QLThanhVienFragment extends Fragment {
    ThanhVienDAO thanhVienDAO;
    RecyclerView recyclerViewThanhVien;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlthanhvien, container, false);

        recyclerViewThanhVien = view.findViewById(R.id.recyclerThanhVien);
        FloatingActionButton floatAdd = view.findViewById(R.id.floatAdd);

        thanhVienDAO = new ThanhVienDAO(getContext());

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
        ArrayList<ThanhVien> list = thanhVienDAO.getDSThanhVien();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewThanhVien.setLayoutManager(linearLayoutManager);
        ThanhVienAdapter adapter = new ThanhVienAdapter(getContext(), list, thanhVienDAO);
        recyclerViewThanhVien.setAdapter(adapter);
    }
    private void showDiaLog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_themthanhvien, null);
        EditText edtHoTen = view.findViewById(R.id.edtHoTen);
        EditText edtNamSinh = view.findViewById(R.id.edtNamSinh);
        builder.setView(view);

        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });



        builder.setNegativeButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String hoten = edtHoTen.getText().toString();
                String namsinh = edtNamSinh.getText().toString();
                if (hoten.length() == 0 || namsinh.length() == 0){
                    Toast.makeText(getContext(), "Vui lòng không để rỗng", Toast.LENGTH_SHORT).show();
                    showDiaLog();
                }else {
                    boolean check = thanhVienDAO.themThanhVien(hoten, namsinh);
                    if (check == true) {
                        Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                    }else {
                        Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
