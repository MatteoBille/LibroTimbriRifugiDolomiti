package com.example.librotimbririfugidolomiti.database;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class RifugiViewModel extends AndroidViewModel {

    private RifugiRepository mRepository;

    private final LiveData<List<Rifugio>> mAllRifugi;

    public RifugiViewModel (Application application) {
        super(application);
        mRepository = new RifugiRepository(application);
        mAllRifugi = mRepository.getAllWords();
    }

    public LiveData<List<Rifugio>> getAllRifugi() { return mAllRifugi; }

    public void insert(Rifugio word) { mRepository.insert(word); }
}
