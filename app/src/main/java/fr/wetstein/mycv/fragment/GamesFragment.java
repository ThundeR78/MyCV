package fr.wetstein.mycv.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.List;

import fr.wetstein.mycv.R;
import fr.wetstein.mycv.adapter.GridConsoleAdapter;
import fr.wetstein.mycv.business.Console;
import fr.wetstein.mycv.parser.GameParser;
import fr.wetstein.mycv.view.ExpandableHeightGridView;

/**
 * Created by ThundeR on 12/07/2014.
 */
public class GamesFragment extends Fragment implements AdapterView.OnItemClickListener {
    public static final String TAG = "GamesFragment";

    private ExpandableHeightGridView gridView;

    private List<Console> listConsole;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listConsole = GameParser.loadConsoles(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_games, container, false);

        gridView = (ExpandableHeightGridView) rootView.findViewById(R.id.gridview_console);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

       initGridView(listConsole);
    }

    private void initGridView(List<Console> listItems) {
        GridConsoleAdapter gridAdapter = new GridConsoleAdapter(getActivity(), R.layout.gridview_console_item, listItems);
        gridView.setAdapter(gridAdapter);
        gridView.setExpanded(true);
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Console console = (Console) parent.getItemAtPosition(position);

        if (console != null) {
            int resIconId = getResources().getIdentifier(console.icon, "drawable", getActivity().getPackageName());

            //Display Dialog with Games
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(console.shortName)
                .setIcon(resIconId)
                .setCancelable(true)
                .setItems(console.listGame.toArray(new CharSequence[console.listGame.size()]), null);

            Dialog dialog = builder.create();
            dialog.show();
        }
    }
}
