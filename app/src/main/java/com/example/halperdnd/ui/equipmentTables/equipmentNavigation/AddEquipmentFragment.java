package com.example.halperdnd.ui.equipmentTables.equipmentNavigation;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.halperdnd.R;
import com.example.halperdnd.database.DataBaseHelper;
import com.example.halperdnd.database.dao.equpment.DataEquipmentImpl;

import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AddEquipmentFragment extends Fragment implements View.OnClickListener{
    private EditText eTEquipmentName;
    private EditText eTEquipmentPrice;
    private EditText eTEquipmentWight;
    private EditText eTEquipmentDescription;
    private RadioGroup rBtnSubType;
    private RadioGroup rBtnType;
    private RadioGroup rBtnPrice;
    private DataBaseHelper dataBaseHelper;
    private Button bttnAddEquipment;
    private DataEquipmentImpl dataEquipmentImpl;
    private NavController navController;
    private int N=0;

    private List<String> listSubtype;
    private List<String> listType;
    private List<String> listCoin;

    private Toast toast;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            dataBaseHelper = new DataBaseHelper(getContext(),"equipmentTable.db");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dataEquipmentImpl = dataBaseHelper.getDataEquipmentResDao();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_equipment, container, false);


        navController= NavHostFragment.findNavController(this);

        /**listType load*/
        rBtnType = view.findViewById(R.id.rBtnGroupTypeEqupment);
        listType = dataEquipmentImpl.loadEquipmentType();
        for(String i : listType) {
            RadioButton rBtn= new RadioButton(getContext());
            rBtn.setText(i);
            rBtnType.addView(rBtn);
            N++;
        }
        /**listCoin load*/
        rBtnPrice = view.findViewById(R.id.rBtnGroupPriceType);
        listCoin = dataEquipmentImpl.loadCoinType();
        for(String i : listCoin) {
            RadioButton rBtn= new RadioButton(getContext());
            rBtn.setText(i);
            rBtnPrice.addView(rBtn);
            N++;
        }
        /**listSubtype load*/
        rBtnSubType = view.findViewById(R.id.rBtnGroupSubTypeEquipment);
        listSubtype = dataEquipmentImpl.loadEquipmentSubtype();
        for(String i : listSubtype) {
            RadioButton rBtn= new RadioButton(getContext());
            rBtn.setText(i);
            rBtnSubType.addView(rBtn);
            N++;
        }

        TextInputLayout b = view.findViewById(R.id.eTEquipmentNameHint);
        eTEquipmentName = b.findViewById(R.id.eTEqupmentName);
        b.setHint("Название предмета");

        b = view.findViewById(R.id.eTEquipmentPriceHint);
        eTEquipmentPrice = b.findViewById(R.id.eTEquipmentPrice);
        b.setHint("Цена предмета");

        b = view.findViewById(R.id.eTEquipmentWightHint);
        eTEquipmentWight = b.findViewById(R.id.eTEquipmentWight);
        b.setHint("Вес (не обязательно)");

        b = view.findViewById(R.id.eTEquipmentDescriptionHint);
        eTEquipmentDescription = b.findViewById(R.id.eTEquipmentDescription);
        b.setHint("Описание предмета(не обязательно)");

        bttnAddEquipment = view.findViewById(R.id.bttnAddEquipment);
        bttnAddEquipment.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bttnAddEquipment:
                Log.d("vasa", "a" +eTEquipmentName.getText());
                Log.d("vasa", "btn" +gedId(rBtnPrice.getCheckedRadioButtonId(),listType.size())+
                        " btn "+gedId(rBtnType.getCheckedRadioButtonId(),0)+
                        " btn " +gedId(rBtnSubType.getCheckedRadioButtonId(),listType.size()+listCoin.size()));
                //navController.popBackStack();
                if(rBtnType.getCheckedRadioButtonId() == -1) {
                    toast = Toast.makeText(v.getContext(), "Тип нужно выбрать", Toast.LENGTH_SHORT);
                    toast.show();
                } else
                    if (eTEquipmentName.getText().toString()+""=="") {
                        toast = Toast.makeText(v.getContext(), "Название нужно написать", Toast.LENGTH_SHORT);
                        toast.show();
                    }else
                        if (eTEquipmentPrice.getText().toString()+""==""){
                            toast = Toast.makeText(v.getContext(), "Цена не указана цена", Toast.LENGTH_SHORT);
                            toast.show();
                        }else
                            if (rBtnPrice.getCheckedRadioButtonId() == -1){
                                    toast = Toast.makeText(v.getContext(), "Не указан тип монет", Toast.LENGTH_SHORT);
                                    toast.show();
                                }else
                                    if (rBtnSubType.getCheckedRadioButtonId() == -1){
                                        toast = Toast.makeText(v.getContext(), "Не указан подтип", Toast.LENGTH_SHORT);
                                        toast.show();
                                        }else {
                                            dataEquipmentImpl.insertEquipment(
                                                    gedId(rBtnType.getCheckedRadioButtonId(),0),
                                                    eTEquipmentName.getText().toString(),
                                                    Integer.parseInt(eTEquipmentPrice.getText().toString()),
                                                    gedId(rBtnPrice.getCheckedRadioButtonId(),listType.size()),
                                                    eTEquipmentWight.getText().toString(),
                                                    eTEquipmentDescription.getText().toString(),
                                                    2,
                                                    gedId(rBtnSubType.getCheckedRadioButtonId(),listType.size()+listCoin.size()));
                                            navController.popBackStack();}
            break;
        }
    }

    private int gedId(int id,int size)
    {
        return id == (id / N)*N ? N-size : (id - (id / N)*N)-size;
    }
}
