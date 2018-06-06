package com.drivewell.drivewell.database.firestore;

import android.support.annotation.NonNull;
import android.util.Log;

import com.drivewell.drivewell.constants.FirebaseEndpoints;
import com.drivewell.drivewell.constants.InitialInformation;
import com.drivewell.drivewell.database.IDatabase;
import com.drivewell.drivewell.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by abid on 2/18/18.
 */

public class Firestore implements IDatabase {

    public static Firestore instance;
    private static final String TAG ="FireStore";
    private FirebaseFirestore firebaseFirestore;
    private List<User> usersList;

    public Firestore() {
        init();
        usersList=new LinkedList<>();
    }

    public static Firestore getInstance() {
        return (instance==null)?new Firestore():instance;
    }

    public void init(){
        firebaseFirestore=FirebaseFirestore.getInstance();
    }


    @Override
    public void createUser(User user) {
        firebaseFirestore.collection(FirebaseEndpoints.USERS).document(user.getUserId()).set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG+"Create","Created New User");
                    }
                });
    }

    @Override
    public void insertdata(Object o) {
        firebaseFirestore.collection(FirebaseEndpoints.USERS).add(o).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.i(TAG+"Insert",documentReference.getId());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i(TAG,e.toString());
            }
        });
    }


    @Override
    public void updatedatabase(Object o) {

    }

    @Override
    public Object retrieveData(Object o) {
        CollectionReference users=firebaseFirestore.collection(FirebaseEndpoints.USERS);

          users.whereEqualTo("name", "Md Abid Hasan")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d(TAG+"retrive", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        return new Object();
    }

    //TODO FIX USER NULL POINTER EXCEPTION
    @Override
    public void getUserInformation(String userId) {
        CollectionReference users=firebaseFirestore.collection(FirebaseEndpoints.USERS);
        users.whereEqualTo("userId", userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            usersList = task.getResult().toObjects(User.class);
                           /* InitialInformation.user=usersList.get(0);*/
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


    }

    @Override
    public void deleteData(Object o) {

    }
}
