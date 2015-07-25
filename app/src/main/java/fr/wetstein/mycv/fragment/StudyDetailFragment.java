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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import fr.wetstein.mycv.R;
import fr.wetstein.mycv.model.Study;
import fr.wetstein.mycv.util.ListViewUtil;
import fr.wetstein.mycv.view.SchoolView;

public class StudyDetailFragment extends DetailFragment<Study> implements OnClickListener {
	private static final String TAG = "StudyDetailFragment";

    private ImageView imgLogo;
	private TextView textName, textDates, textOption;
	private ListView listViewCertifs;
	private SchoolView viewSchool;

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
		textOption = (TextView) contentView.findViewById(R.id.detail_option);
        textDates = (TextView) contentView.findViewById(R.id.detail_dates);
		listViewCertifs = (ListView) contentView.findViewById(R.id.listview_certifs);
		viewSchool = (SchoolView) contentView.findViewById(R.id.detail_school);

		return contentView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_detail_study);
	}
	
	@Override
	protected void displayItem(Study inItem) {
		if (null != inItem && this.isAdded()) {
			textName.setText(inItem.name);
			textOption.setText(inItem.option);
			textDates.setText(inItem.date);

			//Certifications
			if (listViewCertifs != null && inItem.listCertif != null && !inItem.listCertif.isEmpty()) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, inItem.listCertif);
				listViewCertifs.setAdapter(adapter);
                ListViewUtil.setListViewHeightBasedOnChildren(listViewCertifs);
			}

			//School
			if (inItem.school != null) {
				viewSchool.displayItem(inItem.school);
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
