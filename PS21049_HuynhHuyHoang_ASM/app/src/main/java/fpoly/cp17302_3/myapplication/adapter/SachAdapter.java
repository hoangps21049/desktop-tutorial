package fpoly.cp17302_3.myapplication.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import fpoly.cp17302_3.myapplication.R;
import fpoly.cp17302_3.myapplication.dao.SachDAO;
import fpoly.cp17302_3.myapplication.model.Sach;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Sach> list;
    private ArrayList<HashMap<String, Object>> listHM;
    private SachDAO sachDAO;

    public SachAdapter(Context context, ArrayList<Sach> list, ArrayList<HashMap<String, Object>> listHM, SachDAO sachDAO) {
        this.context = context;
        this.list = list;
        this.listHM = listHM;
        this.sachDAO = sachDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item_recycler_sach, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMaSach.setText("Mã Sách: "+ list.get(position).getMasach());
        holder.txtTenSach.setText("Tên Sách: "+ list.get(position).getTensach());
        holder.txtGiaThue.setText("Gía Thuê: " + list.get(position).getGiathue());
        holder.txtMaLoai.setText("Mã Loại: " + list.get(position).getMaloai());
        holder.txtTenLoai.setText("Tên Loại: "+ list.get(position).getTenloai());

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiaLog(list.get(holder.getAdapterPosition()));
            }
        });

        holder.ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check = sachDAO.xoaSach(list.get(holder.getAdapterPosition()).getMasach());
                switch (check){
                    case 1:
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                        break;
                    case 0:
                        Toast.makeText(context, "Xóa không thành công", Toast.LENGTH_SHORT).show();
                        break;
                    case -1:
                        Toast.makeText(context, "Không xóa được sách này vì sách có trong phiếu mượn", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtMaSach, txtTenSach, txtGiaThue, txtMaLoai, txtTenLoai;
        ImageView ivEdit, ivDel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMaSach = itemView.findViewById(R.id.txtMaSach);
            txtTenSach = itemView.findViewById(R.id.txtTenSach);
            txtGiaThue = itemView.findViewById(R.id.txtGiaThue);
            txtMaLoai = itemView.findViewById(R.id.txtMaLoai);
            txtTenLoai = itemView.findViewById(R.id.txtTenLoai);

            ivEdit = itemView.findViewById(R.id.ivEdit);
            ivDel = itemView.findViewById(R.id.ivDel);
        }
    }
    private void showDiaLog(Sach sach){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_sua_sach, null);

        builder.setView(view);

        EditText edtTenSach = view.findViewById(R.id.edtTenSach);
        EditText edtTien = view.findViewById(R.id.edtTien);
        TextView txtMaSach = view.findViewById(R.id.txtMaSach);
        Spinner spnLoaiSach = view.findViewById(R.id.spnLoaiSach);

        txtMaSach.setText("Mã sách: " + sach.getMasach());
        edtTenSach.setText(sach.getTensach());
        edtTien.setText(String.valueOf(sach.getGiathue()));

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                context,
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"tenloai"},
                new int[]{android.R.id.text1}
        );
        spnLoaiSach.setAdapter(simpleAdapter);

        int index = 0;
        int position = -1;
        for(HashMap<String, Object> item : listHM){
            if ( (int) item.get("maloai") == sach.getMaloai()){
                position = index;
            }
            index++;
        }
        spnLoaiSach.setSelection(position);

        builder.setNegativeButton("Cập nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tensach = edtTenSach.getText().toString();
                String tienLoi = edtTien.getText().toString();
                HashMap<String, Object> hs = (HashMap<String, Object>) spnLoaiSach.getSelectedItem();
                int maloai = (int) hs.get("maloai");

                if (tensach.length() == 0 || tienLoi.length() == 0){
                    Toast.makeText(context, "Không đc để trống khi sửa", Toast.LENGTH_SHORT).show();
                }else {
                    int tien = Integer.parseInt(edtTien.getText().toString());
                    boolean check = sachDAO.capNhatThongTinSach(sach.getMasach(), tensach, tien, maloai);
                    if (check == true){
                        Toast.makeText(context, "Cập nhật sách thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                    }else {
                        Toast.makeText(context, "Cập nhật sách thất bại", Toast.LENGTH_SHORT).show();
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

    private void loadData(){
        list.clear();
        list = sachDAO.getDSDauSach();
        notifyDataSetChanged();
    }
}
