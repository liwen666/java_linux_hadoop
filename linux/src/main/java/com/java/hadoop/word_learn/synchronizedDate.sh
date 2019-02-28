#!/bin/sh
#同步集群系统时间
#提示“请输入当前时间，格式为：2017-3-2”，把用户的输入保存入变量date中
read -t 30 -p "请输入正确时间: 格式为:'09:30:56':  " nowdate
echo -e "\n"
echo "当前时间为:$nowdate"
#同步时间
echo "开始同步时间……"
for node in master01 slave01 slave02 slave03;do ssh $node "date -s $nowdate ";done