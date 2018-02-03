# shaman
Java networking with no dependencies

This was created when we were trouble shooting Clay Tablet Connector
It is based on the AWS ShaTest, https://www.amazonsha256.com/shaTest.zip
Clay Tablet added alot of useful stuff. That's why it is called myShaTest

Usage:

$ javac ShaTest.java
$ java ShaTest

## Keystore / Truststore commands

* Use this command to install a cert in the _default JVM keystore_

```
keytool -import -alias myalias -file /path/to/cert/mycert.cer -keystore $JAVA_HOME/jre/lib/security/cacerts -storepass changeit
```

* Use this command to install a cert in the _custom JVM keystore_
(Note: use the javax.net.ssl.keyStore System Property in the java app.)

```
keytool -import -alias myalias -file /path/to/cert/mycert.cer -keystore $JAVA_HOME/jre/lib/security/cacerts -storepass changeit
```
