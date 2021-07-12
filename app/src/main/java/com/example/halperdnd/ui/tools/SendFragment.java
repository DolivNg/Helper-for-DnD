package com.example.halperdnd.ui.tools;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.halperdnd.R;
import com.example.halperdnd.adapter.AdapterMarketElements;
import com.example.halperdnd.database.DataBaseHelper;
import com.example.halperdnd.database.dao.equpment.DataEquipmentImpl;
import com.example.halperdnd.database.modell.ElementEquipment;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SendFragment extends Fragment implements View.OnClickListener,AdapterMarketElements.OnBtnClickListener  {
    RecyclerView recyclerViewSpellsTable;

    private SendViewModel sendViewModel;
    private List<ElementEquipment> elementTableMarkets = new ArrayList<>();
    private AdapterMarketElements adapterMarketElement;
    private DataEquipmentImpl dataEquipment;
    private NavController navController;
    private Button btnShare;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBaseHelper dataBaseHelper;
        try {
            dataBaseHelper = new DataBaseHelper(getContext(), "equipmentTable.db");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dataEquipment = dataBaseHelper.getDataEquipmentResDao();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                ViewModelProviders.of(this).get(SendViewModel.class);
        View root = inflater.inflate(R.layout.fragment_send, container, false);

        btnShare = root.findViewById(R.id.btnShare);
        btnShare.setOnClickListener(this);



        navController = NavHostFragment.findNavController(this);
        //navController
        elementTableMarkets = dataEquipment.loadEquipmentFromMarketTable(getArguments().getInt("getId"));

        recyclerViewSpellsTable = root.findViewById(R.id.recycleViewMarketElement);
        recyclerViewSpellsTable.setLayoutManager(new LinearLayoutManager(root.getContext()));
        initRecyclerView();

        return root;

}
    private void initRecyclerView() {
        adapterMarketElement = new AdapterMarketElements(elementTableMarkets,this);
        recyclerViewSpellsTable.setAdapter(adapterMarketElement);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btnShare:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);

                String sendText = "";

                for(ElementEquipment i: elementTableMarkets) {
                    sendText +=
                            i.getName()+"\n"
                            +"Тип: "+i.getCategoryName()+"\n"
                            +"Подтип: "+i.getTypeName()+"\n"
                            +"Цена: "+i.getPrice()+""+i.getTypeCoin()+"\n"
                            +"---------------------------\n";
                }
                sendIntent.putExtra(Intent.EXTRA_TEXT, sendText);


                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                break;

        }
    }

    @Override
    public void onDeleteClickBtn(int position, int equip_id) {
        dataEquipment.deleteMarketElements(getArguments().getInt("getId"),equip_id);
        elementTableMarkets.remove(position);
        adapterMarketElement.notifyItemRemoved(position);

    }
}