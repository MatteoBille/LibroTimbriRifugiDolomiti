package com.example.librotimbririfugidolomiti.database.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity(tableName = "Persone")
public class Persona {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "CodicePersona")
    private String CodicePersona;

    @NonNull
    @ColumnInfo(name = "NomeCognome")
    private String NomeCognome;

    @NonNull
    @ColumnInfo(name = "Email")
    private String Email;

    @NonNull
    @ColumnInfo(name = "Local")
    private String Local;

    public Persona() {
    }

    public Persona(@NonNull String codicePersona, @NonNull String nomeCognome, @NonNull String email, @NonNull String local) {
        CodicePersona = codicePersona;
        NomeCognome = nomeCognome;
        Email = email;
        Local = local;
    }

    public Persona(@NonNull String codicePersona, @NonNull String nomeCognome, @NonNull String email) {
        CodicePersona = codicePersona;
        NomeCognome = nomeCognome;
        Email = email;
        Local = "true";
    }

    public Persona(@NonNull String nomeCognome, @NonNull String email) {
        NomeCognome = nomeCognome;
        Email = email;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("NomeCognome", NomeCognome);
        result.put("Email", Email);

        return result;
    }

    public void setCodicePersona(@NonNull String codicePersona) {
        CodicePersona = codicePersona;
    }

    public void setNomeCognome(@NonNull String nomeCognome) {
        NomeCognome = nomeCognome;
    }

    public void setEmail(@NonNull String email) {
        Email = email;
    }

    public void setLocal(@NonNull String local) {
        Local = local;
    }

    @NonNull
    public String getCodicePersona() {
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

    @NonNull
    public String getLocal() {
        return Local;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Persona persona = (Persona) o;
        return CodicePersona.equals(persona.CodicePersona);
    }

    @Override
    public int hashCode() {
        return Objects.hash(CodicePersona);
    }

    @Override
    public String toString() {
        return "Persona{" +
                "CodicePersona='" + CodicePersona + '\'' +
                ", NomeCognome='" + NomeCognome + '\'' +
                ", Email='" + Email + '\'' +
                ", Local='" + Local + '\'' +
                '}';
    }
}
