package com.example.librotimbririfugidolomiti.ui.home;

import static android.view.View.GONE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.librotimbririfugidolomiti.R;
import com.example.librotimbririfugidolomiti.database.Entity.Persona;
import com.example.librotimbririfugidolomiti.database.HutsViewModel;
import com.example.librotimbririfugidolomiti.databinding.FragmentHomeBinding;
import com.example.librotimbririfugidolomiti.ui.book.BookActivity;
import com.example.librotimbririfugidolomiti.ui.login.LoginActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeFragment extends Fragment {
    private HutsViewModel databaseSql;
    private FragmentHomeBinding binding;
    private SharedPreferences sharedPreferences;
    private String personId;
    private boolean obtained;
    private final Handler mHandler = new Handler();
    private final Runnable setArgumentsAsync = this::setFragmentArguments;

    private static final String PERSON_ID_IDENTIFIER = "PersonId";
    private static final String OBTAINED_IDENTIFIER = "Obtained";

    public static HomeFragment newInstance(String codicePersona, boolean obtained) {
        HomeFragment myFragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(PERSON_ID_IDENTIFIER, codicePersona);
        args.putBoolean(OBTAINED_IDENTIFIER, obtained);
        myFragment.setArguments(args);
        return myFragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        databaseSql = new ViewModelProvider(this).get(HutsViewModel.class);
        sharedPreferences = requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);

        if (getArguments() == null) {
            personId = sharedPreferences.getString("codicePersona", null);
            obtained = false;
        } else {
            personId = getArguments().getString(PERSON_ID_IDENTIFIER);
            obtained = getArguments().getBoolean(OBTAINED_IDENTIFIER);
            if (obtained) {
                binding.highBar.setVisibility(GONE);
            }
        }

        binding.goToBook.setOnClickListener(e -> {
            Intent intent = new Intent(root.getContext(), BookActivity.class);
            intent.putExtra(PERSON_ID_IDENTIFIER, personId);
            intent.putExtra(OBTAINED_IDENTIFIER, obtained);
            startActivity(intent);
        });

        binding.account.setOnClickListener(e -> {
            openUsersPopup();
        });

        mHandler.post(setArgumentsAsync);
        return root;
    }

    private void setFragmentArguments() {

        Persona persona = databaseSql.getPersonById(personId);
        binding.name.setText(persona.getNomeCognome());

        Integer numberOfHut = databaseSql.getNumberOfHut();
        Integer numberOfHutVisited = databaseSql.getNumberOfHutVisited(personId);
        String date = databaseSql.getLastVisitDay(personId);

        try {
            if (date != null) {
                Date dateLastVisit = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                String dateLastVisitString = String.format(getResources().getString(R.string.lastVisit), dateFormat.format(dateLastVisit));
                binding.lastVisit.setText(dateLastVisitString);
            } else {
                binding.lastVisit.setText("Rifugio non acora visitato");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        String visitedHuts = String.format(getResources().getString(R.string.visitedHuts), numberOfHutVisited, numberOfHut);
        binding.visitedHuts.setText(visitedHuts);


    }


    private void openUsersPopup() {
        View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_listof_all_account_popup, null);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

        databaseSql.getAllLocalPeople().observe(getViewLifecycleOwner(), persons -> {
            switch (persons.size()) {
                case 3:
                    setTextAndListener(popupView, persons.get(2), R.id.id3, popupWindow);
                    setTextAndListener(popupView, persons.get(1), R.id.id2, popupWindow);
                    setTextAndListener(popupView, persons.get(0), R.id.id1, popupWindow);
                    break;
                case 2:
                    popupView.findViewById(R.id.id3).setVisibility(GONE);
                    setTextAndListener(popupView, persons.get(1), R.id.id2, popupWindow);
                    setTextAndListener(popupView, persons.get(0), R.id.id1, popupWindow);
                    popupView.findViewById(R.id.addUser).setVisibility(View.VISIBLE);
                    break;
                case 1:
                    popupView.findViewById(R.id.id3).setVisibility(GONE);
                    popupView.findViewById(R.id.id2).setVisibility(GONE);
                    setTextAndListener(popupView, persons.get(0), R.id.id1, popupWindow);
                    popupView.findViewById(R.id.addUser).setVisibility(View.VISIBLE);
                    break;
            }
            popupView.findViewById(R.id.addUser).setOnClickListener(e ->
            {
                addNewUser();
                popupWindow.dismiss();
            });
        });

    }

    private void setTextAndListener(View popupView, Persona persona, int p, PopupWindow popupWindow) {
        Button but = popupView.findViewById(p);
        but.setText(persona.getNomeCognome());
        but.setOnClickListener(e -> {
            changeUser(persona);
            popupWindow.dismiss();
        });

    }

    private void addNewUser() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
    }

    private void changeUser(Persona persona) {
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("codicePersona", persona.getCodicePersona()).apply();
        personId = sharedPreferences.getString("codicePersona", null);
        mHandler.post(setArgumentsAsync);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}