package com.share.learn.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.share.learn.R;
import com.share.learn.bean.CourseInfo;

import java.util.List;
import java.util.Map;

public class TeacherCourseAdapter extends BaseAdapter {
    private List<CourseInfo> mItemList;
    private Context mContext;

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mItemList == null ? 0 : mItemList.size();
    }

    public TeacherCourseAdapter(Context context, List<CourseInfo> items) {
        this.mContext = context;
        this.mItemList = items;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.teacher_course_adapter,null);
            holder.courcePrice = (TextView) convertView.findViewById(R.id.couse_price);
            holder.courseName = (TextView) convertView.findViewById(R.id.teacher_course);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        CourseInfo map = mItemList.get(position);
        if(map != null){
           holder.courseName.setText(map.getCourseName());
            holder.courcePrice.setText(String.format(mContext.getResources().getString(R.string.price),map.getPrice()) );
        }
        return convertView;
    }

    class ViewHolder {
        private TextView courcePrice;
        private TextView courseName;
    }

    @Override
    public Object getItem(int position) {

        return mItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
