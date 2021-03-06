package com.example.librotimbririfugidolomiti.database.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.librotimbririfugidolomiti.database.Entity.Persona;

import java.util.List;

@Dao
public interface DatabasePersoneDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Persona persona);

    @Query("SELECT * FROM Persone WHERE CodicePersona=:codicePersona AND Local=:local")
    Persona getPersonById(String codicePersona, String local);

    @Query("SELECT * FROM Persone WHERE CodicePersona=:codicePersona")
    Persona getPersonById(String codicePersona);

    @Query("SELECT * FROM Persone WHERE Local=:local")
    LiveData<List<Persona>> getAllPeople(String local);

    @Query("SELECT CodicePersona FROM Persone WHERE Local=:local")
    List<String> getAllPeopleIDs(String local);
}

