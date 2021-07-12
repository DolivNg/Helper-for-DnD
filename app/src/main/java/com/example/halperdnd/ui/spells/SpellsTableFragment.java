package com.example.halperdnd.ui.spells;

import android.app.Activity;
import android.content.ClipData;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.halperdnd.R;
import com.example.halperdnd.adapter.AdapterSpellsTable;
import com.example.halperdnd.database.DataBaseHelper;
import com.example.halperdnd.database.dao.spells.DataSpellsImpl;
import com.example.halperdnd.database.modell.ElementSpellsTable;
import com.example.halperdnd.database.modell.ElemetProfile;
import com.example.halperdnd.ui.spells.dialogs.CreateProfillDialogFragment;
import com.example.halperdnd.ui.spells.filters.OptionFilterSpell;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpellsTableFragment extends Fragment implements View.OnClickListener,AdapterSpellsTable.OnBtnClickListener  {
    private NavController navController;

    private RecyclerView recyclerViewSpellsTable;
    private AdapterSpellsTable adapterSpellsTable;

    private DataBaseHelper dataBaseHelper;

    private DataSpellsImpl dataSpellsImpl;

    private boolean save = false;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        save = switchChekedSpells.isChecked();
    }

    private List<ElementSpellsTable> spellsTableList;
    private List<ElementSpellsTable> spellsTableListCopy;
    private SearchView searchView;
    private TextView tVProfilChoose;

    private Button bttnMenuProfill;
    private Button bttnSpellFilter;
    private PopupMenu popupMenu;
    private PopupMenu popupMenuProfile;

    private List<ElemetProfile> listProfile;

    private int idProfilChoose =1 ;

    private FragmentActivity myContext;

    private CreateProfillDialogFragment dialog;
    private Switch switchChekedSpells;
    private List<Integer> listSpellChoose;
    private OptionFilterSpell myFilter = null;
    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }
    /**onCreate*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**Open db*/
        try {
            dataBaseHelper = new DataBaseHelper(getContext(),"spellsTable.db");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        dataSpellsImpl = dataBaseHelper.getDataSpellsResDao();
        spellsTableList = dataSpellsImpl.loadSpellsTable();
        spellsTableListCopy = new ArrayList<>(spellsTableList);
        updateChecks(idProfilChoose,true);
    }
    /**Create adapter for recycleView*/
    private void initRecyclerView() {
        adapterSpellsTable = new AdapterSpellsTable();
        adapterSpellsTable.setSpellsTableArrayList(spellsTableList);//setSpellsArrayList(spellsTableList);
        adapterSpellsTable.setNavController(navController);
        adapterSpellsTable.setSpellsTableFragment(this);
        recyclerViewSpellsTable.setAdapter(adapterSpellsTable);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_spells, container, false);
        /**NAVcontroler*/
        navController = NavHostFragment.findNavController(this);
        NavBackStackEntry backStackEntry = navController.getBackStackEntry(R.id.nav_spells);
        /**Create ViewModel (ClassData)*/
        SpellFilterViewModel spellFilterViewModel =
                new ViewModelProvider(requireActivity()).get(SpellFilterViewModel.class);
        /**this Fragment subscribe to (ClassData)*/
        spellFilterViewModel.getSelected().observe(backStackEntry,
                new Observer<OptionFilterSpell>() {
            @Override
            public void onChanged(OptionFilterSpell optionFilterSpell) {
                myFilter=optionFilterSpell;
            }
        });

        if (myFilter!=null)
            myFilter.setSpellAndClass(dataSpellsImpl.getSpellAndClass(myFilter.getChooseClass()));
        dialog = new CreateProfillDialogFragment(this);

        /**FilterButton*/
        bttnSpellFilter = root.findViewById(R.id.bttnSpellFilter);
        bttnSpellFilter.setOnClickListener(this);

        /**NAVcontroler*/
        navController = NavHostFragment.findNavController(this);
        /**Profile*/
        tVProfilChoose = root.findViewById(R.id.tVProfilChoos);
        tVProfilChoose.setOnClickListener(this);

        bttnMenuProfill = root.findViewById(R.id.bttnMenuProfill);
        bttnMenuProfill.setOnClickListener(this);

        switchChekedSpells = root.findViewById(R.id.switchChekedSpells);
        switchChekedSpells.setChecked(save);
        switchChekedSpells.setOnClickListener(this);

        updateListProfilles(root);

        /**Create And Delete profile Menu*/
        popupMenu = new PopupMenu(getContext(), bttnMenuProfill, Gravity.CENTER);
        popupMenu.getMenu().add(1,1,1,"Добавить профиль");
        popupMenu.getMenu().add(2,2,2,"Удалить профиль");

        /**SearchView*/
        searchView = root.findViewById(R.id.searchSpellsView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapterSpellsTable.getFilter().filter(newText);
                return false;
            }
        });

        /**RecycleView*/
        spellsTableList = new ArrayList<>(spellsTableListCopy);//!
        recyclerViewSpellsTable = root.findViewById(R.id.spellsRecycle);
        recyclerViewSpellsTable.setLayoutManager(new LinearLayoutManager(root.getContext()));
        initRecyclerView();

        adapterSpellsTable.getFilter().setOptionFilterSpell(myFilter);//setSwitchSpell(switchChekedSpells.isChecked());
        adapterSpellsTable.getFilter().setSwitchSpell(switchChekedSpells.isChecked());
        adapterSpellsTable.getFilter().filter(searchView.getQuery());
        return root;

    }


    /**getters*/
    public int getIdProfilChoose() { return idProfilChoose; }
    public DataSpellsImpl getDataSpellsImpl() { return dataSpellsImpl; }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bttnSpellFilter:
                navController.navigate(R.id.navSpellFilterFragment);
                break;
            case R.id.bttnMenuProfill:
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    return menuItemClicked(item);
                }
            });
                popupMenu.show();
                break;
            case R.id.tVProfilChoos:
                popupMenuProfile.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return menuItemClicked(item);
                    }
                });
                popupMenuProfile.show();
                break;
            case R.id.switchChekedSpells:
                adapterSpellsTable.getFilter().setSwitchSpell(switchChekedSpells.isChecked());
                adapterSpellsTable.getFilter().filter(searchView.getQuery());
                break;
        }
    }

    private void showDialog() {
        dialog.show(myContext.getSupportFragmentManager(),"custom");
    }

    private boolean menuItemClicked(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                showDialog();
                break;
            case 2:
                if (idProfilChoose == 1) {}
                else {
                    updateChecks(idProfilChoose, false);
                    dataSpellsImpl.deleteAllElementFromProfile(idProfilChoose);
                    dataSpellsImpl.deleteProfile(idProfilChoose);
                    idProfilChoose = 1;
                    updateListProfilles(getView());
                    updateChecks(idProfilChoose, true);
                    adapterSpellsTable.getFilter().filter(searchView.getQuery());
                }

                break;
        }
        int id = 2;
        for(ElemetProfile i : listProfile) {
            id ++;
            if (item.getItemId() == id){
                tVProfilChoose.setText(i.getNameProfile());
                updateChecks(idProfilChoose, false);
                idProfilChoose = i.getId();
                updateChecks(idProfilChoose, true);
                spellsTableList = new ArrayList<>(spellsTableListCopy);//!
                adapterSpellsTable.getFilter().filter(searchView.getQuery());
                break;
            }
        }
        return true;
    }
    /**costill TODO*/
    private void updateChecks(int id, boolean check)
    {
        listSpellChoose = dataSpellsImpl.selectElementFromProfile(id);
        for(int i : listSpellChoose) {
            spellsTableListCopy.get(i-1).setCheck(check);
        }
    }

    public void createProfile(String name)
    {
        dataSpellsImpl.insertProfil(name);
        updateListProfilles(getView());
    }

    private void updateListProfilles(View root)
    {
        popupMenuProfile = new PopupMenu(getContext(), tVProfilChoose,Gravity.CENTER);
        listProfile = dataSpellsImpl.loadProfill();

        int id = 2;
        for(ElemetProfile i : listProfile) {
            id ++;
            popupMenuProfile.getMenu().add(id,id,id,i.getNameProfile());
            if (i.getId() == idProfilChoose){
                tVProfilChoose.setText(i.getNameProfile());
                }
        }
        
    }

    @Override
    public void OnBtnDeleteClickListener( int spellId) {
        dataSpellsImpl.deleteElementFromProfile(idProfilChoose,spellId);
    }

    @Override
    public void OnBtnAddClickListener( int spellId) {
        dataSpellsImpl.addElementInProfile(idProfilChoose,spellId);
    }
}