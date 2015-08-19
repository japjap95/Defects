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
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends Activity {

	/** Called when the activity is first created. */

	String url_main = null;
	ArrayAdapter<String> lstItems;
	public ListView lvTopCat;
	int[] data_id;
	ProgressDialog dialog;
	String txtid = null, txttl = null, txtsl = null, txtil = null,
			level = null, level_last = null, txttl_id = null, txtsl_id = null, vech_id = null, pin = null,
			txtil_id = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		lstItems = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1);
		lvTopCat = (ListView) findViewById(R.id.list1);
		txtid = getIntent().getStringExtra("defectid");
		txttl = getIntent().getStringExtra("txttl_base");
		txtsl = getIntent().getStringExtra("txtsl_base");
		txttl_id = getIntent().getStringExtra("defectid_tl");
		txtsl_id = getIntent().getStringExtra("defectid_sl");
		txtil_id = getIntent().getStringExtra("defectid_il");
		level_last = getIntent().getStringExtra("level_last");
		vech_id = getIntent().getStringExtra("vech_id");
		pin = getIntent().getStringExtra("pin");

		if (level_last.contains("1")) {
			url_main = "http://www.fleet-maintenance-systems.com/defecttest/getdefectlist.ashx";
		} else if (level_last.contains("2")) {
			url_main = "http://www.fleet-maintenance-systems.com/defecttest/getdefectlist.ashx?id="
					+ txttl_id;
		} else {
			url_main = "http://www.fleet-maintenance-systems.com/defecttest/getdefectlist.ashx?id="
					+ txtsl_id;
		}

		loadLV_first();

	}

	public void onBackPressed() {
		new AlertDialog.Builder(this)
		.setMessage("Are you sure you want to exit?")
		.setCancelable(false)
		.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						MainActivity.this.finish();
					}
				}).setNegativeButton("No", null).show();
	}

	public void loadLV_first() {

		new DownloadFilesTask().execute(url_main);
	}

	private class DownloadFilesTask extends
			AsyncTask<String, Integer, ArrayList<String>> {

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

			dialog = ProgressDialog.show(MainActivity.this, "",
					"Loading. Please wait...", true);
			dialog.show();
		}

		protected ArrayList<String> doInBackground(String... strings) {
			ArrayList<String> items = new ArrayList<String>();

			try {
				URL url = new URL(url_main);
				HttpURLConnection urlConnection = (HttpURLConnection) url
						.openConnection();
				urlConnection.setRequestMethod("GET");
				urlConnection.connect();
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(urlConnection.getInputStream()));
				String next;

				lstItems.clear();

				while ((next = bufferedReader.readLine()) != null) {
					JSONArray ja = new JSONArray(next);

					data_id = new int[ja.length()];

					for (int i = 0; i < ja.length(); i++) {
						JSONObject jo = (JSONObject) ja.get(i);
						// items.add(jo.getString("text"));
						lstItems.add(jo.getString("text"));
						int id = jo.getInt("id");
						data_id[i] = id;
					}
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return items;
		}

		protected void onProgressUpdate(Integer... progress) {
			// setProgressPercent(progress[0]);
		}

		protected void onPostExecute(ArrayList<String> items) {
			dialog.dismiss();
			lvTopCat.setAdapter(lstItems);
			lvTopCat.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> adapter, View v,
						int position, long arg3) {
					int pos = data_id[position];
					String name = adapter.getItemAtPosition(position)
							.toString();
					Intent n = new Intent(getApplicationContext(),
							DefectSelection.class);

					n.putExtra("defectname", name);
					n.putExtra("defectid", "" + pos);
					n.putExtra("level_detect", level_last);
					n.putExtra("txttl_base", txttl);
					n.putExtra("txtsl_base", txtsl);
					n.putExtra("defectid_tl", txttl_id);
					n.putExtra("defectid_sl", txtsl_id);
					n.putExtra("defectid_il", txtil_id);
					n.putExtra("vech_id", vech_id);
					n.putExtra("pin", pin);

					startActivity(n);
					finish();
				}
			});

		}
	}

}