package com.example.testing_load;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class DefectEntry extends Activity implements View.OnClickListener {

    Button NO, YES;
    String pin, vmodel, vmake;
    int vehicle_id;
    String LOG_TAG = "LOG: " + getClass() + "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yes_or_no);

        pin = getIntent().getStringExtra("pin");
        vmake = getIntent().getStringExtra("vehicle_make");
        vmodel = getIntent().getStringExtra("vehicle_model");
        vehicle_id = getIntent().getIntExtra("vehicle_id", 0);

        NO = (Button) findViewById(R.id.NO);
        YES = (Button) findViewById(R.id.YES);

        NO.setOnClickListener(this);
        YES.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.NO: {
                Log.d(LOG_TAG, "CASE: NO");
                Intent n = new Intent(getApplicationContext(),
                        Confirmation.class);
                n.putExtra("vehicle_make", vmake);
                n.putExtra("vehicle_model", vmodel);
                n.putExtra("pin", pin);
                startActivity(n);
                break;
            }
            case R.id.YES: {
                Log.d(LOG_TAG, "CASE: YES");
                Intent i = new Intent(getApplicationContext(),
                        TierOneListView.class);
                i.putExtra("vehicle_make", vmake);
                i.putExtra("vehicle_model", vmodel);
                i.putExtra("pin", pin);
                startActivity(i);
                break;
            }
        }
    }

    public void onBackPressed(){

        Intent i = new Intent(getApplicationContext(), VehicleSelector.class);
        startActivity(i);
    }

}
