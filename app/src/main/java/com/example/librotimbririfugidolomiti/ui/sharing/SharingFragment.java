package com.example.librotimbririfugidolomiti.ui.sharing;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.librotimbririfugidolomiti.R;
import com.example.librotimbririfugidolomiti.database.Entity.CondivisioneLibro;
import com.example.librotimbririfugidolomiti.database.Entity.Persona;
import com.example.librotimbririfugidolomiti.database.Entity.VisitaRifugio;
import com.example.librotimbririfugidolomiti.database.HutsViewModel;
import com.example.librotimbririfugidolomiti.databinding.FragmentSharingBinding;
import com.example.librotimbririfugidolomiti.ui.sharedbook.SharedBookActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SharingFragment extends Fragment implements RecyclerPersonAdapter.OnPersonListener {
    private FragmentSharingBinding binding;
    private HutsViewModel mHutsViewModel;
    private SharedPreferences sharedPreferences;
    private FirebaseFirestore firebaseDatabase;
    private RecyclerPersonAdapter adapter;
    private List<Persona> obtained = new ArrayList<>();
    private final Handler mHandler = new Handler();

    private final Runnable syncLocalDb = this::synchronizeLocalDb;

    private static final String PERSON_ID_IDENTIFIER = "PersonId";

    public SharingFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSharingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mHutsViewModel = new ViewModelProvider(this).get(HutsViewModel.class);
        sharedPreferences = getActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        firebaseDatabase = FirebaseFirestore.getInstance();
        adapter = new RecyclerPersonAdapter(new ArrayList<>(), this);
        String sharingId = String.format(getResources().getString(R.string.my_id), sharedPreferences.getString("codicePersona", null));

        binding.myId.setText(sharingId);
        binding.share.setOnClickListener(e -> {
            openSharingPopup();
        });
        return root;
    }

    private void openSharingPopup() {
        View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_sharing_popup, null);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
        popupWindow.setFocusable(true);

        popupView.findViewById(R.id.shareBook).setOnClickListener(e ->
        {
            shareUser(popupView);
            popupWindow.dismiss();
        });
    }

    private void shareUser(View popupView) {
        String idPerson = ((EditText) popupView.findViewById(R.id.idPerson)).getText().toString();
        firebaseDatabase.collection("users")
                .document(idPerson)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            saveNewData(idPerson);
                        } else {
                            Log.i("SHARE", "La persona non esiste");
                        }
                    }
                });
    }

    public void saveNewData(String idPersona) {
        Map<String, Object> shareId = new HashMap<>();
        String idSharingFrom = sharedPreferences.getString("codicePersona", null);
        firebaseDatabase.collection("users").document(idPersona)
                .update("sharingOf", Arrays.asList(idSharingFrom));
        CondivisioneLibro entityCondivisioneLibro = new CondivisioneLibro(idSharingFrom, idPersona);

        mHutsViewModel.insert(entityCondivisioneLibro);

    }

    private void createRecyclerView() {
        obtained = new ArrayList<>();
        String codPersona = sharedPreferences.getString("codicePersona", null);
        mHutsViewModel.getObtainedBookAsync(codPersona).observe(getViewLifecycleOwner(), condivisioni ->

        {
            for (CondivisioneLibro condivisione : condivisioni) {
                obtained.add(mHutsViewModel.getPersonById(condivisione.getCodicePersonaProprietaria()));
            }

            RecyclerView recyclerView = binding.obtainedBook;
            adapter.add(obtained);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);
        });
    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(getActivity(), SharedBookActivity.class);
        String codicePersona = obtained.get(position).getCodicePersona();
        intent.putExtra(PERSON_ID_IDENTIFIER, codicePersona);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        createRecyclerView();
        mHandler.post(syncLocalDb);
    }

    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacks(syncLocalDb);
    }

    public void synchronizeLocalDb() {
        List<String> personIDs;
        personIDs = mHutsViewModel.getAllLocalPeopleIDs();
        for (String id : personIDs) {
            firebaseDatabase.collection("users").document(id)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                retrieveAllTheVisistsFromAUserAndSaveOnLocalDb(id, document);
                            }
                        }
                    });
        }
    }

    private void retrieveAllTheVisistsFromAUserAndSaveOnLocalDb(String id, DocumentSnapshot document) {
        List<String> obtained = (List<String>) document.get("sharingOf");
        if (obtained != null && obtained.size() != 0) {
            for (String id2 : obtained)
                firebaseDatabase.collection("users").document(id2)
                        .get().addOnCompleteListener(t -> {
                    if (t.isSuccessful()) {
                        DocumentSnapshot document2 = t.getResult();
                        if (document2.exists()) {
                            String nomeCognome = document2.getString("NomeCognome");
                            String email = document2.getString("Email");
                            Persona persona = new Persona(id2, nomeCognome, email, "false");
                            mHutsViewModel.insert(persona);
                            Log.i("SHARE", persona.toString());
                            CondivisioneLibro condivisioneLibro = new CondivisioneLibro(id2, id);
                            Log.i("SHARE", condivisioneLibro.toString());
                            mHutsViewModel.insert(condivisioneLibro);
                            List<Map> hutVisits = (List<Map>) document2.get("Visits");
                            for (int i = 0; i < hutVisits.size(); i++) {
                                Integer codiceRifugio = ((Long) hutVisits.get(i).get("CodiceRifugio")).intValue();
                                String dataVisita = (String) hutVisits.get(i).get("DataVisita");
                                String info = (String) hutVisits.get(i).get("Info");
                                Integer rating = ((Long) hutVisits.get(i).get("Rating")).intValue();
                                VisitaRifugio visitRifugio = new VisitaRifugio(codiceRifugio, id2, dataVisita, info, rating);
                                Log.i("SHARE", visitRifugio.toString());
                                mHutsViewModel.insert(visitRifugio);
                            }
                            addNewSharedOnReciclerView();
                        }
                    }
                });
        }
    }

    private void addNewSharedOnReciclerView() {
        String codPersona = sharedPreferences.getString("codicePersona", null);
        List<CondivisioneLibro> condivisioni = mHutsViewModel.getObtainedBook(codPersona);
        for (CondivisioneLibro condivisione : condivisioni) {
            obtained.add(mHutsViewModel.getPersonById(condivisione.getCodicePersonaProprietaria()));
        }

        adapter.add(obtained);
    }
}
