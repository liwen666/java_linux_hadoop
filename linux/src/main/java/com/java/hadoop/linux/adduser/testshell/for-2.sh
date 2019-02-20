#!/bin/bash
echo "for-3字符串循环" ;
#循环linux命令获得的结果集
for i in `ls`;
do
echo $i is file name\! ;
done

echo "-----------------------" ;

for i in $* ;
do
echo $i is input chart\! ;
done

echo "-----------------------" ;

for i in f1 f2 f3 ;
do
echo $i is appoint ;
done

echo "-----------------------" ;

list="rootfs usr data data2"
for i in $list;
do
echo $i is appoint ;
done

echo "测试while循环"
user=(luohe yuanhui yancheng zhaoling wuyang linying)
i=0
num=7
while [ $i -lt $num ]
 do
#添加用户
echo $i
#   useradd ${user[i]}
#赋予密码与用户名一致
#   echo ${user[i]} | passwd ${user[i]} --stdin
i=$(($i+1))
done

echo 路径查找===========================;
echo "路径查找";
for file in /home/hadoop/*;
do
echo $file is file path \! ;
done

echo -----------------------------
for file in $( ls /home/hadoop/bash/ *.sh)
do
echo $file is file path \! ;
done