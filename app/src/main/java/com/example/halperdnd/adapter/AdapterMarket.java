package com.example.halperdnd.adapter;

import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
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
import com.example.halperdnd.database.modell.ElementMarket;

import java.util.ArrayList;
import java.util.List;

public class AdapterMarket extends RecyclerView.Adapter<AdapterMarket.AdapterMarketViewHolder> {

    private List<AdapterMarket.AdapterMarketViewHolder> marketTableViewHolders = new ArrayList<>();
    private List<ElementMarket> elementMarkets = new ArrayList<>();
    final OnBtnClickListener listener;

    public AdapterMarket(List<ElementMarket> elementMarkets, OnBtnClickListener listener) {
        this.elementMarkets = elementMarkets;
        this.listener = listener;
    }

    private NavController navController;

    public void setNavController(NavController navController) {
        this.navController = navController;
    }

    public void setDataEquipment(DataEquipmentImpl dataEquipment) {
        this.dataEquipment = dataEquipment;
    }

    private DataEquipmentImpl dataEquipment;
    public void setElementMarkets(List<ElementMarket> elementMarkets) {
        this.elementMarkets = elementMarkets;
    }

    @NonNull
    @Override
    public AdapterMarketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycle_market, parent, false);
        AdapterMarket.AdapterMarketViewHolder d = new AdapterMarket.AdapterMarketViewHolder(view,listener);
        marketTableViewHolders.add(d);
        return d;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMarketViewHolder holder, int position) {
        holder.bind(elementMarkets.get(position));
    }

    @Override
    public int getItemCount() {
        return elementMarkets.size();
    }

     class AdapterMarketViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tVMarketName;
        TextView tVMarketCity;
        Button btnMarketDelete;

        ElementMarket elementMarket;
        LinearLayout layout;
        View view ;

         final OnBtnClickListener listener;
        public AdapterMarketViewHolder(@NonNull View itemView ,final OnBtnClickListener listener) {
            super(itemView);
            this.listener = listener;
            view = itemView;
            tVMarketName = view.findViewById(R.id.tVMarketName);
            tVMarketCity = view.findViewById(R.id.tVMarketCity);

            layout = view.findViewById(R.id.layoutRecycleMarket);
            layout.setOnClickListener(this);
            btnMarketDelete =view.findViewById(R.id.btnMarketDelete);
            btnMarketDelete.setOnClickListener(this);
        }

        public void bind(ElementMarket elementMarket) {

            tVMarketName.setText(elementMarket.getNameMarket());
            tVMarketCity.setText("Город: "+elementMarket.getCityMarket());
            this.elementMarket = elementMarket;
        }

        @Override
        public void onClick(View v) {
            switch(v.getId())
            {
                case R.id.btnMarketDelete:
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onDeleteClickBtn(position,elementMarket.get_id());
                        }
                    }
                    break;
                case R.id.layoutRecycleMarket:
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onNavigateClickBtn(elementMarket.get_id());
                        }
                    }
                    break;
            }
        }
    }
    public interface OnBtnClickListener {

        void onDeleteClickBtn(int position, int id);
        void onNavigateClickBtn(int id);
    }

}
