package fr.wetstein.mycv.util;

import android.app.Activity;
import android.app.Dialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupMenu;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by ThundeR on 28/10/14.
 */
public class WindowUtil {
    public static final String TAG = "WindowUtil";

    public static void displayIconsInPopup(PopupMenu popup) {
        try {
            Field[] fields = popup.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popup);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void resizeDialog(Activity activity, Dialog dialog) {
        if (dialog != null) {
            int maxWidth = 1000;
            DisplayMetrics metrics = activity.getApplication().getResources().getDisplayMetrics();
            maxWidth = (int) (maxWidth / (metrics.densityDpi / 160f));

            Window window = dialog.getWindow();
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(window.getAttributes());
            //Check max width px with screen width px
            Log.v(TAG, "SCREEN = " + metrics.widthPixels + " - MAX = " + maxWidth);
            lp.width = (maxWidth <= 0 || ((maxWidth>0) && metrics.widthPixels <= maxWidth)) ? ViewGroup.LayoutParams.MATCH_PARENT : maxWidth;
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);
        }
    }
}
