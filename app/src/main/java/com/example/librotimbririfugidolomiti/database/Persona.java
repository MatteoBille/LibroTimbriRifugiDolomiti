package com.example.librotimbririfugidolomiti.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Persone")
public class Persona {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "CodicePersona")
    private Integer CodicePersona;

    @NonNull
    @ColumnInfo(name = "NomeCognome")
    private String NomeCognome;

    @NonNull
    @ColumnInfo(name = "Email")
    private String Email;

    public Persona() {
    }

    public Persona(@NonNull String nomeCognome, @NonNull String email) {
        NomeCognome = nomeCognome;
        Email = email;
    }

    public void setCodicePersona(@NonNull Integer codicePersona) {
        CodicePersona = codicePersona;
    }

    public void setNomeCognome(@NonNull String nomeCognome) {
        NomeCognome = nomeCognome;
    }

    public void setEmail(@NonNull String email) {
        Email = email;
    }

    @NonNull
    public Integer getCodicePersona() {
        return CodicePersona;
    }

    @NonNull
    public String getNomeCognome() {
        return NomeCognome;
    }

    @NonNull
    public String getEmail() {
        return Email;
    }
}
