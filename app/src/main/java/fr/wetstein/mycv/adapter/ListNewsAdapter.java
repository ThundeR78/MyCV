package fr.wetstein.mycv.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fr.wetstein.mycv.R;
import fr.wetstein.mycv.model.News;

/**
 * Created by ThundeR on 10/07/2014.
 */
public class ListNewsAdapter extends ArrayAdapter<News> {
    public static final String TAG = "ListNewsAdapter";

    private LayoutInflater mLayoutInflater;
    private int mItemResourceId;

    public ListNewsAdapter(Context context, int viewResourceId, List<News> listItem) {
        super(context, viewResourceId, listItem);

        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mItemResourceId = viewResourceId;
    }

    public View getView(int in_position, View convertView, ViewGroup in_parent) {
        ViewHolder holder = null;

        News item = getItem(in_position);
        if (item != null) {
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(mItemResourceId, null);
                holder = new ViewHolder();
                holder.title = (TextView) convertView.findViewById(R.id.list_news_item_title);
                holder.content = (TextView) convertView.findViewById(R.id.list_news_item_content);
                holder.tags = (TextView) convertView.findViewById(R.id.list_news_item_tags);
                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }
        }

        holder.title.setText(item.title);
        holder.content.setText(item.content);
        if (item.listTag != null && item.listTag.size()>0 && !item.listTag.get(0).equals("ynp_default_tag")) {
            String tags = TextUtils.join(", ", item.listTag);
            holder.tags.setText(tags);
        } else
            holder.tags.setVisibility(View.GONE);

        return convertView;
    }

    public static class ViewHolder {
        public TextView title;
        public TextView content;
        public TextView tags;
    }
}
