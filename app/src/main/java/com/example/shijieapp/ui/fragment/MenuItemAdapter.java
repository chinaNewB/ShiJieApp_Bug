package com.example.shijieapp.ui.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shijieapp.R;
import com.example.shijieapp.bean.MenuItem;

import java.util.List;

/**
 * Date: 2020/5/2 21:35
 * author:renzhipeng
 */
public class MenuItemAdapter extends ArrayAdapter<MenuItem> {

    private int resourceId;

    // 选中项
    private int mSelect = 0;

    public MenuItemAdapter(Context context, int textViewResourceId, List<MenuItem> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MenuItem menuItem = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.itemIcon = view.findViewById(R.id.menu_item_icon);
            viewHolder.itemTitle = view.findViewById(R.id.menu_item_title);

            // 将ViewHolder存储在View中
            view.setTag(viewHolder);
        } else {
            view = convertView;

            // 重新获取ViewHolder
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.itemTitle.setText(menuItem.getItemTitle());
        viewHolder.itemIcon.setBackgroundResource(menuItem.getItemIcon());

        if (mSelect == position) {
            viewHolder.itemTitle.setTextColor(getContext().getResources().getColor(R.color.colorPurple));
        } else {
            viewHolder.itemTitle.setTextColor(getContext().getResources().getColor(R.color.colorBlack));
        }
        return view;
    }

    public void changeSelected(int position) {
        if (position != mSelect) {
            mSelect = position;
            notifyDataSetChanged();
        }
    }

    class ViewHolder {
        TextView itemTitle;
        ImageView itemIcon;
    }
}

