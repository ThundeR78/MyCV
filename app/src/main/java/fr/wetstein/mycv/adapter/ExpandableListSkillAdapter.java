package fr.wetstein.mycv.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.BaseExpandableListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import fr.wetstein.mycv.R;
import fr.wetstein.mycv.business.GroupSkill;
import fr.wetstein.mycv.business.Skill;
import fr.wetstein.mycv.fragment.SkillsFragment;

/**
 * Created by ThundeR on 10/07/2014.
 */
public class ExpandableListSkillAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<GroupSkill> mListDataHeader;
    private HashMap<GroupSkill, List<Skill>> mListDataChild;

    public ExpandableListSkillAdapter(Context context, List<GroupSkill> listDataHeader, HashMap<GroupSkill, List<Skill>> listChildData) {
        mContext = context;
        mListDataHeader = listDataHeader;
        mListDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return mListDataChild.get(mListDataHeader.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Skill skill = (Skill) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_skill_item, null);
        }

        TextView labelChild = (TextView) convertView.findViewById(R.id.list_skill_item_label);
        ProgressBar progressChild = (ProgressBar) convertView.findViewById(R.id.list_skill_item_progress);

        labelChild.setText(skill.label);

        //Animate ProgressBar
        if (progressChild.getProgress() != skill.rate) {
            progressChild.setProgress(0);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                //Will update the "progress" propriety of ProgressBar until it reaches progress
                ObjectAnimator animation = ObjectAnimator.ofInt(progressChild, "progress", skill.rate);
                animation.setDuration(SkillsFragment.PROGRESSBAR_ANIMATION_TIME);
                animation.setInterpolator(new AccelerateDecelerateInterpolator());
                animation.start();
            } else
                progressChild.setProgress(skill.rate);
        }
        //Colorize ProgressBar
        if (skill.color != null) {
            int colorId = mContext.getResources().getIdentifier(skill.color, "color", mContext.getPackageName());
            progressChild.getProgressDrawable().setColorFilter(mContext.getResources().getColor(colorId), PorterDuff.Mode.SRC_IN);
        } else
            progressChild.getProgressDrawable().setColorFilter(mContext.getResources().getColor(R.color.violet_dark), PorterDuff.Mode.SRC_IN);

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mListDataChild.get(mListDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mListDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return mListDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupSkill group = (GroupSkill) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_skill_group, null);
        }

        if (group != null) {
            int backgroundColorId = mContext.getResources().getIdentifier(group.color, "color", mContext.getPackageName());
            convertView.setBackgroundColor(mContext.getResources().getColor(backgroundColorId));

            TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setText(group.label);
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
