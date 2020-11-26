package com.example.myduan1;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myduan1.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class SignUpActivity extends AppCompatActivity {


    private static final String TAG = "AAA";
    private TextInputEditText edSignUpNhapUserName;
    private TextInputEditText edSignUpNhapPassword;
    private TextInputEditText edSignUpNhapLaiPassword;
    private TextInputEditText edSignUpSoDienThoai;
    private TextInputEditText edSignUpHoTen;
    private RadioGroup radioGruoupSignUpGioiTinh;
    private RadioButton rbnSignUpGTNam;
    private RadioButton rbnSignUpGTNu;
    private Button btnSignUpDangKy;
    private Button btnSignUpHuy;
    private Toolbar toolBarDangKi;







    private DatabaseReference databaseReference;
    final String NODE_USER = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dang_ky);
        anhXa();
        setSupportActionBar(toolBarDangKi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        databaseReference = FirebaseDatabase.getInstance().getReference();

        btnSignUpDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUp(edSignUpNhapUserName.getText().toString(), new User(edSignUpHoTen.getText().toString(),
                        edSignUpNhapUserName.getText().toString(),edSignUpNhapLaiPassword.getText().toString(),edSignUpSoDienThoai.getText().toString()));
                Toast.makeText(SignUpActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
            }
        });
        btnSignUpHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void SignUp(String s1, User u) {
        final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setMessage("Xin chờ ...");
        progressDialog.show();
        databaseReference.child(NODE_USER).child(s1).push().setValue(u);
        databaseReference.child(NODE_USER).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
            }
        });
    }

    private void anhXa() {
        edSignUpNhapUserName = findViewById(R.id.ed_SignUp_NhapUserName);
        edSignUpNhapPassword = findViewById(R.id.ed_SignUp_NhapPassword);
        edSignUpNhapLaiPassword = findViewById(R.id.ed_SignUp_NhapLaiPassword);
        edSignUpSoDienThoai = findViewById(R.id.ed_SignUp_SoDienThoai);
        edSignUpHoTen = findViewById(R.id.ed_SignUp_HoTen);
        radioGruoupSignUpGioiTinh = findViewById(R.id.radioGruoup_SignUp_GioiTinh);
        rbnSignUpGTNam = findViewById(R.id.rbn_SignUp_GTNam);
        rbnSignUpGTNu = findViewById(R.id.rbn_SignUp_GTNu);
        btnSignUpDangKy = findViewById(R.id.btn_SignUp_DangKy);
        btnSignUpHuy = findViewById(R.id.btn_SignUp_Huy);
        toolBarDangKi = findViewById(R.id.tool_bar_dang_ki);
    }
}