set CATALINA_OPTS=-Xmx512m -XX:MaxPermSize=256m -Dfile.encoding=UTF-8

set CATALINA_OPTS=%CATALINA_OPTS% -Dlog4j.configuration=file:../conf/log4j.xml

set CATALINA_OPTS=%CATALINA_OPTS% -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=2199 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false

rem set JPDA_OPTS=-Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=n
set JPDA_OPTS=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=9797

set JAVA_OPTS=-Djavax.net.ssl.trustStore=keystore -Djavax.net.ssl.trustStorePassword=qwerty