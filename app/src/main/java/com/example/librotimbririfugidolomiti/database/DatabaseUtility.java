package com.example.librotimbririfugidolomiti.database;

import android.util.Log;

import java.util.List;

public class DatabaseUtility {

    public static void printAllPerson(RifugiViewModel rifugiViewModel) {
        List<Persona> local = rifugiViewModel.getAllLocalPeople();
        List<Persona> nonLocal = rifugiViewModel.getAllNonLocalPeople();
        Log.i("DB PERSONE:LOCAL", local.toString());
        Log.i("DB PERSONE:NON LOCAL", nonLocal.toString());
    }

    public static void getAllVisitsByPerson(RifugiViewModel rifugiViewModel, String id) {
        List<VisitaRifugio> visitaRifugios = rifugiViewModel.getVisitsByPerson(id);
    }
}
