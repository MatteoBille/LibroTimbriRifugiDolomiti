package com.example.librotimbririfugidolomiti.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

class RifugiRepository {

    private RifugiDao mRifugiDao;
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
        mRifugiDao = db.rifugioDao();
        mAllRifugi = mRifugiDao.getAllHut();
        nGroups = mRifugiDao.getNumberOfDolomiticGroups();
        allGroups=mRifugiDao.getListOfDolomiticGroups();
        nHut= mRifugiDao.getNumberOfHut();
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
    Rifugio getHutById(int hutId){return mRifugiDao.getHutById(hutId);}
    Integer getNumberOfHut(){return nHut;}
    List<HutGroup> getNumberOfHutforEachDolomitcGroup(){return mRifugiDao.getNumberOfHutforEachDolomitcGroup();}
    List<Rifugio> getListOfHutByDolomiticGroup(String groupName){return mRifugiDao.getListOfHutByDolomiticGroup(groupName);}

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(Rifugio rifugio) {
        RifugiRoomDatabase.databaseWriteExecutor.execute(() -> {
            mRifugiDao.insert(rifugio);
        });
    }
}