package vn.timtro.timtroproject.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import vn.timtro.timtroproject.LoginActivity;
import vn.timtro.timtroproject.R;
import vn.timtro.timtroproject.adapter.ImageAdapter;
import vn.timtro.timtroproject.model.Image;
import vn.timtro.timtroproject.model.Post;

public class AddPostFragment extends Fragment {

    private RecyclerView viewPaperAddpostAddPostAnh;

    private ImageView imgbnAddpostChupAnh;
    private TextInputEditText edtAddPostTitle;
    private TextInputEditText edtAddPostPrice;
    private TextInputEditText edtAddPostAddress;
    private TextInputEditText edtAddPostPhoneNumber;
    private TextInputEditText edtAddPostAcreage;
    private TextInputEditText edtAddPostDecription;
    private Button btnAddPostUpload;
    private Button btnAddPostCancel;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    final int REQUEST_GALLERY = 831;
    final int REQUEST_CODE = 1;
    final String TAG = "AAA";
    private Bitmap mImageUri;
    Uri uri;
    final ArrayList<Uri> uris = new ArrayList<>();
    public ImageAdapter imageAdapter;
    Intent intent;
    ArrayList<String> endCodeList;
    int i;
    private Spinner spnAddPostBrand;

    public static AddPostFragment newInstance() {

        Bundle args = new Bundle();

        AddPostFragment fragment = new AddPostFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_addpost, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        anhXa(view);
        setClick();

        ArrayList<String> listBrand = new ArrayList<>();
        listBrand.add("Danh mục");
        listBrand.add("Nhà trọ chung chủ");
        listBrand.add("Nhà trọ tự quản");
        listBrand.add("Chung cư");
        listBrand.add("Chung cư mini");
        listBrand.add("Homestay");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, listBrand);
        spnAddPostBrand.setAdapter(adapter);


    }

    private void setClick() {
        btnAddPostCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearForm();
            }
        });
        btnAddPostUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upLoadPost();
            }
        });

        imgbnAddpostChupAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_GALLERY);
            }
               /* if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {

//check if have permmission to write to external + permmision to camera

                    if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

//if its have permission then new intent to capture image

                        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

//if android version >= 24 (Android Nugget)

                        if (Build.VERSION.SDK_INT >= 24) {

//create new String to pictures directory and set the file name to current_time.jpg
                            String file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + Calendar.getInstance().getTimeInMillis() + ".jpg";
                            File file1 = new File(file);

//set GLOBAL variable Uri from the file using FileProvider

                            uri = FileProvider.getUriForFile(getContext(), getActivity().getPackageName() + ".provider", file1);

//add the file location the intent (the picture will be save to this file)

                            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                            startActivityForResult(intent, REQUEST_CODE);
                        }
//else - dont have permission, request for permission
                    } else {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_CAMERA);
                    }
                } else {
// same thing only without FileProvider (FileProvider only required since Nugget)

                    intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    String file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + Calendar.getInstance().getTimeInMillis() + ".jpg";
                    File file1 = new File(file);
                    uri = Uri.fromFile(file1);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    startActivityForResult(intent, REQUEST_CODE);
                }

//if phone can handle the intent, start the intent for resault.


            }*/
        });


    }

    private void upLoadPost() {
        if (validate()) {
            final Date date = Calendar.getInstance().getTime();
            final String key = databaseReference.child("post").push().getKey();
            String idUser = databaseReference.child("user").child(LoginActivity.userName).getKey();
            final Post post = new Post(key, idUser, edtAddPostTitle.getText().toString(), edtAddPostPrice.getText().toString(),
                    edtAddPostAddress.getText().toString(), edtAddPostPhoneNumber.getText().toString(),
                    edtAddPostAcreage.getText().toString(), spnAddPostBrand.getSelectedItem().toString(), edtAddPostDecription.getText().toString(), date.toString());
            databaseReference.child("post").child(key).setValue(post);
            final ProgressDialog dialog = new ProgressDialog(getContext());
            dialog.show();

            for (Uri uri : uris) {
                final String nameImage = UUID.randomUUID().toString();
                storageReference.
                        child("images/" + nameImage)
                        .putFile(uri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                storageReference
                                        .child("images/" + nameImage)
                                        .getDownloadUrl()
                                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                Log.d(TAG, "onSuccess: " + uri.toString());
                                                String keyImage = databaseReference.child("post").child(key).child("linkAnh").push().getKey();
                                                Image image = new Image(keyImage, key, uri.toString());
                                                databaseReference
                                                        .child("post")
                                                        .child(key)
                                                        .child("linkAnh")
                                                        .child(keyImage)
                                                        .setValue(image);
                                            }

                                        });
                                dialog.dismiss();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                double progress = (100.0 * snapshot.getBytesTransferred() / snapshot
                                        .getTotalByteCount());
                                dialog.setMessage("Uploaded " + (int) progress + "%");
                                dialog.show();
                            }
                        });

            }
            clearForm();
        }

    }

    private boolean validate() {
        String title = edtAddPostTitle.getText().toString().trim();
        String price = edtAddPostPrice.getText().toString().trim();
        String address = edtAddPostAddress.getText().toString().trim();
        String phoneNumber = edtAddPostPhoneNumber.getText().toString().trim();
        String acreage = edtAddPostAcreage.getText().toString().trim();

        if (title.length() == 0) {
            edtAddPostTitle.setError("Chưa nhập tiêu đề");

            return false;
        } else if (price.length() == 0) {
            edtAddPostPrice.setError("Chưa nhập giá nhà trọ");
            return false;
        } else if (!price.matches("[0-9]{1,12}")) {
            edtAddPostPrice.setError("Giá chỉ nhập số và không quá 12 số");
            return false;
        } else if (address.length() == 0) {
            edtAddPostAddress.setError("Chưa nhập địa chỉ");
            return false;
        } else if (phoneNumber.length() == 0 || !phoneNumber.matches("0[0-9]{9,10}")) {
            edtAddPostPhoneNumber.setError("Chưa nhập số điện thoại hoặc không đúng định dạng");
            return false;
        } else if (acreage.length() < 1) {
            edtAddPostAcreage.setError("Chưa nhập diện tích");
            return false;
        } else if (!acreage.matches("[0-9]{1,6}")) {
            edtAddPostAcreage.setError("Diện tích chỉ nhập số và không quá 6 số");
            return false;
        } else if (spnAddPostBrand.getSelectedItem().toString().equals("Danh mục")) {
            Toast.makeText(getContext(), "Chưa chọn danh mục", Toast.LENGTH_SHORT).show();
            return false;
        }else if(viewPaperAddpostAddPostAnh.getChildCount() < 1){
            Toast.makeText(getContext(), "Chọn ít nhất 1 ảnh", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void clearForm() {
        getActivity().getSupportFragmentManager().beginTransaction().detach(this).attach(this).commit();
        edtAddPostDecription.setText("");
        edtAddPostPhoneNumber.setText("");
        edtAddPostAcreage.setText("");
        edtAddPostAddress.setText("");
        edtAddPostPrice.setText("");
        edtAddPostTitle.setText("");
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALLERY && resultCode == Activity.RESULT_OK) {
            if (data.getData() != null) {
                Uri uri = data.getData();
                uris.add(uri);
                imageAdapter = new ImageAdapter(getContext(), uris);
                viewPaperAddpostAddPostAnh.setAdapter(imageAdapter);
            } else {
                if (data.getClipData() != null) {
                    ClipData mClipData = data.getClipData();
                    for (int i = 0; i < mClipData.getItemCount(); i++) {
                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri uri = item.getUri();
                        uris.add(uri);
                    }
                    imageAdapter = new ImageAdapter(getContext(), uris);
                    viewPaperAddpostAddPostAnh.setAdapter(imageAdapter);
                    Log.v("LOG_TAG", "Selected Images");
                }
            }
        } else {
            Toast.makeText(getContext(), "You haven't picked Image",
                    Toast.LENGTH_LONG).show();
        }
    }


    private void anhXa(View view) {
        viewPaperAddpostAddPostAnh = view.findViewById(R.id.view_paper_addpost_addPostAnh);
        imgbnAddpostChupAnh = view.findViewById(R.id.imgbn_addpost_chupAnh);
        edtAddPostTitle = view.findViewById(R.id.edt_add_post_title);
        edtAddPostPrice = view.findViewById(R.id.edt_add_post_price);
        edtAddPostAddress = view.findViewById(R.id.edt_add_post_address);
        edtAddPostPhoneNumber = view.findViewById(R.id.edt_add_post_phone_number);
        edtAddPostAcreage = view.findViewById(R.id.edt_add_post_acreage);
        edtAddPostDecription = view.findViewById(R.id.edt_add_post_decription);
        btnAddPostUpload = view.findViewById(R.id.btn_add_post_upload);
        btnAddPostCancel = view.findViewById(R.id.btn_add_post_cancel);
        spnAddPostBrand = view.findViewById(R.id.spn_add_post_brand);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();


    }


}
