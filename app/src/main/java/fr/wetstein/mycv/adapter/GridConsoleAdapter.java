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
import fr.wetstein.mycv.business.Console;

/**
 * Created by ThundeR on 19/07/2014.
 */
public class GridConsoleAdapter extends ArrayAdapter<Console> {
    public static final String TAG = "GridConsoleAdapter";

    private LayoutInflater mLayoutInflater;
    private int mItemResourceId;

    public GridConsoleAdapter(Context context, int viewResourceId, List<Console> listItem) {
        super(context, viewResourceId, listItem);

        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mItemResourceId = viewResourceId;
    }

    public static class ViewHolder {
        public ImageView image;
        public TextView title;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Console item = getItem(position);

        if (item != null) {
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(mItemResourceId, null);

                holder = new ViewHolder();
                holder.title = (TextView) convertView.findViewById(R.id.grid_item_label);
                holder.image = (ImageView) convertView.findViewById(R.id.grid_item_image);

                convertView.setTag(holder);
            } else
                holder = (ViewHolder) convertView.getTag();

            holder.title.setText(item.name);
            if (item.logo != null) {
                int logoId = getContext().getResources().getIdentifier(item.logo, "drawable", getContext().getPackageName());
                holder.image.setImageResource(logoId);
            } else
                holder.image.setVisibility(View.GONE);
        }

        return convertView;
    }

}
