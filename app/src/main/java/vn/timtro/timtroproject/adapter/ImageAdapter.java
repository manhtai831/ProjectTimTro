package vn.timtro.timtroproject.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vn.timtro.timtroproject.R;
import vn.timtro.timtroproject.fragment.AddPostFragment;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageHolder> {

    final Context context;
    final ArrayList<Uri> uris;

    public ImageAdapter(Context context, ArrayList<Uri> uris) {
        this.context = context;
        this.uris = uris;
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_image, parent,false);
        return new ImageHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageHolder holder, final int position) {

       Picasso.get().load(uris.get(position)).resize(120,240).into(holder.ivImage);

        holder.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uris.remove(position);
                notifyDataSetChanged();

            }
        });
    }

    @Override
    public int getItemCount() {
        return uris.size();
    }

    public static class ImageHolder extends RecyclerView.ViewHolder {
        private final ImageView ivImage;
        private final ImageView ivClose;

        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_image);
            ivClose = itemView.findViewById(R.id.iv_close);
        }
    }
}
