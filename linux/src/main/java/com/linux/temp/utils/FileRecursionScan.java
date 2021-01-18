package com.linux.temp.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author lw
 * @since 2020/10/22 16:42
 */
@Slf4j
public class FileRecursionScan {
    /**
     * 初始化nacos配置
     */
    public static File getResourceCfg(String dataId) {
        File file = null;
        try {
            URL resource = FileRecursionScan.class.getClassLoader().getResource("temp/" + dataId);
            if (resource == null) {
                return null;
            }
            file = new File(resource.toURI());
        } catch (URISyntaxException e) {
            log.error("解析启动配置异常, ", e);
        }
        return file;
    }

    public static File findFile(File target, String ext) {
        if (target == null) return null;
        //如果文件是目录
        if (target.isDirectory()) {
            File[] files = target.listFiles();
            if (files != null) {
                for (File f : files) {
                    findFile(f, ext);//递归调用
                }
            }
        } else {
            //如果文件不是目录
            String name = target.getName();
//           System.out.println(name);
            if (name.equals(ext)) {
                System.out.println(target.getAbsolutePath());
                return target;
            }
        }
        return null;
    }
}