package com.example.halperdnd.ui.spells.filters;

import com.example.halperdnd.database.modell.SpellAndClass;

import java.util.List;

public class OptionFilterSpell {

    private List<Boolean> spellLevel ;
    private List<Boolean> spellSchool;
    private List<Boolean> spellSource;
    private boolean boolSpellLevel = false;
    private boolean boolspellSchool = false;
    private boolean boolspellSource = false;
    private int chooseClass;
    private SpellAndClass spellAndClass;

    public void setSpellAndClass(SpellAndClass spellAndClass) {this.spellAndClass = spellAndClass; }

    public SpellAndClass getSpellAndClass() {return spellAndClass;}
    public List<Boolean> getSpellLevel() {
        return spellLevel;
    }

    public List<Boolean> getSpellSchool() {
        return spellSchool;
    }

    public List<Boolean> getSpellSource() {
        return spellSource;
    }

    public int getChooseClass() {
        return chooseClass;
    }

    public boolean isBoolSpellLevel() {
        return boolSpellLevel;
    }

    public boolean isBoolspellSchool() {
        return boolspellSchool;
    }

    public boolean isBoolspellSource() {
        return boolspellSource;
    }

    public OptionFilterSpell() {
    }
    public OptionFilterSpell(List<Boolean> spellLevel, List<Boolean> spellSchool, List<Boolean> spellSource, int chooseClass) {
        this.spellLevel = spellLevel;
        this.spellSchool = spellSchool;
        this.spellSource = spellSource;
        this.chooseClass = chooseClass;
        for (boolean i:spellLevel){
            if (i)
            {boolSpellLevel =true;
            break;}
        }
        for (boolean i:spellSchool){
            if (i)
            {boolspellSchool =true;
                break;}
        }
        for (boolean i:spellSource){
            if (i)
            {boolspellSource =true;
                break;}
        }


    }
}