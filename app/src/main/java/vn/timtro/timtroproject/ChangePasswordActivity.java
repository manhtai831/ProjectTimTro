package vn.timtro.timtroproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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

import java.util.HashMap;
import java.util.Map;

import vn.timtro.timtroproject.model.User;

public class ChangePasswordActivity extends AppCompatActivity {
    private static final String TAG = "ZZZ";
    private Toolbar toolbarChangePassword;
    private TextInputEditText edtUpdatePasswordOld;
    private TextInputEditText edtUpdatePasswordNew;
    private TextInputEditText edtUpdatePasswordNew2;
    private Button btnUpdatePasswordUpdate;
    private Button btnUpdatePasswordCancel;
    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_main_updatepassword);

        anhXa();
        setSupportActionBar(toolbarChangePassword);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        View view = getSupportActionBar().getCustomView();
        ImageButton ivArrow = view.findViewById(R.id.iv_arrow_2);
        TextView tvCustomActionBar = view.findViewById(R.id.tv_custom_action_bar_2);
        tvCustomActionBar.setText("Cập nhật mật khẩu");
        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnUpdatePasswordCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnUpdatePasswordUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference
                        .child("user")
                        .child(getSharedPreferences("userLog", 0).getString("userName", null))
                        .addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                User user = snapshot.getValue(User.class);
                                if (user.getPassword().equals(edtUpdatePasswordOld.getText().toString().trim())) {
                                    if (edtUpdatePasswordNew.getText().toString().length() == 0 || edtUpdatePasswordNew2.getText().toString().length() == 0) {
                                        Toast.makeText(ChangePasswordActivity.this, "Chưa nhập mật khẩu mới", Toast.LENGTH_SHORT).show();
                                    } else if (edtUpdatePasswordNew.getText().toString().trim().equals(edtUpdatePasswordNew2.getText().toString().trim())
                                            && edtUpdatePasswordNew.getText().toString().length() >= 6) {
                                        Map<String, Object> map = new HashMap<>();
                                        map.put("password", edtUpdatePasswordNew.getText().toString());
                                        databaseReference.child("user")
                                                .child(getSharedPreferences("userLog", 0).getString("userName", null))
                                                .child(getSharedPreferences("userLog", 0).getString("idLog", null))
                                                .updateChildren(map)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(ChangePasswordActivity.this, "Mật khẩu cập nhật thành công", Toast.LENGTH_SHORT).show();
                                                        finish();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(ChangePasswordActivity.this, "Cập nhật không thành công", Toast.LENGTH_SHORT).show();

                                                    }
                                                });

                                    } else
                                        Toast.makeText(ChangePasswordActivity.this, "Sai định dạng mật khẩu hoặc không khớp", Toast.LENGTH_SHORT).show();
                                } else if (edtUpdatePasswordOld.getText().toString().length() == 0) {
                                    Toast.makeText(ChangePasswordActivity.this, "Chưa nhập mật khẩu cũ", Toast.LENGTH_SHORT).show();
                                }
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

    private void anhXa() {
        toolbarChangePassword = findViewById(R.id.toolbar_change_password);
        edtUpdatePasswordOld = findViewById(R.id.edt_update_password_old);
        edtUpdatePasswordNew = findViewById(R.id.edt_update_password_new);
        edtUpdatePasswordNew2 = findViewById(R.id.edt_update_password_new_2);
        btnUpdatePasswordUpdate = findViewById(R.id.btn_update_password_update);
        btnUpdatePasswordCancel = findViewById(R.id.btn_update_password_cancel);
    }
}