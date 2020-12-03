package vn.timtro.timtroproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import vn.timtro.timtroproject.model.User;

public class PersonalInfoActivity extends AppCompatActivity {

    private Toolbar toolbarPersonalInfo;
    private ImageView imgPersonalMainInfoAvatar;
    private TextView tvPersonalMainInfoName;
    private TextView tvPersonalMainInfoTaikhoan;
    private TextView tvPersonalMainInfoSex;
    private TextView tvPersonalMainInfoNumberPhone;
    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    final String TAG = "DDD";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_main_info);

        anhXa();
        setSupportActionBar(toolbarPersonalInfo);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        View view = getSupportActionBar().getCustomView();
        ImageButton ivArrow = view.findViewById(R.id.iv_arrow_2);
        TextView tvCustomActionBar = view.findViewById(R.id.tv_custom_action_bar_2);
        tvCustomActionBar.setText("Thông tin");
        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        databaseReference.child("user").child(getSharedPreferences("userLog",0).getString("userName",null)).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);
                Picasso.get().load(user.getAvatar()).resize(120,120).placeholder(R.drawable.user_3).into(imgPersonalMainInfoAvatar);
                tvPersonalMainInfoName.setText(user.getName());
                tvPersonalMainInfoNumberPhone.setText(user.getPhoneNumber());
                tvPersonalMainInfoSex.setText(user.getGender());
                tvPersonalMainInfoTaikhoan.setText(user.getUserName());
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

    private void anhXa() {
        toolbarPersonalInfo = findViewById(R.id.toolbar_personal_info);
        imgPersonalMainInfoAvatar = findViewById(R.id.img_personal_main_info_avatar);
        tvPersonalMainInfoName = findViewById(R.id.tv_personal_main__info_name);
        tvPersonalMainInfoTaikhoan = findViewById(R.id.tv_personal_main__info_Taikhoan);
        tvPersonalMainInfoSex = findViewById(R.id.tv_personal_main__info_sex);
        tvPersonalMainInfoNumberPhone = findViewById(R.id.tv_personal_main__info_numberPhone);

    }

}