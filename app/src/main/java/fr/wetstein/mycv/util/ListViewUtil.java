package fr.wetstein.mycv.util;

import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ListViewUtil {
	
	public static void setListViewHeightBasedOnChildren(ListView listView) {
	    ListAdapter listAdapter = listView.getAdapter();
	    if (listAdapter == null) {
	        return;
	    }
	    int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.AT_MOST);
	    int totalHeight = 0;
	    View view = null;
	    for (int i = 0; i < listAdapter.getCount(); i++) {
	        view = listAdapter.getView(i, view, listView);
	        if (i == 0) {
	            view.setLayoutParams(new LayoutParams(desiredWidth, LayoutParams.WRAP_CONTENT));
	        }
	        view.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
	        totalHeight += view.getMeasuredHeight();
	    }
	    LayoutParams params = listView.getLayoutParams();
	    params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
	    listView.setLayoutParams(params);
	    listView.requestLayout();
	}
	
	public static void setListViewRowColors(ListView listView, int defaultRowColor, int secondRowColor) {
		ListAdapter listAdapter = listView.getAdapter();
		View view = null;
		for (int indexRow = 0; indexRow < listAdapter.getCount() ;indexRow++) {
			view = listAdapter.getView(indexRow, view, listView);
			if (view != null) {
				view.setBackgroundResource((indexRow%2 != 0) ? defaultRowColor : secondRowColor);
			}
		}
		listView.requestLayout();
	}
}
