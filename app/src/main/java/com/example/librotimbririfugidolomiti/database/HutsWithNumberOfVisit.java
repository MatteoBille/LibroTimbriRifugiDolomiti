package com.example.librotimbririfugidolomiti.database;

import androidx.room.Embedded;

public class HutsWithNumberOfVisit {

    @Embedded
    Rifugio rifugio;

    Integer count;

    public Rifugio getRifugio() {
        return rifugio;
    }

    public Integer getCount() {
        return count;
    }
}
