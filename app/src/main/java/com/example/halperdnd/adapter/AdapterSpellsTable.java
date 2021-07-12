package com.example.halperdnd.adapter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;
import com.example.halperdnd.R;
import com.example.halperdnd.database.modell.ElementSpellsTable;
import com.example.halperdnd.database.modell.SpellAndClass;
import com.example.halperdnd.ui.spells.SpellsTableFragment;
import com.example.halperdnd.ui.spells.filters.FillterSpell;

import java.util.ArrayList;
import java.util.List;

public class AdapterSpellsTable extends RecyclerView.Adapter<AdapterSpellsTable.SpellsTableViewHolder> implements Filterable {

    private List<ElementSpellsTable> spellsTableArrayList = new ArrayList<>();
    private List<AdapterSpellsTable.SpellsTableViewHolder> spellsTableViewHolders = new ArrayList<>();
    private List<ElementSpellsTable> spellsTableListFull ;
    private NavController navController;
    private SpellsTableFragment spellsTableFragment;

    public void setSpellsTableFragment(SpellsTableFragment spellsTableFragment) {
        this.spellsTableFragment = spellsTableFragment;
    }

    public void setNavController(NavController navController) {
        this.navController = navController;
    }

    public void setSpellsTableArrayList(List<ElementSpellsTable> spellsTableArrayList ) {
        this.spellsTableArrayList = spellsTableArrayList;
        spellsTableListFull = new ArrayList<>(spellsTableArrayList);
    }


    @NonNull
    @Override
    public AdapterSpellsTable.SpellsTableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycle_spells_table, parent, false);
        AdapterSpellsTable.SpellsTableViewHolder d = new AdapterSpellsTable.SpellsTableViewHolder(view);
        spellsTableViewHolders.add(d);
        return d;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSpellsTable.SpellsTableViewHolder holder, int position) {
        holder.bind(spellsTableArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return spellsTableArrayList.size();
    }

    /**Get filter with equipmentTable*/
    @Override
    public FillterSpell getFilter() {
        return spellsFilter;
    }

    class SpellsTableViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tVSpellName;
        private TextView tVSpellSource;
        private TextView tVSpellLevel;
        private TextView tVSpellSchool;
        private ElementSpellsTable elementSpellsTable;
        private LinearLayout layout;
        private CheckBox checkBoxSpell;

    public SpellsTableViewHolder(View view) {
        super(view);

        tVSpellName= view.findViewById(R.id.tVSpellName);
        tVSpellSource= view.findViewById(R.id.tVSpellSource);
        tVSpellLevel= view.findViewById(R.id.tVSpellLevel);
        tVSpellSchool= view.findViewById(R.id.tVSpellSchool);
        layout = view.findViewById(R.id.spellLinerLayout);
        checkBoxSpell = view.findViewById(R.id.checkBoxSpell);

        checkBoxSpell.setOnClickListener(this);
        layout.setOnClickListener(this);
    }


    public void bind(ElementSpellsTable elementSpellsTable) {
        this.elementSpellsTable=elementSpellsTable;

        tVSpellName.setText(elementSpellsTable.getName());
        tVSpellSource.setText(elementSpellsTable.getStSource());
        tVSpellLevel.setText(elementSpellsTable.getStLevel());
        tVSpellSchool.setText(elementSpellsTable.getStSchool());
        checkBoxSpell.setChecked(elementSpellsTable.isCheck());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.spellLinerLayout:
                /**Send info to DescriptionFragment*/
                Bundle bundle = new Bundle();
                bundle.putString("getName", elementSpellsTable.getName());
                bundle.putString("getDescription", elementSpellsTable.getDescription());
                bundle.putString("getComponents", elementSpellsTable.getComponents());
                bundle.putString("getDistance", elementSpellsTable.getDistance());
                bundle.putString("getDuration", elementSpellsTable.getDuration());
                bundle.putString("getStLevel", elementSpellsTable.getStLevel());
                bundle.putString("getMatComponrnts", elementSpellsTable.getMatComponrnts());
                bundle.putString("getStSchool", elementSpellsTable.getStSchool());
                bundle.putString("getTimeCast", elementSpellsTable.getTimeCast());

                navController.navigate(R.id.action_nav_spells_to_spellsDescriptionFragment,bundle);

                break;
            case R.id.checkBoxSpell:
                if (checkBoxSpell.isChecked()) {
                    spellsTableFragment.OnBtnAddClickListener(elementSpellsTable.getId());

                    elementSpellsTable.setCheck(checkBoxSpell.isChecked());
                }else{
                    spellsTableFragment.OnBtnDeleteClickListener(elementSpellsTable.getId());

                    elementSpellsTable.setCheck(checkBoxSpell.isChecked());
                }
                break;
        }
    }
    }
    /**Filter for spell List*/
    private FillterSpell spellsFilter = new FillterSpell() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ElementSpellsTable> filteredListFirst = new ArrayList<>();
            List<ElementSpellsTable> filteredListSecond = new ArrayList<>();




            if (constraint == null || constraint.length() == 0) {
                filteredListFirst.addAll(spellsTableListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ElementSpellsTable item : spellsTableListFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredListFirst.add(item);
                    }
                }
            }
            if (!switchSpell){ }
            else{
                for (ElementSpellsTable item : filteredListFirst) {
                    if (item.isCheck()) {
                        filteredListSecond.add(item);
                    }
                }
                filteredListFirst.clear();
                filteredListFirst.addAll(filteredListSecond);
            }
            filteredListSecond.clear();

            if (optionFilterSpell != null)
                if (optionFilterSpell.getChooseClass() == 0)
                {}else {
                    for (ElementSpellsTable item : spellsTableListFull)
                        for (int i : optionFilterSpell.getSpellAndClass().getListIdSpell())
                            if (item.getId() == i)
                            {
                                filteredListSecond.add(item);
                            }
                    filteredListFirst.clear();
                    filteredListFirst.addAll(filteredListSecond);
                }


            if (optionFilterSpell != null)
            if ((!optionFilterSpell.isBoolSpellLevel() && !optionFilterSpell.isBoolspellSchool() && !optionFilterSpell.isBoolspellSource()))
            {}else {filteredListSecond.clear();
                for (ElementSpellsTable item : filteredListFirst) {
                    if (optionFilterSpell.getSpellLevel().get(item.getLevel_id() - 1) == optionFilterSpell.isBoolSpellLevel() &&
                            optionFilterSpell.getSpellSchool().get(item.getSchool_id() - 1) == optionFilterSpell.isBoolspellSchool() &&
                            optionFilterSpell.getSpellSource().get(item.getSource_id() - 1) == optionFilterSpell.isBoolspellSource()) {
                        filteredListSecond.add(item);
                    }
                }
                filteredListFirst.clear();
                filteredListFirst.addAll(filteredListSecond);
            }

            FilterResults results = new FilterResults();
            results.values = filteredListFirst;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            spellsTableArrayList.clear();
            spellsTableArrayList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public interface OnBtnClickListener {
        void OnBtnDeleteClickListener( int spellId);
        void OnBtnAddClickListener( int spellId);
    }

}
