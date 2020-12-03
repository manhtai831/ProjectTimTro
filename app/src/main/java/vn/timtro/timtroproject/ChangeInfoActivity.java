package vn.timtro.timtroproject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import vn.timtro.timtroproject.model.User;

public class ChangeInfoActivity extends AppCompatActivity {
    private Toolbar toolbarUpdateInfo;
    private EditText edtHoTen;
    private EditText edtTenTaiKhoan;
    private RadioGroup rgGioiTinh;
    private RadioButton rbGtNam;
    private RadioButton rbGtNu;
    private EditText edtSoDienThoai;
    private Button btnChangeInfoSua;
    private Button btnChangeInfoHuy;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);

        anhXa();

        setSupportActionBar(toolbarUpdateInfo);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        View view = getSupportActionBar().getCustomView();
        ImageButton ivArrow = view.findViewById(R.id.iv_arrow_2);
        TextView tvCustomActionBar = view.findViewById(R.id.tv_custom_action_bar_2);
        tvCustomActionBar.setText("Cập nhật thông tin");
        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        edtTenTaiKhoan.setEnabled(false);
        databaseReference.child("user")
                .child(getSharedPreferences("userLog", 0).getString("userName", null))
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        User user = snapshot.getValue(User.class);
                        edtHoTen.setText(user.getName());
                        edtSoDienThoai.setText(user.getPhoneNumber());
                        edtTenTaiKhoan.setText(user.getUserName());
                        if (user.getGender().equals(rbGtNam.getText().toString())) {
                            rbGtNam.setChecked(true);
                        } else rbGtNu.setChecked(true);
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

        setClickButton();
    }

    private void setClickButton() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        btnChangeInfoSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeDataUser();
            }
        });

        btnChangeInfoHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void changeDataUser() {
        final Map<String, Object> map = new HashMap<>();

        if (rbGtNam.isChecked()) {
            map.put("gender", rbGtNam.getText().toString());
        }
        if (rbGtNu.isChecked()) {
            map.put("gender", rbGtNu.getText().toString());
        }

        map.put("name", edtHoTen.getText().toString().trim());
        map.put("phoneNumber", edtSoDienThoai.getText().toString().trim());
        final ProgressDialog progressDialog = new ProgressDialog(ChangeInfoActivity.this);
        progressDialog.show();

        databaseReference
                .child("user")
                .child(getSharedPreferences("userLog", 0).getString("userName", null))
                .child(getSharedPreferences("userLog", 0).getString("idLog", null))
                .updateChildren(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        Toast.makeText(ChangeInfoActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(ChangeInfoActivity.this, "Không thành công", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void anhXa() {
        toolbarUpdateInfo = findViewById(R.id.toolbar_update_info);
        edtHoTen = findViewById(R.id.edt_ho_ten);
        edtTenTaiKhoan = findViewById(R.id.edt_ten_tai_khoan);
        rgGioiTinh = findViewById(R.id.rg_gioi_tinh);
        rbGtNam = findViewById(R.id.rb_gt_nam);
        rbGtNu = findViewById(R.id.rb_gt_nu);
        edtSoDienThoai = findViewById(R.id.edt_so_dien_thoai);
        btnChangeInfoSua = findViewById(R.id.btn_change_info_sua);
        btnChangeInfoHuy = findViewById(R.id.btn_change_info_huy);
    }
}