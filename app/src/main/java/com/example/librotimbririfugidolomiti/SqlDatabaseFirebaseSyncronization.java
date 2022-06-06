package com.example.librotimbririfugidolomiti;

import androidx.lifecycle.LifecycleOwner;

import com.example.librotimbririfugidolomiti.database.Persona;
import com.example.librotimbririfugidolomiti.database.RifugiViewModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlDatabaseFirebaseSyncronization {

    FirebaseFirestore firebaseDb;
    RifugiViewModel sqlDb;
    LifecycleOwner lifeCicleOwner;

    public SqlDatabaseFirebaseSyncronization(FirebaseFirestore firebaseDb, RifugiViewModel sqlDb, LifecycleOwner lifeCicleOwner) {
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
                sqlDb.getVisitsByHutAndPerson(hutId, id).observe(lifeCicleOwner, visits -> {
                    for (int i = 0; i < visits.size(); i++) {
                        hutMap.add(visits.get(i).toMap());
                    }
                });
                Persona person = sqlDb.getLocalPersonById(id);
                TopLevelVisit.put("NomeCognome", person.getNomeCognome());
                TopLevelVisit.put("Email", person.getEmail());
                TopLevelVisit.put("Visits", hutMap);

                firebaseDb.collection("users").document(id)
                        .set(TopLevelVisit, SetOptions.merge());
            }
        }
    }


}

