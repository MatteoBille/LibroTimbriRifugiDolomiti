package com.example.librotimbririfugidolomiti.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

class RifugiRepository {

    final private DatabaseVisiteRifugiDao databaseVisiteRifugiDao;
    final private DatabasePersoneDao databasePersoneDao;
    final private DatabaseRifugiDao databaseRifugiDao;


    RifugiRepository(Application application) {
        RifugiRoomDatabase db = RifugiRoomDatabase.getDatabase(application);
        databaseVisiteRifugiDao = db.visiteRifugiDao();
        databasePersoneDao = db.personeDao();
        databaseRifugiDao = db.rifugiDao();
    }

    LiveData<List<Rifugio>> getAllWords() {
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

    Integer getNumberOfHutVisited(Integer codicePersona) {
        return databaseVisiteRifugiDao.getNumberOfHutVisited(codicePersona);
    }

    String getLastVisitDay() {
        return databaseVisiteRifugiDao.getLastVisitDay();
    }

    List<HutGroup> getNumberOfHutforEachDolomitcGroup() {
        return databaseVisiteRifugiDao.getNumberOfHutforEachDolomitcGroup();
    }

    List<Rifugio> getListOfHutByDolomiticGroup(String groupName) {
        return databaseVisiteRifugiDao.getListOfHutByDolomiticGroup(groupName);
    }

    Integer getNumberOfVisitByHut(Integer codiceRifugio, Integer codicePersona) {
        return databaseVisiteRifugiDao.getNumberOfVisitByHut(codiceRifugio, codicePersona);
    }

    Integer getNumberOfUsers() {
        return databasePersoneDao.getNumberOfUsers();
    }

    Persona getPersonById(Integer codicePersona) {
        return databasePersoneDao.getPersonById(codicePersona);
    }

    List<VisitaRifugio> getVisitsByHutAndPerson(Integer codiceRifugio, Integer codicePersona) {
        return databaseVisiteRifugiDao.getVisitsByHutAndPerson(codiceRifugio, codicePersona);
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


    public void visitHut(Integer codicePersona, Integer codiceRifugio, String dataVisita) {
        databaseVisiteRifugiDao.visitHut(codicePersona, codiceRifugio, dataVisita);
    }


    public void visitHut(Integer codicePersona, Integer codiceRifugio, String dataVisita, String info) {
        databaseVisiteRifugiDao.visitHut(codicePersona, codiceRifugio, dataVisita, info);
    }


    public void visitHut(Integer codicePersona, Integer codiceRifugio, String dataVisita, Integer rating) {
        databaseVisiteRifugiDao.visitHut(codicePersona, codiceRifugio, dataVisita, rating);
    }


    public void visitHut(Integer codicePersona, Integer codiceRifugio, String dataVisita, String info, Integer rating) {
        databaseVisiteRifugiDao.visitHut(codicePersona, codiceRifugio, dataVisita, info, rating);
    }


    public Integer isVisited(int codicePersona, Integer codiceRifugio) {
        return databaseVisiteRifugiDao.isVisited(codicePersona, codiceRifugio);
    }
}