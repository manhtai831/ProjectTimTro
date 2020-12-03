package vn.timtro.timtroproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.timtro.timtroproject.R;

public class ProvinceAdapter extends BaseAdapter{
    final Context context;
    final ArrayList<String> listProvinces;
    final ArrayList<String> filterProvince;

    public ProvinceAdapter(Context context, ArrayList<String> listProvinces) {
        this.context = context;
        this.listProvinces = listProvinces;
        this.filterProvince = new ArrayList<>();
        this.filterProvince.addAll(listProvinces);
    }

    @Override
    public int getCount() {
        return listProvinces.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.item_province, viewGroup,false);
         TextView tvItemProvince = view.findViewById(R.id.tv_item_province);
         tvItemProvince.setText(listProvinces.get(i));
        return view;
    }



    public void filter(String charText){

        listProvinces.clear();
        if(charText.length() == 0){
            listProvinces.addAll(filterProvince);
        }else{
            for(String s: filterProvince){
                if(s.toLowerCase().contains(charText)){
                    listProvinces.add(s);
                }
            }
        }
        notifyDataSetChanged();
    }

}
