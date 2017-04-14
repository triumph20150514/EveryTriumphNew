package com.warm.pic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.warm.pic.R;
import com.warm.pic.mode.Floder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * author: Trimph
 * data: 2017/4/7.
 * description:
 */

public class FolderAdapter extends BaseAdapter {

    public Context context;

    public FolderAdapter(Context context) {
        this.context = context;
    }

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
        FolderViewHolder folderViewHolder;

        Floder floder = floders.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.floder_item, parent, false);
            folderViewHolder = new FolderViewHolder(convertView);
            convertView.setTag(folderViewHolder);
        } else {
            folderViewHolder = (FolderViewHolder) convertView.getTag();
        }

        if (folderViewHolder != null) {
            Picasso.with(context).load(new File(floder.getFirstImage().getPath()))
//                    .centerCrop()
                    .error(R.mipmap.ic_launcher)
                    .into(folderViewHolder.floder_image);
            folderViewHolder.floder_name.setText(floder.getName());
        }
        return convertView;
    }


    public class FolderViewHolder {
        public ImageView floder_image;
        public TextView floder_name;

        public FolderViewHolder(View contentView) {
            floder_image = (ImageView) contentView.findViewById(R.id.floder_image);
            floder_name = (TextView) contentView.findViewById(R.id.floder_name);
        }
    }


}
