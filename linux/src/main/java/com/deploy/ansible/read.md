#打包程序

tar -cvf anytxn_ansible.tar anytxn_ansible/
tar
最常用的打包命令是 tar，使用 tar 程序打出来的包我们常称为 tar 包，tar 包文件的命令通常都是以 .tar 结尾的。生成 tar 包后，就可以用其它的程序来进行压缩了，所以首先就来讲讲 tar 命令的基本用法。
tar 命令的选项有很多(用 man tar 可以查看到)，但常用的就那么几个选项，下面来举例说明一下：
# tar -cf all.tar *.jpg
这条命令是将所有 .jpg 的文件打成一个名为 all.tar 的包。-c 是表示产生新的包，-f 指定包的文件名。
# tar -rf all.tar *.gif
这条命令是将所有 .gif 的文件增加到 all.tar 的包里面去，-r 是表示增加文件的意思。
# tar -uf all.tar logo.gif
这条命令是更新原来 tar 包 all.tar 中 logo.gif 文件，-u 是表示更新文件的意思。
# tar -tf all.tar
这条命令是列出 all.tar 包中所有文件，-t 是列出文件的意思。
# tar -xf all.tar




