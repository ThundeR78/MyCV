package fr.wetstein.mycv.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fr.wetstein.mycv.R;
import fr.wetstein.mycv.model.Experience;
import fr.wetstein.mycv.util.FormatValue;

/**
 * Created by ThundeR on 10/07/2014.
 */
public class ListExperienceAdapter extends RecyclerView.Adapter<ListExperienceAdapter.ViewHolder> {
    public static final String TAG = "ListExperienceAdapter";

    private List<Experience> mListItem;
    private OnItemClickListener mOnItemClickListener;
    private Context mContext;

    public ListExperienceAdapter(List<Experience> listItem) {
        mListItem = listItem;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_experience_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mListItem != null) {
            Experience item = mListItem.get(position);

            if (item != null) {
                //Dates
                String strDateBegin = FormatValue.monthDateFormat.format(item.dateBegin);
                String strDateEnd = (item.dateEnd != null) ? FormatValue.monthDateFormat.format(item.dateEnd) : mContext.getString(R.string.word_today);
                String expDuration = mContext.getString(R.string.value_dates, strDateBegin, strDateEnd);

                if (item.company != null) {
                    holder.logo.setImageResource(item.company.logo);
                    holder.name.setText(item.company.name);
                }
                holder.date.setText(expDuration);
                holder.type.setText(item.type);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    //View Holder
    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        public ImageView logo;
        public TextView name;
        public TextView date;
        public TextView type;

        private View.OnClickListener mOnClickListener;

        public ViewHolder(View itemView) {
            super(itemView);

            if (itemView != null) {
                logo = (ImageView) itemView.findViewById(R.id.list_exp_item_logo);
                name = (TextView) itemView.findViewById(R.id.list_exp_item_name);
                date = (TextView) itemView.findViewById(R.id.list_exp_item_date);
                type = (TextView) itemView.findViewById(R.id.list_exp_item_type);
                itemView.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, getLayoutPosition());
            }
        }
    }

    //Click Listener
    public interface OnItemClickListener {
        public void onItemClick(View view , int position);
    }
}
