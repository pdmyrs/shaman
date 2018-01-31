

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Get {

	private final String USER_AGENT = "Mozilla/5.0";
    
	private final String HTTP_ADDRESS = "http://httpbin.org/get";
    
    

	public static void main(String[] args) throws Exception {

		Get client = new Get();

		client.sendGet();


	}

	// HTTP GET request
	private void sendGet() throws Exception {

		String url = HTTP_ADDRESS;

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
        
        
        
        InputStream instream = con.getInputStream();
         
                    try {

                        instream.read();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int readBytes = -1;

                		while((readBytes = instream.read(buffer)) > 1){
                			    baos.write(buffer,0,readBytes);
                	    }
                		
                		System.out.println(baos.toString("UTF-8"));
                                                
                    } catch (IOException ex) {
                        // In case of an IOException the connection will be released
                        // back to the connection manager automatically
                        throw ex;
                    } finally {
                       instream.close();
                    }
        

	}

}