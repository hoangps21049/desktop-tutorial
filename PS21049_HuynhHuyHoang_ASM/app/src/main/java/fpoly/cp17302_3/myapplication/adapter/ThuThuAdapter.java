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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.cp17302_3.myapplication.R;
import fpoly.cp17302_3.myapplication.dao.ThuThuDAO;
import fpoly.cp17302_3.myapplication.model.ThuThu;

public class ThuThuAdapter extends RecyclerView.Adapter<ThuThuAdapter.ViewHolder>{
    private Context context;
    private ArrayList<ThuThu> list;
    private ThuThuDAO thuThuDAO;

    public ThuThuAdapter(Context context, ArrayList<ThuThu> list, ThuThuDAO thuThuDAO) {
        this.context = context;
        this.list = list;
        this.thuThuDAO = thuThuDAO;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtMaTT, txtHoTen, txtMatKhau, txtLoaiTaiKhoan;
        ImageView ivEdit, ivDel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMaTT = itemView.findViewById(R.id.txtMaTT);
            txtHoTen = itemView.findViewById(R.id.txtHoTen);
            txtMatKhau = itemView.findViewById(R.id.txtMatKhau);
            txtLoaiTaiKhoan = itemView.findViewById(R.id.txtLoaiTaiKhoan);

            ivEdit = itemView.findViewById(R.id.ivEdit);
            ivDel = itemView.findViewById(R.id.ivDel);

        }
    }
    @NonNull
    @Override
    public ThuThuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_thuthu, parent, false);

        return new ThuThuAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThuThuAdapter.ViewHolder holder, int position) {
        holder.txtMaTT.setText("Mã Thủ Thư: " + list.get(position).getMatt());
        holder.txtHoTen.setText("Họ Tên: " + list.get(position).getHoten());
        holder.txtMatKhau.setText("Mật Khẩu: " + list.get(position).getMatkhau());
        holder.txtLoaiTaiKhoan.setText("Loại Tài Khoản: " + list.get(position).getLoaitaikhoan());

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiaLogCapNhatTT(list.get(holder.getAdapterPosition()));
            }
        });
        holder.ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check = thuThuDAO.xoaThongTinTT(list.get(holder.getAdapterPosition()).getMatt());
                if (check == -1){
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                }
            }
        });
    }
    private void showDiaLogCapNhatTT(ThuThu thuThu){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_chinhsua_thuthu, null);

        builder.setView(view);
        EditText edtMaTT = view.findViewById(R.id.edtMaTT);
        EditText edtHoTen = view.findViewById(R.id.edtHoTen);
        EditText edtMatKhau = view.findViewById(R.id.edtMatKhau);
        EditText edtLoai = view.findViewById(R.id.edtLoaiTaiKhoan);

        edtMaTT.setText(thuThu.getMatt());
        edtHoTen.setText(thuThu.getHoten());
        edtMatKhau.setText(thuThu.getMatkhau());
        edtLoai.setText(thuThu.getLoaitaikhoan());

        builder.setNegativeButton("Cập nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String matt = edtMaTT.getText().toString();
                String hoten = edtHoTen.getText().toString();
                String matkhau = edtMatKhau.getText().toString();
                String loai = edtLoai.getText().toString();

                boolean check =  thuThuDAO.capNhatThongTinTT(matt, hoten, matkhau, loai);

                if (check == true){
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                }else {
                    Toast.makeText(context, "Cập nhật không thành công", Toast.LENGTH_SHORT).show();
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
    @Override
    public int getItemCount() {
        return list.size();
    }

    private void loadData(){
        list.clear();
        list = thuThuDAO.getDSThuThu();
        notifyDataSetChanged();
    }
}