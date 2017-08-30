package com.praneethcorporation.collegeportal;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by user on 8/29/2017.
 */

public class Tab4 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab4, container, false);
        Context c = getActivity().getApplicationContext();
        return view;
    }
}
