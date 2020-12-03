package vn.timtro.timtroproject.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import vn.timtro.timtroproject.FilterActivity;
import vn.timtro.timtroproject.R;
import vn.timtro.timtroproject.adapter.PostAllAdapter;
import vn.timtro.timtroproject.model.Image;
import vn.timtro.timtroproject.model.Post;
import vn.timtro.timtroproject.model.User;

public class HomeFragment extends Fragment {
    private static final String TAG = "AAA";
    private RecyclerView recyclerViewHome;
    private PostAllAdapter postAllAdapter;
    DatabaseReference databaseReference;
    final ArrayList<Post> posts = new ArrayList<>();
    final ArrayList<User> users = new ArrayList<>();
    private ImageView ivChangeLayout;
    int layout;
    final int REQUEST_INTENT_FILTER = 123;
    final ArrayList<Post> posts2 = new ArrayList<>();


    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbarHome = view.findViewById(R.id.toolbar_home);
        EditText searchView = view.findViewById(R.id.search_view);
        ImageView ivFilter = view.findViewById(R.id.iv_filter);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        recyclerViewHome = view.findViewById(R.id.recycler_view_home);
        ivChangeLayout = view.findViewById(R.id.iv_change_layout);

        layout = 1;

       /* searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Thông báo");
                builder.setMessage("Chức năng đang hoàn thiện");
            *    builder.setPositiveButton("OK", null);
                builder.show();
            }
        });*/


        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, int i, int i1, int i2) {
                Log.d(TAG, "onQueryTextSubmit: " + charSequence.toString());

                posts2.clear();
                databaseReference
                        .child("post")
                        .addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                Post post = snapshot.getValue(Post.class);
                                if (post.getTieuDe().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                                    Log.d(TAG, "onChildAdded: vao ham ");
                                    posts2.add(post);
                                    postAllAdapter.notifyDataSetChanged();
                                }
                                if (charSequence.toString().length() == 0) {
                                    posts2.add(post);
                                    postAllAdapter.notifyDataSetChanged();
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
            public void afterTextChanged(Editable editable) {

            }
        });


        ivFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getContext(), FilterActivity.class), REQUEST_INTENT_FILTER);

            }
        });

        //use this function
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
                                        users.add(snapshot.getValue(User.class));
                                        postAllAdapter.notifyDataSetChanged();
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


        final ArrayList<Image> listImageUri = new ArrayList<>();
        databaseReference
                .child("post")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull final DataSnapshot snapshot, @Nullable String previousChildName) {
//                posts.add(snapshot.getValue(Post.class));

                        // posts2.add(snapshot.getValue(Post.class));
                        posts2.add(snapshot.getValue(Post.class));
                        Collections.sort(posts2, new Comparator<Post>() {
                            @Override
                            public int compare(Post post, Post t1) {
                                return new Date(post.getTimePost()).getTime() < new Date(t1.getTimePost()).getTime() ? 1 : -1;
                            }
                        });

                        postAllAdapter.notifyDataSetChanged();


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
                        Post post = snapshot.getValue(Post.class);
                        databaseReference
                                .child("post")
                                .child(post.getId())
                                .child("linkAnh")
                                .addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                                        listImageUri.add(snapshot.getValue(Image.class));
                                        postAllAdapter.notifyDataSetChanged();
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
        ivChangeLayout.setImageResource(R.drawable.ic_grid_2_24);

        postAllAdapter = new PostAllAdapter(getContext(), posts2, users, listImageUri);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerViewHome.setLayoutManager(gridLayoutManager);
        recyclerViewHome.setAdapter(postAllAdapter);
        ivChangeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (layout == 0) {
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                    recyclerViewHome.setLayoutManager(gridLayoutManager);
                    recyclerViewHome.setAdapter(postAllAdapter);
                    ivChangeLayout.setImageResource(R.drawable.ic_grid_2_24);
                    layout = 1;
                } else if (layout == 1) {
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                    recyclerViewHome.setLayoutManager(linearLayoutManager);
                    recyclerViewHome.setAdapter(postAllAdapter);
                    ivChangeLayout.setImageResource(R.drawable.ic_grid_3_24);
                    layout = 0;
                }
            }
        });


    }

    private void filter(Post post) {
        final SharedPreferences sharedPreferences = getContext().getSharedPreferences("filter", Context.MODE_PRIVATE);


        if ((post.getDanhMuc().equals(sharedPreferences.getString("brandMini", null)))) {
            Log.d(TAG, "filter: " + sharedPreferences.getString("brandMini", null));
            checkPriceAndAcreage(post);
        } else if (post.getDanhMuc().equals(sharedPreferences.getString("brandAppartment", null))) {
            Log.d(TAG, "filter: " + sharedPreferences.getString("brandAppartment", null));
            checkPriceAndAcreage(post);
        } else if (post.getDanhMuc().equals(sharedPreferences.getString("brandGeneral", null))) {
            Log.d(TAG, "filter: " + sharedPreferences.getString("brandGeneral", null));
            checkPriceAndAcreage(post);
        } else if (post.getDanhMuc().equals(sharedPreferences.getString("brandHomestay", null))) {
            Log.d(TAG, "filter: " + sharedPreferences.getString("brandHomestay", null));
            checkPriceAndAcreage(post);
        } else if (post.getDanhMuc().equals(sharedPreferences.getString("brandSelf", null))) {
            Log.d(TAG, "filter: " + sharedPreferences.getString("brandSelf", null));
            checkPriceAndAcreage(post);
        } else if (sharedPreferences.getString("brandMini", null).equals("") &&
                sharedPreferences.getString("brandAppartment", null).equals("") &&
                sharedPreferences.getString("brandGeneral", null).equals("") &&
                sharedPreferences.getString("brandHomestay", null).equals("") &&
                sharedPreferences.getString("brandSelf", null).equals("")) {
            Log.d(TAG, "filter: ko chonj");
            checkPriceAndAcreage(post);
        }


    }

    private void addPost(Post post) {
        posts2.add(post);
        postAllAdapter.notifyDataSetChanged();
    }

    private void checkPriceAndAcreage(Post post) {
        final SharedPreferences sharedPreferences = getContext().getSharedPreferences("filter", Context.MODE_PRIVATE);
        Log.d(TAG, "checkPriceAndAcreage: " + sharedPreferences.getString("place", "124123"));
        Log.d(TAG, "checkPriceAndAcreage: dia chi post" + post.getDiaChi());
        if (post.getDiaChi().toLowerCase().equals(sharedPreferences.getString("place", "").toLowerCase())) {
            postAllAdapter.notifyDataSetChanged();
            Log.d(TAG, "checkPriceAndAcreage: dia chi" + sharedPreferences.getString("place", ""));
            if (!(sharedPreferences.getString("moneyMin", "").trim().equals("") ||
                    sharedPreferences.getString("moneyMax", "").trim().equals(""))) {
                postAllAdapter.notifyDataSetChanged();
                if (Integer.parseInt(post.getGia()) >= Integer.parseInt(sharedPreferences.getString("moneyMin", "-1")) &&
                        Integer.parseInt(post.getGia()) <= Integer.parseInt(sharedPreferences.getString("moneyMax", "-1"))) {
                    if (!(sharedPreferences.getString("acreageMin", "-1").trim().equals("") ||
                            sharedPreferences.getString("acreageMax", "-1").trim().equals(""))) {
                        postAllAdapter.notifyDataSetChanged();
                        if (Integer.parseInt(post.getDienTich()) >= Integer.parseInt(sharedPreferences.getString("acreageMin", "0")) &&
                                Integer.parseInt(post.getDienTich()) <= Integer.parseInt(sharedPreferences.getString("acreageMax", "0"))) {
                            sortPost(post);
                        }
                    } else {
                        sortPost(post);
                    }

                }
            } else {

                if (!(sharedPreferences.getString("acreageMin", "-1").trim().equals("") ||
                        sharedPreferences.getString("acreageMax", "-1").trim().equals(""))) {
                    postAllAdapter.notifyDataSetChanged();
                    if (Integer.parseInt(post.getDienTich()) >= Integer.parseInt(sharedPreferences.getString("acreageMin", "0")) &&
                            Integer.parseInt(post.getDienTich()) <= Integer.parseInt(sharedPreferences.getString("acreageMax", "0"))) {
                        sortPost(post);
                    }
                } else {
                    sortPost(post);
                }
            }

        }
        if (sharedPreferences.getString("place", "").length() == 0) {
            Log.d(TAG, "checkPriceAndAcreage: dia chi" + sharedPreferences.getString("place", ""));
            postAllAdapter.notifyDataSetChanged();
            if (!(sharedPreferences.getString("moneyMin", "").trim().equals("") ||
                    sharedPreferences.getString("moneyMax", "").trim().equals(""))) {
                postAllAdapter.notifyDataSetChanged();
                if (Integer.parseInt(post.getGia()) >= Integer.parseInt(sharedPreferences.getString("moneyMin", "-1")) &&
                        Integer.parseInt(post.getGia()) <= Integer.parseInt(sharedPreferences.getString("moneyMax", "-1"))) {
                    if (!(sharedPreferences.getString("acreageMin", "-1").trim().equals("") ||
                            sharedPreferences.getString("acreageMax", "-1").trim().equals(""))) {
                        postAllAdapter.notifyDataSetChanged();
                        if (Integer.parseInt(post.getDienTich()) >= Integer.parseInt(sharedPreferences.getString("acreageMin", "0")) &&
                                Integer.parseInt(post.getDienTich()) <= Integer.parseInt(sharedPreferences.getString("acreageMax", "0"))) {
                            sortPost(post);

                        }
                    } else {
                        sortPost(post);
                    }
                }
            } else {
                Log.d(TAG, "checkPriceAndAcreage: dien tich");
                if (!(sharedPreferences.getString("acreageMin", "-1").trim().equals("") ||
                        sharedPreferences.getString("acreageMax", "-1").trim().equals(""))) {
                    postAllAdapter.notifyDataSetChanged();
                    if (Integer.parseInt(post.getDienTich()) >= Integer.parseInt(sharedPreferences.getString("acreageMin", "0")) &&
                            Integer.parseInt(post.getDienTich()) <= Integer.parseInt(sharedPreferences.getString("acreageMax", "0"))) {
                        sortPost(post);
                    }
                } else {
                    sortPost(post);
                }
            }
        }
    }

    private void sortPost(Post post) {
        final SharedPreferences sharedPreferences = getContext().getSharedPreferences("filter", Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean("sortNewChecked", true)) {

            posts2.add(post);
            Collections.sort(posts2, new Comparator<Post>() {
                @Override
                public int compare(Post post, Post t1) {
                    return new Date(post.getTimePost()).getTime() < new Date(t1.getTimePost()).getTime() ? 1 : -1;
                }
            });

            postAllAdapter.notifyDataSetChanged();
        } else if (sharedPreferences.getBoolean("sortPriceChecked", true)) {
            posts2.add(post);
            Collections.sort(posts2, new Comparator<Post>() {
                @Override
                public int compare(Post post, Post t1) {
                    return Long.parseLong(post.getGia()) > Long.parseLong(t1.getGia()) ? 1 : -1;
                }
            });

            postAllAdapter.notifyDataSetChanged();
        } else {
            posts2.add(post);
            postAllAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_INTENT_FILTER) {
            posts2.clear();
            final SharedPreferences sharedPreferences = getContext().getSharedPreferences("filter", Context.MODE_PRIVATE);

            databaseReference
                    .child("post")
                    .addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull final DataSnapshot snapshot, @Nullable String previousChildName) {
//                posts.add(snapshot.getValue(Post.class));
                            if (sharedPreferences.getString("unknown", null) != null) {
                                filter(snapshot.getValue(Post.class));
                                Log.d(TAG, "onChildAdded: " + snapshot.getValue(Post.class).getDiaChi());
                                //filter(snapshot.getValue(Post.class));
                                postAllAdapter.notifyDataSetChanged();
                            } else {
                                posts2.add(snapshot.getValue(Post.class));
                                postAllAdapter.notifyDataSetChanged();
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
            //  getActivity().getSupportFragmentManager().beginTransaction().detach(this).attach(this).commit();

        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("filter", Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
        sharedPreferences.edit().putString("unknown", null).apply();
    }
}

