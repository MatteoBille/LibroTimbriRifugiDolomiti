package com.example.librotimbririfugidolomiti.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RifugiDao {

    // allowing the insert of the same word multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Rifugio rifugio);

    @Query("DELETE FROM Rifugi")
    void deleteAll();

    @Query("SELECT * FROM Rifugi")
    LiveData<List<Rifugio>> getAllHut();

    @Query("SELECT COUNT(DISTINCT GruppoDolomitico) FROM Rifugi")
    LiveData<Integer> getNumberOfDolomiticGroups();

    @Query("SELECT DISTINCT GruppoDolomitico FROM Rifugi")
    LiveData<List<String>> getListOfDolomiticGroups();

    @Query("SELECT * FROM Rifugi where CodiceRifugio = :hutId")
    LiveData<Rifugio> getHutById(int hutId);

    @Query("SELECT COUNT(CodiceRifugio) FROM Rifugi")
    Integer getNumberOfHut();
}