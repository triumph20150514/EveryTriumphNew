package com.warm.pic.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.warm.pic.mode.Floder;

import java.util.ArrayList;
import java.util.List;

/**
 * author: Trimph
 * data: 2017/4/7.
 * description:
 */

public class FolderAdapter extends BaseAdapter {

    public List<Floder> floders = new ArrayList<>();

    public List<Floder> getFloders() {
        return floders;
    }

    public void setFloders(List<Floder> floders) {
        this.floders = floders;
    }

    @Override
    public int getCount() {
        return floders.size();
    }

    @Override
    public Object getItem(int position) {
        return floders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }


    public class FolderViewHolder {
        public FolderViewHolder(View contentView) {
        }
    }


}
