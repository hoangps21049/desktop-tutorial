package fpoly.cp17302_3.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fpoly.cp17302_3.myapplication.dao.ThuThuDAO;

public class DangNhap extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        EditText edtUser = findViewById(R.id.edtUser);
        EditText edtPass = findViewById(R.id.edtPass);
        Button btnLogin = findViewById(R.id.btnLogin);

        ThuThuDAO thuThuDAO = new ThuThuDAO(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edtUser.getText().toString();
                String pass = edtPass.getText().toString();
                if (thuThuDAO.checkDangNhap(user, pass)){
                    startActivity(new Intent(DangNhap.this, MainActivity.class));
                }else{
                    Toast.makeText(DangNhap.this, "UserName hoặc Mật Khẩu Không Đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}