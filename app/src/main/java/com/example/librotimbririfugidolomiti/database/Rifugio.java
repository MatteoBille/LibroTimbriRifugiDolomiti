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

    @NonNull
    @ColumnInfo(name = "Visitato",defaultValue="0")
    private Integer Visitato;

    @ColumnInfo(name = "DataVisita")
    private String DataVisita;

    public Rifugio() {
    }

    public Rifugio(Double latitudine, Double longitudine, Integer codiceRifugio, String nomeRifugio, String gruppoDolomitico, String nomeImmagine, Integer visitato, String dataVisita) {
        CodiceRifugio = codiceRifugio;
        NomeRifugio = nomeRifugio;
        Latitudine = latitudine;
        Longitudine = longitudine;
        GruppoDolomitico = gruppoDolomitico;
        NomeImmagine = nomeImmagine;
        Visitato = visitato;
        DataVisita = dataVisita;
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

    public void setGruppoDolomitico(String gruppoDolomitico) {
        GruppoDolomitico = gruppoDolomitico;
    }

    public void setNomeImmagine(String nomeImmagine) {
        NomeImmagine = nomeImmagine;
    }

    public void setVisitato(Integer visitato) {
        Visitato = visitato;
    }

    public void setDataVisita(String dataVisita) {
        DataVisita = dataVisita;
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

    public Integer getVisitato() {
        return Visitato;
    }

    public String getDataVisita() {
        return DataVisita;
    }
}
