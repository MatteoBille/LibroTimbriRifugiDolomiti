package com.example.librotimbririfugidolomiti.database;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.librotimbririfugidolomiti.database.Entity.CondivisioneLibro;
import com.example.librotimbririfugidolomiti.database.Entity.HutGroup;
import com.example.librotimbririfugidolomiti.database.Entity.HutsWithNumberOfVisit;
import com.example.librotimbririfugidolomiti.database.Entity.Persona;
import com.example.librotimbririfugidolomiti.database.Entity.Rifugio;
import com.example.librotimbririfugidolomiti.database.Entity.VisitaRifugio;

import java.util.List;

public class HutsViewModel extends AndroidViewModel {

    private final HutsRepository mRepository;

    public HutsViewModel(Application application) {
        super(application);
        mRepository = new HutsRepository(application);

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

    public Long insert(VisitaRifugio visitRifugio) {
        return mRepository.insert(visitRifugio);
    }

    public Integer getNumberOfVisitByHut(Integer codiceRifugio, String codicePersona) {
        return mRepository.getNumberOfVisitByHut(codiceRifugio, codicePersona);
    }

    public void insert(Rifugio hut) {
        mRepository.insert(hut);
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

    public LiveData<List<VisitaRifugio>> getVisitsByHutAndPersonAsync(Integer codiceRifugio, String codicePersona) {
        return mRepository.getVisitsByHutAndPersonAsync(codiceRifugio, codicePersona);
    }

    public List<VisitaRifugio> getVisitsByHutAndPerson(Integer codiceRifugio, String codicePersona) {
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

    public LiveData<List<CondivisioneLibro>> getObtainedBookAsync(String codicePersona) {
        return mRepository.getObtainedBookAsync(codicePersona);
    }

    public List<CondivisioneLibro> getObtainedBook(String codicePersona) {
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

