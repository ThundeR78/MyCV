package fr.wetstein.mycv.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import fr.wetstein.mycv.R;
import fr.wetstein.mycv.model.School;

public class SchoolView extends LinearLayout {
	private static final String TAG = "SchoolView";

    private ImageView imgLogo;
	private TextView textName, textLink;

	public SchoolView(Context context) {
        this(context, null, 0);
	}
    public SchoolView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public SchoolView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(Context context) {
        setOrientation(LinearLayout.VERTICAL);

        View contentView = LayoutInflater.from(context).inflate(R.layout.view_school, this);

        bindView(contentView);
    }

    //Bind layout elements
    private void bindView(View rootView) {
		textName = (TextView) rootView.findViewById(R.id.detail_name);
		textLink = (TextView) rootView.findViewById(R.id.detail_link);
	}

    //Display item data
	public void displayItem(School inItem) {
		if (null != inItem) {
            textName.setText(inItem.name);
            textLink.setText(inItem.link);
            //TODO link
		}
	}
}
