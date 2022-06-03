package com.example.librotimbririfugidolomiti.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DatabaseVisiteRifugiDao {


    @Query("SELECT COUNT(DISTINCT CodiceRifugio ) FROM VisiteRifugi  WHERE CodicePersona=:codicePersona")
    Integer getNumberOfHutVisited(String codicePersona);

    @Query("SELECT MAX(DataVisita) FROM VisiteRifugi WHERE CodicePersona=:codicePersona")
    String getLastVisitDay(String codicePersona);


    @Query("SELECT COUNT(CodiceRifugio) as hutNumber,GruppoDolomitico FROM Rifugi GROUP BY GruppoDolomitico ORDER BY CodiceRifugio")
    List<HutGroup> getNumberOfHutforEachDolomitcGroup();

    @Query("SELECT * FROM Rifugi WHERE GruppoDolomitico=:groupName")
    List<Rifugio> getListOfHutByDolomiticGroup(String groupName);

    @Query("SELECT COUNT(*) FROM VisiteRifugi WHERE CodiceRifugio=:codiceRifugio AND CodicePersona=:codicePersona")
    Integer getNumberOfVisitByHut(Integer codiceRifugio, String codicePersona);

    @Query("SELECT * FROM VisiteRifugi WHERE CodiceRifugio=:codiceRifugio AND CodicePersona=:codicePersona AND DataVisita=:dataVisita")
    VisitaRifugio getVisitsByHutPersonAndDate(Integer codiceRifugio, String codicePersona, String dataVisita);

    @Query("SELECT * FROM VisiteRifugi WHERE CodiceRifugio=:codiceRifugio AND CodicePersona=:codicePersona")
    List<VisitaRifugio> getVisitsByHutAndPerson(Integer codiceRifugio, String codicePersona);

    @Query("SELECT * FROM VisiteRifugi WHERE CodicePersona=:codicePersona")
    List<VisitaRifugio> getVisitsByPerson(String codicePersona);

    @Query("INSERT INTO VisiteRifugi(CodicePersona,CodiceRifugio,DataVisita) VALUES(:codicePersona,:codiceRifugio,:dataVisita)")
    void visitHut(String codicePersona, Integer codiceRifugio, String dataVisita);

    @Query("INSERT INTO VisiteRifugi(CodicePersona,CodiceRifugio,DataVisita,Info) VALUES(:codicePersona,:codiceRifugio,:dataVisita,:info)")
    void visitHut(String codicePersona, Integer codiceRifugio, String dataVisita, String info);

    @Query("INSERT INTO VisiteRifugi(CodicePersona,CodiceRifugio,DataVisita,Rating) VALUES(:codicePersona,:codiceRifugio,:dataVisita,:rating)")
    void visitHut(String codicePersona, Integer codiceRifugio, String dataVisita, Integer rating);

    @Query("INSERT INTO VisiteRifugi(CodicePersona,CodiceRifugio,DataVisita,Info,Rating) VALUES(:codicePersona,:codiceRifugio,:dataVisita,:info,:rating)")
    void visitHut(String codicePersona, Integer codiceRifugio, String dataVisita, String info, Integer rating);

    @Query("SELECT COUNT(VisiteRifugi.CodiceRifugio) FROM VisiteRifugi WHERE CodicePersona=:codicePersona AND CodiceRifugio=:codiceRifugio")
    Integer isVisited(String codicePersona, Integer codiceRifugio);


    @Query("SELECT Rifugi.*,Visite FROM Rifugi LEFT OUTER JOIN (SELECT CodiceRifugio,COUNT(CodiceRifugio) As Visite FROM VisiteRifugi WHERE VisiteRifugi.CodicePersona=:codicePersona GROUP BY (CodiceRifugio))AS Counter ON Rifugi.CodiceRifugio= Counter.CodiceRifugio")
    LiveData<List<HutsWithNumberOfVisit>> getAllTheHutWithNumberOfVisitByUserId(String codicePersona);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Long insert(VisitaRifugio visitaRifugio);
}