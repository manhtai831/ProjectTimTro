package vn.timtro.timtroproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class AvatarActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);
        Toolbar toolbarAvatar = findViewById(R.id.toolbar_avatar);
        ImageView ivAvatar = findViewById(R.id.iv_avatar);

        setSupportActionBar(toolbarAvatar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        View view = getSupportActionBar().getCustomView();
        ImageButton ivArrow = view.findViewById(R.id.iv_arrow_2);
        TextView tvCustomActionBar = view.findViewById(R.id.tv_custom_action_bar_2);
        tvCustomActionBar.setText("Bộ lọc");
        ivArrow.setImageResource(R.drawable.baseline_close_black_24dp);
        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Picasso.get().load(getIntent().getStringExtra("avatar")).resize(720,1080).into(ivAvatar);
    }
}