package net.cherryzhang.sekuhara.LoginAndRegistration;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cherry_zhang.androidbeaconexample.R;


/**
 * Created by geoffreyheath on 2014-09-08.
 */
public class SummaryOfAppFragment extends Fragment
{

    public static final String ARG_SECTION_NUMBER = "section_number";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_section_summary, container, false);
        Bundle args = getArguments();

        //the page number
        int tabNum = args.getInt(ARG_SECTION_NUMBER);
        //((TextView) rootView.findViewById(R.id.text1)).setText(
        //        getString(R.string.dummy_section_text, args.getInt(ARG_SECTION_NUMBER)));

        ((TextView) rootView.findViewById(R.id.TV_text1)).setText("Section" + String.valueOf(tabNum)
                + " information");

        ((TextView)rootView.findViewById(R.id.TV_text2)).setText("< swipe");

        ((ImageView) rootView.findViewById(R.id.IV_image)).setImageResource(R.drawable.kitty);

        return rootView;
    }
}
