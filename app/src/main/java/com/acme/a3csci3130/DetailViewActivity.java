package com.acme.a3csci3130;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

/**
 * The DetailViewActivity program shows all the details of the business selected
 * The business selection occurs in MainActivity and from here this business can be changed or deleted from the Firebase DB
 *
 * It defines: onCreate(Bundle savedInstanceState), updateContact(View v) and eraseContact(View v)
 *
 * The code is forked from Juliano Franz A3CSCI3130 Branch and modified by me, George F.
 *
 * @author  George Faraj
 * @since   2018-06-28
 */
public class DetailViewActivity extends Activity {

    private EditText nameField, numberField,addressField;
    private Spinner primaryBField,provinceField;
    private TextView errorText;
    private MyApplicationData appState;
    Business receivedBusyInfo;

    /**
     * The onCreate method starts up the DetailViewActivity
     * A very important part of this method is that it initializes all the text inputs
     * The inputs are global private variables that all the other methods use
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        appState = ((MyApplicationData) getApplicationContext());
        receivedBusyInfo = (Business)getIntent().getSerializableExtra("Business");

        nameField = (EditText) findViewById(R.id.name);
        numberField = (EditText) findViewById(R.id.number);
        addressField = (EditText) findViewById(R.id.address);
        primaryBField = (Spinner) findViewById(R.id.primaryB);
        provinceField = (Spinner) findViewById(R.id.province);
        errorText = (TextView) findViewById(R.id.errorText);
        errorText.setVisibility(View.INVISIBLE);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> PBadapter = ArrayAdapter.createFromResource(this,
                R.array.business_types, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> PRadapter = ArrayAdapter.createFromResource(this,
                R.array.provinces, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        PBadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        PRadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        primaryBField.setAdapter(PBadapter);
        provinceField.setAdapter(PRadapter);

        int spinnerPosition;

        if(receivedBusyInfo != null){
            nameField.setText(receivedBusyInfo.name);
            numberField.setText(String.valueOf( receivedBusyInfo.number ));
            addressField.setText(receivedBusyInfo.address);

            spinnerPosition = PBadapter.getPosition(receivedBusyInfo.primaryB);
            primaryBField.setSelection(spinnerPosition);

            spinnerPosition = PRadapter.getPosition(receivedBusyInfo.province);
            provinceField.setSelection(spinnerPosition);
        }
    }

    /**
     * The updateContact method gets all the data from the global text inputs
     * It then sends it to the Firebase DB and if it is rejected, it will display an error message for the user
     * NOTE: All error checking is done via the Rules defined in Firebase
     */
    public void updateContact(View v){
        String businessID = receivedBusyInfo.uid;
        String name = nameField.getText().toString();
        String number =  numberField.getText().toString();
        String addr = addressField.getText().toString();
        String prov = provinceField.getSelectedItem().toString();
        String type = primaryBField.getSelectedItem().toString();
        Business busy = new Business(businessID,number,name,type,addr,prov);
        appState.firebaseReference.child(receivedBusyInfo.uid.toString()).setValue(busy,new DatabaseReference.CompletionListener() {
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                if(error == null) {
                    finish();
                }
                else{
                    errorText.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * The eraseContact method simply calls a removeValue function to delete the business that was received from MainActivity
     */
    public void eraseContact(View v)
    {
        appState.firebaseReference.child(receivedBusyInfo.uid.toString()).removeValue();

        finish();
    }
}
