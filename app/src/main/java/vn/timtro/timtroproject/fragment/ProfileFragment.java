package vn.timtro.timtroproject.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import vn.timtro.timtroproject.AvatarActivity;
import vn.timtro.timtroproject.ChangeInfoActivity;
import vn.timtro.timtroproject.ChangePasswordActivity;
import vn.timtro.timtroproject.LoginActivity;
import vn.timtro.timtroproject.PersonalInfoActivity;
import vn.timtro.timtroproject.PostPersonalActivity;
import vn.timtro.timtroproject.R;
import vn.timtro.timtroproject.model.User;

public class ProfileFragment extends Fragment {
    private static final String TAG = "EEE";

    private ImageView imgPersonalMainAvatar;
    private ImageView imgPersonalMainBaidang;
    private ImageView imgPersonalMainInfo;
    private ImageView imgPersonalMainChangInfo;
    private ImageView imgPersonalMainUpdatePass;
    private Button btnPersonalMainDangxuat;
    private ImageView ivPersonalMainPickImage;
    private static final int PICK_PHOTO_FOR_AVATAR = 112;
    Uri bitmap = null;
    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    final StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    Intent intent;

    public static ProfileFragment newInstance() {

        Bundle args = new Bundle();

        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personal_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        anhXa(view);
        intent = new Intent(getContext(),AvatarActivity.class);
        setClickImageView();

        ivPersonalMainPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(pickIntent, PICK_PHOTO_FOR_AVATAR);
                ivPersonalMainPickImage.setBackgroundResource(R.drawable.bg_image_select);
            }
        });

        imgPersonalMainAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu menu = new PopupMenu(getContext(), view);
                MenuInflater inflater = menu.getMenuInflater();
                inflater.inflate(R.menu.menu_image_select, menu.getMenu());
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.menu_upload_image) {
                            Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                            startActivityForResult(pickIntent, PICK_PHOTO_FOR_AVATAR);
                            ivPersonalMainPickImage.setBackgroundResource(R.drawable.bg_image_select);
                            return true;
                        } else if (menuItem.getItemId() == R.id.menu_seen_image) {
                            startActivity(intent);
                            return true;
                        } else return false;
                    }
                });
                menu.show();
            }
        });


    }


    private void anhXa(View view) {

        imgPersonalMainAvatar = view.findViewById(R.id.img_personal_main_avatar);
        imgPersonalMainBaidang = view.findViewById(R.id.img_personal_main_baidang);
        imgPersonalMainInfo = view.findViewById(R.id.img_personal_main_info);
        imgPersonalMainChangInfo = view.findViewById(R.id.img_personal_main_changInfo);
        imgPersonalMainUpdatePass = view.findViewById(R.id.img_personal_main_updatePass);
        btnPersonalMainDangxuat = view.findViewById(R.id.btn_personal_main_dangxuat);
        ivPersonalMainPickImage = view.findViewById(R.id.iv_personal_main_pick_image);

    }

    private void setClickImageView() {
        imgPersonalMainBaidang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), PostPersonalActivity.class));
            }
        });

        imgPersonalMainInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), PersonalInfoActivity.class));
            }
        });
        imgPersonalMainChangInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ChangeInfoActivity.class));
            }
        });

        imgPersonalMainUpdatePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ChangePasswordActivity.class));
            }
        });

        btnPersonalMainDangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), LoginActivity.class));
                getContext().getSharedPreferences("userLog", 0).edit().clear().apply();
                getActivity().finish();

            }
        });


        databaseReference
                .child("user")
                .child(getContext()
                .getSharedPreferences("userLog", 0).getString("userName", null))
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        Log.d(TAG, "onChildAdded: " + snapshot.getValue(User.class));
                        intent.putExtra("avatar",snapshot.getValue(User.class).getAvatar());
                        Picasso.get().load(snapshot.getValue(User.class).getAvatar()).resize(150, 150).placeholder(R.drawable.user_3).into(imgPersonalMainAvatar);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: ");
        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Log.d(TAG, "onActivityResult: data khac null");
                bitmap = data.getData();
                getActivity().getSharedPreferences("userLog", 0).edit().putString("uriImage", bitmap.toString()).apply();
                //  Picasso.get().load(getActivity().getSharedPreferences("userLog",0).getString("uriImage",null)).resize(240,240).into(imgPersonalMainAvatar);

                // getActivity().getSupportFragmentManager().beginTransaction().detach(this).attach(this).commit();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        if (getActivity().getSharedPreferences("userLog", 0).getString("uriImage", null) != null) {
            uploadImage();
        }
    }
    public void uploadImage() {
        try {
            final String nameImage = UUID.randomUUID().toString();
            storageReference.child("imguser/" + nameImage)
                    .putFile(bitmap)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storageReference
                                    .child("imguser/" + nameImage)
                                    .getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Map<String, Object> map = new HashMap<>();
                                            map.put("avatar", uri.toString());
                                            databaseReference.child("user")
                                                    .child(getContext().getSharedPreferences("userLog", 0).getString("userName", null))
                                                    .child(getContext().getSharedPreferences("userLog", 0).getString("idLog", null))
                                                    .updateChildren(map)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Picasso.get().load(getActivity().getSharedPreferences("userLog", 0).getString("uriImage", null)).resize(240, 240).into(imgPersonalMainAvatar);
                                                            Toast.makeText(getContext(), "Cập nhật ảnh thành công", Toast.LENGTH_SHORT).show();
                                                            getActivity().getSharedPreferences("userLog", 0).edit().remove("uriImage").apply();
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(getContext(), "Không thành công", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });

                                        }
                                    });

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
