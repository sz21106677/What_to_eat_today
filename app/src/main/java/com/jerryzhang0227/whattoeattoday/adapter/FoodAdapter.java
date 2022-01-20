package com.jerryzhang0227.whattoeattoday.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jerryzhang0227.whattoeattoday.R;
import com.jerryzhang0227.whattoeattoday.model.Food;

import java.util.LinkedList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.myViewHolder> {

    private LinkedList<Food> mData;
    private Context mContext;

    public FoodAdapter(LinkedList<Food> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //填充相应的view
        View view = View.inflate(mContext, R.layout.recycleview_food,null);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        //绑定值
        holder.foodname.setText(mData.get(position).getFoodName());
        holder.weight.setText(mData.get(position).getWeight()+"");
    }

    @Override
    public int getItemCount() {
        //得到item大小
        return mData.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        private TextView foodname;
        private TextView weight;
        
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            foodname = itemView.findViewById(R.id.tv_foodname);
            weight = itemView.findViewById(R.id.tx_weight);

            itemView.setOnLongClickListener(view -> {
                if (mOnRecycleViewItemsOnClickListener != null) {
                    mOnRecycleViewItemsOnClickListener.onRecycleViewItemsOnLongClick(getLayoutPosition());
                }
                return false;
            });
        }
    }

    //定义监听器
    private OnRecycleViewItemsOnClickListener mOnRecycleViewItemsOnClickListener;

    //定义外部方法
    public void setmOnRecycleViewItemsOnLongClickListener(OnRecycleViewItemsOnClickListener listener) {
        mOnRecycleViewItemsOnClickListener = listener;
    }

    //新建接口用于监听
    public interface OnRecycleViewItemsOnClickListener {
        void onRecycleViewItemsOnLongClick(int position);
    }
}
