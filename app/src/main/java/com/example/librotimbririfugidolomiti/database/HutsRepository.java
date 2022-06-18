package com.example.librotimbririfugidolomiti.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.librotimbririfugidolomiti.database.Dao.DatabaseCondivisioneLibroDao;
import com.example.librotimbririfugidolomiti.database.Dao.DatabasePersoneDao;
import com.example.librotimbririfugidolomiti.database.Dao.DatabaseRifugiDao;
import com.example.librotimbririfugidolomiti.database.Dao.DatabaseVisiteRifugiDao;
import com.example.librotimbririfugidolomiti.database.Entity.CondivisioneLibro;
import com.example.librotimbririfugidolomiti.database.Entity.HutGroup;
import com.example.librotimbririfugidolomiti.database.Entity.HutsWithNumberOfVisit;
import com.example.librotimbririfugidolomiti.database.Entity.Persona;
import com.example.librotimbririfugidolomiti.database.Entity.Rifugio;
import com.example.librotimbririfugidolomiti.database.Entity.VisitaRifugio;

import java.util.List;

class HutsRepository {

    final private DatabaseVisiteRifugiDao databaseVisiteRifugiDao;
    final private DatabasePersoneDao databasePersoneDao;
    final private DatabaseRifugiDao databaseRifugiDao;
    final private DatabaseCondivisioneLibroDao databaseCondivisioneLibroDao;

    HutsRepository(Application application) {
        RifugiRoomDatabase db = RifugiRoomDatabase.getDatabase(application);
        databaseVisiteRifugiDao = db.visiteRifugiDao();
        databasePersoneDao = db.personeDao();
        databaseRifugiDao = db.rifugiDao();
        databaseCondivisioneLibroDao = db.condivisioneLibroDao();
    }

    LiveData<List<Rifugio>> getAllHut() {
        return databaseRifugiDao.getAllHut();
    }

    LiveData<Rifugio> getHutById(int hutId) {
        return databaseRifugiDao.getHutById(hutId);
    }

    Integer getNumberOfHut() {
        return databaseRifugiDao.getNumberOfHut();
    }

    Integer getNumberOfHutVisited(String codicePersona) {
        return databaseVisiteRifugiDao.getNumberOfHutVisited(codicePersona);
    }

    String getLastVisitDay(String codicePersona) {
        return databaseVisiteRifugiDao.getLastVisitDay(codicePersona);
    }

    List<HutGroup> getNumberOfHutforEachDolomitcGroup() {
        return databaseRifugiDao.getNumberOfHutforEachDolomitcGroup();
    }

    List<Rifugio> getListOfHutByDolomiticGroup(String groupName) {
        return databaseRifugiDao.getListOfHutByDolomiticGroup(groupName);
    }

    Integer getNumberOfVisitByHut(Integer codiceRifugio, String codicePersona) {
        return databaseVisiteRifugiDao.getNumberOfVisitByHut(codiceRifugio, codicePersona);
    }

    Persona getPersonById(String codicePersona, String local) {
        return databasePersoneDao.getPersonById(codicePersona, local);
    }

    Persona getPersonById(String codicePersona) {
        return databasePersoneDao.getPersonById(codicePersona);
    }

    LiveData<List<VisitaRifugio>> getVisitsByHutAndPersonAsync(Integer codiceRifugio, String codicePersona) {
        return databaseVisiteRifugiDao.getVisitsByHutAndPersonAsync(codiceRifugio, codicePersona);
    }

    List<VisitaRifugio> getVisitsByHutAndPerson(Integer codiceRifugio, String codicePersona) {
        return databaseVisiteRifugiDao.getVisitsByHutAndPerson(codiceRifugio, codicePersona);
    }

    List<String> getAllPeopleIDs(String local) {
        return databasePersoneDao.getAllPeopleIDs(local);
    }

    public Long insert(VisitaRifugio visitRifugio) {
        return databaseVisiteRifugiDao.insert(visitRifugio);
    }

    void insert(Rifugio hut) {
        RifugiRoomDatabase.databaseWriteExecutor.execute(() -> databaseRifugiDao.insert(hut));
    }

    public Long insert(Persona persona) {
        return databasePersoneDao.insert(persona);
    }

    public Void insert(CondivisioneLibro condivisioneLibro) {
        return databaseCondivisioneLibroDao.insert(condivisioneLibro);
    }

    public void visitHut(String codicePersona, Integer codiceRifugio, String dataVisita, String info, Integer rating) {
        databaseVisiteRifugiDao.visitHut(codicePersona, codiceRifugio, dataVisita, info, rating);
    }


    public Integer isVisited(String codicePersona, Integer codiceRifugio) {
        return databaseVisiteRifugiDao.isVisited(codicePersona, codiceRifugio);
    }

    public LiveData<List<Persona>> getAllPeople(String local) {
        return databasePersoneDao.getAllPeople(local);
    }

    public List<Integer> getHutIds() {
        return databaseRifugiDao.getHutIds();
    }

    LiveData<List<CondivisioneLibro>> getObtainedBookAsync(String codicePersona) {
        return databaseCondivisioneLibroDao.getObtainedBookAsync(codicePersona);
    }

    List<CondivisioneLibro> getObtainedBook(String codicePersona) {
        return databaseCondivisioneLibroDao.getObtainedBook(codicePersona);
    }


    public VisitaRifugio getVisitsByHutPersonAndDate(Integer codiceRifugio, String codicePersona, String dataVisita) {
        return databaseVisiteRifugiDao.getVisitsByHutPersonAndDate(codiceRifugio, codicePersona, dataVisita);
    }

    public LiveData<List<HutsWithNumberOfVisit>> getAllTheHutWithNumberOfVisitByUserId(String codicePersona) {
        return databaseVisiteRifugiDao.getAllTheHutWithNumberOfVisitByUserId(codicePersona);
    }

    public List<VisitaRifugio> getAllVisitsByUser(String codicePersona) {
        return databaseVisiteRifugiDao.getAllVisitsByUser(codicePersona);
    }
}
