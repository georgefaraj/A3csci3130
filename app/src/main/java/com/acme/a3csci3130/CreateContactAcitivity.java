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

/**
 * The CreateContactAcitivity program shows all the details required to create a Business object
 * It also provides a method for the user to upload this information and will let them know if it got rejected
 *
 * It defines: onCreate(Bundle savedInstanceState) and submitInfoButton(View v)
 *
 * The code is forked from Juliano Franz A3CSCI3130 Branch and modified by me, George F.
 *
 * @author  George Faraj
 * @since   2018-06-28
 */
public class CreateContactAcitivity extends Activity {

    private Button submitButton;
    private EditText nameField, numberField,addressField;
    private TextView errorText;
    private Spinner primaryBField,provinceField;
    private MyApplicationData appState;

    /**
     * The onCreate method starts up the CreateContactActivity
     * A very important part of this method is that it initializes all the text inputs
     * The inputs are global private variables that all the other methods use
     */
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

    /**
     * The submitInfoButton method gets all the data from the global text inputs
     * It then sends it to the Firebase DB and if it is rejected, it will display an error message for the user
     * NOTE: All error checking is done via the Rules defined in Firebase
     */
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
