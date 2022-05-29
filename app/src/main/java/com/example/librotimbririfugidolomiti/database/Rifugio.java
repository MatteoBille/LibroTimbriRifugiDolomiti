package com.example.librotimbririfugidolomiti.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Rifugi")
public class Rifugio {


    @ColumnInfo(name = "Latitudine")
    private Double Latitudine;

    @ColumnInfo(name = "Longitudine")
    private Double Longitudine;

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "CodiceRifugio")
    private Integer CodiceRifugio;

    @NonNull
    @ColumnInfo(name = "NomeRifugio")
    private String NomeRifugio;

    @NonNull
    @ColumnInfo(name = "GruppoDolomitico")
    private String GruppoDolomitico;

    @NonNull
    @ColumnInfo(name = "NomeImmagine")
    private String NomeImmagine;

    public Rifugio() {
    }

    public Rifugio(Double latitudine, Double longitudine, Integer codiceRifugio, String nomeRifugio, String gruppoDolomitico, String nomeImmagine) {
        CodiceRifugio = codiceRifugio;
        NomeRifugio = nomeRifugio;
        Latitudine = latitudine;
        Longitudine = longitudine;
        GruppoDolomitico = gruppoDolomitico;
        NomeImmagine = nomeImmagine;
    }

    public void setCodiceRifugio(Integer codiceRifugio) {
        CodiceRifugio = codiceRifugio;
    }

    public void setNomeRifugio(String nomeRifugio) {
        NomeRifugio = nomeRifugio;
    }

    public void setLatitudine(Double latitudine) {
        Latitudine = latitudine;
    }

    public void setLongitudine(Double longitudine) {
        Longitudine = longitudine;
    }

    public void setGruppoDolomitico(String gruppoDolomitico) { GruppoDolomitico = gruppoDolomitico;}

    public void setNomeImmagine(String nomeImmagine) {
        NomeImmagine = nomeImmagine;
    }

    public Integer getCodiceRifugio() {
        return CodiceRifugio;
    }

    public String getNomeRifugio() {
        return NomeRifugio;
    }

    public Double getLatitudine() {
        return Latitudine;
    }

    public Double getLongitudine() {
        return Longitudine;
    }

    public String getGruppoDolomitico() {
        return GruppoDolomitico;
    }

    public String getNomeImmagine() {
        return NomeImmagine;
    }
}
