package vn.timtro.timtroproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;


import java.util.concurrent.TimeUnit;

import vn.timtro.timtroproject.until.GenericTextWatcher;

public class VerifyPhoneActivity extends AppCompatActivity {
    private static final String TAG = "AAA";
    FirebaseAuth firebaseAuth;
    private Toolbar toolbarVeryfyPhone;
    private EditText edtVeryfyPhone1;
    private EditText edtVeryfyPhone2;
    private EditText edtVeryfyPhone3;
    private EditText edtVeryfyPhone4;
    private EditText edtVeryfyPhone5;
    private EditText edtVeryfyPhone6;
    private TextView tvVeryfyPhonePhoneNumber;
    private Button btnVeryfyPhoneContinue;
    private TextView tvVeryfyPhoneTimeWait;


    String mVerificationId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);

        anhXa();

        setSupportActionBar(toolbarVeryfyPhone);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        View view = getSupportActionBar().getCustomView();
        ImageButton ivArrow = view.findViewById(R.id.iv_arrow_2);
        TextView tvCustomActionBar = view.findViewById(R.id.tv_custom_action_bar_2);
        tvCustomActionBar.setText("Nhập mã xác thực");
        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        tvVeryfyPhonePhoneNumber.setText("(+84)" + getIntent().getStringExtra("phoneNumber").substring(1, 10));
        Log.d(TAG, "onCreate: " + getIntent().getStringExtra("phoneNumber").substring(1, 10));


        EditText[] edits = {edtVeryfyPhone1, edtVeryfyPhone2, edtVeryfyPhone3, edtVeryfyPhone4, edtVeryfyPhone5, edtVeryfyPhone6};
//        if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
//                == ConnectionResult.SUCCESS) {
//            Log.d(TAG, "Google play: da xac thuc ");
//            // The SafetyNet Attestation API is available.
//        } else {
//            // Prompt user to update Google Play services.
//            Log.d(TAG, "Google play: chua xac thuc ");
//        }
        sendVerificationCode("+84" + getIntent().getStringExtra("phoneNumber").substring(1, 10));

//        SafetyNet.getClient(this)
//                .isVerifyAppsEnabled()
//                .addOnCompleteListener(new OnCompleteListener<SafetyNetApi.VerifyAppsUserResponse>() {
//                    @Override
//                    public void onComplete(Task<SafetyNetApi.VerifyAppsUserResponse> task) {
//                        if (task.isSuccessful()) {
//                            SafetyNetApi.VerifyAppsUserResponse result = task.getResult();
//                            if (result.isVerifyAppsEnabled()) {
//                                Log.d("MY_APP_TAG", "The Verify Apps feature is enabled.");
//                            } else {
//                                Log.d("MY_APP_TAG", "The Verify Apps feature is disabled.");
//                            }
//                        } else {
//                            Log.e("MY_APP_TAG", "A general error occurred.");
//                        }
//                    }
//                });

