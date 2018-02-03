import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;



public class GetSsl {

	private final String USER_AGENT = "Mozilla/5.0";
    
	private final String HTTPS_ADDRESS = "http://httpbin.org/get";
    
    

	public static void main(String[] args) throws Exception {

		GetSsl client = new GetSsl();

		client.sendGet();


	}
    
	// HTTP GET request
	private void sendGet() throws Exception {
        

        TrustManager trm = new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) { }

            public void checkServerTrusted(X509Certificate[] certs, String authType) { }
        };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, new TrustManager[] { trm }, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        
        
        
        java.net.URL url = new URL(null, HTTPS_ADDRESS,new sun.net.www.protocol.https.Handler());
        

		//URL url = new URL(HTTPS_ADDRESS);
        javax.net.ssl.HttpsURLConnection con = (javax.net.ssl.HttpsURLConnection)url.openConnection();

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