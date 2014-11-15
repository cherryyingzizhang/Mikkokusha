package net.cherryzhang.sekuhara.LoginAndRegistration;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.cherryzhang.sekuhara.R;

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

        ((TextView) rootView.findViewById(R.id.TV_text1)).setText("Section" + String.valueOf(tabNum)
                + " information");

        ((TextView)rootView.findViewById(R.id.TV_text2)).setText("< swipe");

        ((ImageView) rootView.findViewById(R.id.IV_image)).setImageResource(R.drawable.ic_launcher);

        return rootView;
    }
}
