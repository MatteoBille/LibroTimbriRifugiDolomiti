package com.example.librotimbririfugidolomiti.database.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.librotimbririfugidolomiti.database.Entity.HutGroup;
import com.example.librotimbririfugidolomiti.database.Entity.Rifugio;

import java.util.List;

@Dao
public interface DatabaseRifugiDao {

    @Query("SELECT * FROM Rifugi")
    LiveData<List<Rifugio>> getAllHut();

    @Query("SELECT * FROM Rifugi where CodiceRifugio = :hutId")
    LiveData<Rifugio> getHutById(int hutId);

    @Query("SELECT COUNT(CodiceRifugio) FROM Rifugi")
    Integer getNumberOfHut();

    @Query("SELECT COUNT(CodiceRifugio) as hutNumber,GruppoDolomitico FROM Rifugi GROUP BY GruppoDolomitico ORDER BY CodiceRifugio")
    List<HutGroup> getNumberOfHutforEachDolomitcGroup();

    @Query("SELECT * FROM Rifugi WHERE GruppoDolomitico=:groupName")
    List<Rifugio> getListOfHutByDolomiticGroup(String groupName);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Rifugio entityHut);

    @Query("SELECT CodiceRifugio FROM Rifugi")
    List<Integer> getHutIds();
}
