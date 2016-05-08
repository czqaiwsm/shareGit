package com.share.learn.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.share.learn.R;
import com.share.learn.bean.PayDetail;

import java.util.List;

public class DetailAdapter extends BaseAdapter {
    private List<PayDetail> mItemList;
    private Context mContext;

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mItemList == null ? 0 : mItemList.size();
    }

    public DetailAdapter(Context context, List<PayDetail> items) {
        this.mContext = context;
        this.mItemList = items;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.detail_adapter, null);
            convertView.setTag(holder);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        PayDetail map = mItemList.get(position);
        if(map != null){
            holder.detailContent.setText(map.getTitle());
            holder.price.setText(map.getPrice());
            holder.time.setText(map.getCreateTime());
        }

        return convertView;
    }


    @Override
    public Object getItem(int position) {

        return mItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    static class ViewHolder {
        @Bind(R.id.detailContent)
        TextView detailContent;
        @Bind(R.id.time)
        TextView time;
        @Bind(R.id.price)
        TextView price;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
