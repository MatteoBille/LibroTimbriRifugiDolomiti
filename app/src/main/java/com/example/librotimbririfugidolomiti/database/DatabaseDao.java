package com.example.librotimbririfugidolomiti.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DatabaseDao {

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

    @Query("SELECT COUNT(DISTINCT CodiceRifugio ) FROM VisiteRifugi  WHERE CodicePersona=:codicePersona")
    Integer getNumberOfHutVisited(Integer codicePersona);

    @Query("SELECT MAX(DataVisita) FROM VisiteRifugi WHERE CodicePersona=1")
    String getLastVisitDay();

    @Query("SELECT COUNT(CodiceRifugio) as hutNumber,GruppoDolomitico FROM Rifugi GROUP BY GruppoDolomitico ORDER BY CodiceRifugio")
    List<HutGroup> getNumberOfHutforEachDolomitcGroup();

    @Query("SELECT * FROM Rifugi WHERE GruppoDolomitico=:groupName")
    List<Rifugio> getListOfHutByDolomiticGroup(String groupName);

    @Query("SELECT COUNT(*) FROM VisiteRifugi WHERE CodiceRifugio=:codiceRifugio AND CodicePersona=:codicePersona")
    Integer getNumberOfVisitByHut(Integer codiceRifugio,Integer codicePersona);

    @Query("SELECT COUNT(*) FROM Persone")
    Integer getNumberOfUsers();

    @Query("SELECT * FROM Persone WHERE CodicePersona=:codicePersona")
    Persona getPersonById(Integer codicePersona);



    @Query("INSERT INTO VisiteRifugi(CodicePersona,CodiceRifugio,DataVisita) VALUES(:codicePersona,:codiceRifugio,:dataVisita)")
    void visitHut(Integer codicePersona,Integer codiceRifugio,String dataVisita);
    @Query("INSERT INTO VisiteRifugi(CodicePersona,CodiceRifugio,DataVisita,Info) VALUES(:codicePersona,:codiceRifugio,:dataVisita,:info)")
    void visitHut(Integer codicePersona,Integer codiceRifugio,String dataVisita,String info);
    @Query("INSERT INTO VisiteRifugi(CodicePersona,CodiceRifugio,DataVisita,Rating) VALUES(:codicePersona,:codiceRifugio,:dataVisita,:rating)")
    void visitHut(Integer codicePersona,Integer codiceRifugio,String dataVisita,Integer rating);
    @Query("INSERT INTO VisiteRifugi(CodicePersona,CodiceRifugio,DataVisita,Info,Rating) VALUES(:codicePersona,:codiceRifugio,:dataVisita,:info,:rating)")
    void visitHut(Integer codicePersona,Integer codiceRifugio,String dataVisita,String info,Integer rating);


    @Query("INSERT INTO Persone(NomeCognome,Email) VALUES(:nomeCognome,:email)")
    void insertPerson(String nomeCognome,String email);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Long insert(Persona persona);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Long insert(VisitaRifugio visitaRifugio);

    @Query("SELECT COUNT(VisiteRifugi.CodiceRifugio) FROM Persone INNER JOIN  VisiteRifugi ON Persone.CodicePersona=VisiteRifugi.CodicePersona INNER JOIN Rifugi ON VisiteRifugi.CodiceRifugio = Rifugi.CodiceRifugio WHERE Persone.CodicePersona=:codicePersona AND Rifugi.CodiceRifugio=:codiceRifugio")
    Integer isVisited(int codicePersona, Integer codiceRifugio);
}