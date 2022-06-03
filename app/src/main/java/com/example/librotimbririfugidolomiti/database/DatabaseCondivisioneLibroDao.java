package com.example.librotimbririfugidolomiti.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DatabaseCondivisioneLibroDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Void insert(CondivisioneLibro condivisioneLibro);

    @Query("SELECT * FROM CondivisioneLibro WHERE CodicePersonaVisualizzante=:codicePersona")
    List<CondivisioneLibro> getObtainedBook(String codicePersona);

}
