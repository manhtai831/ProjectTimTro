package vn.timtro.timtroproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import vn.timtro.timtroproject.adapter.PostPersonalAdapter;
import vn.timtro.timtroproject.model.Image;
import vn.timtro.timtroproject.model.Post;

public class PostPersonalActivity extends AppCompatActivity {
    private static final String TAG = "BBB";
    private RecyclerView recyclerViewPostManager;
    PostPersonalAdapter postPersonalAdapter;
    DatabaseReference databaseReference;
    int changLayout;
    final ArrayList<Post> posts = new ArrayList<>();
    final ArrayList<Image> images = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_main_quanlibaidang);
        recyclerViewPostManager = findViewById(R.id.recycler_view_post_manager);
        Toolbar toolbarPostPersonal = findViewById(R.id.toolbar_post_personal);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        setSupportActionBar(toolbarPostPersonal);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        View view = getSupportActionBar().getCustomView();
        ImageButton ivArrow = view.findViewById(R.id.iv_arrow_2);
        TextView tvCustomActionBar = view.findViewById(R.id.tv_custom_action_bar_2);
        tvCustomActionBar.setText("Bài đăng của tôi");

        changLayout = 1;

        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        postPersonalAdapter = new PostPersonalAdapter(this, posts, images);
        recyclerViewPostManager.setAdapter(postPersonalAdapter);

        getPost();
        getImageOfPost();

    }

    private void getPost() {
        final String userNameLogin = getSharedPreferences("userLog", 0).getString("userName", null);
        databaseReference.child("post")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        Post post = snapshot.getValue(Post.class);
                        if (post.getIdUser().equals(userNameLogin)) {
                            posts.add(post);
                            postPersonalAdapter.notifyDataSetChanged();
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

    private void getImageOfPost() {
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
                                        images.add(snapshot.getValue(Image.class));
                                        postPersonalAdapter.notifyDataSetChanged();
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if (item.getItemId() == R.id.menu_grid) {
            if (changLayout == 0) {
                GridLayoutManager gridLayoutManager = new GridLayoutManager(PostPersonalActivity.this, 2);
                recyclerViewPostManager.setLayoutManager(gridLayoutManager);
                recyclerViewPostManager.setAdapter(postPersonalAdapter);
                changLayout = 1;
            } else if (changLayout == 1) {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PostPersonalActivity.this);
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerViewPostManager.setLayoutManager(linearLayoutManager);
                recyclerViewPostManager.setAdapter(postPersonalAdapter);
                changLayout = 0;
            }
        } else if (item.getItemId() == R.id.menu_info_app) {
            startActivity(new Intent(PostPersonalActivity.this,SearchActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_post_personal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
        new Thread(new Runnable() {
            @Override
            public void run() {
                finish();
                startActivity(getIntent());
            }
        }).start();
    }
}