package vn.timtro.timtroproject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import vn.timtro.timtroproject.model.User;

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



    private DatabaseReference databaseReference;
    final String NODE_USER = "user";
    String gender = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        anhXa();
        setSupportActionBar(toolBarDangKi);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        View view = getSupportActionBar().getCustomView();
        ImageButton ivArrow = view.findViewById(R.id.iv_arrow_2);
        TextView tvCustomActionBar = view.findViewById(R.id.tv_custom_action_bar_2);
        tvCustomActionBar.setText("Đăng kí");
        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference();
        setClickButton();

        btnSignUpDangKy.setEnabled(false);
        edSignUpNhapUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {
                databaseReference.child("user").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        Log.d(TAG, "onChildAdded: tai khoan" + snapshot.getKey());
                        btnSignUpDangKy.setEnabled(!editable.toString().equals(snapshot.getKey()));
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


    }

    private void SignUp(final String s1) {
        if (validate()) {
            String keyUser = databaseReference.child(NODE_USER).child(s1).push().getKey();
            User u = new User(keyUser,
                    edSignUpNhapUserName.getText().toString(),
                    edSignUpHoTen.getText().toString(),
                    gender,
                    edSignUpSoDienThoai.getText().toString(),
                    edSignUpNhapLaiPassword.getText().toString());
            final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
            progressDialog.setMessage("Xin chờ ...");
            progressDialog.show();
            databaseReference
                    .child(NODE_USER)
                    .child(s1)
                    .child(keyUser)
                    .setValue(u)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            progressDialog.dismiss();
                            Toast.makeText(SignUpActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                            getSharedPreferences("user", MODE_PRIVATE).edit().putString("user", edSignUpNhapUserName.getText().toString()).apply();
                            getSharedPreferences("user", MODE_PRIVATE).edit().putString("pass", edSignUpNhapPassword.getText().toString()).apply();
                            getSharedPreferences("user", MODE_PRIVATE).edit().putBoolean("checkbox", true).apply();
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignUpActivity.this, "Đăng kí không thành công", Toast.LENGTH_SHORT).show();
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

    }

    private boolean validate() {
        String tenTaiKhoan = edSignUpNhapUserName.getText().toString().trim();
        String hoTen = edSignUpHoTen.getText().toString().trim();
        String soDienThoai = edSignUpSoDienThoai.getText().toString().trim();
        String matKhau1 = edSignUpNhapPassword.getText().toString().trim();
        String matKhau2 = edSignUpNhapLaiPassword.getText().toString().trim();
        if (tenTaiKhoan.length() < 4 || tenTaiKhoan.length() > 20) {
            edSignUpNhapUserName.setError("Tên tài khoản phải dài hơn 4 kí tự");
            return false;
        } else if (hoTen.length() == 0) {
            edSignUpNhapUserName.setError("Cần nhập họ và tên");
            return false;
        } else if (gender.length() == 0) {
            Toast.makeText(this, "Cần chọn giới tính", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!soDienThoai.matches("0[0-9]{8,9}")) {
            edSignUpSoDienThoai.setError("Số điện thoại không tồn tại");
            return false;
        } else if (!matKhau1.equals(matKhau2)) {
            edSignUpNhapLaiPassword.setError("Mật khẩu không khớp");

            return false;
        } else if (matKhau1.length() < 6) {
            edSignUpNhapPassword.setError("Mật khẩu dài hơn 6 kí tự");
            return false;
        } else
            return true;

    }

    private void setClickButton() {
        radioGruoupSignUpGioiTinh.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = radioGroup.findViewById(i);
                gender = radioButton.getText().toString();
            }
        });

        btnSignUpDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUp(edSignUpNhapUserName.getText().toString());

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