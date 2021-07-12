package com.example.halperdnd.ui.spells.pages;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.halperdnd.R;


public class SpellsDescriptionFragment extends Fragment {

    TextView tVSpellName;
    TextView tVSpellDescription;
    TextView tVSpellDistance;
    TextView tVSpellDuration;
    TextView tVSpellTimeCast;
    TextView tVSpellComponent;
    TextView tVLevelSpell;
    TextView tVMComponent;
    public SpellsDescriptionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_spells_discription, container, false);

        tVSpellName = v.findViewById(R.id.tVSpellsName);
        tVSpellName.setText(getArguments().getString("getName"));

        tVSpellDistance = v.findViewById(R.id.tVDistance);
        tVSpellDistance.setText(getArguments().getString("getDistance"));

        tVSpellDuration = v.findViewById(R.id.tVDuration);
        tVSpellDuration.setText(getArguments().getString("getDuration"));

        tVSpellTimeCast = v.findViewById(R.id.tVTimeCast);
        tVSpellTimeCast.setText(getArguments().getString("getTimeCast"));

        tVSpellComponent = v.findViewById(R.id.tVComponent);
        tVSpellComponent.setText(getArguments().getString("getComponents"));


        tVLevelSpell = v.findViewById(R.id.tVLevelSpell);
        tVLevelSpell.setText(
                getArguments().getString("getStLevel")
                        +" , "+
                        getArguments().getString("getStSchool"));

        tVMComponent = v.findViewById(R.id.tVMComponent);
         if (getArguments().getString("getMatComponrnts") == null ||
                 getArguments().getString("getMatComponrnts")  =="") {
             tVMComponent.setVisibility(View.GONE);
         }else
             tVMComponent.setText(getArguments().getString("getMatComponrnts") );

        tVSpellDescription = v.findViewById(R.id.tVSpellDescription);
        tVSpellDescription.setText(getArguments().getString("getDescription") );

         return v;
    }


}
