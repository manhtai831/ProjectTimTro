package com.example.myduan1;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
    private TextInputEditText edSignUpNhapLaiPassword;
    private TextInputEditText edSignUpNhapHoTen;
    private TextInputEditText edSignUpNhapSDT;
    private Button btnSignUpDangKy;


    private DatabaseReference databaseReference;
    final String NODE_USER = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dang_ky);

        anhXa();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        btnSignUpDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUp(edSignUpNhapUserName.getText().toString(), new User(edSignUpNhapHoTen.getText().toString(),
                        edSignUpNhapUserName.getText().toString(),edSignUpNhapLaiPassword.getText().toString(),edSignUpNhapSDT.getText().toString()));
                Toast.makeText(SignUpActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
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
        edSignUpNhapUserName = (TextInputEditText) findViewById(R.id.ed_SignUp_NhapUserName);
        edSignUpNhapLaiPassword = (TextInputEditText) findViewById(R.id.ed_SignUp_NhapLaiPassword);
        edSignUpNhapHoTen = (TextInputEditText) findViewById(R.id.ed_SignUp_NhapHoTen);
        edSignUpNhapSDT = (TextInputEditText) findViewById(R.id.ed_SignUp_NhapSDT);
        btnSignUpDangKy = (Button) findViewById(R.id.btn_SignUp_DangKy);

    }
}