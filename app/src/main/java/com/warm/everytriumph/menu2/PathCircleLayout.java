package com.warm.everytriumph.menu2;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

import com.warm.everytriumph.R;

/**
 * 半圆主菜单 Created by hzw on 2016/9/1.
 */
public class PathCircleLayout extends RelativeLayout {

	public static final String tag = "PathCircleLayout";

	public static final int ICONS[] = new int[] { R.drawable.ic_play_circle_outline_black_24dp, R.drawable.ic_play_circle_outline_black_24dp,
			R.drawable.ic_play_circle_outline_black_24dp, R.drawable.ic_play_circle_outline_black_24dp, R.drawable.ic_play_circle_outline_black_24dp,
			R.drawable.ic_play_circle_outline_black_24dp };
//
	public static final int[] NAMES = new int[] { R.string.app_name, R.string.app_name, R.string.app_name,
			R.string.app_name, R.string.app_name, R.string.app_name };

	private PathCircleItemView[] mItemViews = new PathCircleItemView[ICONS.length];

	private PathMeasure mPathMeasure;
	private float mPathLength;
	private long mTotalDuration = 800;
	private ViewHolder[] mHolders = new ViewHolder[ICONS.length];
	private OnItemClickListener mOnItemClickListener;
	private OnAnimatorStateListener mOnAnimatorStateListener;
	private boolean isCompleted = false;

	public PathCircleLayout(Context context) {
		this(context, null);
	}

	public PathCircleLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		View view = View.inflate(context, R.layout.path_circle_layout, null);
		mItemViews[0] = (PathCircleItemView) view.findViewById(R.id.path_circle_item_camera);
		mItemViews[1] = (PathCircleItemView) view.findViewById(R.id.path_circle_item_selfie);
		mItemViews[2] = (PathCircleItemView) view.findViewById(R.id.path_circle_item_antenna);
		mItemViews[3] = (PathCircleItemView) view.findViewById(R.id.path_circle_item_fast_fill);
		mItemViews[4] = (PathCircleItemView) view.findViewById(R.id.path_circle_item_fingerprint);
		mItemViews[5] = (PathCircleItemView) view.findViewById(R.id.path_circle_item_more);

		for (int i = 0; i < mItemViews.length; i++) {
			mItemViews[i].setIcon(ICONS[i]);
//			if(i==1){
//				mItemViews[i].setName(BaseTools.setSpannable(getResources().getString(NAMES[i])));
//			}else{
//
//			}
			mItemViews[i].setName(getResources().getString(NAMES[i]));
		}

		addView(view);

		mPathMeasure = createPathMeasure();
		mPathLength = mPathMeasure.getLength();

		float blockLength = mPathLength / (mItemViews.length - 1);
		long blockDuration = mTotalDuration / mItemViews.length;

		float tempLength;
		long tempDuration;
		long tempDelay;
		for (int i = 0; i < mHolders.length; i++) {
			tempLength = mPathLength - i * blockLength;
			tempDuration = mTotalDuration - i * blockDuration;
			tempDelay = i * blockDuration;
			mHolders[i] = new ViewHolder(mItemViews[i], tempLength, tempDuration, tempDelay);
		}
		reset();

		for (int i = 0; i < mItemViews.length; i++) {
			addClick(mItemViews[i], i);
		}
	}
	
	

	private void addClick(PathCircleItemView view, final int position) {
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try{
					if (mOnItemClickListener != null && isCompleted) {
						mOnItemClickListener.onItemClick(position, (PathCircleItemView) v);
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private PathMeasure createPathMeasure() {
		Path path = new Path();
		RectF rectF = new RectF(-320, 540, 780, 1640);
		path.addArc(rectF, 90, -180);
		return new PathMeasure(path, false);
	}

	public void startAnimator(final int position, final PathCircleItemView view, float length, long duration) {
		view.setVisibility(VISIBLE);
		final float halfWidth = view.getWidth() / 2;
		final float halfHeight = view.getHeight() / 2;
		ValueAnimator animator = ValueAnimator.ofFloat(0, length);
		animator.setDuration(duration);
		animator.setInterpolator(new DecelerateInterpolator(1.5f));
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float distance = (Float) animation.getAnimatedValue();
				float[] currentPoint = new float[2];
				mPathMeasure.getPosTan(distance, currentPoint, null);
				view.setX(currentPoint[0] - halfWidth);
				view.setY(currentPoint[1] - halfHeight);
			}
		});
		animator.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				view.startAnimation();
				if (position == mHolders.length - 1) {
					isCompleted = true;
					if (mOnAnimatorStateListener != null) {
						mOnAnimatorStateListener.onAnimatorEnd();
					}
				}
			}
		});
		animator.start();
	}

	public void startAnimatorDelay(final int position, final PathCircleItemView view, final float length, final long duration, long delay) {
		try{
			postDelayed(new Runnable() {
				@Override
				public void run() {
					startAnimator(position, view, length, duration);
				}
			}, delay);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void startAnimatorAll() {
		ViewHolder holder;
		isCompleted = false;
		for (int i = 0; i < mHolders.length; i++) {
			holder = mHolders[i];
			startAnimatorDelay(i, holder.mItemView, holder.mLength, holder.mDuration, holder.mDelay);
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		View child = getChildAt(0);
		child.layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
	}

	public OnItemClickListener getOnItemClickListener() {
		return mOnItemClickListener;
	}

	public void addOnItemClickListener(OnItemClickListener onItemClickListener) {
		mOnItemClickListener = onItemClickListener;
	}

	public OnAnimatorStateListener getOnAnimatorStateListener() {
		return mOnAnimatorStateListener;
	}

	public void setOnAnimatorStateListener(OnAnimatorStateListener onAnimatorStateListener) {
		mOnAnimatorStateListener = onAnimatorStateListener;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void reset() {
		for (PathCircleItemView item : mItemViews) {
			item.reset();
			item.setX(0f);
			item.setY(0f);
			item.setVisibility(INVISIBLE);
		}
	}

	class ViewHolder {
		PathCircleItemView mItemView;
		float mLength;
		long mDuration;
		long mDelay;

		public ViewHolder(PathCircleItemView itemView, float length, long duration, long delay) {
			mItemView = itemView;
			mLength = length;
			mDuration = duration;
			mDelay = delay;
		}
	}

	public interface OnItemClickListener {
		void onItemClick(int position, PathCircleItemView view);
	}

	public interface OnAnimatorStateListener {
		void onAnimatorEnd();
	}
}
