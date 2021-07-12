package com.example.halperdnd.database.modell;

import java.util.Objects;

public class ElementSpellsTable {
    private int id;
    private int source_id;
    private int class_id;
    private int school_id;
    private int level_id;

    private boolean check=false;

    private String components;
    private String distance;

    public int getId() {
        return id;
    }

    public int getSource_id() {
        return source_id;
    }

    public int getClass_id() {
        return class_id;
    }

    public int getSchool_id() {
        return school_id;
    }

    public int getLevel_id() {
        return level_id;
    }

    public String getComponents() {
        return components;
    }

    public void setCheck(boolean check) { this.check = check; }

    public boolean isCheck() { return check; }

    public String getDistance() {
        return distance;
    }

    public String getDuration() {
        return duration;
    }

    public String getMatComponrnts() {
        return matComponrnts;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getTimeCast() {
        return timeCast;
    }

    private String duration;
    private String matComponrnts;
    private String description;
    private String name;
    private String timeCast;

    public String getStLevel() {
        return stLevel;
    }

    public String getStSource() {
        return stSource;
    }

    public String getStSchool() {
        return stSchool;
    }

    private String stLevel;
    private String stSource;
    private String stSchool;

    public ElementSpellsTable(int id, int source_id, int school_id, int level_id, String components,
                              String distance, String duration, String matComponrnts, String description,
                              String name, String timeCast, String stLevel, String stSource, String stSchool) {

        this.id = id;
        this.source_id = source_id;
        this.school_id = school_id;
        this.level_id = level_id;
        this.components = components;
        this.distance = distance;
        this.duration = duration;
        this.matComponrnts = matComponrnts;
        this.description = description;
        this.name = name;
        this.timeCast = timeCast;
        this.stLevel = stLevel;
        this.stSource = stSource;
        this.stSchool = stSchool;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElementSpellsTable that = (ElementSpellsTable) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
