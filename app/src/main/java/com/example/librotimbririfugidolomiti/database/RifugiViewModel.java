package com.example.librotimbririfugidolomiti.database;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class RifugiViewModel extends AndroidViewModel {

    private final RifugiRepository mRepository;

    public RifugiViewModel(Application application) {
        super(application);
        mRepository = new RifugiRepository(application);

    }

    public LiveData<List<Rifugio>> getAllRifugi() {
        return mRepository.getAllHut();
    }

    public LiveData<Rifugio> getHutById(int hutId) {
        return mRepository.getHutById(hutId);
    }

    public Integer getNumberOfHut() {
        return mRepository.getNumberOfHut();
    }

    public Integer getNumberOfHutVisited(String codicePersona) {
        return mRepository.getNumberOfHutVisited(codicePersona);
    }

    public String getLastVisitDay(String codicePersona) {
        return mRepository.getLastVisitDay(codicePersona);
    }

    public List<HutGroup> getNumberOfHutforEachDolomitcGroup() {
        return mRepository.getNumberOfHutforEachDolomitcGroup();
    }

    public List<Rifugio> getListOfHutByDolomiticGroup(String groupName) {
        return mRepository.getListOfHutByDolomiticGroup(groupName);
    }

    public Long insert(VisitaRifugio visitaRifugio) {
        return mRepository.insert(visitaRifugio);
    }

    public Integer getNumberOfVisitByHut(Integer codiceRifugio, String codicePersona) {
        return mRepository.getNumberOfVisitByHut(codiceRifugio, codicePersona);
    }

    public void insert(Rifugio word) {
        mRepository.insert(word);
    }

    public Long insert(Persona persona) {
        return mRepository.insert(persona);
    }

    public Void insert(CondivisioneLibro condivisioneLibro) {
        return mRepository.insert(condivisioneLibro);
    }

    public Persona getLocalPersonById(String codicePersona) {
        return mRepository.getPersonById(codicePersona, "true");
    }

    public Persona getPersonById(String codicePersona) {
        return mRepository.getPersonById(codicePersona);
    }

    public LiveData<List<VisitaRifugio>> getVisitsByHutAndPerson(Integer codiceRifugio, String codicePersona) {
        return mRepository.getVisitsByHutAndPerson(codiceRifugio, codicePersona);
    }

    public List<String> getAllLocalPeopleIDs() {
        return mRepository.getAllPeopleIDs("true");
    }

    public List<String> getAllNonLocalPeopleIDs() {
        return mRepository.getAllPeopleIDs("false");
    }

    public void visitHut(String codicePersona, Integer codiceRifugio, String dataVisita, String info, Integer rating) {
        mRepository.visitHut(codicePersona, codiceRifugio, dataVisita, info, rating);
    }

    public Integer numberOfVisitByHutId(String codicePersona, Integer codiceRifugio) {
        return mRepository.isVisited(codicePersona, codiceRifugio);
    }

    public LiveData<List<Persona>> getAllLocalPeople() {
        return mRepository.getAllPeople("true");
    }

    public LiveData<List<Persona>> getAllNonLocalPeople() {
        return mRepository.getAllPeople("false");
    }

    public List<Integer> getHutIds() {
        return mRepository.getHutIds();
    }

    public LiveData<List<CondivisioneLibro>> getObtainedBook(String codicePersona) {
        return mRepository.getObtainedBook(codicePersona);
    }

    public VisitaRifugio getVisitsByHutPersonAndDate(Integer codiceRifugio, String codicePersona, String dataVisita) {
        return mRepository.getVisitsByHutPersonAndDate(codiceRifugio, codicePersona, dataVisita);
    }

    public LiveData<List<HutsWithNumberOfVisit>> getAllTheHutWithNumberOfVisitByUserId(String codicePersona) {
        return mRepository.getAllTheHutWithNumberOfVisitByUserId(codicePersona);
    }

    public List<VisitaRifugio> getAllVisitsByUser(String codicePersona) {
        return mRepository.getAllVisitsByUser(codicePersona);
    }

}

