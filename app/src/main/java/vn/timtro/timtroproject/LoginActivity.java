package vn.timtro.timtroproject;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import vn.timtro.timtroproject.model.User;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "AAA";
    private TextInputEditText edLoginUsername;
    private TextInputEditText edLoginPass;
    private CheckBox cbLoginLuuMK;
    private Button btnLoginLogin;
    private Button btnLoginSignUp;
    private DatabaseReference databaseReference;
    private ImageView imgLoginFB;
    private ImageView imgLoginTwitter;
    private ImageView imgLoginGoogle;
    private TextView tvVersionLogin;
    private Button btnLoginWithMessage;






    ArrayList<User> users;
    public static String userName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        anhXa();
        users = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
        if(sharedPreferences.getBoolean("checkbox",false)){
            edLoginUsername.setText(sharedPreferences.getString("user",""));
            edLoginPass.setText(sharedPreferences.getString("pass",""));
            cbLoginLuuMK.setChecked(sharedPreferences.getBoolean("checkbox",false));
        }
        btnLoginSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
        btnLoginWithMessage.setVisibility(View.INVISIBLE);

        btnLoginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnLoginWithMessage.getText().toString().equals("Đăng nhập bằng tin nhắn")){
                    SignIn(edLoginUsername.getText().toString(),edLoginPass.getText().toString());
                }else{
                    signInWithMessage(edLoginUsername.getText().toString());
                }


            }
        });

//        btnLoginWithMessage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(btnLoginWithMessage.getText().toString().equals("Đăng nhập bằng tin nhắn")){
//                    edLoginUsername.setHint("Số điện thoại");
//                    edLoginUsername.setText("0943574556");
//                    edLoginPass.setVisibility(View.INVISIBLE);
//                    btnLoginWithMessage.setText("Đăng nhập bằng tài khoản");
//                }else{
//                    edLoginUsername.setHint("Tên tài khoản");
//                    edLoginPass.setVisibility(View.VISIBLE);
//                    btnLoginWithMessage.setText("Đăng nhập bằng tin nhắn");
//                }
//
//            }
//        });

        imgLoginFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNotification();
            }
        });
        imgLoginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNotification();
            }
        });
        imgLoginTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNotification();
            }
        });
        tvVersionLogin.setText(BuildConfig.VERSION_NAME);
    }

    private void signInWithMessage(String phoneNumber) {
        if(phoneNumber.matches("0[0-9]{8,9}")){
            Intent intent = new Intent(LoginActivity.this,VerifyPhoneActivity.class);
            intent.putExtra("phoneNumber",phoneNumber);
            startActivity(intent);
        }else{
            edLoginUsername.setError("Số điện thoại không tồn tại");
        }
    }

    private void SignIn(final String s1, final String s2){
        final ProgressDialog progress = new ProgressDialog(LoginActivity.this);
        progress.setMessage("Xin chờ ...");
        progress.show();

            databaseReference
                    .child("user")
                    .addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    if(s1.trim().equals(snapshot.getKey())){
                        databaseReference.child("user").child(snapshot.getKey()).addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                getSharedPreferences("userLog",0).edit().putString("idLog",snapshot.getKey()).apply();
                                User user = snapshot.getValue(User.class);

                                if(s2.trim().equals(user.getPassword())){
                                    SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
                                    Editor editor = sharedPreferences.edit();
                                    if(cbLoginLuuMK.isChecked()){
                                        editor.putString("user",s1);
                                        editor.putString("pass",s2);
                                        editor.putBoolean("checkbox",true);
                                    }else{
                                        editor.putString("user","");
                                        editor.putString("pass","");
                                        editor.putBoolean("checkbox",false);
                                    }
                                    editor.apply();
                                    getSharedPreferences("userLog",0).edit().putString("userName",s1.trim()).apply();
                                    getSharedPreferences("userLog",0).edit().putString("password",s2.trim()).apply();
                                    getSharedPreferences("userLog",0).edit().putString("keyUser",user.getKey()).apply();
                                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                    userName = s1;
                                    progress.dismiss();
                                    finish();
                                }else{
                                    Toast.makeText(LoginActivity.this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                                    progress.dismiss();
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
                    } else{
                        progress.dismiss();
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
                    progress.setMessage("Time out");
                }
            });





    }

    private void showNotification(){
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Thông báo");
        builder.setMessage("Tính năng sắp ra mắt");
        builder.setPositiveButton("OK",null);
        builder.show();
    }

    private void anhXa() {
        edLoginUsername = (TextInputEditText) findViewById(R.id.ed_login_Username);
        edLoginPass = (TextInputEditText) findViewById(R.id.ed_login_Pass);
        cbLoginLuuMK = (CheckBox) findViewById(R.id.cb_login_LuuMK);
        btnLoginLogin = (Button) findViewById(R.id.btn_login_Login);
        btnLoginSignUp = (Button) findViewById(R.id.btn_login_SignUp);
        imgLoginFB = findViewById(R.id.img_login_FB);
        imgLoginTwitter = findViewById(R.id.img_login_Twitter);
        imgLoginGoogle = findViewById(R.id.img_login_Google);
        tvVersionLogin = findViewById(R.id.tv_version_login);
        btnLoginWithMessage = findViewById(R.id.btn_login_with_message);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        edLoginUsername.setText(getSharedPreferences("user", MODE_PRIVATE).getString("user",null));
        edLoginPass.setText(getSharedPreferences("user", MODE_PRIVATE).getString("pass",null));
        cbLoginLuuMK.setChecked(getSharedPreferences("user", MODE_PRIVATE).getBoolean("checkbox",true));
    }
}