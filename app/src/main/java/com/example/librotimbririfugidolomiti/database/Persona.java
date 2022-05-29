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
    @ColumnInfo(name = "Password")
    private String Password;

    public Persona() {
    }

    public Persona(@NonNull Integer codicePersona, @NonNull String nomeCognome, @NonNull String password) {
        CodicePersona = codicePersona;
        NomeCognome = nomeCognome;
        Password = password;
    }

    public void setCodicePersona(@NonNull Integer codicePersona) {
        CodicePersona = codicePersona;
    }

    public void setNomeCognome(@NonNull String nomeCognome) {
        NomeCognome = nomeCognome;
    }

    public void setPassword(@NonNull String password) {
        Password = password;
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
    public String getPassword() {
        return Password;
    }
}
