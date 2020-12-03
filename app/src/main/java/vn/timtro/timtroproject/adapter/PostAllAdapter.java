package vn.timtro.timtroproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.timtro.timtroproject.PostDetailActivity;
import vn.timtro.timtroproject.R;
import vn.timtro.timtroproject.model.Image;
import vn.timtro.timtroproject.model.Post;
import vn.timtro.timtroproject.model.User;

public class PostAllAdapter extends RecyclerView.Adapter<PostAllAdapter.PostAllHolder> {

    private static final String TAG = "AAA";
    final Context context;
    final ArrayList<Post> posts;
    final ArrayList<User> users;
    final ArrayList<Image> uris;
    Intent intent;

    public PostAllAdapter(Context context, ArrayList<Post> posts, ArrayList<User> users, ArrayList<Image> uris) {
        this.context = context;
        this.posts = posts;
        this.users = users;
        this.uris = uris;
    }

    @NonNull
    @Override
    public PostAllHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_home, parent, false);
        return new PostAllHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final PostAllHolder holder, final int position) {
        try{

            for (User u: users) {
                if(u.getUserName().equals(posts.get(position).getIdUser())){
                    holder.tvItemHomeTitle.setText(posts.get(position).getTieuDe());
                    holder.tvItemHomeAcreage.setText(posts.get(position).getDienTich() + " mét vuông");
                    NumberFormat numberFormat = NumberFormat.getNumberInstance();
                    holder.tvItemHomePrice.setText(numberFormat.format(Double.parseDouble(posts.get(position).getGia())) + " VND");
                    holder.tvItemHomeAddress.setText(posts.get(position).getDiaChi());
                    holder.tvItemHomeName.setText(u.getName());
                    Picasso.get().load(u.getAvatar()).placeholder(R.drawable.user_3).resize(120,120).into(holder.imgItemHomeAvatar);


                    Date date = Calendar.getInstance().getTime();
                    Date date1 = new Date(posts.get(position).getTimePost());
                    long diff = (date.getTime() -  date1.getTime())/1000;
                    int days = (int) (diff/86400);
                    int hours = (int) ((diff % 86400) / 3600);
                    int minuates = (int) (((diff % 86400) % 3600)/60);
                    if(days >= 1){
                        holder.tvItemHomeTime.setText(days + " " + context.getResources().getString(R.string.time_day));
                    }else if(hours >= 1){
                        holder.tvItemHomeTime.setText(hours + " " + context.getResources().getString(R.string.time_hour));
                    }else if(minuates >= 1)
                        holder.tvItemHomeTime.setText(minuates + " " + context.getResources().getString(R.string.time_minuates));
                    else if(minuates < 1){
                        holder.tvItemHomeTime.setText(context.getResources().getString(R.string.time_second));
                    }
                }
            }
            for(Image uri: uris){
                if(uri.getIdPost().equals(posts.get(position).getId())){
                    Picasso.get().load(uri.getLink()).resize(250,300).into(holder.ivItemHomeImage);

                }
            }


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intent = new Intent(context,PostDetailActivity.class);
                    intent.putExtra("keyPost",posts.get(position).getId());
                    intent.putExtra("name",holder.tvItemHomeName.getText().toString());
                    intent.putExtra("idUser",posts.get(position).getIdUser());

                    context.startActivity(intent);

                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    protected static class PostAllHolder extends RecyclerView.ViewHolder {
        private final CircleImageView imgItemHomeAvatar;
        private final TextView tvItemHomeName;
        private final TextView tvItemHomeTime;
        private final ImageView ivItemHomeImage;
        private final TextView tvItemHomeTitle;

        private final TextView tvItemHomeAcreage;
        private final TextView tvItemHomePrice;

        private final TextView tvItemHomeAddress;

        public PostAllHolder(@NonNull View itemView) {
            super(itemView);
            imgItemHomeAvatar = itemView.findViewById(R.id.tv_item_home_avatar);
            tvItemHomeName = itemView.findViewById(R.id.tv_item_home_name);
            tvItemHomeTime = itemView.findViewById(R.id.tv_item_home_time);
            ivItemHomeImage = itemView.findViewById(R.id.iv_item_home_image);
            tvItemHomeTitle = itemView.findViewById(R.id.tv_item_home_title);

            tvItemHomeAcreage = itemView.findViewById(R.id.tv_item_home_acreage);
            tvItemHomePrice = itemView.findViewById(R.id.tv_item_home_price);

            tvItemHomeAddress = itemView.findViewById(R.id.tv_item_home_address);
        }
    }
}
