package fr.wetstein.mycv.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.chiralcode.colorpicker.ColorPickerDialog;

import fr.wetstein.mycv.R;
import fr.wetstein.mycv.util.WindowUtil;

public class DrawSettingsView extends LinearLayout implements View.OnClickListener {
    public static final String TAG = "DrawSettingsView";

    public interface IDrawSettings {
        public void setCurrentLineStyle(int lineStyle);
        public void setCurrentLineColor(int lineColor);
        public void setCurrentLineSize(int lineThickness);
    }

    private IDrawSettings mCallbacks;
    private Button btnStyle, btnSize, btnColor;

    public DrawSettingsView(Context context) {
        this(context, null, 0);
    }

    public DrawSettingsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawSettingsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        initialize(context);
    }

    private void initialize(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);

        View contentView = LayoutInflater.from(context).inflate(R.layout.view_draw_settings, this);

        btnStyle = (Button) contentView.findViewById(R.id.btn_draw_style);
        btnSize = (Button) contentView.findViewById(R.id.btn_draw_size);
        btnColor = (Button) contentView.findViewById(R.id.btn_draw_color);

        btnStyle.setOnClickListener(this);
        btnSize.setOnClickListener(this);
        btnColor.setOnClickListener(this);
    }

    public void setCallback(IDrawSettings iDrawSettings) {
        mCallbacks = iDrawSettings;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (mCallbacks != null) {
            if (id == R.id.btn_draw_style) {
                PopupMenu popup = new PopupMenu(getContext(), btnStyle);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_draw_style, popup.getMenu());

                WindowUtil.displayIconsInPopup(popup);

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        int itemId = item.getItemId();
                        Toast.makeText(getContext(), "You Clicked : " + itemId + " " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });

                popup.show();
                //setCurrentLineStyle();
            }
            else if (id == R.id.btn_draw_color) {
                int initialColor = Color.BLACK;
                ColorPickerDialog colorPickerDialog = new ColorPickerDialog(getContext(), initialColor, new ColorPickerDialog.OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int color) {
                        Toast.makeText(getContext(), "COLOR = "+color, Toast.LENGTH_LONG).show();
                        btnColor.se
                    }
                });
                colorPickerDialog.show();
            }
            else if (id == R.id.btn_draw_size) {

            }
        }
    }
}
