package fr.wetstein.mycv.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fr.wetstein.mycv.R;
import fr.wetstein.mycv.model.News;

/**
 * Created by ThundeR on 10/07/2014.
 */
public class ListNewsAdapter extends RecyclerView.Adapter<ListNewsAdapter.ViewHolder> {
    public static final String TAG = "ListNewsAdapter";

    private List<News> mListItem;
    private OnItemClickListener mOnItemClickListener;

    public ListNewsAdapter(List<News> listItem) {
        mListItem = listItem;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_news_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        News item = mListItem.get(position);

        holder.mTitle.setText(item.title);
        holder.mContent.setText(item.content);
        if (item.listTag != null && item.listTag.size()>0 && !item.listTag.get(0).equals("ynp_default_tag")) {
            String tags = TextUtils.join(", ", item.listTag);
            holder.mTags.setText(tags);
        } else
            holder.mTags.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        public TextView mTitle;
        public TextView mContent;
        public TextView mTags;

        private View.OnClickListener mOnClickListener;

        public ViewHolder(View itemView) {
            super(itemView);

            if (itemView != null) {
                mTitle = (TextView) itemView.findViewById(R.id.list_news_item_title);
                mContent = (TextView) itemView.findViewById(R.id.list_news_item_content);
                mTags = (TextView) itemView.findViewById(R.id.list_news_item_tags);
            }
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, getLayoutPosition());
            }
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view , int position);
    }
}
