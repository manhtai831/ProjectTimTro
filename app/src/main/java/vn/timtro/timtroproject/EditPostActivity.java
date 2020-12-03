package vn.timtro.timtroproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.UUID;

import vn.timtro.timtroproject.adapter.ImageUpdatePostAdapter;
import vn.timtro.timtroproject.model.Image;
import vn.timtro.timtroproject.model.Post;

public class EditPostActivity extends AppCompatActivity {
    private static final String TAG = "CCC";
    private static final int REQUEST_GALLERY = 123;
    private Toolbar toolbarEditPost;
    private RecyclerView viewPaperAddpostAddPostAnh;
    private ImageView imgbnAddpostChupAnh;
    private TextInputEditText edtAddPostTitle;
    private TextInputEditText edtAddPostPrice;
    private TextInputEditText edtAddPostAddress;
    private TextInputEditText edtAddPostPhoneNumber;
    private TextInputEditText edtAddPostAcreage;
    private Spinner spnAddPostBrand;
    private TextInputEditText edtAddPostDecription;
    private Button btnAddPostUpload;
    private Button btnAddPostCancel;
    public ImageUpdatePostAdapter imageAdapter;
    Intent intent;
    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    Post post;
    final ArrayList<Uri> uris = new ArrayList<>();
    final StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        anhXa();
        setSupportActionBar(toolbarEditPost);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        View view = getSupportActionBar().getCustomView();
        ImageButton ivArrow = view.findViewById(R.id.iv_arrow_2);
        TextView tvCustomActionBar = view.findViewById(R.id.tv_custom_action_bar_2);
        tvCustomActionBar.setText("Chỉnh sửa");
        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final Bundle bundle = getIntent().getExtras().getBundle("bundle");
        for (String s : bundle.getStringArrayList("listUri")) {

            uris.add(Uri.parse(s));
            Log.d(TAG, "onCreate: " + s);
        }

