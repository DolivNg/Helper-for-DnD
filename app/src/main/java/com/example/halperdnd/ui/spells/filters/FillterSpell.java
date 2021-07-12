package com.example.halperdnd.ui.spells.filters;

import android.provider.BaseColumns;
import android.widget.Filter;

import java.util.List;

public class FillterSpell extends Filter {

    protected OptionFilterSpell optionFilterSpell = null;//= new OptionFilterSpell();
    protected boolean switchSpell = false;

    public void setOptionFilterSpell(OptionFilterSpell optionFilterSpell) {
        this.optionFilterSpell = optionFilterSpell;
    }
    public void setSwitchSpell(boolean switchSpell) {
        this.switchSpell = switchSpell;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        return null;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {


    }
}
