#!/bin/bash
#for file in `find  *.sh`
for file in `ls demo/  *.sh`
do
echo ${file}
#对文件遍历，修改文件格式
#vi +':w ++ff=unix' +':q' ${file}
done
