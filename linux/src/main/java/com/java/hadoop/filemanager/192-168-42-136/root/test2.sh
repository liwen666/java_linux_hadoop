#!/bin/bash
#自定义函数遍历目录
function ergodic(){
#for file in `find . -name "*.py" -o -name "*.sh" -o -name "*.css" -o -name "*.js" -o -name "*.html"`
#for file in `find . -name "*.py" -o -name "*.sh" -o -name "*.css" -o -name "*.js" -o -name "*.html" |sed 's#.*/##'`
    for file in ` ls $1 `
    do
    #判断文件是否是目录
        if [ -d $1"/"$file ]
        then
        #递归调用函数 遍历目录
             ergodic $1"/"$file
        else
#             wc -L $1"/"$file | cut -d' ' -f1 >> /home/liwen/out
# wc -L $1"/"$file 这个命令是求当前这个文件的行数，没有包括空行
#| cut -d' ' -f1 这个命令是管道的应用，通过前面得到的结果我们去重新定义cut的分割符为空格，并且只显示第一列
#>> /home/chenguolin/out 把前面得到的内容重定向到家目录下的out文件
                echo ${file}
#                if [ "${file##*.}"x = "css"x ]||[ "${file##*.}"x = "js"x ];then
                if [ "${file##*.}"x = "sh"x ];then
                echo "修改文件权限，修改文件为unix--->:" ${file}
                vi +':w ++ff=unix' +':q' ${file}
                fi

        fi
    done
}
INIT_PATH="/home/liwen/shell_test/demo"
ergodic $INIT_PATH
