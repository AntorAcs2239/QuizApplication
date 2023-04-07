package com.example.myquiz;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myquiz.databinding.ActivityMainBinding;
import com.example.myquiz.databinding.FragmentHomeFregmentBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFregment#newInstance} factory method to
        * create an instance of this fragment.
        */
public class HomeFregment extends Fragment {


    public HomeFregment() {
        // Required empty public constructor
    }

    public static HomeFregment newInstance(String param1, String param2) {
        HomeFregment fragment = new HomeFregment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }
    FragmentHomeFregmentBinding binding;
    FirebaseFirestore firestore;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentHomeFregmentBinding.inflate(inflater,container,false);
        firestore= FirebaseFirestore.getInstance();
        ArrayList<CatagoryItems> catagoryItems=new ArrayList<>();
        CatagoryAdapter catagoryAdapter=new CatagoryAdapter(getContext(),catagoryItems);
        firestore.collection("catagories").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                catagoryItems.clear();
                for (DocumentSnapshot snapshot:value.getDocuments())
                {
                    CatagoryItems catagoryItems1=snapshot.toObject(CatagoryItems.class);
                    catagoryItems1.setId(snapshot.getId());
                    catagoryItems.add(catagoryItems1);
                }
                catagoryAdapter.notifyDataSetChanged();
            }
        });
        binding.catagoryitems.setLayoutManager(new GridLayoutManager(getContext(),2));
        binding.catagoryitems.setAdapter(catagoryAdapter);
        return binding.getRoot();
    }
}