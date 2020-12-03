  package vn.timtro.timtroproject.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;

import vn.timtro.timtroproject.EditPostActivity;
import vn.timtro.timtroproject.R;
import vn.timtro.timtroproject.model.Image;
import vn.timtro.timtroproject.model.Post;

public class PostPersonalAdapter extends RecyclerView.Adapter<PostPersonalAdapter.PostPersonalHolder> {

    private static final String TAG = "AAA";
    final Context context;
    final ArrayList<Post> posts;
    final ArrayList<Image> images;
    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public PostPersonalAdapter(Context context, ArrayList<Post> posts, ArrayList<Image> images) {
        this.context = context;
        this.posts = posts;
        this.images = images;
    }

    @NonNull
    @Override
    public PostPersonalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_quanlibaidang, parent,false);
        return new PostPersonalHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final PostPersonalHolder holder, int position) {
        final Post post = posts.get(position);
        final Intent intent = new Intent(context, EditPostActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("post",post);
        holder.tvItemQuanlibaidangDientich.setText(post.getDienTich() + " mét vuông");
        NumberFormat numberFormat = NumberFormat.getInstance();
        holder.tvItemQuanlibaidangGiatien.setText(numberFormat.format(Long.parseLong(post.getGia())) + " VNĐ");
        holder.tvQuanLiBaiDangAddress.setText(post.getDiaChi());
        holder.tvQuanLiBaiDangTitle.setText(post.getTieuDe());
        ArrayList<String> listUriImage = new ArrayList<>();
        for(Image img:images){
            if(post.getId().equals(img.getIdPost())){
                Picasso.get().load(img.getLink()).into(holder.ivItemHomeImage);
                listUriImage.add(img.getLink());

            }
        }
        bundle.putStringArrayList("listUri",listUriImage);

        intent.putExtra("bundle",bundle);

        holder.ibMenu.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                PopupMenu menu = new PopupMenu(context,view);
                MenuInflater inflater = menu.getMenuInflater();
                inflater.inflate(R.menu.menu_post_manager,menu.getMenu());
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getItemId() == R.id.menu_post_manager_delete){
                            deleteItem(post);
                            return true;
                        }else if(menuItem.getItemId() == R.id.menu_post_manager_edit){


                            context.startActivity(intent);
                            return true;
                        }else return false;


                    }
                });
                menu.show();
            }
        });


    }

    private void deleteItem(final Post post) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn muốn xóa " + post.getTieuDe() + " không?" );
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                databaseReference.child("post").child(post.getId()).removeValue();
                ((Activity) context).finish();
                context.startActivity(((Activity) context).getIntent());

            }
        });
        builder.setNegativeButton("Không", null);
        builder.show();
    }


    @Override
    public int getItemCount() {
        return posts.size();
    }



    protected static class PostPersonalHolder extends RecyclerView.ViewHolder {
        private final ImageButton ibMenu;

        private final TextView tvQuanLiBaiDangTitle;
        private final TextView tvItemQuanlibaidangDientich;

        private final TextView tvItemQuanlibaidangGiatien;
        private final ImageView ivItemHomeImage;



        private final TextView tvQuanLiBaiDangAddress;
        public PostPersonalHolder(@NonNull View itemView) {
            super(itemView);


            ibMenu = itemView.findViewById(R.id.ib_menu);
            tvQuanLiBaiDangTitle = itemView.findViewById(R.id.tv_item_home_title);
            tvItemQuanlibaidangDientich = itemView.findViewById(R.id.tv_item_home_acreage);

            tvItemQuanlibaidangGiatien = itemView.findViewById(R.id.tv_item_home_price);
            ivItemHomeImage = itemView.findViewById(R.id.iv_item_home_image);
            tvQuanLiBaiDangAddress = itemView.findViewById(R.id.tv_item_home_address);

        }
    }
}