        ArrayList<String> listBrand = new ArrayList<>();
        listBrand.add("Danh mục");
        listBrand.add("Nhà trọ chung chủ");
        listBrand.add("Nhà trọ tự quản");
        listBrand.add("Chung cư");
        listBrand.add("Chung cư mini");
        listBrand.add("Homestay");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listBrand);
        spnAddPostBrand.setAdapter(adapter);

        post = (Post) bundle.getSerializable("post");
        edtAddPostTitle.setText(post.getTieuDe());
        edtAddPostAcreage.setText(post.getDienTich());
        edtAddPostAddress.setText(post.getDiaChi());
        edtAddPostDecription.setText(post.getMoTa());
        edtAddPostPhoneNumber.setText(post.getSoDienThoai());
        edtAddPostPrice.setText(post.getGia());
        for (int i = 0; i < listBrand.size(); i++) {
            if (post.getDanhMuc().equals(listBrand.get(i))) {
                spnAddPostBrand.setSelection(i);
            }
        }
        edtAddPostDecription.setText(post.getMoTa());
        btnAddPostUpload.setText("Cập nhật");

        btnAddPostUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {

                    Post post1 = new Post(post.getId(), post.getIdUser(), edtAddPostTitle.getText().toString(), edtAddPostPrice.getText().toString(),
                            edtAddPostAddress.getText().toString(), edtAddPostPhoneNumber.getText().toString(),
                            edtAddPostAcreage.getText().toString(), spnAddPostBrand.getSelectedItem().toString(),
                            edtAddPostDecription.getText().toString(), post.getTimePost());
                    databaseReference
                            .child("post")
                            .child(post.getId())
                            .setValue(post1)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(EditPostActivity.this, "Thành công", Toast.LENGTH_SHORT).show();


                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Toast.makeText(EditPostActivity.this, "Gặp lỗi trong quá trình update", Toast.LENGTH_SHORT).show();
                                }
                            });
                    final ProgressDialog dialog = new ProgressDialog(EditPostActivity.this);
                    dialog.show();
                    databaseReference
                            .child("post")
                            .child(post.getId())
                            .child("linkAnh")
                            .removeValue();
                    for (Uri uri : uris) {

                        final String nameImage = UUID.randomUUID().toString();
                        storageReference
                                .child("images/" + nameImage)
                                .putFile(uri)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        storageReference
                                                .child("images/" + nameImage)
                                                .getDownloadUrl()
                                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(final Uri uri) {
                                                        String keyImage = databaseReference.child("post").child(post.getId()).child("linkAnh").push().getKey();
                                                        Image image = new Image(keyImage, post.getId(), uri.toString());
                                                        databaseReference
                                                                .child("post")
                                                                .child(post.getId())
                                                                .child("linkAnh")
                                                                .child(keyImage)
                                                                .setValue(image)
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {
                                                                        finish();
                                                                    }
                                                                });
                                                    }

                                                });
                                        dialog.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialog.dismiss();
                                        Toast.makeText(EditPostActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                        double progress = (100.0 * snapshot.getBytesTransferred() / snapshot
                                                .getTotalByteCount());
                                        dialog.setMessage("Update " + (int) progress + "%");
                                        dialog.show();
                                    }
                                });

                    }
                }


            }
        });

        imageAdapter = new ImageUpdatePostAdapter(this, uris);
        viewPaperAddpostAddPostAnh.setAdapter(imageAdapter);
        imgbnAddpostChupAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_GALLERY);
            }
        });
        btnAddPostCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private boolean validate() {
        String title = edtAddPostTitle.getText().toString().trim();
        String price = edtAddPostPrice.getText().toString().trim();
        String address = edtAddPostAddress.getText().toString().trim();
        String phoneNumber = edtAddPostPhoneNumber.getText().toString().trim();
        String acreage = edtAddPostAcreage.getText().toString().trim();

        if (title.length() == 0) {
            Toast.makeText(getApplicationContext(), "Chưa nhập tiêu đề", Toast.LENGTH_SHORT).show();
            return false;
        } else if (price.length() == 0) {
            Toast.makeText(getApplicationContext(), "Chưa nhập giá nhà trọ", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!price.matches("[0-9]{1,12}")) {
            Toast.makeText(getApplicationContext(), "Giá chỉ nhập số và không quá 12 số", Toast.LENGTH_SHORT).show();
            return false;
        } else if (address.length() == 0) {
            Toast.makeText(getApplicationContext(), "Chưa nhập địa chỉ", Toast.LENGTH_SHORT).show();
            return false;
        } else if (phoneNumber.length() == 0 || !phoneNumber.matches("0[0-9]{9}")) {
            Toast.makeText(getApplicationContext(), "Chưa nhập số điện thoại hoặc không đúng định dạng", Toast.LENGTH_LONG).show();
            return false;
        } else if (acreage.length() < 1) {
            Toast.makeText(getApplicationContext(), "Chưa nhập diện tích", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!acreage.matches("[0-9]{1,6}")) {
            Toast.makeText(getApplicationContext(), "Diện tích chỉ nhập số và không quá 6 số", Toast.LENGTH_SHORT).show();
            return false;
        } else if (spnAddPostBrand.getSelectedItem().toString().equals("Danh mục")) {
            Toast.makeText(getApplicationContext(), "Chưa chọn danh mục", Toast.LENGTH_SHORT).show();
            return false;
        } else if (viewPaperAddpostAddPostAnh.getChildCount() < 1) {
            Toast.makeText(getApplicationContext(), "Chọn ít nhất 1 ảnh", Toast.LENGTH_SHORT).show();
            return false;
        }
        for (Uri uri : uris) {
            if (uri.toString().contains("https")) {
                Toast.makeText(this, "Cần chọn ảnh mới", Toast.LENGTH_SHORT).show();

                return false;

            }
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALLERY && resultCode == Activity.RESULT_OK) {
            uris.clear();
            if (data.getData() != null) {
                Uri uri = data.getData();
                uris.add(uri);
                imageAdapter = new ImageUpdatePostAdapter(EditPostActivity.this, uris);
                viewPaperAddpostAddPostAnh.setAdapter(imageAdapter);
            } else {
                if (data.getClipData() != null) {
                    ClipData mClipData = data.getClipData();
                    for (int i = 0; i < mClipData.getItemCount(); i++) {
                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri uri = item.getUri();
                        uris.add(uri);
                    }
                    imageAdapter = new ImageUpdatePostAdapter(EditPostActivity.this, uris);
                    viewPaperAddpostAddPostAnh.setAdapter(imageAdapter);
                    Log.v("LOG_TAG", "Selected Images");
                }
            }
        } else {
            Toast.makeText(EditPostActivity.this, "You haven't picked Image",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void anhXa() {
        toolbarEditPost = findViewById(R.id.toolbar_edit_post);
        viewPaperAddpostAddPostAnh = findViewById(R.id.view_paper_addpost_addPostAnh);
        imgbnAddpostChupAnh = findViewById(R.id.imgbn_addpost_chupAnh);
        edtAddPostTitle = findViewById(R.id.edt_add_post_title);
        edtAddPostPrice = findViewById(R.id.edt_add_post_price);
        edtAddPostAddress = findViewById(R.id.edt_add_post_address);
        edtAddPostPhoneNumber = findViewById(R.id.edt_add_post_phone_number);
        edtAddPostAcreage = findViewById(R.id.edt_add_post_acreage);
        spnAddPostBrand = findViewById(R.id.spn_add_post_brand);
        edtAddPostDecription = findViewById(R.id.edt_add_post_decription);
        btnAddPostUpload = findViewById(R.id.btn_add_post_upload);
        btnAddPostCancel = findViewById(R.id.btn_add_post_cancel);
    }
}