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

import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;


import vn.timtro.timtroproject.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "AAA";
    private TextInputEditText edLoginUsername;
    private TextInputEditText edLoginPass;
    private CheckBox cbLoginLuuMK;
    private Button btnLoginLogin;
    private Button btnLoginSignUp;
    private Button btnQuenMK;
    private DatabaseReference databaseReference;
    private ImageView imgLoginFB;
    private ImageView imgLoginTwitter;
    private ImageView imgLoginGoogle;



    ArrayList<User> users;





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

        btnLoginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignIn(edLoginUsername.getText().toString(),edLoginPass.getText().toString());
                finish();
            }
        });

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


    }

    private void SignIn(String s1, final String s2){
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


        
        databaseReference.child("user").child(s1).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d(TAG, "onDataChange: " + snapshot.child("password").getValue().toString());
                ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setMessage("Xin chờ ...");
                progressDialog.show();
                if(s2.equals(snapshot.child("password").getValue().toString())){
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                    progressDialog.dismiss();
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
        //btnQuenMK = (Button) findViewById(R.id.btnQuenMK);
        imgLoginFB = findViewById(R.id.img_login_FB);
        imgLoginTwitter = findViewById(R.id.img_login_Twitter);
        imgLoginGoogle = findViewById(R.id.img_login_Google);

    }


}