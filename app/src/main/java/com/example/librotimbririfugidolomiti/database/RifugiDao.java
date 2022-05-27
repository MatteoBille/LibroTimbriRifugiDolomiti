package com.example.librotimbririfugidolomiti.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

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
    Rifugio getHutById(int hutId);

    @Query("SELECT COUNT(CodiceRifugio) FROM Rifugi")
    Integer getNumberOfHut();

    @Query("SELECT COUNT(CodiceRifugio) FROM Rifugi WHERE Visitato==1")
    Integer getNumberOfHutVisited();

    @Query("SELECT MAX(DataVisita) FROM Rifugi WHERE Visitato==1")
    String getLastVisitDay();

    @Query("SELECT COUNT(CodiceRifugio) as hutNumber,GruppoDolomitico FROM Rifugi GROUP BY GruppoDolomitico ORDER BY CodiceRifugio")
    List<HutGroup> getNumberOfHutforEachDolomitcGroup();

    @Query("SELECT * FROM Rifugi WHERE GruppoDolomitico=:groupName")
    List<Rifugio> getListOfHutByDolomiticGroup(String groupName);


    @Query("UPDATE Rifugi SET Visitato=1,DataVisita=:dataVisita WHERE CodiceRifugio=:idRifugio")
    Integer setVisitTrueAndDate(String dataVisita, Integer idRifugio);

}