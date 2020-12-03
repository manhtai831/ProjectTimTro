package vn.timtro.timtroproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.timtro.timtroproject.adapter.SlideImageAdapter;
import vn.timtro.timtroproject.model.Image;
import vn.timtro.timtroproject.model.Post;
import vn.timtro.timtroproject.model.User;

public class PostDetailActivity extends AppCompatActivity {
    private static final String TAG = "AAA";
    private static final int REQUEST_CALL_PHONE = 123;
    private Toolbar toolbarPostDetail;
    private ViewPager vpPostDetailSlide;
    private TextView tvPostDetailPrice;
    private CircleImageView ivPostDetailAvatar;
    private TextView tvPostDetailName;

    private TextView tvPostDetailPhoneNumber;
    private TextView tvPostDetailAcreage;
    private TextView tvPostDetailPlace;
    private TextView tvPostDetailBrand;
    private TextView tvPostDetailDecription;
    private ImageButton ibPostDetailCall;
    private ImageButton ivPostDetailMessage;
    private TextView tvPostDetailTimePost;
    private TextView tvPostDetailTitle;
    private LinearLayout slideDotPanelPostDetail;

    ImageView[] dots;


    SlideImageAdapter slideImageAdapter;
    DatabaseReference databaseReference;
    public static String keyPost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_chi_tiet);

        anhXa();

        setSupportActionBar(toolbarPostDetail);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        View view = getSupportActionBar().getCustomView();
        ImageButton ivArrow = view.findViewById(R.id.iv_arrow_2);
        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final ArrayList<Image> images = new ArrayList<>();

        keyPost = getIntent().getStringExtra("keyPost");

        final ArrayList<String> listUriStrings = new ArrayList<>();

        tvPostDetailName.setText(getIntent().getStringExtra("name"));
        databaseReference
                .child("user")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        databaseReference
                                .child("user")
                                .child(snapshot.getKey())
                                .addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                        if(getIntent().getStringExtra("idUser").equals(snapshot.getValue(User.class).getUserName())){
                                            if(snapshot.getValue(User.class).getAvatar() == null){
                                                Picasso.get().load(R.drawable.user_3).resize(96, 96).into(ivPostDetailAvatar);
                                            }else{
                                                Picasso.get().load(snapshot.getValue(User.class).getAvatar()).resize(96, 96).into(ivPostDetailAvatar);
                                            }
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

        databaseReference
                .child("post")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        Log.d(TAG, "onChildAdded: key post" + snapshot.getKey());
                        databaseReference
                                .child("post")
                                .child(snapshot.getKey())
                                .child("linkAnh")
                                .addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                        if (keyPost.equals(snapshot.getValue(Image.class).getIdPost())) {
                                            images.add(snapshot.getValue(Image.class));
                                            listUriStrings.add(snapshot.getValue(Image.class).getLink());
                                        }
                                        slideImageAdapter.notifyDataSetChanged();
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


        databaseReference
                .child("post")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        Log.d(TAG, "onChildAdded: personal post" + snapshot.getValue(Post.class).getTieuDe());
                        final Post post = snapshot.getValue(Post.class);
                        if (keyPost.equals(post.getId())) {
                            tvPostDetailTitle.setText(post.getTieuDe());
                            tvPostDetailAcreage.setText("Diện tích: " + post.getDienTich() + " mét vuông");
                            tvPostDetailDecription.setText(post.getMoTa());
                            tvPostDetailPhoneNumber.setText("Liên hệ ngay: " + post.getSoDienThoai());
                            tvPostDetailPlace.setText("Khu vực: " + post.getDiaChi());
                            tvPostDetailBrand.setText("Danh mục: " + post.getDanhMuc());
                            NumberFormat numberFormat = NumberFormat.getInstance();
                            tvPostDetailPrice.setText(numberFormat.format(Double.parseDouble(post.getGia())));
                            Date date = Calendar.getInstance().getTime();
                            Date date1 = new Date(post.getTimePost());
                            long diff = (date.getTime() - date1.getTime()) / 1000;
                            int days = (int) (diff / 86400);
                            int hours = (int) ((diff % 86400) / 3600);
                            int minuates = (int) (((diff % 86400) % 3600) / 60);
                            if (days >= 1) {
                                tvPostDetailTimePost.setText(days + " " + getResources().getString(R.string.time_day));
                            } else if (hours >= 1) {
                                tvPostDetailTimePost.setText(hours + " " + getResources().getString(R.string.time_hour));
                            } else if (minuates >= 1)
                                tvPostDetailTimePost.setText(minuates + " " + getResources().getString(R.string.time_minuates));
                            else if (minuates < 1) {
                                tvPostDetailTimePost.setText(getResources().getString(R.string.time_second));
                            }

                            ibPostDetailCall.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    Intent intent = new Intent(Intent.ACTION_DIAL);
                                    intent.setData(Uri.parse("tel:" + post.getSoDienThoai()));
                                    startActivity(intent);
                                }


                            });

                            ivPostDetailMessage.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //  startActivity(new Intent(Intent.ACTION_VIEW,Uri.fromParts("sms","0943574556",null)));
                                    Uri uri = Uri.parse("smsto:" + post.getSoDienThoai());
                                    Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                                    it.putExtra("sms_body", post.getTieuDe() + " diện tích " + post.getDienTich());
                                    startActivity(it);
                                }
                            });
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

        setDotSlide();

        slideImageAdapter = new SlideImageAdapter(this, listUriStrings);
        vpPostDetailSlide.setAdapter(slideImageAdapter);

    }


    private void anhXa() {
        toolbarPostDetail = findViewById(R.id.toolbar_post_detail);
        vpPostDetailSlide = findViewById(R.id.vp_post_detail_slide);
        tvPostDetailPrice = findViewById(R.id.tv_post_detail_price);
        ivPostDetailAvatar = findViewById(R.id.tv_item_home_avatar);
        tvPostDetailName = findViewById(R.id.tv_item_home_name);

        tvPostDetailPhoneNumber = findViewById(R.id.tv_post_detail_phone_number);
        tvPostDetailAcreage = findViewById(R.id.tv_post_detail_acreage);
        tvPostDetailPlace = findViewById(R.id.tv_post_detail_place);
        tvPostDetailBrand = findViewById(R.id.tv_post_detail_brand);
        tvPostDetailDecription = findViewById(R.id.tv_post_detail_decription);
        ibPostDetailCall = findViewById(R.id.ib__post_detail_call);
        ivPostDetailMessage = findViewById(R.id.iv_post_detail_message);
        tvPostDetailTimePost = findViewById(R.id.tv_post_detail_time_post);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        tvPostDetailTitle = findViewById(R.id.tv_post_detail_title);
        slideDotPanelPostDetail = findViewById(R.id.slide_dot_panel_post_detail);
    }

    private void setDotSlide() {
        final ArrayList<Image> images = new ArrayList<>();

        databaseReference
                .child("post")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        databaseReference
                                .child("post")
                                .child(snapshot.getKey())
                                .child("linkAnh")
                                .addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                        if (PostDetailActivity.keyPost.equals(snapshot.getValue(Image.class).getIdPost())) {

                                            images.add(snapshot.getValue(Image.class));


                                            try {
                                                final int dotCount = images.size();
                                                dots = new ImageView[dotCount];
                                                slideDotPanelPostDetail.removeAllViews();
                                                for (int i = 0; i < dotCount; i++) {
                                                    Log.d(TAG, "onChildAdded: " + i);

                                                    dots[i] = new ImageView(getApplicationContext());
                                                    dots[i].setImageResource(R.drawable.non_active_dot);
                                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                                    params.setMargins(8, 0, 8, 0);
                                                    slideDotPanelPostDetail.addView(dots[i], params);

                                                }
                                                dots[0].setImageResource(R.drawable.active_dot);
                                                vpPostDetailSlide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
                                        slideImageAdapter.notifyDataSetChanged();
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

    }

}