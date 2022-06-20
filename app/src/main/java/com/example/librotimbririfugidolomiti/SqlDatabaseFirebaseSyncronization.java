package com.example.librotimbririfugidolomiti;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;

import com.example.librotimbririfugidolomiti.database.Entity.Persona;
import com.example.librotimbririfugidolomiti.database.Entity.VisitaRifugio;
import com.example.librotimbririfugidolomiti.database.HutsViewModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlDatabaseFirebaseSyncronization {

    FirebaseFirestore firebaseDb;
    HutsViewModel sqlDb;
    LifecycleOwner lifeCicleOwner;

    public SqlDatabaseFirebaseSyncronization(FirebaseFirestore firebaseDb, HutsViewModel sqlDb, LifecycleOwner lifeCicleOwner) {
        this.firebaseDb = firebaseDb;
        this.sqlDb = sqlDb;
        this.lifeCicleOwner = lifeCicleOwner;
    }


    public void synchronizeCloudDb() {
        List<String> personIDs;
        List<Integer> hutIDs;
        personIDs = sqlDb.getAllLocalPeopleIDs();
        hutIDs = sqlDb.getHutIds();
        Map<String, Object> TopLevelVisit = new HashMap<>();

        for (String id : personIDs) {
            List<Map> hutMap = new ArrayList<>();
            for (Integer hutId : hutIDs) {
                List<VisitaRifugio> visits = sqlDb.getVisitsByHutAndPerson(hutId, id);
                for (int i = 0; i < visits.size(); i++) {
                    hutMap.add(visits.get(i).toMap());
                }
            }
            Persona persona = sqlDb.getLocalPersonById(id);
            TopLevelVisit.put("NomeCognome", persona.getNomeCognome());
            TopLevelVisit.put("Email", persona.getEmail());
            TopLevelVisit.put("Visits", hutMap);
            Log.i("SYNC", hutMap.toString());
            firebaseDb.collection("users").document(id)
                    .set(TopLevelVisit, SetOptions.merge());
        }
    }


}

