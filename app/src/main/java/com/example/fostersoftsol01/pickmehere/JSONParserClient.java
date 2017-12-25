package com.example.fostersoftsol01.pickmehere;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class JSONParserClient {
	
	static JSONObject jObj = null;
	
	//Method to HTTP request using HttpURLConnection using POST method//
	public JSONObject makaHTTPRequest(String url,String urlParameters) throws MalformedURLException, IOException, JSONException
	{
		HttpURLConnection conn=null;
        InputStream iStream=null;
        try
	    {
			String str;
	        conn = (HttpURLConnection) ( new URL(url)).openConnection();
			conn.setRequestMethod("POST");
		//	conn.setChunkedStreamingMode(0);
			conn.setRequestProperty("Content-Type", 
			           "application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", "" + 
		               Integer.toString(urlParameters.getBytes().length));
			conn.setRequestProperty("Content-Language", "en-US");  
			
			conn.setDoInput(true);
	        conn.setDoOutput(true);
	        
	        // conn.getOutputStream().write(urlParameters.getBytes());
	        //Send request
	        DataOutputStream wr = new DataOutputStream (
	                    conn.getOutputStream ());
	        wr.writeBytes (urlParameters);
	        wr.flush ();
	        wr.close ();
	        
	      //System.out.println("the response is "+conn.getResponseCode());
	//        OutputStream out = new BufferedOutputStream(conn.getOutputStream());
	//        out.write(("cid = "+categoryid).getBytes());
	        if(conn.getResponseCode()==200)
	        {
	        	iStream = conn.getInputStream();
	            
	            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
	            StringBuffer sb = new StringBuffer();
	            String line = "";
	            while( ( line = br.readLine()) != null){
	                sb.append(line);
	            }
	
	            str = sb.toString();
	            br.close();
	            iStream.close();
	         //   System.out.println("The string data is "+str);
	            JSONObject mainObj=new JSONObject(str);
	            jObj=mainObj;
	        }
	        else
	        {
	        	throw new IOException("Post failed with error code " + conn.getResponseCode());
	        }
	  }
	  finally {
          if (conn != null) {
              conn.disconnect();
          }     
	  	}//end of finally//
        
        
        return jObj;
	}//end of method


}
