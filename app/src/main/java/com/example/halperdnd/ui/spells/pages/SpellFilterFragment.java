package com.example.halperdnd.ui.spells.pages;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.halperdnd.R;
import com.example.halperdnd.database.DataBaseHelper;
import com.example.halperdnd.database.dao.spells.DataSpellsImpl;
import com.example.halperdnd.ui.spells.SpellFilterViewModel;
import com.example.halperdnd.ui.spells.filters.OptionFilterSpell;
import com.example.halperdnd.view.CheckButtonBig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SpellFilterFragment extends Fragment implements View.OnClickListener{
    private NavController navController;

    private ArrayList<CheckButtonBig> arrayCheckButtonBigLevelSpell;
    private ArrayList<CheckButtonBig> arrayCheckButtonBigSourceSpell;
    private ArrayList<CheckButtonBig> arrayCheckButtonBigSchoolSpell;

    private int classChoose =0;

    private Button btnReset;
    private Button btnAccept;

    DataSpellsImpl dataSpellsImpl;

    private PopupMenu popupMenuClass;

    private TextView tVClass;


    public SpellFilterFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBaseHelper  dataBaseHelper;
        /**Open db*/
        try {
              dataBaseHelper = new DataBaseHelper(getContext(),"spellsTable.db");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dataSpellsImpl = dataBaseHelper.getDataSpellsResDao();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_spell_filter, container, false);

        List<String> schoolList = dataSpellsImpl.getSchoolNames();
        List<String> sourceList = dataSpellsImpl.getSourceNames();
        List<String>  classList  = dataSpellsImpl.getClassNames();

        navController = NavHostFragment.findNavController(this);
        NavBackStackEntry backStackEntry = navController.getBackStackEntry(R.id.nav_spells);
        /**Spell level List initilization*/
        arrayCheckButtonBigLevelSpell = new ArrayList<>();
        arrayCheckButtonBigLevelSpell.add((CheckButtonBig) root.findViewById(R.id.chBoxLevel0));
        arrayCheckButtonBigLevelSpell.add((CheckButtonBig) root.findViewById(R.id.chBoxLevel1));
        arrayCheckButtonBigLevelSpell.add((CheckButtonBig) root.findViewById(R.id.chBoxLevel2));
        arrayCheckButtonBigLevelSpell.add((CheckButtonBig) root.findViewById(R.id.chBoxLevel3));
        arrayCheckButtonBigLevelSpell.add((CheckButtonBig) root.findViewById(R.id.chBoxLevel4));
        arrayCheckButtonBigLevelSpell.add((CheckButtonBig) root.findViewById(R.id.chBoxLevel5));
        arrayCheckButtonBigLevelSpell.add((CheckButtonBig) root.findViewById(R.id.chBoxLevel6));
        arrayCheckButtonBigLevelSpell.add((CheckButtonBig) root.findViewById(R.id.chBoxLevel7));
        arrayCheckButtonBigLevelSpell.add((CheckButtonBig) root.findViewById(R.id.chBoxLevel8));
        arrayCheckButtonBigLevelSpell.add((CheckButtonBig) root.findViewById(R.id.chBoxLevel9));
        /**insert CliclLissener*/
        for(CheckButtonBig i : arrayCheckButtonBigLevelSpell) { i.setOnClickListener(this); }

        arrayCheckButtonBigSchoolSpell = new ArrayList<>();
        arrayCheckButtonBigSchoolSpell.add((CheckButtonBig) root.findViewById(R.id.chBoxSchool1));
        arrayCheckButtonBigSchoolSpell.add((CheckButtonBig) root.findViewById(R.id.chBoxSchool2));
        arrayCheckButtonBigSchoolSpell.add((CheckButtonBig) root.findViewById(R.id.chBoxSchool3));
        arrayCheckButtonBigSchoolSpell.add((CheckButtonBig) root.findViewById(R.id.chBoxSchool4));
        arrayCheckButtonBigSchoolSpell.add((CheckButtonBig) root.findViewById(R.id.chBoxSchool5));
        arrayCheckButtonBigSchoolSpell.add((CheckButtonBig) root.findViewById(R.id.chBoxSchool6));
        arrayCheckButtonBigSchoolSpell.add((CheckButtonBig) root.findViewById(R.id.chBoxSchool7));
        arrayCheckButtonBigSchoolSpell.add((CheckButtonBig) root.findViewById(R.id.chBoxSchool8));
        int id =0;
        for(int i=0 ;i<arrayCheckButtonBigSchoolSpell.size();i++) {
            arrayCheckButtonBigSchoolSpell.get(i).setOnClickListener(this);
            arrayCheckButtonBigSchoolSpell.get(i).setText(schoolList.get(i)); id++;}
        /**class*/
        tVClass = root.findViewById(R.id.tVClass);
        tVClass.setText("Не выбран");
        tVClass.setOnClickListener(this);

        id = 1;
        popupMenuClass = new PopupMenu(getContext(), tVClass, Gravity.CENTER);
        popupMenuClass.getMenu().add(0,0,0,"Не выбран");

        for(String i : classList) {
            popupMenuClass.getMenu().add(id,id,id,i);
            id++;}
        /**SourceBooks*/
        arrayCheckButtonBigSourceSpell = new ArrayList<>();
        arrayCheckButtonBigSourceSpell.add((CheckButtonBig) root.findViewById(R.id.chBoxSource1));
        arrayCheckButtonBigSourceSpell.add((CheckButtonBig) root.findViewById(R.id.chBoxSource2));
        arrayCheckButtonBigSourceSpell.add((CheckButtonBig) root.findViewById(R.id.chBoxSource3));
        arrayCheckButtonBigSourceSpell.add((CheckButtonBig) root.findViewById(R.id.chBoxSource4));
        arrayCheckButtonBigSourceSpell.add((CheckButtonBig) root.findViewById(R.id.chBoxSource5));
        arrayCheckButtonBigSourceSpell.add((CheckButtonBig) root.findViewById(R.id.chBoxSource6));
       // arrayCheckButtonBigSourceSpell.add((CheckButtonBig) root.findViewById(R.id.chBoxSource7));

        for(int i=0 ;i<sourceList.size();i++) {
            arrayCheckButtonBigSourceSpell.get(i).setOnClickListener(this);
            arrayCheckButtonBigSourceSpell.get(i).setText(sourceList.get(i));}

        btnAccept = root.findViewById(R.id.bttnAccept);
        btnAccept.setOnClickListener(this);
        btnReset = root.findViewById(R.id.bttnReset);
        btnReset.setOnClickListener(this);
        // Inflate the layout for this fragment
        /**this class subscribe to get saveValue ViewModel */
        SpellFilterViewModel spellFilterViewModel =
                new ViewModelProvider(requireActivity()).get(SpellFilterViewModel.class);
        spellFilterViewModel.getSelected().observe(getViewLifecycleOwner(),
                new Observer<OptionFilterSpell>() {
            @Override
            public void onChanged(OptionFilterSpell optionFilterSpell) {
                int id=0;
                for(CheckButtonBig i : arrayCheckButtonBigLevelSpell) {
                    i.setChecked(optionFilterSpell.getSpellLevel().get(id));
                    id++;
                }
                id=0;
                for(CheckButtonBig i : arrayCheckButtonBigSchoolSpell) {
                    i.setChecked(optionFilterSpell.getSpellSchool().get(id));
                    id++;
                }
                id=0;
                for(CheckButtonBig i : arrayCheckButtonBigSourceSpell) {
                    i.setChecked(optionFilterSpell.getSpellSource().get(id));
                    id++;
                }
            }
        });

        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /**Button accept*/
            case R.id.bttnAccept:

                List<Boolean> spellLevel = new ArrayList<>();
                List<Boolean> spellShool = new ArrayList<>();;
                List<Boolean> spellSource = new ArrayList<>();;
                for(CheckButtonBig i : arrayCheckButtonBigLevelSpell) {
                    spellLevel.add(i.isChecked());
                }
                for(CheckButtonBig i : arrayCheckButtonBigSchoolSpell) {
                    spellShool.add(i.isChecked());
                }
                for(CheckButtonBig i : arrayCheckButtonBigSourceSpell) {
                    spellSource.add(i.isChecked());
                }
                OptionFilterSpell a =
                        new OptionFilterSpell(spellLevel,spellShool,spellSource,classChoose);

                /**Save Values in ViewModel*/
                SpellFilterViewModel spellFilterViewModel =
                        new ViewModelProvider(requireActivity()).get(SpellFilterViewModel.class);
                /**ViewModel select*/
                spellFilterViewModel.select(a);

                navController.popBackStack();
                break;
            case R.id.bttnReset:
                /**Do standart option*/
                for(CheckButtonBig i : arrayCheckButtonBigLevelSpell)
                    i.setChecked(false);

                for(CheckButtonBig i :arrayCheckButtonBigSourceSpell)
                    i.setChecked(false);

                for(CheckButtonBig i :arrayCheckButtonBigSchoolSpell)
                    i.setChecked(false);

                tVClass.setText(popupMenuClass.getMenu().getItem(0).getTitle());
                classChoose = 0;
                break;

            case R.id.tVClass:
                popupMenuClass.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return menuItemClicked(item);
                    }
                });
                popupMenuClass.show();
                break;

        }
    }

    private boolean menuItemClicked(MenuItem item) {

        for(int i=0 ; i < popupMenuClass.getMenu().size();i++) {

            if (item.getItemId() == i){
                tVClass.setText(popupMenuClass.getMenu().getItem(i).getTitle());
                classChoose = i;
                break;
            }
        }
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        onDestroy();
    }
}
