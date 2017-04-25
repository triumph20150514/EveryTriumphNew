package com.warm.pic.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.warm.pic.R;
import com.warm.pic.mode.Floder;
import com.warm.pic.mode.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * author: Trimph
 * data: 2017/4/6.
 * description:
 */

public class FlodersAdapter extends RecyclerView.Adapter {
    private static int open_camera = 0;
    private static int item_image = 1;

    public List<Floder> floders = new ArrayList<>();

    public boolean isShowCamera = true; //默认显示打开相机按钮

    public boolean isShowCamera() {
        return isShowCamera;
    }

    public void setShowCamera(boolean showCamera) {
        isShowCamera = showCamera;
    }

    public Context context;

    public FlodersAdapter(Context context, List<Floder> imageList) {
        this.context = context;
        this.floders = imageList;
    }

    public FlodersAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.floder_item, parent, false);
        return new FoldersViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Floder floder = floders.get(position);
        if (floder == null) {
            return;
        }

        FoldersViewHolder imageItemViewHolder = (FoldersViewHolder) holder;
//            imageItemViewHolder.imageView.setImageBitmap(getBitmap(image.getPath()));
        imageItemViewHolder.floder_name.setText(floder.getName());
        imageItemViewHolder.floder_path.setText(floder.getPath());
        Picasso.with(context).load(new File(floder.getFirstImage().getPath()))
//                    .centerCrop()
                .error(R.mipmap.ic_launcher)
                .into(imageItemViewHolder.floder_image);

        imageItemViewHolder.floder_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }


    @Override
    public int getItemCount() {
        return floders.size();
    }


    public class FoldersViewHolder extends RecyclerView.ViewHolder {

        public ImageView floder_image;
        public TextView floder_name, floder_path;

        public FoldersViewHolder(View contentView) {
            super(contentView);
            floder_image = (ImageView) contentView.findViewById(R.id.floder_image);
            floder_name = (TextView) contentView.findViewById(R.id.floder_name);
            floder_path = (TextView) contentView.findViewById(R.id.floder_path);
        }
    }


    public class OpenCameraHolder extends RecyclerView.ViewHolder {
        public OpenCameraHolder(View itemView) {
            super(itemView);
        }
    }


}
