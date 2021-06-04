


#！/bin/sh  
#arrayTest  
name=(yunix yhx yfj)  
echo "array is:${name[@]}"  
echo "array length is:${#name[*]}"  
echo ${name[1]}  
name[1]=yang  
echo ${name[1]}  
read -a name  
echo ${name[1]}  
echo "loop the array"  
len=${#name[*]}  
i=0  
while [ $i -lt $len ]  
do  
echo ${name[$i]}  
let i++  
done 


#!/usr/bin/env bash

Project_HOME=/home/liwen/flink12
SCRIPT_HOME=/home/liwen/flink12/examples
CLASSPATH=$CLASSPATH:/home/liwen/flink12/examples/MysqlComplemented.jar
JAVA_OPT=" -XX:+UseG1GC -Xms1024M -Xms1024M -XX:MaxDirectMemorySize=268435458 -XX:MaxMetaspaceSize=268435456 "
MAIN_CLASS=":: jrx.data.hub.flink.example.table.MysqlComplemented"



for key in "$@"
do
echo ----------"$key"----------
        if [ "x$key" = "x--project_home" ]; then
            Project_HOME=$key
        #else
#               echo "Error: Multiple matches for kafka version: $KAFKA_VERSION"
        fi
done




echo 后缀 $@
echo 长度 $#


for i in $(seq 1 $#)

do

echo ---------------------------
echo $i
echo $1
echo $[i+1]
echo $@
echo --------c1------- ${@:i:1}
echo ---------c2-------- ${@:i+1:1}
echo --------key--$i-----------------
echo -----------------------
done


echo -----------参数解析----------------
x=${@:2:1}
echo ${x}

echo "flink地址是--$Project_HOME"

for jarfile in "$Project_HOME"/lib/*.jar
do
   CLASSPATH="$CLASSPATH:$jarfile";
done
echo -环境变量是----$CLASSPATH------



#java $JAVA_OPT  -classpath $CLASSPATH$MAIN_CLASS  --script $SCRIPT_HOME    > start.out &tail -f start.out
