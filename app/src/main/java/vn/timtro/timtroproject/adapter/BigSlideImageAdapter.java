package vn.timtro.timtroproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vn.timtro.timtroproject.R;
import vn.timtro.timtroproject.model.Image;

public class BigSlideImageAdapter extends PagerAdapter {

    final Context context;
    final ArrayList<Image> images;

    public BigSlideImageAdapter(Context context, ArrayList<Image> images) {
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
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_big_slide, container,false);
        ImageView ivItemSlideImage = view.findViewById(R.id.iv_item_big_slide);
        Picasso.get().load(images.get(position).getLink()).resize(720,1080).into(ivItemSlideImage);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
