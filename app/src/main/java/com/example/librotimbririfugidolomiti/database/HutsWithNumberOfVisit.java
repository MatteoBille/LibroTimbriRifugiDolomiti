package com.example.librotimbririfugidolomiti.database;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;

public class HutsWithNumberOfVisit {

    @Embedded
    Rifugio rifugio;

    @ColumnInfo(name = "Visite")
    Integer Visite;

    public Rifugio getRifugio() {
        return rifugio;
    }

    public Integer getVisite() {
        return Visite;
    }

    @Override
    public String toString() {
        return "HutsWithNumberOfVisit{" +
                "rifugio=" + rifugio +
                ", count=" + Visite +
                '}';
    }
}
