package vn.timtro.timtroproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vn.timtro.timtroproject.BigSlideImageActivity;
import vn.timtro.timtroproject.R;

public class SlideImageAdapter extends PagerAdapter {

    final Context context;
    final ArrayList<String> images;

    public SlideImageAdapter(Context context, ArrayList<String> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_slide, container,false);
        ImageView ivItemSlideImage = view.findViewById(R.id.iv_item_slide_image);
        Picasso.get().load(images.get(position)).resize(480,720).into(ivItemSlideImage);
        container.addView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(new Intent(context, BigSlideImageActivity.class));
                context.startActivity(intent);
            }
        });


        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
