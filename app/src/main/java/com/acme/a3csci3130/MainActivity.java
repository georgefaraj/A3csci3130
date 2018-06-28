package com.acme.a3csci3130;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.FirebaseDatabase;


/**
 * The MainActivity program acts as the home page for the app
 * that performs CRUD functions for the a business class defined in Assignment 3
 *
 * It defines: onCreate(Bundle savedInstanceState) and createContactButton(View v)
 *
 * The code is forked from Juliano Franz A3CSCI3130 Branch and modified by me, George F.
 *
 * @author  George Faraj
 * @since   2018-06-28
 */
public class MainActivity extends Activity {


    private ListView businessListView;
    private FirebaseListAdapter<Business> firebaseAdapter;

    /**
     * The onCreate method starts up the app and initializes the connection to the database
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get the app wide shared variables
        MyApplicationData appData = (MyApplicationData)getApplication();

        //Set-up Firebase
        appData.firebaseDBInstance = FirebaseDatabase.getInstance();
        appData.firebaseReference = appData.firebaseDBInstance.getReference("businesses");

        //Get the reference to the UI contents
        businessListView = (ListView) findViewById(R.id.listView);

        //Set up the List View
       firebaseAdapter = new FirebaseListAdapter<Business>(this, Business.class,
                android.R.layout.simple_list_item_1, appData.firebaseReference) {
            @Override
            protected void populateView(View v, Business model, int position) {
                TextView contactName = (TextView)v.findViewById(android.R.id.text1);
                contactName.setText(model.name);
            }
        };
        businessListView.setAdapter(firebaseAdapter);
        businessListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // onItemClick method is called everytime a user clicks an item on the list
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Business person = (Business) firebaseAdapter.getItem(position);
                showDetailView(person);
            }
        });
    }

    /**
     * The createContactButton method switches the user from the MainActivity to the CreateBusinessActivity
     */
    public void createContactButton(View v)
    {
        Intent intent=new Intent(this, CreateBusinessActivity.class);
        startActivity(intent);
    }

    private void showDetailView(Business busy)
    {
        Intent intent = new Intent(this, DetailViewActivity.class);
        intent.putExtra("Business", busy);
        startActivity(intent);
    }



}
