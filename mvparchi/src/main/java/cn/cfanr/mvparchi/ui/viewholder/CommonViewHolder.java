package cn.cfanr.mvparchi.ui.viewholder;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author xifan
 * @time 2016-04-01
 * @desc 通用适配器的ViewHolder
 */
public class CommonViewHolder {
    private Context context;
    private final SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;

    private CommonViewHolder(Context context, ViewGroup parent, int layoutId,
                             int position) {
        this.context=context;
        this.mPosition = position;
        this.mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        mConvertView.setTag(this);
    }

    /**
     * 获取一个ViewHolder对象
     */
    public static CommonViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        CommonViewHolder holder = null;
        if (convertView == null) {
            holder = new CommonViewHolder(context, parent, layoutId, position);
        } else {
            holder = (CommonViewHolder) convertView.getTag();
            holder.mPosition = position;
        }
        return holder;
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 为TextView设置字符串
     */
    public CommonViewHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * 为TextView设置颜色
     */
    public CommonViewHolder setTextColor(int viewId, int colorId){
        TextView view = getView(viewId);
        view.setTextColor(context.getResources().getColor(colorId));
        return this;
    }

    /**
     * 为ImageView设置图片
     */
    public CommonViewHolder setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);

        return this;
    }

    /**
     * 为ImageView设置图片
     */
    public CommonViewHolder setImageBitmap(int viewId, Bitmap bm) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }

//    /**
//     * 为ImageView设置图片
//     */
//    public CommonViewHolder setImageByUrl(int viewId, String url) {
//        ImageLoader.getInstance(3, ImageLoader.Type.LIFO).loadImage(url, (ImageView) getView(viewId));
//        return this;
//    }

    public int getPosition() {
        return mPosition;
    }

}
