package com.example.librotimbririfugidolomiti.ui.mybook;

import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.librotimbririfugidolomiti.R;
import com.example.librotimbririfugidolomiti.database.RifugiViewModel;
import com.example.librotimbririfugidolomiti.databinding.FragmentBookBinding;
import com.example.librotimbririfugidolomiti.databinding.FragmentListofallhutsBinding;

import java.io.File;
import java.util.List;

public class BookFragment extends Fragment {
    private RifugiViewModel mRifugiViewModel;
    private FragmentBookBinding binding;
    private int nextHut;
    private int numberOfHut;
    private String currentGroup;
    public BookFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBookBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        mRifugiViewModel = new ViewModelProvider(this).get(RifugiViewModel.class);
        numberOfHut=mRifugiViewModel.getNumberOfHut();

        Log.i("LTE",numberOfHut+"");
        binding.next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              toNextPage();
            }
        });

        binding.prev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                toPrevPage();
            }
        });

        nextHut=1;
        setPage(nextHut);

        return root;
    }

    private void setPage(int page){
        if(page<=2){
            binding.prev.setEnabled(false);
        }
        if(page>=numberOfHut-1){
            binding.next.setEnabled(false);
        }
        mRifugiViewModel.getHutById(page).observe(getViewLifecycleOwner(), hut->{
                currentGroup=hut.getGruppoDolomitico();
                binding.title.setText(currentGroup);
                binding.nameHut1.setText(hut.getNomeRifugio());
                binding.visitDateLabel1.setText("Data visita:");
                File file =new File(getContext().getFilesDir()+"/images/"+hut.getNomeImmagine().trim());
                Log.i("LTE",file.getAbsolutePath()+"");
                Bitmap bit = BitmapFactory.decodeFile(file.getAbsolutePath());
                binding.imageHut1.setImageBitmap(bit);
        });
        page++;

        binding.secondCardView.setVisibility(View.VISIBLE);
        mRifugiViewModel.getHutById(page).observe(getViewLifecycleOwner(), hut->{
            if(currentGroup==null){
                currentGroup=hut.getGruppoDolomitico();
            }
            if(currentGroup.equals(hut.getGruppoDolomitico())){
                binding.nameHut2.setText(hut.getNomeRifugio());
                binding.visitDateLabel2.setText("Data visita:");
                File file =new File(getContext().getFilesDir()+"/images/"+hut.getNomeImmagine().trim());
                Log.i("LTE",file.getAbsolutePath()+"");
                Bitmap bit = BitmapFactory.decodeFile(file.getAbsolutePath());
                binding.imageHut2.setImageBitmap(bit);
            }else{
                binding.secondCardView.setVisibility(View.INVISIBLE);
            }
        });
        if(binding.secondCardView.getVisibility()!=View.INVISIBLE){
            page++;
        }
        nextHut=page;
    }

    private void toNextPage(){
        binding.prev.setEnabled(true);
        setPage(nextHut);
    }

    private void toPrevPage(){
        binding.prev.setEnabled(true);
        Log.i("PAGE",nextHut+"");
        if(binding.secondCardView.getVisibility()!=View.INVISIBLE){
            nextHut-=4;
        }else{
            nextHut-=3;
        }
        Log.i("PAGE",nextHut+"");
        setPage(nextHut);
    }
}