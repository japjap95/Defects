package com.example.testing_load;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class NoteDefectEntry extends Activity implements OnClickListener {

	Button savenotes, cancelnotes;
	public ListView lvTopCat;
	String url_main = null, stril = null, add_note = null, txttl = null,
			txtsl = null, txtil = null, name = null, defectid_tl = null, defectid_sl = null, defectid_il = null,vech_id = null, pin = null;
	ArrayAdapter<String> lstItems;
	int[] data_id;
	int id;
	final Context context = this;
	ProgressDialog dialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_entry);

		savenotes = (Button) findViewById(R.id.savenotes);
		cancelnotes = (Button) findViewById(R.id.cancelnotes);
		lvTopCat = (ListView) findViewById(R.id.list1);
		lstItems = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1);

		savenotes.setOnClickListener(this);
		cancelnotes.setOnClickListener(this);
		stril = getIntent().getStringExtra("stril");
		txttl = getIntent().getStringExtra("txttl_base");
		txtsl = getIntent().getStringExtra("txtsl_base");
		txtil = getIntent().getStringExtra("txtil_base");
		defectid_tl = getIntent().getStringExtra("defectid_tl");
		defectid_sl = getIntent().getStringExtra("defectid_sl");
		defectid_il = getIntent().getStringExtra("defectid_il");
		vech_id = getIntent().getStringExtra("vech_id");
		pin = getIntent().getStringExtra("pin");

		url_main = "http://www.fleet-maintenance-systems.com/defecttest/getnotelist.ashx?id="
				+ stril;
		loadLV_note();
	}
	
	public void onBackPressed() {
		new AlertDialog.Builder(this)
		.setMessage("Are you sure you want to exit?")
		.setCancelable(false)
		.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						NoteDefectEntry.this.finish();
					}
				}).setNegativeButton("No", null).show();
	}

	public void loadLV_note() {

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

			dialog = ProgressDialog.show(NoteDefectEntry.this, "",
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

				while ((next = bufferedReader.readLine()) != null) {
					JSONArray ja = new JSONArray(next);

					data_id = new int[ja.length()];

					for (int i = 0; i < ja.length(); i++) {
						JSONObject jo = (JSONObject) ja.get(i);
						items.add(jo.getString("text"));
						// lstItems.add(jo.getString("text"));
						id = jo.getInt("id");
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
			lstItems.clear();
			
			if(items.size() == 0){
				Toast.makeText(getApplicationContext(), "No Data Found. Please Add New Note.",
						Toast.LENGTH_LONG).show();
			}
			for (int i = 0; i < items.size(); i++) {
				lstItems.add(items.get(i));
			}

			lvTopCat.setAdapter(lstItems);
			lvTopCat.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> adapter, View v,
						int position, long arg3) {
					//int pos = data_id[position];
					name = adapter.getItemAtPosition(position).toString();

					Intent n = new Intent(getApplicationContext(),
							DefectSelection.class);
					n.putExtra("noteadded", name);
					n.putExtra("txttl_base", txttl);
					n.putExtra("txtsl_base", txtsl);
					n.putExtra("txtil_base", txtil);
					n.putExtra("stril", stril);
					n.putExtra("defectid_tl", defectid_tl);
					n.putExtra("defectid_sl", defectid_sl);
					n.putExtra("defectid_il", defectid_il);
					n.putExtra("vech_id", vech_id);
					n.putExtra("pin", pin);
					startActivity(n);
					finish();
				}
			});

		}
	}

	@SuppressLint("InflateParams")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.savenotes:

			LayoutInflater layoutInflater = LayoutInflater.from(context);
			View promptView = layoutInflater.inflate(R.layout.promts, null);
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					context);

			alertDialogBuilder.setView(promptView);
			final EditText input = (EditText) promptView.findViewById(R.id.userInput);
			alertDialogBuilder
					.setCancelable(false)
					.setPositiveButton("Save",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									add_note = "http://www.fleet-maintenance-systems.com/defecttest/addnote.ashx?id="
											+ stril
											+ "&text="
											+ Uri.encode(input.getText().toString());

									Thread thread = new Thread(new Runnable() {
										@Override
										public void run() {
											try {
												DefaultHttpClient httpclient = new DefaultHttpClient();
												HttpGet httpget = new HttpGet(
														add_note);
												try {
													HttpResponse response = httpclient
															.execute(httpget);
													InputStream content = response
															.getEntity()
															.getContent();
													content.close();
													Intent n = new Intent(
															getApplicationContext(),
															NoteDefectEntry.class);
													n.putExtra("noteadded", name);
													n.putExtra("txttl_base", txttl);
													n.putExtra("txtsl_base", txtsl);
													n.putExtra("txtil_base", txtil);
													n.putExtra("stril", stril);
													n.putExtra("defectid_tl", defectid_tl);
													n.putExtra("defectid_sl", defectid_sl);
													n.putExtra("defectid_il", defectid_il);
													n.putExtra("vech_id", vech_id);
													n.putExtra("pin", pin);
													startActivity(n);
													finish();
												} catch (ClientProtocolException e) {
													// TODO Auto-generated catch
													// block
													e.printStackTrace();
												} catch (IOException e) {
													// TODO Auto-generated catch
													// block
													e.printStackTrace();
												}
											} catch (Exception e) {
												e.printStackTrace();
											}
										}
									});

									thread.start();

								}

							})
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							});
			AlertDialog alertD = alertDialogBuilder.create();
			alertD.show();
			break;
		case R.id.cancelnotes:
			Intent i = new Intent(getApplicationContext(),
					DefectSelection.class);
			i.putExtra("txttl_base", txttl);
			i.putExtra("txtsl_base", txtsl);
			i.putExtra("txtil_base", txtil);
			i.putExtra("stril", stril);
			i.putExtra("defectid_tl", defectid_tl);
			i.putExtra("defectid_sl", defectid_sl);
			i.putExtra("defectid_il", defectid_il);
			i.putExtra("vech_id", vech_id);
			i.putExtra("pin", pin);
			startActivity(i);
			finish();
			break;
		}
	}
}
