package com.example.halperdnd.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.halperdnd.R;
import com.example.halperdnd.database.dao.equpment.DataEquipmentImpl;
import com.example.halperdnd.database.modell.ElementEquipment;

import java.util.ArrayList;
import java.util.List;

public class AdapterEquipmentTable extends RecyclerView.Adapter<AdapterEquipmentTable.EquipmentTableViewHolder> implements Filterable {

    private List<ElementEquipment> equipmentTableArrayList = new ArrayList<>();
    private List<AdapterEquipmentTable.EquipmentTableViewHolder> equipmentTableViewHolders = new ArrayList<>();
    private List<ElementEquipment> equipmentTableListFull ;
    private DataEquipmentImpl dataEquipmentImpl;
    private OnBtnClickListener listener;

    public AdapterEquipmentTable(List<ElementEquipment> equipmentTableArrayList, OnBtnClickListener listener) {
        this.equipmentTableArrayList = equipmentTableArrayList;
        this.listener = listener;
        equipmentTableListFull = new ArrayList<>(equipmentTableArrayList);
    }

    /** constructor*/
    public void setEquipmentTableArrayList(List<ElementEquipment> equipmentTableArrayList) {
        this.equipmentTableArrayList = equipmentTableArrayList;
        equipmentTableListFull = new ArrayList<>(equipmentTableArrayList);
    }

    @NonNull
    @Override
    public AdapterEquipmentTable.EquipmentTableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycle_equipment, parent, false);
        AdapterEquipmentTable.EquipmentTableViewHolder d = new AdapterEquipmentTable.EquipmentTableViewHolder(view);
        equipmentTableViewHolders.add(d);
        return d;
    }

    public void setDataEquipmentImpl(DataEquipmentImpl dataEquipmentImpl)
    {this.dataEquipmentImpl= dataEquipmentImpl;}

    @Override
    public void onBindViewHolder(@NonNull AdapterEquipmentTable.EquipmentTableViewHolder holder, int position) {
        holder.bind(equipmentTableArrayList.get(position),dataEquipmentImpl);
    }

    @Override
    public int getItemCount() {
        return equipmentTableArrayList.size();
    }

    /**Get filter with equipmentTable*/
    @Override
    public Filter getFilter() {
        return equipmentFilter;
    }
    /**ViewHolder*/
    class EquipmentTableViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tViewName;
        TextView tViewCategory;
        TextView tViewType;
        TextView tViewPrice;
        TextView tViewWight;
        TextView tViewDescription;
        LinearLayout layout;
        Button bttnDeleteEquipment;
        ElementEquipment elementEquipment;
        DataEquipmentImpl dataEquipmentImpl;

        public EquipmentTableViewHolder(View view) {
            super(view);
            tViewName = view.findViewById(R.id.tViewName);
            tViewCategory = view.findViewById(R.id.tViewCategory);
            tViewType = view.findViewById(R.id.tViewType);
            tViewPrice = view.findViewById(R.id.tViewPrice);
            tViewWight = view.findViewById(R.id.tViewWight);
            tViewDescription = view.findViewById(R.id.tViewDescription);
            layout = view.findViewById(R.id.elemRecycleEquipment);
            bttnDeleteEquipment = view.findViewById(R.id.btnDeleteEquipment);

            bttnDeleteEquipment.setOnClickListener(this);
            layout.setOnClickListener(this);
        }


        public void bind(ElementEquipment elementEquipment, DataEquipmentImpl dataEquipmentImpl) {
            this.elementEquipment = elementEquipment;

            tViewName.setText(elementEquipment.getName());
            tViewCategory.setText("Тип: "+elementEquipment.getCategoryName());
            tViewType.setText("Подтип: "+elementEquipment.getTypeName());
            tViewPrice.setText("Цена: "+ elementEquipment.getPrice() + " " + elementEquipment.getTypeCoin());
            if (elementEquipment.getWight() == null)
                tViewWight.setText("Вес: "+"- фнт");else
            tViewWight.setText("Вес: "+elementEquipment.getWight() + " фнт");
            tViewDescription.setText(elementEquipment.getDescription());

            this.dataEquipmentImpl = dataEquipmentImpl;

            if (elementEquipment.getSource() == 2)
                bttnDeleteEquipment.setVisibility(View.VISIBLE);
            else
                bttnDeleteEquipment.setVisibility(View.GONE);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.elemRecycleEquipment:
                    if (tViewDescription.getVisibility() == View.VISIBLE)
                        tViewDescription.setVisibility(View.GONE);
                    else
                        tViewDescription.setVisibility(View.VISIBLE);
                    break;
                case R.id.btnDeleteEquipment:

                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.OnBtnDeleteClickListener(position, elementEquipment.getId());
                        }

                        break;
                    }
            }
        }
    }
    /**Filter for equipment List*/
    private Filter equipmentFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ElementEquipment> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(equipmentTableListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ElementEquipment item : equipmentTableListFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            equipmentTableArrayList.clear();
            equipmentTableArrayList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public interface OnBtnClickListener {
        void OnBtnDeleteClickListener(int position, int equipId);
    }
}
