#!/bin/bash

set -e

base_dir="$(dirname "$0")/.."
lib_dir="$base_dir/lib"
lib_dir_development="$base_dir/target/lib"
if [ ! -e "$lib_dir" -a -e "$lib_dir_development" ]; then
	lib_dir="$lib_dir_development"
	CLASSPATH="$CLASSPATH:$base_dir/target/classes"
fi

CLASSPATH="$CLASSPATH:$lib_dir/*"

KAFKA_VERSION="1.0.0"


if [ -z "$JAVA_HOME" ]; then
	JAVA="java"
else
	JAVA="$JAVA_HOME/bin/java"
fi

JMX_OPTS="${JMX_OPTS} -Djava.rmi.server.hostname=127.0.0.1"
JMX_OPTS="${JMX_OPTS} -Dcom.sun.management.jmxremote.rmi.port=1099"
JMX_OPTS="${JMX_OPTS} -Dcom.sun.management.jmxremote"
JMX_OPTS="${JMX_OPTS} -Dcom.sun.management.jmxremote.port=1099"
JMX_OPTS="${JMX_OPTS} -Dcom.sun.management.jmxremote.ssl=false"
JMX_OPTS="${JMX_OPTS} -Dcom.sun.management.jmxremote.authenticate=false"

JAVA_OPT="${JAVA_OPT} ${JMX_OPTS}"



# Launch mode
if [ "x$DAEMON_MODE" = "xtrue" ]; then
    nohup $JAVA $JAVA_OPTS -Dfile.encoding=UTF-8 -Dlog4j.shutdownCallbackRegistry=com.djdch.log4j.StaticShutdownCallbackRegistry -cp $CLASSPATH com.zendesk.maxwell.jrx.JrxMaxStater "$@" > "$CONSOLE_OUTPUT_FILE" 2>&1 < /dev/null &
else
    exec $JAVA $JAVA_OPTS -Dfile.encoding=UTF-8 -Dlog4j.shutdownCallbackRegistry=com.djdch.log4j.StaticShutdownCallbackRegistry -cp $CLASSPATH com.zendesk.maxwell.jrx.JrxMaxStater "$@"
fi
