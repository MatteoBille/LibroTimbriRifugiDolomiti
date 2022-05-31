package com.example.librotimbririfugidolomiti.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DatabaseVisiteRifugiDao {

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

    @Query("SELECT * FROM VisiteRifugi WHERE CodiceRifugio=:codiceRifugio AND CodicePersona=:codicePersona")
    List<VisitaRifugio> getVisitsByHutAndPerson(Integer codiceRifugio,Integer codicePersona);

    @Query("INSERT INTO VisiteRifugi(CodicePersona,CodiceRifugio,DataVisita) VALUES(:codicePersona,:codiceRifugio,:dataVisita)")
    void visitHut(Integer codicePersona,Integer codiceRifugio,String dataVisita);
    @Query("INSERT INTO VisiteRifugi(CodicePersona,CodiceRifugio,DataVisita,Info) VALUES(:codicePersona,:codiceRifugio,:dataVisita,:info)")
    void visitHut(Integer codicePersona,Integer codiceRifugio,String dataVisita,String info);
    @Query("INSERT INTO VisiteRifugi(CodicePersona,CodiceRifugio,DataVisita,Rating) VALUES(:codicePersona,:codiceRifugio,:dataVisita,:rating)")
    void visitHut(Integer codicePersona,Integer codiceRifugio,String dataVisita,Integer rating);
    @Query("INSERT INTO VisiteRifugi(CodicePersona,CodiceRifugio,DataVisita,Info,Rating) VALUES(:codicePersona,:codiceRifugio,:dataVisita,:info,:rating)")
    void visitHut(Integer codicePersona,Integer codiceRifugio,String dataVisita,String info,Integer rating);

    @Query("SELECT COUNT(VisiteRifugi.CodiceRifugio) FROM VisiteRifugi WHERE CodicePersona=:codicePersona AND CodiceRifugio=:codiceRifugio")
    Integer isVisited(int codicePersona, Integer codiceRifugio);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Long insert(VisitaRifugio visitaRifugio);
}