package com.example.librotimbririfugidolomiti.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

class RifugiRepository {

    final private DatabaseVisiteRifugiDao databaseVisiteRifugiDao;
    final private DatabasePersoneDao databasePersoneDao;
    final private DatabaseRifugiDao databaseRifugiDao;
    final private DatabaseCondivisioneLibroDao databaseCondivisioneLibroDao;

    RifugiRepository(Application application) {
        RifugiRoomDatabase db = RifugiRoomDatabase.getDatabase(application);
        databaseVisiteRifugiDao = db.visiteRifugiDao();
        databasePersoneDao = db.personeDao();
        databaseRifugiDao = db.rifugiDao();
        databaseCondivisioneLibroDao = db.condivisioneLibroDao();
    }

    LiveData<List<Rifugio>> getAllHut() {
        return databaseRifugiDao.getAllHut();
    }

    LiveData<Integer> getNumberOfDolomiticGroups() {
        return databaseRifugiDao.getNumberOfDolomiticGroups();
    }

    LiveData<List<String>> getListOfDolomiticGroups() {
        return databaseRifugiDao.getListOfDolomiticGroups();
    }

    Rifugio getHutById(int hutId) {
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
        return databaseVisiteRifugiDao.getNumberOfHutforEachDolomitcGroup();
    }

    List<Rifugio> getListOfHutByDolomiticGroup(String groupName) {
        return databaseVisiteRifugiDao.getListOfHutByDolomiticGroup(groupName);
    }

    Integer getNumberOfVisitByHut(Integer codiceRifugio, String codicePersona) {
        return databaseVisiteRifugiDao.getNumberOfVisitByHut(codiceRifugio, codicePersona);
    }

    Integer getNumberOfUsers(String local) {
        return databasePersoneDao.getNumberOfUsers(local);
    }

    Persona getPersonById(String codicePersona, String local) {
        return databasePersoneDao.getPersonById(codicePersona, local);
    }

    Persona getPersonById(String codicePersona) {
        return databasePersoneDao.getPersonById(codicePersona);
    }

    List<VisitaRifugio> getVisitsByHutAndPerson(Integer codiceRifugio, String codicePersona) {
        return databaseVisiteRifugiDao.getVisitsByHutAndPerson(codiceRifugio, codicePersona);
    }

    List<String> getAllPeopleIDs(String local) {
        return databasePersoneDao.getAllPeopleIDs(local);
    }

    List<VisitaRifugio> getVisitsByPerson(String id) {
        return databaseVisiteRifugiDao.getVisitsByPerson(id);
    }

    public Long insert(VisitaRifugio visitaRifugio) {
        return databaseVisiteRifugiDao.insert(visitaRifugio);
    }

    void insert(Rifugio rifugio) {
        RifugiRoomDatabase.databaseWriteExecutor.execute(() -> databaseRifugiDao.insert(rifugio));
    }

    public Long insert(Persona persona) {
        return databasePersoneDao.insert(persona);
    }

    public Void insert(CondivisioneLibro condivisioneLibro) {
        return databaseCondivisioneLibroDao.insert(condivisioneLibro);
    }


    public void visitHut(String codicePersona, Integer codiceRifugio, String dataVisita) {
        databaseVisiteRifugiDao.visitHut(codicePersona, codiceRifugio, dataVisita);
    }


    public void visitHut(String codicePersona, Integer codiceRifugio, String dataVisita, String info) {
        databaseVisiteRifugiDao.visitHut(codicePersona, codiceRifugio, dataVisita, info);
    }


    public void visitHut(String codicePersona, Integer codiceRifugio, String dataVisita, Integer rating) {
        databaseVisiteRifugiDao.visitHut(codicePersona, codiceRifugio, dataVisita, rating);
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

    List<CondivisioneLibro> getObtainedBook(String codicePersona) {
        return databaseCondivisioneLibroDao.getObtainedBook(codicePersona);
    }

    public VisitaRifugio getVisitsByHutPersonAndDate(Integer codiceRifugio, String codicePersona, String dataVisita) {
        return databaseVisiteRifugiDao.getVisitsByHutPersonAndDate(codiceRifugio, codicePersona, dataVisita);
    }

    public LiveData<List<HutsWithNumberOfVisit>> getAllTheHutWithNumberOfVisitByUserId(String codicePersona) {
        return databaseVisiteRifugiDao.getAllTheHutWithNumberOfVisitByUserId(codicePersona);
    }
}
