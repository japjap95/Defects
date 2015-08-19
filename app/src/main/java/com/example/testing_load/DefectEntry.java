package com.example.testing_load;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DefectEntry extends Activity implements View.OnClickListener {

    Button nil, report;
    String pin = null, vech_id = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.defect_entry);

        pin = getIntent().getStringExtra("pin");
        vech_id = getIntent().getStringExtra("vech_id");

        nil = (Button) findViewById(R.id.nil);
        report = (Button) findViewById(R.id.report);

        nil.setOnClickListener(this);
        report.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.nil:
                break;
            case R.id.report:
                Intent i = new Intent(getApplicationContext(),
                        DefectSelection.class);
                i.putExtra("vech_id", vech_id);
                i.putExtra("pin", pin);
                startActivity(i);
                finish();
                break;
        }
    }

}
