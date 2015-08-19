package com.example.testing_load;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class DefectSelection extends Activity implements OnClickListener {

	Button btntl, btnsl, btnil, btnnotes, submit;
	String txtname = null, txtid = null, txttl = null, txtsl = null, noteadded = null, stril = null,
			txtil = null, dummy_holder = null, level = null, txttl_id = null, txtsl_id = null, txtil_id = null, vech_id = null, pin = null;
	TextView ettl, etsl, etil, t1, t2, t3, noteadd;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.defect_selection);

		submit = (Button) findViewById(R.id.button2);
		btntl = (Button) findViewById(R.id.btntoplevel);
		btnsl = (Button) findViewById(R.id.btnsublevel);
		btnil = (Button) findViewById(R.id.btnitemlevel);
		btnnotes = (Button) findViewById(R.id.btnnoteslevel);
		ettl = (TextView) findViewById(R.id.ettoplevel);
		etsl = (TextView) findViewById(R.id.etsublevel);
		etil = (TextView) findViewById(R.id.etitemlevel);
		noteadd = (TextView) findViewById(R.id.noteadd);
		ettl.setTextSize(20);
		etsl.setTextSize(20);
		etil.setTextSize(20);
		noteadd.setTextSize(20);
		t1 = (TextView) findViewById(R.id.t1);
		t2 = (TextView) findViewById(R.id.t2);
		t3 = (TextView) findViewById(R.id.t3);
		t1.setVisibility(View.GONE);
		t2.setVisibility(View.GONE);
		t3.setVisibility(View.GONE);

		btntl.setOnClickListener(this);
		btnsl.setOnClickListener(this);
		btnil.setOnClickListener(this);
		btnnotes.setOnClickListener(this);
		submit.setOnClickListener(this);
		
		pin = getIntent().getStringExtra("pin");
		vech_id = getIntent().getStringExtra("vech_id");
		txtname = getIntent().getStringExtra("defectname");
		txtid = getIntent().getStringExtra("defectid");
		level = getIntent().getStringExtra("level_detect");
		txttl = getIntent().getStringExtra("txttl_base");
		txtsl = getIntent().getStringExtra("txtsl_base");
		txtil = getIntent().getStringExtra("txtil_base");
		txttl_id = getIntent().getStringExtra("defectid_tl");
		txtsl_id = getIntent().getStringExtra("defectid_sl");
		txtil_id = getIntent().getStringExtra("defectid_il");
		noteadded = getIntent().getStringExtra("noteadded");
		stril = getIntent().getStringExtra("stril");
		
		if (level != null && level.length() != 0 && level.contains("1")) {
			txttl = txtname;
			txttl_id = txtid;
			t1.setText(txttl_id);
			ettl.setText(txttl);
		} else {
			ettl.setText(txttl);
			t1.setText(txttl_id);
		}
		if (level != null && level.length() != 0 && level.contains("2")) {
			txtsl = txtname;
			txtsl_id = txtid;
			t2.setText(txtsl_id);
			etsl.setText(txtsl);
		} else {
			t2.setText(txtsl_id);
			etsl.setText(txtsl);
		}
		if (level != null && level.length() != 0 && level.contains("3")) {
			txtil = txtname;
			txtil_id = txtid;
			t3.setText(txtil_id);
			etil.setText(txtil);
		} else {
			t3.setText(txtil_id);
			etil.setText(txtil);
		}
		if (noteadded != null && noteadded.length() != 0){
			noteadd.setText(noteadded);
		}
		
	}

	public void onBackPressed() {
		new AlertDialog.Builder(this)
		.setMessage("Are you sure you want to exit?")
		.setCancelable(false)
		.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						DefectSelection.this.finish();
					}
				}).setNegativeButton("No", null).show();
	}
	
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.button2:
			Time today = new Time(Time.getCurrentTimezone());
			today.setToNow();
			String datenow = today.year+today.month+today.monthDay+"T"+today.format("%k:%M:%S");
			JsonUtil.toJSon(vech_id, t3.getText().toString(), "dfn", datenow, pin);
			
		case R.id.btntoplevel:
			Intent a = new Intent(getApplicationContext(), MainActivity.class);
			a.putExtra("level_last", "1");
			a.putExtra("vech_id", vech_id);
			a.putExtra("pin", pin);
			startActivity(a);
			finish();
			break;
		case R.id.btnsublevel:
			Intent b = new Intent(getApplicationContext(), MainActivity.class);
			b.putExtra("level_last", "2");
			b.putExtra("defectid_tl", txttl_id);
			b.putExtra("txttl_base", txttl);
			b.putExtra("vech_id", vech_id);
			b.putExtra("pin", pin);
			startActivity(b);
			finish();
			break;
		case R.id.btnitemlevel:
			Intent c = new Intent(getApplicationContext(), MainActivity.class);
			c.putExtra("level_last", "3");
			c.putExtra("txttl_base", txttl);
			c.putExtra("txtsl_base", txtsl);
			c.putExtra("defectid_tl", txttl_id);
			c.putExtra("defectid_sl", txtsl_id);
			c.putExtra("vech_id", vech_id);
			c.putExtra("pin", pin);
			startActivity(c);
			finish();
			break;
		case R.id.btnnoteslevel:
			Intent d = new Intent(getApplicationContext(), NoteDefectEntry.class);
			if(stril == null){
				stril = t3.getText().toString();
			}
			d.putExtra("stril", stril);
			d.putExtra("txttl_base", txttl);
			d.putExtra("txtsl_base", txtsl);
			d.putExtra("txtil_base", etil.getText().toString());
			d.putExtra("defectid_tl", t1.getText().toString());
			d.putExtra("defectid_sl", t2.getText().toString());
			d.putExtra("defectid_il", t3.getText().toString());
			d.putExtra("vech_id", vech_id);
			d.putExtra("pin", pin);
			startActivity(d);
			finish();
			break;
		}
	}
}
