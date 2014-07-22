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
import fr.wetstein.mycv.business.Experience;

public class ExperienceDetailFragment extends DetailFragment<Experience> implements OnClickListener {
	private static final String TAG = "ExperienceDetailFragment";

    private ImageView imgLogo;
	private TextView textName, textLink, textFunction, textAddress;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		

		Bundle args = getArguments();
		if (args != null) {

		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View contentView = inflater.inflate(R.layout.fragment_detail_experience, container, false);
		
		//Bind layout elements
        imgLogo = (ImageView) contentView.findViewById(R.id.detail_exp_logo);
        textName = (TextView) contentView.findViewById(R.id.detail_exp_name);
		textFunction = (TextView) contentView.findViewById(R.id.detail_exp_function);
		//textLink = (TextView) contentView.findViewById(R.id.text_email);
		//textAddress = (TextView) contentView.findViewById(R.id.text_phone);


		return contentView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		getActivity().getActionBar().setTitle(R.string.title_detail_experience);
	}
	
	@Override
	protected void displayItem(Experience inItem) {
		if (null != inItem && this.isAdded()) {
			//Display item data
            textName.setText(inItem.name);
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
