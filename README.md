# shaman
Java networking with no dependencies

This was created when we were trouble shooting Clay Tablet Connector
It is based on the AWS ShaTest, https://www.amazonsha256.com/shaTest.zip
Clay Tablet added alot of useful stuff. That's why it is called myShaTest

Usage:

$ javac ShaTest.java
$ java ShaTest

## Keystore / Truststore commands
Use this command to install a cert in the default jvm keystore
* keytool -import -alias apigeedev -file /home/vagrant/CERTS/apigee-dev.cer -keystore $JAVA_HOME/jre/lib/security/cacerts -storepass changeit
