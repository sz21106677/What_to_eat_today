package com.jerryzhang0227.whattoeattoday.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jerryzhang0227.whattoeattoday.R;
import com.jerryzhang0227.whattoeattoday.model.Food;

import java.util.LinkedList;

public class FoodAdapter extends BaseAdapter {

    private LinkedList<Food> mData;
    private Context mContext;

    public FoodAdapter(LinkedList<Food> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(mContext).inflate(R.layout.listview_food,viewGroup,false);
        TextView txt_mFood = view.findViewById(R.id.tv_foodname);
        TextView txt_weight = view.findViewById(R.id.tx_weight);
        txt_mFood.setText(mData.get(i).getFoodName());
        txt_weight.setText(mData.get(i).getWeight()+"");
        return view;
    }
}
