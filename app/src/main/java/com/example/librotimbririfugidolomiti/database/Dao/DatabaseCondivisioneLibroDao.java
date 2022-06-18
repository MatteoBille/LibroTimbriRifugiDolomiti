package com.example.librotimbririfugidolomiti.database.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.librotimbririfugidolomiti.database.Entity.CondivisioneLibro;

import java.util.List;

@Dao
public interface DatabaseCondivisioneLibroDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Void insert(CondivisioneLibro entityCondivisioneLibro);

    @Query("SELECT * FROM CondivisioneLibro WHERE CodicePersonaVisualizzante=:codicePersona")
    LiveData<List<CondivisioneLibro>> getObtainedBookAsync(String codicePersona);


    @Query("SELECT * FROM CondivisioneLibro WHERE CodicePersonaVisualizzante=:codicePersona")
    List<CondivisioneLibro> getObtainedBook(String codicePersona);

}
