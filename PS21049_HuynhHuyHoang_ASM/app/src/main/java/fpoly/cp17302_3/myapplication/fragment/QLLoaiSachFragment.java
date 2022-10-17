package fpoly.cp17302_3.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.cp17302_3.myapplication.R;
import fpoly.cp17302_3.myapplication.adapter.LoaiSachAdapter;
import fpoly.cp17302_3.myapplication.dao.LoaiSachDAO;
import fpoly.cp17302_3.myapplication.model.ItemClick;
import fpoly.cp17302_3.myapplication.model.LoaiSach;

public class QLLoaiSachFragment extends Fragment {
    RecyclerView recyclerViewLoaiSach;
    LoaiSachDAO loaiSachDAO;
    EditText edtLoaiSach;
    int maloai;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlloaisach, container, false);

        recyclerViewLoaiSach = view.findViewById(R.id.recyclerLoaiSach);

        edtLoaiSach = view.findViewById(R.id.edtLoaiSach);
        Button btnThem = view.findViewById(R.id.btnThem);
        Button btnSua = view.findViewById(R.id.btnSua);

        loaiSachDAO = new LoaiSachDAO(getContext());

        loadData();

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenloai = edtLoaiSach.getText().toString();

                if (tenloai.length() == 0){
                    Toast.makeText(getContext(), "Không để rỗng tên loại", Toast.LENGTH_SHORT).show();
                }else {
                    if (loaiSachDAO.themLoaiSach(tenloai) == true){
                        //Thông báo + load lại danh sách
                        loadData();
                        edtLoaiSach.setText("");

                    }else {
                        //Thông báo không thành công
                        Toast.makeText(getContext(), "Thêm loại sách không thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenloai = edtLoaiSach.getText().toString();
                LoaiSach loaiSach = new LoaiSach(maloai, tenloai);
                if (tenloai.length() == 0){
                    Toast.makeText(getContext(), "Không được rỗng", Toast.LENGTH_SHORT).show();
                }else {
                    if (loaiSachDAO.thayDoiLoaiSach(loaiSach)){
                        loadData();
                        edtLoaiSach.setText("");
                        Toast.makeText(getContext(), "Thay đổi thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getContext(), "Thay đổi thông tin không thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return view;
    }

    private void loadData(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewLoaiSach.setLayoutManager(linearLayoutManager);
        ArrayList<LoaiSach> list = loaiSachDAO.getDSLoaiSach();
        LoaiSachAdapter adapter = new LoaiSachAdapter(getContext(), list, new ItemClick() {
            @Override
            public void onClickLoaiSach(LoaiSach loaiSach) {
                edtLoaiSach.setText(loaiSach.getTenloai());
                maloai = loaiSach.getId();

            }
        });
        recyclerViewLoaiSach.setAdapter(adapter);
    }
}
