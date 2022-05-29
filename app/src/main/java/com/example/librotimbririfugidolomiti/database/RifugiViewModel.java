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
    public Integer getNumberOfHutVisited(){return mRepository.getNumberOfHutVisited();}
    public String getLastVisitDay(){return mRepository.getLastVisitDay();}
    public List<HutGroup> getNumberOfHutforEachDolomitcGroup(){return mRepository.getNumberOfHutforEachDolomitcGroup();}
    public List<Rifugio> getListOfHutByDolomiticGroup(String groupName){return mRepository.getListOfHutByDolomiticGroup(groupName);}

    public Long insert(VisitaRifugio visitaRifugio){return mRepository.insert(visitaRifugio);}

    public void insert(Rifugio word) { mRepository.insert(word); }

    public void visitHut(Integer codicePersona,Integer codiceRifugio,String dataVisita){mRepository.visitHut(codicePersona,codiceRifugio,dataVisita);};
    public void visitHut(Integer codicePersona,Integer codiceRifugio,String dataVisita,String info){mRepository.visitHut(codicePersona,codiceRifugio,dataVisita,info);};
    public void visitHut(Integer codicePersona,Integer codiceRifugio,String dataVisita,Integer rating){mRepository.visitHut(codicePersona,codiceRifugio,dataVisita,rating);};

    public void visitHut(Integer codicePersona,Integer codiceRifugio,String dataVisita,String info,Integer rating){mRepository.visitHut(codicePersona,codiceRifugio,dataVisita,info,rating);};

    public Integer numberOfVisitByHutId(int codicePersona, Integer codiceRifugio) {return mRepository.isVisited(codicePersona,codiceRifugio);}
}
