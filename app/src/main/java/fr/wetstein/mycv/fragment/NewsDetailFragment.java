package fr.wetstein.mycv.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.wetstein.mycv.R;
import fr.wetstein.mycv.model.News;

public class NewsDetailFragment extends DetailFragment<News> implements OnClickListener {
	private static final String TAG = "NewsDetailFragment";

	private TextView textTitle, textTags, textContent;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		

		Bundle args = getArguments();
		if (args != null) {

		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View contentView = inflater.inflate(R.layout.fragment_detail_news, container, false);
		
		//Bind layout elements
        textTitle = (TextView) contentView.findViewById(R.id.detail_title);
		textContent = (TextView) contentView.findViewById(R.id.detail_content);
        textTags = (TextView) contentView.findViewById(R.id.detail_tags);

		return contentView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		getActivity().getActionBar().setTitle(R.string.title_detail_news);
	}
	
	@Override
	protected void displayItem(News inItem) {
		if (null != inItem && this.isAdded()) {
			//Display item data
            textTitle.setText(inItem.title);
            textContent.setText(inItem.content);
            textTags.setText(TextUtils.join(", ", inItem.listTag));
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
