package fr.wetstein.mycv.parser.xml;

import android.content.Context;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import fr.wetstein.mycv.model.Place;
import fr.wetstein.mycv.model.Travel;
import fr.wetstein.mycv.parser.ParserAssets;

/**
 * Created by ThundeR on 18/07/2014.
 */
public class TravelParser extends ParserAssets {
    public static final String TAG = "TravelParser";

    private static final String file = "travels.xml";

    private static Context mContext;

    //Load Travels
    public static List<Travel> loadTravels(Context context) {
        mContext = context;
        List<Travel> listItem = null;
        Travel item = null;

        XmlPullParser xpp = Xml.newPullParser();
        InputStream is = null;

        try {
            is = loadInputStreamFromAsset(context, pathData + file);
            xpp.setInput(is, null);

            //Parse XML
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tag = xpp.getName();

                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        listItem = new ArrayList<Travel>();
                        break;

                    case XmlPullParser.START_TAG:
                        if (tag.equals("travel")) {
                            item = readTravel(xpp);
                            listItem.add(item);
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if (tag.equals("travels") && item != null) {
//                            listItem.add(item);
                        }
                        break;
                }
                eventType = xpp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return listItem;
    }

    //Read XML Travel
    private static Travel readTravel(XmlPullParser xpp) throws IOException, XmlPullParserException {
        Travel item = new Travel();
        List<Place> listPlace = new ArrayList<Place>();
        float pin = 0;
        int color = 0;

        while (xpp.next() != XmlPullParser.END_TAG) {
            if (xpp.getEventType() != XmlPullParser.START_TAG)
                continue;

            String tag = xpp.getName();

            //Log.v("PARSE TRAVEL", tag);
            if (item != null) {
                if (tag.equals("country")) {
                    item.country = xpp.nextText();
                } else if (tag.equals("context")) {
                    item.context = xpp.nextText();
                /*} else if (tag.equals("dateBegin")) {
                    String dateBStr = xpp.nextText();
                    try {
                        if (dateBStr != null)
                            item.dateBegin = FormatValue.dateFormat.parse(dateBStr);
                    } catch (ParseException e) { e.printStackTrace(); }
                } else if (tag.equals("dateEnd")) {
                    String dateEStr = xpp.nextText();
                    try {
                        if (dateEStr != null)
                            item.dateEnd = FormatValue.dateFormat.parse(dateEStr);
                    } catch (ParseException e) { e.printStackTrace(); }*/
                } else if (tag.equals("date")) {
                    String dateBegin = xpp.getAttributeValue(null, "begin");
                    String dateEnd = xpp.getAttributeValue(null, "end");

                    xpp.nextTag();
                } else if (tag.equals("display")) {
                    String resPin = xpp.getAttributeValue(null, "pin");
                    String resColorName = xpp.getAttributeValue(null, "color");

                    if (resPin != null && !resPin.isEmpty())
                        pin = Float.parseFloat(resPin);
                    if (resColorName != null && !resColorName.isEmpty() && mContext != null)
                        color = mContext.getResources().getIdentifier(resColorName, "color", mContext.getPackageName());

                    xpp.nextTag();
                } else if (tag.equals("places")) {
                    //List Place
                    while (xpp.next() != XmlPullParser.END_TAG) {
                        if (xpp.getEventType() != XmlPullParser.START_TAG)
                            continue;

                        tag = xpp.getName();

                        if (tag.equals("place")) {
                            Place place = readPlace(xpp);
                            place.color = color;
                            place.pin = pin;

                            if (place != null)
                                listPlace.add(place);
                        }
                    }

                    item.listPlace = listPlace;

//                    Log.v("PARSE PLACES", listPlace.size()+"");
                } else
                    skip(xpp);
            }
        }

        return item;
    }


    //Read XML Place
    private static Place readPlace(XmlPullParser xpp) throws IOException, XmlPullParserException {
        Place item = new Place();

        String idStr = xpp.getAttributeValue(null, "id");
        if (idStr != null)
            item.id = Integer.parseInt(idStr);

        while (xpp.next() != XmlPullParser.END_TAG) {
            if (xpp.getEventType() != XmlPullParser.START_TAG)
                continue;

            String tag = xpp.getName();

            if (item != null) {
                if (tag.equals("city")) {
                    item.name = xpp.nextText();
                } else if (tag.equals("address")) {
                    item.address = xpp.nextText();
                } else if (tag.equals("position")) {
                    String latStr = xpp.getAttributeValue(null, "latitude");
                    String lngStr = xpp.getAttributeValue(null, "longitude");

                    if (latStr != null && !latStr.isEmpty())
                        item.latitude = Double.parseDouble(latStr);
                    if (lngStr != null && !lngStr.isEmpty())
                        item.longitude = Double.parseDouble(lngStr);

                    xpp.nextTag();
                } else
                    skip(xpp);
            }
        }

        return item;
    }

    private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
