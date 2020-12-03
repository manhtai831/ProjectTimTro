package vn.timtro.timtroproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import vn.timtro.timtroproject.adapter.BigSlideImageAdapter;
import vn.timtro.timtroproject.model.Image;

public class BigSlideImageActivity extends AppCompatActivity {
    private static final String TAG = "AAA";
    private ViewPager vpBigSlideImage;
    DatabaseReference databaseReference;
    BigSlideImageAdapter bigSlideImageAdapter;
    private LinearLayout slideDotPanel;



    ImageView[] dots;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_slide_image);
        vpBigSlideImage = findViewById(R.id.vp_big_slide_image);
        LinearLayout vgBigSlideImageClose = findViewById(R.id.vg_big_slide_image_close);
        ImageButton ivItemBigClose = findViewById(R.id.iv_item_big_close);
        TextView tvItemBigClose = findViewById(R.id.tv_item_big_close);
        slideDotPanel = findViewById(R.id.slide_dot_panel);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        final ArrayList<Image> images = new ArrayList<>();
        bigSlideImageAdapter = new BigSlideImageAdapter(this,images);
        vpBigSlideImage.setAdapter(bigSlideImageAdapter);

        databaseReference.child("post").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d(TAG, "onChildAdded: key post" + snapshot.getKey());
                databaseReference.child("post").child(snapshot.getKey()).child("linkAnh").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        if(PostDetailActivity.keyPost.equals(snapshot.getValue(Image.class).getIdPost())) {

                            images.add(snapshot.getValue(Image.class));


                            try {
                                final int dotCount = images.size();
                                dots = new ImageView[dotCount];
                                slideDotPanel.removeAllViews();
                                for (int i = 0; i < dotCount; i++) {
                                    Log.d(TAG, "onChildAdded: " + i);

                                    dots[i] = new ImageView(getApplicationContext());
                                    dots[i].setImageResource(R.drawable.non_active_dot);
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    params.setMargins(8, 0, 8, 0);
                                    slideDotPanel.addView(dots[i], params);

                                }
                                dots[0].setImageResource(R.drawable.active_dot);
                                vpBigSlideImage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                    @Override
                                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                                    }

                                    @Override
                                    public void onPageSelected(int position) {
                                        for (int i = 0; i < dotCount; i++) {
                                            dots[i].setImageResource(R.drawable.non_active_dot);
                                        }
                                        dots[position].setImageResource(R.drawable.active_dot);
                                    }

                                    @Override
                                    public void onPageScrollStateChanged(int state) {

                                    }
                                });


                            } catch (Exception e) {
                                Log.d(TAG, "onCreate: This isn't error");
                            }
                        }
                        bigSlideImageAdapter.notifyDataSetChanged();
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

        vgBigSlideImageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ivItemBigClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ivItemBigClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }
}