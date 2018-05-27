package com.tamoghnodas.android.tododemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.HashMap;
import java.util.Map;



public class ScheduleActivity extends AppCompatActivity {
    public static final String TASK = "task";
    public static final String TAG = "ScheduleActivity";


    private DocumentReference mDocRef = FirebaseFirestore.getInstance().document
            ("sampleData/tasks");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText task =findViewById(R.id.editText);
        final Button saveButton = findViewById(R.id.save_button);
        Log.d(TAG,"initialized successfully");
        // Enable Send button when there's text to send
        task.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    saveButton.setEnabled(true);
                } else {
                    saveButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newTask = task.getText().toString();
                if(newTask.isEmpty())
                    return;
                Map<String,Object> dataToSave = new HashMap<String,Object>();
                dataToSave.put(TASK,newTask);
                mDocRef.set(dataToSave).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG,"Document has been saved");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG,"Document was not saved",e);
                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDocRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot,@Nullable FirebaseFirestoreException e) {
                Work toDoTasks;
                if (documentSnapshot.exists())
                    toDoTasks = documentSnapshot.toObject(Work.class);

                else if (e != null)
                    Log.e(TAG, "Got an exception", e);
            }
        });
    }
}
