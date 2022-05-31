package com.example.librotimbririfugidolomiti.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface DatabasePersoneDao {

    @Query("SELECT * FROM Persone WHERE CodicePersona=:codicePersona")
    Persona getPersonById(Integer codicePersona);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Long insert(Persona persona);



    @Query("SELECT COUNT(*) FROM Persone")
    Integer getNumberOfUsers();
}
