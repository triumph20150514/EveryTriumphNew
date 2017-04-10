package com.warm.pic.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.warm.pic.R;
import com.warm.pic.mode.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * author: Trimph
 * data: 2017/4/6.
 * description:
 */

public class ImageItemAdapter extends RecyclerView.Adapter {
    private static int open_camera = 0;
    private static int item_image = 1;

    public List<Image> imageList = new ArrayList<>();
    public boolean isShowCamera = true; //默认显示打开相机按钮

    public boolean isShowCamera() {
        return isShowCamera;
    }

    public void setShowCamera(boolean showCamera) {
        isShowCamera = showCamera;
    }

    public Context context;

    public ImageItemAdapter(Context context, List<Image> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    public ImageItemAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            if (isShowCamera) { //是否显示可以调到相机
                view = LayoutInflater.from(context).inflate(R.layout.image_open_camera, parent, false);
                return new OpenCameraHolder(view);
            } else {
                view = LayoutInflater.from(context).inflate(R.layout.image_item, parent, false);
                return new ImageItemViewHolder(view);
            }
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.image_item, parent, false);
            return new ImageItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Image image = imageList.get(position);
        if (image == null) {
            return;
        }
        if (holder instanceof OpenCameraHolder) {
            OpenCameraHolder openCameraHolder = (OpenCameraHolder) holder;

        } else if (holder instanceof ImageItemViewHolder) {
            ImageItemViewHolder imageItemViewHolder = (ImageItemViewHolder) holder;
//            imageItemViewHolder.imageView.setImageBitmap(getBitmap(image.getPath()));
            Picasso.with(context).load(new File(image.getPath()))
//                    .centerCrop()
                    .error(R.mipmap.ic_launcher)
                    .into(imageItemViewHolder.imageView);
        }
    }

    public Bitmap getBitmap(String path) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        return bitmap;
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }



    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            if (isShowCamera){
                return open_camera;
            }else {
                return item_image;
            }
        } else {
            return item_image;
        }
    }

    public class ImageItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ImageItemViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }


    public class OpenCameraHolder extends RecyclerView.ViewHolder {
        public OpenCameraHolder(View itemView) {
            super(itemView);
        }
    }


}
