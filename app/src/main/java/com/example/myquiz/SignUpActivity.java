package com.example.myquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myquiz.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    ActivitySignUpBinding binding;
   ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait a moment");
        firestore=FirebaseFirestore.getInstance();
       binding.Login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String username,password,email,refer;
               email=binding.useremail.getText().toString();
               password=binding.userpassword.getText().toString();
               username=binding.username.getText().toString();
               refer=binding.userrefer.getText().toString();
               UserDetails userDetails=new UserDetails(username,password,email,refer);
               auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful())
                       {
                           String uid=task.getResult().getUser().getUid();
                           firestore.collection("users").document(uid).set(userDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   if (task.isSuccessful())
                                   {
                                       progressDialog.dismiss();
                                       startActivity(new Intent(SignUpActivity.this,MainActivity.class));
                                       finish();
                                   }
                                   else
                                   {
                                       Toast.makeText(SignUpActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_LONG).show();
                                   }
                               }
                           });
                           progressDialog.show();
                       }
                       else
                       {
                           progressDialog.dismiss();
                           Toast.makeText(SignUpActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_LONG).show();
                       }
                   }
               });
           }
       });
    }
}