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
import com.example.librotimbririfugidolomiti.SqlDatabaseFirebaseSyncronization;
import com.example.librotimbririfugidolomiti.database.CondivisioneLibro;
import com.example.librotimbririfugidolomiti.database.Persona;
import com.example.librotimbririfugidolomiti.database.RifugiViewModel;
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
    FragmentSharingBinding binding;
    RifugiViewModel mRifugiViewModel;
    SharedPreferences sharedPreferences;
    FirebaseFirestore firebaseDatabase;
    RecyclerPersonAdapter adapter;
    List<Persona> obtained = new ArrayList<>();
    private final Handler mHandler = new Handler();

    private SqlDatabaseFirebaseSyncronization databaseSync;

    private final Runnable syncLocalDb = new Runnable() {
        @Override
        public void run() {
            databaseSync.synchronizeLocalDb();
            int time = 1000 * 60 * 2;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            createRecyclerView();
            mHandler.postDelayed(this, time);
        }
    };

    public SharingFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSharingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mRifugiViewModel = new ViewModelProvider(this).get(RifugiViewModel.class);
        sharedPreferences = getActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        firebaseDatabase = FirebaseFirestore.getInstance();
        databaseSync = new SqlDatabaseFirebaseSyncronization(firebaseDatabase, mRifugiViewModel);
        adapter = new RecyclerPersonAdapter(new ArrayList<>(), this);
        String sharingId = String.format(getResources().getString(R.string.my_id), sharedPreferences.getString("codicePersona", null));

        binding.myId.setText(sharingId);
        binding.share.setOnClickListener(e -> {
            openSharingPopup();
        });

        createRecyclerView();
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
        Log.i("SHARE", "inserito " + shareId);
        String idSharingFrom = sharedPreferences.getString("codicePersona", null);
        firebaseDatabase.collection("users").document(idPersona)
                .update("sharingOf", Arrays.asList(idSharingFrom));
        CondivisioneLibro condivisioneLibro = new CondivisioneLibro(idSharingFrom, idPersona);

        mRifugiViewModel.insert(condivisioneLibro);

    }

    private void createRecyclerView() {
        obtained = new ArrayList<>();
        String codPersona = sharedPreferences.getString("codicePersona", null);
        List<CondivisioneLibro> condivisioni = mRifugiViewModel.getObtainedBook(codPersona);
        Log.i("CONDIVISIONE1", condivisioni + "");
        for (CondivisioneLibro condivisione : condivisioni) {
            Log.i("CONDIVISIONE2", condivisione.getCodicePersonaVisualizzante() + " " + condivisione.getCodicePersonaProprietaria());
            obtained.add(mRifugiViewModel.getPersonById(condivisione.getCodicePersonaProprietaria()));
        }

        RecyclerView recyclerView = binding.obtainedBook;
        adapter.add(obtained);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RecyclerPersonAdapter(obtained, this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(getActivity(), SharedBookActivity.class);
        String codicePersona = obtained.get(position).getCodicePersona();
        intent.putExtra("codicePersona", codicePersona);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        mHandler.post(syncLocalDb);
    }

    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacks(syncLocalDb);
    }
}
