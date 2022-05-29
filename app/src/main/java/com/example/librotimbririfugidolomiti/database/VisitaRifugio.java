package com.example.librotimbririfugidolomiti.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="VisiteRifugi",primaryKeys = {"CodicePersona","CodiceRifugio","DataVisita"})
public class VisitaRifugio {

    @NonNull
    @ColumnInfo(name = "CodiceRifugio")
    private Integer CodiceRifugio;

    @NonNull
    @ColumnInfo(name = "CodicePersona")
    private Integer CodicePersona;

    @NonNull
    @ColumnInfo(name = "DataVisita")
    private String DataVisita;

    @ColumnInfo(name = "Info")
    private String Info;

    @ColumnInfo(name = "Rating")
    private Integer Rating;

    public VisitaRifugio() {
    }

    public VisitaRifugio(@NonNull Integer codiceRifugio, @NonNull Integer codicePersona, @NonNull String dataVisita, @NonNull String info, @NonNull Integer rating) {
        CodiceRifugio = codiceRifugio;
        CodicePersona = codicePersona;
        DataVisita = dataVisita;
        Info = info;
        Rating = rating;
    }

    public VisitaRifugio(@NonNull Integer codiceRifugio, @NonNull Integer codicePersona, @NonNull String dataVisita) {
        CodiceRifugio = codiceRifugio;
        CodicePersona = codicePersona;
        DataVisita = dataVisita;
    }

    public VisitaRifugio(@NonNull Integer codiceRifugio, @NonNull Integer codicePersona, @NonNull String dataVisita, String info) {
        CodiceRifugio = codiceRifugio;
        CodicePersona = codicePersona;
        DataVisita = dataVisita;
        Info = info;
    }

    public VisitaRifugio(@NonNull Integer codiceRifugio, @NonNull Integer codicePersona, @NonNull String dataVisita, Integer rating) {
        CodiceRifugio = codiceRifugio;
        CodicePersona = codicePersona;
        DataVisita = dataVisita;
        Rating = rating;
    }

    public void setCodiceRifugio(@NonNull Integer codiceRifugio) {
        CodiceRifugio = codiceRifugio;
    }

    public void setCodicePersona(@NonNull Integer codicePersona) {
        CodicePersona = codicePersona;
    }

    public void setDataVisita(@NonNull String dataVisita) {
        DataVisita = dataVisita;
    }

    public void setInfo(@NonNull String info) {
        Info = info;
    }

    public void setRating(@NonNull Integer rating) {
        Rating = rating;
    }

    @NonNull
    public Integer getCodiceRifugio() {
        return CodiceRifugio;
    }

    @NonNull
    public Integer getCodicePersona() {
        return CodicePersona;
    }

    @NonNull
    public String getDataVisita() {
        return DataVisita;
    }

    @NonNull
    public String getInfo() {
        return Info;
    }

    @NonNull
    public Integer getRating() {
        return Rating;
    }
}
