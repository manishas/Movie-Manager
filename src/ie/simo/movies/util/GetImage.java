package ie.simo.movies.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class GetImage {
	
	public static final String URL = "http://www.google.com/search?tbm=isch&hl=en&q=%s&biw=1366&bih=665";
	
	public String getImageResultHtml(String query){
		
		String dataAsString = "";
		// Create client and set our specific user-agent string
		  HttpClient client = new DefaultHttpClient();
		  HttpGet request = new HttpGet(String.format(URL, query));
		  Log.d("GetImage", "making request");
		  try {
		      HttpResponse response = client.execute(request);

		      // Check if server response is valid
		      StatusLine status = response.getStatusLine();
		      if (status.getStatusCode() != 200) {
		          throw new IOException("Invalid response from server: " + status.toString());
		      }

		      // Pull content stream from response
		      HttpEntity entity = response.getEntity();
		      InputStream inputStream = entity.getContent();

		      ByteArrayOutputStream content = new ByteArrayOutputStream();

		      // Read response into a buffered stream
		      int readBytes = 0;
		      byte[] sBuffer = new byte[512];
		      while ((readBytes = inputStream.read(sBuffer)) != -1) {
		          content.write(sBuffer, 0, readBytes);
		      }

		      // Return result from buffered stream
		      dataAsString = new String(content.toByteArray());
		      
		      System.out.println(dataAsString);
		      
		  } catch (IOException e) {
		     Log.d("error", e.getLocalizedMessage());
		  }

	    return dataAsString;

	}
}
