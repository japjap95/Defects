package com.example.testing_load;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VehicleSelector extends Activity implements OnClickListener {

	String result = null, url_main = null, name = null, manf = null, model = null, pin = null, refno = null;
	ArrayAdapter<String> lstItems;
    ArrayAdapter<String> lstItems1;
	public ListView lvTopCat;
	int[] data_id;
	int pos = 0, id = 0, b=0;
    EditText et;
    TextView tmodel, tmake;
    String[] str;
    Button btn;
	Button btnLogout;
    ProgressDialog dialog;
    String[] datainfo = new String[16];
    String[] datainfo1 = new String[16];
    Object obj, obj1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vehicle_selector);
		
		lstItems = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1);
        lstItems1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1);
		lvTopCat = (ListView) findViewById(R.id.list1);
		et = (EditText) findViewById(R.id.EditText01);
		tmodel = (TextView) findViewById(R.id.model);
		tmake = (TextView) findViewById(R.id.menu);
		tmodel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		tmake.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		btn = (Button) findViewById(R.id.next);
		btn.setOnClickListener(this);
		btn.setEnabled(false);
		btnLogout = (Button) findViewById(R.id.logout);
		pin = getIntent().getStringExtra("pin");


		url_main = "geteqptlist.ashx";
		validate_vehicle();
		
	}

	public void onBackPressed() {
		new AlertDialog.Builder(this)
		.setMessage("Are you sure you want to exit?")
		.setCancelable(false)
		.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						VehicleSelector.this.finish();
					}
				}).setNegativeButton("No", null).show();
	}
	
	public void validate_vehicle() {

		new DownloadVehicles().execute(url_main);
	}
	
	public void validate_vehicle_details() {

		new DownloadVehicleDetails().execute(url_main);
	}

	public class DownloadVehicles extends AsyncTask<String, Integer, String> {
		@Override
		protected void onPreExecute() {
			
			super.onPreExecute();

			ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			if (conMgr.getActiveNetworkInfo() == null) {
				Toast.makeText(getApplicationContext(), "No Internet Access!",
						Toast.LENGTH_LONG).show();
				Intent n = new Intent(getApplicationContext(),
						DefectSelection.class);
				startActivity(n);
			}

			dialog = ProgressDialog.show(VehicleSelector.this, "",
					"Loading. Please wait...", true);
			dialog.show();
			
		};

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			lstItems.clear();

			JsonParser jParser = new JsonParser();
			JSONObject json = jParser.getJSONFromUrl(url_main);

			try {

				JSONArray dataArray = json.getJSONArray("data");
				data_id = new int[dataArray.length()];
				for (int i = 0; i < dataArray.length(); i++) {
					JSONObject jo = (JSONObject) dataArray.get(i);
					refno = jo.getString("refno"); 
					
					id = jo.getInt("ID");
					String com = refno+id;
					String str = Integer.toString(id);
					
					datainfo[i]= com;
                    lstItems.add(refno);


                    //backup data for list item values
                    datainfo1[i] = refno;
                    //Log.i("ID", datainfo1[i]);

					Log.i("ID", str);
					data_id[i] = id;
				}



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
			
			str = new String[lstItems.getCount()];
			if (result.equals("OK")) {
				lvTopCat.setAdapter(lstItems);
				
				for(int i = 0; i < lstItems.getCount(); i++){
				     String litem = (String)lstItems.getItem(i);
				     str[i] = litem;
				}
				
				lvTopCat.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> adapter, View v,
							int position, long arg3) {
						name = adapter.getItemAtPosition(position).toString();
						for(String log : datainfo)
						{
							if(log.contains(name)){
								Log.i("Hello", log);
								char id = log.charAt(log.length() - 1);
								b = Character.getNumericValue(id);
								url_main = "geteqptdetail.ashx?id="+b;
								validate_vehicle_details();
							}
						}
						
						
						
					}
				});
			}
			
			
			
			et.addTextChangedListener(new TextWatcher() {
	             
	            @Override
	            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
	                // When user changed the Text
	                //VehicleSelector.this.lstItems.getFilter().filter(cs.toString());
	                //VehicleSelector.this.lstItems.getFilter().
                   // Log.i("TEST", cs.toString());
	            }
	             
	            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
	                    int arg3) {
	                // TODO Auto-generated method stub
	                 
	            }
	             
	            @Override
	            public void afterTextChanged(Editable arg0) {
	                // TODO Auto-generated method stub
                   String text = et.getText().toString();
                   lstItems.clear();

                    for(int x = 0; x<datainfo1.length; x++){
                        if(datainfo1[x].toString().toLowerCase().contains(text)) {
                            lstItems.add(datainfo1[x]);
                        }
                    }
	            }
	        });
			
		}
	}

	public class DownloadVehicleDetails extends AsyncTask<String, Integer, String> {
		@Override
		protected void onPreExecute() {

			super.onPreExecute();

			ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			if (conMgr.getActiveNetworkInfo() == null) {
				Toast.makeText(getApplicationContext(), "No Internet Access!",
						Toast.LENGTH_LONG).show();
				// SEH - surely if no internet access we need to exit the app.
				// Cannot proceed to Defect Selection
				Intent n = new Intent(getApplicationContext(),
                        DefectEntry.class);
						//DefectSelection.class);
				startActivity(n);
			}

			dialog = ProgressDialog.show(VehicleSelector.this, "",
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
				manf = dataObject.getString("manf");
				model =dataObject.getString("model");
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
			tmake.setText(manf);
			tmodel.setText(model);
			
			if(tmake.getText().toString().length()>0 && tmodel.getText().toString().length()>0){
				btn.setEnabled(true);
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		//Intent n = new Intent(getApplicationContext(), DefectSelection.class);
        Intent n = new Intent(getApplicationContext(), DefectEntry.class);
		//Log.i("TEST", ""+pos);
		n.putExtra("vehicle_id", ""+pos);
		n.putExtra("pin", pin);
		startActivity(n);
		finish();
	}

}
