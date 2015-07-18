package fr.wetstein.mycv.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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

		((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_detail_news);
	}
	
	@Override
	protected void displayItem(News inItem) {
		if (null != inItem && this.isAdded()) {
			//Display item data
            textTitle.setText(inItem.title);

			if (inItem.getType() == News.Type.HTML) {
				textContent.setText(Html.fromHtml(inItem.content));
				textContent.setMovementMethod(LinkMovementMethod.getInstance());
			} else {
				textContent.setText(inItem.content);
			}

			List<String> tags = new ArrayList<>();
			for (String tag : inItem.listTag)
				tags.add("#"+ tag);
            textTags.setText(TextUtils.join(" ", tags));
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
