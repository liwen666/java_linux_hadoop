#!/bin/bash
user=(hadoop yuanhui yancheng zhaoling wuyang linying)
#数组下标从0开始  只有六个用户   -gt（大于）  -lt（小于）  -eq（等于）-le（         小于等于）  -ge（大于等于）
i=0
num=6
while [ $i -lt $num ]
 do
#添加用户
   useradd ${user[i]}
echo ${user[i]} --------------------------- $i
#赋予密码与用户名一致
   echo ${user[i]} | passwd ${user[i]} --stdin
i=$(($i+1))
done