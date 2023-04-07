package com.example.myquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.myquiz.databinding.ActivityQuestionsAnswerBinding;
import com.example.myquiz.databinding.ActivityResultBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class ResultActivity extends AppCompatActivity {
    ActivityResultBinding binding;
    long earn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        int corr=getIntent().getIntExtra("correctAns",0);
        int total=getIntent().getIntExtra("total",0);
        binding.ResultScore.setText(""+corr+"/"+total);
        earn=corr*10;
        binding.ResultCoinsEarn.setText(""+earn);
        FirebaseFirestore firestore=FirebaseFirestore.getInstance();
        firestore.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .update("coins", FieldValue.increment(earn));
    }
}