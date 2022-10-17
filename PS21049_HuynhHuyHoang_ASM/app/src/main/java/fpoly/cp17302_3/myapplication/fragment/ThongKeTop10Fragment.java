package fpoly.cp17302_3.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.cp17302_3.myapplication.R;
import fpoly.cp17302_3.myapplication.adapter.Top10Adapter;
import fpoly.cp17302_3.myapplication.dao.ThongKeDAO;
import fpoly.cp17302_3.myapplication.model.Sach;

public class ThongKeTop10Fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thongketop10, container, false);
        RecyclerView recyclerViewTop10 = view.findViewById(R.id.recyclerTop10);
        ThongKeDAO thongKeDAO = new ThongKeDAO(getContext());
        ArrayList<Sach> list = thongKeDAO.getTop10();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewTop10.setLayoutManager(linearLayoutManager);
        Top10Adapter adapter = new Top10Adapter(getContext(), list);
        recyclerViewTop10.setAdapter(adapter);

        return view;
    }
}
