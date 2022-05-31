package com.example.librotimbririfugidolomiti.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

class RifugiRepository {

    private DatabaseDao mDatabaseDao;
    private LiveData<List<Rifugio>> mAllRifugi;
    private LiveData<Integer> nGroups;
    private LiveData<List<String>> allGroups;
    private LiveData<Rifugio> hut;
    private Integer nHut;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    RifugiRepository(Application application) {
        RifugiRoomDatabase db = RifugiRoomDatabase.getDatabase(application);
        mDatabaseDao = db.rifugioDao();
        mAllRifugi = mDatabaseDao.getAllHut();
        nGroups = mDatabaseDao.getNumberOfDolomiticGroups();
        allGroups= mDatabaseDao.getListOfDolomiticGroups();
        nHut= mDatabaseDao.getNumberOfHut();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Rifugio>> getAllWords() {
        return mAllRifugi;
    }
    LiveData<Integer> getNumberOfDolomiticGroups() {
        return nGroups;
    }
    LiveData<List<String>> getListOfDolomiticGroups() { return allGroups; }
    Rifugio getHutById(int hutId){return mDatabaseDao.getHutById(hutId);}
    Integer getNumberOfHut(){return nHut;}
    Integer getNumberOfHutVisited(Integer codicePersona){return mDatabaseDao.getNumberOfHutVisited(codicePersona);}
    String getLastVisitDay(){return mDatabaseDao.getLastVisitDay();}
    List<HutGroup> getNumberOfHutforEachDolomitcGroup(){return mDatabaseDao.getNumberOfHutforEachDolomitcGroup();}
    List<Rifugio> getListOfHutByDolomiticGroup(String groupName){return mDatabaseDao.getListOfHutByDolomiticGroup(groupName);}
    Integer getNumberOfVisitByHut(Integer codiceRifugio,Integer codicePersona){return mDatabaseDao.getNumberOfVisitByHut(codiceRifugio,codicePersona);}
    Integer getNumberOfUsers(){return mDatabaseDao.getNumberOfUsers();};
    Persona getPersonById(Integer codicePersona){return mDatabaseDao.getPersonById(codicePersona);};
    List<VisitaRifugio> getVisitsByHutAndPerson(Integer codiceRifugio,Integer codicePersona){return mDatabaseDao.getVisitsByHutAndPerson(codiceRifugio,codicePersona);};
    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.

    public Long insert(VisitaRifugio visitaRifugio){
        return  mDatabaseDao.insert(visitaRifugio);
    }
    void insert(Rifugio rifugio) {
        RifugiRoomDatabase.databaseWriteExecutor.execute(() -> {
            mDatabaseDao.insert(rifugio);
        });
    }
    public Long insert(Persona persona){
        return mDatabaseDao.insert(persona);
    }


    public void visitHut(Integer codicePersona,Integer codiceRifugio,String dataVisita){mDatabaseDao.visitHut(codicePersona,codiceRifugio,dataVisita);};
    public void visitHut(Integer codicePersona,Integer codiceRifugio,String dataVisita,String info){mDatabaseDao.visitHut(codicePersona,codiceRifugio,dataVisita,info);};
    public void visitHut(Integer codicePersona,Integer codiceRifugio,String dataVisita,Integer rating){mDatabaseDao.visitHut(codicePersona,codiceRifugio,dataVisita,rating);};
    public void visitHut(Integer codicePersona,Integer codiceRifugio,String dataVisita,String info,Integer rating){mDatabaseDao.visitHut(codicePersona,codiceRifugio,dataVisita,info,rating);};

    public Integer isVisited(int codicePersona, Integer codiceRifugio) {return mDatabaseDao.isVisited(codicePersona,codiceRifugio);
    }
}