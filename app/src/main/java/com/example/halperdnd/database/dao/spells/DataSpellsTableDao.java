package com.example.halperdnd.database.dao.spells;

import com.example.halperdnd.database.modell.ElementSpellsTable;
import com.example.halperdnd.database.modell.ElemetProfile;
import com.example.halperdnd.database.modell.SimpleTable;
import com.example.halperdnd.database.modell.SpellAndClass;

import java.util.List;

public interface DataSpellsTableDao {
    List<ElementSpellsTable> loadSpellsTable();
    List<ElemetProfile> loadProfill();
    void insertProfil(String name);
    void deleteElementFromProfile(int profileId,int spell_id);
    void addElementInProfile(int profileId,int spell_id);
    List<Integer> selectElementFromProfile(int profileId);
    void deleteProfile(int profileId);
    void deleteAllElementFromProfile(int profileId);
    List<String> getClassNames();
    List<String> getSchoolNames();
    List<String> getSourceNames();
    SpellAndClass getSpellAndClass(int id);
}
