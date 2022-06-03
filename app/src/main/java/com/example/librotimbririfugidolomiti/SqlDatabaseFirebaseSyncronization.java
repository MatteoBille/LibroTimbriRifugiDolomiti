package com.example.librotimbririfugidolomiti;

import android.util.Log;

import com.example.librotimbririfugidolomiti.database.CondivisioneLibro;
import com.example.librotimbririfugidolomiti.database.Persona;
import com.example.librotimbririfugidolomiti.database.RifugiViewModel;
import com.example.librotimbririfugidolomiti.database.VisitaRifugio;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlDatabaseFirebaseSyncronization {

    FirebaseFirestore firebaseDb;
    RifugiViewModel sqlDb;

    public SqlDatabaseFirebaseSyncronization(FirebaseFirestore firebaseDb, RifugiViewModel sqlDb) {
        this.firebaseDb = firebaseDb;
        this.sqlDb = sqlDb;
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
                List<VisitaRifugio> visits;
                visits = sqlDb.getVisitsByHutAndPerson(hutId, id);
                for (int i = 0; i < visits.size(); i++) {
                    hutMap.add(visits.get(i).toMap());
                }
            }
            Persona person = sqlDb.getLocalPersonById(id);
            TopLevelVisit.put("NomeCognome", person.getNomeCognome());
            TopLevelVisit.put("Email", person.getEmail());
            TopLevelVisit.put("Visits", hutMap);

            firebaseDb.collection("users").document(id)
                    .set(TopLevelVisit, SetOptions.merge());
        }
    }

    public void synchronizeLocalDb() {
        List<String> personIDs;
        personIDs = sqlDb.getAllLocalPeopleIDs();
        for (String id : personIDs) {
            firebaseDb.collection("users").document(id)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                List<String> obtained = (List<String>) document.get("sharingOf");
                                if (obtained != null && obtained.size() != 0) {
                                    for (String id2 : obtained)
                                        firebaseDb.collection("users").document(id2)
                                                .get().addOnCompleteListener(t -> {
                                            if (t.isSuccessful()) {
                                                DocumentSnapshot document2 = t.getResult();
                                                if (document2.exists()) {
                                                    String nomeCognome = document2.getString("NomeCognome");
                                                    String email = document2.getString("Email");
                                                    String locale = "false";
                                                    Persona persona = new Persona(id2, nomeCognome, email, locale);
                                                    sqlDb.insert(persona);
                                                    CondivisioneLibro condivisioneLibro = new CondivisioneLibro(id2, id);
                                                    sqlDb.insert(condivisioneLibro);
                                                    Log.i("CONDIVISIONE", sqlDb.getObtainedBook(id).toString());
                                                    Log.i("LOCAL", persona.toString());
                                                    Log.i("LOCAL", sqlDb.getAllNonLocalPeople().toString());
                                                    Log.i("LOCAL", sqlDb.getAllLocalPeople().toString());
                                                    List<Map> hutVisits = (List<Map>) document2.get("Visits");
                                                    for (int i = 0; i < hutVisits.size(); i++) {
                                                        Integer codiceRifugio = ((Long) hutVisits.get(i).get("CodiceRifugio")).intValue();
                                                        String dataVisita = (String) hutVisits.get(i).get("DataVisita");
                                                        String info = (String) hutVisits.get(i).get("Info");
                                                        Integer rating = ((Long) hutVisits.get(i).get("Rating")).intValue();
                                                        VisitaRifugio visitaRifugio = new VisitaRifugio(codiceRifugio, id2, dataVisita, info, rating);
                                                        sqlDb.insert(visitaRifugio);
                                                    }
                                                }
                                            }
                                        });
                                }
                            }
                        }
                    });
        }
    }
}

