# shaman
Java networking with no dependencies

This was created when we were trouble shooting Clay Tablet Connector
It is based on the AWS ShaTest, https://www.amazonsha256.com/shaTest.zip

Clay Tablet added alot of useful stuff. That's why it is called myShaTest

Usage:

`$ javac ShaMan.java`

`$ java ShaMan`

## Keystore / Truststore commands

* Use this command to install a cert in the *_default JVM truststore_*

```
keytool -import -alias myalias -file /path/to/cert/mycert.cer -keystore $JAVA_HOME/jre/lib/security/cacerts -storepass changeit
```

* Use this command to install a cert in the *_custom JVM truststore_*
(Note: use the `javax.net.ssl.trustStore=` System Property in the java app to
use the custom TrustStore.)

```
keytool -import -alias myalias -file /path/to/cert/mycert.crt -keystore ./mykeyStore  -storepass 123456
```


### Examples

`keytool -import -alias httpbinorg -file /Users/paulmyers/Desktop/httpbinorg.crt -keystore ./keyStore  -storepass 123456`


`keytool -import -alias adobecqmsnet -file /Users/paulmyers/Desktop/adobecqmsnet.crt -keystore ./keyStore  -storepass 123456`

NOTE: use the -trustcacerts option to include the root CA cert in the truststore.

* Use openssl to look dump the data in a cert:

`openssl x509 -noout -text -in myCert.crt` 

## References
https://docs.oracle.com/javase/8/docs/technotes/guides/security/jsse/JSSERefGuide.html

https://blogs.oracle.com/java-platform-group/diagnosing-tls,-ssl,-and-https

https://whatsmychaincert.com/
