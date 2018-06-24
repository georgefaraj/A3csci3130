package com.acme.a3csci3130;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class DetailViewActivity extends Activity {

    private EditText nameField, numberField,addressField;
    private Spinner primaryBField,provinceField;
    private MyApplicationData appState;
    Business receivedBusyInfo;

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

    public void updateContact(View v){
        String businessID = receivedBusyInfo.uid;
        String name = nameField.getText().toString();
        int number = Integer.parseInt( numberField.getText().toString() );
        String addr = addressField.getText().toString();
        String prov = provinceField.getSelectedItem().toString();
        String type = primaryBField.getSelectedItem().toString();
        Business busy = new Business(businessID,number,name,type,addr,prov);
        appState.firebaseReference.child(receivedBusyInfo.uid.toString()).setValue(busy);

        finish();
    }

    
    public void eraseContact(View v)
    {
        appState.firebaseReference.child(receivedBusyInfo.uid.toString()).removeValue();

        finish();
    }
}
