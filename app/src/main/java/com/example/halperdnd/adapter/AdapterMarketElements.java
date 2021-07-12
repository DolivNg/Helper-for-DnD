package com.example.halperdnd.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.example.halperdnd.R;
import com.example.halperdnd.database.dao.equpment.DataEquipmentImpl;
import com.example.halperdnd.database.modell.ElementEquipment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdapterMarketElements  extends RecyclerView.Adapter<AdapterMarketElements.AdapterMarketElementViewHolder> {
    private List<ElementEquipment> equipmentTableArrayList = new ArrayList<>();
    private List<AdapterMarketElements.AdapterMarketElementViewHolder> equipmentTableViewHolders = new ArrayList<>();

    public AdapterMarketElements(List<ElementEquipment> equipmentTableArrayList, OnBtnClickListener listener) {
        this.equipmentTableArrayList = equipmentTableArrayList;
        this.listener = listener;
    }

    private OnBtnClickListener listener;


    public void setEquipmentTableArrayList(List<ElementEquipment> equipmentTableArrayList) {
        this.equipmentTableArrayList = equipmentTableArrayList;
    }

    @NotNull
    @Override
    public AdapterMarketElementViewHolder onCreateViewHolder( @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycle_equipment, parent, false);
        AdapterMarketElementViewHolder d = new AdapterMarketElementViewHolder(view);
        equipmentTableViewHolders.add(d);
        return d;
    }

    @Override
    public void onBindViewHolder( @NotNull AdapterMarketElementViewHolder holder, int position) {
        holder.bind(equipmentTableArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return equipmentTableArrayList.size();
    }

    class AdapterMarketElementViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tViewName;
        TextView tViewCategory;
        TextView tViewType;
        TextView tViewPrice;
        TextView tViewWight;
        TextView tViewDescription;
        LinearLayout layout;
        Button btnDeleteEquipment;
        ElementEquipment elementEquipment;


        public AdapterMarketElementViewHolder(View view) {
            super(view);
            tViewName = view.findViewById(R.id.tViewName);
            tViewCategory = view.findViewById(R.id.tViewCategory);
            tViewType = view.findViewById(R.id.tViewType);
            tViewPrice = view.findViewById(R.id.tViewPrice);
            tViewWight = view.findViewById(R.id.tViewWight);
            tViewDescription = view.findViewById(R.id.tViewDescription);
            layout = view.findViewById(R.id.elemRecycleEquipment);
            btnDeleteEquipment =view.findViewById(R.id.btnDeleteEquipment);

            btnDeleteEquipment.setOnClickListener(this);

        }


        public void bind(ElementEquipment elementEquipment) {
            this.elementEquipment= elementEquipment;

            tViewName.setText(elementEquipment.getName());
            tViewCategory.setText("Тип: "+elementEquipment.getCategoryName());
            tViewType.setText("Подтип: "+elementEquipment.getTypeName());
            tViewPrice.setText("Цена: "+ elementEquipment.getPrice() + " " + elementEquipment.getTypeCoin());
            if (elementEquipment.getWight() == null)
                tViewWight.setText("Вес: "+"- фнт");else
                tViewWight.setText("Вес: "+elementEquipment.getWight() + " фнт");
            tViewDescription.setText(elementEquipment.getDescription());
            btnDeleteEquipment.setVisibility(View.VISIBLE);

        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btnDeleteEquipment:
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onDeleteClickBtn(position,elementEquipment.getId());
                        }
                    }

                    break;
            }


        }
    }
    public interface OnBtnClickListener {
        void onDeleteClickBtn(int position,int equip_id);
    }

}
