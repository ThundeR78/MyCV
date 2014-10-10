package fr.wetstein.mycv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fr.wetstein.mycv.R;
import fr.wetstein.mycv.model.Experience;
import fr.wetstein.mycv.util.FormatValue;

/**
 * Created by ThundeR on 10/07/2014.
 */
public class ListExperienceAdapter extends ArrayAdapter<Experience> {
    public static final String TAG = "ListExperienceAdapter";

    private LayoutInflater mLayoutInflater;
    private int mItemResourceId;

    public ListExperienceAdapter(Context context, int viewResourceId, List<Experience> listExperience) {
        super(context, viewResourceId, listExperience);

        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mItemResourceId = viewResourceId;
    }

    public View getView(int in_position, View convertView, ViewGroup in_parent) {
        ViewHolder holder = null;

        Experience item = getItem(in_position);
        if (item != null) {
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(mItemResourceId, null);
                holder = new ViewHolder();
                holder.logo = (ImageView) convertView.findViewById(R.id.list_exp_item_logo);
                holder.name = (TextView) convertView.findViewById(R.id.list_exp_item_name);
                holder.date = (TextView) convertView.findViewById(R.id.list_exp_item_date);
                holder.type = (TextView) convertView.findViewById(R.id.list_exp_item_type);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
        }

        String strDateBegin = FormatValue.monthDateFormat.format(item.dateBegin);
        String strDateEnd = (item.dateEnd != null) ? FormatValue.monthDateFormat.format(item.dateEnd) : getContext().getString(R.string.word_today);
        String expDuration = getContext().getString(R.string.value_dates, strDateBegin, strDateEnd);

        //holder.logo.setImageResource(item.logo);
        holder.name.setText(item.name);
        holder.date.setText(expDuration);
        holder.type.setText(item.type);

        return convertView;
    }

    public static class ViewHolder {
        public ImageView logo;
        public TextView name;
        public TextView date;
        public TextView type;
    }
}
