package cn.xifan.demo.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.xifan.demo.R;
import cn.xifan.demo.model.News;
import cn.xifan.demo.utils.ImageUtils;

/**
 * @author xifan
 *         date: 2016/8/29
 *         desc:
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    private Context context;
    private List<News> newsList=new ArrayList<>();

    public HomeAdapter(Context context, List<News> newsList){
        this.context = context;
        this.newsList = newsList;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, String articleId);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        HomeViewHolder homeViewHolder=new HomeViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_home_news, parent, false));  //not  .inflate(R.layout.item_home_news, null) 不能达到阴影效果
        return homeViewHolder;
    }

    @Override
    public void onBindViewHolder(final HomeViewHolder holder, int position) {
        final News news=newsList.get(position);
       holder.tvTitle.setText(news.getTitle());
        List<String> imgList=news.getImages();
        if(imgList!=null&&imgList.size()>0) {
            ImageUtils.loadImage(holder.mImg, news.getImages().get(0));
        }
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int pos=holder.getLayoutPosition();
                    String articleId=news.getId();
                    mOnItemClickListener.onItemClick(holder.itemView, pos, articleId);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return newsList==null ? 0 : newsList.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder{
        ImageView mImg;
        TextView tvTitle;
        public HomeViewHolder(View itemView) {
            super(itemView);
            mImg=$(itemView, R.id.iv_home_news_img);
            tvTitle=$(itemView, R.id.tv_home_news_title);
        }

        private <T extends View>T $(View view, @IdRes int resId){
            return (T) view.findViewById(resId);
        }
    }

}
