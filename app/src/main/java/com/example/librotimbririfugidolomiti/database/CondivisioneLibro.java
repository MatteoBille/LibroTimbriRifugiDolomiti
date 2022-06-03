package com.example.librotimbririfugidolomiti.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "CondivisioneLibro", primaryKeys = {"CodicePersonaProprietaria", "CodicePersonaVisualizzante"})
public class CondivisioneLibro {

    @NonNull
    @ColumnInfo(name = "CodicePersonaProprietaria")
    private String CodicePersonaProprietaria;

    @NonNull
    @ColumnInfo(name = "CodicePersonaVisualizzante")
    private String CodicePersonaVisualizzante;

    public CondivisioneLibro() {
    }

    public CondivisioneLibro(@NonNull String codicePersonaProprietaria, @NonNull String codicePersonaVisualizzante) {
        CodicePersonaProprietaria = codicePersonaProprietaria;
        CodicePersonaVisualizzante = codicePersonaVisualizzante;
    }

    @NonNull
    public String getCodicePersonaProprietaria() {
        return CodicePersonaProprietaria;
    }

    @NonNull
    public String getCodicePersonaVisualizzante() {
        return CodicePersonaVisualizzante;
    }

    public void setCodicePersonaProprietaria(@NonNull String codicePersonaProprietaria) {
        CodicePersonaProprietaria = codicePersonaProprietaria;
    }

    public void setCodicePersonaVisualizzante(@NonNull String codicePersonaVisualizzante) {
        CodicePersonaVisualizzante = codicePersonaVisualizzante;
    }
}
