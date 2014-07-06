package fr.wetstein.mycv.adapter;

import android.app.Activity;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

import fr.wetstein.mycv.R;

public class NavDrawerAdapter extends BaseAdapter {

	// Declare Variables
	Activity context;
	int menuResource;
	LayoutInflater inflater;
	private ArrayList<SlideMenuItem> menuItemList;

	//Item for the Menu
	public static class SlideMenuItem {
		public int id;
		public Drawable icon;
		public String label;
		public int position;
	}
	
	//Item Holder for the Menu
	public static class MenuItemHolder {
		public ImageView icon;
		public TextView label;
	}

	public NavDrawerAdapter(Activity activity, int menuResource) {
		this.context = activity;
		this.menuResource = menuResource;
		
		//Parse menu xml
		parseXml(menuResource);
	}

	@Override
	public int getCount() {
		return menuItemList.size();
	}

	@Override
	public Object getItem(int position) {
		return menuItemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		
		if (rowView == null) {
			inflater = context.getLayoutInflater();
			
			rowView = inflater.inflate(R.layout.navdrawer_list_item, null);
			
			MenuItemHolder viewHolder = new MenuItemHolder();
			viewHolder.label = (TextView) rowView.findViewById(R.id.menu_label);
			viewHolder.icon = (ImageView) rowView.findViewById(R.id.menu_icon);
			rowView.setTag(viewHolder);
		}
		
		MenuItemHolder holder = (MenuItemHolder) rowView.getTag();
		holder.label.setText(menuItemList.get(position).label);
		holder.icon.setImageDrawable(menuItemList.get(position).icon);

		return rowView;
	}
	
	//Parse Menu XML resource 
	private void parseXml(int menu) {
		menuItemList = new ArrayList<SlideMenuItem>();

		try {
			XmlResourceParser xpp = context.getResources().getXml(menu);

			xpp.next();
			int eventType = xpp.getEventType();

			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG) {
					String elemName = xpp.getName();

					if (elemName.equals("item")) {
						String textId = xpp.getAttributeValue("http://schemas.android.com/apk/res/android", "title");
						String iconId = xpp.getAttributeValue("http://schemas.android.com/apk/res/android", "icon");
						String resId = xpp.getAttributeValue("http://schemas.android.com/apk/res/android", "id");
						String posId = xpp.getAttributeValue("http://schemas.android.com/apk/res/android", "orderInCategory");

						SlideMenuItem item = new SlideMenuItem();
						item.id = Integer.valueOf(resId.replace("@", ""));
						item.icon = context.getResources().getDrawable(Integer.valueOf(iconId.replace("@", "")));
						item.label = resourceIdToString(textId);
						item.position = Integer.valueOf(posId);

						menuItemList.add(item);
					}

				}
				eventType = xpp.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String resourceIdToString(String text) {
		if (!text.contains("@")) {
			return text;
		} else {
			String id = text.replace("@", "");
			return context.getResources().getString(Integer.valueOf(id));
		}
	}
}
