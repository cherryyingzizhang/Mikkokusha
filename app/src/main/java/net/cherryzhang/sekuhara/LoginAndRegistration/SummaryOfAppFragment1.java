package net.cherryzhang.sekuhara.LoginAndRegistration;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import net.cherryzhang.sekuhara.R;

/**
 * Created by geoffreyheath on 2014-09-08.
 */
public class SummaryOfAppFragment1 extends Fragment
{
    ShimmerTextView TV_text1, TV_text2, TV_text3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_section_summary_1, container, false);

        TV_text1 = (ShimmerTextView) rootView.findViewById(R.id.TV_text1);
        TV_text2 = (ShimmerTextView) rootView.findViewById(R.id.TV_text2);
        TV_text3 = (ShimmerTextView) rootView.findViewById(R.id.TV_text3);

        Shimmer shimmer = new Shimmer();
        shimmer.start(TV_text1);
        shimmer.start(TV_text2);
        shimmer.start(TV_text3);

        return rootView;
    }
}
