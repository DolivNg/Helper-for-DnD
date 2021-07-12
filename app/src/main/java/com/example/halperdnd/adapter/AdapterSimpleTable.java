package com.example.halperdnd.adapter;

import android.inputmethodservice.Keyboard;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.halperdnd.R;
import com.example.halperdnd.database.modell.SimpleTable;
import com.example.halperdnd.dopClasses.DiceRolling;
import com.example.halperdnd.ui.simpletables.SimpleTableFragment;

import java.util.ArrayList;
import java.util.List;

public class AdapterSimpleTable extends RecyclerView.Adapter<AdapterSimpleTable.SimpleTableViewHolder> {


    private List<SimpleTable> simpleTableArrayList = new ArrayList<>();
    private List<SimpleTableViewHolder> simpleTableViewHolders = new ArrayList<>();


    private SimpleTableFragment simpleTableFragment;

    public void setSimpleTableFragment(SimpleTableFragment simpleTableFragment) {
        this.simpleTableFragment = simpleTableFragment;
    }

    public void setSimpleTableArrayList(List<SimpleTable> simpleTableArrayList) {
        this.simpleTableArrayList = simpleTableArrayList;
    }

    @NonNull
    @Override
    public SimpleTableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycle_simple_table, parent, false);
        SimpleTableViewHolder d = new SimpleTableViewHolder(view);
        simpleTableViewHolders.add(d);
        return d;
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleTableViewHolder holder, int position) {

        holder.bind(simpleTableArrayList.get(position), simpleTableFragment);
    }

    @Override
    public int getItemCount() {
        return simpleTableArrayList.size();
    }

    class SimpleTableViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textViewName;
        private Button bttnRoll;
        private EditText rollingNumber;
        private SimpleTable simpleTable;
        private SimpleTableFragment simpleTableFragment;

        public SimpleTableViewHolder(View view) {
            super(view);
            textViewName = view.findViewById(R.id.textViewTableName);
            bttnRoll = view.findViewById(R.id.bttnRoll);
            rollingNumber = view.findViewById(R.id.editTextRollingNumber);
            rollingNumber.setOnKeyListener(new View.OnKeyListener() {
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        return true;
                    }
                    return false;
                }
            });
            bttnRoll.setOnClickListener(this);
        }


        public void bind(SimpleTable simpleTable, SimpleTableFragment simpleTableFragment) {
            textViewName.setText(simpleTable.getTable_name());
            this.simpleTable = simpleTable;
            this.simpleTableFragment = simpleTableFragment;
        }

        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.bttnRoll:
                    int rollingDice = DiceRolling.diceRoll(simpleTable.getDice());
                    String buff = rollingNumber.getText().toString();
                    if (buff.isEmpty()) {
                        simpleTableFragment.getTextViewRessult().setText(
                                rollingDice + "\n" +
                                        simpleTable.getRessult(rollingDice));
                    } else {
                        if (Integer.valueOf(buff) >= 1 && Integer.valueOf(buff) <= 100) {
                            simpleTableFragment.getTextViewRessult().setText(
                                    buff + "\n" +
                                            simpleTable.getRessult(
                                                    Integer.valueOf(buff)));
                            rollingNumber.setText("");
                        } else {
                            Toast toast = Toast.makeText(simpleTableFragment.getContext(), "Не коректне значення", Toast.LENGTH_SHORT);
                            toast.show();
                            rollingNumber.setText("");
                        }
                    }
                    break;
            }
        }
    }
}
