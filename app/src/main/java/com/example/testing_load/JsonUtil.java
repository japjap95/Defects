package com.example.testing_load;

import android.util.Log;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class JsonUtil {
	
	static String save_entry = null;
	static JSONObject jsonObj;
	
	public static String toJSon(String eqp, String dcl, String dfn, String datenow, String pin) {
		
		try {
			
			jsonObj = new JSONObject();

	        JSONObject jsonDtl = new JSONObject(); 
	        jsonDtl.put("eqp", eqp);
	        jsonDtl.put("dcl", dcl);
	        jsonDtl.put("dfn", dfn);
	        
	        jsonObj.put("dtl", jsonDtl);

	        
	        JSONObject jsonHdr = new JSONObject(); 
	        jsonHdr.put("id", "0");
	        jsonHdr.put("opr", pin);
	        jsonHdr.put("eqp", eqp);
	        jsonHdr.put("date", datenow);
	        
	        jsonObj.put("hdr", jsonHdr);

	        
	        // and finally we add the phone number
	        // In this case we need a json array to hold the java list
//	        JSONArray jsonArr = new JSONArray();
//
//	        for (PhoneNumber pn : person.getPhoneList() ) {
//	            JSONObject pnObj = new JSONObject();
//	            pnObj.put("num", pn.getNumber());
//	            pnObj.put("type", pn.getType());
//	            jsonArr.put(pnObj);
//	        }
//
//	        jsonObj.put("phoneNumber", jsonArr);
	        Log.d("tag", jsonObj.toString(4));
	       
	        save_entry = "http://www.webfleetsystems.co.uk/defects/submitdefect.ashx";
			
	        HttpClient client = new DefaultHttpClient();
	        HttpPost post = new HttpPost(save_entry);
	        StringEntity se;
			try {
				se = new StringEntity( jsonObj.toString());
				se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
		        post.setHeader("Accept", "application/json");
		        post.setHeader("Content-type", "application/json");
		        post.setEntity(se);
		        try {
					client.execute(post);
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	        

	        return jsonObj.toString();

	    }
	    catch(JSONException ex) {
	        ex.printStackTrace();
	    }
		
		return null;
	}

}
