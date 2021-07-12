package com.example.halperdnd.ui.equipmentTables.equipmentNavigation;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import com.example.halperdnd.R;
import com.example.halperdnd.adapter.AdapterEquipmentTable;
import com.example.halperdnd.database.DataBaseHelper;
import com.example.halperdnd.database.dao.equpment.DataEquipmentImpl;
import com.example.halperdnd.database.modell.ElementEquipment;

import java.io.IOException;
import java.util.List;


public class EquipmentTableListFragment extends Fragment implements View.OnClickListener,AdapterEquipmentTable.OnBtnClickListener{

    private NavController navController;
    private RecyclerView recyclerViewEquipmentTable;
    private AdapterEquipmentTable adapterEquipmentTable;

    private DataBaseHelper dataBaseHelper;

    private DataEquipmentImpl dataEquipmentImpl;
    private List<ElementEquipment> equipmentTableList;
    private Button bttnAddEquipment;
    SearchView searchView;

    public EquipmentTableListFragment(){
        super(R.layout.layout_recycle_equipment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_equipment_table_list, container, false);
        navController= NavHostFragment.findNavController(this);

        bttnAddEquipment=root.findViewById(R.id.bttnAddEquipment);
        bttnAddEquipment.setOnClickListener(this);
        /**RecycleView*/
        recyclerViewEquipmentTable = root.findViewById(R.id.equipmentRecycle);
        recyclerViewEquipmentTable.setLayoutManager(new LinearLayoutManager(root.getContext()));
        initRecyclerView();
        /**SearchView*/
        searchView = root.findViewById(R.id.searchEquipmentView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapterEquipmentTable.getFilter().filter(newText);
                return false;
            }
        });
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            dataBaseHelper = new DataBaseHelper(getContext(),"equipmentTable.db");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dataEquipmentImpl = dataBaseHelper.getDataEquipmentResDao();

    }

    private void initRecyclerView() {
        equipmentTableList = dataEquipmentImpl.loadEquipmentTable();
        adapterEquipmentTable = new AdapterEquipmentTable( equipmentTableList ,this);
        //adapterEquipmentTable.setEquipmentTableArrayList(equipmentTableList);
        //adapterEquipmentTable.setDataEquipmentImpl(dataEquipmentImpl);
        recyclerViewEquipmentTable.setAdapter(adapterEquipmentTable);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.bttnAddEquipment:
                navController.navigate(R.id.addEquipmentFragment);
                break;
        }
    }

    @Override
    public void OnBtnDeleteClickListener(int position, int equipId) {
        dataEquipmentImpl.deleteEquipmentElement(equipId);
        equipmentTableList.remove(position);
        adapterEquipmentTable.notifyItemRemoved(position);
    }
}