//        SafetyNet.getClient(this)
//                .verifyWithRecaptcha("6Lf_X_oZAAAAAGK3J7891G69apqb8SZxTVrHwPWp")
//                .addOnSuccessListener(new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
//                    @Override
//                    public void onSuccess(SafetyNetApi.RecaptchaTokenResponse recaptchaTokenResponse) {
//                        Log.d(TAG, "onSuccess: Captcha thanh cong");
//                        //sendVerificationCode("+84" + getIntent().getStringExtra("phoneNumber").substring(1, 10));
//                        SafetyNet
//                                .getClient(VerifyPhoneActivity.this)
//                                .enableVerifyApps()
//                                .addOnCompleteListener(new OnCompleteListener<SafetyNetApi.VerifyAppsUserResponse>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<SafetyNetApi.VerifyAppsUserResponse> task) {
//                                        Log.d(TAG, "Safety net Complete: ");
//                                        sendVerificationCode("+84" + getIntent().getStringExtra("phoneNumber").substring(1, 10));
//
//                                        if (task.isSuccessful()) {
//
//                                        }
//                                    }
//                                })
//                                .addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                });
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d(TAG, "onSuccess: Captcha khong thanh cong" + e.getStackTrace());
//                        Log.d(TAG, "onSuccess: Captcha khong thanh cong" + e.getMessage());
//                    }
//                });

        // sendVerificationCode("+84" + getIntent().getStringExtra("phoneNumber").substring(1, 10));

        edtVeryfyPhone1.addTextChangedListener(new GenericTextWatcher(edits, edtVeryfyPhone1));
        edtVeryfyPhone2.addTextChangedListener(new GenericTextWatcher(edits, edtVeryfyPhone2));
        edtVeryfyPhone3.addTextChangedListener(new GenericTextWatcher(edits, edtVeryfyPhone3));
        edtVeryfyPhone4.addTextChangedListener(new GenericTextWatcher(edits, edtVeryfyPhone4));
        edtVeryfyPhone5.addTextChangedListener(new GenericTextWatcher(edits, edtVeryfyPhone5));
        edtVeryfyPhone6.addTextChangedListener(new GenericTextWatcher(edits, edtVeryfyPhone6));


        btnVeryfyPhoneContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = edtVeryfyPhone1.getText().toString() + edtVeryfyPhone2.getText().toString() +
                        edtVeryfyPhone3.getText().toString() + edtVeryfyPhone4.getText().toString() +
                        edtVeryfyPhone5.getText().toString() + edtVeryfyPhone6.getText().toString();
                if (code.length() < 6) {
                    Toast.makeText(VerifyPhoneActivity.this, "Mã xác thực không đúng", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    verifyVerificationCode(code);
                }


            }
        });
    }

    private void sendVerificationCode(String phoneNumber) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallBack)          // OnVerificationStateChangedCallbacks
                        .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void verifyVerificationCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        signInWithPhoneCredential(credential);
    }

    private void signInWithPhoneCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "onComplete: task success ");
                    startActivity(new Intent(VerifyPhoneActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(VerifyPhoneActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();
                    if (code != null) {
                        Log.d(TAG, "onVerificationCompleted: " + code);
                        int maCode = Integer.parseInt(code);
                        edtVeryfyPhone1.setText(maCode % 10);
                        maCode = maCode / 10;
                        edtVeryfyPhone2.setText(maCode % 10);
                        maCode = maCode / 10;
                        edtVeryfyPhone3.setText(maCode % 10);
                        maCode = maCode / 10;
                        edtVeryfyPhone4.setText(maCode % 10);
                        maCode = maCode / 10;
                        edtVeryfyPhone5.setText(maCode % 10);
                        maCode = maCode / 10;
                        edtVeryfyPhone6.setText(maCode % 10);

                    }
                    Log.d(TAG, "onVerificationCompleted: " + phoneAuthCredential.getSmsCode());

                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    tvVeryfyPhoneTimeWait.setText("Nếu không nhận được mã. Hãy thử nhận lại mã \n hoặc đăng nhập bằng cách khác");
                    Log.d(TAG, "onVerificationFailed: " + e.getLocalizedMessage());
                }

                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    Log.d(TAG, "onCodeSent: " + s);
                    mVerificationId = s;
                }
            };

    private void anhXa() {
        toolbarVeryfyPhone = findViewById(R.id.toolbar_veryfy_phone);
        edtVeryfyPhone1 = findViewById(R.id.edt_veryfy_phone_1);
        edtVeryfyPhone2 = findViewById(R.id.edt_veryfy_phone_2);
        edtVeryfyPhone3 = findViewById(R.id.edt_veryfy_phone_3);
        edtVeryfyPhone4 = findViewById(R.id.edt_veryfy_phone_4);
        edtVeryfyPhone5 = findViewById(R.id.edt_veryfy_phone_5);
        edtVeryfyPhone6 = findViewById(R.id.edt_veryfy_phone_6);
        tvVeryfyPhonePhoneNumber = findViewById(R.id.tv_veryfy_phone_phone_number);
        btnVeryfyPhoneContinue = findViewById(R.id.btn_veryfy_phone_continue);
        tvVeryfyPhoneTimeWait = findViewById(R.id.tv_veryfy_phone_time_wait);
    }
}