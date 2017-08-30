package com.praneethcorporation.collegeportal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by user on 8/29/2017.
 */

public class Tab1 extends Fragment {
Button b1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1, container, false);
        Context c = getActivity().getApplicationContext();

        b1 = (Button)view.findViewById(R.id.editButton1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),Tab1Form.class);
            startActivity(intent);
            }
        });

        return view;
    }
}
