package vn.timtro.timtroproject.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import vn.timtro.timtroproject.R;

public class PostPersonalAdapter extends RecyclerView.Adapter<PostPersonalAdapter.PostPersonalHolder> {

    private static final String TAG = "AAA";
    Context context;

    public PostPersonalAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public PostPersonalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_quanlibaidang, parent,false);
        PostPersonalHolder holder = new PostPersonalHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final PostPersonalHolder holder, int position) {
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
                            Log.d(TAG, "onMenuItemClick: Delete");
                            return true;
                        }else if(menuItem.getItemId() == R.id.menu_post_manager_edit){
                            Log.d(TAG, "onMenuItemClick: Edit");
                            return true;
                        }else return false;


                    }
                });
                menu.show();
                /*RestrictedApi so pass
                @SuppressLint("RestrictedApi") MenuBuilder menuBuilder = new MenuBuilder(context);
                MenuInflater menuInflater = new MenuInflater(context);
                menuInflater.inflate(R.menu.menu_post_manager,menuBuilder);
                @SuppressLint("RestrictedApi") MenuPopupHelper menuPopupHelper = new MenuPopupHelper(context,menuBuilder,view);
                menuPopupHelper.setForceShowIcon(true);
                menuPopupHelper.show();*/
            }
        });
    }



    @Override
    public int getItemCount() {
        return 5;
    }



    protected class PostPersonalHolder extends RecyclerView.ViewHolder {
        private ImageButton ibMenu;
        private ImageView vpQuanLiBaiDang;
        private TextView tvQuanLiBaiDangTitle;
        private TextView tvItemQuanlibaidangDientich;
        private ImageView imageView2;
        private TextView tvItemQuanlibaidangGiatien;
        private TextView tvItemQuanlibaidangNumberPhone;
        private TextView tvQuanLiBaiDangAddress;
        public PostPersonalHolder(@NonNull View itemView) {
            super(itemView);


            ibMenu = itemView.findViewById(R.id.ib_menu);
            vpQuanLiBaiDang = itemView.findViewById(R.id.vp_quan_li_bai_dang);
            tvQuanLiBaiDangTitle = itemView.findViewById(R.id.tv_quan_li_bai_dang_title);
            tvItemQuanlibaidangDientich = itemView.findViewById(R.id.tv_item_quanlibaidang_dientich);
            imageView2 = itemView.findViewById(R.id.imageView2);
            tvItemQuanlibaidangGiatien = itemView.findViewById(R.id.tv_item_quanlibaidang_giatien);
            tvItemQuanlibaidangNumberPhone = itemView.findViewById(R.id.tv_item_quanlibaidang_numberPhone);
            tvQuanLiBaiDangAddress = itemView.findViewById(R.id.tv_quan_li_bai_dang_address);

        }
    }
}
