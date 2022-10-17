package fpoly.cp17302_3.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import fpoly.cp17302_3.myapplication.dao.ThuThuDAO;
import fpoly.cp17302_3.myapplication.fragment.QLLoaiSachFragment;
import fpoly.cp17302_3.myapplication.fragment.QLPhieuMuonFragment;
import fpoly.cp17302_3.myapplication.fragment.QLSachFragment;
import fpoly.cp17302_3.myapplication.fragment.QLThanhVienFragment;
import fpoly.cp17302_3.myapplication.fragment.QLThuThuFragment;
import fpoly.cp17302_3.myapplication.fragment.ThongKeDoanhThuFragment;
import fpoly.cp17302_3.myapplication.fragment.ThongKeTop10Fragment;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        FrameLayout frameLayout = findViewById(R.id.framelayout);
        NavigationView navigationView = findViewById(R.id.navigation);
        drawerLayout = findViewById(R.id.drawerlayout);
        View headerLayout = navigationView.getHeaderView(0);
        TextView txtTen = headerLayout.findViewById(R.id.txtTen);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.mQLPhieuMuon:
                        fragment = new QLPhieuMuonFragment();
                        break;
                    case R.id.mQLLoaiSach:
                        fragment = new QLLoaiSachFragment();
                        break;
                    case  R.id.mThoat:
                        Intent intent = new Intent(MainActivity.this, DangNhap.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    case R.id.mDoiMK:
                        showDiaLogDoiMK();
                        break;
                    case R.id.mTop10:
                        fragment = new ThongKeTop10Fragment();
                        break;
                    case R.id.mDoanhThu:
                        fragment = new ThongKeDoanhThuFragment();
                        break;
                    case R.id.mQLThanhVien:
                        fragment = new QLThanhVienFragment();
                        break;
                    case R.id.mQLSach:
                        fragment = new QLSachFragment();
                        break;
                    case R.id.mQLThuThu:
                        fragment = new QLThuThuFragment();
                        break;
                    default:
                        fragment = new QLPhieuMuonFragment();
                        break;
                }

                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.framelayout, fragment)
                            .commit();
                    toolbar.setTitle(item.getTitle());
                }
                drawerLayout.closeDrawer(GravityCompat.START);

                return false;
            }
        });

        //Hiển thị 1 số chức năng cho admin
        SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);
        String loaitaikhoan = sharedPreferences.getString("loaitaikhoan", "");
        if (!loaitaikhoan.equalsIgnoreCase("Admin")){
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.mDoanhThu).setVisible(false);
            menu.findItem(R.id.mTop10).setVisible(false);
            menu.findItem(R.id.mQLThuThu).setVisible(false);

        }
        String hoten = sharedPreferences.getString("hoten", "");
        txtTen.setText("Xin Chào "+ hoten);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }


    private void showDiaLogDoiMK(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setNegativeButton("Cập nhật", null)
                .setPositiveButton("Hủy", null);
        LayoutInflater inflater  = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_doimatkhau, null);
        EditText edtoldPass = view.findViewById(R.id.edtPassOld);
        EditText edtNewPass = view.findViewById(R.id.edtNewPass);
        EditText edtReNewPass = view.findViewById(R.id.edtReNewPass);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        //Không được click ra ngoài dialog
        alertDialog.setCancelable(false);
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPass = edtoldPass.getText().toString();
                String newPass = edtNewPass.getText().toString();
                String reNewPass = edtReNewPass.getText().toString();
                if (oldPass.equals("") || newPass.equals("") || reNewPass.equals("")){
                    Toast.makeText(MainActivity.this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    if (newPass.equals(reNewPass)){
                        SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);

                        String matt = sharedPreferences.getString("matt", "");
                        //cập nhật
                        ThuThuDAO thuThuDAO = new ThuThuDAO(MainActivity.this);
                        int check = thuThuDAO.capNhatMatKhau(matt, oldPass, newPass);
                        if (check == 1){
                            Toast.makeText(MainActivity.this, "cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, DangNhap.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }else if(check == 0){
                            Toast.makeText(MainActivity.this, "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MainActivity.this, "Cập nhật mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(MainActivity.this, "Nhập mật khẩu không trùng với nhau", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
}