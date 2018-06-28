package com.acme.a3csci3130;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



/**
 * The MyApplicationData program acts as pseudo singleton class to ensure all classes can access the database
 *
 * The code is forked from Juliano Franz A3CSCI3130 Branch and modified by me, George F.
 *
 * @author  George Faraj
 * @since   2018-06-28
 */
/**
 * Created by Franz on 2017-05-31.
 */
public class MyApplicationData extends Application {

    public DatabaseReference firebaseReference;
    public FirebaseDatabase firebaseDBInstance;

}
