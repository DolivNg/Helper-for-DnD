package com.example.halperdnd.database.modell;

import java.util.List;

public class SpellAndClass {
    private int idClass;
    private List<Integer> listIdSpell;

    public SpellAndClass(int idClass, List<Integer> listIdSpell) {
        this.idClass = idClass;
        this.listIdSpell = listIdSpell;
    }

    public int getIdClass() {
        return idClass;
    }

    public List<Integer> getListIdSpell() {
        return listIdSpell;
    }
}
