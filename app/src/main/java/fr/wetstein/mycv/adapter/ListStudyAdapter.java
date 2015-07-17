package fr.wetstein.mycv.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fr.wetstein.mycv.R;
import fr.wetstein.mycv.model.Study;

/**
 * Created by ThundeR on 10/07/2014.
 */
public class ListStudyAdapter extends RecyclerView.Adapter<ListStudyAdapter.ViewHolder> {
    public static final String TAG = "ListStudyAdapter";

    private List<Study> mListItem;
    private OnItemClickListener mOnItemClickListener;

    public ListStudyAdapter(List<Study> listItem) {
        mListItem = listItem;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_study_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mListItem != null) {
            Study item = mListItem.get(position);

            if (item != null) {
                holder.name.setText(item.name);
                holder.option.setText(item.option);
                holder.date.setText(item.date);
                if (item.school != null)
                    holder.school.setText(item.school.name);
                else
                    holder.school.setVisibility(View.GONE);
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
        public TextView name;
        public TextView option;
        public TextView date;
        public TextView school;

        private View.OnClickListener mOnClickListener;

        public ViewHolder(View itemView) {
            super(itemView);

            if (itemView != null) {
                name = (TextView) itemView.findViewById(R.id.list_study_item_name);
                option = (TextView) itemView.findViewById(R.id.list_study_item_option);
                date = (TextView) itemView.findViewById(R.id.list_study_item_date);
                school = (TextView) itemView.findViewById(R.id.list_study_item_school);
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
