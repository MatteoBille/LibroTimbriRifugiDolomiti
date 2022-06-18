package com.example.librotimbririfugidolomiti.database.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;

public class HutsWithNumberOfVisit {

    @Embedded
    Rifugio entityHut;

    @ColumnInfo(name = "Visite")
    Integer Visite;

    public Rifugio getRifugio() {
        return entityHut;
    }

    public Integer getVisite() {
        return Visite;
    }


    public void setEntityHut(Rifugio entityHut) {
        this.entityHut = entityHut;
    }

    public void setVisite(Integer visite) {
        Visite = visite;
    }

    @Override
    public String toString() {
        return "HutsWithNumberOfVisit{" +
                "rifugio=" + entityHut +
                ", count=" + Visite +
                '}';
    }
}
