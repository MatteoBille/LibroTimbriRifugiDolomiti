package com.example.librotimbririfugidolomiti.database;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class RifugiViewModel extends AndroidViewModel {

    private RifugiRepository mRepository;

    private final LiveData<List<Rifugio>> mAllRifugi;
    private final LiveData<Integer> nGroups;
    private LiveData<List<String>> allGroups;
    private LiveData<Rifugio> hut;
    private Integer nHut;

    public RifugiViewModel (Application application) {
        super(application);
        mRepository = new RifugiRepository(application);
        mAllRifugi = mRepository.getAllWords();
        nGroups = mRepository.getNumberOfDolomiticGroups();
        allGroups = mRepository.getListOfDolomiticGroups();
        nHut=mRepository.getNumberOfHut();

    }

    public LiveData<List<Rifugio>> getAllRifugi() { return mAllRifugi; }
    public LiveData<Integer> getNumberOfDolomiticGroups() { return nGroups; }
    public LiveData<List<String>> getListOfDolomiticGroups(){ return allGroups; }
    public Rifugio getHutById(int hutId){return mRepository.getHutById(hutId);}
    public Integer getNumberOfHut(){return nHut;}
    public List<HutGroup> getNumberOfHutforEachDolomitcGroup(){return mRepository.getNumberOfHutforEachDolomitcGroup();}
    public List<Rifugio> getListOfHutByDolomiticGroup(String groupName){return mRepository.getListOfHutByDolomiticGroup(groupName);}

    public void insert(Rifugio word) { mRepository.insert(word); }
}
