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


### Example

`keytool -import -alias httpbinorg -file /Users/paulmyers/Desktop/httpbinorg.crt -keystore ./keyStore  -storepass 12345`
