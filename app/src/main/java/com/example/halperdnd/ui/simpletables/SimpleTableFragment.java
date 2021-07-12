package com.example.halperdnd.ui.simpletables;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.halperdnd.R;
import com.example.halperdnd.adapter.AdapterSimpleTable;
import com.example.halperdnd.database.DataBaseHelper;
import com.example.halperdnd.database.dao.simple_table.DataSimpleTableImpl;
import com.example.halperdnd.database.modell.SimpleTable;

import java.io.IOException;
import java.util.List;

public class SimpleTableFragment extends Fragment {


    private RecyclerView recyclerViewSimpleTable;
    private AdapterSimpleTable adapterSimpleTable;

    private DataBaseHelper dataBaseHelper;

    private DataSimpleTableImpl dataSimpleTable;

    private List<SimpleTable> simpleTableList;

    private TextView textViewRessult;
    public SimpleTableFragment(){
        super(R.layout.layout_recycle_simple_table);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_simple_table, container, false);
        recyclerViewSimpleTable = root.findViewById(R.id.recyclerViewSimpleTable);

        recyclerViewSimpleTable.setLayoutManager(new LinearLayoutManager(root.getContext()));

        textViewRessult = root.findViewById(R.id.textViewRessult);
        initRecyclerView();
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            dataBaseHelper = new DataBaseHelper(getContext(),"simpleTables.db");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dataSimpleTable = dataBaseHelper.getDataResDao();

        simpleTableList = dataSimpleTable.loadSimpleTable();
        for(SimpleTable i : simpleTableList) {
            i.setElemetSimpleTableArrayList
                    (dataSimpleTable.loadSimpleTableElement(i.getTable_name_db()));
        }
    }

    private void initRecyclerView() {
        adapterSimpleTable = new AdapterSimpleTable();
        adapterSimpleTable.setSimpleTableFragment(this);
        adapterSimpleTable.setSimpleTableArrayList(simpleTableList);
        recyclerViewSimpleTable.setAdapter(adapterSimpleTable);
    }

    public TextView getTextViewRessult() {
        return textViewRessult;
    }
}