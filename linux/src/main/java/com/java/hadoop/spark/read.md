

启动spark  
进入到sbin目录下面  hadoop配置了sbin 这里不能配置
./start-all.sh


6 启动hadoop然后本地运行spark-shell

在shell下执行文件的统计排序
val textFile=sc.textFile("hdfs://192.168.42.220:9000/input/1.txt")
hdfsFile.first()


val textFile=sc.textFile("/home/hadoop/test/input4test/1.txt")
textFile.first()
