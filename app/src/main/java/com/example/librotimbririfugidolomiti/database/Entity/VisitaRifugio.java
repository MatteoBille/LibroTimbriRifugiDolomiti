package com.example.librotimbririfugidolomiti.database.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity(tableName = "VisiteRifugi", primaryKeys = {"CodicePersona", "CodiceRifugio", "DataVisita"})
public class VisitaRifugio {

    @NonNull
    @ColumnInfo(name = "CodiceRifugio")
    private Integer CodiceRifugio;

    @NonNull
    @ColumnInfo(name = "CodicePersona")
    private String CodicePersona;

    @NonNull
    @ColumnInfo(name = "DataVisita")
    private String DataVisita;

    @ColumnInfo(name = "Info")
    private String Info;

    @ColumnInfo(name = "Rating")
    private Integer Rating;

    public VisitaRifugio() {
    }

    public VisitaRifugio(@NonNull Integer codiceRifugio, @NonNull String codicePersona, @NonNull String dataVisita, @NonNull String info, @NonNull Integer rating) {
        CodiceRifugio = codiceRifugio;
        CodicePersona = codicePersona;
        DataVisita = dataVisita;
        Info = info;
        Rating = rating;
    }

    public VisitaRifugio(@NonNull Integer codiceRifugio, @NonNull String codicePersona, @NonNull String dataVisita) {
        CodiceRifugio = codiceRifugio;
        CodicePersona = codicePersona;
        DataVisita = dataVisita;
    }

    public VisitaRifugio(@NonNull Integer codiceRifugio, @NonNull String codicePersona, @NonNull String dataVisita, String info) {
        CodiceRifugio = codiceRifugio;
        CodicePersona = codicePersona;
        DataVisita = dataVisita;
        Info = info;
    }

    public VisitaRifugio(@NonNull Integer codiceRifugio, @NonNull String codicePersona, @NonNull String dataVisita, Integer rating) {
        CodiceRifugio = codiceRifugio;
        CodicePersona = codicePersona;
        DataVisita = dataVisita;
        Rating = rating;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("CodiceRifugio", CodiceRifugio);
        result.put("DataVisita", DataVisita);
        result.put("Info", Info);
        result.put("Rating", Rating);

        return result;
    }

    public void setCodiceRifugio(@NonNull Integer codiceRifugio) {
        CodiceRifugio = codiceRifugio;
    }

    public void setCodicePersona(@NonNull String codicePersona) {
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
    public String getCodicePersona() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VisitaRifugio that = (VisitaRifugio) o;
        return CodiceRifugio.equals(that.CodiceRifugio) && CodicePersona.equals(that.CodicePersona) && DataVisita.equals(that.DataVisita) && Objects.equals(Info, that.Info) && Objects.equals(Rating, that.Rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(CodiceRifugio, CodicePersona, DataVisita, Info, Rating);
    }

    @Override
    public String toString() {
        return "VisitaRifugio{" +
                "CodiceRifugio=" + CodiceRifugio +
                ", CodicePersona='" + CodicePersona + '\'' +
                ", DataVisita='" + DataVisita + '\'' +
                ", Info='" + Info + '\'' +
                ", Rating=" + Rating +
                '}';
    }
}
