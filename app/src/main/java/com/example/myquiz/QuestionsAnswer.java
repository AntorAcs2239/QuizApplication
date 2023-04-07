package com.example.myquiz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.myquiz.databinding.ActivityQuestionsAnswerBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Random;

public class QuestionsAnswer extends AppCompatActivity {
    ActivityQuestionsAnswerBinding binding;
    ArrayList<QuesionItems>question;
    QuesionItems q;
    int index=0;
    CountDownTimer countDownTimer;
    String select;
    FirebaseFirestore firestore;
    int correctans=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityQuestionsAnswerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firestore=FirebaseFirestore.getInstance();
        question=new ArrayList<>();
        final Random random=new Random();
        int t=random.nextInt(3);
       final String catagoriId=getIntent().getStringExtra("catagoryid");
        firestore.collection("catagories")
                .document(catagoriId)
                .collection("question")
                .whereGreaterThanOrEqualTo("index",t)
                .orderBy("index")
                .limit(2)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.size()<2)
                        {
                            firestore.collection("catagories")
                                    .document(catagoriId)
                                    .collection("question")
                                    .whereLessThanOrEqualTo("index",t)
                                    .orderBy("index")
                                    .limit(2)
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            for (DocumentSnapshot snapshot:queryDocumentSnapshots)
                                            {
                                                QuesionItems quesionItems=snapshot.toObject(QuesionItems.class);
                                                question.add(quesionItems);
                                            }
                                            nextquestion();
                                        }
                                    });
                        }
                        else
                        {
                            for (DocumentSnapshot snapshot:queryDocumentSnapshots)
                            {
                                QuesionItems quesionItems=snapshot.toObject(QuesionItems.class);
                                question.add(quesionItems);
                            }
                            nextquestion();
                        }
                    }
                });
//        firestore.collection("catagories")
//                .document(catagoriId)
//                .collection("question")
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                        question.clear();
//                        for (DocumentSnapshot snapshot:value.getDocuments())
//                        {
//                            QuesionItems quesionItems=snapshot.toObject(QuesionItems.class);
//                            question.add(quesionItems);
//                        }
//                    }
//                });
        countdowntimer();
    }
    void defaultset()
    {
        binding.option1.setBackgroundResource(R.drawable.background_for_field);
        binding.option2.setBackgroundResource(R.drawable.background_for_field);
        binding.option3.setBackgroundResource(R.drawable.background_for_field);
        binding.option4.setBackgroundResource(R.drawable.background_for_field);
    }
    void countdowntimer()
    {
          countDownTimer=new CountDownTimer(30000,1000) {
              @Override
              public void onTick(long millisUntilFinished) {
                  binding.runningtime.setText(""+millisUntilFinished/1000);
              }

              @Override
              public void onFinish() {
                      index++;
                      if(index==question.size())
                          countDownTimer.cancel();
                      else nextquestion();
              }
          };
    }
    void nextquestion()
    {
        if(countDownTimer!=null)countDownTimer.cancel();
        countDownTimer.start();
        if(index<question.size())
        {
            defaultset();
            questioncount();
            q=question.get(index);
            binding.question.setText(q.getQuestion());
            binding.option1.setText(q.getOption1());
            binding.option2.setText(q.getOption2());
            binding.option3.setText(q.getOption3());
            binding.option4.setText(q.getOption4());
        }
        else
        {
            Toast.makeText(QuestionsAnswer.this,"End of quesion",Toast.LENGTH_LONG).show();
        }
    }
    void questioncount()
    {
        binding.questioncount.setText(""+(index+1)+"/"+question.size());
    }
    public void click(View view) {
        switch (view.getId())
        {
            case R.id.option1:
            case R.id.option2:
            case R.id.option3:
            case R.id.option4:
                if(countDownTimer!=null)countDownTimer.cancel();
               TextView txt=(TextView)view;
               checkanswer(txt);
                break;
            case R.id.nextquestion:
                index++;
                enable();
                if(index<question.size())nextquestion();
                else
                {
                    Toast.makeText(QuestionsAnswer.this,"End of quesion",Toast.LENGTH_LONG).show();
                    if (countDownTimer!=null)countDownTimer.cancel();
                    disableall();
                    Intent intent=new Intent(QuestionsAnswer.this,ResultActivity.class);
                    intent.putExtra("correctAns",correctans);
                    intent.putExtra("total",question.size());
                    startActivity(intent);
                }
                break;
        }
    }
    void checkanswer(TextView textView)
    {
        String tem=textView.getText().toString();
        if(tem.equals(q.getAnswer()))
        {
            correctans++;
            textView.setBackgroundResource(R.drawable.option_correct);
            disable();
        }
        else
        {
            textView.setBackgroundResource(R.drawable.option_wrong);
            correcans();
            disable();
        }
    }
    void correcans()
    {
        String answer=q.getAnswer();
        if(answer.equals(q.getOption1()))binding.option1.setBackgroundResource(R.drawable.option_correct);
        if(answer.equals(q.getOption2()))binding.option2.setBackgroundResource(R.drawable.option_correct);
        if(answer.equals(q.getOption3()))binding.option3.setBackgroundResource(R.drawable.option_correct);
        if(answer.equals(q.getOption4()))binding.option4.setBackgroundResource(R.drawable.option_correct);
    }
    void disable()
    {
        if(q.getOption1()!=q.getAnswer())binding.option1.setEnabled(false);
        if(q.getOption2()!=q.getAnswer())binding.option2.setEnabled(false);
        if(q.getOption3()!=q.getAnswer())binding.option3.setEnabled(false);
        if(q.getOption4()!=q.getAnswer())binding.option4.setEnabled(false);
    }
    void enable()
    {
        binding.option1.setEnabled(true);
        binding.option2.setEnabled(true);
        binding.option3.setEnabled(true);
        binding.option4.setEnabled(true);
    }
    void disableall()
    {
        binding.option1.setEnabled(false);
        binding.option2.setEnabled(false);
        binding.option3.setEnabled(false);
        binding.option4.setEnabled(false);
    }
    public void quizagain(View view) {
        startActivity(new Intent(QuestionsAnswer.this,MainActivity.class));
        finish();
    }
}