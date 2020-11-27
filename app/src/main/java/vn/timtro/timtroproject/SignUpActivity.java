package vn.timtro.timtroproject;

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

import vn.timtro.timtroproject.model.User;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {


    private static final String TAG = "AAA";
    private TextInputEditText edSignUpNhapUserName;
    private TextInputEditText edSignUpNhapPassword;
    private TextInputEditText edSignUpNhapLaiPassword;
    private TextInputEditText edSignUpSoDienThoai;
    private TextInputEditText edSignUpHoTen;
    private RadioGroup radioGruoupSignUpGioiTinh;
    private Button btnSignUpDangKy;
    private Button btnSignUpHuy;
    private Toolbar toolBarDangKi;
    private RadioButton rbnSignUpGTNam;



    private DatabaseReference databaseReference;
    final String NODE_USER = "user";
    String gender = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        anhXa();
        setSupportActionBar(toolBarDangKi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        databaseReference = FirebaseDatabase.getInstance().getReference();
        setClickButton();


    }

    private void SignUp(String s1, User u) {
        if(validate()){
            final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
            progressDialog.setMessage("Xin chờ ...");
            progressDialog.show();
            databaseReference.child(NODE_USER).child(s1).push().setValue(u);
            databaseReference.child(NODE_USER).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(SignUpActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressDialog.dismiss();
                }
            });
        }

    }

    private void anhXa() {
        edSignUpNhapUserName = findViewById(R.id.ed_SignUp_NhapUserName);
        edSignUpNhapPassword = findViewById(R.id.ed_SignUp_NhapPassword);
        edSignUpNhapLaiPassword = findViewById(R.id.ed_SignUp_NhapLaiPassword);
        edSignUpSoDienThoai = findViewById(R.id.ed_SignUp_SoDienThoai);
        edSignUpHoTen = findViewById(R.id.ed_SignUp_HoTen);
        radioGruoupSignUpGioiTinh = findViewById(R.id.radioGruoup_SignUp_GioiTinh);
        btnSignUpDangKy = findViewById(R.id.btn_SignUp_DangKy);
        btnSignUpHuy = findViewById(R.id.btn_SignUp_Huy);
        toolBarDangKi = findViewById(R.id.tool_bar_dang_ki);
        rbnSignUpGTNam = findViewById(R.id.rbn_SignUp_GTNam);
    }

    private boolean validate(){
        String tenTaiKhoan  = edSignUpNhapUserName.getText().toString().trim();
        String hoTen        = edSignUpHoTen.getText().toString().trim();
        String soDienThoai  = edSignUpSoDienThoai.getText().toString().trim();
        String matKhau1      = edSignUpNhapPassword.getText().toString().trim();
        String matKhau2     = edSignUpNhapLaiPassword.getText().toString().trim();
        if(tenTaiKhoan.length() <4 || tenTaiKhoan.length() > 20  ){
            Toast.makeText(this, "Tên tài khoản phải dài hơn 4 kí tự", Toast.LENGTH_SHORT).show();
            return false;
        }else if(hoTen.length() == 0){
            Toast.makeText(this, "Cần nhập họ và tên", Toast.LENGTH_SHORT).show();
            return false;
        }else if(gender.length() == 0){
            Toast.makeText(this, "Cần chọn giới tính", Toast.LENGTH_SHORT).show();
            return false;
        }else if(!soDienThoai.matches("0[1-9]{9}")){
            Toast.makeText(this, "Cần nhập số điện thoại đúng định dạng", Toast.LENGTH_SHORT).show();
            return false;
        }else if(!matKhau1.equals(matKhau2)){
            Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            return false;
        }else if(matKhau1.length() < 6){
            Toast.makeText(this, "Mật khẩu dài hơn 6 kí tự", Toast.LENGTH_SHORT).show();
            return false;
        }else
            return true;

    }

    private void setClickButton(){
        radioGruoupSignUpGioiTinh.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = radioGroup.findViewById(i);
                gender = radioButton.getText().toString();
                Log.d(TAG, "onCheckedChanged: " + gender);
            }
        });

        btnSignUpDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUp(edSignUpNhapUserName.getText().toString(), new User(edSignUpNhapUserName.getText().toString(),
                        edSignUpHoTen.getText().toString(),gender,edSignUpSoDienThoai.getText().toString(),edSignUpNhapLaiPassword.getText().toString()));

            }
        });
        btnSignUpHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}