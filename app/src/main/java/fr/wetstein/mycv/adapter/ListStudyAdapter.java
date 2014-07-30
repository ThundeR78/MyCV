package fr.wetstein.mycv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fr.wetstein.mycv.R;
import fr.wetstein.mycv.model.Study;

/**
 * Created by ThundeR on 10/07/2014.
 */
public class ListStudyAdapter extends ArrayAdapter<Study> {
    public static final String TAG = "ListStudyAdapter";

    private LayoutInflater mLayoutInflater;
    private int mItemResourceId;

    public ListStudyAdapter(Context context, int viewResourceId, List<Study> listItem) {
        super(context, viewResourceId, listItem);

        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mItemResourceId = viewResourceId;
    }

    public View getView(int in_position, View convertView, ViewGroup in_parent) {
        ViewHolder holder = null;

        Study item = getItem(in_position);
        if (item != null) {
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(mItemResourceId, null);
                holder = new ViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.list_study_item_name);
                holder.option = (TextView) convertView.findViewById(R.id.list_study_item_option);
                holder.date = (TextView) convertView.findViewById(R.id.list_study_item_date);
                holder.school = (TextView) convertView.findViewById(R.id.list_study_item_school);
                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }
        }

        holder.name.setText(item.name);
        holder.option.setText(item.option);
        holder.date.setText(item.date);
        if (item.school != null)
            holder.school.setText(item.school.name);
        else
            holder.school.setVisibility(View.GONE);

        return convertView;
    }

    public static class ViewHolder {
        public TextView name;
        public TextView option;
        public TextView date;
        public TextView school;
    }
}
