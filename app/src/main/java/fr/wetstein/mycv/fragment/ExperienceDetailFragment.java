package fr.wetstein.mycv.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import fr.wetstein.mycv.model.Experience;

public class ExperienceDetailFragment extends DetailFragment<Experience> implements OnClickListener {
	private static final String TAG = "ExperienceDetailFragment";

    private ImageView imgLogo;
    private TextView textType, textAddress, textFunction;   //Experience
	private TextView textName, textDesc, textWebsite, textPhone, textEmail, textActivity, textCeo, textNbEmployees, textHeadOffice; //Company

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
        textEmail = (TextView) contentView.findViewById(R.id.detail_exp_email);
        textPhone = (TextView) contentView.findViewById(R.id.detail_exp_phone);
        textDesc = (TextView) contentView.findViewById(R.id.detail_exp_desc);
		textFunction = (TextView) contentView.findViewById(R.id.detail_exp_function);
		textWebsite = (TextView) contentView.findViewById(R.id.detail_exp_link);
		textAddress = (TextView) contentView.findViewById(R.id.detail_exp_address);
        textType = (TextView) contentView.findViewById(R.id.detail_exp_type);
        textActivity = (TextView) contentView.findViewById(R.id.detail_exp_activity);
        textHeadOffice = (TextView) contentView.findViewById(R.id.detail_exp_headoffice);
        textNbEmployees = (TextView) contentView.findViewById(R.id.detail_exp_nbemployees);
        textCeo = (TextView) contentView.findViewById(R.id.detail_exp_ceo);

		return contentView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_detail_experience);
	}
	
	@Override
	protected void displayItem(Experience inItem) {
		if (inItem != null && this.isAdded()) {
			//Display item data
            textName.setText(inItem.name);
            textFunction.setText(inItem.function);
            textAddress.setText(inItem.address);

            //Display company data
            if (inItem.company != null) {
                imgLogo.setImageResource(inItem.company.logo);
                textActivity.setText(inItem.company.activity);
                textHeadOffice.setText(inItem.company.address);
                textWebsite.setText(inItem.company.website);
                textPhone.setText(inItem.company.phone);
                textEmail.setText(inItem.company.email);
                textCeo.setText(inItem.company.ceo);
                textNbEmployees.setText(getString(R.string.value_employees, inItem.company.nbEmployees));
                textDesc.setText(inItem.company.desc);
            }
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
