package fr.wetstein.mycv.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import fr.wetstein.mycv.R;
import fr.wetstein.mycv.model.School;

public class SchoolDetailFragment extends DetailFragment<School> implements OnClickListener {
	private static final String TAG = "SchoolDetailFragment";

    private ImageView imgLogo;
	private TextView textName, textLink, textFunction, textAddress;
    private TextView textType;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle args = getArguments();
		if (args != null) {

		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View contentView = inflater.inflate(R.layout.fragment_detail_study, container, false);
		
		//Bind layout elements
        textName = (TextView) contentView.findViewById(R.id.detail_name);
		textFunction = (TextView) contentView.findViewById(R.id.detail_option);
        textType = (TextView) contentView.findViewById(R.id.detail_dates);

		return contentView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		getActivity().getActionBar().setTitle(R.string.title_detail_study);
	}
	
	@Override
	protected void displayItem(School inItem) {
		if (null != inItem && this.isAdded()) {
			//Display item data
            textName.setText(inItem.name);
//            textFunction.setText(inItem.option);
//            textLink.setText(inItem.date);
            //textAddress.setText(inItem.listCertif);
		}
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		
		//inflater.inflate(R.menu.detail_company, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {

	}
}
