package com.linux.temp.utils;

import java.io.File;

/**
 * @author Crazy-ZJ
 * @Title:FileDemo2
 * @Description:在指定的目录中查找文件
 * @data 2017年9月21日下午5:45:24
 * @book 疯狂java讲义（第三版）：
 */
public class FileDemo2 {
    public static void main(String[] args) {
        File file = FileRecursionScan.findFile(new File("D:\\workspace\\java_linux_hadoop\\linux\\linux\\src\\main\\java\\com\\java\\hadoop\\filemanager"), "core-site.xml");
        System.out.println(file.getName());
    }


}