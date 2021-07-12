package com.example.halperdnd.ui.tools;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.halperdnd.R;
import com.example.halperdnd.adapter.AdapterMarket;
import com.example.halperdnd.database.DataBaseHelper;
import com.example.halperdnd.database.dao.equpment.DataEquipmentImpl;
import com.example.halperdnd.database.modell.ElementEquipment;
import com.example.halperdnd.database.modell.ElementMarket;
import com.example.halperdnd.database.modell.OptionEquipment;
import com.example.halperdnd.dopClasses.DiceRolling;
import com.example.halperdnd.view.CheckButtonBig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EquipmentTableMarketFragment extends Fragment implements View.OnClickListener ,AdapterMarket.OnBtnClickListener {


    private List<ElementMarket> elementTableMarkets = new ArrayList<>();
    private AdapterMarket adapterMarket;
    private RecyclerView recyclerViewMarkets;
    private DataEquipmentImpl dataEquipment;

    private EditText eTMarketName;
    private EditText eTMarketCity;
    private Button btnMarketNameRoll;
    private Switch switchMarketHomeBrew;
    private Switch switchRollFullMarket;
    private LinearLayout tableLayoutMarket;
    private List<OptionEquipment> optionEquipmentList;
    private List<ListCheckButton> tableRowList = new ArrayList<>();
    private Button btnRollMarket;
    private List<ElementEquipment> elementEquipmentList;
    private List<Integer> chance;
    private NavController navController;
    private PopupMenu popMenuTypeTrader;
    private TextView tVTypeTrader;
    private int  intTypeTrader = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DataBaseHelper dataBaseHelper;
        try {
            dataBaseHelper = new DataBaseHelper(getContext(), "equipmentTable.db");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dataEquipment = dataBaseHelper.getDataEquipmentResDao();
        optionEquipmentList = dataEquipment.loadOptionEquipment();
        elementEquipmentList = dataEquipment.loadEquipmentTable();
        chance = dataEquipment.chanceSubType();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_equipment_table_market, container, false);

        /**TypeTraderMenu*/
        tVTypeTrader = root.findViewById(R.id.tVTypeTrider) ;
        tVTypeTrader.setOnClickListener(this);

        popMenuTypeTrader  = new PopupMenu(getContext(),tVTypeTrader);
        popMenuTypeTrader.getMenu().add(0,0,0,"Багатый");
        popMenuTypeTrader.getMenu().add(1,1,1,"Достаточный");
        popMenuTypeTrader.getMenu().add(2,2,2,"Бедный");

        tVTypeTrader.setText(popMenuTypeTrader.getMenu().getItem(1).getTitle());
        /**EditTextName*/
        eTMarketName = root.findViewById(R.id.eTMarketName);
        eTMarketCity = root.findViewById(R.id.eTMarketCity);
        /**Button roll*/
        /*btnMarketNameRoll = root.findViewById(R.id.btnMarketNameRoll);
        btnMarketNameRoll.setOnClickListener(this);*/
        /**switch Home Brew*/
        switchMarketHomeBrew = root.findViewById(R.id.switchMarketHomeBrew);
        /**switchMarketFullRoll*/
        switchRollFullMarket = root.findViewById(R.id.switchRollFullMarket);
        switchRollFullMarket.setOnClickListener(this);
        /**RollMarket*/
        btnRollMarket = root.findViewById(R.id.btnRollMarket);
        btnRollMarket.setOnClickListener(this);

        tableLayoutMarket = root.findViewById(R.id.tableLayoutMarket);
        initMarket();

        navController = NavHostFragment.findNavController(this);

        recyclerViewMarkets = root.findViewById(R.id.recycleViewMarkets);
        recyclerViewMarkets.setLayoutManager(new LinearLayoutManager(root.getContext()));
        initRecyclerView();

        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switchRollFullMarket:
                if (!switchRollFullMarket.isChecked())
                    tableLayoutMarket.setVisibility(View.VISIBLE);
                else
                    tableLayoutMarket.setVisibility(View.GONE);
                break;

            case R.id.btnRollMarket:
                /**тим часово*/
                int namber =0;
                int value=70;//= //10+DiceRolling.diceRoll(6)+DiceRolling.diceRoll(6)+DiceRolling.diceRoll(6)+DiceRolling.diceRoll(6);
                switch(intTypeTrader)
                {
                    case 2 :
                        namber = 8+DiceRolling.diceRoll(6)+DiceRolling.diceRoll(6);
                        value =70;
                        break;
                    case 1 :
                        namber = 12+DiceRolling.diceRoll(6)+DiceRolling.diceRoll(6)+DiceRolling.diceRoll(6);
                        value =50;
                        break;
                    case 0 :
                        value =30;
                        namber = 16+DiceRolling.diceRoll(6)+DiceRolling.diceRoll(6)+DiceRolling.diceRoll(6)+DiceRolling.diceRoll(6);
                        break;
                }

                List<ChooseEquipment> bufferList = new ArrayList<>();


                if (eTMarketName.getText().toString().equals("") ||
                        eTMarketName.getText().toString() == null)
                {
                    Toast toast = Toast.makeText(v.getContext(), "Назовите Магазин", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                { if (switchRollFullMarket.isChecked())
                {
                    for(int i=0;i<5;i++) {
                        tableRowList.get(i).getCheckButtonBigCategory().setChecked(true);
                        for(CheckBox  type :tableRowList.get(i).getListSubtype()) {
                                    type.setChecked(true);
                        }
                    }
                }

                         for (ElementEquipment equip: elementEquipmentList )//all equpment
                            for(ListCheckButton category : tableRowList) {
                                if (category.getCheckButtonBigCategory().isChecked() &&
                                        equip.getCategoryName().equals(category.getCheckButtonBigCategory().getText())) {
                                     for(CheckBox  type :category.getListSubtype()) {
                                        if (type.isChecked() &&
                                                equip.getTypeName().equals(type.getText()) ) {
                                            bufferList.add(new  ChooseEquipment(equip.getId(),
                                                    chance.get(equip.getType_id()-1)+DiceRolling.diceRoll(100)));
                                        }
                                    }
                                }
                            }
                         /**Sort Buffer*/
                    Collections.sort(bufferList);

                   int id =   dataEquipment.insertElementMarket(eTMarketName.getText().toString(),
                            eTMarketCity.getText().toString());


                   for(int i=0;i<bufferList.size() && i<namber;i++){
                        if (bufferList.get(i).getRolling() >= value)
                            dataEquipment.insertMarketElement(id , bufferList.get(i).equipment_id);
                   }

                    if (switchRollFullMarket.isChecked())
                    {
                        for(int i=0;i<5;i++) {
                            tableRowList.get(i).getCheckButtonBigCategory().setChecked(false);
                            for(CheckBox  type :tableRowList.get(i).getListSubtype()) {
                                type.setChecked(false);
                            }
                        }
                    }
                    elementTableMarkets = dataEquipment.loadElementMarket();
                    adapterMarket.setElementMarkets(elementTableMarkets);
                    adapterMarket.notifyDataSetChanged();
                }
                break;
            case R.id.tVTypeTrider:
                popMenuTypeTrader.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return menuItemClicked(item);
                    }
                });
                popMenuTypeTrader.show();
                break;
        }
    }



    class ChooseEquipment implements Comparable<ChooseEquipment>{

        private int equipment_id;

        public int getRolling() {
            return rolling;
        }

        private int rolling;

        public ChooseEquipment(int equipment_id, int rolling) {
            this.equipment_id = equipment_id;

            this.rolling = rolling;
        }

        @NonNull
        @Override
        public String toString() {
            return "roll "+rolling;
        }

        @Override
        public int compareTo(ChooseEquipment o) {

            return o.getRolling() - this.rolling;

        }
    }


    private void initRecyclerView() {
        elementTableMarkets = dataEquipment.loadElementMarket();
        adapterMarket = new AdapterMarket(elementTableMarkets,this);
        adapterMarket.setDataEquipment(dataEquipment);
        adapterMarket.setNavController(navController);
        recyclerViewMarkets.setAdapter(adapterMarket);
    }

    /**initilization Market interface */
    private void initMarket()
    {
        int id =0;
        for (OptionEquipment category : optionEquipmentList) {
            ListCheckButton elementListCheckButton = new ListCheckButton();

            elementListCheckButton.createCheckButtonBigCategory(category.getCategoryName(),id);
            id++;
            elementListCheckButton.createLinearLayout();

            tableLayoutMarket.addView(elementListCheckButton.getCheckButtonBigCategory());
            tableLayoutMarket.addView(elementListCheckButton.getLinearLayout());
            int k = 0;
            for (OptionEquipment.SubType subType : category.getSubTypeList()) {
                elementListCheckButton.addCheckBox(subType.getSubTypeName());

            }
            elementListCheckButton.checkButtonBigSubTypeVisible(
                    elementListCheckButton.getCheckButtonBigCategory().isChecked()  ? View.VISIBLE : View.GONE);
            tableRowList.add(elementListCheckButton);
        }

    }


    class ListCheckButton implements View.OnClickListener {
         CheckButtonBig checkButtonBigCategory ;
         LinearLayout linearLayout;

        List<CheckBox> tableRowListSubtype = new ArrayList<>();

         public void createCheckButtonBigCategory(String name,int id){
             checkButtonBigCategory =  new CheckButtonBig(getContext());
                checkButtonBigCategory.setText(name);
                checkButtonBigCategory.setId(id);
                checkButtonBigCategory.setTextColor(
                        getResources().getColor(R.color.color_black));
                checkButtonBigCategory.setBackground(
                        getResources().getDrawable(R.drawable.ic_btn_big_choose));
                checkButtonBigCategory.setBackgroundTintList(
                        getResources().getColorStateList(R.color.button_roll_selector));
                checkButtonBigCategory.setLayoutParams(
                     new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                             TableRow.LayoutParams.WRAP_CONTENT));
                checkButtonBigCategory.setOnClickListener(this);

         }
        public LinearLayout getLinearLayout() {
            return linearLayout;
        }

         public void addCheckBox( String name) {
             CheckBox chBox = new CheckBox(getContext());
             chBox.setText(name);
             chBox.setPadding(25,0,0,0);
             chBox.setLayoutParams(
                     new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                             LinearLayout.LayoutParams.WRAP_CONTENT));

             tableRowListSubtype.add(chBox);
             linearLayout.addView(chBox);
         }


        public void createLinearLayout( ) {
            linearLayout = new LinearLayout(getContext());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
        }

        public List<CheckBox> getListSubtype() {
            return tableRowListSubtype;
        }

        public CheckButtonBig getCheckButtonBigCategory() {
            return checkButtonBigCategory;
        }

        @Override
        public void onClick(View v) {
                if (checkButtonBigCategory.getId() == v.getId())
                    checkButtonBigSubTypeVisible( checkButtonBigCategory.isChecked()  ? View.VISIBLE : View.GONE);
        }
    public void checkButtonBigSubTypeVisible(int visible)
    {linearLayout.setVisibility(visible);}

    }

    @Override
    public void onDeleteClickBtn(int position, int id) {
        dataEquipment.deleteMarketElements(id);
        dataEquipment.deleteMarket(id);
        elementTableMarkets.remove(position);
        adapterMarket.notifyItemRemoved(position);
    }

    @Override
    public void onNavigateClickBtn(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("getId", id);
        navController.navigate(R.id.action_nav_market_to_sendFragment,bundle);
    }
/**PopMenu*/
    private boolean menuItemClicked(MenuItem item) {

        switch(item.getItemId())
        {
            case 0:
                tVTypeTrader.setText(popMenuTypeTrader.getMenu().getItem(0).getTitle());
                intTypeTrader = 0;
                break;
            case 1:
                tVTypeTrader.setText(popMenuTypeTrader.getMenu().getItem(1).getTitle());
                intTypeTrader = 1;
                break;
            case 2:
                tVTypeTrader.setText(popMenuTypeTrader.getMenu().getItem(2).getTitle());
                intTypeTrader = 2;
                break;
        }

        return true;
    }
}
