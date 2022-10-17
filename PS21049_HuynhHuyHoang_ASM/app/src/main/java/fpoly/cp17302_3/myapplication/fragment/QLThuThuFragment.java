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

import fpoly.cp17302_3.myapplication.R;
import fpoly.cp17302_3.myapplication.adapter.ThuThuAdapter;
import fpoly.cp17302_3.myapplication.dao.ThuThuDAO;
import fpoly.cp17302_3.myapplication.model.ThuThu;

public class QLThuThuFragment extends Fragment {
    ThuThuDAO thuThuDAO;
    RecyclerView recyclerViewThuThu;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlthuthu, container, false);
        recyclerViewThuThu = view.findViewById(R.id.recyclerThuThu);
        FloatingActionButton floatAdd = view.findViewById(R.id.floatAdd);

        thuThuDAO = new ThuThuDAO(getContext());

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
        ArrayList<ThuThu> list = thuThuDAO.getDSThuThu();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewThuThu.setLayoutManager(linearLayoutManager);
        ThuThuAdapter adapter = new ThuThuAdapter(getContext(), list, thuThuDAO);
        recyclerViewThuThu.setAdapter(adapter);
    }

    private void showDiaLog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_themthuthu, null);
        EditText edtMatt = view.findViewById(R.id.edtMaTT);
        EditText edtHoTen = view.findViewById(R.id.edtHoTen);
        EditText edtMatKhau = view.findViewById(R.id.edtMatKhau);
        EditText edtLoai = view.findViewById(R.id.edtLoaiTaiKhoan);

        builder.setView(view);

        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNegativeButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String matt = edtMatt.getText().toString();
                String hoten = edtHoTen.getText().toString();
                String matkhau = edtMatKhau.getText().toString();
                String loai = edtLoai.getText().toString();
                if (matt.length() == 0 || hoten.length() == 0 || matkhau.length() == 0 || loai.length() == 0){
                    Toast.makeText(getContext(), "Không để rỗng", Toast.LENGTH_SHORT).show();
                    showDiaLog();
                }else {
                    boolean check = thuThuDAO.themThuThu(matt ,hoten, matkhau, loai);
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
