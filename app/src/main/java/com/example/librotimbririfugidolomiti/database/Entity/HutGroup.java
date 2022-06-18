package com.example.librotimbririfugidolomiti.database.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "Rifugi")
public class HutGroup {

    @NonNull
    @ColumnInfo(name = "hutNumber")
    private Integer hutNumber;

    @NonNull
    @ColumnInfo(name = "GruppoDolomitico")
    private String GruppoDolomitico;


    public HutGroup(){

    }
    public HutGroup(Integer hutNumber, String gruppoDolomitico) {
        this.hutNumber = hutNumber;
        GruppoDolomitico = gruppoDolomitico;
    }

    public Integer getHutNumber() {
        return hutNumber;
    }

    public void setHutNumber(Integer hutNumber) {
        this.hutNumber = hutNumber;
    }

    public String getGruppoDolomitico() {
        return GruppoDolomitico;
    }

    public void setGruppoDolomitico(String gruppoDolomitico) {
        GruppoDolomitico = gruppoDolomitico;
    }
}
