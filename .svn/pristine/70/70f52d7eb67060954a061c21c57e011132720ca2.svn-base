package com.example.testing_load;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginMenu extends Activity implements OnClickListener {
	
	String result = null, pin = null , getPin = null , url_main = null, deviceid = null;
	EditText txtPin;
	Button btnLogin;
	TextView errormsg, device_id;
	ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginmenu);
		
		txtPin = (EditText) findViewById(R.id.txtPin);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		errormsg = (TextView) findViewById(R.id.errormsg);
		device_id = (TextView) findViewById(R.id.deviceID);
		btnLogin.setOnClickListener(this);

		
		// DEVICE INFORMATION - JOSH
				TelephonyManager telephonyManager = null;
				telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

				if (telephonyManager.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE) {
		            //Tablet
					deviceid = Secure.getString(this.getContentResolver(),
		                    Secure.ANDROID_ID);

		        } else {
		            //Mobile
		        	deviceid = telephonyManager.getDeviceId();

		        }


				device_id.setText("Device ID:"+deviceid);
		
	}
	
	public void onBackPressed() {
		new AlertDialog.Builder(this)
		.setMessage("Are you sure you want to exit?")
		.setCancelable(false)
		.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						LoginMenu.this.finish();
					}
				}).setNegativeButton("No", null).show();
	}
	
	public void validate_login() {

		new DownloadPinInformation().execute(url_main);
	}
	
	public class DownloadPinInformation extends AsyncTask<String, Integer, String> {
		@Override
		protected void onPreExecute() {
			
			super.onPreExecute();

			ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			if (conMgr.getActiveNetworkInfo() == null) {
				Toast.makeText(getApplicationContext(), "No Internet Access - application cannot proceed!",
						Toast.LENGTH_LONG).show();
				
				// SEH: Surely the app should now exit,
				// Why proceed to show Defect Selection Intent if there is no Internet Access??
				LoginMenu.this.finish();
				return;
				/*
				Intent n = new Intent(getApplicationContext(),
						DefectSelection.class);
				startActivity(n);
				*/
			}

			dialog = ProgressDialog.show(LoginMenu.this, "",
					"Loading. Please wait...", true);
			dialog.show();

		};

		@Override
		protected String doInBackground(String... params) {
		    // TODO Auto-generated method stub

			JsonParser jParser = new JsonParser();
			JSONObject json = jParser.getJSONFromUrl(url_main);
			
			try {
				JSONObject dataObject = json.getJSONObject("data");
				pin = dataObject.getString("PIN");
				result = json.getString("result");
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return result;
		}

		@Override
		protected void onPostExecute(String result) {
		    // TODO Auto-generated method stub
			dialog.dismiss();
			if(pin.equals(getPin) && result.equals("OK")){
				Intent n = new Intent(getApplicationContext(), VehicleSelector.class);
				n.putExtra("pin", pin);
				startActivity(n);
				finish();
			}else{
				errormsg.setTextColor(Color.RED);
				errormsg.setText("Please enter a valid PIN.");
			}
		
		}
		}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		getPin = txtPin.getText().toString();
		if(getPin.length()!=4){
			//Toast.makeText(getApplicationContext(), "Please Input PIN at least 4 characters",Toast.LENGTH_LONG).show();
			errormsg.setTextColor(Color.RED);
			errormsg.setText("Pin must contain 4 digits.");
		}
		else{
			//url_main = "http://www.webfleetsystems.co.uk/getoperator.ashx?pin="+getPin;
			url_main = "getoperator.ashx?pin="+getPin;
			validate_login();
		}
	}

}
