package com.example.halperdnd.database.dao.simple_table;

import com.example.halperdnd.database.modell.ElemetSimpleTable;
import com.example.halperdnd.database.modell.SimpleTable;

import java.util.List;

public interface DataSimpleTableDao {
    List<ElemetSimpleTable> loadSimpleTableElement(String table);
    List<SimpleTable> loadSimpleTable();
}
