
scala 的常用函数

val textFile=sc.textFile("hdfs://192.168.42.220:9000/input/1.txt")
//显示第一行
hdfsFile.first()  
//显示总行数
hdfsFile.count()
val textFile=sc.textFile("/home/hadoop/test/input4test/1.txt")
textFile.first()