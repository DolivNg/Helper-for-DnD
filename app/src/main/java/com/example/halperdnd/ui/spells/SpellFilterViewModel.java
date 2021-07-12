package com.example.halperdnd.ui.spells;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.halperdnd.ui.spells.filters.OptionFilterSpell;

import java.util.Set;
/**Class "DataFilter"*/
public class SpellFilterViewModel extends ViewModel {
    private final MutableLiveData<OptionFilterSpell> selected = new MutableLiveData<OptionFilterSpell>();
    /**Set*/
    public void select(OptionFilterSpell item) {
        selected.setValue(item);
    }
    /**get*/
    public LiveData<OptionFilterSpell> getSelected() {
        return selected;
    }
}
