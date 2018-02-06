import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;
import java.util.Enumeration;

import javax.crypto.Cipher;

import java.security.NoSuchAlgorithmException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;



import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;


public class ShaMan{

		// this is for testing the internal T. Rowe certificate
		//private final String HTTPS_ADDRESS = "https://deothapp:20213/pcs-rest/jaxrs/product-catalog-service/public/gb/en/products/GEFIU/detail";

		// this is for testing Clay Tablet connector
		//private final String HTTPS_ADDRESS = "https://queue.amazonaws.com/209580136678/TRP-dev-producer";
		//private final String HTTPS_ADDRESS = "https://TRP-dev-producer.s3.amazonaws.com";


				//private final static String HTTPS_ADDRESS = "https://httpbin.org/get";

				private final static String HTTPS_ADDRESS = "https://troweprice-stage.adobecqms.net/content/regent.html";


    public static void main(String[] args) throws Exception {

			System.out.println("Java version: " + System.getProperty("java.version"));
      // read in properties from config.properties
			setProperties();

		  setProxy(false);
			checkJce(false);

		  dumpKeyStore();


     // do we accept non-trusted certs ?
		 trustAllCerts(false);

		 //make the call:
		 getUrl(HTTPS_ADDRESS);

        System.exit(0);
    }

		private static void getUrl(String urlString) throws Exception {
        URL url = new URL(urlString);
        InputStream is = null;
        String actual = "";


        System.out.println("Connecting to " + urlString);


        try {
          URLConnection conn = url.openConnection();

            try {
                is = conn.getInputStream();
            } catch (IOException ioe) {
                if (conn instanceof HttpURLConnection) {
                    HttpURLConnection httpConn = (HttpURLConnection) conn;
                    int statusCode = httpConn.getResponseCode();
                    if (statusCode != 200) {
                        is = httpConn.getErrorStream();
                    }
                } else {
                    System.out.println("connection not instanceof HttpURLConnection");
                }
            }


            if(conn instanceof HttpsURLConnection){
								HttpsURLConnection httpsConn = (HttpsURLConnection) conn;

								   System.out.println("Server Cipher Suite=" + httpsConn.getCipherSuite());
									 System.out.println("Peer Principal=" + httpsConn.getPeerPrincipal().toString());
									 System.out.println("Local Principal=" +
(httpsConn.getLocalPrincipal() != null ?
									 httpsConn.getLocalPrincipal().toString() : "null" ));


						}

            actual = new String(toByteArray(is));

        } finally {
            if (is != null)
                is.close();
        }
        System.out.println("RESPONSE:" + actual);
        if (!"Success".equals(actual)) {
//            throw new AssertionError("Unexpected content: " + actual);
        }
    }


  private static void trustAllCerts(boolean ok) throws Exception{

		if(ok==false){
			return;
		}
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
			}
			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}
			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}
	   }
	 };
    // Install the all-trusting trust manager
    SSLContext sc = SSLContext.getInstance("SSL");
    sc.init(null, trustAllCerts, new java.security.SecureRandom());
    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());


    // Create all-trusting host name verifier
    HostnameVerifier allHostsValid = new HostnameVerifier() {
	     public boolean verify(String hostname, SSLSession session) {
			   return true;
	     }
    };
		/*
		 * NOTE: You don't have to actually use setDefaultHostnameVerifier
		 * in Java 8 at least. Maybe earlier versions you do.
		 *
		 */
     // Install the all-trusting host verifier
    // HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);


	}
	//
	// check if the JCE Unlimited Strength Jurisdiction Policy files have been installed in the JVM
	// see http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html
	//
	private static void checkJce(boolean yes) throws NoSuchAlgorithmException {

		if(yes == true) {
			// Without the JCE Unlimited Strength Jurisdiction Policy this results in 128, after they have been installed properly the result is 2147483647.
			int maxKeyLen = Cipher.getMaxAllowedKeyLength("AES");
			System.out.println("Checking JCE : Max allowed AES Key length = " + maxKeyLen + ", is this greater than 128?");
		} else {

          System.out.println("ATTENTION: No Check for JCE!");
		}

	}

	//
	private static void dumpKeyStore() throws Exception {



		    String storeName = System.getProperty("javax.net.ssl.trustStore");
				String storePass = System.getProperty("javax.net.ssl.trustStorePassword");

				KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());

        System.out.println("Accessing Keystore " + storeName);

				System.out.println("Keystore certs: ");


		java.io.FileInputStream fis = null;
		try {
			fis = new java.io.FileInputStream(storeName);
			keystore.load(fis, storePass.toCharArray());


			Enumeration enumeration = keystore.aliases();
			while(enumeration.hasMoreElements()) {
				String alias = (String)enumeration.nextElement();
				System.out.println();
				System.out.println("Alias Name: " + alias);
				Certificate [] certs = keystore.getCertificateChain(alias);

				printCerts(certs);

			}

		} finally {
			if (fis != null) {
            fis.close();
			}
		}

	}

	private static void printCerts(Certificate [] certs) {

		if(certs==null) return;

		for(Certificate cert : certs) {

     System.out.println(" > Type=" + cert.getType());

		 if(cert instanceof X509Certificate) {
			 	X509Certificate xcert = (X509Certificate)cert;
				printX509Cert(xcert);
		 }
		 System.out.println();

    }


	}

		private static void printX509Cert(X509Certificate xcert) {
      System.out.println(" > Pricipal=" + xcert.getSubjectX500Principal().toString());
		  System.out.println(" > Version=" + xcert.getVersion());
		  System.out.println(" > Issuer Principal=" + xcert.getIssuerX500Principal().toString());

		}


		/*
		 * set a proxy for http and https
		 */
		private static void setProxy(boolean yes){

			if(yes == true){

			   Properties systemProperties = System.getProperties();

			   // this proxy is used by AEM for connecting to Clay Tablet on AWS
			  // systemProperties.setProperty( "https.proxyHost", "qbc101.troweprice.com" );
	  		  // systemProperties.setProperty( "https.proxyPort", "8080" );

			  // this is the standard T. Rowe proxy for both HTTP and HTTPS
			   systemProperties.setProperty( "https.proxyHost", "proxy.troweprice.com" );
	  		   systemProperties.setProperty( "https.proxyPort", "8080" );
			   systemProperties.setProperty( "http.proxyHost", "proxy.troweprice.com" );
	  		   systemProperties.setProperty( "http.proxyPort", "8080" );


	          System.out.println(" >> HTTPS Proxy: " + System.getProperty("https.proxyHost"));
	          System.out.println(" >> HTTPS Proxy Port: " + System.getProperty("https.proxyPort"));
	          System.out.println(" >> HTTP Proxy: " + System.getProperty("http.proxyHost"));
	          System.out.println(" >> HTTP Proxy Port: " + System.getProperty("http.proxyPort"));

			} else {

	          System.out.println("ATTENTION: No Proxy Set!");
			}


		}

    private static byte[] toByteArray(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            byte[] b = new byte[16];
            int n = 0;
            while ((n = is.read(b)) != -1) {
                baos.write(b, 0, n);
            }
            return baos.toByteArray();
        } finally {
            baos.close();
        }
    }

		private static void setProperties() throws IOException{

			FileInputStream propertiesFile = new FileInputStream("./config.properties");
			Properties p = new Properties(System.getProperties());
			p.load(propertiesFile);
			System.setProperties(p);
		}
}
