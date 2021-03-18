yarn模式
#将hadoop依赖包下载下来放到flink的lib目录下
bin/yarn-session.sh -h

##每个任务一个集群
./bin/flink run   -m yarn-cluster  -c org.apache.flink.examples.java.wordcount.WordCount  /home/liwen/flink12/flink12/examples/batch/WordCount.jar --port 9000

#yarnserssion模式  将app的启动放到yarn容器

nohup ./bin/yarn-session.sh -n 12 -st -tm 1000 -s 5 -nm flink-1 > yarn.log & tail -100f yarn.log

                   ./bin/yarn-session.sh -n 2 -jm 1024 -tm 1024 -s 2

./bin/flink run  -c org.apache.flink.examples.java.wordcount.WordCount  /home/liwen/flink12/flink12/examples/batch/WordCount.jar --port 9000


./bin/flink run -s hdfs://master:9000/flink/savepoint-ca1f3a-9f86a020ee76 -c org.apache.flink.examples.java.wordcount.WordCount  /home/liwen/flink12/flink12/examples/batch/WordCount.jar --port 9000

#远程执行jar任务

./bin/flink run  -m 11.11.1.79:8081  -s hdfs://master:9000/flink/savepoint-ca1f3a-9f86a020ee76 -c org.apache.flink.examples.java.wordcount.WordCount  /home/liwen/flink12/flink12/examples/batch/WordCount.jar --port 9000

./bin/flink run -m 11.11.1.79:8081  -c org.apache.flink.examples.java.wordcount.WordCount  /home/liwen/flink12/flink12/examples/batch/WordCount.jar --port 9000
- m 表示执行的jobmanager的ip和端口
- c 表示入口类
- p 表示并行度

/home/liwen/application/flink12/bin/flink run -m 11.11.1.79:8088  -c org.apache.flink.examples.java.wordcount.WordCount  /home/liwen/application/flink12/examples/batch/WordCount.jar --port 9000


yarn 查看app的运行情况
yarn application -list
yarn application -kill application_1539058959130_0001







Usage:
   Required
     -n,--container <arg>   为YARN分配容器的数量 (=Number of Task Managers)
   Optional
     -D <property=value>             动态属性 
     -d,--detached                   以分离模式运行作业
     -h,--help                       Yarn session帮助.
     -id,--applicationId <arg>       连接到一个正在运行的YARN session
     -j,--jar <arg>                  Flink jar文件的路径
     -jm,--jobManagerMemory <arg>    JobManager的内存大小，driver-memory [in MB]
     -m,--jobmanager <arg>           Address of the JobManager (master) to which to connect. Use this flag to connect to a different JobManager than the one specified in the configuration.
     -n,--container <arg>            TaskManager的数量，相当于executor的数量
     -nm,--name <arg>                设置YARN应用自定义名称 
     -q,--query                      显示可用的YARN资源 (memory, cores)
     -qu,--queue <arg>               指定YARN队列
     -s,--slots <arg>                每个JobManager的core的数量，executor-cores。建议将slot的数量设置每台机器的处理器数量
     -st,--streaming                 在流模式下启动Flink
     -t,--ship <arg>                 在指定目录中传送文件(t for transfer)
     -tm,--taskManagerMemory <arg>   每个TaskManager的内存大小，executor-memory  [in MB]
     -yd,--yarndetached              如果存在，则以分离模式运行作业 (deprecated; use non-YARN specific option instead)
     -z,--zookeeperNamespace <arg>   为高可用性模式创建Zookeeper子路径的命名空间