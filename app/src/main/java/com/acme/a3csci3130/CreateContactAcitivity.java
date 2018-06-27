package com.acme.a3csci3130;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

public class CreateContactAcitivity extends Activity {

    private Button submitButton;
    private EditText nameField, numberField,addressField;
    private TextView errorText;
    private Spinner primaryBField,provinceField;
    private MyApplicationData appState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact_acitivity);
        //Get the app wide shared variables
        appState = ((MyApplicationData) getApplicationContext());
        submitButton = (Button) findViewById(R.id.submitButton);
        nameField = (EditText) findViewById(R.id.name);
        numberField = (EditText) findViewById(R.id.number);
        addressField = (EditText) findViewById(R.id.address);
        primaryBField = (Spinner) findViewById(R.id.primaryB);
        provinceField = (Spinner) findViewById(R.id.province);
        errorText = (TextView) findViewById(R.id.errorText2);
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
    }

    public void submitInfoButton(View v) {
        //each entry needs a unique ID
        String businessID = appState.firebaseReference.push().getKey();
        String name = nameField.getText().toString();
        String number = numberField.getText().toString();
        String addr = addressField.getText().toString();
        String prov = provinceField.getSelectedItem().toString();
        String type = primaryBField.getSelectedItem().toString();
        Business busy = new Business(businessID,number,name,type,addr,prov);

        appState.firebaseReference.child(businessID).setValue(busy,new DatabaseReference.CompletionListener() {
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
}
