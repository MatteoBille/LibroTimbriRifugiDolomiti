package com.example.librotimbririfugidolomiti.ui.home;

import static android.view.View.GONE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
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
import com.example.librotimbririfugidolomiti.database.Persona;
import com.example.librotimbririfugidolomiti.database.RifugiViewModel;
import com.example.librotimbririfugidolomiti.databinding.FragmentHomeBinding;
import com.example.librotimbririfugidolomiti.ui.book.MyBookActivity;
import com.example.librotimbririfugidolomiti.ui.login.LoginActivity;

import java.util.List;

public class HomeFragment extends Fragment {
    private RifugiViewModel mRifugiViewModel;
    private FragmentHomeBinding binding;
    SharedPreferences sharedPreferences;
    String codicePersona;
    boolean obtained;

    public static HomeFragment newInstance() {
        HomeFragment myFragment = new HomeFragment();
        return myFragment;
    }

    public static HomeFragment newInstance(String codicePersona, boolean obtained) {
        HomeFragment myFragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("codicePersona", codicePersona);
        args.putBoolean("obtained", obtained);
        myFragment.setArguments(args);
        return myFragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mRifugiViewModel = new ViewModelProvider(this).get(RifugiViewModel.class);
        sharedPreferences = getActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        if (getArguments() == null) {
            codicePersona = sharedPreferences.getString("codicePersona", null);
            obtained = false;
        } else {
            codicePersona = getArguments().getString("codicePersona");
            obtained = getArguments().getBoolean("obtained");
            if (obtained) {
                binding.highBar.setVisibility(GONE);
            }
        }

        setFragmentArguments();

        binding.goToBook.setOnClickListener(e -> {
            Intent intent = new Intent(root.getContext(), MyBookActivity.class);
            intent.putExtra("codicePersona", codicePersona);
            intent.putExtra("obtained", obtained);
            startActivity(intent);
        });

        binding.account.setOnClickListener(e -> {
            openUsersPopup();
        });
        return root;
    }

    private void setFragmentArguments() {

        Persona person = mRifugiViewModel.getPersonById(codicePersona);
        binding.name.setText(person.getNomeCognome());

        Integer numberOfHut = mRifugiViewModel.getNumberOfHut();
        Integer numberOfHutVisited = mRifugiViewModel.getNumberOfHutVisited(codicePersona);


        String visitedHuts = String.format(getResources().getString(R.string.visitedHuts), numberOfHutVisited, numberOfHut);
        binding.visitedHuts.setText(visitedHuts);

        String date = mRifugiViewModel.getLastVisitDay(codicePersona);
        String dateLastVisit = String.format(getResources().getString(R.string.lastVisit), date);
        binding.lastVisit.setText(dateLastVisit);
    }


    private void openUsersPopup() {
        View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_listof_all_account_popup, null);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);

        List<Persona> persons = mRifugiViewModel.getAllLocalPeople();
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

    }

    private void addNewUser() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
    }

    private void setTextAndListener(View popupView, Persona person, int p, PopupWindow popupWindow) {
        Button but = (Button) popupView.findViewById(p);
        but.setText(person.getNomeCognome());
        but.setOnClickListener(e -> {
            changeUser(person);
            popupWindow.dismiss();
        });

    }

    private void changeUser(Persona person) {
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("codicePersona", person.getCodicePersona());
        myEdit.commit();
        codicePersona = sharedPreferences.getString("codicePersona", null);
        this.setFragmentArguments();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}