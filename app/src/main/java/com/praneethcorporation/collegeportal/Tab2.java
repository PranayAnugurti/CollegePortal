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

public class Tab2 extends Fragment {
Button ed;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2, container, false);
        Context c = getActivity().getApplicationContext();
   ed = (Button)view.findViewById(R.id.editButton2);
        ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),Tab2Form.class);
                startActivity(intent);
            }
        });
        return view;

    }
}
